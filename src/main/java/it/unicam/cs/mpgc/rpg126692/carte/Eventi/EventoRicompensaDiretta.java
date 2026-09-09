package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class EventoRicompensaDiretta extends CartaEvento {
    private final int quantitaOggetti;
    private final int dannoSubito;

    public EventoRicompensaDiretta(String descrizione, int quantitaOggetti, int dannoSubito){
        super(descrizione);
        this.quantitaOggetti = quantitaOggetti;
        this.dannoSubito = dannoSubito;
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println(getDescrizione());
    }

    @Override
    public void applicaEffettoDiretto(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        if (dannoSubito > 0) {
            // System.out.println("Subisci " + dannoSubito + " HP di danno!");
            personaggio.subisciDanno(dannoSubito, false, false);
        }

        for (int i = 0; i < quantitaOggetti; i++) {
            if (!mazzoOggetti.isVuoto()) {
                personaggio.getInventario().aggiungi(mazzoOggetti.pesca());
            }
        }
    }
}
