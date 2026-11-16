package it.unicam.cs.mpgc.rpg126692.oggetti;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneEvasione;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneFortuna;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneIntuizione;
import it.unicam.cs.mpgc.rpg126692.oggetti.pozioni.PozioneSimbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.FrammentoDellaFuria;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.PietreDellaDuplicazione;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.RunaDiRengorn;
import it.unicam.cs.mpgc.rpg126692.oggetti.reliquie.VersettiCurativi;

import java.util.ArrayList;
import java.util.Collections;

public class MazzoOggetti {
    private ArrayList<Oggetto> carte = new ArrayList<>();

    public MazzoOggetti(){
        // --- Cibi ---
        carte.add(new Cibo("Mela parzialmente marcia", "Mela bacata. Cura 1 HP.", "/images/oggetti/mela_marcia.png", 1));
        carte.add(new Cibo("Mela parzialmente marcia", "Mela bacata. Cura 1 HP.", "/images/oggetti/mela_marcia.png", 1));
        carte.add(new Cibo("Mela parzialmente marcia", "Mela bacata. Cura 1 HP.", "/images/oggetti/mela_marcia.png", 1));
        carte.add(new Cibo("Mela parzialmente marcia", "Mela bacata. Cura 1 HP.", "/images/oggetti/mela_marcia.png", 1));
        carte.add(new Cibo("Mela parzialmente marcia", "Mela bacata. Cura 1 HP.", "/images/oggetti/mela_marcia.png", 1));
        carte.add(new Cibo("Mela parzialmente marcia", "Mela bacata. Cura 1 HP.", "/images/oggetti/mela_marcia.png", 1));
        carte.add(new Cibo("Pagnotta rafferma", "Pane duro. Cura 2 HP.", "/images/oggetti/pagnotta.png", 2));
        carte.add(new Cibo("Pagnotta rafferma", "Pane duro. Cura 2 HP.", "/images/oggetti/pagnotta.png", 2));
        carte.add(new Cibo("Pagnotta rafferma", "Pane duro. Cura 2 HP.", "/images/oggetti/pagnotta.png", 2));
        carte.add(new Cibo("Formaggio verminoso", "Formaggio forte. Cura 1 HP.", "/images/oggetti/formaggio.png", 1));

        // --- Armi ---
        carte.add(new Arma("Lama deteriorata", "Se esce SAPIENZA, puoi rilanciare il dado e scegliere quale dei due risultati applicare.", "/images/oggetti/lama.png", 1, Simbolo.SAPIENZA, false));
        carte.add(new Arma("Randello deformato", "Se esce POSSANZA, puoi rilanciare il dado e scegliere quale dei due risultati applicare.", "/images/oggetti/randello.png", 1, Simbolo.POSSANZA, false));
        carte.add(new Arma("Flagello arrugginito", "Se esce ASTUZIA, puoi rilanciare il dado e scegliere quale dei due risultati applicare.", "/images/oggetti/flagello.png", 1, Simbolo.ASTUZIA, false));
        carte.add(new Arma("Ascia bipenne", "Occupazione: 2 mani. Lanci un Dado Capitolo extra ad ogni attacco.", "/images/oggetti/ascia.png", 2, null, true));

        // --- Pozioni ---
        carte.add(new PozioneSimbolo("Distillato di sapienza","Scarta per ottenere un risultato SAPIENZA.", "/images/oggetti/distillato.png", Simbolo.SAPIENZA));
        carte.add(new PozioneSimbolo("Mistura dell'astuzia", "Scarta per ottenere un risultato in ASTUZIA.", "/images/oggetti/mistura.png", Simbolo.ASTUZIA));
        carte.add(new PozioneSimbolo("Infuso della possanza", "Scarta per ottenere un risultato in POSSANZA.", "/images/oggetti/infuso.png", Simbolo.POSSANZA));
        carte.add(new PozioneEvasione("Evasione effervecente", "Scarta per non perdere HP.", "/images/oggetti/evasione.png"));
        carte.add(new PozioneEvasione("Evasione effervecente", "Scarta per non perdere HP.", "/images/oggetti/evasione.png"));
        carte.add(new PozioneEvasione("Evasione effervecente", "Scarta per non perdere HP.", "/images/oggetti/evasione.png"));
        carte.add(new PozioneEvasione("Evasione effervecente", "Scarta per non perdere HP.", "/images/oggetti/evasione.png"));
        carte.add(new PozioneFortuna("Fortuna liquida", "Scarta per tirare di nuovo il dado personaggio.", "/images/oggetti/fortuna.png"));
        carte.add(new PozioneFortuna("Fortuna liquida", "Scarta per tirare di nuovo il dado personaggio.", "/images/oggetti/fortuna.png"));
        carte.add(new PozioneFortuna("Fortuna liquida", "Scarta per tirare di nuovo il dado personaggio.", "/images/oggetti/fortuna.png"));
        carte.add(new PozioneFortuna("Fortuna liquida", "Scarta per tirare di nuovo il dado personaggio.", "/images/oggetti/fortuna.png"));
        carte.add(new PozioneFortuna("Fortuna liquida", "Scarta per tirare di nuovo il dado personaggio.", "/images/oggetti/fortuna.png"));
        carte.add(new PozioneIntuizione("Elisir dell'intuizione", "Scarta per cambiare la faccia di un dado capitolo del mostro.", "/images/oggetti/elisir.png"));
        carte.add(new PozioneIntuizione("Elisir dell'intuizione", "Scarta per cambiare la faccia di un dado capitolo del mostro.", "/images/oggetti/elisir.png"));
        carte.add(new PozioneIntuizione("Elisir dell'intuizione", "Scarta per cambiare la faccia di un dado capitolo del mostro.", "/images/oggetti/elisir.png"));

        // --- Scudi ---
        carte.add(new Scudo("Scudo marcito", "Quando stai per perdere punti vita, riduci la perdita di 1.", "/images/oggetti/scudo.png", 1));
        carte.add(new Scudo("Scudo marcito", "Quando stai per perdere punti vita, riduci la perdita di 1.", "/images/oggetti/scudo.png", 1));

        // --- Reliquie ---
        carte.add(new VersettiCurativi("Versetti curativi", "Quando ottieni un risultato doppio con un dado, puoi dare un punto vita a qualsiasi personaggio.", "/images/oggetti/versetti.jpg"));
        carte.add(new FrammentoDellaFuria("Frammento della furia", "Una volta per turno di combattimento, quando ottieni un doppio con un dado, puoi tirarlo ancora e applicare entrambi i risultati.", "/images/oggetti/frammento.jpg"));
        carte.add(new PietreDellaDuplicazione("Pietre della duplicazione", "Quando ottieni un risultato singolo con un dado, questo vale come due singoli di quella caratteristica.", "/images/oggetti/pietre.jpg"));
        carte.add(new RunaDiRengorn("Runa di Rengorn","Quando ottieni un risultato doppio con un dado, puoi cambiarlo in un qualsiasi risultato singolo.", "/images/oggetti/runa.jpg"));

        // Mescoliamo il mazzo all'inizio del gioco
        Collections.shuffle(carte);
    }

    // Metodo per pescare una carta in cima
    public Oggetto pesca() {
        if (!carte.isEmpty()) {
            return carte.remove(0);     // Rimuove e restituisce la prima carta
        }
        Interazioni.corrente().println("Gli oggetti sono finiti!");
        return null;
    }

    public boolean isVuoto() {
        return carte.isEmpty();
    }
}
