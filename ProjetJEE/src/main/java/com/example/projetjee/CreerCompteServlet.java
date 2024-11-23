package com.example.projetjee;
import com.example.projetjee.HibernateUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Date;

@WebServlet(name="CreerCompteServlet", value="/CreerCompteServlet")
public class CreerCompteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeCompte = request.getParameter("typeCompte");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        Date dateNaissance = java.sql.Date.valueOf(request.getParameter("dateNaissance"));
        String mdp = request.getParameter("mdp");
        String filiere = "AUCUNE";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {
            if ("etudiant".equals(typeCompte)) {
                Etudiant etudiant = new Etudiant(email, nom, prenom, dateNaissance, mdp,filiere);
                session.save(etudiant);
            } else if ("enseignant".equals(typeCompte)) {
                Enseignant enseignant = new Enseignant(email, nom, prenom, dateNaissance, mdp);
                session.save(enseignant);
            }

            transaction.commit();
            response.sendRedirect("admin.jsp?message=Compte créé avec succès");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            response.sendRedirect("creationCompte.jsp?error=Erreur lors de la création du compte");
        } finally {
            session.close();
        }
    }
} 