package it.unicam.cs.mpgc.rpg126692.personaggi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoPersonaggio;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;

import java.util.List;

// Classe usata per definire il personaggio il rispettivo dado
public class PersonaggioFactory {

    public static Personaggio creaCook(){
        DadoPersonaggio dadoCook = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true, "/images/dadi/doppio_astuzia.png"),
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true, "/images/dadi/doppio_forza.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/astuzia.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/cook_dado.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/cook_dado.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/saggezza.png")
        ));

        return new Personaggio(35, "Cook", 4, 3, 1, dadoCook);
    }

    public static Personaggio creaAbbot(){
        DadoPersonaggio dadoAbbot = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true, "/images/dadi/doppio_saggezza.png"),
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true, "/images/dadi/doppio_forza.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/forza.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/abbot_dado.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/abbot_dado.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/astuzia.png")
        ));

        return new Personaggio(35, "Abbot", 3, 1, 4, dadoAbbot);
    }

    public static Personaggio creaTailor(){
        DadoPersonaggio dadoTailor = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true, "/images/dadi/doppio_saggezza.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true, "/images/dadi/doppio_astuzia.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/saggezza.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/tailor_dado.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/tailor_dado.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/forza.png")
        ));

        return new Personaggio(35, "Tailor", 1, 4, 3, dadoTailor);
    }

    public static Personaggio creaMiller(){
        DadoPersonaggio dadoMiller = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true, "/images/dadi/doppio_forza.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true, "/images/dadi/doppio_astuzia.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/astuzia.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/miller_dado.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/miller_dado.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/saggezza.png")
        ));

        return new Personaggio(35, "Miller", 3, 4, 1, dadoMiller);
    }

    public static Personaggio creaTanner(){
        DadoPersonaggio dadoTanner = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.ASTUZIA, Simbolo.ASTUZIA), true, "/images/dadi/doppio_astuzia.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true, "/images/dadi/doppio_saggezza.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/astuzia.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/tanner_dado.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/tanner_dado.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/forza.png")
        ));

        return new Personaggio(35, "Tanner", 1, 3, 4, dadoTanner);
    }

    public static Personaggio creaSmith(){
        DadoPersonaggio dadoSmith = new DadoPersonaggio(List.of(
                new FacciaDado(List.of(Simbolo.FORZA, Simbolo.FORZA), true, "/images/dadi/doppio_forza.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), true, "/images/dadi/doppio_saggezza.png"),
                new FacciaDado(List.of(Simbolo.SAGGEZZA), false, "/images/dadi/saggezza.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/smith_dado.png"),
                new FacciaDado(List.of(Simbolo.FORZA), false, "/images/dadi/smith_dado.png"),
                new FacciaDado(List.of(Simbolo.ASTUZIA), false, "/images/dadi/astuzia.png")
        ));

        return new Personaggio(35, "Smith", 4, 1, 3, dadoSmith);
    }
}
