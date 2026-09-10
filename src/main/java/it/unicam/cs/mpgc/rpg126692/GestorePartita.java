package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.Arma;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneFortuna;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.FrammentoDellaFuria;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.RunaDiRengorn;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.VersettiCurativi;
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

            // Reset effetti per turno (es. Frammento della Furia)
            resetEffettiTurno();

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

    private void resetEffettiTurno(){
        for (Oggetto obj : giocatore.getInventario().getOggetti()){
            if (obj instanceof FrammentoDellaFuria furia){
                furia.resetTurno();
            }
        }
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

        // Ciclo finché l'inventario non è in grado di contenere l'oggetto o finché il giocatore non decide di lasciarlo
        while (!giocatore.getInventario().puoContenere(nuovoOggetto)) {
            List<Oggetto> inventario = giocatore.getInventario().getOggetti();

            if (inventario.isEmpty()) {
                System.out.println("Impossibile equipaggiare l'oggetto.");
                break;
            }

            System.out.println("\nNon hai abbastanza mani libere per equipaggiare " + nuovoOggetto.getNome() + "!");
            System.out.println("Scegli un'azione:");
            System.out.println("[1] Lascia " + nuovoOggetto.getNome() + " a terra");
            System.out.println("[2] Scarta un oggetto per liberare mani");
            System.out.print("> ");

            String scelta = localScanner.nextLine().trim();

            if (scelta.equals("2")) {
                System.out.println("\nQuale oggetto vuoi scartare?");
                for (int i = 0; i < inventario.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + inventario.get(i).getNome() + " (" + inventario.get(i).getDescrizione() + ")");
                }
                System.out.print("> ");

                try {
                    int idxScartare = Integer.parseInt(localScanner.nextLine().trim()) - 1;
                    if (idxScartare >= 0 && idxScartare < inventario.size()) {
                        Oggetto scartato = giocatore.getInventario().scarta(idxScartare);
                        System.out.println("Hai scartato: " + scartato.getNome());
                    } else {
                        System.out.println("Scelta non valida!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Input non valido!");
                }
            } else {
                System.out.println("Hai lasciato a terra " + nuovoOggetto.getNome() + ".");
                return; // Esce senza equipaggiare
            }
        }

        // Quando le mani/spazi sono finalmente sufficienti, aggiunge l'oggetto
        giocatore.getInventario().aggiungi(nuovoOggetto);
        System.out.println("Hai equipaggiato con successo: " + nuovoOggetto.getNome() + "!");
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

    public static void gestisciEffettiReliquie(Personaggio giocatore, FacciaDado esito, Scanner scanner) {
        List<Oggetto> inventario = giocatore.getInventario().getOggetti();

        for (Oggetto obj : inventario) {

            // 1. VERSETTI CURATIVI (Interattiva se si fa un DOPPIO)
            if (obj instanceof VersettiCurativi versetti && esito.isDoppio()) {
                System.out.println("\n[RELIQUIA: Versetti Curativi] Hai ottenuto un DOPPIO!");
                System.out.println("Vuoi recuperare 1 HP? [1 = Si / 2 = No]");
                System.out.print("> ");
                if (scanner.nextLine().trim().equals("1")) {
                    versetti.applicaCura(giocatore);
                }
            }

            // 2. RUNA DI RENGORN (Interattiva se si fa un DOPPIO)
            if (obj instanceof RunaDiRengorn runa && esito.isDoppio()) {
                System.out.println("\n[RELIQUIA: Runa di Rengorn] Hai ottenuto un DOPPIO!");
                System.out.println("Vuoi convertire il DOPPIO in un simbolo SINGOLO a tua scelta? (Perderai i benefici del doppio) [1 = Si / 2 = No]");
                System.out.print("> ");
                if (scanner.nextLine().trim().equals("1")) {
                    System.out.println("Scegli il simbolo desiderato:");
                    System.out.println("[1] FORZA | [2] ASTUZIA | [3] SAGGEZZA");
                    System.out.print("> ");
                    String sc = scanner.nextLine().trim();
                    Simbolo scelto = sc.equals("1") ? Simbolo.FORZA : (sc.equals("2") ? Simbolo.ASTUZIA : Simbolo.SAGGEZZA);

                    runa.trasformaDoppioInSingolo(scelto);
                    // Aggiorna visivamente o logicamente il simbolo dell'esito
                }
            }

            // 3. FRAMMENTO DELLA FURIA (Interattiva, 1 volta per turno su DOPPIO)
            if (obj instanceof FrammentoDellaFuria furia && esito.isDoppio()) {
                if (furia.canUsa()) {
                    System.out.println("\n[RELIQUIA: Frammento della Furia] Hai ottenuto un DOPPIO!");
                    System.out.println("Vuoi attivare la Furia per rilanciare il dado e sommare entrambi i risultati? [1 = Si / 2 = No]");
                    System.out.print("> ");
                    if (scanner.nextLine().trim().equals("1")) {
                        furia.attivaEffetto();
                        FacciaDado secondoLancio = giocatore.lanciaDado();
                        System.out.println("Secondo lancio ottenuto: " + secondoLancio.getSimboloPrincipale());
                        // Entrambi i risultati ora si sommano nella risoluzione del combattimento/prova
                    }
                }
            }
        }
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

    public static FacciaDado gestisciUsoFortunaLiquida(Personaggio giocatore, FacciaDado primoLancio, Scanner scan) {
        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();

        for (int i = 0; i < oggetti.size(); i++) {
            if (oggetti.get(i) instanceof PozioneFortuna pozione) {
                System.out.println("\n[POZIONE DISPONIBILE] Hai " + pozione.getNome() + "!");
                System.out.println("Risultato attuale: " + primoLancio.getSimboloPrincipale());
                System.out.println("Vuoi usarla per scartare questo tiro e tirare di nuovo? [1 = Si / 2 = No]");
                System.out.print("> ");

                if (scan.nextLine().trim().equals("1")) {
                    pozione.usa(giocatore, null); // Messaggio + scarto dall'inventario
                    System.out.println("Lanci nuovamente il dado personaggio...");
                    return giocatore.lanciaDado(); // Ritorna direttamente il NUOVO lancio!
                }
            }
        }
        return primoLancio; // Se non la usi o non ce l'hai, tiene il primo lancio
    }

    // Metodo CENTRALIZZATO per tutti i lanci di dado del gioco
    public static FacciaDado eseguiLancioCompleto(Personaggio giocatore, Scanner scanner) {
        // 1. Lancia il dado e gestisce i reroll dell'Arma
        FacciaDado esito = gestisciLancioConArma(giocatore);

        // 2. Controlla e attiva le Reliquie (Versetti, Runa, Furia)
        gestisciEffettiReliquie(giocatore, esito, scanner);

        // 3. Controlla e attiva le Reliquie (Versetti, Runa, Furia)
        gestisciEffettiReliquie(giocatore, esito, scanner);

        return esito;
    }

}
