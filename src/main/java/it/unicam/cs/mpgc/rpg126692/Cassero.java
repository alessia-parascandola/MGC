package it.unicam.cs.mpgc.rpg126692;

import it.unicam.cs.mpgc.rpg126692.carte.CartaBoss;
import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.carte.MazzoCapitoli;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Cassero {
    private final Stack<CartaCapitolo> pilaCarte = new Stack<>();

    public Cassero(MazzoCapitoli mazzoCompleto) {
        costruisciCassero(mazzoCompleto);
    }

    private void costruisciCassero(MazzoCapitoli mazzo) {
        pilaCarte.clear();

        // 1. Inserisce 1 Boss casuale in fondo allo stack
        List<CartaBoss> bossDisponibili = mazzo.getCarteBoss();
        Collections.shuffle(bossDisponibili);
        pilaCarte.push(bossDisponibili.get(0));

        // 2. Mescola e inserisce 15 Carte Capitolo casuali
        List<CartaCapitolo> capitoliDisponibili = mazzo.getCarteCapitolo();
        Collections.shuffle(capitoliDisponibili);

        int numeroCarte = Math.min(15, capitoliDisponibili.size());
        for (int i = 0; i < numeroCarte; i++) {
            pilaCarte.push(capitoliDisponibili.get(i));
        }

        // 3. Inserisce l'Intro in cima allo stack (verrà pescata per prima)
        pilaCarte.push(mazzo.getCartaIntro());
    }

    public CartaCapitolo pescaProssimaCarta() {
        if (!pilaCarte.isEmpty()) {
            return pilaCarte.pop(); // Rimuove la carta pescata per non ripescarla
        }
        return null;
    }

    public boolean haCarte() {
        return !pilaCarte.isEmpty();
    }

    public int carteRimanenti() {
        return pilaCarte.size();
    }
}
