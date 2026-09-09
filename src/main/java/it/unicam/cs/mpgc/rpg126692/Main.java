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

        // 1. Inizializzazione personaggi tramite la tua PersonaggioFactory
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

        System.out.print("\nScegli il tuo personaggio (1-6): ");
        int scelta = scanner.nextInt() - 1;

        // Validazione input
        while (scelta < 0 || scelta >= personaggiDisponibili.size()) {
            System.out.print("Scelta non valida! Inserisci un numero da 1 a " + personaggiDisponibili.size() + ": ");
            scelta = scanner.nextInt() - 1;
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
