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

@WebServlet(name = "ModifierEtudiantServlet", value = "/modifierEtudiant")
public class ModifierEtudiantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Etudiant> query = session.createQuery("FROM Etudiant WHERE email = :email", Etudiant.class);
            query.setParameter("email", email);
            Etudiant etudiant = query.uniqueResult();

            if (etudiant != null) {
                request.setAttribute("nom", etudiant.getNom());
                request.setAttribute("prenom", etudiant.getPrenom());
                request.setAttribute("dateNaissance", etudiant.getDateNaissance());
                request.setAttribute("mdp", etudiant.getMdp());
            }

            request.getRequestDispatcher("modifierEtudiant.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEtudiants?error=Erreur lors de la récupération des informations de l'étudiant");
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
            Query query = session.createQuery("UPDATE Etudiant SET nom = :nom, prenom = :prenom, dateNaissance = :dateNaissance, mdp = :mdp WHERE email = :email");
            query.setParameter("nom", nom);
            query.setParameter("prenom", prenom);
            query.setParameter("dateNaissance", java.sql.Date.valueOf(dateNaissance));
            query.setParameter("mdp", mdp);
            query.setParameter("email", email);

            int result = query.executeUpdate();
            transaction.commit();

            if (result > 0) {
                response.sendRedirect("gererEtudiants?message=Modification réussie");
            } else {
                response.sendRedirect("gererEtudiants?error=Erreur lors de la mise à jour");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEtudiants?error=Erreur lors de la mise à jour de l'étudiant");
        }
    }
}
