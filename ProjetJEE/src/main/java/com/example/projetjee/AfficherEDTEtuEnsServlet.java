package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/AfficherEDTEtuEnsServlet")
public class AfficherEDTEtuEnsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer le rôle, l'email et la semaine sélectionnée
        String role = (String) request.getSession().getAttribute("role");
        String email = (String) request.getSession().getAttribute("email");
        String semaineParam = request.getParameter("semaine");
        int semaine = (semaineParam != null) ? Integer.parseInt(semaineParam) : 1; // Par défaut : semaine 1

        if (role == null || email == null) {
            // Si l'utilisateur n'est pas connecté, rediriger vers la page de connexion
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupérer l'emploi du temps filtré
        List<Object[]> emploiDuTemps = getEmploiDuTemps(role, email, semaine);

        // Organiser les données par jour et heure
        Map<String, Map<String, String>> emploiParJourEtHeure = new HashMap<>();
        for (Object[] row : emploiDuTemps) {
            String jour = (String) row[0];
            String heure = (String) row[1];
            String cours = (String) row[2];
            String professeur = (String) row[3];

            String contenu = cours + " (" + professeur + ")";
            emploiParJourEtHeure.putIfAbsent(jour, new HashMap<>());
            emploiParJourEtHeure.get(jour).put(heure, contenu);
        }

        // Ajouter les données à la requête pour la JSP
        request.setAttribute("emploiParJourEtHeure", emploiParJourEtHeure);
        request.setAttribute("semaine", semaine); // Pour réutiliser dans la JSP
        request.getRequestDispatcher("/EDTEtuEns.jsp").forward(request, response);
    }

    private List<Object[]> getEmploiDuTemps(String role, String email, int semaine) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql;

        if ("etudiant".equals(role)) {
            // Requête pour les étudiants
            hql = """
            SELECT e.jour, e.heure, m.nom, p.nom
            FROM EmploiDuTemps e
            JOIN e.matiere m
            JOIN e.professeur p
            JOIN Filiere f ON e.filiere.id = f.id
            WHERE f.nom = (SELECT et.filiere FROM Etudiant et WHERE et.email = :email)
            AND e.semaineDebut <= :semaine AND e.semaineFin >= :semaine
            ORDER BY e.jour, e.heure
        """;
        } else if ("enseignant".equals(role)) {
            // Requête pour les enseignants
            hql = """
            SELECT e.jour, e.heure, m.nom, p.nom
            FROM EmploiDuTemps e
            JOIN e.matiere m
            JOIN e.professeur p
            WHERE p.email = :email
            AND e.semaineDebut <= :semaine AND e.semaineFin >= :semaine
            ORDER BY e.jour, e.heure
        """;
        } else {
            session.close();
            throw new IllegalArgumentException("Rôle utilisateur non valide : " + role);
        }

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("email", email);
        query.setParameter("semaine", semaine);
        List<Object[]> results = query.list();
        session.close();
        return results;
    }

}
