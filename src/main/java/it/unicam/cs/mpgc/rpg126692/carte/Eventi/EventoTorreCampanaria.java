package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoTorreCampanaria extends CartaEvento{

    public EventoTorreCampanaria() {
        super("Sali lungo una stretta scala a chiocciola ed emergi in cima a una torre campanaria \n" +
                "sferzata dal vento. Mentre attraversi la torre, la campana inizia a oscillare, \n" +
                "facendo tremare le tue ossa con il suo tetro rintocco. \n" +
                "Prova di SAPIENZA o DOPPIO per resistere al suono asfissiante della campana!",
                "/images/cassero/campana.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        Interazioni.corrente().println("\n--- EVENTO: TORRE CAMPANARIA ---");
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        Interazioni.corrente().println("\n[Premi INVIO per lanciare il dado ed eseguire la prova di SAPIENZA...]");
        Interazione.nextLine();

        FacciaDado faccia = personaggio.lanciaDado();
        Simbolo tiro = faccia.getSimboloPrincipale();
        boolean eDoppio = faccia.isDoppio();

        Interazioni.corrente().print("Hai tirato: " + faccia.descrivi() + " -> ");
        if (tiro == Simbolo.SAPIENZA || eDoppio) {
            Interazioni.corrente().println("SUCCESSO! Hai resistito al rintocco della campana!");
        } else {
            Interazioni.corrente().println("FALLIMENTO! Il rintocco ti fa impazzire! Perdi 2 HP.");
            personaggio.subisciDanno(2, false, false);
            Interazioni.corrente().println("HP Attuali: [" + personaggio.getHP() + "/" + personaggio.getSaluteMassima() + "]");
        }
    }
}