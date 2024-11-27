package com.example.projetjee.Servlet;

import com.example.projetjee.HibernateUtil.HibernateUtil;
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

@WebServlet("/AfficherEmploiDuTempsServlet")
public class AfficherEmploiDuTempsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer le message de confirmation ou d'erreur depuis les paramètres
        String message = request.getParameter("message");
        if (message != null) {
            request.setAttribute("message", message);
        }

        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }

        String filiereNom = request.getParameter("filiere");
        String semaineParam = request.getParameter("semaine");
        int semaine = semaineParam != null && !semaineParam.isEmpty() ? Integer.parseInt(semaineParam) : -1;

        // Si aucune filière ou semaine n'est sélectionnée, définir des valeurs par défaut
        filiereNom = (filiereNom == null || filiereNom.isEmpty()) ? "Mathématiques" : filiereNom;
        semaine = (semaine <= 0) ? 1 : semaine;

        request.setAttribute("filiereNom", filiereNom);
        request.setAttribute("semaine", semaine);

        // Récupérer l'emploi du temps filtré
        List<Object[]> emploiDuTemps = getEmploiDuTemps(filiereNom, semaine);

        // Organiser les données par jour et heure
        Map<String, Map<String, String>> emploiParJourEtHeure = new HashMap<>();
        Map<String, Map<String, Integer>> emploiIdParJourEtHeure = new HashMap<>();
        for (Object[] row : emploiDuTemps) {
            String jour = (String) row[0];
            String heure = (String) row[1];
            String cours = (String) row[2];
            String professeur = (String) row[3];
            Integer coursId = (Integer) row[4]; // Ajouter l'ID du cours

            String contenu = cours + " (" + professeur + ")";
            emploiParJourEtHeure.putIfAbsent(jour, new HashMap<>());
            emploiParJourEtHeure.get(jour).put(heure, contenu);

            emploiIdParJourEtHeure.putIfAbsent(jour, new HashMap<>());
            emploiIdParJourEtHeure.get(jour).put(heure, coursId);
        }

        // Ajouter la pause de 12h à 14h
        for (String jour : List.of("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi")) {
            emploiParJourEtHeure.putIfAbsent(jour, new HashMap<>());
            emploiParJourEtHeure.get(jour).put("12h-14h", "Pause");
        }

        // Ajouter les données à la requête pour la JSP
        request.setAttribute("emploiParJourEtHeure", emploiParJourEtHeure);
        request.setAttribute("emploiIdParJourEtHeure", emploiIdParJourEtHeure);
        request.getRequestDispatcher("/emploiDuTemps.jsp").forward(request, response);
    }

    private List<Object[]> getEmploiDuTemps(String filiereNom, int semaine) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = """
            SELECT e.jour, e.heure, m.nom, p.nom, e.id
            FROM EmploiDuTemps e
            JOIN e.matiere m
            JOIN e.professeur p
            JOIN e.filiere f
            WHERE f.nom = :filiereNom
            AND e.semaineDebut <= :semaine AND e.semaineFin >= :semaine
            ORDER BY e.jour, e.heure
            """;
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("filiereNom", filiereNom);
        query.setParameter("semaine", semaine);
        List<Object[]> results = query.list();
        session.close();
        return results;
    }

}
