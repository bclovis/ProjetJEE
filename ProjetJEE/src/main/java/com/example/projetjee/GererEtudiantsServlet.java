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

@WebServlet(name = "GererEtudiantsServlet", value = "/gererEtudiants")
public class GererEtudiantsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Etudiant> query = session.createQuery("FROM Etudiant", Etudiant.class);
            List<Etudiant> etudiants = query.list();
            request.setAttribute("etudiants", etudiants);
            request.getRequestDispatcher("gererEtudiants.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererPersonnel.jsp?error=Erreur lors de la récupération des étudiants");
        }
    }
}
