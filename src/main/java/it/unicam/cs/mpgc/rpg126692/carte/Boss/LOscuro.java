package it.unicam.cs.mpgc.rpg126692.carte.Boss;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;

public class LOscuro extends CartaBoss{

    public LOscuro(){
        super("L'Oscuro", "I vostri patetici ninnoli non sono all'altezza della mia magia oscura!",
                "Non appena arrivi al cospetto dell'Oscuro, gli Oggetti che stai trasportando evaporano. Scartali tutti immediatamente.",
                List.of(Simbolo.ASTUZIA, Simbolo.SAGGEZZA, Simbolo.SAGGEZZA, Simbolo.SAGGEZZA), 3);
    }

    @Override
    public void applicaEffettoInizioScontro(Personaggio personaggio) {
        System.out.println("L'Oscuro dissolve tutti i tuoi oggetti!");
        personaggio.getInventario().svuotaInventario();
    }
}
