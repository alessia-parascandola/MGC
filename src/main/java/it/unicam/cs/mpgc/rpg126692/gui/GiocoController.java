package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.Cassero;
import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.carte.Eventi.CartaEvento;
import it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.InputStream;
import java.util.List;

public class GiocoController {

    @FXML private ImageView imgCartaScoperta;
    @FXML private ImageView imgMazzoCoperto;
    @FXML private ImageView imgPersonaggio;
    @FXML private ImageView imgDadoPersonaggio;
    @FXML private HBox containerDadiMostro;
    @FXML private Label lblHpPersonaggio;
    @FXML private Label lblNomePersonaggio;

    private Personaggio personaggioGiocatore;
    private Cassero cassero;
    private CartaCapitolo cartaCapitoloCorrente;
    private MazzoOggetti mazzoOggetti = new MazzoOggetti();

    // Gestione HP locale per la GUI per evitare blocchi da Scanner del backend
    private int hpCorrentiGui;

    @FXML
    public void initialize() {
        caricaImmagineSuView(imgMazzoCoperto, "/images/carte/dorso_intro.png");
    }

    public void setPersonaggio(Personaggio personaggio) {
        setPersonaggioGiocatore(personaggio);
    }

    public void setPersonaggioGiocatore(Personaggio personaggio) {
        this.personaggioGiocatore = personaggio;
        if (personaggio != null) {
            this.hpCorrentiGui = personaggio.getHP();
        }

        aggiornaGraficaPersonaggio();

        if (personaggioGiocatore != null) {
            String nomePersonaggioClean = personaggioGiocatore.getNome().toLowerCase().trim();
            String pathDadoIniziale = "/images/dadi/" + nomePersonaggioClean + "_dado.png";
            caricaImmagineSuView(imgDadoPersonaggio, pathDadoIniziale);
        }

        if (this.cassero == null) {
            inizializzaCasseroDefault();
        }
    }

    public void setCassero(Cassero cassero) {
        this.cassero = cassero;
    }

    private void inizializzaCasseroDefault() {
        try {
            MazzoCapitoli mazzo = new MazzoCapitoli();
            this.cassero = new Cassero(mazzo);
        } catch (Exception e) {
            System.err.println("Info Cassero: " + e.getMessage());
        }
    }

    // --- PESCA CARTA DAL CASSERO ---
    @FXML
    private void handlePescaCartaCassero(Event event) {
        if (cartaCapitoloCorrente instanceof CartaMostro mostro && !mostro.eSconfitto()) {
            mostraAvviso("Combattimento in corso", "Devi sconfiggere il mostro prima di avanzare!");
            return;
        }

        if (cassero == null || !cassero.haCarte()) {
            mostraAvviso("Fine Esplorazione", "Non ci sono più carte nel Cassero!");
            if (imgMazzoCoperto != null) imgMazzoCoperto.setImage(null);
            return;
        }

        cartaCapitoloCorrente = cassero.pescaProssimaCarta();

        if (cartaCapitoloCorrente != null && cartaCapitoloCorrente.getImagePath() != null) {
            caricaImmagineSuView(imgCartaScoperta, cartaCapitoloCorrente.getImagePath());
        }

        aggiornaDorsoMazzo();

        if (cartaCapitoloCorrente instanceof CartaMostro mostro) {
            if (mostro.getTracciatoSimboli().isEmpty()) {
                mostro.generaSimboliVita(new DadoCapitolo(), 1);
            }
            aggiornaGraficaDadiMostro(mostro.getTracciatoSimboli());
        } else if (cartaCapitoloCorrente instanceof CartaBoss boss) {
            if (boss.getTracciatoSimboli().isEmpty()) {
                boss.generaSimboliVita(new DadoCapitolo(), 1);
            }
            aggiornaGraficaDadiMostro(boss.getTracciatoSimboli());
        } else if (cartaCapitoloCorrente instanceof CartaEvento) {
            svuotaDadiMostro();
            mostraAvviso("Carta Evento", "Hai incontrato una carta Evento.");
        } else {
            svuotaDadiMostro();
            if (cartaCapitoloCorrente != null) {
                cartaCapitoloCorrente.esegui(personaggioGiocatore, mazzoOggetti);
                aggiornaGraficaPersonaggio();
            }
        }
    }

    private void aggiornaDorsoMazzo() {
        if (cassero == null || !cassero.haCarte()) {
            if (imgMazzoCoperto != null) imgMazzoCoperto.setImage(null);
            return;
        }

        if (cassero.carteRimanenti() == 1) {
            caricaImmagineSuView(imgMazzoCoperto, "/images/carte/dorso_boss.png");
        } else {
            caricaImmagineSuView(imgMazzoCoperto, "/images/carte/dorso_capitolo.png");
        }
    }

    // --- TIRO DADO PERSONAGGIO ---
    @FXML
    private void handleLancioDadoPersonaggio(Event event) {
        if (personaggioGiocatore == null || personaggioGiocatore.getDado() == null) return;

        if (!(cartaCapitoloCorrente instanceof CartaMostro mostro) || mostro.eSconfitto()) {
            return;
        }

        FacciaDado facciaUscita = personaggioGiocatore.lanciaDado();
        if (facciaUscita.getImagePath() != null) {
            caricaImmagineSuView(imgDadoPersonaggio, facciaUscita.getImagePath());
        }

        List<Simbolo> simboliLanciati = facciaUscita.getSimboli();
        boolean colpoSegnato = false;

        for (Simbolo s : simboliLanciati) {
            if (mostro instanceof CartaBoss boss) {
                if (boss.puoRimuovereSimbolo(s)) {
                    boss.rimuoviSimbolo(s);
                    colpoSegnato = true;
                    break;
                }
            } else if (mostro.haSimbolo(s)) {
                mostro.rimuoviSimbolo(s);
                colpoSegnato = true;
                break;
            }
        }

        if (colpoSegnato) {
            aggiornaGraficaDadiMostro(mostro.getTracciatoSimboli());

            if (mostro.eSconfitto()) {
                mostraAvviso("Vittoria!", "Hai sconfitto il mostro! Ora puoi avanzare.");
                svuotaDadiMostro();
            } else {
                mostraAvviso("Colpo a segno!", "Hai colpito il mostro! Un simbolo è stato eliminato.");
            }
        } else {
            int dannoBase = mostro.getDanno();
            boolean haScudoDado = facciaUscita.haScudo();

            if (haScudoDado) {
                mostraAvviso("Attacco Parato!", "Il doppio simbolo/scudo sul dado ha parato completamente l'attacco!");
            } else {
                int riduzioneScudo = 0;
                for (Oggetto obj : personaggioGiocatore.getInventario().getOggetti()) {
                    if (obj.getNome().toLowerCase().contains("scudo")) {
                        riduzioneScudo = 1;
                        break;
                    }
                }

                int dannoEffettivo = dannoBase;
                if (riduzioneScudo > 0 && dannoBase > 1) {
                    dannoEffettivo = Math.max(1, dannoBase - riduzioneScudo);
                }

                // Sfruttiamo subisciDanno del backend o sottraiamo direttamente e sincronizziamo
                personaggioGiocatore.subisciDanno(dannoEffettivo, false, false);
                aggiornaGraficaPersonaggio();

                if (personaggioGiocatore.isSconfitto()) {
                    mostraAvviso("Game Over", "Il tuo personaggio è stato sconfitto!");
                } else {
                    if (riduzioneScudo > 0 && dannoBase > 1) {
                        mostraAvviso("Attacco Subito!", "Lo scudo ha ridotto il danno! Subisci " + dannoEffettivo + " danni.");
                    } else {
                        mostraAvviso("Attacco Subito!", "Nessun simbolo corrispondente! Subisci " + dannoEffettivo + " danni.");
                    }
                }
            }
        }
    }

    @FXML
    private void handlePescaOggetto(Event event) {
        mostraAvviso("Mazzo Oggetti", "Gestione oggetti.");
    }

    @FXML
    private void handleUsaOggetto(Event event) {
        mostraAvviso("Inventario", "Usa oggetto.");
    }

    @FXML
    private void handleScartaOggetto(Event event) {
        mostraAvviso("Inventario", "Scarta oggetto.");
    }

    // --- REFRESH GRAFICO ---

    private void aggiornaGraficaDadiMostro(List<Simbolo> simboliDadi) {
        if (containerDadiMostro == null) return;
        containerDadiMostro.getChildren().clear();
        if (simboliDadi == null) return;

        containerDadiMostro.setAlignment(Pos.CENTER);
        containerDadiMostro.setSpacing(10);

        for (Simbolo s : simboliDadi) {
            ImageView imgSimbolo = new ImageView();
            String path = "/images/dadi/" + s.name().toLowerCase() + "_capitolo.png";
            caricaImmagineSuView(imgSimbolo, path);

            imgSimbolo.setFitWidth(70);
            imgSimbolo.setFitHeight(70);
            imgSimbolo.setPreserveRatio(true);

            containerDadiMostro.getChildren().add(imgSimbolo);
        }
    }

    private void svuotaDadiMostro() {
        if (containerDadiMostro != null) {
            containerDadiMostro.getChildren().clear();
        }
    }

    private void aggiornaGraficaPersonaggio() {
        if (personaggioGiocatore != null) {
            javafx.application.Platform.runLater(() -> {
                // 1. Aggiorna HP con scritta bianca e dimensione leggibile
                if (lblHpPersonaggio != null) {
                    lblHpPersonaggio.setText("HP: " + personaggioGiocatore.getHP() + " / " + personaggioGiocatore.getSaluteMassima());
                    lblHpPersonaggio.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
                }

                // 2. Aggiorna il nome del personaggio
                if (lblNomePersonaggio != null) {
                    lblNomePersonaggio.setText(personaggioGiocatore.getNome());
                    lblNomePersonaggio.setStyle("-fx-text-fill: white;");
                }
            });

            // 3. Aggiorniamo l'immagine della carta del personaggio
            if (imgPersonaggio != null) {
                String pathCarta = "/images/personaggi/" + personaggioGiocatore.getNome().toLowerCase().trim() + ".png";
                caricaImmagineSuView(imgPersonaggio, pathCarta);
            }
        }
    }

    private void caricaImmagineSuView(ImageView imageView, String resourcePath) {
        if (imageView == null || resourcePath == null) return;
        try {
            InputStream is = getClass().getResourceAsStream(resourcePath);
            if (is != null) {
                imageView.setImage(new Image(is));
            } else {
                System.err.println("Immagine non trovata: " + resourcePath);
            }
        } catch (Exception e) {
            System.err.println("Errore caricamento: " + resourcePath);
        }
    }

    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
