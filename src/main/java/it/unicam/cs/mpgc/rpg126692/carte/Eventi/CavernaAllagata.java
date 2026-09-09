package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;

public class CavernaAllagata extends CartaEvento{

    public CavernaAllagata() {
        super("Questo corridoio porta a una rampa di vecchi scalini di pietra. \n" +
                "Scendono verso una caverna allagata. L'acqua è torbida, ma una \n" +
                "tenue luce si distingue nelle profondità. Prendi un respiro profondo \n" +
                "e ti tuffi, nuotando verso la luce. \n" +
                "Ottieni un DOPPIO entro 3 tentativi per riemergere!");
    }

    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println(getDescrizione());
    }

    // Risolve la prova a tentativi progressivi per ciascun personaggio
    public void eseguiProvaMoltitudine(List<Personaggio> gruppo) {
        for (Personaggio p : gruppo) {
            System.out.println("\n--- Tuffo di " + p.getNome() + " ---");
            boolean superato = false;

            // Il personaggio ha fino a un massimo di 3 tentativi per ottenere un doppio
            for (int tentativo = 1; tentativo <= 3; tentativo++) {
                FacciaDado faccia = p.lanciaDado();
                System.out.println("Tentativo " + tentativo + ": " + faccia.getSimboloPrincipale() + (faccia.isDoppio() ? " (DOPPIO)" : ""));

                // Se la faccia del dado tirata ha due simboli (isDoppio == true), la prova è superata
                if (faccia.isDoppio()) {
                    System.out.println("SUCCESSO: Riemergi dall'altra parte!");
                    superato = true;
                    break; // Interrompe i tentativi rimanenti per questo personaggio
                } else {
                    // Ad ogni fallimento perdi 1 HP prima di poter ritentare
                    System.out.println("Inizi ad annegare! Perdi 1 HP.");
                    p.subisciDanno(1, false, false);

                    // Se gli HP scendono a 0, interrompe i tentativi per evitare lanci da morto
                    if (p.isSconfitto()) break;
                }
            }

            // Se dopo 3 tentativi non ha mai fatto un doppio ma è ancora vivo, riesce comunque a passare esausto
            if (!superato && !p.isSconfitto()) {
                System.out.println("Dopo 3 tentativi, riesci a passare esausto e senza fiato.");
            }
        }
    }
}
