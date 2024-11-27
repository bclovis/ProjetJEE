package com.example.projetjee.Servlet;

import com.example.projetjee.HibernateUtil.HibernateUtil;
import com.example.projetjee.Model.Etudiant;
import com.example.projetjee.Model.Matiere;
import com.example.projetjee.Model.Message;
import com.example.projetjee.Model.Note;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AjouterNoteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailProf = (String) request.getSession().getAttribute("email");
        String nomMatiere = request.getParameter("matiere");
        String emailEtudiant = request.getParameter("emailEtudiant");
        float note;

        try {
            note = Float.parseFloat(request.getParameter("note"));
        } catch (NumberFormatException e) {
            request.setAttribute("error", "La note doit être un nombre valide.");
            request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Vérifier la matière
            Matiere matiere = getMatiereByName(session, nomMatiere);
            if (matiere == null) {
                request.setAttribute("error", "Erreur : Matière non trouvée.");
                request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
                return;
            }

            // Vérifier les droits du professeur
            if (!professeurEnseigneMatiere(session, emailProf, matiere.getId())) {
                request.setAttribute("error", "Erreur : Vous ne pouvez pas saisir de note pour cette matière.");
                request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
                return;
            }

            // Vérifier si l'étudiant est dans la filière de la matière
            if (!etudiantDansFiliereMatiere(session, emailEtudiant, matiere.getId())) {
                request.setAttribute("error", "Erreur : L'étudiant n'appartient pas à la filière de cette matière.");
                request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
                return;
            }

            // Ajouter la note
            Transaction transaction = session.beginTransaction();
            Etudiant etudiant = session.get(Etudiant.class, emailEtudiant);
            Note noteEntity = new Note(note, etudiant, matiere);
            session.save(noteEntity);

            // Ajouter un message pour l'étudiant
            String messageContent = String.format(
                    "Vous avez reçu une nouvelle note : %.2f en %s.",
                    note, nomMatiere
            );
            Message message = new Message(emailProf, emailEtudiant, "Nouvelle Note", messageContent, LocalDateTime.now());
            session.save(message);

            transaction.commit();

            response.sendRedirect("ajouterNote?success=true");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de l'ajout de la note.");
            request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String emailProf = (String) request.getSession().getAttribute("email");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Récupérer les matières enseignées par le professeur
            String hql = "SELECT m FROM ProfesseurMatiere pm JOIN Matiere m ON pm.matiereId = m.id WHERE pm.professeurEmail = :emailProf";
            Query<Matiere> query = session.createQuery(hql, Matiere.class);
            query.setParameter("emailProf", emailProf);
            List<Matiere> matieres = query.getResultList();

            if (matieres.isEmpty()) {
                request.setAttribute("error", "Vous n'êtes assigné à aucune matière.");
                request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
                return;
            }

            request.setAttribute("matieres", matieres);

            // Transmettre les notes
            String hqlNotes = "FROM Note n JOIN FETCH n.etudiant e JOIN FETCH n.matiere m WHERE m.id IN :matiereIds";
            Query<Note> queryNotes = session.createQuery(hqlNotes, Note.class);
            queryNotes.setParameter("matiereIds", matieres.stream().map(Matiere::getId).collect(Collectors.toList()));
            List<Note> notes = queryNotes.getResultList();

            request.setAttribute("notes", notes);
            request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la récupération des données.");
            request.getRequestDispatcher("ajouterNote.jsp").forward(request, response);
        }
    }

    private Matiere getMatiereByName(Session session, String nomMatiere) {
        String hql = "FROM Matiere m WHERE m.nom = :nomMatiere";
        Query<Matiere> query = session.createQuery(hql, Matiere.class);
        query.setParameter("nomMatiere", nomMatiere);
        return query.uniqueResult();
    }

    private boolean professeurEnseigneMatiere(Session session, String emailProf, int matiereId) {
        String hql = "SELECT COUNT(*) FROM ProfesseurMatiere pm WHERE pm.professeurEmail = :emailProf AND pm.matiereId = :matiereId";
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("emailProf", emailProf);
        query.setParameter("matiereId", matiereId);
        return query.uniqueResult() > 0;
    }

    private boolean etudiantDansFiliereMatiere(Session session, String emailEtudiant, int matiereId) {
        String hql = """
        SELECT COUNT(*)
        FROM MatiereFiliere mf
        JOIN Filiere f ON mf.filiere.id = f.id
        WHERE mf.matiere.id = :matiereId
        AND f.nom = (SELECT e.filiere FROM Etudiant e WHERE e.email = :emailEtudiant)
    """;
        Query<Long> query = session.createQuery(hql, Long.class);
        query.setParameter("matiereId", matiereId);
        query.setParameter("emailEtudiant", emailEtudiant);
        return query.uniqueResult() > 0;
    }

}
