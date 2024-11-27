package com.example.projetjee;

public enum Filieres {
    INFORMATIQUE,
    MATHEMATIQUES,
    AUCUNE;

    @Override
    public String toString() {
        switch (this) {
            case INFORMATIQUE:
                return "Informatique";
            case MATHEMATIQUES:
                return "Mathématiques";
            case AUCUNE:
                return "";
            default:
                return super.toString();
        }
    }
}
