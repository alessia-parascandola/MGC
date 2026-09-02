package it.unicam.cs.mpgc.rpg126692.personaggi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoPersonaggio;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;

public class Personaggio {
    private int hp;
    private final String nome;
    private final int forza;
    private final int astuzia;
    private final int saggezza;
    private final DadoPersonaggio dado;

    public Personaggio(int hp, String nome, int forza, int astuzia, int saggezza, DadoPersonaggio dado){
        if(hp <= 0 || hp > 18){
            throw new IllegalArgumentException("Gli HP iniziali devono essere compresi tra 1 e 18!");
        }
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome del personaggio non può essere vuoto!");
        }
        if(dado == null){
            throw new IllegalArgumentException("Il personaggio deve avere un dado associato!");
        }

        this.hp = hp;
        this.nome = nome;
        this.forza = forza;
        this.astuzia = astuzia;
        this.saggezza = saggezza;
        this.dado = dado;
    }

    // Getter
    public int getHP(){ return hp; }
    public String getNome(){ return nome; }
    public int getForza(){ return forza; }
    public int getAstuzia(){ return astuzia; }
    public int getSaggezza(){ return saggezza; }
    public DadoPersonaggio getDado() { return dado; }

    // Metodi per gestire la salute
    public void subisciDanno(int danno){
        hp = Math.max(0, hp - danno);
    }
    public void cura(int quantita){
        hp = Math.min(18, hp + quantita);
    }
    public boolean isSconfitto(){
        return hp <= 0;
    }

    public FacciaDado lanciaDado(){
        return dado.lancia();  // Invoca il metodo del Dado
    }
}
