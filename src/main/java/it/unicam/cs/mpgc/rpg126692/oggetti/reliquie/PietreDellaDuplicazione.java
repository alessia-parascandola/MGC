package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class PietreDellaDuplicazione extends Reliquia {

    public PietreDellaDuplicazione(String nome, String descrizione, String imagePath) {
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        Interazioni.corrente().println("Reliquia equipaggiata: " + getNome() + ".");
        Interazioni.corrente().println("Effetto passivo attivo: Ogni risultato singolo sui dadi vale come due singoli della stessa caratteristica.");
    }
}
