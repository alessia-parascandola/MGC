package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.Arma;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;
import java.util.Scanner;

public class GestorePartita {

    private final Personaggio giocatore;
    private final Cassero cassero;
    private final MazzoOggetti mazzoOggetti;
    private final Scanner scanner;

    public GestorePartita(Personaggio giocatore, Cassero cassero, MazzoOggetti mazzoOggetti) {
        this.giocatore = giocatore;
        this.cassero = cassero;
        this.mazzoOggetti = mazzoOggetti;
        this.scanner = new Scanner(System.in);
    }

    public void avviaPartita() {
        System.out.println("=========================================");
        System.out.println("      BENVENUTO IN: FUGA DAL CASSERO      ");
        System.out.println("=========================================");

        int numeroStanza = 0;        // Parte da 0 per gestire la carta Intro

        // Pesca ed esegue le carte dallo stack Cassero finché ce ne sono
        while (cassero.haCarte()) {
            CartaCapitolo carta = cassero.pescaProssimaCarta();

            System.out.println("\n======================================================================\n");

            // CASO 1: Carta Intro (Stanza 0)
            if (numeroStanza == 0) {
                System.out.println("---> INTRODUZIONE <---");

                carta.esegui(giocatore, mazzoOggetti);

                System.out.println("\n[Premi INVIO per entrare nel Cassero...]");
                scanner.nextLine();

                numeroStanza++;
                continue; // Passa subito alla Stanza 1
            }

            // CASO 2: Stanze Normali e Boss (Mostra la Dashboard completa)
            mostraDashboard();

            if (carta instanceof CartaBoss) {
                System.out.println("\n=========================================");
                System.out.println("         SCONTRO FINALE CON IL BOSS       ");
                System.out.println("=========================================");
            } else {
                System.out.println("\n---> STANZA " + numeroStanza + " / 15 <---");
            }

            // Esecuzione carta normale
            carta.esegui(giocatore, mazzoOggetti);

            // Controllo sconfitta immediato
            if (giocatore.isSconfitto()) {
                gestisciGameOver();
                return;
            }

            // Ricompensa se si sconfigge un mostro
            if (carta instanceof CartaMostro cartaMostro) {
                if (cartaMostro.getTracciatoSimboli().isEmpty()) {
                    assegnaRicompensaOggetto();
                }
            }

            numeroStanza++;

            // Menù di interazione tra una stanza e l'altra (solo dalla stanza 1 in poi)
            if (cassero.haCarte() && giocatore.eVivo()) {
                mostraMenuInterazione();
            }
        }

        // Se sopravvissuto a tutte le carte compreso il Boss
        System.out.println("\n*****************************************");
        System.out.println("         FUGA RIUSCITA! HAI VINTO!       ");
        System.out.println("*****************************************");
    }

    private void mostraDashboard() {
        System.out.println("\n======================================================================");
        System.out.println(" PERSONAGGIO: " + giocatore.getNome() + " | HP: [" + giocatore.getHP() + "/18]");
        System.out.println(" TRATTI: Forza: " + giocatore.getForza() +
                " | Astuzia: " + giocatore.getAstuzia() +
                " | Saggezza: " + giocatore.getSaggezza());

        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();

        // Mostra Nome + Descrizione
        String manoDestra = (!oggetti.isEmpty())
                ? oggetti.get(0).getNome() + " (" + oggetti.get(0).getDescrizione() + ")"
                : "Vuota";
        String manoSinistra = (oggetti.size() > 1)
                ? oggetti.get(1).getNome() + " (" + oggetti.get(1).getDescrizione() + ")"
                : "Vuota";

        System.out.println(" EQUIPAGGIAMENTO:");
        System.out.println("   [Mano Destra]  : " + manoDestra);
        System.out.println("   [Mano Sinistra]: " + manoSinistra);
        System.out.println("======================================================================\n");
    }

    private void assegnaRicompensaOggetto() {
        if (mazzoOggetti.isVuoto()) return;

        System.out.println("\n[Premi INVIO per pescare la tua ricompensa...]");
        scanner.nextLine();

        Oggetto nuovoOggetto = mazzoOggetti.pesca();
        System.out.println("\n[RICOMPENSA] Hai trovato: " + nuovoOggetto.getNome() + " - " + nuovoOggetto.getDescrizione());

        gestisciAcquisizioneOggetto(giocatore, nuovoOggetto);
    }

    // Metodo helper statico riutilizzabile da qualsiasi carta evento o dinamica di gioco
    public static void gestisciAcquisizioneOggetto(Personaggio giocatore, Oggetto nuovoOggetto) {
        Scanner localScanner = new Scanner(System.in);
        List<Oggetto> inventario = giocatore.getInventario().getOggetti();

        if (inventario.size() >= 2) {
            System.out.println("\nIl tuo inventario è pieno! Scegli un'azione:");
            System.out.println("[1] Mantieni i tuoi oggetti attuali (" + inventario.get(0).getNome() + ", " + inventario.get(1).getNome() + ") e lascia " + nuovoOggetto.getNome());
            System.out.println("[2] Prendi " + nuovoOggetto.getNome() + " e scarta uno dei vecchi");
            System.out.print("> ");

            String scelta = localScanner.nextLine().trim();

            if (scelta.equals("2")) {
                // Sottomenu: Scegli quale dei due vecchi oggetti sostituire
                System.out.println("\nQuale oggetto vuoi sostituire?");
                System.out.println("[1] Sostituisci " + inventario.get(0).getNome() + " (Mano Destra)");
                System.out.println("[2] Sostituisci " + inventario.get(1).getNome() + " (Mano Sinistra)");
                System.out.print("> ");

                String subScelta = localScanner.nextLine().trim();
                int idxScartare = subScelta.equals("2") ? 1 : 0;

                Oggetto scartato = inventario.get(idxScartare);
                giocatore.getInventario().scarta(idxScartare);
                giocatore.getInventario().aggiungi(nuovoOggetto);
                System.out.println("Hai scartato " + scartato.getNome() + " ed equipaggiato " + nuovoOggetto.getNome() + "!");
            } else {
                System.out.println("Hai lasciato a terra " + nuovoOggetto.getNome() + " e mantenuto i tuoi oggetti.");
            }
        } else {
            giocatore.getInventario().aggiungi(nuovoOggetto);
            // Qui viene aggiunto se hai spazio nell'inventario
        }
    }

    // Menù Esplorazione
    private void mostraMenuInterazione() {
        while (true) {
            System.out.println("\n---------------------------------");
            System.out.println("Scegli un'azione:");
            System.out.println("[1] Usa un oggetto consumabile");
            System.out.println("[INVIO] Accedi alla prossima stanza");
            System.out.print("> ");

            String scelta = scanner.nextLine().trim();

            if (scelta.equals("1")) {
                gestisciUsoOggetto();
                mostraDashboard(); // Aggiorna la vista dopo aver usato l'oggetto
            } else if (scelta.isEmpty()) {
                break; // Prosegue al turno successivo
            } else {
                System.out.println("Scelta non valida!");
            }
        }
    }

    private void gestisciUsoOggetto() {
        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();

        if (oggetti.isEmpty()) {
            System.out.println("Non hai oggetti nell'inventario!");
            return;
        }

        System.out.println("\n--- USA OGGETTO ---");
        for (int i = 0; i < oggetti.size(); i++) {
            Oggetto obj = oggetti.get(i);
            System.out.println("[" + (i + 1) + "] " + obj.getNome() + " - " + obj.getDescrizione());
        }
        System.out.println("[0] Annulla");
        System.out.print("Scegli l'oggetto da usare: ");

        String input = scanner.nextLine().trim();
        try {
            int indice = Integer.parseInt(input) - 1;
            if (indice >= 0 && indice < oggetti.size()) {
                Oggetto daUsare = oggetti.get(indice);
                daUsare.usa(giocatore, null);
            }
        } catch (NumberFormatException e) {
            System.out.println("Inserisci un numero valido.");
        }
    }

    private void gestisciGameOver() {
        System.out.println("\n=========================================");
        System.out.println("               GAME OVER                 ");
        System.out.println("  Sei caduto tra le ombre del Cassero... ");
        System.out.println("=========================================");
    }

    // Gestisce il tiro del dado verificando se l'arma equipaggiata permette il rilancio sul simbolo uscito
    public static FacciaDado gestisciLancioConArma(Personaggio giocatore) {
        Scanner scan = new Scanner(System.in);
        FacciaDado esito = giocatore.lanciaDado();

        for (Oggetto obj : giocatore.getInventario().getOggetti()) {
            if (obj instanceof Arma arma) {
                Simbolo simboloTarget = arma.getSimboloRilancio();

                // Se l'esito corrisponde al simbolo di rilancio dell'arma equipaggiata
                if (simboloTarget != null && esito.getSimboloPrincipale() == simboloTarget) {
                    System.out.println("\n[ABILITÀ ARMA: " + arma.getNome() + "]");
                    System.out.println("È uscito il simbolo " + simboloTarget + "!");
                    System.out.println("Vuoi usare l'effetto di " + arma.getNome() + " per lanciare di nuovo il dado? [1 = Si / 2 = No]");
                    System.out.print("> ");

                    String risposta = scan.nextLine().trim();
                    if (risposta.equals("1")) {
                        System.out.println("Rilanci il dado grazie a " + arma.getNome() + "...");
                        return giocatore.lanciaDado();
                    }
                }
            }
        }

        return esito;
    }
}
