package it.unicam.cs.mpgc.rpg126692.carte.Boss;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;

public class SignoreDelleTenebre extends CartaBoss{

    public SignoreDelleTenebre() {
        super("Il Signore delle Tenebre", "Sarò anche cieco, ma vedo più di quanto tu possa immaginare!",
                "Ogni turno, dopo aver applicato il normale danno causato dal Signore delle Tenebre, " +
                        "il Gargoyle attacca e non può essere bloccato.",
                List.of(Simbolo.FORZA, Simbolo.ASTUZIA, Simbolo.ASTUZIA, Simbolo.SAGGEZZA), 3);
    }

    @Override
    public void applicaEffettoDopoAttacco(Personaggio personaggio, DadoCapitolo dadoCapitolo) {
        System.out.println("Il Gargoyle ti attacca dall'alto! Perdi 1 HP.");
        personaggio.subisciDanno(1, false, false);
    }
}
