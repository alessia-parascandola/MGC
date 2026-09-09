package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;

public class EventoLameOscillanti extends CartaEvento {

    public EventoLameOscillanti() {
        super("Una serie di lunghe lame oscilla dal soffitto di questo stretto corridoio. \n" +
                "Studia lo schema dei loro movimenti e preparati a scattare verso l'altro lato.\n" +
                "Esegui 3 tiri di schivata!");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println(getDescrizione());
    }

    public void eseguiProvaMoltitudine(List<Personaggio> gruppo, DadoCapitolo dadoCapitolo) {
        for (int i = 1; i <= 3; i++) {
            System.out.println("\n--- LANCIO LAME " + i + "/3 ---");
            Simbolo simboloLama = dadoCapitolo.lancia();
            System.out.println("Risultato Trappola (Dado Capitolo): " + simboloLama);

            for (Personaggio p : gruppo) {
                FacciaDado faccia = p.lanciaDado();
                Simbolo tiroGiocatore = faccia.getSimboloPrincipale();
                boolean eDoppio = faccia.isDoppio();

                System.out.println(p.getNome() + " ha tirato: " + tiroGiocatore + (eDoppio ? " (DOPPIO)" : ""));

                if (eDoppio) {
                    System.out.println("-> " + p.getNome() + " ha ottenuto un DOPPIO! Schiva le lame!");
                } else if (tiroGiocatore == simboloLama) {
                    System.out.println("-> " + p.getNome() + " viene colpito dalle lame! Perdi 2 HP.");
                    p.subisciDanno(2, false, false);
                } else {
                    System.out.println("-> " + p.getNome() + " evita le lame.");
                }
            }
        }
    }
}
