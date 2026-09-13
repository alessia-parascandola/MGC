package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class CartaIntro extends CartaCapitolo {

    // Richiama il costruttore della superclasse (CartaCapitolo) passando
    // valori fissi per incapsulare il testo narrativo specifico della Carta Intro.
    public CartaIntro() {
        super("Dopo anni di prigionia nelle profondità dell'oscuro Cassero, riesci finalmente a fuggire dalla tua cella. \n" +
                        "In una piccola stanza di roccia adiacente alle prigioni trovi un vecchio forziere di legno.\n" +
                        "La serratura è aperta... \n" +
                        "Odi l'avvicinarsi di passi. Non puoi fermarti a lungo. Ti dirigi verso l'uscita, sgattaiolando e sparendo nell'oscurità...\n" +
                        "Gira ora la prima CartaCapitolo.",  "/images/fronte_cartaIntro.png");
    }

    // Implementa il metodo astratto esegui() di CartaCapitolo, definendo
    // il comportamento specifico della Carta Intro quando viene giocata (Polimorfismo).
    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println(getDescrizione());
    }
}
