package it.unicam.cs.mpgc.rpg126692;

/** Porta di interazione condivisa: nessuna dipendenza da JavaFX. */
public interface Interazione {
    String nextLine();
    void print(Object testo);
    default void println(Object testo) { print(testo + System.lineSeparator()); }
    default void println() { println(""); }
}
