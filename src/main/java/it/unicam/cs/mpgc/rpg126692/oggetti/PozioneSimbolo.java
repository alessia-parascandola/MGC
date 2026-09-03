package it.unicam.cs.mpgc.rpg126692.oggetti;

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
    public void usa(Personaggio utilizzatore, CartaMostro mostro){
        if (mostro != null && mostro.haSimbolo(simboloOttenuto)) {
            mostro.rimuoviSimbolo(simboloOttenuto);
            System.out.println("Usi " + getNome() + "! Ottieni un risultato " + simboloOttenuto + " e rimuovi un dado al mostro!");
            super.usa(utilizzatore, mostro);
        } else {
            System.out.println("Non puoi usare questa pozione ora: il mostro non ha il simbolo " + simboloOttenuto + "!");
        }
    }
}
