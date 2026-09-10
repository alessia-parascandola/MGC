package it.unicam.cs.mpgc.rpg126692.oggetti.pozioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class PozioneSimbolo extends Pozione {
    private final Simbolo simboloOttenuto;

    public PozioneSimbolo(String nome, String descrizione, Simbolo simboloOttenuto){
        super(nome, descrizione);
        this.simboloOttenuto = simboloOttenuto;
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        if (mostro != null && !mostro.getTracciatoSimboli().isEmpty()) {
            if (mostro.getTracciatoSimboli().contains(simboloOttenuto)) {
                mostro.getTracciatoSimboli().remove(simboloOttenuto);
                System.out.println("Usi " + getNome() + "! Ottieni 1 " + simboloOttenuto + " e completi un simbolo del mostro!");
                super.usa(utilizzatore, mostro); // La scarta
            } else {
                System.out.println("Il mostro non ha il simbolo " + simboloOttenuto + " nel suo tracciato!");
            }
        } else {
            System.out.println("Puoi usare questa pozione solo durante un combattimento o una prova!");
        }
    }
}
