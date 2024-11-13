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

@WebServlet(name = "GererEnseignantsServlet", value = "/gererEnseignants")
public class GererEnseignantsServlet extends HttpServlet {
    private static final int PAGE_SIZE = 20;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        String keyword = request.getParameter("keyword");
        String hql = "FROM Enseignant";
        if (keyword != null && !keyword.trim().isEmpty()) {
            hql += " WHERE nom LIKE :keyword OR prenom LIKE :keyword OR email LIKE :keyword";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Enseignant> query = session.createQuery(hql, Enseignant.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword + "%");
            }

            // Pagination
            query.setFirstResult((page - 1) * PAGE_SIZE);
            query.setMaxResults(PAGE_SIZE);
            List<Enseignant> enseignants = query.list();

            // Calcul du nombre total de pages
            Query<Long> countQuery = session.createQuery("SELECT COUNT(*) " + hql, Long.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                countQuery.setParameter("keyword", "%" + keyword + "%");
            }
            long totalResults = countQuery.uniqueResult();
            int totalPages = (int) Math.ceil((double) totalResults / PAGE_SIZE);

            // Set attributes for the JSP
            request.setAttribute("enseignants", enseignants);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);

            // Forward to JSP
            request.getRequestDispatcher("gererEnseignants.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("gererPersonnel.jsp?error=Erreur lors de la récupération des enseignants");
        }
    }
}
