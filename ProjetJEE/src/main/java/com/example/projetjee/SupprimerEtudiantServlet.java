package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;

@WebServlet(name = "SupprimerEtudiantServlet", value = "/supprimerEtudiant")
public class SupprimerEtudiantServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Etudiant etudiant = session.get(Etudiant.class, email);
            if (etudiant != null) {
                // Supprimer les demandes associées avant de supprimer l'étudiant
                Query<?> deleteDemandesQuery = session.createQuery("DELETE FROM DemandeFiliere WHERE etudiantEmail = :email");
                deleteDemandesQuery.setParameter("email", email);
                deleteDemandesQuery.executeUpdate();

                // Supprimer l'étudiant
                session.delete(etudiant);
                transaction.commit();
                response.sendRedirect("gererEtudiants?message=Étudiant supprimé avec succès");
            } else {
                response.sendRedirect("gererEtudiants?error=Étudiant introuvable");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEtudiants?error=Erreur lors de la suppression de l'étudiant");
        }
    }
}
