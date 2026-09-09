package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.Scanner;

public class EventoBarattoUomoNascosto extends CartaEvento{

    public EventoBarattoUomoNascosto() {
        super("Un uomo nascosto si avvicina. Egli osserva prudentemente alle sue \n" +
                "spalle e poi, sussurrando, ti chiede se sei interessato a barattare. \n" +
                "Opzioni:\n" +
                "1. Baratta (Scarta 1 Oggetto e ne peschi 2)\n" +
                "2. Tratta (Prova su DOPPIO: se riesci peschi 1 Oggetto)");
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzo) {
        System.out.println("\n--- EVENTO: L'UOMO NASCOSTO ---");
        System.out.println(getDescrizione());

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nFai la tua scelta (1 o 2): ");
        String input = scanner.nextLine().trim();

        if (input.equals("1")) {
            if (personaggio.getInventario().isVuoto()) {
                System.out.println("Non hai oggetti da barattare! L'uomo scuote la testa e se ne va.");
                return;
            }

            System.out.println("\nQuale oggetto vuoi scartare?");
            for (int i = 0; i < personaggio.getInventario().getOggetti().size(); i++) {
                System.out.println("[" + (i + 1) + "] " + personaggio.getInventario().getOggetti().get(i).getNome());
            }
            System.out.print("> ");

            try {
                int idx = Integer.parseInt(scanner.nextLine().trim()) - 1;
                if (idx >= 0 && idx < personaggio.getInventario().getOggetti().size()) {
                    personaggio.getInventario().scarta(idx);
                    System.out.println("Hai barattato l'oggetto! Peschi 2 nuovi oggetti.");
                    for (int i = 0; i < 2; i++) {
                        if (!mazzo.isVuoto()) personaggio.getInventario().aggiungi(mazzo.pesca());
                    }
                } else {
                    System.out.println("Scelta non valida, l'uomo si spazientisce e se ne va!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido! L'evento termina.");
            }

        } else if (input.equals("2")) {
            System.out.println("\nTenti di trattare! Fai una prova su DOPPIO...");
            FacciaDado faccia = personaggio.lanciaDado();

            if (faccia.isDoppio()) {
                System.out.println("SUCCESSO: La tua parlantina ti premia! Peschi 1 Oggetto.");
                if (!mazzo.isVuoto()) personaggio.getInventario().aggiungi(mazzo.pesca());
            } else {
                System.out.println("FALLIMENTO: Il tuo tentativo è inutile, non ottieni nulla.");
            }
        } else {
            System.out.println("Scelta non valida! Prosegui oltre ignorando l'uomo.");
        }
    }
}
