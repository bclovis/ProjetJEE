package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(name = "ModifierEtudiantServlet", value = "/modifierEtudiant")
public class ModifierEtudiantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email != null && !email.isEmpty()) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Etudiant etudiant = session.get(Etudiant.class, email);

                if (etudiant != null) {
                    request.setAttribute("email", etudiant.getEmail());
                    request.setAttribute("nom", etudiant.getNom());
                    request.setAttribute("prenom", etudiant.getPrenom());
                    request.setAttribute("dateNaissance", new SimpleDateFormat("yyyy-MM-dd").format(etudiant.getDateNaissance()));
                    request.setAttribute("mdp", etudiant.getMdp());

                    request.getRequestDispatcher("modifierEtudiant.jsp").forward(request, response);
                } else {
                    response.sendRedirect("gererEtudiants?error=Étudiant%20introuvable");
                }
            }
        } else {
            response.sendRedirect("gererEtudiants?error=Email%20non%20fourni");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String dateNaissanceStr = request.getParameter("dateNaissance");
        String mdp = request.getParameter("mdp");

        if (email == null || nom == null || prenom == null || dateNaissanceStr == null || mdp == null) {
            response.sendRedirect("modifierEtudiant?email=" + email + "&error=Champs%20incomplets");
            return;
        }

        Date dateNaissance;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateNaissance = sdf.parse(dateNaissanceStr);
        } catch (ParseException e) {
            response.sendRedirect("modifierEtudiant?email=" + email + "&error=Format%20de%20date%20invalide");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Etudiant etudiant = session.get(Etudiant.class, email);

            if (etudiant != null) {
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setDateNaissance(dateNaissance);
                etudiant.setMdp(mdp);

                session.update(etudiant);
                transaction.commit();
                response.sendRedirect("gererEtudiants?message=Modification%20réussie");
            } else {
                response.sendRedirect("modifierEtudiant?email=" + email + "&error=Étudiant%20introuvable");
            }
        }
    }
}
