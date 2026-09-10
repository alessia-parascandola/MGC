package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;
import java.util.Scanner;

public class CavernaAllagata extends CartaEvento{

    public CavernaAllagata() {
        super("Questo corridoio porta a una rampa di vecchi scalini di pietra. \n" +
                "Scendono verso una caverna allagata. L'acqua è torbida, ma una \n" +
                "tenue luce si distingue nelle profondità. Prendi un respiro profondo \n" +
                "e ti tuffi, nuotando verso la luce. \n" +
                "Ottieni un DOPPIO entro 3 tentativi per riemergere!");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println("\n--- EVENTO: CAVERNA ALLAGATA ---");
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);
        boolean superato = false;

        for (int tentativo = 1; tentativo <= 3; tentativo++) {
            if (personaggio.isSconfitto()) break;

            System.out.println("\n[Tentativo " + tentativo + "/3 - Premi INVIO per lanciare il dado e cercare un DOPPIO...]");
            scanner.nextLine();

            FacciaDado faccia = personaggio.lanciaDado();
            boolean eDoppio = faccia.isDoppio();

            System.out.println("Esito lancio: " + faccia.getSimboloPrincipale() + (eDoppio ? " (DOPPIO!)" : ""));

            if (eDoppio) {
                System.out.println("SUCCESSO: Riesci a trovare un varco e riemergi dall'altra parte!");
                superato = true;
                break;
            } else {
                System.out.println("Inizi ad annegare! Perdi 1 HP.");
                personaggio.subisciDanno(1, false, false);
                System.out.println("HP Attuali: [" + personaggio.getHP() + "/18]");
            }
        }

        if (!superato && !personaggio.isSconfitto()) {
            System.out.println("\nDopo 3 tentativi falliti, riesci trascinandoti a riemergere esausto e senza fiato.");
        }
    }
}
