package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class CavernaAllagata extends CartaEvento{

    public CavernaAllagata() {
        super("Questo corridoio porta a una rampa di vecchi scalini di pietra. \n" +
                "Scendono verso una caverna allagata. L'acqua è torbida, ma una \n" +
                "tenue luce si distingue nelle profondità. Prendi un respiro profondo \n" +
                "e ti tuffi, nuotando verso la luce. \n" +
                "Ottieni un DOPPIO entro 3 tentativi per riemergere!", "/images/cassero/caverna.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        Interazioni.corrente().println("\n--- EVENTO: CAVERNA ALLAGATA ---");
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        boolean superato = false;

        for (int tentativo = 1; tentativo <= 3; tentativo++) {
            if (personaggio.isSconfitto()) break;

            Interazioni.corrente().println("\n[Tentativo " + tentativo + "/3 - Premi INVIO per lanciare il dado e cercare un DOPPIO...]");
            Interazione.nextLine();

            FacciaDado faccia = personaggio.lanciaDado();
            boolean eDoppio = faccia.isDoppio();

            Interazioni.corrente().println("Esito lancio: " + faccia.descrivi());

            if (eDoppio) {
                Interazioni.corrente().println("SUCCESSO: Riesci a trovare un varco e riemergi dall'altra parte!");
                superato = true;
                break;
            } else {
                Interazioni.corrente().println("Inizi ad annegare! Perdi 1 HP.");
                personaggio.subisciDanno(1, false, false);
                Interazioni.corrente().println("HP Attuali: [" + personaggio.getHP() + "/" + personaggio.getSaluteMassima() + "]");
            }
        }

        if (!superato && !personaggio.isSconfitto()) {
            Interazioni.corrente().println("\nDopo 3 tentativi falliti, riesci trascinandoti a riemergere esausto e senza fiato.");
        }
    }
}
