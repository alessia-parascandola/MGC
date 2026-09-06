package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public abstract class CartaCapitolo {
    protected final String descrizione;

    public CartaCapitolo(String descrizione) {
        if (descrizione == null || descrizione.isBlank()) {
            throw new IllegalArgumentException("Ci deve essere una narrazione!");
        }

        // this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getDescrizione() {
        return descrizione;
    }

    // Metodo astratto che ogni stanza/evento implementa per agire sul personaggio
     public abstract void esegui(Personaggio personaggio);
}

