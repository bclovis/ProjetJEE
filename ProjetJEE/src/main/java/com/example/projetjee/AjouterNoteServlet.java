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
import java.util.stream.Collectors;

public class AjouterNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailProf = request.getSession().getAttribute("email").toString();
        String nomMatiere = request.getParameter("matiere"); // Récupère le nom de la matière
        String emailEtudiant = request.getParameter("emailEtudiant");
        float note = Float.parseFloat(request.getParameter("note"));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Récupérer la matière à partir de son nom
            Matiere matiere = getMatiereByName(session, nomMatiere);

            if (matiere == null) {
                request.setAttribute("error", "Erreur : Matière non trouvée.");
                request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
                return;
            }

            // Vérifier si le professeur enseigne cette matière
            if (!professeurEnseigneMatiere(session, emailProf, matiere.getId())) {
                request.setAttribute("error", "Erreur : Vous ne pouvez pas saisir de note pour cette matière.");
                request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
                return;
            }

            // Vérifier si l'étudiant appartient à une filière compatible avec cette matière
            if (!etudiantDansFiliereMatiere(session, emailEtudiant, matiere.getId())) {
                request.setAttribute("error", "Erreur : L'étudiant n'appartient pas à la filière de cette matière.");
                request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
                return;
            }

            // Charger l'étudiant depuis la base de données
            Etudiant etudiant = session.get(Etudiant.class, emailEtudiant);

            if (etudiant == null) {
                request.setAttribute("error", "Erreur : Étudiant non trouvé.");
                request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
                return;
            }

            // Ajouter la note pour l'étudiant
            session.beginTransaction();
            Note noteEntity = new Note(note, etudiant, matiere);
            session.save(noteEntity);
            session.getTransaction().commit();

            // Rediriger vers l'affichage des notes après ajout
            response.sendRedirect("ajouterNote");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'ajout de la note.");
            request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailProf = request.getSession().getAttribute("email").toString();

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Récupérer les matières enseignées par le professeur
            String hql = "SELECT m FROM ProfesseurMatiere pm JOIN pm.matiere m WHERE pm.professeurEmail.email = :emailProf";
            Query<Matiere> query = session.createQuery(hql, Matiere.class);
            query.setParameter("emailProf", emailProf);
            List<Matiere> matieres = query.getResultList();

            // Vérification si aucune matière n'est associée
            if (matieres.isEmpty()) {
                request.setAttribute("error", "Vous n'êtes assigné à aucune matière.");
                request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
                return;
            }

            // Ajouter les matières à la requête
            request.setAttribute("matieres", matieres);

            // Transmettre les notes existantes à la JSP
            String hqlNotes = "FROM Note n JOIN FETCH n.etudiant e JOIN FETCH n.matiere m WHERE m.id IN :matiereIds";
            Query<Note> queryNotes = session.createQuery(hqlNotes, Note.class);
            queryNotes.setParameter("matiereIds", matieres.stream().map(Matiere::getId).collect(Collectors.toList()));
            List<Note> notes = queryNotes.getResultList();

            request.setAttribute("notes", notes);
            request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des données.");
            request.getRequestDispatcher("/enseignant/ajouterNote.jsp").forward(request, response);
        }
    }


    // Méthode pour récupérer une matière à partir de son nom
    private Matiere getMatiereByName(Session session, String nomMatiere) {
        String hql = "FROM Matiere m WHERE m.nom = :nomMatiere";
        Query<Matiere> query = session.createQuery(hql, Matiere.class);
        query.setParameter("nomMatiere", nomMatiere);
        return query.uniqueResult();
    }

    private boolean professeurEnseigneMatiere(Session session, String emailProf, Long matiereId) {
        String hql = "SELECT COUNT(*) FROM ProfesseurMatiere pm WHERE pm.professeurEmail.email = :emailProf AND pm.matiere.id = :matiereId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("emailProf", emailProf);
        query.setParameter("matiereId", matiereId);
        Long count = query.uniqueResult();
        return count > 0;
    }

    private boolean etudiantDansFiliereMatiere(Session session, String emailEtudiant, Long matiereId) {
        String hql = "SELECT COUNT(*) " +
                "FROM MatiereFiliere mf " +
                "JOIN mf.filiere f " +
                "JOIN Etudiant e ON e.filiere.id = f.id " +
                "WHERE mf.matiere.id = :matiereId AND e.email = :emailEtudiant";

        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("matiereId", matiereId);
        query.setParameter("emailEtudiant", emailEtudiant);
        Long count = query.uniqueResult();
        return count > 0;
    }
}
