package it.unicam.cs.mpgc.rpg126692.carte.Boss;
import it.unicam.cs.mpgc.rpg126692.Interazioni;

import it.unicam.cs.mpgc.rpg126692.dadi.Simbolo;
import it.unicam.cs.mpgc.rpg126692.personaggi.Personaggio;

import java.util.List;

public class LOscuro extends CartaBoss{

    public LOscuro(){
        super("L'Oscuro", "I vostri patetici ninnoli non sono all'altezza della mia magia oscura!",
                "Non appena arrivi al cospetto dell'Oscuro, gli Oggetti che stai trasportando evaporano. Scartali tutti immediatamente.",
                "/images/cassero/boss_oscuro.png", List.of(Simbolo.ASTUZIA, Simbolo.SAPIENZA, Simbolo.SAPIENZA, Simbolo.SAPIENZA), 3);
    }

    @Override
    public void applicaEffettoInizioScontro(Personaggio personaggio) {
        Interazioni.corrente().println("L'Oscuro dissolve tutti i tuoi oggetti!");
        personaggio.getInventario().svuotaInventario();
    }
}
