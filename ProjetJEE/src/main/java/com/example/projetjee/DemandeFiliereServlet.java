package com.example.projetjee;

import jakarta.persistence.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/demandeFiliere")
public class DemandeFiliereServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filiereStr = request.getParameter("filiere");
        String etudiantEmail = (String) request.getSession().getAttribute("email");

        if (filiereStr != null && etudiantEmail != null) {
            Filiere filiere = Filiere.valueOf(filiereStr.toUpperCase());

            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Transaction transaction = session.beginTransaction();

                // Création de la demande
                DemandeFiliere demande = new DemandeFiliere();
                demande.setEtudiant(session.get(Etudiant.class, etudiantEmail));
                demande.setFiliere(filiere);

                session.save(demande);
                transaction.commit();
            }

            response.sendRedirect("etudiant.jsp?message=Votre demande a été envoyée.");
        } else {
            response.sendRedirect("choixFiliere.jsp?error=Erreur lors de l'envoi de la demande.");
        }
    }
}

