package it.unicam.cs.mpgc.rpg126692.dadi;

import java.util.List;

public class DadoCapitolo extends Dado<Simbolo>{

    //Costruttore che riceve le 6 facce dall'esterno
    public DadoCapitolo(List<Simbolo> facce) {
        super(facce);      //Invoca il costruttore di Dado<T> per la validazione e l'assegnazione
    }

    public DadoCapitolo(){
        //Passa la lista al costruttore del padre, garantendo che la creazione
        //rispetti tutti i controlli dell classe astratta
        super(List.of(
                Simbolo.FORZA, Simbolo.FORZA,
                Simbolo.ASTUZIA, Simbolo.ASTUZIA,
                Simbolo.SAGGEZZA, Simbolo.SAGGEZZA
        ));
    }

    /* TEST PER VEDERE SE FUNZIONA:
    public static void main(String[] args) {
        DadoCapitolo dadoCapitolo = new DadoCapitolo(); // Crei l'oggetto
        Simbolo risultato = dadoCapitolo.lancia();     // Chiami il metodo ereditato dal padre

        System.out.println("Risultato lancio: " + risultato);
    } */

}
