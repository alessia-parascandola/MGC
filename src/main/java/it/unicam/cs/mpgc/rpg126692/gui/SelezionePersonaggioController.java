package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.personaggi.PersonaggioFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SelezionePersonaggioController {

    @FXML
    private Button btnContinua;

    private Personaggio personaggioSelezionato;

    @FXML
    public void initialize() {
        // Il pulsante continua parte disabilitato finché non si sceglie una carta
        if (btnContinua != null) {
            btnContinua.setDisable(true);
        }
    }

    @FXML
    private void handleSelezionePersonaggio(ActionEvent event) {
        Node sorgente = (Node) event.getSource();
        String idPulsante = sorgente.getId();

        if (idPulsante == null) return;

        // Assegniamo il personaggio temporaneo in base alla carta cliccata
        switch (idPulsante) {
            case "btnCook" -> personaggioSelezionato = PersonaggioFactory.creaCook();
            case "btnAbbot" -> personaggioSelezionato = PersonaggioFactory.creaAbbot();
            case "btnTailor" -> personaggioSelezionato = PersonaggioFactory.creaTailor();
            case "btnMiller" -> personaggioSelezionato = PersonaggioFactory.creaMiller();
            case "btnTanner" -> personaggioSelezionato = PersonaggioFactory.creaTanner();
            case "btnSmith" -> personaggioSelezionato = PersonaggioFactory.creaSmith();
            default -> {
                return;
            }
        }

        System.out.println("Scelta temporanea: " + personaggioSelezionato.getNome());

        // Abilitiamo il pulsante CONTINUA
        if (btnContinua != null) {
            btnContinua.setDisable(false);
        }
    }

    @FXML
    private void handleContinuaClick(ActionEvent event) {
        if (personaggioSelezionato == null) return;

        System.out.println("SCELTA CONFERMATA: " + personaggioSelezionato.getNome());

        try {
            // 1. Carica il file FXML della schermata di gioco
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SchermataGioco.fxml"));
            Parent root = loader.load();

            // 2. Recupera il GiocoController e gli passa il personaggio selezionato
            GiocoController giocoController = loader.getController();
            if (giocoController != null) {
                giocoController.setPersonaggio(personaggioSelezionato);
            }

            // 3. Effettua lo switch di scena sulla stessa finestra (Stage)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.err.println("Errore durante il caricamento di SchermataGioco.fxml:");
            e.printStackTrace();
        }
    }
}