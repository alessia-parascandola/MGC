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
import it.unicam.cs.mpgc.rpg126692.Interazione;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

public class GestorePartita {

    private final Personaggio giocatore;
    private final Cassero cassero;
    private final MazzoOggetti mazzoOggetti;
    private final Interazione Interazione;

    public GestorePartita(Personaggio giocatore, Cassero cassero, MazzoOggetti mazzoOggetti) {
        this.giocatore = giocatore;
        this.cassero = cassero;
        this.mazzoOggetti = mazzoOggetti;
        this.Interazione = Interazioni.corrente();
    }

    public void avviaPartita() {
        Interazioni.corrente().println("=========================================");
        Interazioni.corrente().println("      BENVENUTO IN: FUGA DAL CASSERO      ");
        Interazioni.corrente().println("=========================================");

        int numeroStanza = 0;        // Parte da 0 per gestire la carta Intro

        // Pesca ed esegue le carte dallo stack Cassero finché ce ne sono
        while (cassero.haCarte()) {
            CartaCapitolo carta = cassero.pescaProssimaCarta();

            // Reset effetti per turno (es. Frammento della Furia)
            resetEffettiTurno();

            Interazioni.corrente().println("\n======================================================================\n");

            // CASO 1: Carta Intro (Stanza 0)
            if (numeroStanza == 0) {
                Interazioni.corrente().println("---> INTRODUZIONE <---");
                carta.esegui(giocatore, mazzoOggetti);

                Interazioni.corrente().println("\n[Premi INVIO per entrare nel Cassero...]");
                Interazione.nextLine();

                numeroStanza++;
                continue; // Passa subito alla Stanza 1
            }

            // CASO 2: Stanze Normali e Boss (Mostra la Dashboard completa)
            mostraDashboard();

            if (carta instanceof CartaBoss) {
                Interazioni.corrente().println("\n=========================================");
                Interazioni.corrente().println("         SCONTRO FINALE CON IL BOSS       ");
                Interazioni.corrente().println("=========================================");
            } else {
                Interazioni.corrente().println("\n---> STANZA " + numeroStanza + " / 15 <---");
            }

            // Esecuzione carta normale
            carta.esegui(giocatore, mazzoOggetti);

            // Controllo sconfitta immediato
            if (giocatore.isSconfitto()) {
                salvaEsito("SCONFITTA");
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

        salvaEsito("VITTORIA");
        // Se sopravvissuto a tutte le carte compreso il Boss
        Interazioni.corrente().println("\n*****************************************");
        Interazioni.corrente().println("         FUGA RIUSCITA! HAI VINTO!       ");
        Interazioni.corrente().println("*****************************************");
    }

    private void salvaEsito(String esito) {
        try { new StoricoPartite().salva(giocatore.getNome(), esito); }
        catch (java.io.IOException e) { Interazioni.corrente().println("Errore salvataggio: " + e.getMessage()); }
    }
    private void resetEffettiTurno(){
        for (Oggetto obj : giocatore.getInventario().getOggetti()){
            if (obj instanceof FrammentoDellaFuria furia){
                furia.resetTurno();
            }
        }
    }

    private void mostraDashboard() {
        Interazioni.corrente().println("\n======================================================================");
        Interazioni.corrente().println(" PERSONAGGIO: " + giocatore.getNome() + " | HP: [" + giocatore.getHP() + "/" + giocatore.getSaluteMassima() + "]");
        Interazioni.corrente().println(" TRATTI: Possanza: " + giocatore.getPossanza() +
                " | Astuzia: " + giocatore.getAstuzia() +
                " | Sapienza: " + giocatore.getSapienza());

        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();

        // Mostra Nome + Descrizione
        String manoDestra = (!oggetti.isEmpty())
                ? oggetti.get(0).getNome() + " (" + oggetti.get(0).getDescrizione() + ")"
                : "Vuota";
        String manoSinistra = (oggetti.size() > 1)
                ? oggetti.get(1).getNome() + " (" + oggetti.get(1).getDescrizione() + ")"
                : "Vuota";

        Interazioni.corrente().println(" EQUIPAGGIAMENTO:");
        Interazioni.corrente().println("   [Mano Destra]  : " + manoDestra);
        Interazioni.corrente().println("   [Mano Sinistra]: " + manoSinistra);
        Interazioni.corrente().println("======================================================================\n");
    }

    private void assegnaRicompensaOggetto() {
        if (mazzoOggetti.isVuoto()) return;

        Interazioni.corrente().println("\n[Premi INVIO per pescare la tua ricompensa...]");
        Interazione.nextLine();

        Oggetto nuovoOggetto = mazzoOggetti.pesca();
        Interazioni.corrente().println("\n[RICOMPENSA] Hai trovato: " + nuovoOggetto.getNome() + " - " + nuovoOggetto.getDescrizione());

        gestisciAcquisizioneOggetto(giocatore, nuovoOggetto);
    }

    // Metodo helper statico riutilizzabile da qualsiasi carta evento o dinamica di gioco
    public static void gestisciAcquisizioneOggetto(Personaggio giocatore, Oggetto nuovoOggetto) {
        Interazione localScanner = Interazioni.corrente();

        // Ciclo finché l'inventario non è in grado di contenere l'oggetto o finché il giocatore non decide di lasciarlo
        while (!giocatore.getInventario().puoContenere(nuovoOggetto)) {
            List<Oggetto> inventario = giocatore.getInventario().getOggetti();

            if (inventario.isEmpty()) {
                Interazioni.corrente().println("Impossibile equipaggiare l'oggetto.");
                break;
            }

            Interazioni.corrente().println("\nNon hai abbastanza mani libere per equipaggiare " + nuovoOggetto.getNome() + "!");
            Interazioni.corrente().println("Scegli un'azione:");
            Interazioni.corrente().println("[1] Lascia " + nuovoOggetto.getNome() + " a terra");
            Interazioni.corrente().println("[2] Scarta un oggetto per liberare mani");
            Interazioni.corrente().print("> ");

            String scelta = localScanner.nextLine().trim();

            if (scelta.equals("2")) {
                Interazioni.corrente().println("\nQuale oggetto vuoi scartare?");
                for (int i = 0; i < inventario.size(); i++) {
                    Interazioni.corrente().println("[" + (i + 1) + "] " + inventario.get(i).getNome() + " (" + inventario.get(i).getDescrizione() + ")");
                }
                Interazioni.corrente().print("> ");

                try {
                    int idxScartare = Integer.parseInt(localScanner.nextLine().trim()) - 1;
                    if (idxScartare >= 0 && idxScartare < inventario.size()) {
                        Oggetto scartato = giocatore.getInventario().scarta(idxScartare);
                        Interazioni.corrente().println("Hai scartato: " + scartato.getNome());
                    } else {
                        Interazioni.corrente().println("Scelta non valida!");
                    }
                } catch (NumberFormatException e) {
                    Interazioni.corrente().println("Input non valido!");
                }
            } else {
                Interazioni.corrente().println("Hai lasciato a terra " + nuovoOggetto.getNome() + ".");
                return; // Esce senza equipaggiare
            }
        }

        // Quando le mani/spazi sono finalmente sufficienti, aggiunge l'oggetto
        giocatore.getInventario().aggiungi(nuovoOggetto);
        Interazioni.corrente().println("Hai equipaggiato con successo: " + nuovoOggetto.getNome() + "!");
    }

    // Menù Esplorazione
    private void mostraMenuInterazione() {
        while (true) {
            Interazioni.corrente().println("\n---------------------------------");
            Interazioni.corrente().println("Scegli un'azione:");
            Interazioni.corrente().println("[1] Usa un oggetto consumabile");
            Interazioni.corrente().println("[INVIO] Accedi alla prossima stanza");
            Interazioni.corrente().print("> ");

            String scelta = Interazione.nextLine().trim();

            if (scelta.equals("1")) {
                gestisciUsoOggetto();
                mostraDashboard(); // Aggiorna la vista dopo aver usato l'oggetto
            } else if (scelta.isEmpty()) {
                break; // Prosegue al turno successivo
            } else {
                Interazioni.corrente().println("Scelta non valida!");
            }
        }
    }

    private void gestisciUsoOggetto() {
        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();

        if (oggetti.isEmpty()) {
            Interazioni.corrente().println("Non hai oggetti nell'inventario!");
            return;
        }

        Interazioni.corrente().println("\n--- USA OGGETTO ---");
        for (int i = 0; i < oggetti.size(); i++) {
            Oggetto obj = oggetti.get(i);
            Interazioni.corrente().println("[" + (i + 1) + "] " + obj.getNome() + " - " + obj.getDescrizione());
        }
        Interazioni.corrente().println("[0] Annulla");
        Interazioni.corrente().print("Scegli l'oggetto da usare: ");

        String input = Interazione.nextLine().trim();
        try {
            int indice = Integer.parseInt(input) - 1;
            if (indice >= 0 && indice < oggetti.size()) {
                Oggetto daUsare = oggetti.get(indice);
                daUsare.usa(giocatore, null);
            }
        } catch (NumberFormatException e) {
            Interazioni.corrente().println("Inserisci un numero valido.");
        }
    }

    private void gestisciGameOver() {
        Interazioni.corrente().println("\n=========================================");
        Interazioni.corrente().println("               GAME OVER                 ");
        Interazioni.corrente().println("  Sei caduto tra le ombre del Cassero... ");
        Interazioni.corrente().println("=========================================");
    }

    public static void gestisciEffettiReliquie(Personaggio giocatore, FacciaDado esito, Interazione Interazione) {
        List<Oggetto> inventario = giocatore.getInventario().getOggetti();

        // Controlla se la faccia uscitia ha almeno 2 simboli (un DOPPIO)
        boolean eUnDoppio = esito.getSimboli().size() >= 2;

        for (Oggetto obj : inventario) {

            // 1. VERSETTI CURATIVI (Interattiva se si fa un DOPPIO)
            if (obj instanceof VersettiCurativi versetti && esito.isDoppio()) {
                Interazioni.corrente().println("\n[RELIQUIA: Versetti Curativi] Hai ottenuto: " + esito.descrivi());
                Interazioni.corrente().println("Vuoi recuperare 1 HP? [1 = Si / 2 = No]");
                Interazioni.corrente().print("> ");
                if (Interazione.nextLine().trim().equals("1")) {
                    versetti.applicaCura(giocatore);
                }
            }

            // 2. RUNA DI RENGORN (Interattiva se si fa un DOPPIO)
            if (obj instanceof RunaDiRengorn runa && esito.isDoppio()) {
                Interazioni.corrente().println("\n[RELIQUIA: Runa di Rengorn] Hai ottenuto: " + esito.descrivi());
                Interazioni.corrente().println("Vuoi convertire il DOPPIO in un simbolo SINGOLO a tua scelta? (Perderai i benefici del doppio) [1 = Si / 2 = No]");
                Interazioni.corrente().print("> ");
                if (Interazione.nextLine().trim().equals("1")) {
                    Interazioni.corrente().println("Scegli il simbolo desiderato:");
                    Interazioni.corrente().println("[1] POSSANZA | [2] ASTUZIA | [3] SAPIENZA");
                    Interazioni.corrente().print("> ");
                    String sc = Interazione.nextLine().trim();
                    Simbolo scelto = sc.equals("1") ? Simbolo.POSSANZA : (sc.equals("2") ? Simbolo.ASTUZIA : Simbolo.SAPIENZA);

                    // Sostituisce i simboli nell'esito corrente
                    esito.getSimboli().clear();
                    esito.getSimboli().add(scelto);
                    Interazioni.corrente().println("Simbolo convertito in: " + scelto);
                }
            }

            // 3. FRAMMENTO DELLA FURIA (Interattiva, 1 volta per turno su DOPPIO)
            if (obj instanceof FrammentoDellaFuria furia && esito.isDoppio()) {
                if (furia.canUsa()) {
                    Interazioni.corrente().println("\n[RELIQUIA: Frammento della Furia] Hai ottenuto: " + esito.descrivi());
                    Interazioni.corrente().println("Vuoi attivare la Furia per rilanciare il dado e sommare entrambi i risultati? [1 = Si / 2 = No]");
                    Interazioni.corrente().print("> ");
                    if (Interazione.nextLine().trim().equals("1")) {
                        furia.attivaEffetto();
                        FacciaDado secondoLancio = giocatore.lanciaDado();
                        Interazioni.corrente().println("Secondo lancio ottenuto: " + secondoLancio.descrivi());

                        // FUSIONE FONDAMENTALE: aggiunge i simboli del secondo tiro al tiro attuale!
                        esito.getSimboli().addAll(secondoLancio.getSimboli());
                    }
                }
            }
        }
    }

    // Gestisce il tiro del dado verificando se l'arma equipaggiata permette il rilancio sul simbolo uscito
    public static FacciaDado gestisciLancioConArma(Personaggio giocatore) {
        Interazione scan = Interazioni.corrente();
        FacciaDado esito = giocatore.lanciaDado();

        for (Oggetto obj : giocatore.getInventario().getOggetti()) {
            if (obj instanceof Arma arma) {
                Simbolo simboloTarget = arma.getSimboloRilancio();

                // Controlla se il simbolo dell'arma è contenuto nei simboli usciti sul dado!
                if (simboloTarget != null && esito.getSimboli().contains(simboloTarget)) {
                    Interazioni.corrente().println("\n[ABILITÀ ARMA: " + arma.getNome() + "]");
                    Interazioni.corrente().println("Risultato ottenuto: " + esito.descrivi());
                    Interazioni.corrente().println("Vuoi usare l'effetto di " + arma.getNome() + " per lanciare di nuovo il dado? [1 = Si / 2 = No]");
                    Interazioni.corrente().print("> ");

                    String risposta = scan.nextLine().trim();
                    if (risposta.equals("1")) {
                        Interazioni.corrente().println("Rilanci il dado grazie a " + arma.getNome() + "...");
                        FacciaDado secondoLancio = giocatore.lanciaDado();
                        scan.println("[1] Primo risultato: " + esito.getSimboli()
                                + (esito.haScudo() ? " (Scudo)" : ""));
                        scan.println("[2] Secondo risultato: " + secondoLancio.getSimboli()
                                + (secondoLancio.haScudo() ? " (Scudo)" : ""));
                        while (true) {
                            scan.println("Quale risultato vuoi applicare? [1 / 2]");
                            String scelta = scan.nextLine().trim();
                            if (scelta.equals("1")) return giocatore.selezionaRisultatoDado(esito);
                            if (scelta.equals("2")) return giocatore.selezionaRisultatoDado(secondoLancio);
                            scan.println("Scelta non valida: inserisci 1 o 2.");
                        }
                    }
                }
            }
        }
        return esito;
    }

    public static FacciaDado gestisciUsoFortunaLiquida(Personaggio giocatore, FacciaDado primoLancio, Interazione scan) {
        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();

        for (int i = 0; i < oggetti.size(); i++) {
            if (oggetti.get(i) instanceof PozioneFortuna pozione) {
                Interazioni.corrente().println("\n[POZIONE DISPONIBILE] Hai " + pozione.getNome() + "!");
                Interazioni.corrente().println("Risultato attuale: " + primoLancio.getSimboli());
                Interazioni.corrente().println("Vuoi usarla per scartare questo tiro e tirare di nuovo? [1 = Si / 2 = No]");
                Interazioni.corrente().print("> ");

                if (scan.nextLine().trim().equals("1")) {
                    pozione.usa(giocatore, null); // Messaggio + scarto dall'inventario
                    Interazioni.corrente().println("Lanci nuovamente il dado personaggio...");
                    return giocatore.lanciaDado(); // Ritorna direttamente il NUOVO lancio!
                }
            }
        }
        return primoLancio; // Se non la usi o non ce l'hai, tiene il primo lancio
    }

    // Metodo CENTRALIZZATO per tutti i lanci di dado del gioco
    public static FacciaDado eseguiLancioCompleto(Personaggio giocatore, Interazione Interazione) {
        // 1. Lancia il dado e gestisce i reroll dell'Arma
        FacciaDado esito = gestisciLancioConArma(giocatore);

        // 2. Controlla e attiva l'eventuale Pozione Fortuna Liquida
        esito = gestisciUsoFortunaLiquida(giocatore, esito, Interazione);

        // 3. Controlla e attiva le Reliquie (Versetti, Runa, Furia)
        gestisciEffettiReliquie(giocatore, esito, Interazione);

        return esito;
    }

}
