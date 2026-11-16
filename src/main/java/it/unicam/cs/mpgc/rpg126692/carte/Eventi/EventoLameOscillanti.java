package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoLameOscillanti extends CartaEvento {

    public EventoLameOscillanti() {
        super("Una serie di lunghe lame oscilla dal soffitto di questo stretto corridoio. \n" +
                "Studia lo schema dei loro movimenti e preparati a scattare verso l'altro lato.\n" +
                "Esegui 3 tiri di schivata!\n" +
                "(Lancia un DadoPersonaggio insieme a un DadoCapitolo 3 volte, \n" +
                "per ogni corrispondenza perdi 2HP)", "/images/cassero/lame.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        Interazioni.corrente().println("\n--- EVENTO: LAME OSCILLANTI ---");
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        DadoCapitolo dadoCapitolo = new DadoCapitolo();

        for (int i = 1; i <= 3; i++) {
            if (personaggio.isSconfitto()) break;

            Interazioni.corrente().println("\n--- SCHIVATA " + i + "/3 ---");
            Interazioni.corrente().println("[Premi INVIO per schivare la lama...]");
            Interazione.nextLine();

            Simbolo simboloLama = dadoCapitolo.lancia();
            Interazioni.corrente().println("Movimento Trappola (Lama): " + simboloLama);

            FacciaDado faccia = personaggio.lanciaDado();
            Simbolo tiroGiocatore = faccia.getSimboloPrincipale();
            boolean eDoppio = faccia.isDoppio();

            Interazioni.corrente().println("Tuo tiro: " + faccia.descrivi());

            if (eDoppio) {
                Interazioni.corrente().println("-> Hai ottenuto un DOPPIO! Schivi perfettamente la lama!");
            } else if (tiroGiocatore == simboloLama) {
                Interazioni.corrente().println("-> Vieni colpito dalle lame! Perdi 2 HP.");
                personaggio.subisciDanno(2, false, false);
                Interazioni.corrente().println("HP Attuali: [" + personaggio.getHP() + "/" + personaggio.getSaluteMassima() + "]");
            } else {
                Interazioni.corrente().println("-> Eviti le lame con successo.");
            }
        }
    }
}
