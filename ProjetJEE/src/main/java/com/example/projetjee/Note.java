package com.example.projetjee;

import jakarta.persistence.*;
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
    @JoinColumn(name = "cours", nullable = false)
    private Cours cours;
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    // Constructeurs
    public Note() {
    }

    public Note(float note, Date date, Etudiant etudiant, Cours cours) {
        this.note = note;
        this.date = date;
        this.etudiant = etudiant;
        this.cours = cours;
    }

    // Getters et Setters

    public float getNote() {
        return note;
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

    public Cours getCours() {
        return cours;
    }



}