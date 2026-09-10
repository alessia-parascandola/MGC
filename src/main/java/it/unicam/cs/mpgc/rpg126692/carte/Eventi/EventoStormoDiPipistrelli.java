package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;
import java.util.Scanner;

public class EventoStormoDiPipistrelli extends CartaEvento{

    public EventoStormoDiPipistrelli() {
        super("All'improvviso, uno stormo di pipistrelli giganti irrompe nel corridoio. \n" +
                "Puoi solo cercare di ripararti dal soverchiante turbinare di ali e zanne.");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println("\n--- EVENTO: STORMO DI PIPISTRELLI ---");
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n[Premi INVIO per lanciare il dado e difenderti...]");
        scanner.nextLine();

        FacciaDado faccia = personaggio.lanciaDado();
        Simbolo tiroGiocatore = faccia.getSimboloPrincipale();
        boolean eDoppio = faccia.isDoppio();

        System.out.println("Hai tirato: " + tiroGiocatore + (eDoppio ? " (DOPPIO)" : ""));

        if (eDoppio) {
            System.out.println("-> DOPPIO! Respingi tutti i pipistrelli senza subire danni!");
            return;
        }

        DadoCapitolo dadoCapitolo = new DadoCapitolo();
        int ferite = 0;

        for (int i = 1; i <= 3; i++) {
            Simbolo pipistrello = dadoCapitolo.lancia();
            System.out.println("Pipistrello " + i + " attacca con: " + pipistrello);
            if (pipistrello == tiroGiocatore) {
                ferite++;
            }
        }

        if (ferite > 0) {
            System.out.println("\n-> Subisci " + ferite + " ferita/e dai pipistrelli!");
            personaggio.subisciDanno(ferite, false, false);
            System.out.println("HP Attuali: [" + personaggio.getHP() + "/18]");
        } else {
            System.out.println("\n-> Nessun pipistrello ti ha colpito! Sei salvo.");
        }
    }
}
