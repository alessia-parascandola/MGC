package it.unicam.cs.mpgc.rpg126692;

import java.util.List;
import java.util.Random;

    //classe astratta perchè funge solo da "padre"
    //<T> è un segnaposto: la classe gestisce il tipo T che decideranno i figli
public abstract class Dado<T> {
    //"protected" si usa nelle classi astratte o padre quando vogliamo che un
    //metodo o attributo venga condiviso solo ed esclusivamente con i suoi figli
    protected List<T> facce;
    protected final Random random = new Random();

    public Dado(List<T> facce){
        if (facce == null || facce.size() != 6){     //size() si usa sulle liste
            throw new IllegalArgumentException("Un dado deve avere 6 facce!");
        }
        this.facce = facce;
    }

    public T lancia(){
        return facce.get(random.nextInt(facce.size()));
    }
}
