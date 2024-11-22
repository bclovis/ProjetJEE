package com.example.projetjee;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Filiere")
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EmploiDuTemps> emploiDuTemps;

    // Getters et Setters
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

    public Set<EmploiDuTemps> getEmploiDuTemps() {
        return emploiDuTemps;
    }

    public void setEmploiDuTemps(Set<EmploiDuTemps> emploiDuTemps) {
        this.emploiDuTemps = emploiDuTemps;
    }
}
