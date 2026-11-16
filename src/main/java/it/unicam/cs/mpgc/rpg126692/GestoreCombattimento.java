package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;

import java.util.List;
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class GestoreCombattimento {
    private final Interazione Interazione = Interazioni.corrente();

    public void combatti(Personaggio personaggio, CartaMostro mostro, DadoCapitolo dadoCapitolo) {
        // 1. Genera SUBITO il tracciato completo dei simboli
        mostro.generaSimboliVita(dadoCapitolo, 1);

        Interazioni.corrente().println("\n=================================");
        if (mostro instanceof CartaBoss boss) {
            Interazioni.corrente().println("=== SCONTRO BOSS: " + boss.getNome().toUpperCase() + " ===");
            Interazioni.corrente().println("REGOLE SPECIALI: " + boss.getRegoleSpeciali());
            boss.applicaEffettoInizioScontro(personaggio);
        } else {
            Interazioni.corrente().println("=== COMBATTIMENTO CONTRO MOSTRO ===");
        }
        Interazioni.corrente().println("Danno del mostro: " + mostro.getDanno() + " HP");
        Interazioni.corrente().println("Tracciato simboli da eliminare: " + mostro.getTracciatoSimboli());
        Interazioni.corrente().println("=================================");

        // 2. Loop del combattimento
        while (!mostro.eSconfitto() && personaggio.eVivo()) {
            Interazioni.corrente().println("\nHP Personaggio: " + personaggio.getHP() + "/" + personaggio.getSaluteMassima());
            Interazioni.corrente().println("Simboli mostro rimasti: " + mostro.getTracciatoSimboli());

            // Menu di scelta turno: consente di usare oggetti prima del lancio
            boolean turnoPronto = false;
            while (!turnoPronto) {
                Interazioni.corrente().println("\n[1] Usa un oggetto dall'inventario");
                Interazioni.corrente().println("[INVIO] Lancia il dado");
                Interazioni.corrente().print("> ");
                String scelta = Interazione.nextLine().trim();

                if (scelta.equals("1")) {
                    gestisciUsoOggettoInCombattimento(personaggio, mostro);
                } else if (scelta.isEmpty()) {
                    turnoPronto = true; // Procede al lancio del dado
                } else {
                    Interazioni.corrente().println("Opzione non valida.");
                }
            }

            // 3. Lancio completo: Chiamata a GestorePartita per attivare Armi, Reliquie e Pozione Fortuna
            FacciaDado facciaOttenuta = GestorePartita.eseguiLancioCompleto(personaggio, Interazione);
            List<Simbolo> simboliUsciti = facciaOttenuta.getSimboli();
            boolean haScudo = facciaOttenuta.haScudo();

            Interazioni.corrente().println("Hai ottenuto i simboli: " + simboliUsciti);
            if (haScudo) {
                Interazioni.corrente().println("[PARATA] La tua faccia di dado contiene uno Scudo!");
            }

            // 4. Confronta i simboli e li rimuove dal mostro
            boolean colpoAndatoASegno = false;
            for (Simbolo s : simboliUsciti) {
                if (mostro instanceof CartaBoss boss) {
                    if (boss.puoRimuovereSimbolo(s)) {
                        boss.rimuoviSimbolo(s);
                        colpoAndatoASegno = true;
                        Interazioni.corrente().println("-> Colpito! Rimosso simbolo: " + s);
                    }
                } else if (mostro.haSimbolo(s)) {
                    mostro.rimuoviSimbolo(s);
                    colpoAndatoASegno = true;
                    Interazioni.corrente().println("-> Colpito! Rimosso simbolo: " + s);
                }
            }

            if (!colpoAndatoASegno) {
                Interazioni.corrente().println("-> Nessun simbolo corrispondente!");
            }

            // 5. Se il mostro è ancora vivo, subisci il danno
            if (!mostro.eSconfitto()) {
                Interazioni.corrente().println("Il mostro ti attacca e prova a infliggere " + mostro.getDanno() + " danni!");

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
            Interazioni.corrente().println("\n*** VITTORIA! Hai sconfitto il mostro! ***");
        }
    }

    private void gestisciUsoOggettoInCombattimento(Personaggio personaggio, CartaMostro mostro) {
        List<Oggetto> oggetti = personaggio.getInventario().getOggetti();
        if (oggetti.isEmpty()) {
            Interazioni.corrente().println("Non hai oggetti nell'inventario!");
            return;
        }

        Interazioni.corrente().println("\n--- OGGETTI DISPONIBILI ---");
        for (int i = 0; i < oggetti.size(); i++) {
            Oggetto obj = oggetti.get(i);
            Interazioni.corrente().println("[" + (i + 1) + "] " + obj.getNome() + " - " + obj.getDescrizione());
        }
        Interazioni.corrente().println("[0] Annulla");
        Interazioni.corrente().print("Scegli un oggetto da usare: ");

        String input = Interazione.nextLine().trim();
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < oggetti.size()) {
                Oggetto daUsare = oggetti.get(idx);
                daUsare.usa(personaggio, mostro);
            }
        } catch (NumberFormatException e) {
            Interazioni.corrente().println("Input non valido.");
        }
    }
}