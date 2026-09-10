package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.GestorePartita;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
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

        // 1. Danno immediato (se presente)
        if (dannoSubito > 0) {
            // System.out.println("Subisci " + dannoSubito + " HP di danno!");
            personaggio.subisciDanno(dannoSubito, false, false);
        }

        for (int i = 0; i < quantitaOggetti; i++) {
            if (!mazzoOggetti.isVuoto()) {
                Oggetto nuovo = mazzoOggetti.pesca();;
                System.out.println("\nHai trovato: " + nuovo.getNome() + " - " + nuovo.getDescrizione());

                // Richiama la gestione inventario centralizzata di GestorePartita
                GestorePartita.gestisciAcquisizioneOggetto(personaggio, nuovo);
            } else {
                System.out.println("\nIl mazzo degli oggetti è vuoto!");
                break;
            }
        }
    }
}
