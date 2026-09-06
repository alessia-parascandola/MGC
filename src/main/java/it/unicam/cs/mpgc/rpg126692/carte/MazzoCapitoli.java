package it.unicam.cs.mpgc.rpg126692.carte;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;

import java.util.ArrayList;
import java.util.List;

public class MazzoCapitoli {
    private ArrayList<CartaCapitolo> carteCapitolo = new ArrayList<>();
    private List<CartaBoss> carteBoss = new ArrayList<>();
    private CartaIntro cartaIntro;

    public MazzoCapitoli(){
        // Carte Mostro
        carteCapitolo.add(new CartaMostro("Mentre passi, una pesante porta di legno " +
                "si apre violentemente e un pazzo vestito di stracci si lancia fuori. Impugna le sue catene" +
                "a mo' di arma e il suo folle sguardo manda un chiaro messaggio: ti stai frapponendo tra lui e la libertà.",
                List.of(Simbolo.FORZA, Simbolo.ASTUZIA), 2));
        carteCapitolo.add(new CartaMostro("Questa sala è fiancheggiata da grotte e disseminata di ossa masticate." +
                "Un verso gutturale risuona mentre quacosa emerge dall'ombra.", List.of(Simbolo.FORZA, Simbolo.FORZA, Simbolo.ASTUZIA), 2));

        // Carte Evento

        // Carte Boss
    }

    public CartaIntro getCartaIntro(){
        return cartaIntro;
    }
    public List<CartaCapitolo> getCarteCapitolo(){
        return new ArrayList<>(carteCapitolo);
    }
    public List<CartaBoss> getCarteBoss(){
        return new ArrayList<>(carteBoss);
    }
}
