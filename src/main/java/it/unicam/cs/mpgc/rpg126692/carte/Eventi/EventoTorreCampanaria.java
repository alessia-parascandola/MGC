package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;

public class EventoTorreCampanaria extends CartaEvento{

    public EventoTorreCampanaria() {
        super("Sali lungo una stretta scala a chiocciola ed emergi in cima a una torre campanaria \n" +
                "sferzata dal vento. Mentre attraversi la torre, la campana inizia a oscillare, \n" +
                "facendo tremare le tue ossa con il suo tetro rintocco. \n" +
                "Prova di SAPIENZA o DOPPIO per resistere al suono asfissiante della campana!");
    }

    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println(getDescrizione());
    }

    public void eseguiProvaMoltitudine(List<Personaggio> gruppo) {
        for (Personaggio p : gruppo) {
            FacciaDado faccia = p.lanciaDado();
            Simbolo tiro = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            System.out.print(p.getNome() + " ha tirato: " + tiro + (eDoppio ? " (DOPPIO)" : "") + " -> ");
            if (tiro == Simbolo.SAGGEZZA || eDoppio) {
                System.out.println("Hai resistito alla fobia della campana!");
            } else {
                System.out.println("Il rintocco ti fa impazzire! Perdi 2 HP.");
                p.subisciDanno(2, false, false);
            }
        }
    }
}
