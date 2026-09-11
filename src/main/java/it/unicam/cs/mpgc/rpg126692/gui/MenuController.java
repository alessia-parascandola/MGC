package it.unicam.cs.mpgc.rpg126692.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnAvanti;

    @FXML
    private void handleAvantiClick(ActionEvent event) {
        try {
            // Carica la seconda schermata FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SelezionePersonaggio.fxml"));
            Scene scene = new Scene(loader.load());

            // Recupera lo Stage (la finestra) attuale dall'evento del click
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Imposta la nuova scena sulla finestra corrente
            stage.setScene(scene);
            stage.sizeToScene(); // Ridimensiona la finestra esattamente alla grandezza della nuova schermata
            stage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento di SelezionePersonaggio.fxml!");
            e.printStackTrace();
        }
    }
}