package it.unicam.cs.mpgc.rpg126692.oggetti.pozioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

    // ELISIR DELL'INTUIZIONE
public class PozioneIntuizione extends Pozione {

    public PozioneIntuizione(String nome, String descrizione, String imagePath) {
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Usi Elisir dell'intuizione! Puoi scegliere di modificare un dado capitolo del mostro!");
        // La scelta di quale dado cambiare viene gestita nel controller
        super.usa(utilizzatore, mostro);
    }
}