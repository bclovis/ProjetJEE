package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Date;

@WebServlet(name = "DemandeFiliereServlet", value = "/demandeFiliere")
public class DemandeFiliereServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filiereStr = request.getParameter("filiere");
        String etudiantEmail = (String) request.getSession().getAttribute("email");

        if (filiereStr != null && etudiantEmail != null) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();

                // Création de la demande
                DemandeFiliere demande = new DemandeFiliere();
                demande.setEtudiantEmail(etudiantEmail);

                try {
                    demande.setFiliere(Filieres.valueOf(filiereStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    response.sendRedirect("choixFiliere.jsp?error=Filière invalide.");
                    return;
                }

                demande.setStatut("EN_ATTENTE");
                demande.setDateDemande(new Date());
                demande.setCommentaireAdmin("En attente de traitement");

                session.save(demande);
                transaction.commit();

                response.sendRedirect("etudiant.jsp?message=Votre demande a été envoyée.");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("choixFiliere.jsp?error=Erreur lors de l'envoi de la demande.");
            }
        } else {
            response.sendRedirect("choixFiliere.jsp?error=Erreur lors de l'envoi de la demande.");
        }
    }
}