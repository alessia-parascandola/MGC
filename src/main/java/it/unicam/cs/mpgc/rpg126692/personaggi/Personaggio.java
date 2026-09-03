package it.unicam.cs.mpgc.rpg126692.personaggi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoPersonaggio;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.Inventario;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;

public class Personaggio {
    private int hp;
    private final String nome;
    private final int forza;
    private final int astuzia;
    private final int saggezza;
    private final DadoPersonaggio dado;
    private Inventario inventario;

    public Personaggio(int hp, String nome, int forza, int astuzia, int saggezza, DadoPersonaggio dado){
        if(hp <= 0 || hp > 18){
            throw new IllegalArgumentException("Gli HP iniziali devono essere compresi tra 1 e 18!");
        }
        if(nome == null || nome.isBlank()){
            throw new IllegalArgumentException("Il nome del personaggio non può essere vuoto!");
        }
        if(dado == null){
            throw new IllegalArgumentException("Il personaggio deve avere un dado associato!");
        }

        this.hp = hp;
        this.nome = nome;
        this.forza = forza;
        this.astuzia = astuzia;
        this.saggezza = saggezza;
        this.dado = dado;
        this.inventario = new Inventario();     // Crea un inventario vuoto all'inizio
    }

    // Getter
    public int getHP(){ return hp; }
    public String getNome(){ return nome; }
    public int getForza(){ return forza; }
    public int getAstuzia(){ return astuzia; }
    public int getSaggezza(){ return saggezza; }
    public DadoPersonaggio getDado() { return dado; }
    public Inventario getInventario(){ return inventario; }

    // Metodi per gestire la salute
    public void subisciDanno(int danno, boolean usaEvasione, boolean paratoDaDado) {
        if (danno <= 0) return;

        // 1. Se il giocatore ha scelto di usare la pozione
        if (usaEvasione) {
            Oggetto pozioneEvasione = cercaOggettoPerNome("Evasione effervescente");
            if (pozioneEvasione != null) {
                System.out.println("Usi Evasione effervescente! Danno annullato.");
                pozioneEvasione.usa(this, null); // Annulla il danno e la scarta
                return;
            }
        }

        // 2. Parata da dado
        if (paratoDaDado) {
            System.out.println("Attacco parato dal dado!");
            return;
        }

        // 3. Scudo Marcito equipaggiato (automatico)
        int riduzione = inventario.getRiduzioneDannoTotale();
        int dannoEffettivo = Math.max(0, danno - riduzione);

        if (riduzione > 0) {
            System.out.println("Lo Scudo Marcito riduce il danno di " + riduzione + "!");
        }

        this.hp = Math.max(0, this.hp - dannoEffettivo);
        System.out.println("HP rimanenti: " + this.hp);
    }

    public void cura(int quantita){
        hp = Math.min(18, hp + quantita);
    }
    public boolean isSconfitto(){
        return hp <= 0;
    }

    public FacciaDado lanciaDado(){
        return dado.lancia();  // Invoca il metodo del Dado
    }

    public Oggetto cercaOggettoPerNome(String nomeCercato) {
        // 1. Chiediamo all'inventario la lista di tutti gli oggetti in mano
        for (Oggetto obj : inventario.getOggetti()) {

            // 2. Confrontiamo il nome (equalsIgnoreCare ignora maiuscole/minuscole)
            if (obj.getNome().equalsIgnoreCase(nomeCercato)) {
                return obj; // Trovato! Restituisce l'oggetto (es. la pozione)
            }
        }

        // 3. Se il ciclo finisce e non l'ha trovata, restituisce null
        return null;
    }
}
