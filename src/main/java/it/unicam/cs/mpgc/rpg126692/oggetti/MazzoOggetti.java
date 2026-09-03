package it.unicam.cs.mpgc.rpg126692.oggetti;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;

import java.util.ArrayList;
import java.util.Collections;

public class MazzoOggetti {
    private ArrayList<Oggetto> carte = new ArrayList<>();

    public MazzoOggetti(){
        // --- Cibi ---
        carte.add(new Cibo("Mela parzialmente marcia.", "Mela bacata. Cura 1 HP.", 1));
        carte.add(new Cibo("Mela parzialmente marcia.", "Mela bacata. Cura 1 HP.", 1));
        carte.add(new Cibo("Mela parzialmente marcia.", "Mela bacata. Cura 1 HP.", 1));
        carte.add(new Cibo("Mela parzialmente marcia.", "Mela bacata. Cura 1 HP.", 1));
        carte.add(new Cibo("Mela parzialmente marcia.", "Mela bacata. Cura 1 HP.", 1));
        carte.add(new Cibo("Mela parzialmente marcia.", "Mela bacata. Cura 1 HP.", 1));
        carte.add(new Cibo("Pagnotta rafferma.", "Pane duro. Cura 2 HP.", 2));
        carte.add(new Cibo("Pagnotta rafferma.", "Pane duro. Cura 2 HP.", 2));
        carte.add(new Cibo("Pagnotta rafferma.", "Pane duro. Cura 2 HP.", 2));
        carte.add(new Cibo("Formaggio verminoso.", "Formaggio forte. Cura 1 HP.", 1));

        // --- Armi ---
        carte.add(new Arma("Lama deteriorata.", "Rilancia il dado se esce SAGGEZZA.", 1, Simbolo.SAGGEZZA, false));
        carte.add(new Arma("Randello deformato", "Rilancia il dado se esce FORZA.", 1, Simbolo.FORZA, false));
        carte.add(new Arma("Flagello arrugginito", "Rilancia il dado se esce ASTUZIA.", 1, Simbolo.ASTUZIA, false));
        carte.add(new Arma("Ascia bipenne", "Occupazione: 2 mani. Lanci un Dado Capitolo extra ad ogni attacco.", 2, null, true));

        // --- Pozioni ---
        carte.add(new PozioneSimbolo("Distillato di sapienza.","Scarta per ottenere un risultato SAGGEZZA.", Simbolo.SAGGEZZA));
        carte.add(new PozioneSimbolo("Mistura dell'astuzia.", "Scarta per ottenere un risultato in ASTUZIA.", Simbolo.ASTUZIA));
        carte.add(new PozioneSimbolo("Infuso della possanza.", "Scarta per ottenere un risultato in FORZA.", Simbolo.FORZA));
        carte.add(new PozioneEvasione("Evasione effervecente.", "Scarta per non perdere HP."));
        carte.add(new PozioneEvasione("Evasione effervecente.", "Scarta per non perdere HP."));
        carte.add(new PozioneEvasione("Evasione effervecente.", "Scarta per non perdere HP."));
        carte.add(new PozioneEvasione("Evasione effervecente.", "Scarta per non perdere HP."));
        carte.add(new PozioneFortuna("Fortuna liquida.", "Scarta per tirare di nuovo il dado personaggio."));
        carte.add(new PozioneFortuna("Fortuna liquida.", "Scarta per tirare di nuovo il dado personaggio."));
        carte.add(new PozioneFortuna("Fortuna liquida.", "Scarta per tirare di nuovo il dado personaggio."));
        carte.add(new PozioneFortuna("Fortuna liquida.", "Scarta per tirare di nuovo il dado personaggio."));
        carte.add(new PozioneFortuna("Fortuna liquida.", "Scarta per tirare di nuovo il dado personaggio."));
        carte.add(new PozioneIntuizione("Elisir dell'intuizione.", "Scarta per cambiare la faccia di un dado capitolo del mostro."));
        carte.add(new PozioneIntuizione("Elisir dell'intuizione.", "Scarta per cambiare la faccia di un dado capitolo del mostro."));
        carte.add(new PozioneIntuizione("Elisir dell'intuizione.", "Scarta per cambiare la faccia di un dado capitolo del mostro."));

        // --- Scudi ---
        carte.add(new Scudo("Scudo marcito.", "Quando stai per perdere punti vita, riduci la perdita di 1.", 1));
        carte.add(new Scudo("Scudo marcito.", "Quando stai per perdere punti vita, riduci la perdita di 1.", 1));

        // --- Reliquie ---

        // Mescoliamo il mazzo all'inizio del gioco
        Collections.shuffle(carte);
    }

    // Metodo per pescare una carta in cima
    public Oggetto pesca() {
        if (!carte.isEmpty()) {
            return carte.remove(0);     // Rimuove e restituisce la prima carta
        }
        System.out.println("Gli oggetti sono finiti!");
        return null;
    }
}
