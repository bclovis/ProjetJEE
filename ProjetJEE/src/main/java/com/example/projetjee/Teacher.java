package com.example.projetjee;

import java.util.Date;
import jakarta.persistence.Entity;

@Entity
public class Teacher extends User implements Observateur {

    public Teacher() { super();}
    public Teacher(int id, String nom, String prenom, Date dateNaissance, String mail, String mdp) {
        super(id, nom, prenom, dateNaissance, mail, mdp);
    }

    public void saisieNotes(){
        // à compléter
    }

    public void consulterCours(){
        // à compléter
    }

    public void consulterStudent() {
        // à compléter
    }

    public void mettreAJour(String message) {

    }

}
