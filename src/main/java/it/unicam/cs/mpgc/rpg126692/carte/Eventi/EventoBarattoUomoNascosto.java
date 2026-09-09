package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class EventoBarattoUomoNascosto extends CartaEvento{

    public EventoBarattoUomoNascosto() {
        super("Un uomo nascosto si avvicina. Egli osserva prudentemente alle sue \n" +
                "spalle e poi, sussurrando, ti chiede se sei interessato a barattare. \n" +
                "Opzioni:\n" +
                "1. Baratta (Scarta 1 Oggetto e ne peschi 2)\n" +
                "2. Tratta (Prova su DOPPIO: se riesci peschi 1 Oggetto)");
    }

    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println(getDescrizione());
    }

    // Riceve l'opzione e l'indice dell'oggetto da scartare se si sceglie il baratto
    public void risolviScelta(int opzione, int indiceOggettoDaScartare, Personaggio p, MazzoOggetti mazzo) {
        // Opzione 1: Baratto (richiede la presenza di almeno un oggetto)
        if (opzione == 1) {
            if (p.getInventario().isVuoto()) {
                System.out.println("Non hai oggetti da barattare!");
                return;
            }
            // Scarta l'oggetto scelto dall'utente e pesca 2 oggetti dal mazzo
            p.getInventario().scarta(indiceOggettoDaScartare);
            System.out.println("Hai barattato un oggetto! Peschi 2 nuovi oggetti.");
            for (int i = 0; i < 2; i++) {
                if (!mazzo.isVuoto()) p.getInventario().aggiungi(mazzo.pesca());
            }
        }
        // Opzione 2: Trattativa tramite prova di dadi
        else if (opzione == 2) {
            System.out.println("Tenti di trattare! Fai una prova su DOPPIO...");
            FacciaDado faccia = p.lanciaDado();

            // Se esce un doppio, pesca 1 oggetto gratis senza scartare nulla
            if (faccia.isDoppio()) {
                System.out.println("SUCCESSO: La tua parlantina ti premia! Pesca 1 Oggetto.");
                if (!mazzo.isVuoto()) p.getInventario().aggiungi(mazzo.pesca());
            } else {
                System.out.println("FALLIMENTO: Il tuo tentativo è inutile, non ottieni nulla.");
            }
        }
    }
}
