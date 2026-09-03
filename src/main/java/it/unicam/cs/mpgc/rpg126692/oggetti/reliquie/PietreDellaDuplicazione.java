package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class PietreDellaDuplicazione extends Reliquia {

    public PietreDellaDuplicazione(String nome, String descrizione) {
        super(nome, descrizione);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Reliquia equipaggiata: " + getNome() + ".");
        System.out.println("Effetto passivo attivo: Ogni risultato singolo sui dadi vale come due singoli della stessa caratteristica.");
    }
}
