package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.Cassero;
import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
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
    @FXML private ImageView imgMazzoCoperto; // ImageView del mazzo a sinistra
    @FXML private ImageView imgPersonaggio;   // ImageView della carta del personaggio selezionato
    @FXML private ImageView imgDadoPersonaggio;
    @FXML private HBox containerDadiMostro;
    @FXML private Label lblHpPersonaggio;
    @FXML private Label lblNomePersonaggio;

    private Personaggio personaggioGiocatore;
    private Cassero cassero;
    private CartaCapitolo cartaCapitoloCorrente;

    @FXML
    public void initialize() {
        // All'avvio della schermata, il mazzo mostra il dorso della carta Intro
        caricaImmagineSuView(imgMazzoCoperto, "/images/carte/dorso_intro.png");
    }

    public void setPersonaggio(Personaggio personaggio) {
        setPersonaggioGiocatore(personaggio);
    }

    public void setPersonaggioGiocatore(Personaggio personaggio) {
        this.personaggioGiocatore = personaggio;

        // 1. Aggiorna Nome, HP e Carta Personaggio
        aggiornaGraficaPersonaggio();

        // 2. Mostra subito la faccia iniziale del dado costruita in PersonaggioFactory (faccia del personaggio)
        if (personaggioGiocatore != null && personaggioGiocatore.getDado() != null) {
            String nomePersonaggioClean = personaggioGiocatore.getNome().toLowerCase().trim();
            String pathDadoIniziale = "/images/dadi/" + nomePersonaggioClean + "_dado.png";
            caricaImmagineSuView(imgDadoPersonaggio, pathDadoIniziale);
        }

        // 3. Inizializza il Cassero se non ancora creato
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
            System.err.println("Info: Errore creazione Cassero default: " + e.getMessage());
        }
    }

    // --- AZIONE 1: PESCA CARTA DAL CASSERO ---
    @FXML
    private void handlePescaCartaCassero(Event event) {
        // Non si avanza se c'è un mostro ancora vivo
        if (cartaCapitoloCorrente instanceof CartaMostro mostro && !mostro.eSconfitto()) {
            mostraAvviso("Combattimento in corso", "Devi sconfiggere il mostro prima di avanzare!");
            return;
        }

        if (cassero == null || !cassero.haCarte()) {
            mostraAvviso("Fine Esplorazione", "Non ci sono più carte nel Cassero!");
            if (imgMazzoCoperto != null) {
                imgMazzoCoperto.setImage(null);
            }
            return;
        }

        // Pesca la carta in cima allo stack
        cartaCapitoloCorrente = cassero.pescaProssimaCarta();

        // Carica la carta scoperta a destra
        if (cartaCapitoloCorrente != null && cartaCapitoloCorrente.getImagePath() != null) {
            caricaImmagineSuView(imgCartaScoperta, cartaCapitoloCorrente.getImagePath());
        }

        // Aggiorna il dorso del mazzo a sinistra (dorso nero capitolo oppure dorso boss)
        aggiornaDorsoMazzo();

        // Se è un mostro o un boss, genera ed eroga i dadi vita
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
        } else {
            svuotaDadiMostro();
        }
    }

    // --- AGGIORNAMENTO DEL DORSO DEL MAZZO ---
    private void aggiornaDorsoMazzo() {
        if (cassero == null || !cassero.haCarte()) {
            if (imgMazzoCoperto != null) imgMazzoCoperto.setImage(null);
            return;
        }

        // Se è rimasta 1 sola carta è il Boss, altrimenti sono le Carte Capitolo
        if (cassero.carteRimanenti() == 1) {
            caricaImmagineSuView(imgMazzoCoperto, "/images/carte/dorso_boss.png");
        } else {
            caricaImmagineSuView(imgMazzoCoperto, "/images/carte/dorso_capitolo.png");
        }
    }

    // --- AZIONE 2: TIRO DADO PERSONAGGIO ---
    @FXML
    private void handleLancioDadoPersonaggio(Event event) {
        if (personaggioGiocatore == null || personaggioGiocatore.getDado() == null) return;

        if (!(cartaCapitoloCorrente instanceof CartaMostro mostro) || mostro.eSconfitto()) {
            return;
        }

        // 1. Lancio del dado
        FacciaDado facciaUscita = personaggioGiocatore.lanciaDado();
        if (facciaUscita.getImagePath() != null) {
            caricaImmagineSuView(imgDadoPersonaggio, facciaUscita.getImagePath());
        }

        List<Simbolo> simboliLanciati = facciaUscita.getSimboli();
        boolean colpoSegnato = false;

        // 2. Controllo colpo a segno
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

        // 3. Risoluzione turno con POP-UP DI FEEDBACK
        if (colpoSegnato) {
            aggiornaGraficaDadiMostro(mostro.getTracciatoSimboli());

            if (mostro.eSconfitto()) {
                mostraAvviso("Vittoria!", "Hai sconfitto il mostro! Ora puoi avanzare.");
                svuotaDadiMostro();
            } else {
                mostraAvviso("Colpo a segno!", "Hai colpito il mostro! Un simbolo è stato eliminato.");
            }
        } else {
            // Tiro fallito: Calcolo danno considerando lo Scudo
            int dannoBase = mostro.getDanno();
            boolean haScudo = facciaUscita.haScudo();

            int dannoEffettivo = dannoBase;
            if (haScudo) {
                dannoEffettivo = Math.max(0, dannoBase - 1);
            }

            personaggioGiocatore.subisciDanno(dannoEffettivo, false, haScudo);
            aggiornaGraficaPersonaggio();

            if (personaggioGiocatore.isSconfitto()) {
                mostraAvviso("Game Over", "Il tuo personaggio è stato sconfitto!");
            } else {
                if (haScudo && dannoEffettivo < dannoBase) {
                    mostraAvviso("Attacco Parato!", "Lo scudo assorbe parte del colpo! Subisci " + dannoEffettivo + " danni invece di " + dannoBase + ".");
                } else {
                    mostraAvviso("Attacco Subito!", "Nessun simbolo corrispondente! Il mostro ti infligge " + dannoEffettivo + " danni.");
                }
            }
        }
    }

    // --- AZIONI RICHIESTE DA FXML ---
    @FXML
    private void handlePescaOggetto(Event event) {
        mostraAvviso("Mazzo Oggetti", "Hai cliccato sul mazzo oggetti!");
    }

    @FXML
    private void handleUsaOggetto(Event event) {
        mostraAvviso("Inventario", "Funzione usa oggetto invocata.");
    }

    @FXML
    private void handleScartaOggetto(Event event) {
        mostraAvviso("Inventario", "Funzione scarta oggetto invocata.");
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
            if (lblHpPersonaggio != null) {
                lblHpPersonaggio.setText("HP: " + personaggioGiocatore.getHP() + " / " + personaggioGiocatore.getSaluteMassima());
            }
            if (lblNomePersonaggio != null) {
                lblNomePersonaggio.setText(personaggioGiocatore.getNome());
            }
            // Carica l'immagine della carta del personaggio (es. /images/carte/cook.png)
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
                System.err.println("Immagine non trovata nel classpath: " + resourcePath);
            }
        } catch (Exception e) {
            System.err.println("Errore caricamento immagine: " + resourcePath);
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