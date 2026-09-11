package it.unicam.cs.mpgc.rpg126692.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML
    private Button btnAvanti;

    @FXML
    private void handleAvantiClick() {
        System.out.println("Pulsante 'Avanti' cliccato! Il controller funziona!");
        // Qui metteremo il codice per passare alla Selezione Personaggio
    }
}