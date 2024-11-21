package com.example.projetjee;

import jakarta.persistence.*;

@Entity
@Table(name = "Matiere")
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nom", nullable = false)
    private String nom;

    // Constructeurs
    public Matiere() {}

    public Matiere(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }
    @Override
    public String toString(){
        return nom;
    }
}