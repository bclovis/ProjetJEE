package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SendInscriptionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des informations d'inscription et de l'email
        String emailEtudiant = (String) request.getSession().getAttribute("email");
        String nouvelleMatiere = request.getParameter("matiere");

        // Mettre à jour l'inscription dans la base de données
        // ... Code de mise à jour de l'inscription ...

        // Envoyer une notification par email
        String sujet = "Changement d'inscription";
        String corpsMessage = "Bonjour,\n\nVotre inscription a été modifiée. Vous êtes maintenant inscrit(e) en " + nouvelleMatiere + ".\n\nCordialement,\nL'équipe académique";

        EmailUtil.sendEmail(emailEtudiant, sujet, corpsMessage);

        // Redirection ou autre traitement
        response.sendRedirect("voirInscription.jsp");
    }
}
