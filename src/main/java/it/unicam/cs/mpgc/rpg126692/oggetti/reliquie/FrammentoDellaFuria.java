package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class FrammentoDellaFuria extends Reliquia {
    private boolean usataInQuestoTurno = false;

    public FrammentoDellaFuria(String nome, String descrizione, String imagePath) {
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        Interazioni.corrente().println("Reliquia equipaggiata: " + getNome() + ".");
        Interazioni.corrente().println("Effetto: Una volta per turno, quando ottieni un doppio, puoi ritirare il dado e applicare entrambi i risultati.");
    }

    public boolean canUsa() {
        return !usataInQuestoTurno;
    }

    public void attivaEffetto() {
        this.usataInQuestoTurno = true;
        Interazioni.corrente().println("Effetto Frammento della Furia attivato per questo turno!");
    }

    public void resetTurno() {
        this.usataInQuestoTurno = false;
    }
}
