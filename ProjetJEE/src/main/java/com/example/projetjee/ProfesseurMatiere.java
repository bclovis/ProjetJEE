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
    private Enseignant professeurEmail;

    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    private Matiere matiere;

    // Constructeurs
    public ProfesseurMatiere() {}

    public ProfesseurMatiere(Enseignant professeurEmail, Matiere matiere) {
        this.professeurEmail = professeurEmail;
        this.matiere = matiere;
        ;
    }

    public Long getId() {
        return id;
    }

    public Enseignant getProfesseurEmail() {
        return professeurEmail;
    }

    public void setProfesseurEmail(Enseignant enseignant) {
        this.professeurEmail = enseignant;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }
}