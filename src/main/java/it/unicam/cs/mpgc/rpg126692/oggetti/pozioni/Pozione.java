package it.unicam.cs.mpgc.rpg126692.oggetti.pozioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public abstract class Pozione extends Oggetto {

    public Pozione(String nome, String descrizione, String imagePath){
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Hai usato la pozione: " + getNome());

        // Una volta usata, si scarta dall'inventario
        int indice = utilizzatore.getInventario().getOggetti().indexOf(this);
        if (indice != -1) {
            utilizzatore.getInventario().scarta(indice);
        }
    }
}
