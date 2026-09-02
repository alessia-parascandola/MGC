package it.unicam.cs.mpgc.rpg126692;

import java.util.List;

public class DadoPersonaggio extends Dado<FacciaDado>{

    public DadoPersonaggio (List<FacciaDado> facce){
        super(facce);   //Il padre controlla che le facce siano 6
    }

    // 1. Dado per Cook
    DadoPersonaggio dadoCook = new DadoPersonaggio(List.of(
            new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
            new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false)
    ));

    // 2. Dado per Abbot
    DadoPersonaggio dadoAbbot = new DadoPersonaggio(List.of(
            new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
            new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false)
    ));

    // 3. Dado per Tailor
    DadoPersonaggio dadoTailor = new DadoPersonaggio(List.of(
            new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
            new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false)
    ));

    // 4. Dado per Miller
    DadoPersonaggio dadoMiler = new DadoPersonaggio(List.of(
            new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
            new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false)
    ));

    // 5. Dado per Tanner
    DadoPersonaggio dadoTanner = new DadoPersonaggio(List.of(
            new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
            new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false)
    ));

    // 6. Dado per Smith
    DadoPersonaggio dadoSmith = new DadoPersonaggio(List.of(
            new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
            new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
            new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.FORZA), false),
            new FacciaDado(List.of(Simbolo.ASTUZIA), false)
    ));
}
