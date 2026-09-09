package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class EventoStanzaDelTorturatore extends CartaEvento{

    public EventoStanzaDelTorturatore() {
        super("Vieni stordito e trascinato lungo un passaggio laterale. Ti risvegli legato a un tavolo, \n" +
                "mentre un uomo vestito di un sudicio grembiule sta raschiando via della ruggine da un lama seghettata. \n" +
                "Stai per diventare una cavia del torturatore del castello!\n" +
                "Resisti al dolore (5 tentativi su FORZA o DOPPIO).");
    }

    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println(getDescrizione());
    }

    @Override
    public void eseguiProvaDado(Personaggio personaggioCheHaGirato, DadoCapitolo dadoCapitolo) {
        System.out.println("\n" + personaggioCheHaGirato.getNome() + " tenta di resistere alla tortura!");

        for (int i = 1; i <= 5; i++) {
            FacciaDado faccia = personaggioCheHaGirato.lanciaDado();
            Simbolo tiro = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            System.out.print("Tiro " + i + "/5: " + tiro + (eDoppio ? " (DOPPIO)" : "") + " -> ");
            if (tiro == Simbolo.FORZA || eDoppio) {
                System.out.println("Resisti!");
            } else {
                System.out.println("Fallito! Perdi 1 HP.");
                personaggioCheHaGirato.subisciDanno(1, false, false);
            }
        }
    }
}
