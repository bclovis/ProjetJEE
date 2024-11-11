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

@WebServlet("/voirNotes")
public class NoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email"); // Récupérer l'email de l'étudiant connecté
        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Note WHERE etudiant.email = :etudiant_email";
            Query<Note> query = session.createQuery(hql, Note.class);
            query.setParameter("etudiant_email", email);
            List<Note> notes = query.list();

            // Passer les notes à la JSP pour affichage
            request.setAttribute("note", notes);
            request.getRequestDispatcher("/etudiant/voirNote.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des notes");
        }
    }
}