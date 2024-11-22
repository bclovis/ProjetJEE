package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

@WebServlet(name = "SupprimerCoursServlet", value = "/SupprimerCoursServlet")
public class SupprimerCoursServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coursId = Integer.parseInt(request.getParameter("coursId"));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            EmploiDuTemps cours = session.get(EmploiDuTemps.class, coursId);
            if (cours != null) {
                session.delete(cours);
                transaction.commit();
                response.sendRedirect("AfficherEmploiDuTempsServlet?message=Cours supprime avec succes");
            } else {
                response.sendRedirect("AfficherEmploiDuTempsServlet?error=Le cours n'existe pas");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("AfficherEmploiDuTempsServlet?error=Erreur lors de la suppression du cours");
        }
    }
}
