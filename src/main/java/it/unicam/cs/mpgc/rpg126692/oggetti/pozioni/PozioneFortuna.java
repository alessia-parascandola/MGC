package it.unicam.cs.mpgc.rpg126692.oggetti.pozioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

    // FORTUNA LIQUIDA
public class PozioneFortuna extends Pozione {

    public PozioneFortuna(String nome, String descrizione) {
        super(nome, descrizione);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Usi Fortuna liquida! Rilanci il tuo Dado Personaggio!");
        // La logica del rilancio del dado verrà richiamata dal controller di combattimento
        super.usa(utilizzatore, mostro);
    }
}
