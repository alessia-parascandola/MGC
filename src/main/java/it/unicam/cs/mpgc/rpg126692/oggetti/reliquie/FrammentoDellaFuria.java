package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class FrammentoDellaFuria extends Reliquia {
    private boolean usataInQuestoTurno = false;

    public FrammentoDellaFuria(String nome, String descrizione) {
        super(nome, descrizione);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Reliquia equipaggiata: " + getNome() + ".");
        System.out.println("Effetto: Una volta per turno, quando ottieni un doppio, puoi ritirare il dado e applicare entrambi i risultati.");
    }

    public boolean canUsa() {
        return !usataInQuestoTurno;
    }

    public void attivaEffetto() {
        this.usataInQuestoTurno = true;
        System.out.println("Effetto Frammento della Furia attivato per questo turno!");
    }

    public void resetTurno() {
        this.usataInQuestoTurno = false;
    }
}
