package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;
import java.util.Random;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoPioggiaAcido extends CartaEvento{

    public EventoPioggiaAcido(){
        super("Inavvertitamente, il tuo stivale inciampa sul filo di una trappola che rilascia una pioggia d'acido dall'alto!\n" +
                "Esegui una prova di ASTUZIA per schivare!", "/images/cassero/acido.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        Interazioni.corrente().println("\n--- EVENTO: PIOGGIA D'ACIDO ---");
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        Interazioni.corrente().println("\n[Premi INVIO per lanciare il dado ed eseguire la prova di ASTUZIA...]");
        Interazione.nextLine();

        FacciaDado faccia = personaggio.lanciaDado();
        Simbolo tiro = faccia.getSimboloPrincipale();
        boolean eDoppio = faccia.isDoppio();

        Interazioni.corrente().print("Hai tirato: " + faccia.descrivi() + " -> ");

        if (tiro == Simbolo.ASTUZIA || eDoppio) {
            Interazioni.corrente().println("SUCCESSO! Schivi la pioggia d'acido in tempo!");
        } else {
            Interazioni.corrente().println("FALLIMENTO! L'acido cade su di te!");
            List<Oggetto> oggetti = personaggio.getInventario().getOggetti();

            if (oggetti.isEmpty()) {
                Interazioni.corrente().println("Non hai oggetti da proteggerti! L'acido ti corrode la pelle: perdi 1 HP.");
                personaggio.subisciDanno(1, false, false);
                Interazioni.corrente().println("HP Attuali: [" + personaggio.getHP() + "/" + personaggio.getSaluteMassima() + "]");
            } else if (oggetti.size() == 1) {
                Oggetto distrutto = oggetti.get(0);
                Interazioni.corrente().println("L'acido corrode e distrugge il tuo oggetto: " + distrutto.getNome() + "!");
                personaggio.getInventario().scarta(0);
            } else {
                Random random = new Random();
                int idxDistretto = random.nextInt(oggetti.size());
                Oggetto distrutto = oggetti.get(idxDistretto);
                Interazioni.corrente().println("L'acido corrode e distrugge un tuo oggetto equipaggiato: " + distrutto.getNome() + "!");
                personaggio.getInventario().scarta(idxDistretto);
            }
        }
    }
}
