package com.example.projetjee;

import java.util.Date;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Grade implements Observable {
    @Id
    private float note;
    @ManyToOne
    @JoinColumn(name = "student_id")  // Clé étrangère vers Student
    @Id
    private Student etudiant;
    @ManyToOne
    @JoinColumn(name = "subject_id")  // Clé étrangère vers Subject
    @Id
    private Subject matiere;
    private Date date;
    public List<Observateur> observateurs;

    // Constructeurs, getters et setters
    public Grade() {}

    public Grade(float note, Subject matiere) {
        this.note = note;
        this.matiere = matiere;
    }
    public void ajouterObservateur(Observateur observateur) {

    }

    public void supprimerObservateur(Observateur observateur) {

    }

    public void notifierObservateur(String message) {

    }

}
