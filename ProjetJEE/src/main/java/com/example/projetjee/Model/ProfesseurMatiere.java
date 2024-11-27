package com.example.projetjee.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "ProfesseurMatiere")
public class ProfesseurMatiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "professeur_email", nullable = false)
    private String professeurEmail;

    @Column(name = "matiere_id", nullable = false)
    private int matiereId;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfesseurEmail() {
        return professeurEmail;
    }

    public void setProfesseurEmail(String professeurEmail) {
        this.professeurEmail = professeurEmail;
    }

    public int getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(int matiereId) {
        this.matiereId = matiereId;
    }
}
