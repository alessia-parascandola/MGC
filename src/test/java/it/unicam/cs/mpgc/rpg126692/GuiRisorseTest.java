package it.unicam.cs.mpgc.rpg126692;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import org.junit.jupiter.api.Test;
import java.util.concurrent.*;
import static org.junit.jupiter.api.Assertions.*;

@org.junit.jupiter.api.Tag("gui")
class GuiRisorseTest {
    private void verificaFaccia(javafx.scene.image.ImageView vista, String path) {
        var attesa = new javafx.scene.image.Image(getClass().getResource(path).toExternalForm());
        var effettiva = vista.getImage();
        assertNotNull(effettiva);
        assertFalse(effettiva.isError());
        assertEquals(attesa.getWidth(), effettiva.getWidth());
        assertEquals(attesa.getHeight(), effettiva.getHeight());
        int larghezza = (int) attesa.getWidth(), altezza = (int) attesa.getHeight();
        int[] pixelAttesi = new int[larghezza * altezza], pixelEffettivi = new int[larghezza * altezza];
        var formato = javafx.scene.image.PixelFormat.getIntArgbInstance();
        attesa.getPixelReader().getPixels(0, 0, larghezza, altezza, formato, pixelAttesi, 0, larghezza);
        effettiva.getPixelReader().getPixels(0, 0, larghezza, altezza, formato, pixelEffettivi, 0, larghezza);
        assertArrayEquals(pixelAttesi, pixelEffettivi);
    }

    @Test void caricaTutteLeSchermate() throws Exception {
        var completato = new CompletableFuture<Void>();
        Platform.startup(() -> {
            try {
                for (String nome : new String[]{"SchermataIntro", "SelezionePersonaggio", "SchermataGioco"}) {
                    var url = getClass().getResource("/fxml/" + nome + ".fxml");
                    assertNotNull(url);
                    var loader = new FXMLLoader(url);
                    assertNotNull(loader.load());
                    if (nome.equals("SchermataGioco")) {
                        var controller = (it.unicam.cs.mpgc.rpg126692.gui.GiocoController) loader.getController();
                        var facce = it.unicam.cs.mpgc.rpg126692.personaggi.PersonaggioFactory.creaCook().getDado().getFacce();
                        var indiceLancio = new java.util.concurrent.atomic.AtomicInteger();
                        var dado = new it.unicam.cs.mpgc.rpg126692.dadi.DadoPersonaggio(facce) {
                            @Override public it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado lancia() {
                                return facce.get(indiceLancio.getAndIncrement() == 0 ? 0 : 5);
                            }
                        };
                        var p = new it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio(35, "Cook", 4, 3, 1, dado, null);
                        controller.setPersonaggioGiocatore(p);
                        var vistaDado = (javafx.scene.image.ImageView) loader.getNamespace().get("imgDadoPersonaggio");
                        var aggiornaStato = controller.getClass().getDeclaredMethod("aggiornaStato");
                        aggiornaStato.setAccessible(true);
                        var primaImmagine = vistaDado.getImage();
                        var primo = p.lanciaDado();
                        aggiornaStato.invoke(controller);
                        assertNotSame(primaImmagine, vistaDado.getImage());
                        verificaFaccia(vistaDado, primo.getImagePath());
                        var secondo = p.lanciaDado();
                        aggiornaStato.invoke(controller);
                        verificaFaccia(vistaDado, secondo.getImagePath());
                        p.selezionaRisultatoDado(primo);
                        aggiornaStato.invoke(controller);
                        verificaFaccia(vistaDado, primo.getImagePath());
                        var cassero = new Cassero(new it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli());
                        controller.setCassero(cassero);
                        var contatore = (javafx.scene.control.Label) loader.getNamespace().get("lblStanze");
                        assertEquals("Stanza: 0/17", contatore.getText());
                        var aggiorna = controller.getClass().getDeclaredMethod("aggiornaContatoreStanze");
                        aggiorna.setAccessible(true);
                        for (int stanza = 1; stanza <= 17; stanza++) {
                            cassero.pescaProssimaCarta();
                            aggiorna.invoke(controller);
                            assertEquals("Stanza: " + stanza + "/17", contatore.getText());
                        }
                    }
                }
                completato.complete(null);
            } catch (Throwable errore) { completato.completeExceptionally(errore); }
        });
        try { completato.get(30, TimeUnit.SECONDS); }
        finally { Platform.exit(); }
    }
}
