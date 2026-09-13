package it.unicam.cs.mpgc.rpg126692.dadi;

public enum Simbolo {
    FORZA("/images/dadi/forza_capitolo.png"),
    ASTUZIA("/images/dadi/astuzia_capitolo.png"),
    SAGGEZZA("/images/dadi/saggezza_capitolo.png");

    private final String imagePath;

    Simbolo(String imagePath){
        this.imagePath = imagePath;
    }

    public String getImagePath(){
        return imagePath;
    }
}
