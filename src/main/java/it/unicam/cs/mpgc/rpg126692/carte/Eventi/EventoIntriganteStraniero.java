package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class EventoIntriganteStraniero extends CartaEvento{
    public EventoIntriganteStraniero() {
        super("Fermando il suo carro di fianco a te, un intrigante straniero insiste per farti \n" +
                "provare la sua ultima pozione: un elisir curativo, afferma. Una mazza \n" +
                "ferrata penzola dalla sua cintura e, impaziente come sei di continuare, \n" +
                "non ti sembra una buona idea rifiutare. Del resto, che male può fare? \n" +
                "Fai una scelta:\n" +
                "1. Bevi un piccolo sorso. \n" +
                "2. Bevi un lungo sorso.");
    }

    // Stampa iniziale del capitolo
    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println(getDescrizione());
    }

    // Metodo chiamato dal Controller in base al valore scelto dal giocatore (1 o 2)
    public void risolviScelta(int opzione, Personaggio personaggio, MazzoOggetti mazzo) {
        // Opzione 1: Ricompensa sicura (1 carta oggetto estratta dal mazzo)
        if (opzione == 1) {
            System.out.println("Bevi un piccolo sorso. L'uomo sorride e ti ricompensa!");
            if (!mazzo.isVuoto()) {
                personaggio.getInventario().aggiungi(mazzo.pesca());
            }
        }
        // Opzione 2: Rischio con prova di FORZA
        else if (opzione == 2) {
            System.out.println("Bevi un lungo sorso! Esegui una prova di FORZA...");
            FacciaDado faccia = personaggio.lanciaDado();

            // Successo: Cura 3 HP
            if (faccia.getSimboloPrincipale() == Simbolo.FORZA) {
                System.out.println("SUCCESSO: L'elisir ti scalda le viscere! Guadagni 3 HP.");
                personaggio.cura(3);
            }
            // Fallimento: Perdi 1 HP
            else {
                System.out.println("FALLIMENTO: È tossico e pestilenziale! Vomiti e perdi 1 HP.");
                personaggio.subisciDanno(1, false, false);
            }
        }
    }
}
