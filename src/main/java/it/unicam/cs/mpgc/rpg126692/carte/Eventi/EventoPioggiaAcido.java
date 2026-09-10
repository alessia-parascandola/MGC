package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class EventoPioggiaAcido extends CartaEvento{

    public EventoPioggiaAcido(){
        super("Inavvertitamente, il tuo stivale inciampa sul filo di una trappola che rilascia una pioggia d'acido dall'alto!\n" +
                "Esegui una prova di ASTUZIA per schivare!");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println("\n--- EVENTO: PIOGGIA D'ACIDO ---");
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n[Premi INVIO per lanciare il dado ed eseguire la prova di ASTUZIA...]");
        scanner.nextLine();

        FacciaDado faccia = personaggio.lanciaDado();
        Simbolo tiro = faccia.getSimboloPrincipale();
        boolean eDoppio = faccia.isDoppio();

        System.out.print("Hai tirato: " + tiro + (eDoppio ? " (DOPPIO)" : "") + " -> ");

        if (tiro == Simbolo.ASTUZIA || eDoppio) {
            System.out.println("SUCCESSO! Schivi la pioggia d'acido in tempo!");
        } else {
            System.out.println("FALLIMENTO! L'acido cade su di te!");
            List<Oggetto> oggetti = personaggio.getInventario().getOggetti();

            if (oggetti.isEmpty()) {
                System.out.println("Non hai oggetti da proteggerti! L'acido ti corrode la pelle: perdi 1 HP.");
                personaggio.subisciDanno(1, false, false);
                System.out.println("HP Attuali: [" + personaggio.getHP() + "/18]");
            } else if (oggetti.size() == 1) {
                Oggetto distrutto = oggetti.get(0);
                System.out.println("L'acido corrode e distrugge il tuo oggetto: " + distrutto.getNome() + "!");
                personaggio.getInventario().scarta(0);
            } else {
                Random random = new Random();
                int idxDistretto = random.nextInt(oggetti.size());
                Oggetto distrutto = oggetti.get(idxDistretto);
                System.out.println("L'acido corrode e distrugge un tuo oggetto equipaggiato: " + distrutto.getNome() + "!");
                personaggio.getInventario().scarta(idxDistretto);
            }
        }
    }
}
