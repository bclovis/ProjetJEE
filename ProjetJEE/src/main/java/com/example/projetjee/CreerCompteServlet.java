package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Date;

public class CreerCompteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeCompte = request.getParameter("typeCompte");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        Date dateNaissance = java.sql.Date.valueOf(request.getParameter("dateNaissance"));
        String mdp = request.getParameter("mdp");

        // Validation du champ date

        if (typeCompte == null || nom == null || email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Erreur : Tous les champs sont requis.");
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            if ("etudiant".equals(typeCompte)) {
                Etudiant etudiant = new Etudiant(email, nom, prenom, dateNaissance, mdp);
                session.save(etudiant);
            } else if ("enseignant".equals(typeCompte)) {
                Enseignant enseignant = new Enseignant(email, nom, prenom, dateNaissance, mdp);
                session.save(enseignant);
            }

            transaction.commit();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Compte créé avec succès.");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erreur lors de la création du compte.");
        } finally {
            session.close();
        }
    }
}
