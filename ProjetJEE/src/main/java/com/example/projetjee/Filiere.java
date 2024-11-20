package com.example.projetjee;

public enum Filiere {
    INFORMATIQUE,
    MATHEMATIQUES,
    BIOTECHNOLOGIE,
    GENIE_CIVIL,
    AUCUNE;

    @Override
    public String toString() {
        switch (this) {
            case INFORMATIQUE:
                return "Informatique";
            case MATHEMATIQUES:
                return "Mathématiques";
            case BIOTECHNOLOGIE:
                return "Physique";
            case GENIE_CIVIL:
                return "Chimie";
            case AUCUNE:
                return "";
            default:
                return super.toString();
        }
    }
}
