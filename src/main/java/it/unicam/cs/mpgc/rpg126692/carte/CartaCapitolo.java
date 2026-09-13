package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public abstract class CartaCapitolo {
    protected final String descrizione;
    private final String imagePath;

    public CartaCapitolo(String descrizione, String imagePath) {
        if (descrizione == null || descrizione.isBlank()) {
            throw new IllegalArgumentException("Ci deve essere una narrazione!");
        }

        // this.nome = nome;
        this.descrizione = descrizione;
        this.imagePath = imagePath;
    }

    public String getDescrizione() {
        return descrizione;
    }
    public String getImagePath(){ return imagePath; }

    // Metodo astratto che ogni stanza/evento implementa per agire sul personaggio
    public abstract void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti);}

