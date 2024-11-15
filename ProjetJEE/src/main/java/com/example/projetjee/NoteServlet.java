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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute("email");

        // Vérification que l'utilisateur est connecté
        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Vérification de l'action demandée
        String action = request.getParameter("action"); // ex : "download" ou null

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Récupération des notes depuis la base de données
            String hql = "FROM Note WHERE etudiant.email = :email";
            Query<Note> query = session.createQuery(hql, Note.class);
            query.setParameter("email", email);
            List<Note> notes = query.list();
            transaction.commit();

            // Organisation des notes par matière et calcul des moyennes
            Map<Matiere, List<Note>> notesParMatiere = notes.stream().collect(Collectors.groupingBy(note -> note.getCours().getMatiere()));
            double moyenneGenerale = notes.stream().mapToDouble(Note::getNote).average().orElse(0);

            if ("download".equals(action)) {
                // Génération et envoi du relevé PDF
                String filePath = System.getProperty("java.io.tmpdir") + "/releve_notes.pdf";

                ReleveNotesGenerator.genererReleve(filePath, email, notesParMatiere, moyenneGenerale);

                // Répondre avec le fichier PDF
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=releve_notes.pdf");
                Files.copy(Paths.get(filePath), response.getOutputStream());
                response.getOutputStream().flush();

            } else {
                // Envoi des données à la JSP pour affichage
                request.setAttribute("notes", notes);
                request.setAttribute("moyenneGenerale", moyenneGenerale);
                request.setAttribute("notesParMatiere", notesParMatiere);
                request.getRequestDispatcher("/etudiant/voirNote.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement.");
        }
    }

}
