package com.example.projetjee;

import jakarta.persistence.*;

@Entity
@Table(name = "ProfesseurMatiere")
public class ProfesseurMatiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "professeur_email", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere;

    // Constructeurs
    public ProfesseurMatiere() {}

    public ProfesseurMatiere(Enseignant enseignant, Matiere matiere) {
        this.enseignant = enseignant;
        this.matiere = matiere;
        ;
    }

    public Long getId() {
        return id;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}