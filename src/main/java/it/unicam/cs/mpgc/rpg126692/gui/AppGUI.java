package it.unicam.cs.mpgc.rpg126692.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AppGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SchermataIntro.fxml"));
            Scene scene = new Scene(loader.load());

            primaryStage.setTitle("Fuga dal Cassero - RPG Game");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Errore durante il caricamento dell'interfaccia FXML!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}