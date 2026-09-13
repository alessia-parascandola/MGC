package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.Scanner;

public class EventoTorreCampanaria extends CartaEvento{

    public EventoTorreCampanaria() {
        super("Sali lungo una stretta scala a chiocciola ed emergi in cima a una torre campanaria \n" +
                "sferzata dal vento. Mentre attraversi la torre, la campana inizia a oscillare, \n" +
                "facendo tremare le tue ossa con il suo tetro rintocco. \n" +
                "Prova di SAGGEZZA o DOPPIO per resistere al suono asfissiante della campana!",
                "/images/cassero/campana.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println("\n--- EVENTO: TORRE CAMPANARIA ---");
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n[Premi INVIO per lanciare il dado ed eseguire la prova di SAGGEZZA...]");
        scanner.nextLine();

        FacciaDado faccia = personaggio.lanciaDado();
        Simbolo tiro = faccia.getSimboloPrincipale();
        boolean eDoppio = faccia.isDoppio();

        System.out.print("Hai tirato: " + tiro + (eDoppio ? " (DOPPIO)" : "") + " -> ");
        if (tiro == Simbolo.SAGGEZZA || eDoppio) {
            System.out.println("SUCCESSO! Hai resistito al rintocco della campana!");
        } else {
            System.out.println("FALLIMENTO! Il rintocco ti fa impazzire! Perdi 2 HP.");
            personaggio.subisciDanno(2, false, false);
            System.out.println("HP Attuali: [" + personaggio.getHP() + "/18]");
        }
    }
}