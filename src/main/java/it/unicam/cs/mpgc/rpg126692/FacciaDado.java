package it.unicam.cs.mpgc.rpg126692;

public class FacciaDado {
    // "private" su usa di default sugli attributi delle classi concrete normali per proteggere lo stato interno
    private final Simbolo simbolo;
    private final int danno;
    private final boolean scudo;

    public FacciaDado(Simbolo simbolo, int danno, boolean scudo){
        this.simbolo = simbolo;
        this.danno = danno;
        this.scudo = scudo;
    }

    //Metodi scritti in camelCase
    public Simbolo getSimbolo(){
        return simbolo;
    }
    public int getDanno(){
        return danno;
    }
    public boolean haScudo(){
        return scudo;
    }
}
