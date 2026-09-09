package it.unicam.cs.mpgc.rpg126692.carte.Eventi;

import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;
import java.util.List;
import java.util.Random;

public class EventoPioggiaAcido extends CartaEvento{

    public EventoPioggiaAcido(){
        super("Inavvertitamente, il tuo stivale inciampa sul filo di una trappola che rilascia una pioggia d'acido dall'alto!\n" +
                "Esegui una prova di ASTUZIA per schivare!");
    }

    @Override
    public void esegui(Personaggio personaggio) {
        System.out.println(getDescrizione());
    }

    // Gestisce la prova per tutti i personaggi presenti nel gruppo
    public void eseguiProvaMoltitudine(List<Personaggio> gruppo) {
        Random random = new Random();

        for (Personaggio p : gruppo) {
            System.out.println("\n--- Prova Acido per " + p.getNome() + " ---");
            // Il personaggio lancia il suo dado unico
            FacciaDado faccia = p.lanciaDado();
            Simbolo tiro = faccia.getSimboloPrincipale();

            System.out.println(p.getNome() + " ha tirato: " + tiro);

            // Verifica della prova di Astuzia
            if (tiro == Simbolo.ASTUZIA) {
                System.out.println("SUCCESSO: Schivi la pioggia d'acido!");
            } else {
                System.out.println("FALLIMENTO: L'acido inizia a corrodere!");
                List<?> oggetti = p.getInventario().getOggetti();

                // Caso 1: L'inventario è vuoto, il danno si riflette direttamente sugli HP del personaggio
                if (oggetti.isEmpty()) {
                    System.out.println("Non hai oggetti! L'acido ti corrode la pelle: perdi 1 HP.");
                    p.subisciDanno(1, false, false);
                }
                // Caso 2: C'è un solo oggetto, viene scartato quello all'indice 0
                else if (oggetti.size() == 1) {
                    System.out.println("L'acido corrode il tuo unico oggetto!");
                    p.getInventario().scarta(0);
                }
                // Caso 3: Ci sono due oggetti, si estrae un indice casuale (0 o 1) per la distruzione
                else {
                    int indiceRandom = random.nextInt(oggetti.size());
                    System.out.println("L'acido corrode un oggetto casuale!");
                    p.getInventario().scarta(indiceRandom);
                }
            }
        }
    }
}
