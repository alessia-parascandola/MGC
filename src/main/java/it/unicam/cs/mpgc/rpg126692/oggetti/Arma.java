package it.unicam.cs.mpgc.rpg126692.oggetti;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class Arma extends Oggetto {
    private int maniRichieste;              // 1 o 2 mani
    private Simbolo simboloRilancio;        // null se non abilita il reroll
    private boolean daDadoCapitoloExtra;    // true per l'ascia bipenne

    public Arma (String nome, String descrizione, int maniRichieste, Simbolo simboloRilancio, boolean daDadoCapitoloExtra){
        super(nome, descrizione);
        this.maniRichieste = maniRichieste;
        this.simboloRilancio = simboloRilancio;
        this.daDadoCapitoloExtra = daDadoCapitoloExtra;
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Hai equipaggiato " + getNome() + "! (" + maniRichieste + " mani occupate)");
    }

    // Getter
    public int getManiRichieste(){
        return maniRichieste;
    }
    public Simbolo getSimboloRilancio(){
        return simboloRilancio;
    }
    public boolean isDaDadoCapitoloExtra(){
        return daDadoCapitoloExtra;
    }
}
