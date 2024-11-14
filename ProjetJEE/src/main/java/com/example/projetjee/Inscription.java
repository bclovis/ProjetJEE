package com.example.projetjee;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Inscription")
public class Inscription {

    @Id
    @JoinColumn(name = "etudiant_email", nullable = false)
    private String etudiant;

    @Id
    @JoinColumn(name = "cours_id", nullable = false)
    private int cours;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    // Constructeurs
    public Inscription() {}

    public Inscription(String etudiant, int cours, Date date) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.date = date;
    }

    // Getters et Setters

    public String getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(String etudiant) {
        this.etudiant = etudiant;
    }

    public int getCours() {
        return cours;
    }

    public void setCours(int cours) {
        this.cours = cours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
