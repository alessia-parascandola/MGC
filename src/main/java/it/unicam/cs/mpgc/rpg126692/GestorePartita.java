package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
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

        int numeroTurno = 1;

        // Pesca ed esegue le carte dallo stack Cassero finché ce ne sono
        while (cassero.haCarte()) {
            CartaCapitolo carta = cassero.pescaProssimaCarta();

            mostraDashboard();

            if (carta instanceof CartaBoss) {
                System.out.println("\n=========================================");
                System.out.println("         SCONTRO FINALE CON IL BOSS       ");
                System.out.println("=========================================");
            } else if (numeroTurno > 1) {
                System.out.println("\n---> CAPITOLO " + (numeroTurno - 1) + " / 15 <---");
            }

            // Esecuzione carta
            carta.esegui(giocatore);

            // Controllo sconfitta immediato
            if (giocatore.isSconfitto()) {
                gestisciGameOver();
                return;
            }

            // Ricompensa se si sconfigge un mostro
            if (carta instanceof CartaMostro) {
                CartaMostro cartaMostro = (CartaMostro) carta;
                if (cartaMostro.getTracciatoSimboli().isEmpty()) {
                    assegnaRicompensaOggetto();
                }
            }

            numeroTurno++;
            attendiInvio();
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
        String manoDestra = (!oggetti.isEmpty()) ? oggetti.get(0).getNome() : "Vuota";
        String manoSinistra = (oggetti.size() > 1) ? oggetti.get(1).getNome() : "Vuota";

        System.out.println(" EQUIPAGGIAMENTO:");
        System.out.println("   [Mano Destra]  : " + manoDestra);
        System.out.println("   [Mano Sinistra]: " + manoSinistra);
        System.out.println("======================================================================\n");
    }

    private void assegnaRicompensaOggetto() {
        if (!mazzoOggetti.isVuoto()) {
            Oggetto nuovoOggetto = mazzoOggetti.pesca();
            System.out.println("\n[RICOMPENSA] Hai ottenuto: " + nuovoOggetto.getNome() + "!");
            giocatore.getInventario().aggiungi(nuovoOggetto);
        }
    }

    private void gestisciGameOver() {
        System.out.println("\n=========================================");
        System.out.println("               GAME OVER                 ");
        System.out.println("  Sei caduto tra le ombre del Cassero... ");
        System.out.println("=========================================");
    }

    private void attendiInvio() {
        System.out.println("\n[Premi INVIO per proseguire...]");
        scanner.nextLine();
    }
}
