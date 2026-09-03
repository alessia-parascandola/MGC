package it.unicam.cs.mpgc.rpg126692.oggetti;

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Oggetto> oggetti = new ArrayList<>();
    private static final int MAX_MANI = 2;      // Vincolo di 2 mani massimo

    // Calcola quante mani sono attualmente occupate
    public int getManiOccupate(){
        int maniUsate = 0;
        for (Oggetto obj : oggetti){
            if (obj instanceof Arma){
                maniUsate += ((Arma) obj).getManiRichieste();
            }
            else {
                // Gli altri oggetti (cibo, pozioni) occupano 1 mano finchè li trasporti
                maniUsate += 1;
            }
        }
        return maniUsate;
    }

    // Calcola quanti slot mano sono ancora liberi
    public int getManiLibere() {
        return MAX_MANI - getManiOccupate();
    }

    // Controlla se un nuovo oggetto può essere impugnato senza superare le 2 mani
    public boolean puoContenere(Oggetto nuovoOggetto){
        int maniRichieste = 1;
        if (nuovoOggetto instanceof Arma){
            maniRichieste = ((Arma) nuovoOggetto).getManiRichieste();
        }
        return (getManiOccupate() + maniRichieste) <= MAX_MANI;
    }

    // Aggiunge un oggetto (se c'è posto)
    public boolean aggiungi(Oggetto nuovoOggetto) {
        if (puoContenere(nuovoOggetto)) {
            oggetti.add(nuovoOggetto);
            System.out.println(nuovoOggetto.getNome() + " aggiunto all'inventario!");
            return true;
        } else {
            System.out.println("Non hai abbastanza mani libere per impugnare " + nuovoOggetto.getNome() + "!");
            return false;
        }
    }

    // Rimuove e scarta un oggetto dato il suo indice
    public Oggetto scarta(int indice) {
        if (indice >= 0 && indice < oggetti.size()) {
            Oggetto rimossa = oggetti.remove(indice);
            System.out.println("Hai scartato: " + rimossa.getNome());
            return rimossa;
        }
        return null;
    }

    // Mostra il contenuto delle mani in console
    public void mostra() {
        System.out.println("\n--- INVENTARIO (" + getManiOccupate() + "/" + MAX_MANI + " Mani Occupate) ---");
        if (oggetti.isEmpty()) {
            System.out.println("Nessun oggetto in mano.");
        } else {
            for (int i = 0; i < oggetti.size(); i++) {
                System.out.println((i + 1) + ". " + oggetti.get(i).getNome() + " - " + oggetti.get(i).getDescrizione());
            }
        }
    }

    // L'inventario scansiona se tra le carte in mano c'è un'istanza di scudo
    public int getRiduzioneDannoTotale() {
        int riduzione = 0;
        for (Oggetto obj : oggetti) {
            if (obj instanceof Scudo) {
                riduzione += ((Scudo) obj).getRiduzioneDanno();
            }
        }
        return riduzione;
    }

    // Getter
    public List<Oggetto> getOggetti() {
        return oggetti;
    }
    public boolean isVuoto() {
        return oggetti.isEmpty();
    }
}
