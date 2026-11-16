package it.unicam.cs.mpgc.rpg126692.carte.Boss;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;

import java.util.List;

public class SacerdotessaFolle extends CartaBoss{

    public SacerdotessaFolle(){
        super("La Sacerdotessa Folle", "Non puoi nuocermi. Osserva la devozione dei miei seguaci!",
                "La Sacerdotessa è protetta dai suoi tirapiedi invasati (la riga di dadi Capitolo con " +
                        "il simbolo POSSANZA). Tutti questi dadi devono essere rimossi prima di poter attaccare la " +
                        "Sacerdotessa (i dadi Capitolo posizionati dietro la linea con il simbolo POSSANZA).",
                "/images/cassero/boss_sacerdotessa.png",
                List.of(Simbolo.POSSANZA, Simbolo.POSSANZA, Simbolo.POSSANZA, Simbolo.SAPIENZA), 3);
    }

    @Override
    public boolean puoRimuovereSimbolo(Simbolo s) {
        // Se c'è ancora almeno un simbolo POSSANZA nel tracciato, non si possono eliminare altri simboli
        if (haSimbolo(Simbolo.POSSANZA) && s != Simbolo.POSSANZA) {
            Interazioni.corrente().println("I tirapiedi protettori (POSSANZA) ti impediscono di colpire gli altri simboli!");
            return false;
        }
        return haSimbolo(s);
    }
}
