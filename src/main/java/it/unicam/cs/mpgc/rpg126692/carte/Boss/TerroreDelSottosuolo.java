package it.unicam.cs.mpgc.rpg126692.carte.Boss;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;

import java.util.List;

public class TerroreDelSottosuolo extends CartaBoss{

    public TerroreDelSottosuolo(){
        super("Terrore del Sottosuolo", "Il dolore mi rende soltanto più forte!",
                "Alla fine di ogni turno di combattimento, se il Terrore del Sottosuolo " +
                        "non è stato sconfitto, diventa più forte: lancia un dado Capitolo e aggiungilo " +
                        "ai dadi Capitolo rimanenti.", "/images/cassero/boss_terrore.png",
                List.of(Simbolo.FORZA, Simbolo.ASTUZIA, Simbolo.ASTUZIA, Simbolo.ASTUZIA), 3);
    }

    @Override
    public void applicaRegolaSpecialeFineTurno(DadoCapitolo dadoCapitolo) {
        if (!eSconfitto()){
            Simbolo nuovoSimbolo = dadoCapitolo.lancia();
            getTracciatoSimboli().add(nuovoSimbolo);
            System.out.println("Il Terrore del Sottosuolo si rafforza! Aggiunto simbolo: " + nuovoSimbolo);
        }
    }
}
