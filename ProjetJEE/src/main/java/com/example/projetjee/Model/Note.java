package com.example.projetjee.Model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "note")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "note", nullable = false)
    private float note;
    @ManyToOne
    @JoinColumn(name = "etudiant", nullable = false)
    private Etudiant etudiant;
    @ManyToOne
    @JoinColumn(name = "matiere", nullable = false)
    private Matiere matiere;
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    // Constructeurs
    public Note() {
    }

    public Note(float note, Etudiant etudiant, Matiere matiere) {
        this.note = note;
        this.date = new Date();
        this.etudiant = etudiant;
        this.matiere = matiere;
    }

    // Getters et Setters

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }


    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}