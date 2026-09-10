package it.unicam.cs.mpgc.rpg126692.personaggi;

import it.unicam.cs.mpgc.rpg126692.dadi.DadoPersonaggio;
import it.unicam.cs.mpgc.rpg126692.dadi.FacciaDado;
import it.unicam.cs.mpgc.rpg126692.oggetti.Inventario;
import it.unicam.cs.mpgc.rpg126692.oggetti.Oggetto;

import java.util.Scanner;

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

        // 1. Controlla se il giocatore ha la Pozione Evasione
        Oggetto pozioneEvasione = cercaOggettoPerNome("Evasione effervescente");

        // Se ha la pozione (e non è un danno già parato dal dado), gli chiediamo se vuole usarla
        if (pozioneEvasione != null && !paratoDaDado) {
            Scanner scan = new Scanner(System.in);
            System.out.println("\n=========================================");
            System.out.println(" ATTENZIONE: Stai per subire " + danno + " danni!");
            System.out.println(" Hai nell'inventario: Evasione effervescente");
            System.out.println(" Vuoi usarla per ANNULLARE COMPLETAMENTE il danno? [1 = Sì / 2 = No]");
            System.out.print("> ");

            String scelta = scan.nextLine().trim();

            if (scelta.equals("1")) {
                pozioneEvasione.usa(this, null); // Messaggio + scarto dall'inventario
                System.out.println("I tuoi HP rimangono invariati: " + this.hp + "/18");
                System.out.println("=========================================");
                return; // Esce subito senza applicare danno o usare lo scudo
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
            if (danno > 1) {
                // Riduce il danno di 1, ma garantisce che il danno non scenda sotto 1
                dannoEffettivo = Math.max(1, danno - riduzione);
                System.out.println("Lo Scudo Marcito riduce il danno di " + riduzione + "!");
            } else {
                System.out.println("Lo Scudo Marcito non può azzerare un danno singolo! Subisci 1 HP.");
            }
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

    public boolean eVivo() {
        return this.hp > 0;
    }

    public int getSaluteMassima(){
        return 18;
    }
}
