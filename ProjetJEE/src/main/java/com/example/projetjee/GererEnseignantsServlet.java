package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GererEnseignantsServlet", value = "/gererEnseignants")
public class GererEnseignantsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Enseignant> query = session.createQuery("FROM Enseignant", Enseignant.class);
            List<Enseignant> enseignants = query.list();
            request.setAttribute("enseignants", enseignants);
            request.getRequestDispatcher("gererEnseignants.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererPersonnel.jsp?error=Erreur lors de la récupération des enseignants");
        }
    }
}
