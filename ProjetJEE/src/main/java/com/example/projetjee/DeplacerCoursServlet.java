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

@WebServlet(name = "DeplacerCoursServlet", value = "/DeplacerCoursServlet")
public class DeplacerCoursServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coursId = Integer.parseInt(request.getParameter("coursId"));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            EmploiDuTemps cours = session.get(EmploiDuTemps.class, coursId);
            if (cours != null) {
                request.setAttribute("cours", cours);
                request.getRequestDispatcher("deplacerCours.jsp").forward(request, response);
            } else {
                response.sendRedirect("AfficherEmploiDuTempsServlet?error=Le cours n'existe pas");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coursId = Integer.parseInt(request.getParameter("coursId"));
        String jour = request.getParameter("jour");
        String heure = request.getParameter("heure");
        int semaine = Integer.parseInt(request.getParameter("semaine"));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            EmploiDuTemps cours = session.get(EmploiDuTemps.class, coursId);
            if (cours == null) {
                response.sendRedirect("AfficherEmploiDuTempsServlet?error=Le cours n'existe pas");
                return;
            }

            // Vérification des conflits avec le même professeur
            Query<Long> professeurConflictQuery = session.createQuery("""
                SELECT COUNT(e)
                FROM EmploiDuTemps e
                WHERE e.jour = :jour
                AND e.heure = :heure
                AND e.professeur.email = :professeurEmail
                AND e.semaineDebut <= :semaine
                AND e.semaineFin >= :semaine
                AND e.id != :coursId
            """, Long.class);
            professeurConflictQuery.setParameter("jour", jour);
            professeurConflictQuery.setParameter("heure", heure);
            professeurConflictQuery.setParameter("professeurEmail", cours.getProfesseur().getEmail());
            professeurConflictQuery.setParameter("semaine", semaine);
            professeurConflictQuery.setParameter("coursId", coursId);

            if (professeurConflictQuery.uniqueResult() > 0) {
                response.sendRedirect("AfficherEmploiDuTempsServlet?error=Le professeur a déjà un cours sur ce créneau");
                return;
            }

            // Vérification des conflits d'emploi du temps sur le créneau avec la même filière
            Query<Long> conflictQuery = session.createQuery("""
                SELECT COUNT(e)
                FROM EmploiDuTemps e
                WHERE e.jour = :jour
                AND e.heure = :heure
                AND e.semaineDebut <= :semaine
                AND e.semaineFin >= :semaine
                AND e.filiere.id = :filiereId
                AND e.id != :coursId
            """, Long.class);
            conflictQuery.setParameter("jour", jour);
            conflictQuery.setParameter("heure", heure);
            conflictQuery.setParameter("semaine", semaine);
            conflictQuery.setParameter("filiereId", cours.getFiliere().getId());
            conflictQuery.setParameter("coursId", coursId);

            if (conflictQuery.uniqueResult() > 0) {
                response.sendRedirect("AfficherEmploiDuTempsServlet?error=Un autre cours existe deja sur ce créneau");
                return;
            }

            // Déplacer le cours
            cours.setJour(jour);
            cours.setHeure(heure);
            cours.setSemaineDebut(semaine);
            cours.setSemaineFin(semaine);
            session.update(cours);
            transaction.commit();

            response.sendRedirect("AfficherEmploiDuTempsServlet?message=Cours déplace avec succes");
        }
    }
}
