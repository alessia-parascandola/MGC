package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoPersonaggio;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.Arma;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneEvasione;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneFortuna;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneSimbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.VersettiCurativi;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.ArrayList;
import java.util.List;

public class TestOggetti {

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("      AVVIO COLLAUDO RAPIDO OGGETTI           ");
        System.out.println("==============================================");

        // 1. Creazione Personaggio di prova
        DadoPersonaggio dado = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false)
        ));
        Personaggio p = new Personaggio(18, "Eroe di Test", 3, 3, 3, dado);

        // 2. EQUIPAGGIAMENTO MANUALE PER IL TEST
        System.out.println("\n--- Mettiamo gli oggetti da testare nell'inventario ---");

        // Aggiungi un'Arma che fa ritirare se esce ad esempio FORZA (o usa il tuo costruttore di Arma)
        // NOTA: Adatta le chiamate ai costruttori in base a come sono definiti nel tuo progetto!
        Arma spadaTest = new Arma("Spada di Test", "Lancia di nuovo se esce FORZA", 1, Simbolo.FORZA, true);
        p.getInventario().aggiungi(spadaTest);

        // Aggiungi una Pozione Evasione
        PozioneEvasione pozioneEvasione = new PozioneEvasione("Evasione effervescente", "Annulla il danno");
        p.getInventario().aggiungi(pozioneEvasione);

        // Aggiungi una Pozione Fortuna
        PozioneFortuna pozioneFortuna = new PozioneFortuna("Fortuna Liquida", "Rilancia il dado");
        p.getInventario().aggiungi(pozioneFortuna);

        // Aggiungi una Pozione Simbolo
        PozioneSimbolo pozioneForza = new PozioneSimbolo("Pozione della Forza", "Dà 1 simbolo Forza", Simbolo.FORZA);
        p.getInventario().aggiungi(pozioneForza);

        // Aggiungi una Reliquia (es. Versetti Curativi)
        VersettiCurativi versetti = new VersettiCurativi("Versetti Curativi", "Cura 1 HP su doppio");
        p.getInventario().aggiungi(versetti);

        // 3. Creazione Mostro di prova
        List<Simbolo> simboliFissi = new ArrayList<>();
        simboliFissi.add(Simbolo.FORZA);
        simboliFissi.add(Simbolo.ASTUZIA);
        simboliFissi.add(Simbolo.SAGGEZZA);

        CartaMostro mostroTest = new CartaMostro("Manichino di Test", simboliFissi, 3);

        System.out.println("\nInventario attuale dell'Eroe:");
        for (Object obj : p.getInventario().getOggetti()) {
            System.out.println("- " + obj);
        }

        // 4. Avvio Combattimento di prova
        System.out.println("\n==============================================");
        System.out.println("     INIZIO COMBATTIMENTO DI PROVA            ");
        System.out.println("==============================================");

        DadoCapitolo dadoCapitolo = new DadoCapitolo();
        GestoreCombattimento gestore = new GestoreCombattimento();

        // Avvia il combattimento: potrai provare a usare la pozione simbolo dal menu,
        // tirare il dado per vedere se scattano arma, fortuna e versetti,
        // e subire l'attacco per vedere se scatta la pozione evasione!
        gestore.combatti(p, mostroTest, dadoCapitolo);
    }
}