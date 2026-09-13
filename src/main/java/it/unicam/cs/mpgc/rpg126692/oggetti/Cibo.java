package it.unicam.cs.mpgc.rpg126692.oggetti;

import it.unicam.cs.mpgc.rpg126692.carte.CartaMostro;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

public class Cibo extends Oggetto{
    private int puntiCura;

    public Cibo(String nome, String descrizione, String imagePath, int puntiCura){
        super(nome, descrizione, imagePath);
        this.puntiCura = puntiCura;
    }

    @Override
    public void usa(Personaggio utilizzatore, CartaMostro mostro) {
        // 1. Controlliamo se gli HP sono già al massimo (18)
        if (utilizzatore.getHP() >= 18) {
            System.out.println("Hai già i Punti Vita al massimo! " + getNome() + " non è stato usato e rimane nel tuo inventario.");
            return; // Interrompe l'esecuzione: il cibo NON viene consumato né rimosso
        }

        // 2. Appliciamo la cura
        utilizzatore.cura(this.puntiCura);
        System.out.println("Hai consumato " + getNome() + "! HP attuali: " + utilizzatore.getHP() + "/18 ");

        // 3. Troviamo la posizione di QUESTO cibo nell'inventario e lo scartiamo
        int indice = utilizzatore.getInventario().getOggetti().indexOf(this);
        if (indice != -1) {
            utilizzatore.getInventario().scarta(indice);
        }
    }
}
