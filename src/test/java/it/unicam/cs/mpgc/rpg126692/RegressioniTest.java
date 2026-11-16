package it.unicam.cs.mpgc.rpg126692;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Path;
import java.util.List;
import it.unicam.cs.mpgc.rpg126692.personaggi.PersonaggioFactory;
import it.unicam.cs.mpgc.rpg126692.dadi.*;
import it.unicam.cs.mpgc.rpg126692.carte.*;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.carte.Eventi.EventoIntriganteStraniero;
import it.unicam.cs.mpgc.rpg126692.oggetti.*;
import static org.junit.jupiter.api.Assertions.*;

class RegressioniTest {
    @TempDir Path directory;
    static class Scelte implements Interazione {
        final java.util.ArrayDeque<String> risposte;
        final StringBuilder testo = new StringBuilder();
        Scelte(String... risposte) { this.risposte = new java.util.ArrayDeque<>(List.of(risposte)); }
        public String nextLine() { return risposte.remove(); }
        public void print(Object valore) { testo.append(valore); }
    }
    @Test void intuizioneCambiaSoloIlSimboloSceltoEConsumaLaPozione() {
        var p = PersonaggioFactory.creaCook();
        var mostro = new CartaMostro("Test", null, List.of(), 1);
        mostro.getTracciatoSimboli().addAll(List.of(Simbolo.POSSANZA, Simbolo.ASTUZIA));
        var pozione = new it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneIntuizione("Elisir", "", null);
        p.getInventario().aggiungi(pozione);
        var scelte = new Scelte("errato", "9", "2", "9", "3");
        Interazioni.esegui(scelte, () -> pozione.usa(p, mostro));
        assertEquals(List.of(Simbolo.POSSANZA, Simbolo.SAPIENZA), mostro.getTracciatoSimboli());
        assertFalse(p.getInventario().getOggetti().contains(pozione));
        assertTrue(scelte.risposte.isEmpty());
    }
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.ValueSource(strings = {"0", "1,0"})
    void intuizioneAnnullataNonModificaIlMostroENonSiConsuma(String input) {
        var p = PersonaggioFactory.creaCook();
        var mostro = new CartaMostro("Test", null, List.of(), 1);
        mostro.getTracciatoSimboli().add(Simbolo.POSSANZA);
        var pozione = new it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneIntuizione("Elisir", "", null);
        p.getInventario().aggiungi(pozione);
        Interazioni.esegui(new Scelte(input.split(",")), () -> pozione.usa(p, mostro));
        assertEquals(List.of(Simbolo.POSSANZA), mostro.getTracciatoSimboli());
        assertTrue(p.getInventario().getOggetti().contains(pozione));
    }
    @Test void intuizioneSenzaMostroRimaneNellInventario() {
        var p = PersonaggioFactory.creaCook();
        var pozione = new it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneIntuizione("Elisir", "", null);
        p.getInventario().aggiungi(pozione);
        Interazioni.esegui(new Scelte(), () -> {
            pozione.usa(p, null);
            pozione.usa(p, new CartaMostro("Sconfitto", null, List.of(), 1));
        });
        assertTrue(p.getInventario().getOggetti().contains(pozione));
    }
    @Test void reliquiaMostraEntrambiISimboliPrimaDellaScelta() {
        var p = PersonaggioFactory.creaCook();
        p.getInventario().aggiungi(new it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.VersettiCurativi("Versetti", "", null));
        var doppio = new FacciaDado(List.of(Simbolo.SAPIENZA, Simbolo.SAPIENZA), true, null);
        var scelte = new Scelte("2");
        Interazioni.esegui(scelte, () -> GestorePartita.gestisciEffettiReliquie(p, doppio, scelte));
        assertTrue(scelte.testo.toString().contains("[SAPIENZA, SAPIENZA] (DOPPIO)"));
    }
    @Test void provaDiBarattoMostraIlDoppioOttenuto() {
        var doppio = new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true, null);
        var p = new it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio(35, "Test", 1, 1, 1,
                new DadoPersonaggio(java.util.Collections.nCopies(6, doppio)), null);
        var scelte = new Scelte("2");
        Interazioni.esegui(scelte, () -> new it.unicam.cs.mpgc.rpg126692.carte.Eventi.EventoBarattoUomoNascosto().esegui(p, new MazzoOggetti()));
        assertTrue(scelte.testo.toString().contains("[ASTUZIA, ASTUZIA] (DOPPIO)"));
    }
    @Test void storicoRiaperto() throws Exception {
        Path file = directory.resolve("dati/partite.tsv");
        assertTrue(new StoricoPartite(file).leggi().isEmpty());
        new StoricoPartite(file).salva("Cook", "VITTORIA");
        assertTrue(new StoricoPartite(file).leggi().getFirst().contains("Cook\tVITTORIA"));
    }
    @Test void risultatoNonModificaFacce() {
        FacciaDado faccia = new FacciaDado(List.of(Simbolo.POSSANZA), false, null);
        DadoPersonaggio dado = new DadoPersonaggio(java.util.Collections.nCopies(6, faccia));
        dado.lancia().getSimboli().clear();
        assertEquals(List.of(Simbolo.POSSANZA), dado.lancia().getSimboli());
    }
    @Test void rilancioArmaPermetteDiConservareIlPrimoRisultato() {
        verificaRilancioArma(new Scelte("1", "1"), true, 2);
    }
    @Test void rilancioArmaPermetteDiScegliereIlSecondoRisultato() {
        verificaRilancioArma(new Scelte("1", "errato", "2"), false, 2);
    }
    @Test void rilancioArmaRifiutatoMantieneIlPrimoRisultato() {
        verificaRilancioArma(new Scelte("2"), true, 1);
    }
    private void verificaRilancioArma(Scelte scelte, boolean primo, int lanciAttesi) {
        var faccia1 = new FacciaDado(List.of(Simbolo.POSSANZA, Simbolo.POSSANZA), true, "primo.png");
        var faccia2 = new FacciaDado(List.of(Simbolo.ASTUZIA), false, "secondo.png");
        int[] lanci = {0};
        var dado = new DadoPersonaggio(java.util.Collections.nCopies(6, faccia1)) {
            @Override public FacciaDado lancia() { return lanci[0]++ == 0 ? faccia1 : faccia2; }
        };
        var p = new it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio(35, "Test", 1, 1, 1, dado, null);
        p.getInventario().aggiungi(new Arma("Randello", "", null, 1, Simbolo.POSSANZA, false));
        Interazioni.esegui(scelte, () -> {
            var risultato = GestorePartita.gestisciLancioConArma(p);
            assertSame(primo ? faccia1 : faccia2, risultato);
            assertSame(risultato, p.getUltimoRisultatoDado());
            assertEquals(primo, risultato.haScudo());
        });
        assertEquals(lanciAttesi, lanci[0]);
        assertTrue(scelte.risposte.isEmpty());
    }
    @Test void ordineCassero() {
        Cassero cassero = new Cassero(new MazzoCapitoli());
        assertEquals(17, cassero.carteRimanenti());
        assertInstanceOf(CartaIntro.class, cassero.pescaProssimaCarta());
        while(cassero.carteRimanenti() > 1) assertFalse(cassero.pescaProssimaCarta() instanceof CartaBoss);
        assertInstanceOf(CartaBoss.class, cassero.pescaProssimaCarta());
        assertNull(cassero.pescaProssimaCarta());
    }
    @Test void sceltaEventoCondivisa() {
        var giocatore = PersonaggioFactory.creaCook();
        Interazioni.esegui(new Scelte("1"), () -> new EventoIntriganteStraniero().esegui(giocatore, new MazzoOggetti()));
        assertEquals(1, giocatore.getInventario().getOggetti().size());
    }
    @Test void inventarioLimitaMani() {
        var inventario = new Inventario();
        var arma = new Arma("Arma", "", null, 2, Simbolo.POSSANZA, false);
        assertTrue(inventario.aggiungi(arma));
        assertFalse(inventario.aggiungi(arma));
        assertEquals(arma, inventario.scarta(0));
    }
    @Test void contestoRipristinatoDopoErrore() {
        var precedente = Interazioni.corrente();
        assertThrows(IllegalStateException.class, () -> Interazioni.esegui(new Scelte(), () -> { throw new IllegalStateException(); }));
        assertSame(precedente, Interazioni.corrente());
    }
    @Test void scudoNonAnnullaDannoSingolo() {
        var p = PersonaggioFactory.creaCook();
        p.getInventario().aggiungi(new Scudo("Scudo", "", null, 1));
        p.subisciDanno(1, false, false);
        assertEquals(34, p.getHP());
    }
    @Test void combattimentoRimuoveEntrambiISimboli() {
        FacciaDado doppio = new FacciaDado(List.of(Simbolo.POSSANZA, Simbolo.POSSANZA), true, null);
        var p = new it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio(35, "Test", 1, 1, 1,
                new DadoPersonaggio(java.util.Collections.nCopies(6, doppio)), null);
        var mostro = new CartaMostro("Test", null, List.of(Simbolo.POSSANZA), 3);
        var dadoCapitolo = new DadoCapitolo() { @Override public Simbolo lancia() { return Simbolo.POSSANZA; } };
        Interazioni.esegui(new Scelte(""), () -> new GestoreCombattimento().combatti(p, mostro, dadoCapitolo));
        assertTrue(mostro.eSconfitto());
        assertEquals(35, p.getHP());
    }
    @org.junit.jupiter.params.ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource({
            "29, 1, 30, true",
            "34, 3, 35, true",
            "35, 1, 35, false"
    })
    void ciboRispettaIlMassimoDelPersonaggio(int hpIniziali, int cura, int hpAttesi, boolean consumato) {
        var p = PersonaggioFactory.creaSmith();
        p.subisciDanno(p.getSaluteMassima() - hpIniziali, false, false);
        var cibo = new Cibo("Mela parzialmente marcia", "Cibo", null, cura);
        assertTrue(p.getInventario().aggiungi(cibo));
        Interazioni.esegui(new Scelte(), () -> cibo.usa(p, null));
        assertEquals(hpAttesi, p.getHP());
        assertEquals(!consumato, p.getInventario().getOggetti().contains(cibo));
    }

    @Test void saluteLimitata() {
        var p = PersonaggioFactory.creaCook();
        p.subisciDanno(100, false, false);
        assertTrue(p.isSconfitto());
        p.cura(100);
        assertEquals(p.getSaluteMassima(), p.getHP());
    }
}
