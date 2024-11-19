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

@WebServlet("/AfficherEmploiDuTempsServlet")
public class AfficherEmploiDuTempsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filiereNom = request.getParameter("filiere"); // Récupérer la filière sélectionnée
        String semaineParam = request.getParameter("semaine"); // Récupérer la semaine sélectionnée
        int semaine = semaineParam != null && !semaineParam.isEmpty() ? Integer.parseInt(semaineParam) : -1;

        // Ajouter la filière et la semaine comme attributs pour affichage
        request.setAttribute("filiereNom", filiereNom != null && !filiereNom.isEmpty() ? filiereNom : "Non spécifiée");
        request.setAttribute("semaine", semaine > 0 ? semaine : "Non spécifiée");

        // Récupérer l'emploi du temps pour la filière et la semaine spécifiée
        List<Object[]> emploiDuTemps = getEmploiDuTempsByFiliereEtSemaine(filiereNom, semaine);

        // Organiser les données par jour et heure pour l'affichage
        Map<String, Map<String, String>> emploiParJourEtHeure = new HashMap<>();
        for (Object[] row : emploiDuTemps) {
            String jour = (String) row[0];
            String heure = (String) row[1];
            String cours = (String) row[2];
            String professeur = (String) row[3];

            emploiParJourEtHeure.putIfAbsent(jour, new HashMap<>());
            emploiParJourEtHeure.get(jour).put(heure, cours + " (" + professeur + ")");
        }

        // Ajouter la pause de 12h à 14h pour chaque jour
        for (String jour : List.of("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi")) {
            emploiParJourEtHeure.putIfAbsent(jour, new HashMap<>());
            emploiParJourEtHeure.get(jour).put("12h-14h", "Pause");
        }

        // Ajouter les données à la requête pour l'affichage dans la JSP
        request.setAttribute("emploiParJourEtHeure", emploiParJourEtHeure);
        request.getRequestDispatcher("/emploiDuTemps.jsp").forward(request, response);
    }


    /**
     * Récupère l'emploi du temps pour une filière et une semaine spécifique.
     * On filtre les cours où la semaine sélectionnée est comprise entre semaineDebut et semaineFin.
     *
     * @param filiereNom Le nom de la filière.
     * @param semaine    La semaine sélectionnée.
     * @return Une liste contenant les informations sur l'emploi du temps.
     */
    private List<Object[]> getEmploiDuTempsByFiliereEtSemaine(String filiereNom, int semaine) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String hql = """
                SELECT e.jour, e.heure, m.nom, p.nom 
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
