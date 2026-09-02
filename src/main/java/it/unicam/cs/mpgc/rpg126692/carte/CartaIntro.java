package it.unicam.cs.mpgc.rpg126692.carte;

public class CartaIntro extends CartaCapitolo {

    // Richiama il costruttore della superclasse (CartaCapitolo) passando
    // valori fissi per incapsulare il testo narrativo specifico della Carta Intro.
    public CartaIntro() {
        super("La fuga",
                "Dopo anni di prigionia nelle profondità dell'oscuro castello, riesci finalmente a fuggire dalla tua cella. " +
                        "In una piccola stanza di roccia adiacente alle prigioni trovi un vecchio forziere di legno.\n" +
                        "La serratura è aperta... \n" +
                        "Odi l'avvicinarsi di passi. Non puoi fermarti a lungo. Ti dirigi verso l'uscita, sgattaiolando e sparendo nell'oscurità...\n" +
                        "Gira ora la prima CartaCapitolo.");
    }

    // Implementa il metodo astratto esegui() di CartaCapitolo, definendo
    // il comportamento specifico della Carta Intro quando viene giocata (Polimorfismo).
    @Override
    public void esegui() {
        System.out.println("=== " + getNome().toUpperCase() + " ===");
        System.out.println(getDescrizione());
    }
}
