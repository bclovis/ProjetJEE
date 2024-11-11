package com.example.projetjee;

import java.util.Date;
import java.util.List;
import java.util.Map;
import jakarta.persistence.*;

@Entity
public class Student extends User implements Observateur {
    @OneToMany(mappedBy = "student")
    private List<Inscription> inscriptions;
    // Utiliser @OneToMany pour gérer une collection de grades par matière
    @OneToMany(mappedBy = "etudiant")
    private List<StudentGrade> resultats;

    public Student() {}
    public Student(int id, String nom, String prenom, Date dateNaissance, String mail, String mdp) {
        super(id, nom, prenom, dateNaissance, mail, mdp);
    }

    public void consulterCours(){
        // à compléter
    }

    public void consulterNote(){
        // à compléter
    }

    public void sinscrireCours(){
        // à compléter
    }

    public void mettreAJour(String message) {

    }
}
