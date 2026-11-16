package it.unicam.cs.mpgc.rpg126692.gui;

import it.unicam.cs.mpgc.rpg126692.Interazione;
import javafx.scene.control.*;

/** Raccoglie il racconto fino alla prossima decisione, senza leggere System.in. */
final class InterazioneGrafica implements Interazione {
    private final StringBuilder testo = new StringBuilder();
    private final Runnable aggiorna;
    InterazioneGrafica(Runnable aggiorna) { this.aggiorna = aggiorna; }
    public void print(Object valore) { testo.append(valore); }
    public String nextLine() {
        aggiorna.run();
        TextInputDialog dialogo = new TextInputDialog("");
        dialogo.setTitle("Fuga dal Cassero");
        dialogo.setHeaderText("Inserisci la scelta indicata; lascia vuoto per INVIO");
        TextArea racconto = new TextArea(testo.toString());
        racconto.setEditable(false);
        racconto.setWrapText(true);
        racconto.setPrefSize(620, 380);
        dialogo.getDialogPane().setExpandableContent(racconto);
        dialogo.getDialogPane().setExpanded(true);
        dialogo.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        testo.setLength(0);
        return dialogo.showAndWait().orElse("");
    }
    void mostraEsito() {
        aggiorna.run();
        if (testo.isEmpty()) return;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Esito");
        TextArea area = new TextArea(testo.toString());
        area.setEditable(false);
        area.setWrapText(true);
        area.setPrefSize(620, 380);
        alert.getDialogPane().setContent(area);
        testo.setLength(0);
        alert.showAndWait();
    }
}
