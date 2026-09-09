package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.Boss.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.carte.CartaIntro;
import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GestorePartita {

    private final Personaggio giocatore;
    private final CartaIntro intro;
    private final List<CartaCapitolo> mazzoCapitoli; // Le 15 carte mescolate
    private final CartaBoss boss;
    private final MazzoOggetti mazzoOggetti;
    private final Scanner scanner;

    public GestorePartita(Personaggio giocatore, CartaIntro intro, List<CartaCapitolo> capitoli, CartaBoss boss, MazzoOggetti mazzoOggetti) {
        this.giocatore = giocatore;
        this.intro = intro;
        this.mazzoCapitoli = capitoli;
        this.boss = boss;
        this.mazzoOggetti = mazzoOggetti;
        this.scanner = new Scanner(System.in);

        // Preparazione mazzo Cassero: mescola le 15 carte capitolo
        Collections.shuffle(this.mazzoCapitoli);
    }

    public void avviaPartita() {
        System.out.println("=========================================");
        System.out.println("      BENVENUTO IN: FUGA DAL CASSERO      ");
        System.out.println("=========================================");

        // 1. Mostra Intro
        intro.esegui(giocatore);
        attendiInvio();

        // 2. Ciclo del mazzo Cassero (15 Carte Capitolo)
        int numeroTurno = 1;
        for (CartaCapitolo carta : mazzoCapitoli) {
            mostraDashboard();
            System.out.println("\n---> CAPITOLO " + numeroTurno + " / 15 <---");

            // Esecuzione della carta
            carta.esegui(giocatore);

            // Controllo sconfitta immediato
            if (giocatore.isSconfitto()) {
                gestisciGameOver();
                return;
            }

            // Controlla se la carta è un mostro ed è stato sconfitto
            if (carta instanceof CartaMostro) {
                CartaMostro cartaMostro = (CartaMostro) carta;

                // Se il tracciato dei simboli è vuoto, il mostro è stato sconfitto!
                if (cartaMostro.getTracciatoSimboli().isEmpty()) {
                    assegnaRicompensaOggetto();
                }
            }

            numeroTurno++;
            attendiInvio();
        }

        // 3. Scontro Finale con il Boss
        mostraDashboard();
        System.out.println("\n=========================================");
        System.out.println("         SCONTRO FINALE CON IL BOSS       ");
        System.out.println("=========================================");
        boss.esegui(giocatore);

        if (giocatore.isSconfitto()) {
            gestisciGameOver();
        } else {
            System.out.println("\n*****************************************");
            System.out.println("         FUGA RIUSCITA! HAI VINTO!       ");
            System.out.println("*****************************************");
        }
    }

    // Metodo per rendere SEMPRE VISIBILI le statistiche e l'inventario
    private void mostraDashboard() {
        System.out.println("\n======================================================================");
        System.out.println(" PERSONAGGIO: " + giocatore.getNome() + " | HP: [" + giocatore.getHP() + "/18]");
        System.out.println(" TRATTI: Forza: " + giocatore.getForza() +
                " | Astuzia: " + giocatore.getAstuzia() +
                " | Saggezza: " + giocatore.getSaggezza());

        List<Oggetto> oggetti = giocatore.getInventario().getOggetti();
        String manoDestra = (oggetti.size() > 0) ? oggetti.get(0).getNome() : "Vuota";
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
