package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;

public class CartaBoss extends CartaMostro {
    private String nome;

    public CartaBoss(String nome, String descrizione, List<Simbolo> simboliFissi, int danno){
        super(descrizione, simboliFissi, danno);
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println("=== SCONTRO FINALE: " + nome + " ===");
        System.out.println(getDescrizione());
        System.out.println("Danno del Boss: " + getDanno() + " HP");
        System.out.println("Tracciato simboli per la vittoria finale: " + getTracciatoSimboli());
    }
}
