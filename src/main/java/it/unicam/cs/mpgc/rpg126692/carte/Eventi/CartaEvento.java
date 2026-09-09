package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.carte.CartaCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public abstract class CartaEvento extends CartaCapitolo {

    public CartaEvento(String descrizione){
        super(descrizione);
    }

    // Per la tipologia 1 (Oggetti Diretti)
    public void applicaEffettoDiretto(Personaggio personaggio, MazzoOggetti mazzoOggetti){}

    // Per la tipologia 2 (Scelte multiple / opzioni)
    public void risolviScelta(int opzione, Personaggio personaggio, MazzoOggetti mazzoOggetti){}

    // Per la tipologia 3 (Prove con tiro dadi)
    public void eseguiProvaDado(Personaggio personaggio, DadoCapitolo dado){}
}
