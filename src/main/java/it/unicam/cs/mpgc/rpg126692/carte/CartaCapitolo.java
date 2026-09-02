package it.unicam.cs.mpgc.rpg126692.carte;

public abstract class CartaCapitolo {
    protected final String nome;
    protected final String descrizione;

    public CartaCapitolo(String nome, String descrizione) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Il nome della carta non può essere vuoto!");
        }
        if (descrizione == null || descrizione.isBlank()) {
            throw new IllegalArgumentException("Ci deve essere una narrazione!");
        }

        this.nome = nome;
        this.descrizione = descrizione;
    }

    public String getNome() {
        return nome;
    }
    public String getDescrizione() {
        return descrizione;
    }

     public abstract void esegui();
}

