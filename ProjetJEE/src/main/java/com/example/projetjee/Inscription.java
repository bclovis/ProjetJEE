package com.example.projetjee;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inscription")  // Notez que le nom est en minuscule pour correspondre au schéma de la base de données
public class Inscription {

    @Id
    @ManyToOne
    @JoinColumn(name = "etudiant", nullable = false)
    private Etudiant etudiant;

    @Id
    @ManyToOne
    @JoinColumn(name = "cours", nullable = false)
    private Cours cours;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;



    // Constructeurs
    public Inscription() {}

    public Inscription(Etudiant etudiant, Cours cours, Date date) {
        this.etudiant = etudiant;
        this.cours = cours;
        this.date = date;
    }

    // Getters et Setters

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
