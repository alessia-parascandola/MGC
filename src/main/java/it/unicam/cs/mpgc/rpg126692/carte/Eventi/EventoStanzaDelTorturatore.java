package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoStanzaDelTorturatore extends CartaEvento{

    public EventoStanzaDelTorturatore() {
        super("Vieni stordito e trascinato lungo un passaggio laterale. Ti risvegli legato a un tavolo, \n" +
                "mentre un uomo vestito di un sudicio grembiule sta raschiando via della ruggine da un lama seghettata. \n" +
                "Stai per diventare una cavia del torturatore del castello!\n" +
                "Resisti al dolore (5 tentativi su POSSANZA o DOPPIO).", "/images/cassero/torturatore.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();

        for (int i = 1; i <= 5; i++) {
            if (personaggio.isSconfitto()) break;

            Interazioni.corrente().println("\n[Premi INVIO per il lancio " + i + "/5 (POSSANZA o DOPPIO)...]");
            Interazione.nextLine();

            FacciaDado faccia = personaggio.lanciaDado();
            Simbolo tiro = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            Interazioni.corrente().print("Tiro " + i + "/5: " + faccia.descrivi() + " -> ");
            if (tiro == Simbolo.POSSANZA || eDoppio) {
                Interazioni.corrente().println("Resisti al dolore!");
            } else {
                Interazioni.corrente().println("Fallito! Subisci 1 HP di danno.");
                personaggio.subisciDanno(1, false, false);
                Interazioni.corrente().println("HP Attuali: [" + personaggio.getHP() + "/" + personaggio.getSaluteMassima() + "]");
            }
        }
    }
}
