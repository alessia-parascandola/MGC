package it.unicam.cs.mpgc.rpg126692.dadi;

import java.util.List;

public class DadoPersonaggio extends Dado<FacciaDado>{

    public DadoPersonaggio (List<FacciaDado> facce){
        super(facce);   //Il padre controlla che le facce siano 6
    }

    public List<FacciaDado> getFacce() {
        return facce; // oppure il nome del campo/lista delle facce definito nella tua classe
    }
}
