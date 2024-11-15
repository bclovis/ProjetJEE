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

@WebServlet(name = "ModifierEnseignantServlet", value = "/modifierEnseignant")
public class ModifierEnseignantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Enseignant> query = session.createQuery("FROM Enseignant WHERE email = :email", Enseignant.class);
            query.setParameter("email", email);
            Enseignant enseignant = query.uniqueResult();

            if (enseignant != null) {
                request.setAttribute("nom", enseignant.getNom());
                request.setAttribute("prenom", enseignant.getPrenom());
                request.setAttribute("dateNaissance", enseignant.getDateNaissance());
                request.setAttribute("mdp", enseignant.getMdp());
            }

            request.getRequestDispatcher("modifierEnseignant.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEnseignants?error=Erreur lors de la récupération des informations de l'enseignant");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String dateNaissance = request.getParameter("dateNaissance");
        String mdp = request.getParameter("mdp");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("UPDATE Enseignant SET nom = :nom, prenom = :prenom, dateNaissance = :dateNaissance, mdp = :mdp WHERE email = :email");
            query.setParameter("nom", nom);
            query.setParameter("prenom", prenom);
            query.setParameter("dateNaissance", java.sql.Date.valueOf(dateNaissance));
            query.setParameter("mdp", mdp);
            query.setParameter("email", email);

            int result = query.executeUpdate();
            transaction.commit();

            if (result > 0) {
                response.sendRedirect("gererEnseignants?message=Modification réussie");
            } else {
                response.sendRedirect("gererEnseignants?error=Erreur lors de la mise à jour");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEnseignants?error=Erreur lors de la mise à jour de l'enseignant");
        }
    }
}
