package com.example.projetjee.Servlet;

import com.example.projetjee.HibernateUtil.HibernateUtil;
import com.example.projetjee.Model.DemandeFiliere;
import com.example.projetjee.Model.Etudiant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "GererDemandeFiliereServlet", value = "/gererDemandes")
public class GererDemandeFiliereServlet extends HttpServlet {
    private static final int PAGE_SIZE = 20;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String demandeIdStr = request.getParameter("demandeId");
        String commentaire = request.getParameter("commentaire");
        String keyword = request.getParameter("recherche");

        if (demandeIdStr == null) {
            response.sendRedirect("gererDemandes" + (keyword != null ? "?recherche=" + keyword : ""));
            return;
        }

        int demandeId;
        try {
            demandeId = Integer.parseInt(demandeIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("gererDemandes" + (keyword != null ? "?recherche=" + keyword : ""));
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            DemandeFiliere demande = session.get(DemandeFiliere.class, demandeId);

            if (demande == null) {
                response.sendRedirect("gererDemandes" + (keyword != null ? "?recherche=" + keyword : ""));
                return;
            }

            boolean demandeUpdated = false;

            if (action != null) {
                switch (action) {
                    case "accepter":
                        demande.setStatut("ACCEPTEE");
                        // Mettre à jour la filière de l'étudiant lorsque la demande est acceptée
                        Query<Etudiant> etudiantQuery = session.createQuery("FROM Etudiant WHERE email = :email", Etudiant.class);
                        etudiantQuery.setParameter("email", demande.getEtudiantEmail());
                        Etudiant etudiant = etudiantQuery.uniqueResult();
                        if (etudiant != null) {
                            etudiant.setFiliere(demande.getFiliere());
                            session.update(etudiant);
                        }
                        demandeUpdated = true;
                        break;
                    case "refuser":
                        demande.setStatut("REFUSEE");
                        demandeUpdated = true;
                        break;
                    case "supprimer":
                        session.delete(demande);
                        transaction.commit();
                        response.sendRedirect("gererDemandes" + (keyword != null ? "?recherche=" + keyword : ""));
                        return;
                    case "commenter":
                        if (commentaire != null && !commentaire.trim().isEmpty()) {
                            demande.setCommentaireAdmin(commentaire);
                            demandeUpdated = true;
                        }
                        break;
                }
            }

            if (demandeUpdated) {
                session.update(demande);
            }

            transaction.commit();
            response.sendRedirect("gererDemandes" + (keyword != null ? "?recherche=" + keyword : ""));
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors de la modification de la demande : " + e.getMessage());
            request.getRequestDispatcher("gererDemandes.jsp").forward(request, response);
        }
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
        String hql = "FROM DemandeFiliere";

        if (keyword != null && !keyword.trim().isEmpty()) {
            hql += " WHERE CAST(id AS string) LIKE :keyword " +
                    "OR LOWER(etudiantEmail) LIKE :keyword " +
                    "OR LOWER(CAST(filiere AS string)) LIKE :keyword " +
                    "OR LOWER(statut) LIKE :keyword " +
                    "OR CAST(dateDemande AS string) LIKE :keyword " +
                    "OR LOWER(commentaireAdmin) LIKE :keyword";
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<DemandeFiliere> query = session.createQuery(hql, DemandeFiliere.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                query.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            }

            // Pagination
            query.setFirstResult((page - 1) * PAGE_SIZE);
            query.setMaxResults(PAGE_SIZE);
            List<DemandeFiliere> demandes = query.list();

            // Calcul du nombre total de pages
            String countHql = "SELECT COUNT(*) FROM DemandeFiliere";
            if (keyword != null && !keyword.trim().isEmpty()) {
                countHql += " WHERE CAST(id AS string) LIKE :keyword " +
                        "OR LOWER(etudiantEmail) LIKE :keyword " +
                        "OR LOWER(CAST(filiere AS string)) LIKE :keyword " +
                        "OR LOWER(statut) LIKE :keyword " +
                        "OR CAST(dateDemande AS string) LIKE :keyword " +
                        "OR LOWER(commentaireAdmin) LIKE :keyword";
            }

            Query<Long> countQuery = session.createQuery(countHql, Long.class);
            if (keyword != null && !keyword.trim().isEmpty()) {
                countQuery.setParameter("keyword", "%" + keyword.toLowerCase() + "%");
            }
            long totalResults = countQuery.uniqueResult();
            int totalPages = (int) Math.ceil((double) totalResults / PAGE_SIZE);

            // Set attributes pour la page JSP
            request.setAttribute("demandes", demandes);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("recherche", keyword);

            // Forward vers la page JSP
            request.getRequestDispatcher("gererDemandes.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur lors du chargement des demandes : " + e.getMessage());
            request.getRequestDispatcher("gererDemandes.jsp").forward(request, response);
        }
    }
}
