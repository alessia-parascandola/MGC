package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;

public class EventoStormoDiPipistrelli extends CartaEvento{

    public EventoStormoDiPipistrelli() {
        super("All'improvviso, uno stormo di pipistrelli giganti irrompe nel corridoio. \n" +
                "Puoi solo cercare di ripararti dal soverchiante turbinare di ali e zanne.");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println(getDescrizione());
    }

    public void eseguiProvaMoltitudine(List<Personaggio> gruppo, DadoCapitolo dadoCapitolo) {
        for (Personaggio p : gruppo) {
            System.out.println("\nI pipistrelli attaccano " + p.getNome() + "!");
            FacciaDado faccia = p.lanciaDado();
            Simbolo tiroGiocatore = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            System.out.println(p.getNome() + " ha tirato: " + tiroGiocatore + (eDoppio ? " (DOPPIO)" : ""));

            if (eDoppio) {
                System.out.println("-> " + p.getNome() + " ottiene un DOPPIO! Respinge tutti i pipistrelli!");
                continue;
            }

            int ferite = 0;
            for (int i = 1; i <= 3; i++) {
                Simbolo pipistrello = dadoCapitolo.lancia();
                System.out.println("Pipistrello " + i + ": " + pipistrello);
                if (pipistrello == tiroGiocatore) {
                    ferite++;
                }
            }

            if (ferite > 0) {
                System.out.println("-> " + p.getNome() + " subisce " + ferite + " ferita/e dai pipistrelli!");
                p.subisciDanno(ferite, false, false);
            } else {
                System.out.println("-> " + p.getNome() + " non subisce alcuna ferita.");
            }
        }
    }
}
