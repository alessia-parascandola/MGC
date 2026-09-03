package it.unicam.cs.mpgc.rpg126692.oggetti.reliquie;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class RunaDiRengorn extends Reliquia {

    public RunaDiRengorn(String nome, String descrizione) {
        super(nome, descrizione);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        System.out.println("Reliquia equipaggiata: " + getNome() + ".");
        System.out.println("Effetto: Puoi cambiare un risultato doppio in un qualsiasi simbolo singolo (gli effetti del doppio non si applicheranno).");
    }

    // Metodo richiamato quando il giocatore decide di convertire il simbolo
    public Simbolo trasformaDoppioInSingolo(Simbolo nuovoSimboloSingolo) {
        System.out.println("Effetto Runa di Rengorn: Il risultato doppio e' stato convertito in un simbolo singolo " + nuovoSimboloSingolo + ".");
        return nuovoSimboloSingolo;
    }
}
