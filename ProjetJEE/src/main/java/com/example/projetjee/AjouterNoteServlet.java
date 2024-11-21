package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

public class AjouterNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'email du professeur depuis la session
        String emailProf = request.getSession().getAttribute("profEmail").toString();
        int matiereId = Integer.parseInt(request.getParameter("matiereId"));
        String emailEtudiant = request.getParameter("emailEtudiant");
        float note = Float.parseFloat(request.getParameter("note"));  // Utiliser un float

        // Créer une session Hibernate
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Vérifier si le professeur enseigne cette matière
            if (!professeurEnseigneMatiere(session, emailProf, matiereId)) {
                response.getWriter().write("Erreur : Vous ne pouvez pas saisir de note pour cette matière.");
                return;
            }

            // Vérifier si l'étudiant appartient à une filière compatible avec cette matière
            if (!etudiantDansFiliereMatiere(session, emailEtudiant, matiereId)) {
                response.getWriter().write("Erreur : L'étudiant n'appartient pas à la filière de cette matière.");
                return;
            }

            // Charger l'étudiant et la matière depuis la base de données
            Etudiant etudiant = session.get(Etudiant.class, emailEtudiant);
            Matiere matiere = session.get(Matiere.class, matiereId);

            if (etudiant == null || matiere == null) {
                response.getWriter().write("Erreur : Étudiant ou matière non trouvé.");
                return;
            }

            // Ajouter la note pour l'étudiant
            session.beginTransaction();
            Note noteEntity = new Note(note, etudiant, matiere);  // Passer les objets etudiant et matiere
            session.save(noteEntity);
            session.getTransaction().commit();

            response.getWriter().write("Note ajoutée avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Erreur lors de l'ajout de la note.");
        }
    }

    // Vérifie si le professeur enseigne cette matière
    private boolean professeurEnseigneMatiere(Session session, String emailProf, int matiereId) {
        String hql = "SELECT COUNT(*) FROM ProfesseurMatiere pm WHERE pm.enseignant.email = :emailProf AND pm.matiere.id = :matiereId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("emailProf", emailProf);
        query.setParameter("matiereId", matiereId);
        Long count = query.uniqueResult();
        return count > 0;
    }

    // Vérifie si l'étudiant appartient à une filière compatible avec cette matière
    private boolean etudiantDansFiliereMatiere(Session session, String emailEtudiant, int matiereId) {
        String hql = "SELECT COUNT(*) FROM MatiereFiliere mf JOIN mf.filiere f JOIN f.etudiants e WHERE mf.matiere.id = :matiereId AND e.email = :emailEtudiant";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("matiereId", matiereId);
        query.setParameter("emailEtudiant", emailEtudiant);
        Long count = query.uniqueResult();
        return count > 0;
    }
}
