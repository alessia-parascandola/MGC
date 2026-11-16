package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class RunaDiRengorn extends Reliquia {

    public RunaDiRengorn(String nome, String descrizione, String imagePath) {
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        Interazioni.corrente().println("Reliquia equipaggiata: " + getNome() + ".");
        Interazioni.corrente().println("Effetto: Puoi cambiare un risultato doppio in un qualsiasi simbolo singolo (gli effetti del doppio non si applicheranno).");
    }

    // Metodo richiamato quando il giocatore decide di convertire il simbolo
    public Simbolo trasformaDoppioInSingolo(Simbolo nuovoSimboloSingolo) {
        Interazioni.corrente().println("Effetto Runa di Rengorn: Il risultato doppio e' stato convertito in un simbolo singolo " + nuovoSimboloSingolo + ".");
        return nuovoSimboloSingolo;
    }
}
