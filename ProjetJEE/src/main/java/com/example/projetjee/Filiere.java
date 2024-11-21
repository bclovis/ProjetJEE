package com.example.projetjee;

import jakarta.persistence.*;

@Entity
@Table(name = "Filiere")
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    // Constructeur par défaut
    public Filiere() {}

    // Constructeur avec le nom de la filière
    public Filiere(String nom) {
        this.nom = nom;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Filiere [id=" + id + ", nom=" + nom + "]";
    }
}
