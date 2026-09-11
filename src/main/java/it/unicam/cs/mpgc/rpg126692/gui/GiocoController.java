package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
    private int indiceCapitolo = 0;

    @FXML
    public void initialize() {
        System.out.println("Schermata di Gioco pronta.");
    }

    /**
     * Riceve il personaggio ed elabora la grafica iniziale del giocatore
     */
    public void setPersonaggio(Personaggio personaggio) {
        if (personaggio == null) return;

        this.personaggioGiocatore = personaggio;
        String nomeEroe = personaggioGiocatore.getNome().toLowerCase();

        // 1. Aggiorna HP iniziale (es. "HP: 35 / 35")
        // Se nel tuo Personaggio il metodo per gli HP max ha un altro nome, sostituisci getHP()
        aggiornaHP(personaggioGiocatore.getHP(), personaggioGiocatore.getHP());

        // 2. Carica la carta del personaggio
        if (imgPersonaggio != null) {
            String percorsoCarta = "/images/personaggi/" + nomeEroe + ".png";
            try {
                var stream = getClass().getResourceAsStream(percorsoCarta);
                if (stream != null) {
                    imgPersonaggio.setImage(new Image(stream));
                } else {
                    System.err.println("ERRORE: File carta non trovato in " + percorsoCarta);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 3. Carica l'immagine del Dado del personaggio
        if (imgDadoPersonaggio != null) {
            String percorsoDado = "/images/dadi/" + nomeEroe + "_dado.png";
            try {
                var streamDado = getClass().getResourceAsStream(percorsoDado);
                if (streamDado != null) {
                    imgDadoPersonaggio.setImage(new Image(streamDado));
                } else {
                    System.err.println("ERRORE: File dado non trovato in " + percorsoDado);
                }
            } catch (Exception e) {
                System.err.println("Errore caricamento dado per: " + nomeEroe);
            }
        }

        // 4. Carica il dorso iniziale del mazzo
        aggiornaDorsoMazzo();
    }

    /**
     * Gestisce il click sul Mazzo Cassero coperto per scoprire una nuova carta
     */
    @FXML
    private void handlePescaCartaCassero(Event event) {
        if (indiceCapitolo >= 17) {
            System.out.println("Esplorazione completata!");
            return;
        }

        indiceCapitolo++;
        System.out.println("Capitolo attuale: " + indiceCapitolo);

        // TODO: Carica l'immagine della carta capitolo estratta su 'imgCartaScoperta'

        // Aggiorna il dorso per la prossima pesca (Intro -> Nero -> Boss)
        aggiornaDorsoMazzo();
    }

    /**
     * Gestisce il lancio del dado del personaggio
     */
    @FXML
    private void handleLancioDadoPersonaggio(Event event) {
        if (personaggioGiocatore == null) return;

        System.out.println("Tiro del dado per " + personaggioGiocatore.getNome());
        // TODO: Integrare il lancio reale tramite il backend
    }

    private void aggiornaDorsoMazzo() {
        if (imgMazzoCassero == null) return;

        String percorsoDorso;
        if (indiceCapitolo == 0) {
            percorsoDorso = "/images/dorsi/dorso_intro.png";
        } else if (indiceCapitolo < 16) {
            percorsoDorso = "/images/dorsi/dorso_nero.png";
        } else {
            percorsoDorso = "/images/dorsi/dorso_boss.png";
        }

        try {
            var streamDorso = getClass().getResourceAsStream(percorsoDorso);
            if (streamDorso != null) {
                imgMazzoCassero.setImage(new Image(streamDorso));
            }
        } catch (Exception e) {
            System.err.println("Errore caricamento dorso mazzo: " + percorsoDorso);
        }
    }

    /**
     * Metodo helper per aggiornare la Label degli HP in tempo reale
     */
    public void aggiornaHP(int hpAttuali, int hpMassimi) {
        if (lblHP != null) {
            lblHP.setText("HP: " + hpAttuali + " / " + hpMassimi);
        }
    }
}