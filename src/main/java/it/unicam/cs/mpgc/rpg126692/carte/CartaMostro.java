package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.GestoreCombattimento;
import it.unicam.cs.mpgc.rpg126692.dadi.DadoCapitolo;
import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.oggetti.MazzoOggetti;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.ArrayList;
import java.util.List;

public class CartaMostro extends CartaCapitolo{
    // Lista dei simboli fissi stampati sulla carta (può essere vuota)
    private final List<Simbolo> simboliFissi;

    // Il tracciato effettivo per lo scontro
    private final List<Simbolo> tracciatoSimboli;

    // Danno che il mostro infligge al personaggio
    private final int danno;

    public CartaMostro(String descrizione, String imagePath, List<Simbolo> simboliFissi, int danno){
        super(descrizione, imagePath);
        this.simboliFissi = (simboliFissi != null) ? simboliFissi : new ArrayList<>();      // Se non ci sono simboli fissi sulla carta, inizializza una lista vuota
        this.tracciatoSimboli = new ArrayList<>();
        this.danno = danno;
    }

    // Prepara la vita del mostro: combina i simboli fissi col lancio del Dado Capitolo
    public void generaSimboliVita(DadoCapitolo dadoCapitolo, int numeroGiocatori){
        // Pulisce il tracciato da eventuali scontri precedenti
        tracciatoSimboli.clear();

        // 1. Aggiunge i simboli fissi della carta (se ce ne sono)
        tracciatoSimboli.addAll(simboliFissi);

        // 2. Tira il DadoCapitolo per player nel gruppo
        for (int i = 0; i < numeroGiocatori; i++){
            Simbolo simboloRandom = dadoCapitolo.lancia();
            tracciatoSimboli.add(simboloRandom);
        }
    }

    // Restituisce il tracciato dei simboli ancora da eliminare
    public List<Simbolo> getTracciatoSimboli(){
        return tracciatoSimboli;
    }
    public int getDanno(){
        return danno;
    }
    public boolean haSimbolo(Simbolo simboloOttenuto) {
        return tracciatoSimboli.contains(simboloOttenuto);
    }
    public void rimuoviSimbolo(Simbolo simboloOttenuto) {
        tracciatoSimboli.remove(simboloOttenuto);
    }
    public boolean eSconfitto(){
        return tracciatoSimboli.isEmpty();
    }

    @Override
    public void esegui(Personaggio personaggio, MazzoOggetti mazzoOggetti) {
        System.out.println(getDescrizione());

        // Usa il costruttore vuoto (senza niente tra le parentesi)
        DadoCapitolo dadoCapitolo = new DadoCapitolo();
        GestoreCombattimento gestore = new GestoreCombattimento();
        gestore.combatti(personaggio, this, dadoCapitolo);
    }
}
