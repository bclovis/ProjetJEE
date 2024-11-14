package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        boolean isAuthenticated = authenticateUser(email, password, role);

        if (isAuthenticated) {
            request.getSession().setAttribute("email", email);
            // Redirige vers la page appropriée selon le rôle
            switch (role) {
                case "etudiant":
                    response.sendRedirect("etudiant.jsp");
                    break;
                case "enseignant":
                    response.sendRedirect("enseignant.jsp");
                    break;
                case "admin":
                    response.sendRedirect("admin.jsp");
                    break;
            }
        } else {
            // Affiche un message d'erreur si la connexion échoue
            request.setAttribute("errorMessage", "Identifiants incorrects. Veuillez réessayer.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private boolean authenticateUser(String email, String password, String role) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql;
            switch (role) {
                case "etudiant":
                    hql = "FROM Etudiant WHERE email = :email AND mdp = :password";
                    break;
                case "enseignant":
                    hql = "FROM Enseignant WHERE email = :email AND mdp = :password";
                    break;
                case "admin":
                    hql = "FROM Admin WHERE email = :email AND mdp = :password";
                    break;
                default:
                    System.out.println("Rôle non valide : " + role);
                    return false;
            }

            Query<?> query = session.createQuery(hql);
            query.setParameter("email", email);
            query.setParameter("password", password);

            System.out.println("Exécution de la requête avec email = " + email + ", password = " + password + ", role = " + role);
            Object result = query.uniqueResult();
            System.out.println("Résultat de la requête : " + result);

            return result != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
