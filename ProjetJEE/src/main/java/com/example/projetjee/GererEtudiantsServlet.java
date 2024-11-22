package com.example.projetjee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GererEtudiantsServlet", value = "/gererEtudiants")
public class GererEtudiantsServlet extends HttpServlet {
    private static final int PAGE_SIZE = 20;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 1; // Par défaut, la page est à 1 si le paramètre n'est pas valide
            }
        }

        String keyword = request.getParameter("recherche");
        String hql = "FROM Etudiant";

        // Si un mot-clé est spécifié, ajouter la condition de recherche
        if (keyword != null && !keyword.trim().isEmpty()) {
            hql += " WHERE LOWER(email) LIKE :keyword OR LOWER(nom) LIKE :keyword OR LOWER(prenom) LIKE :keyword OR CAST(dateNaissance AS string) LIKE :keyword OR LOWER(CAST(filiere AS string)) LIKE :keyword";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Etudiant> query = session.createQuery(hql, Etudiant.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            }

            // Pagination
            query.setFirstResult((page - 1) * PAGE_SIZE);
            query.setMaxResults(PAGE_SIZE);
            List<Etudiant> etudiants = query.list();

            // Calcul du nombre total de pages
            String countHql = "SELECT COUNT(*) FROM Etudiant";
            if (keyword != null && !keyword.trim().isEmpty()) {
                countHql += " WHERE LOWER(email) LIKE :keyword OR LOWER(nom) LIKE :keyword OR LOWER(prenom) LIKE :keyword OR CAST(dateNaissance AS string) LIKE :keyword OR LOWER(CAST(filiere AS string)) LIKE :keyword";
            }

            Query<Long> countQuery = session.createQuery(countHql, Long.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                countQuery.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            }
            long totalResults = countQuery.uniqueResult();
            int totalPages = (int) Math.ceil((double) totalResults / PAGE_SIZE);

            // Set attributes pour la page JSP
            request.setAttribute("etudiants", etudiants);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("recherche", keyword);

            // Redirection de la réponse AJAX vers la JSP partielle
            request.getRequestDispatcher("gererEtudiants.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erreur lors de la recherche de l'étudiant.");
            //response.sendRedirect("gererPersonnel.jsp?error=Erreur lors de la récupération des étudiants");
        }
    }
}
