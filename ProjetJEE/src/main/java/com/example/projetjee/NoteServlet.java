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

public class NoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération de l'email de l'étudiant connecté depuis la session
        String email = "etudiant.test@gmail.com";

        // Vérification que l'étudiant est bien connecté
        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Ouverture de la session Hibernate et démarrage d'une transaction
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Création de la requête pour récupérer les notes de l'étudiant
            String hql = "FROM Note WHERE etudiant = :email";
            Query<Note> query = session.createQuery(hql, Note.class);
            query.setParameter("email", email);

            // Exécution de la requête et récupération de la liste des notes
            List<Note> notes = query.list();
            transaction.commit();

            // Vérification que des notes ont été récupérées
            if (notes.isEmpty()) {
                System.out.println("Aucune note trouvée pour l'étudiant avec l'email : " + email);
            } else {
                System.out.println("Notes récupérées : " + notes);
            }

            // Envoi des notes à la JSP
            request.setAttribute("notes", notes);
            request.getRequestDispatcher("/etudiant/voirNote.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erreur lors de la récupération des notes.");
        }
    }
}
