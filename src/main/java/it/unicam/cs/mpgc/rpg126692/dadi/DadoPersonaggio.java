package it.unicam.cs.mpgc.rpg126692.dadi;

import java.util.List;

public class DadoPersonaggio extends Dado<FacciaDado>{

    public DadoPersonaggio (List<FacciaDado> facce){
        super(facce);   //Il padre controlla che le facce siano 6
    }

    @Override public FacciaDado lancia() { FacciaDado f = super.lancia(); return new FacciaDado(f.getSimboli(), f.haScudo(), f.getImagePath()); }
    public List<FacciaDado> getFacce() {
        return facce; // oppure il nome del campo/lista delle facce definito nella tua classe
    }
}
