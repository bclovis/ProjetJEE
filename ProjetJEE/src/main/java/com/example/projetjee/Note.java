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

    @JoinColumn(name = "etudiant_email", nullable = false)
    private String etudiant;

    @JoinColumn(name = "cours_id", nullable = false)
    private int cours;
    @Temporal(TemporalType.DATE)
    @Column(name = "date", nullable = false)
    private Date date;

    @Transient
    private List<Observateur> observateurs = new ArrayList<>();

    // Constructeurs
    public Note() {
    }

    public Note(float note, Date date, String etudiant, int cours) {
        this.note = note;
        this.date = date;
        this.etudiant = etudiant;
        this.cours = cours;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
        notifierObservateurs("Note modifiée : " + note);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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

    // Méthodes pour gérer les observateurs (patron de conception Observer)
    public void ajouterObservateur(Observateur observateur) {
        observateurs.add(observateur);
    }

    public void supprimerObservateur(Observateur observateur) {
        observateurs.remove(observateur);
    }

    public void notifierObservateurs(String message) {
        for (Observateur observateur : observateurs) {
            observateur.mettreAJour(message);
        }
    }
}