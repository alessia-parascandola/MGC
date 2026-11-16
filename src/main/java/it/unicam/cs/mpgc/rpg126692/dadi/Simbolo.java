package it.unicam.cs.mpgc.rpg126692.dadi;

public enum Simbolo {
    POSSANZA("/images/dadi/possanza_capitolo.png"),
    ASTUZIA("/images/dadi/astuzia_capitolo.png"),
    SAPIENZA("/images/dadi/sapienza_capitolo.png");

    private final String imagePath;

    Simbolo(String imagePath){
        this.imagePath = imagePath;
    }

    public String getImagePath(){
        return imagePath;
    }
}
