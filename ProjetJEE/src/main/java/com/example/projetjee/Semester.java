package com.example.projetjee;

public enum Semester {
    SEMESTER_1("Semester 1"),
    SEMESTER_2("Semester 2");

    private final String name;

    // Constructeur de l'énumération
    Semester(String name) {
        this.name = name;
    }

    // Getter pour obtenir le nom du semestre
    public String getName() {
        return name;
    }
}
