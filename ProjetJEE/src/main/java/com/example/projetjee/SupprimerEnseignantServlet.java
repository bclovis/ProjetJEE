package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

@WebServlet(name = "SupprimerEnseignantServlet", value = "/supprimerEnseignant")
public class SupprimerEnseignantServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Enseignant enseignant = session.get(Enseignant.class, email);
            if (enseignant != null) {
                session.delete(enseignant);
                transaction.commit();
                response.sendRedirect("gererEnseignants?message=Enseignant supprimé avec succès");
            } else {
                response.sendRedirect("gererEnseignants?error=Enseignant introuvable");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEnseignants?error=Erreur lors de la suppression de l'enseignant");
        }
    }
}
