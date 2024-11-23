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
        // Récupération des paramètres depuis la requête
        String filiereStr = request.getParameter("filiere"); // Nom de la filière
        String etudiantEmail = (String) request.getSession().getAttribute("email");

        // Validation des données entrantes
        if (filiereStr == null || filiereStr.trim().isEmpty()) {
            response.sendRedirect("choixFiliere.jsp?error=Filière invalide.");
            return;
        }

        if (etudiantEmail == null || etudiantEmail.trim().isEmpty()) {
            response.sendRedirect("login.jsp?error=Veuillez vous connecter pour soumettre une demande.");
            return;
        }

        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Début de la transaction Hibernate
            transaction = session.beginTransaction();

            // Recherche de la filière dans la base de données
            Filiere filiere = session.createQuery("FROM Filiere WHERE nom = :nom", Filiere.class)
                    .setParameter("nom", filiereStr.trim())
                    .uniqueResult();

            // Si la filière n'existe pas, redirection avec un message d'erreur
            if (filiere == null) {
                transaction.rollback();
                response.sendRedirect("choixFiliere.jsp?error=La filière sélectionnée est introuvable.");
                return;
            }

            // Création de l'objet `DemandeFiliere`
            DemandeFiliere demande = new DemandeFiliere();
            demande.setEtudiantEmail(etudiantEmail);
            demande.setFiliere(filiere.getNom());
            demande.setStatut("EN_ATTENTE");
            demande.setDateDemande(new Date());
            demande.setCommentaireAdmin("En attente de traitement");

            // Sauvegarde de la demande dans la base de données
            session.save(demande);

            // Commit de la transaction si tout s'est bien passé
            transaction.commit();

            // Redirection vers la page étudiant avec un message de succès
            response.sendRedirect("etudiant.jsp?message=Votre demande a été envoyée avec succès.");
        } catch (Exception e) {
            // Rollback de la transaction en cas d'erreur
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            // Affichage de l'erreur dans les logs pour faciliter le débogage
            e.printStackTrace();

            // Redirection vers la page de sélection de filière avec un message d'erreur
            response.sendRedirect("choixFiliere.jsp?error=Erreur lors de l'envoi de la demande.");
        }
    }
}
