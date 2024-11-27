package com.example.projetjee.Model;

import com.example.projetjee.Model.Filieres;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class DemandeFiliere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "etudiant_email")
    private String etudiantEmail;

    @Enumerated(EnumType.STRING)
    private Filieres filiere;

    private String statut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_demande", nullable = false, updatable = false)
    private Date dateDemande;

    @Column(name = "commentaire_admin")
    private String commentaireAdmin;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtudiantEmail() {
        return etudiantEmail;
    }

    public void setEtudiantEmail(String etudiantEmail) {
        this.etudiantEmail = etudiantEmail;
    }

    public Filieres getFiliere() {
        return filiere;
    }

    public void setFiliere(Filieres filiere) {
        this.filiere = filiere;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public String getCommentaireAdmin() {
        return commentaireAdmin;
    }

    public void setCommentaireAdmin(String commentaireAdmin) {
        this.commentaireAdmin = commentaireAdmin;
    }
}
