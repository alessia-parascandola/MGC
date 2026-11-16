package it.unicam.cs.mpgc.rpg126692.oggetti.pozioni;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

    // ELISIR DELL'INTUIZIONE
public class PozioneIntuizione extends Pozione {

    public PozioneIntuizione(String nome, String descrizione, String imagePath) {
        super(nome, descrizione, imagePath);
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        var interazione = Interazioni.corrente();
        if (mostro == null || mostro.eSconfitto()) {
            interazione.println("L'Elisir dell'intuizione richiede un mostro in combattimento. La pozione rimane nell'inventario.");
            return;
        }
        var simboli = mostro.getTracciatoSimboli();
        int indice;
        while (true) {
            interazione.println("Scegli il simbolo del mostro da cambiare:");
            for (int i = 0; i < simboli.size(); i++) interazione.println("[" + (i + 1) + "] " + simboli.get(i));
            interazione.println("[0] Annulla");
            try {
                indice = Integer.parseInt(interazione.nextLine().trim()) - 1;
                if (indice == -1) return;
                if (indice >= 0 && indice < simboli.size()) break;
            } catch (NumberFormatException errore) { /* Chiede nuovamente una scelta valida. */ }
            interazione.println("Scelta non valida.");
        }
        var valori = it.unicam.cs.mpgc.rpg126692.dadi.Simbolo.values();
        while (true) {
            interazione.println("Scegli la nuova faccia:");
            for (int i = 0; i < valori.length; i++) interazione.println("[" + (i + 1) + "] " + valori[i]);
            interazione.println("[0] Annulla");
            try {
                int scelta = Integer.parseInt(interazione.nextLine().trim()) - 1;
                if (scelta == -1) return;
                if (scelta >= 0 && scelta < valori.length) {
                    var precedente = simboli.set(indice, valori[scelta]);
                    interazione.println("Simbolo del mostro cambiato: " + precedente + " -> " + valori[scelta]);
                    super.usa(utilizzatore, mostro);
                    return;
                }
            } catch (NumberFormatException errore) { /* Chiede nuovamente una scelta valida. */ }
            interazione.println("Scelta non valida.");
        }
    }
}