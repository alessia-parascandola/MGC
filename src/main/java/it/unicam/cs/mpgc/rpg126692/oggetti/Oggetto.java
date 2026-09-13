package it.unicam.cs.mpgc.rpg126692.oggetti;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public abstract class Oggetto {
    private final String nome;
    private final String descrizione;
    private final String imagePath;     // Stringa con il percorso del file

    public Oggetto(String nome, String descrizione, String imagePath){
        this.nome = nome;
        this.descrizione = descrizione;
        this.imagePath = imagePath;
    }

    // Metodo astratto: ogni oggetto DEVE definire cosa succede quando viene usato!
    // Gli passiamo il Personaggio che lo usa (e la CartaMostro se serve per le armi)
    public abstract void usa(Personaggio utilizzatore, CartaMostro mostro);

    // Getter
    public String getNome(){
        return nome;
    }
    public String getDescrizione(){
        return descrizione;
    }
    public String getImagePath(){ return imagePath; }
}
