package it.unicam.cs.mpgc.rpg126692;

import java.util.List;

public class PersonaggioFactory {
    // Classe usata per collegare il personaggio al rispettivo dado

    public static Personaggio creaCook(){
        DadoPersonaggio dadoCook = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false)
        ));

        return new Personaggio(18, "Cook", 4, 3, 1, dadoCook);
    }

    public static Personaggio creaAbbot(){
        DadoPersonaggio dadoAbbot = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false)
        ));

        return new Personaggio(18, "Abbot", 3, 1, 4, dadoAbbot);
    }

    public static Personaggio creaTailor(){
        DadoPersonaggio dadoTailor = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false)
        ));

        return new Personaggio(18, "Tailor", 1, 4, 3, dadoTailor);
    }

    public static Personaggio creaMiller(){
        DadoPersonaggio dadoMiller = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false)
        ));

        return new Personaggio(18, "Miller", 3, 4, 1, dadoMiller);
    }

    public static Personaggio creaTanner(){
        DadoPersonaggio dadoTanner = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true),
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false)
        ));

        return new Personaggio(18, "Tanner", 1, 3, 4, dadoTanner);
    }

    public static Personaggio creaSmith(){
        DadoPersonaggio dadoSmith = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true),
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.FORZA), false),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false)
        ));

        return new Personaggio(18, "Smith", 4, 1, 3, dadoSmith);
    }
}
