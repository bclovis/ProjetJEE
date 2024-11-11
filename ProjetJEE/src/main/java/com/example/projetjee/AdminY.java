package com.example.projetjee;

import java.util.Date;
import jakarta.persistence.Entity;

@Entity
public class AdminY extends User{

    public AdminY() {}
    public AdminY(int id, String nom, String prenom, Date dateNaissance, String mail, String mdp) {
        super(id, nom, prenom, dateNaissance, mail, mdp);
    }

    public void gestionInscription() {
        // à compléter
    }

    public void gestionCours() {
        // à compléter
    }

    public void ajouterStudent() {
        // à compléter
    }

    public void supprimerStudent() {
        // à compléter
    }

    public void modifierStudent() {
        // à compléter
    }

    public void consulterStudent() {
        // à compléter
    }

    public void ajouterTeacher() {
        // à compléter
    }

    public void supprimerTeacher() {
        // à compléter
    }

    public void consulterTeacher() {
        // à compléter
    }

    public void modifierTeacher() {
        // à compléter
    }

}
