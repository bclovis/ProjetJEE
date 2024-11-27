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

@WebServlet(name = "ModifierEnseignantServlet", value = "/modifierEnseignant")
public class ModifierEnseignantServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email != null && !email.isEmpty()) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                Enseignant enseignant = session.get(Enseignant.class, email);

                if (enseignant != null) {
                    request.setAttribute("email", enseignant.getEmail());
                    request.setAttribute("nom", enseignant.getNom());
                    request.setAttribute("prenom", enseignant.getPrenom());
                    request.setAttribute("dateNaissance", new SimpleDateFormat("yyyy-MM-dd").format(enseignant.getDateNaissance()));
                    request.setAttribute("mdp", enseignant.getMdp());

                    request.getRequestDispatcher("modifierEnseignant.jsp").forward(request, response);
                } else {
                    response.sendRedirect("gererEnseignants?error=Enseignant%20introuvable");
                }
            }
        } else {
            response.sendRedirect("gererEnseignants?error=Email%20non%20fourni");
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
            response.sendRedirect("modifierEnseignant?email=" + email + "&error=Champs%20incomplets");
            return;
        }

        Date dateNaissance;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateNaissance = sdf.parse(dateNaissanceStr);
        } catch (ParseException e) {
            response.sendRedirect("modifierEnseignant?email=" + email + "&error=Format%20de%20date%20invalide");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            Enseignant enseignant = session.get(Enseignant.class, email);

            if (enseignant != null) {
                enseignant.setNom(nom);
                enseignant.setPrenom(prenom);
                enseignant.setDateNaissance(dateNaissance);
                enseignant.setMdp(mdp);

                session.update(enseignant);
                transaction.commit();
                response.sendRedirect("gererEnseignants?message=Modification%20réussie");
            } else {
                response.sendRedirect("modifierEnseignant?email=" + email + "&error=Enseignant%20introuvable");
            }
        }
    }
}
