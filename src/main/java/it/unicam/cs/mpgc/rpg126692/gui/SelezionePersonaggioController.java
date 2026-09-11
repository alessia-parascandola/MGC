package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.personaggi.PersonaggioFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SelezionePersonaggioController {

    private Personaggio personaggioSelezionato;

    @FXML
    public void initialize() {
        System.out.println("Schermata Selezione Personaggio caricata con successo!");
    }

    @FXML
    private void handleSelezionePersonaggio(ActionEvent event) {
        // Recuperiamo il pulsante che è stato cliccato
        Button btnCliccato = (Button) event.getSource();
        String idPulsante = btnCliccato.getId();

        // Creiamo il personaggio corrispondente in base all'fx:id del pulsante
        switch (idPulsante) {
            case "btnCook":
                personaggioSelezionato = PersonaggioFactory.creaCook();
                break;
            case "btnAbbot":
                personaggioSelezionato = PersonaggioFactory.creaAbbot();
                break;
            case "btnTailor":
                personaggioSelezionato = PersonaggioFactory.creaTailor();
                break;
            case "btnMiller":
                personaggioSelezionato = PersonaggioFactory.creaMiller();
                break;
            case "btnTanner":
                personaggioSelezionato = PersonaggioFactory.creaTanner();
                break;
            case "btnSmith":
                personaggioSelezionato = PersonaggioFactory.creaSmith();
                break;
            default:
                System.err.println("Pulsante non riconosciuto: " + idPulsante);
                return;
        }

        System.out.println("Personaggio selezionato: " + personaggioSelezionato.getNome());

        // TODO: Passare il personaggioSelezionato alla schermata di gioco/mappa e cambiare scena
    }
}