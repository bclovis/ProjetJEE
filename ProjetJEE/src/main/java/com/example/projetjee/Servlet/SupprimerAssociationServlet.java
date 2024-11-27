package com.example.projetjee.Servlet;

import com.example.projetjee.HibernateUtil.HibernateUtil;
import com.example.projetjee.Model.ProfesseurMatiere;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;

public class SupprimerAssociationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer l'ID de l'association depuis le formulaire
        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            int id = Integer.parseInt(idStr); // Convertir en entier

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // Démarrer une transaction
                Transaction transaction = session.beginTransaction();

                // Récupérer l'association avec l'ID donné
                ProfesseurMatiere association = session.get(ProfesseurMatiere.class, id);

                // Supprimer l'association si elle existe
                if (association != null) {
                    session.delete(association);
                }

                // Valider la transaction
                transaction.commit();
            }
        }

        // Rediriger vers le servlet principal pour rafraîchir la liste
        response.sendRedirect("AssocierProfesseurMatiereServlet");
    }
}
