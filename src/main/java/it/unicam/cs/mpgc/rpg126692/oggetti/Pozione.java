package it.unicam.cs.mpgc.rpg126692.oggetti;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public abstract class Pozione extends Oggetto {

    public Pozione(String nome, String descrizione){
        super(nome, descrizione);
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
