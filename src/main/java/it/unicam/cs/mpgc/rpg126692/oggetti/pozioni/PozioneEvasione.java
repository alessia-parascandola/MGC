package it.unicam.cs.mpgc.rpg126692.oggetti.pozioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

    // EVASIONE EFFERVESCENTE
public class PozioneEvasione extends Pozione {

    public PozioneEvasione(String nome, String descrizione, String imagePath) {
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Usi Evasione effervescente! Annulli completamente il danno subìto!");
        super.usa(utilizzatore, mostro);    // Richiama il padre per scartarla dall'inventario
    }
}