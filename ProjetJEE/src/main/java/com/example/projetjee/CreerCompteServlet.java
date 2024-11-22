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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

@WebServlet(name = "CreerCompteServlet", value = "/CreerCompteServlet")
public class CreerCompteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String typeCompte = request.getParameter("typeCompte");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String dateNaissanceStr = request.getParameter("dateNaissance");
        String mdp = request.getParameter("mdp");
        Filieres filiere = Filieres.AUCUNE;

        // Vérification du format de l'email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            request.setAttribute("error", "Le format de l'email est invalide");
            request.getRequestDispatcher("creationCompte.jsp").forward(request, response);
            return;
        }

        // Vérification de la date de naissance et de l'âge (au moins 18 ans)
        Date dateNaissance;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateNaissance = sdf.parse(dateNaissanceStr);
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(dateNaissance);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            if (age < 18) {
                request.setAttribute("error", "L'utilisateur doit avoir au moins 18 ans");
                request.getRequestDispatcher("creationCompte.jsp").forward(request, response);
                return;
            }
        } catch (ParseException e) {
            request.setAttribute("error", "Le format de la date de naissance est invalide");
            request.getRequestDispatcher("creationCompte.jsp").forward(request, response);
            return;
        }

        // Vérification si l'email existe déjà
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("SELECT COUNT(*) FROM Etudiant e WHERE e.email = :email", Long.class);
            query.setParameter("email", email);
            Long countEtudiant = query.uniqueResult();

            Query<Long> queryEnseignant = session.createQuery("SELECT COUNT(*) FROM Enseignant e WHERE e.email = :email", Long.class);
            queryEnseignant.setParameter("email", email);
            Long countEnseignant = queryEnseignant.uniqueResult();

            if (countEtudiant > 0 || countEnseignant > 0) {
                request.setAttribute("error", "L'email existe déjà");
                request.getRequestDispatcher("creationCompte.jsp").forward(request, response);
                return;
            }

            // Créer le compte si l'email n'existe pas déjà
            Transaction transaction = session.beginTransaction();
            try {
                if ("etudiant".equals(typeCompte)) {
                    Etudiant etudiant = new Etudiant(email, nom, prenom, dateNaissance, mdp, filiere);
                    session.save(etudiant);
                } else if ("enseignant".equals(typeCompte)) {
                    Enseignant enseignant = new Enseignant(email, nom, prenom, dateNaissance, mdp);
                    session.save(enseignant);
                }
                transaction.commit();
                response.sendRedirect("admin.jsp?message=Compte créé avec succès");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                request.setAttribute("error", "Erreur lors de la création du compte : " + e.getMessage());
                request.getRequestDispatcher("creationCompte.jsp").forward(request, response);
            }
        }
    }
}
