package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.Scanner;

public class EventoStanzaDelTorturatore extends CartaEvento{

    public EventoStanzaDelTorturatore() {
        super("Vieni stordito e trascinato lungo un passaggio laterale. Ti risvegli legato a un tavolo, \n" +
                "mentre un uomo vestito di un sudicio grembiule sta raschiando via della ruggine da un lama seghettata. \n" +
                "Stai per diventare una cavia del torturatore del castello!\n" +
                "Resisti al dolore (5 tentativi su FORZA o DOPPIO).", "/images/cassero/torturatore.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);

        for (int i = 1; i <= 5; i++) {
            if (personaggio.isSconfitto()) break;

            System.out.println("\n[Premi INVIO per il lancio " + i + "/5 (FORZA o DOPPIO)...]");
            scanner.nextLine();

            FacciaDado faccia = personaggio.lanciaDado();
            Simbolo tiro = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            System.out.print("Tiro " + i + "/5: " + tiro + (eDoppio ? " (DOPPIO)" : "") + " -> ");
            if (tiro == Simbolo.FORZA || eDoppio) {
                System.out.println("Resisti al dolore!");
            } else {
                System.out.println("Fallito! Subisci 1 HP di danno.");
                personaggio.subisciDanno(1, false, false);
                System.out.println("HP Attuali: [" + personaggio.getHP() + "/18]");
            }
        }
    }
}
