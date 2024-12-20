package com.example.projetjee.Servlet;

import com.example.projetjee.HibernateUtil.HibernateUtil;
import com.example.projetjee.Model.Enseignant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GererEnseignantsServlet", value = "/gererEnseignants")
public class GererEnseignantsServlet extends HttpServlet {
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
        String hql = "FROM Enseignant";

        // Si un mot-clé est spécifié, ajouter la condition de recherche
        if (keyword != null && !keyword.trim().isEmpty()) {
            hql += " WHERE LOWER(email) LIKE :keyword OR LOWER(nom) LIKE :keyword OR LOWER(prenom) LIKE :keyword OR CAST(dateNaissance AS string) LIKE :keyword";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Enseignant> query = session.createQuery(hql, Enseignant.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            }

            // Pagination
            query.setFirstResult((page - 1) * PAGE_SIZE);
            query.setMaxResults(PAGE_SIZE);
            List<Enseignant> enseignants = query.list();

            // Calcul du nombre total de pages
            String countHql = "SELECT COUNT(*) FROM Enseignant";
            if (keyword != null && !keyword.trim().isEmpty()) {
                countHql += " WHERE LOWER(email) LIKE :keyword OR LOWER(nom) LIKE :keyword OR LOWER(prenom) LIKE :keyword OR CAST(dateNaissance AS string) LIKE :keyword";
            }

            Query<Long> countQuery = session.createQuery(countHql, Long.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                countQuery.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            }
            long totalResults = countQuery.uniqueResult();
            int totalPages = (int) Math.ceil((double) totalResults / PAGE_SIZE);

            // Set attributes pour la page JSP
            request.setAttribute("enseignants", enseignants);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("recherche", keyword);

            // Forward vers la page JSP
            request.getRequestDispatcher("gererEnseignants.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Erreur lors de la recherche de l'enseignant.");
            //request.setAttribute("error", "Erreur lors du chargement des enseignants : " + e.getMessage());
            //request.getRequestDispatcher("gererEnseignants.jsp").forward(request, response);
        }
    }
}
