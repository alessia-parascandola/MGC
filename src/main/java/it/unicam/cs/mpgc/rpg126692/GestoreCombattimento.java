package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;

import java.util.List;
import java.util.Scanner;

public class GestoreCombattimento {
    private final Scanner scanner = new Scanner(System.in);

    public void combatti(Personaggio personaggio, CartaMostro mostro, DadoCapitolo dadoCapitolo) {
        // 1. Genera SUBITO il tracciato completo dei simboli
        mostro.generaSimboliVita(dadoCapitolo, 1);

        System.out.println("\n=================================");
        if (mostro instanceof CartaBoss boss) {
            System.out.println("=== SCONTRO BOSS: " + boss.getNome().toUpperCase() + " ===");
            System.out.println("REGOLE SPECIALI: " + boss.getRegoleSpeciali());
            boss.applicaEffettoInizioScontro(personaggio);
        } else {
            System.out.println("=== COMBATTIMENTO CONTRO MOSTRO ===");
        }
        System.out.println("Danno del mostro: " + mostro.getDanno() + " HP");
        System.out.println("Tracciato simboli da eliminare: " + mostro.getTracciatoSimboli());
        System.out.println("=================================");

        // 2. Loop del combattimento
        while (!mostro.eSconfitto() && personaggio.eVivo()) {
            System.out.println("\nHP Personaggio: " + personaggio.getHP() + "/" + personaggio.getSaluteMassima());
            System.out.println("Simboli mostro rimasti: " + mostro.getTracciatoSimboli());

            // Menu di scelta turno: consente di usare oggetti prima del lancio
            boolean turnoPronto = false;
            while (!turnoPronto) {
                System.out.println("\n[1] Usa un oggetto dall'inventario");
                System.out.println("[INVIO] Lancia il dado");
                System.out.print("> ");
                String scelta = scanner.nextLine().trim();

                if (scelta.equals("1")) {
                    gestisciUsoOggettoInCombattimento(personaggio, mostro);
                } else if (scelta.isEmpty()) {
                    turnoPronto = true; // Procede al lancio del dado
                } else {
                    System.out.println("Opzione non valida.");
                }
            }

            // 3. Lancio completo: Chiamata a GestorePartita per attivare Armi, Reliquie e Pozione Fortuna
            FacciaDado facciaOttenuta = GestorePartita.eseguiLancioCompleto(personaggio, scanner);
            List<Simbolo> simboliUsciti = facciaOttenuta.getSimboli();
            boolean haScudo = facciaOttenuta.haScudo();

            System.out.println("Hai ottenuto i simboli: " + simboliUsciti);
            if (haScudo) {
                System.out.println("[PARATA] La tua faccia di dado contiene uno Scudo!");
            }

            // 4. Confronta i simboli e li rimuove dal mostro
            boolean colpoAndatoASegno = false;
            for (Simbolo s : simboliUsciti) {
                if (mostro instanceof CartaBoss boss) {
                    if (boss.puoRimuovereSimbolo(s)) {
                        boss.rimuoviSimbolo(s);
                        colpoAndatoASegno = true;
                        System.out.println("-> Colpito! Rimosso simbolo: " + s);
                    }
                } else if (mostro.haSimbolo(s)) {
                    mostro.rimuoviSimbolo(s);
                    colpoAndatoASegno = true;
                    System.out.println("-> Colpito! Rimosso simbolo: " + s);
                }
            }

            if (!colpoAndatoASegno) {
                System.out.println("-> Nessun simbolo corrispondente!");
            }

            // 5. Se il mostro è ancora vivo, subisci il danno
            if (!mostro.eSconfitto()) {
                System.out.println("Il mostro ti attacca e prova a infliggere " + mostro.getDanno() + " danni!");

                // Passa 'haScudo' al parametro 'paratoDaDado' per azzerare il danno!
                personaggio.subisciDanno(mostro.getDanno(), false, haScudo);

                if (mostro instanceof CartaBoss boss) {
                    boss.applicaEffettoDopoAttacco(personaggio, dadoCapitolo);
                    boss.applicaRegolaSpecialeFineTurno(dadoCapitolo);
                }
            }
        }

        // 6. Esito dello scontro
        if (personaggio.eVivo()) {
            System.out.println("\n*** VITTORIA! Hai sconfitto il mostro! ***");
        }
    }

    private void gestisciUsoOggettoInCombattimento(Personaggio personaggio, CartaMostro mostro) {
        List<Oggetto> oggetti = personaggio.getInventario().getOggetti();
        if (oggetti.isEmpty()) {
            System.out.println("Non hai oggetti nell'inventario!");
            return;
        }

        System.out.println("\n--- OGGETTI DISPONIBILI ---");
        for (int i = 0; i < oggetti.size(); i++) {
            Oggetto obj = oggetti.get(i);
            System.out.println("[" + (i + 1) + "] " + obj.getNome() + " - " + obj.getDescrizione());
        }
        System.out.println("[0] Annulla");
        System.out.print("Scegli un oggetto da usare: ");

        String input = scanner.nextLine().trim();
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < oggetti.size()) {
                Oggetto daUsare = oggetti.get(idx);
                daUsare.usa(personaggio, mostro);
            }
        } catch (NumberFormatException e) {
            System.out.println("Input non valido.");
        }
    }
}