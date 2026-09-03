package it.unicam.cs.mpgc.rpg126692.oggetti;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class Scudo extends Oggetto {
    private final int riduzioneDanno;

    public Scudo(String nome, String descrizione, int riduzioneDanno){
        super(nome, descrizione);
        this.riduzioneDanno = riduzioneDanno;
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println(getNome() + " è equipaggiato. Riduce automaticamente i danni subiti!");
    }

    public int getRiduzioneDanno(){
        return riduzioneDanno;
    }
}
