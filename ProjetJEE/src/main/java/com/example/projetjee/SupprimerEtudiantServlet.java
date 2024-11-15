package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

@WebServlet(name = "SupprimerEtudiantServlet", value = "/supprimerEtudiant")
public class SupprimerEtudiantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Etudiant etudiant = session.get(Etudiant.class, email);
            if (etudiant != null) {
                session.delete(etudiant);
                transaction.commit();
                response.sendRedirect("gererEtudiants?message=Suppression réussie");
            } else {
                response.sendRedirect("gererEtudiants?error=Erreur lors de la suppression");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererEtudiants?error=Erreur lors de la suppression de l'étudiant");
        }
    }
}
