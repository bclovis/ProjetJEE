package com.example.projetjee.Servlet;

import com.example.projetjee.HibernateUtil.HibernateUtil;
import com.example.projetjee.Model.Etudiant;
import com.example.projetjee.Model.Matiere;
import com.example.projetjee.Model.Note;
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

@WebServlet(name = "NoteServlet", value = "/voirNotes")
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

            // Récupérer les informations personnelles de l'étudiant
            String studentHql = "FROM Etudiant WHERE email = :email";
            Query<Etudiant> studentQuery = session.createQuery(studentHql, Etudiant.class);
            studentQuery.setParameter("email", email);
            Etudiant etudiant = (Etudiant) studentQuery.uniqueResult();

            transaction.commit();

            // Organisation des notes par matière
            Map<Matiere, List<Note>> notesParMatiere = notes.stream()
                    .collect(Collectors.groupingBy(Note::getMatiere));

            // Calcul des moyennes par matière
            Map<Matiere, Double> moyennesParMatiere = new HashMap<>();
            for (Matiere matiere : notesParMatiere.keySet()) {
                List<Note> notesDeMatiere = notesParMatiere.get(matiere);
                double somme = notesDeMatiere.stream().mapToDouble(Note::getNote).sum();
                double moyenne = somme / notesDeMatiere.size();
                moyenne = Math.round(moyenne * 100.0) / 100.0;
                moyennesParMatiere.put(matiere, moyenne);
            }

            // Calcul de la moyenne générale
            double moyenneGenerale = notes.stream().mapToDouble(Note::getNote).average().orElse(0);
            moyenneGenerale = Math.round(moyenneGenerale * 100.0) / 100.0;

            if ("download".equals(action)) {
                // Génération et envoi du relevé PDF
                String filePath = System.getProperty("java.io.tmpdir") + "/releve_notes.pdf";

                // Passer les informations de l'étudiant (nom, prénom, date de naissance) au générateur de PDF
                ReleveNotesGenerator.genererReleve(filePath, etudiant, notesParMatiere, moyenneGenerale);

                // Répondre avec le fichier PDF
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=releve_notes.pdf");
                Files.copy(Paths.get(filePath), response.getOutputStream());
                response.getOutputStream().flush();

            } else {
                // Envoi des données à la JSP pour affichage
                request.setAttribute("notes", notes);
                request.setAttribute("moyenneGenerale", moyenneGenerale);
                request.setAttribute("rapport", genererRapport(moyenneGenerale));
                request.setAttribute("notesParMatiere", notesParMatiere);
                request.setAttribute("moyennesParMatiere", moyennesParMatiere); // Passage des moyennes par matière
                request.getRequestDispatcher("/voirNote.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors du traitement.");
        }
    }

    public static String genererRapport(double moyenneGenerale) {
        String rapport;

        if (moyenneGenerale >= 17) {
            rapport = "Des résultats remarquables. Le travail et l'engagement sont exemplaires. Il faut continuer sur cette voie pour maintenir ce niveau d'excellence.";
        } else if (moyenneGenerale >= 14) {
            rapport = "Très bon travail, continuez à maintenir ce niveau d'effort et de motivation pour atteindre des performances exceptionnelles.";
        } else if (moyenneGenerale >= 10) {
            rapport = "Les résultats sont globalement bons, mais il est possible d'atteindre un niveau encore plus élevé avec plus de rigueur et d'engagement.";
        } else if (moyenneGenerale >= 7) {
            rapport = "Des progrès sont attendus. Une implication plus constante et un travail plus méthodique permettront d'atteindre des résultats plus satisfaisants.";
        } else {
            rapport = "Les résultats sont en dessous des attentes. Il est essentiel de prendre des initiatives pour mieux maîtriser les matières abordées.";
        }

        return rapport;
    }

}
