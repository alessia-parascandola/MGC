package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class VersettiCurativi extends Reliquia {

    public VersettiCurativi (String nome, String descrizione, String imagePath){
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        Interazioni.corrente().println("Reliquia equipaggiata: " + getNome() + ".");
        Interazioni.corrente().println("Effetto: Quando ottieni un risultato doppio, puoi curare di 1 HP te stesso o un alleato.");
    }

    // Metodo specifico richiamato dal controller al lancio dei dadi
    public void applicaCura(Personaggio bersaglio) {
        if (bersaglio != null) {
            bersaglio.cura(1);
            Interazioni.corrente().println("Effetto Versetti Curativi attivato! " + bersaglio.getNome() + " recupera 1 HP.");
        }
    }
}
