package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.personaggi.PersonaggioFactory;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("        FUGA DAL CASSERO - SELEZIONE     ");
        System.out.println("=========================================");

        // 1. Inizializzazione personaggi tramite la PersonaggioFactory
        List<Personaggio> personaggiDisponibili = List.of(
                PersonaggioFactory.creaCook(),
                PersonaggioFactory.creaAbbot(),
                PersonaggioFactory.creaTailor(),
                PersonaggioFactory.creaMiller(),
                PersonaggioFactory.creaTanner(),
                PersonaggioFactory.creaSmith()
        );

        // 2. Menu di selezione da console
        for (int i = 0; i < personaggiDisponibili.size(); i++) {
            Personaggio p = personaggiDisponibili.get(i);
            System.out.println((i + 1) + ". " + p.getNome() +
                    " [HP: " + p.getHP() + " | FOR: " + p.getForza() +
                    " | AST: " + p.getAstuzia() + " | SAG: " + p.getSaggezza() + "]");
        }

        // --- GESTIONE INPUT PULITA (Senza residui di tasto INVIO) ---
        int scelta = -1;
        while (scelta < 0 || scelta >= personaggiDisponibili.size()) {
            System.out.print("\nScegli il tuo personaggio (1-6): ");
            String input = scanner.nextLine().trim();

            try {
                scelta = Integer.parseInt(input) - 1;
                if (scelta < 0 || scelta >= personaggiDisponibili.size()) {
                    System.out.println("Scelta non valida! Inserisci un numero compreso tra 1 e " + personaggiDisponibili.size() + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input non valido! Per favore inserisci un numero.");
            }
        }

        Personaggio giocatoreScelto = personaggiDisponibili.get(scelta);
        System.out.println("\nHai scelto: " + giocatoreScelto.getNome() + "!");

        // 3. Creazione Mazzi e assemblaggio del Cassero
        MazzoCapitoli mazzoCapitoli = new MazzoCapitoli();
        MazzoOggetti mazzoOggetti = new MazzoOggetti();
        Cassero cassero = new Cassero(mazzoCapitoli);

        // 4. Avvio della partita
        GestorePartita gestore = new GestorePartita(giocatoreScelto, cassero, mazzoOggetti);
        gestore.avviaPartita();
    }
}