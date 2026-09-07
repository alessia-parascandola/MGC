package it.unicam.cs.mpgc.rpg126692.carte.Boss;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import java.util.List;

public class Mutaforma extends CartaBoss{

    public Mutaforma() {
        super("Il Mutaforma", "Posso assumere le sembianze della tua più grande paura!",
                "I risultati doppi contano come un solo colpo andato a segno contro il Mutaforma. " +
                        "Alla fine di ogni turno di combattimento, se il Mutaforma non è stato sconfitto, " +
                        "cambia aspetto: rilancia tutti i suoi dadi Capitolo rimanenti.",
                List.of(Simbolo.FORZA, Simbolo.FORZA, Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), 3);
    }

    @Override
    public void applicaRegolaSpecialeFineTurno(DadoCapitolo dadoCapitolo) {
        if (!eSconfitto()) {
            int numeroSimboliRimanenti = getTracciatoSimboli().size();
            getTracciatoSimboli().clear();

            System.out.println("Il Mutaforma cambia aspetto! Rilancio dei simboli rimanenti:");
            for (int i = 0; i < numeroSimboliRimanenti; i++) {
                Simbolo nuovoSimbolo = dadoCapitolo.lancia();
                getTracciatoSimboli().add(nuovoSimbolo);
            }
            System.out.println("Nuovo tracciato: " + getTracciatoSimboli());
        }
    }
}
