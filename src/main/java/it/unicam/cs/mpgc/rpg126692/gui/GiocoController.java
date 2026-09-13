package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Collections;
import java.util.Optional;

public class GiocoController {

    // --- COLLEGAMENTI SCENE BUILDER ---
    @FXML private ImageView imgPersonaggio;
    @FXML private Label lblHP;
    @FXML private HBox containerMano;
    @FXML private ImageView imgDadoPersonaggio; // Sostituito il Button con ImageView
    @FXML private ImageView imgMazzoCassero;
    @FXML private ImageView imgCartaScoperta;
    @FXML private ImageView imgMazzoOggetti;

    // --- LOGICA DI GIOCO ---
    private Personaggio personaggioGiocatore;
    private MazzoCapitoli mazzoCapitoli;
    private MazzoOggetti mazzoOggetti;

    private CartaCapitolo cartaCapitoloCorrente;
    private int indiceCapitolo = 0;
    private List<CartaCapitolo> carteCapitoloEstratte; // Lista con la sequenza della partita

    @FXML
    public void initialize() {
        System.out.println("Schermata di Gioco pronta.");

        // Inizializziamo i mazzi backend
        this.mazzoCapitoli = new MazzoCapitoli();
        this.mazzoOggetti = new MazzoOggetti();

        // Prendiamo le carte dal mazzo
        this.carteCapitoloEstratte = mazzoCapitoli.getCarteCapitolo();

        // MESCOLIAMO LE CARTE CAPITOLO CASUALMENTE!
        java.util.Collections.shuffle(this.carteCapitoloEstratte);
    }

    /**
     * Riceve il personaggio ed elabora la grafica iniziale del giocatore
     */
    public void setPersonaggio(Personaggio personaggio) {
        if (personaggio == null) return;

        this.personaggioGiocatore = personaggio;
        String nomeEroe = personaggioGiocatore.getNome().toLowerCase();

        // 1. Aggiorna HP iniziale (es. "HP: 35 / 35")
        aggiornaHP(personaggioGiocatore.getHP(), personaggioGiocatore.getHP());

        // 2. Carica la carta del personaggio
        if (imgPersonaggio != null) {
            String percorsoCarta = "/images/personaggi/" + nomeEroe + ".png";
            caricaImmagineSuView(imgPersonaggio, percorsoCarta);
        }

        // 3. Carica l'immagine del Dado del personaggio
        if (imgDadoPersonaggio != null) {
            String percorsoDado = "/images/dadi/" + nomeEroe + "_dado.png";
            caricaImmagineSuView(imgDadoPersonaggio, percorsoDado);
        }

        // 4. Carica il dorso iniziale del mazzo
        aggiornaDorsoMazzo();
    }

    /**
     * Gestisce il click sul Mazzo Cassero coperto per scoprire una nuova carta
     */
    @FXML
    private void handlePescaCartaCassero(Event event) {
        // Se si clicca all'inizio (capitolo 0), mostriamo la carta Intro
        if (indiceCapitolo == 0) {
            System.out.println("Mostro Carta Intro");
            caricaImmagineSuView(imgCartaScoperta, mazzoCapitoli.getCartaIntro().getImagePath());
            indiceCapitolo++;
            aggiornaDorsoMazzo();
            return;
        }

        // Se l'esplorazione è terminata
        if (indiceCapitolo > carteCapitoloEstratte.size()) {
            System.out.println("Esplorazione completata!");
            return;
        }

        // Pesca la carta capitolo corrente dalla lista backend
        cartaCapitoloCorrente = carteCapitoloEstratte.get(indiceCapitolo - 1);
        System.out.println("Capitolo " + indiceCapitolo + " mostrato.");

        // Carica l'immagine dinamica della carta pescata (usando il suo getPercorsoImmagine)
        if (cartaCapitoloCorrente != null) {
            caricaImmagineSuView(imgCartaScoperta, cartaCapitoloCorrente.getImagePath());
        }

        indiceCapitolo++;
        aggiornaDorsoMazzo();
    }

    /**
     * Gestisce il lancio del dado del personaggio
     */
    @FXML
    private void handleLancioDadoPersonaggio(Event event) {
        if (personaggioGiocatore == null) return;

        System.out.println("Tiro del dado per " + personaggioGiocatore.getNome());

        // Esegue il lancio del dado reale tramite il backend del personaggio
        // Assicurati che nel tuo Personaggio ci sia il getter per il suo DadoPersonaggio
        if (personaggioGiocatore.getDado() != null) {
            FacciaDado facciaUscita = personaggioGiocatore.getDado().lancia();

            // Aggiorna l'ImageView del dado con la faccia risultante!
            caricaImmagineSuView(imgDadoPersonaggio, facciaUscita.getImagePath());
        }
    }

    /**
     * Gestisce il click sul Mazzo Oggetti per pescare una carta oggetto nella mano
     */
    @FXML
    private void handlePescaOggetto(Event event) {
        if (mazzoOggetti == null || mazzoOggetti.isVuoto()) {
            System.out.println("Mazzo oggetti vuoto!");
            return;
        }

        Oggetto pescato = mazzoOggetti.pesca();
        if (pescato != null && containerMano != null) {
            ImageView vistaCartaOggetto = new ImageView();
            caricaImmagineSuView(vistaCartaOggetto, pescato.getImagePath());

            // Dimensioni ideali per la carta dentro l'HBox della mano
            vistaCartaOggetto.setFitWidth(80);
            vistaCartaOggetto.setFitHeight(120);
            vistaCartaOggetto.setPreserveRatio(true);

            // Interazione: click sull'oggetto in mano per usarlo o scartarlo
            vistaCartaOggetto.setOnMouseClicked(e -> handleUsaScartaOggetto(vistaCartaOggetto, pescato));

            // Aggiunge la carta alla mano del giocatore a schermo
            containerMano.getChildren().add(vistaCartaOggetto);
        }
    }

    private void handleUsaScartaOggetto(ImageView cartaView, Oggetto oggetto) {
        boolean conferma = mostraConfermaScelta("Usa/Scarta Oggetto", "Vuoi scartare o usare l'oggetto: " + oggetto.getNome() + "?");
        if (conferma) {
            System.out.println("Oggetto rimosso dalla mano: " + oggetto.getNome());
            containerMano.getChildren().remove(cartaView);
        }
    }

    private void aggiornaDorsoMazzo() {
        if (imgMazzoCassero == null) return;

        String percorsoDorso;
        if (indiceCapitolo == 0) {
            percorsoDorso = "/images/retro_cartaIntro.png";
        } else if (indiceCapitolo < 16) {
            percorsoDorso = "/images/dorso_carteCapitolo.png";
        } else {
            percorsoDorso = "/images/dorso_boss.jpg";
        }

        caricaImmagineSuView(imgMazzoCassero, percorsoDorso);
    }

    /**
     * Metodo helper per aggiornare la Label degli HP in tempo reale
     */
    public void aggiornaHP(int hpAttuali, int hpMassimi) {
        if (lblHP != null) {
            lblHP.setText("HP: " + hpAttuali + " / " + hpMassimi);
        }

        if (hpAttuali <= 0) {
            mostraSchermataFineGioco("GAME OVER", "I tuoi punti vita sono scesi a 0. Sei stato sconfitto!");
        }
    }

    // --- FINESTRE DI DIALOGO / POP-UP PER SCELTE E FINE GIOCO ---

    public String mostraFinestraSceltaOpzioni(String titolo, String messaggio, List<String> opzioni) {
        if (opzioni == null || opzioni.isEmpty()) return null;
        ChoiceDialog<String> dialog = new ChoiceDialog<>(opzioni.get(0), opzioni);
        dialog.setTitle(titolo);
        dialog.setHeaderText(null);
        dialog.setContentText(messaggio);
        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public boolean mostraConfermaScelta(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK;
    }

    private void mostraSchermataFineGioco(String titolo, String messaggio) {
        Alert alert = new Alert(personaggioGiocatore != null && personaggioGiocatore.getHP() <= 0 ?
                Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(titolo);
        alert.setContentText(messaggio);
        alert.showAndWait();

        System.exit(0);
    }

    /**
     * Helper sicuro per caricare un'immagine in una ImageView senza duplicare try-catch
     */
    private void caricaImmagineSuView(ImageView view, String percorso) {
        if (view == null || percorso == null) return;
        try {
            var stream = getClass().getResourceAsStream(percorso);
            if (stream != null) {
                view.setImage(new Image(stream));
            } else {
                System.err.println("ERRORE: Immagine non trovata in: " + percorso);
            }
        } catch (Exception e) {
            System.err.println("Errore caricamento immagine: " + percorso);
        }
    }
}
