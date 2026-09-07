package it.unicam.cs.mpgc.rpg126692.carte.Boss;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;

public class CartaBoss extends CartaMostro {
    private String nome;
    private String regoleSpeciali;

    public CartaBoss(String nome, String descrizione, String regoleSpeciali, List<Simbolo> simboliFissi, int danno){
        super(descrizione, simboliFissi, danno);
        this.nome = nome;
        this.regoleSpeciali = regoleSpeciali;
    }

    public String getNome() {
        return nome;
    }
    public String getRegoleSpeciali(){ return regoleSpeciali; }

    // Hook 1: Effetto che scatta all'inizio dello scontro (es. L'Oscuro)
    public void applicaEffettoInizioScontro(Personaggio personaggio) {}

    // Hook 2: Regola speciale sul tracciato dei simboli (es. La Sacerdotessa Folle)
    public boolean puoRimuovereSimbolo(Simbolo s) {
        return haSimbolo(s);
    }

    // Hook 3: Regola speciale dopo l'attacco del Boss (es. Il Signore delle Tenebre)
    public void applicaEffettoDopoAttacco(Personaggio personaggio, DadoCapitolo dadoCapitolo) {}

    // Hook 4: Regola di fine turno (es. Terrore del Sottosuolo, Il Mutaforma)
    public void applicaRegolaSpecialeFineTurno(DadoCapitolo dadoCapitolo) {}
}
