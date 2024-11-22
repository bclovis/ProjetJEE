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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@WebServlet(name = "AjouterCoursServlet", value = "/ajouterCours")
public class AjouterCoursServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Charger toutes les filières
            List<Filiere> filieres = session.createQuery("FROM Filiere", Filiere.class).list();
            request.setAttribute("filieres", filieres);

            // Charger toutes les matières
            List<Matiere> matieres = session.createQuery("FROM Matiere", Matiere.class).list();
            request.setAttribute("matieres", matieres);

            // Charger les enseignants
            List<Enseignant> enseignants = session.createQuery("FROM Enseignant", Enseignant.class).list();
            request.setAttribute("enseignants", enseignants);

            // Charger les jours, heures et semaines
            List<String> jours = List.of("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi");
            List<String> heures = List.of("08h-10h", "10h-12h", "12h-14h", "14h-16h", "16h-18h");
            List<Integer> semaines = IntStream.rangeClosed(1, 36).boxed().collect(Collectors.toList());

            request.setAttribute("jours", jours);
            request.setAttribute("heures", heures);
            request.setAttribute("semaines", semaines);

            request.getRequestDispatcher("ajouterCours.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filiereNom = request.getParameter("filiere");
        int semestre = Integer.parseInt(request.getParameter("semestre"));
        int matiereId = Integer.parseInt(request.getParameter("matiere"));
        String jour = request.getParameter("jour");
        String heure = request.getParameter("heure");
        int semaine = Integer.parseInt(request.getParameter("semaine"));
        String professeurEmail = request.getParameter("professeur");

        if (professeurEmail == null || professeurEmail.isEmpty()) {
            request.setAttribute("error", "Veuillez sélectionner un professeur.");
            doGet(request, response);
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Vérification si le professeur enseigne la matière
            Query<Long> professeurMatiereQuery = session.createQuery("""
            SELECT COUNT(pm)
            FROM ProfesseurMatiere pm
            WHERE pm.professeurEmail = :professeurEmail
            AND pm.matiereId = :matiereId
        """, Long.class);
            professeurMatiereQuery.setParameter("professeurEmail", professeurEmail);
            professeurMatiereQuery.setParameter("matiereId", matiereId);

            if (professeurMatiereQuery.uniqueResult() == 0) {
                request.setAttribute("error", "Le professeur sélectionné n'enseigne pas cette matière.");
                doGet(request, response);
                return;
            }

            // Vérification des conflits d'emploi du temps pour le même professeur
            Query<Long> professeurConflictQuery = session.createQuery("""
            SELECT COUNT(e)
            FROM EmploiDuTemps e
            WHERE e.jour = :jour
            AND e.heure = :heure
            AND e.professeur.email = :professeurEmail
        """, Long.class);
            professeurConflictQuery.setParameter("jour", jour);
            professeurConflictQuery.setParameter("heure", heure);
            professeurConflictQuery.setParameter("professeurEmail", professeurEmail);

            if (professeurConflictQuery.uniqueResult() > 0) {
                request.setAttribute("error", "Le professeur a déjà un cours programmé sur ce créneau.");
                doGet(request, response);
                return;
            }

            // Vérification des conflits d'emploi du temps pour la filière
            Query<Long> conflictQuery = session.createQuery("""
            SELECT COUNT(e)
            FROM EmploiDuTemps e
            WHERE e.jour = :jour
            AND e.heure = :heure
            AND e.semaineDebut <= :semaine
            AND e.semaineFin >= :semaine
            AND e.filiere.nom = :filiereNom
            AND e.semestre = :semestre
        """, Long.class);
            conflictQuery.setParameter("jour", jour);
            conflictQuery.setParameter("heure", heure);
            conflictQuery.setParameter("semaine", semaine);
            conflictQuery.setParameter("filiereNom", filiereNom);
            conflictQuery.setParameter("semestre", semestre);

            if (conflictQuery.uniqueResult() > 0) {
                request.setAttribute("error", "Un cours existe déjà sur ce créneau pour cette filière.");
                doGet(request, response);
                return;
            }

            // Ajouter le cours
            Matiere matiere = session.get(Matiere.class, matiereId);
            Filiere filiere = session.createQuery("FROM Filiere f WHERE f.nom = :nom", Filiere.class)
                    .setParameter("nom", filiereNom)
                    .uniqueResult();
            Enseignant professeur = session.get(Enseignant.class, professeurEmail);

            EmploiDuTemps newCours = new EmploiDuTemps();
            newCours.setJour(jour);
            newCours.setHeure(heure);
            newCours.setSemaineDebut(semaine);
            newCours.setSemaineFin(semaine);
            newCours.setMatiere(matiere);
            newCours.setFiliere(filiere);
            newCours.setSemestre(semestre);
            newCours.setProfesseur(professeur);

            session.save(newCours);
            transaction.commit();

            // Redirection vers la page emploi du temps
            response.sendRedirect("AfficherEmploiDuTempsServlet?message=Cours ajoute avec succes");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'ajout du cours.");
            doGet(request, response);
        }
    }

}
