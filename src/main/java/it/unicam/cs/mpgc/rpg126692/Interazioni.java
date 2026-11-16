package it.unicam.cs.mpgc.rpg126692;

import java.util.Scanner;
import java.util.function.Supplier;

/** Contesto confinato al thread per adattare gradualmente il modello esistente. */
public final class Interazioni {
    private Interazioni() {}
    private static final Interazione CONSOLE = new Interazione() {
        private final Scanner input = new Scanner(System.in);
        public String nextLine() { return input.nextLine(); }
        public void print(Object testo) { System.out.print(testo); }
    };
    private static final ThreadLocal<Interazione> CORRENTE = new ThreadLocal<>();
    public static Interazione corrente() {
        return CORRENTE.get() == null ? CONSOLE : CORRENTE.get();
    }
    public static void esegui(Interazione interazione, Runnable azione) {
        Interazione precedente = CORRENTE.get();
        CORRENTE.set(java.util.Objects.requireNonNull(interazione));
        try { azione.run(); }
        finally {
            if (precedente == null) CORRENTE.remove(); else CORRENTE.set(precedente);
        }
    }
}
