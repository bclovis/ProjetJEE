package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class SendNoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Redirection ou autre traitement
        response.sendRedirect("http://localhost:8081/ProjetJEE_war_exploded/voirNotes");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les informations nécessaires, y compris l'email de l'étudiant
        String emailEtudiant = "melaimiani@cy-tech.fr";
        String matiere = "test";
        double note = Double.parseDouble(request.getParameter("note"));

        // Enregistrer la note dans la base de données (exemple simplifié)
        // ... Code pour enregistrer la note ...

        // Envoi de la notification par email
        String sujet = "Nouvelle Note Publiée en " + matiere;
        String corpsMessage = "Bonjour,\n\nVotre nouvelle note en " + matiere + " est de " + note + ".\nConsultez votre espace étudiant pour plus de détails.\n\nCordialement,\nL'équipe académique";

        EmailUtil.sendEmail(emailEtudiant, sujet, corpsMessage);
    }
}
