package com.example.projetjee;

import jakarta.persistence.*;

@Entity
@Table(name = "MatiereFiliere")
public class MatiereFiliere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Matiere matiere;  // Référence à la table Matiere

    @ManyToOne
    private Filiere filiere;  // Référence à la table Filiere

    // Constructeur par défaut
    public MatiereFiliere() {}

    // Constructeur avec les objets Matiere et Filiere
    public MatiereFiliere(Matiere matiere, Filiere filiere) {
        this.matiere = matiere;
        this.filiere = filiere;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    @Override
    public String toString() {
        return "MatiereFiliere [id=" + id + ", matiere=" + matiere.getNom() + ", filiere=" + filiere.getNom() + "]";
    }
}
