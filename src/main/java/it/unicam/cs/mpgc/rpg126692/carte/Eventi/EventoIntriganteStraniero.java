package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoIntriganteStraniero extends CartaEvento{
    public EventoIntriganteStraniero() {
        super("Fermando il suo carro di fianco a te, un intrigante straniero insiste per farti \n" +
                "provare la sua ultima pozione: un elisir curativo, afferma. Una mazza \n" +
                "ferrata penzola dalla sua cintura e, impaziente come sei di continuare, \n" +
                "non ti sembra una buona idea rifiutare. Del resto, che male può fare? \n" +
                "Fai una scelta:\n" +
                "1. Bevi un piccolo sorso. \n" +
                "2. Bevi un lungo sorso.", "/images/cassero/straniero.png");
    }

    // Stampa iniziale del capitolo
    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzo) {
        Interazioni.corrente().println("\n--- EVENTO: L'INTRIGANTE STRANIERO ---");
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        Interazioni.corrente().print("\nFai la tua scelta (1 o 2): ");
        String input = Interazione.nextLine().trim();

        if (input.equals("1")) {
            Interazioni.corrente().println("\nBevi un piccolo sorso. L'uomo sorride e ti ricompensa!");
            if (!mazzo.isVuoto()) {
                personaggio.getInventario().aggiungi(mazzo.pesca());
            }
        } else if (input.equals("2")) {
            Interazioni.corrente().println("\nBevi un lungo sorso! Esegui una prova di POSSANZA...");
            FacciaDado faccia = personaggio.lanciaDado();
            Interazioni.corrente().println("Esito lancio: " + faccia.descrivi());

            if (faccia.getSimboloPrincipale() == Simbolo.POSSANZA) {
                Interazioni.corrente().println("SUCCESSO: L'elisir ti scalda le viscere! Guadagni 3 HP.");
                personaggio.cura(3);
            } else {
                Interazioni.corrente().println("FALLIMENTO: È tossico e pestilenziale! Vomiti e perdi 1 HP.");
                personaggio.subisciDanno(1, false, false);
            }
        } else {
            Interazioni.corrente().println("Tentenni troppo! Lo straniero scontento parte con il suo carro.");
        }
    }
}
