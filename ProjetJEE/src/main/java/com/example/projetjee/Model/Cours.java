package com.example.projetjee.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Cours")
public class Cours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "matiere", nullable = false)
    private Matiere matiere;

    @ManyToOne
    @JoinColumn(name = "enseignant", nullable = false)
    private Enseignant enseignant;

    @Column(name = "heure_debut", nullable = false)
    private LocalDateTime debut;

    @Column(name = "heure_fin", nullable = false)
    private LocalDateTime fin;

    @Enumerated(EnumType.STRING)
    @Column(name = "semestre", nullable = false)
    private Semestre semestre;

    // Constructeurs
    public Cours() {}

    public Cours(Matiere matiere, Enseignant enseignant, LocalDateTime debut, LocalDateTime fin, Semestre semestre) {
        this.matiere = matiere;
        this.enseignant = enseignant;
        this.debut = debut;
        this.fin = fin;
        this.semestre = semestre;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public LocalDateTime getDebut() {
        return debut;
    }

    public void setDebut(LocalDateTime debut) {
        this.debut = debut;
    }

    public LocalDateTime getFin() {
        return fin;
    }

    public void setFin(LocalDateTime fin) {
        this.fin = fin;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }
}
