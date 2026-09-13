package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
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
    private final List<Oggetto> oggettiInMano = new ArrayList<>();

    @FXML
    public void initialize() {
        System.out.println("Schermata di Gioco pronta.");

        // Centriamo graficamente gli oggetti dentro l'HBox della mano
        if (containerMano != null) {
            containerMano.setAlignment(Pos.CENTER);
            containerMano.setSpacing(10);
        }

        // Inizializziamo i mazzi backend
        this.mazzoCapitoli = new MazzoCapitoli();
        this.mazzoOggetti = new MazzoOggetti();

        preparaMazzoCassero();
    }

    /**
     * Prepara esattamente: 15 Carte Capitolo casuali + 1 Carta Boss in fondo.
     */
    private void preparaMazzoCassero() {
        List<CartaCapitolo> tutteLeCarte = mazzoCapitoli.getCarteCapitolo();
        Collections.shuffle(tutteLeCarte);

        // Prendiamo 15 carte tra mostri ed eventi
        carteCapitoloEstratte = new ArrayList<>(tutteLeCarte.subList(0, Math.min(15, tutteLeCarte.size())));

        // Scegliamo 1 Boss casuale e lo mettiamo come 16ª carta
        List<CartaBoss> bossDisponibili = mazzoCapitoli.getCarteBoss();
        if (!bossDisponibili.isEmpty()) {
            Collections.shuffle(bossDisponibili);
            carteCapitoloEstratte.add(bossDisponibili.get(0));
        }
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
            mostraAvviso("Mazzo Oggetti", "Non ci sono più carte nel mazzo oggetti!");
            return;
        }

        // Controlliamo quante mani stiamo occupando prima di pescare
        int maniOccupateAttuali = calcolaManiOccupate();
        if (maniOccupateAttuali >= 2) {
            mostraAvviso("Mano Piena", "Hai le mani piene! (Max 2 mani). Scarta o usa un oggetto prima di pescarne un altro.");
            return;
        }

        Oggetto pescato = mazzoOggetti.pesca();
        if (pescato != null && containerMano != null) {
            // Verifica se l'oggetto pescato a due mani supera il limite
            int ingombroPescato = getIngombroOggetto(pescato);
            if (maniOccupateAttuali + ingombroPescato > 2) {
                mostraAvviso("Ingombro Oggetto", "Questo oggetto richiede " + ingombroPescato + " mani, ma hai solo " + (2 - maniOccupateAttuali) + " mano libera!");
                // Rimettiamo la carta in cima al mazzo o la gestiamo
                return;
            }

            oggettiInMano.add(pescato);

            ImageView vistaCartaOggetto = new ImageView();
            caricaImmagineSuView(vistaCartaOggetto, pescato.getImagePath());

            vistaCartaOggetto.setFitWidth(80);
            vistaCartaOggetto.setFitHeight(120);
            vistaCartaOggetto.setPreserveRatio(true);

            // Click sull'oggetto per scartarlo/usarlo
            vistaCartaOggetto.setOnMouseClicked(e -> handleUsaScartaOggetto(vistaCartaOggetto, pescato));

            containerMano.getChildren().add(vistaCartaOggetto);
        }
    }

    private void handleUsaScartaOggetto(ImageView cartaView, Oggetto oggetto) {
        boolean conferma = mostraConfermaScelta("Usa/Scarta Oggetto", "Vuoi scartare o usare l'oggetto: " + oggetto.getNome() + "?");
        if (conferma) {
            oggettiInMano.remove(oggetto);
            containerMano.getChildren().remove(cartaView);
            System.out.println("Oggetto rimosso dalla mano: " + oggetto.getNome());
        }
    }

    /**
     * Calcola il numero totale di mani attualmente occupate dagli oggetti equipaggiati.
     */
    private int calcolaManiOccupate() {
        int totaleMani = 0;
        for (Oggetto obj : oggettiInMano) {
            totaleMani += getIngombroOggetto(obj);
        }
        return totaleMani;
    }

    /**
     * Riconosce se un oggetto occupa 1 o 2 mani (es. Armi a 2 mani vs Armi a 1 mano/Pozioni/Cibo).
     */
    private int getIngombroOggetto(Oggetto oggetto) {
        // Se nel tuo modello l'oggetto ha un metodo o attributo getMani() o isDueMani()
        // Ad esempio per l'Ascia bipenne o Scudi a 2 mani:
        String nome = oggetto.getNome().toLowerCase();
        if (nome.contains("ascia bipenne") || nome.contains("due mani")) {
            return 2;
        }
        return 1;
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

    // --- POP-UP UTILS ---
    private void mostraAvviso(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
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
