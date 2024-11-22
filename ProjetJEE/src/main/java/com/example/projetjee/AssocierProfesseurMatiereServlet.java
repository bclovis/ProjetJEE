package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class AssocierProfesseurMatiereServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer le mot-clé pour la recherche
        String keyword = request.getParameter("keyword");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Récupérer les professeurs
            List<Enseignant> professeurs = session.createQuery("from Enseignant", Enseignant.class).list();
            request.setAttribute("professeurs", professeurs);

            // Récupérer les matières
            List<Matiere> matieres = session.createQuery("from Matiere", Matiere.class).list();
            request.setAttribute("matieres", matieres);

            // Requête pour récupérer les associations avec un filtre sur le mot-clé
            String hql = "select pm.id, e.nom, e.prenom, m.nom from ProfesseurMatiere pm " +
                    "join Enseignant e on pm.professeurEmail = e.email " +
                    "join Matiere m on pm.matiereId = m.id";

            if (keyword != null && !keyword.trim().isEmpty()) {
                hql += " WHERE e.nom LIKE :keyword OR e.prenom LIKE :keyword OR m.nom LIKE :keyword";
            }

            Query<Object[]> query = session.createQuery(hql, Object[].class);

            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword + "%");
            }

            List<Object[]> associations = query.list();
            request.setAttribute("associations", associations);
            request.setAttribute("keyword", keyword); // Pour réutiliser dans la JSP
        }

        // Rediriger vers la JSP
        request.getRequestDispatcher("associerProfesseurMatiere.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String professeurEmail = request.getParameter("professeur_email");
        String matiereIdStr = request.getParameter("matiere_id");

        // Validation des champs vides
        if (professeurEmail == null || professeurEmail.isEmpty() ||
                matiereIdStr == null || matiereIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Veuillez sélectionner un professeur et une matière.");
            doGet(request, response);
            return;
        }

        int matiereId = Integer.parseInt(matiereIdStr);

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Vérifier si l'association existe déjà
            List<ProfesseurMatiere> existantes = session.createQuery(
                            "from ProfesseurMatiere where professeurEmail = :email and matiereId = :matiereId",
                            ProfesseurMatiere.class)
                    .setParameter("email", professeurEmail)
                    .setParameter("matiereId", matiereId)
                    .list();

            if (!existantes.isEmpty()) {
                request.setAttribute("errorMessage", "Cette association existe déjà !");
                doGet(request, response);
                return;
            }

            // Créer une nouvelle association
            Transaction transaction = session.beginTransaction();
            ProfesseurMatiere association = new ProfesseurMatiere();
            association.setProfesseurEmail(professeurEmail);
            association.setMatiereId(matiereId);
            session.save(association);
            transaction.commit();
        }

        // Rediriger vers la page JSP pour afficher les mises à jour
        doGet(request, response);
    }
}
