package com.example.projetjee;

public enum Subject {
    MATH("Mathematics"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    BIOLOGY("Biology"),
    COMPUTER_SCIENCE("Computer Science"),
    HISTORY("History"),
    GEOGRAPHY("Geography"),
    LITERATURE("Literature");

    private final String name;

    // Constructeur de l'énumération
    Subject(String name) {
        this.name = name;
    }

    // Getter pour le nom de la matière
    public String getName() {
        return name;
    }
}
