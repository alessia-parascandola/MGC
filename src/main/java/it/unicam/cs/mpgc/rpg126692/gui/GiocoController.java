package it.unicam.cs.mpgc.rpg126692.gui;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

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
    @FXML private Label lblStanze;
    @FXML private Label lblNomePersonaggio;
    @FXML private HBox containerMano;

    private Personaggio personaggioGiocatore;
    private Cassero cassero;
    private int totaleStanze;
    private CartaCapitolo cartaCapitoloCorrente;
    private MazzoOggetti mazzoOggetti = new MazzoOggetti();

    // Impedisce azioni rientranti durante i dialoghi modali.
    private boolean occupato;

    @FXML
    public void initialize() {
        // Caso 1: All'avvio mostra il dorso della carta Intro
        caricaImmagineSuView(imgMazzoCoperto, "/images/retro_cartaIntro.png");
    }

    public void setPersonaggio(Personaggio personaggio) {
        setPersonaggioGiocatore(personaggio);
    }

    public void setPersonaggioGiocatore(Personaggio personaggio) {
        this.personaggioGiocatore = personaggio;


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
        totaleStanze = cassero.carteRimanenti();
        aggiornaContatoreStanze();
    }

    private void inizializzaCasseroDefault() {
        try {
            MazzoCapitoli mazzo = new MazzoCapitoli();
            setCassero(new Cassero(mazzo));
        } catch (Exception e) {
            System.err.println("Info Cassero: " + e.getMessage());
        }
    }

    @FXML
    private void handlePescaCartaCassero(Event event) {
        if (occupato || personaggioGiocatore == null || personaggioGiocatore.isSconfitto()) return;
        if (cassero == null || !cassero.haCarte()) return;
        eseguiAzione(() -> {
            for (Oggetto obj : personaggioGiocatore.getInventario().getOggetti()) {
                if (obj instanceof it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.FrammentoDellaFuria furia) furia.resetTurno();
            }
            cartaCapitoloCorrente = cassero.pescaProssimaCarta();
            aggiornaContatoreStanze();
            caricaImmagineSuView(imgCartaScoperta, cartaCapitoloCorrente.getImagePath());
            cartaCapitoloCorrente.esegui(personaggioGiocatore, mazzoOggetti);
            if (personaggioGiocatore.eVivo() && cartaCapitoloCorrente instanceof CartaMostro mostro
                    && mostro.eSconfitto() && !mazzoOggetti.isVuoto()) {
                it.unicam.cs.mpgc.rpg126692.GestorePartita.gestisciAcquisizioneOggetto(personaggioGiocatore, mazzoOggetti.pesca());
            }
            if (personaggioGiocatore.isSconfitto() || !cassero.haCarte()) {
                String esito = personaggioGiocatore.isSconfitto() ? "SCONFITTA" : "VITTORIA";
                it.unicam.cs.mpgc.rpg126692.Interazioni.corrente().println(esito);
                try {
                    new it.unicam.cs.mpgc.rpg126692.StoricoPartite().salva(personaggioGiocatore.getNome(), esito);
                } catch (java.io.IOException errore) {
                    mostraAvviso("Errore salvataggio", errore.getMessage());
                }
            }
        });
    }

    private void eseguiAzione(Runnable azione) {
        if (occupato) return;
        occupato = true;
        InterazioneGrafica interazione = new InterazioneGrafica(this::aggiornaStato);
        try {
            it.unicam.cs.mpgc.rpg126692.Interazioni.esegui(interazione, azione);
            interazione.mostraEsito();
        } catch (RuntimeException errore) {
            mostraAvviso("Errore durante la partita", errore.toString());
        } finally {
            occupato = false;
            aggiornaStato();
        }
    }

    private void aggiornaContatoreStanze() {
        if (lblStanze != null && cassero != null) {
            lblStanze.setText("Stanza: " + (totaleStanze - cassero.carteRimanenti()) + "/" + totaleStanze);
        }
    }

    private void aggiornaStato() {
        aggiornaGraficaDado();
        aggiornaGraficaPersonaggio();
        aggiornaGraficaMani();
        aggiornaDorsoMazzo();
        if (cartaCapitoloCorrente instanceof CartaMostro mostro) aggiornaGraficaDadiMostro(mostro.getTracciatoSimboli());
        else svuotaDadiMostro();
    }
    private void aggiornaGraficaDado() {
        if (personaggioGiocatore == null) return;
        FacciaDado risultato = personaggioGiocatore.getUltimoRisultatoDado();
        if (risultato != null) {
            caricaImmagineSuView(imgDadoPersonaggio, risultato.getImagePath());
        }
    }

    private void aggiornaDorsoMazzo() {
        if (cassero == null || !cassero.haCarte()) {
            if (imgMazzoCoperto != null) imgMazzoCoperto.setImage(null);
            return;
        }

        // Se è rimasta 1 sola carta, mostra il dorso del Boss
        if (cassero.carteRimanenti() == 1) {
            caricaImmagineSuView(imgMazzoCoperto, "/images/dorso_boss.jpg");
        } else {
            // Per tutte le altre carte capitolo
            caricaImmagineSuView(imgMazzoCoperto, "/images/dorso_carteCapitolo.png");
        }
    }

    @FXML
    private void handleLancioDadoPersonaggio(Event event) {
        if (!occupato) mostraAvviso("Dado", "I lanci si eseguono nei dialoghi della stanza.");
    }

    @FXML
    private void handleUsaOggetto(Event event) {
        if (personaggioGiocatore == null || personaggioGiocatore.isSconfitto() || !cassero.haCarte()) return;
        eseguiAzione(() -> {
            var oggetti = personaggioGiocatore.getInventario().getOggetti();
            if (oggetti.isEmpty()) return;
            javafx.scene.control.ChoiceDialog<Oggetto> scelta = new javafx.scene.control.ChoiceDialog<>(oggetti.get(0), oggetti);
            scelta.setHeaderText("Scegli un oggetto da usare");
            scelta.showAndWait().ifPresent(obj -> obj.usa(personaggioGiocatore, null));
        });
    }

    @FXML
    private void handleScartaOggetto(Event event) {
        if (personaggioGiocatore == null || personaggioGiocatore.isSconfitto()) return;
        eseguiAzione(() -> {
            var oggetti = personaggioGiocatore.getInventario().getOggetti();
            if (oggetti.isEmpty()) return;
            javafx.scene.control.ChoiceDialog<Oggetto> scelta = new javafx.scene.control.ChoiceDialog<>(oggetti.get(0), oggetti);
            scelta.setHeaderText("Scegli un oggetto da scartare");
            scelta.showAndWait().ifPresent(obj -> personaggioGiocatore.getInventario().scarta(oggetti.indexOf(obj)));
        });
    }
    @FXML
    private void handleStorico(Event event) {
        if (occupato) return;
        try {
            var righe = new it.unicam.cs.mpgc.rpg126692.StoricoPartite().leggi();
            mostraAvviso("Storico partite", righe.isEmpty() ? "Nessuna partita conclusa." : String.join("\n", righe));
        } catch (java.io.IOException errore) {
            mostraAvviso("Errore lettura storico", errore.getMessage());
        }
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
            {
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
            }

            // 3. Aggiorniamo l'immagine della carta del personaggio
            if (imgPersonaggio != null) {
                String pathCarta = "/images/personaggi/" + personaggioGiocatore.getNome().toLowerCase().trim() + ".png";
                caricaImmagineSuView(imgPersonaggio, pathCarta);
            }
        }
    }

    private void aggiornaGraficaMani() {
        {
            if (containerMano == null) return;

            containerMano.getChildren().clear(); // Pulisce la grafica precedente

            if (personaggioGiocatore == null || personaggioGiocatore.getInventario() == null) return;

            List<Oggetto> oggettiInMano = personaggioGiocatore.getInventario().getOggetti();
            if (oggettiInMano == null || oggettiInMano.isEmpty()) return;

            for (Oggetto obj : oggettiInMano) {
                ImageView imgOggetto = new ImageView();

                String path = obj.getImagePath();
                if (path != null) {
                    if (!path.startsWith("/")) {
                        path = "/" + path;
                    }
                    try (InputStream is = getClass().getResourceAsStream(path)) {
                        if (is != null) {
                            imgOggetto.setImage(new Image(is));
                            // Imposta le dimensioni della miniatura dell'oggetto nell'HBox
                            imgOggetto.setFitWidth(170);
                            imgOggetto.setFitHeight(240);
                            imgOggetto.setPreserveRatio(true);

                            containerMano.getChildren().add(imgOggetto);
                            System.out.println("SUCCESSO: Oggetto aggiunto a containerMano -> " + obj.getNome());
                        } else {
                            System.err.println("ERRORE: Immagine oggetto non trovata -> " + path);
                        }
                    } catch (Exception e) {
                        System.err.println("ECCEZIONE caricamento oggetto: " + path);
                    }
                }
            }
        }
    }

    private void caricaImmagineSuView(ImageView imageView, String resourcePath) {
        if (imageView == null || resourcePath == null) return;
        try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
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

    private boolean chiediConfermaPopup(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);

        javafx.scene.control.ButtonType btnSi = new javafx.scene.control.ButtonType("Sì");
        javafx.scene.control.ButtonType btnNo = new javafx.scene.control.ButtonType("No");
        alert.getButtonTypes().setAll(btnSi, btnNo);

        java.util.Optional<javafx.scene.control.ButtonType> ris = alert.showAndWait();
        return ris.isPresent() && ris.get() == btnSi;
    }
}
