package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoStormoDiPipistrelli extends CartaEvento{

    public EventoStormoDiPipistrelli() {
        super("All'improvviso, uno stormo di pipistrelli giganti irrompe nel corridoio. \n" +
                "Puoi solo cercare di ripararti dal soverchiante turbinare di ali e zanne.\n" +
                "Difenditi: tira il DadoPersonaggio insieme a tre DadiCapitolo, per ogni simbolo\n" +
                "corrispondente perdi 1HP, ma se ti esce un DOPPIO non vieni ferito!", "/images/cassero/pipistrelli.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        Interazioni.corrente().println("\n[Premi INVIO per lanciare il dado e difenderti...]");
        Interazione.nextLine();

        FacciaDado faccia = personaggio.lanciaDado();
        Simbolo tiroGiocatore = faccia.getSimboloPrincipale();
        boolean eDoppio = faccia.isDoppio();

        Interazioni.corrente().println("Hai tirato: " + faccia.descrivi());

        if (eDoppio) {
            Interazioni.corrente().println("-> DOPPIO! Respingi tutti i pipistrelli senza subire danni!");
            return;
        }

        DadoCapitolo dadoCapitolo = new DadoCapitolo();
        int ferite = 0;

        for (int i = 1; i <= 3; i++) {
            Simbolo pipistrello = dadoCapitolo.lancia();
            Interazioni.corrente().println("Pipistrello " + i + " attacca con: " + pipistrello);
            if (pipistrello == tiroGiocatore) {
                ferite++;
            }
        }

        if (ferite > 0) {
            Interazioni.corrente().println("\n-> Subisci " + ferite + " ferita/e dai pipistrelli!");
            personaggio.subisciDanno(ferite, false, false);
            Interazioni.corrente().println("HP Attuali: [" + personaggio.getHP() + "/" + personaggio.getSaluteMassima() + "]");
        } else {
            Interazioni.corrente().println("\n-> Nessun pipistrello ti ha colpito! Sei salvo.");
        }
    }
}
