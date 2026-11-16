package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class EventoBarattoUomoNascosto extends CartaEvento{

    public EventoBarattoUomoNascosto() {
        super("Un uomo nascosto si avvicina. Egli osserva prudentemente alle sue \n" +
                "spalle e poi, sussurrando, ti chiede se sei interessato a barattare. \n" +
                "Opzioni:\n" +
                "1. Baratta (Scarta 1 Oggetto e ne peschi 2)\n" +
                "2. Tratta (Prova su DOPPIO: se riesci peschi 1 Oggetto)", "/images/cassero/uomo_nascosto.png");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzo) {
        Interazioni.corrente().println("\n--- EVENTO: L'UOMO NASCOSTO ---");
        Interazioni.corrente().println(getDescrizione());

        Interazione Interazione = Interazioni.corrente();
        Interazioni.corrente().print("\nFai la tua scelta (1 o 2): ");
        String input = Interazione.nextLine().trim();

        if (input.equals("1")) {
            if (personaggio.getInventario().isVuoto()) {
                Interazioni.corrente().println("Non hai oggetti da barattare! L'uomo scuote la testa e se ne va.");
                return;
            }

            Interazioni.corrente().println("\nQuale oggetto vuoi scartare?");
            for (int i = 0; i < personaggio.getInventario().getOggetti().size(); i++) {
                Interazioni.corrente().println("[" + (i + 1) + "] " + personaggio.getInventario().getOggetti().get(i).getNome());
            }
            Interazioni.corrente().print("> ");

            try {
                int idx = Integer.parseInt(Interazione.nextLine().trim()) - 1;
                if (idx >= 0 && idx < personaggio.getInventario().getOggetti().size()) {
                    personaggio.getInventario().scarta(idx);
                    Interazioni.corrente().println("Hai barattato l'oggetto! Peschi 2 nuovi oggetti.");
                    for (int i = 0; i < 2; i++) {
                        if (!mazzo.isVuoto()) personaggio.getInventario().aggiungi(mazzo.pesca());
                    }
                } else {
                    Interazioni.corrente().println("Scelta non valida, l'uomo si spazientisce e se ne va!");
                }
            } catch (NumberFormatException e) {
                Interazioni.corrente().println("Input non valido! L'evento termina.");
            }

        } else if (input.equals("2")) {
            Interazioni.corrente().println("\nTenti di trattare! Fai una prova su DOPPIO...");
            FacciaDado faccia = personaggio.lanciaDado();
            Interazioni.corrente().println("Esito lancio: " + faccia.descrivi());

            if (faccia.isDoppio()) {
                Interazioni.corrente().println("SUCCESSO: La tua parlantina ti premia! Peschi 1 Oggetto.");
                if (!mazzo.isVuoto()) personaggio.getInventario().aggiungi(mazzo.pesca());
            } else {
                Interazioni.corrente().println("FALLIMENTO: Il tuo tentativo è inutile, non ottieni nulla.");
            }
        } else {
            Interazioni.corrente().println("Scelta non valida! Prosegui oltre ignorando l'uomo.");
        }
    }
}
