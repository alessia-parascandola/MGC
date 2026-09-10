package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;
import java.util.Scanner;

public class EventoLameOscillanti extends CartaEvento {

    public EventoLameOscillanti() {
        super("Una serie di lunghe lame oscilla dal soffitto di questo stretto corridoio. \n" +
                "Studia lo schema dei loro movimenti e preparati a scattare verso l'altro lato.\n" +
                "Esegui 3 tiri di schivata!");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println("\n--- EVENTO: LAME OSCILLANTI ---");
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);
        DadoCapitolo dadoCapitolo = new DadoCapitolo();

        for (int i = 1; i <= 3; i++) {
            if (personaggio.isSconfitto()) break;

            System.out.println("\n--- SCHIVATA " + i + "/3 ---");
            System.out.println("[Premi INVIO per schivare la lama...]");
            scanner.nextLine();

            Simbolo simboloLama = dadoCapitolo.lancia();
            System.out.println("Movimento Trappola (Lama): " + simboloLama);

            FacciaDado faccia = personaggio.lanciaDado();
            Simbolo tiroGiocatore = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            System.out.println("Tuo tiro: " + tiroGiocatore + (eDoppio ? " (DOPPIO)" : ""));

            if (eDoppio) {
                System.out.println("-> Hai ottenuto un DOPPIO! Schivi perfettamente la lama!");
            } else if (tiroGiocatore == simboloLama) {
                System.out.println("-> Vieni colpito dalle lame! Perdi 2 HP.");
                personaggio.subisciDanno(2, false, false);
                System.out.println("HP Attuali: [" + personaggio.getHP() + "/18]");
            } else {
                System.out.println("-> Eviti le lame con successo.");
            }
        }
    }
}
