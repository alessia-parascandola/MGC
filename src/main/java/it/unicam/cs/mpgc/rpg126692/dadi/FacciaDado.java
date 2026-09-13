package it.unicam.cs.mpgc.rpg126692.dadi;

import java.util.List;

public class FacciaDado {
    // "private" su usa di default sugli attributi delle classi concrete normali per proteggere lo stato interno
    private final List<Simbolo> simboli;
    private final boolean scudo;
    private String imagePath;

    public FacciaDado(List<Simbolo> simboli, boolean scudo, String imagePath){
        //Vincolo 1: la lista deve contenere 1 o 2 simboli
        if (simboli == null || simboli.isEmpty() || simboli.size() >2){
            throw new IllegalArgumentException("Una faccia deve contenere 1 o 2 simboli!");
        }

        //Vincolo 2: lo scudo è presente solo su facce con 2 simboli
        if (scudo && simboli.size() != 2){
            throw new IllegalArgumentException("Lo scudo deve essere presente su una faccia doppia figura!");
        }

        this.simboli = new java.util.ArrayList<>(simboli);
        this.scudo = scudo;
        this.imagePath = imagePath;
    }

    public List<Simbolo> getSimboli(){
        return simboli;
    }

    public boolean haScudo(){
        return scudo;
    }

    public String getImagePath(){ return imagePath; }

    // Restituisce il simbolo primario presente sulla faccia
    public Simbolo getSimboloPrincipale() {
        return simboli.get(0);
    }

    // Un lancio è un doppio se la lista contiene 2 simboli
    public boolean isDoppio() {
        return simboli.size() == 2;
    }
}
