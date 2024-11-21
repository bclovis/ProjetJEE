package com.example.projetjee;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Note")
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
    private LocalDate date;

    // Constructeurs
    public Note() {
    }

    public Note(float note, Etudiant etudiant, Matiere matiere) {
        this.note = note;
        this.date = LocalDate.now();
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
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