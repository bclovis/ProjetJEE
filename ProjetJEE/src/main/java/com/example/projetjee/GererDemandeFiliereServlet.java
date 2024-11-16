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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/gererDemandeFiliere")
public class GererDemandeFiliereServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Récupère toutes les demandes de filière
            List<DemandeFiliere> demandes = session.createQuery("FROM DemandeFiliere", DemandeFiliere.class).list();
            System.out.println("Nombre de demandes : " + demandes.size());

            // Récupère les filières actuelles des étudiants pour afficher dans une colonne supplémentaire
            Query<Object[]> query = session.createQuery(
                    "SELECT e.email, e.filiere FROM Etudiant e", Object[].class);
            List<Object[]> etudiantFiliere = query.list();

            // Transforme les données en une Map pour un accès facile dans la JSP
            Map<String, String> etudiantFiliereMap = new HashMap<>();
            for (Object[] row : etudiantFiliere) {
                etudiantFiliereMap.put((String) row[0], (String) row[1]);
            }

            // Attache les données à la requête
            request.setAttribute("demandes", demandes);
            request.setAttribute("etudiantFiliereMap", etudiantFiliereMap);
        }

        // Redirige vers la JSP des demandes
        request.getRequestDispatcher("gererDemandes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int demandeId = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        String commentaire = request.getParameter("commentaire");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            // Récupère la demande correspondante
            DemandeFiliere demande = session.get(DemandeFiliere.class, demandeId);

            if (demande != null) {
                if ("accepter".equals(action)) {
                    demande.setStatut(StatutDemande.ACCEPTE);

                    // Logique pour mettre à jour la filière de l'étudiant
                    Etudiant etudiant = demande.getEtudiant();
                    etudiant.setFiliere(demande.getFiliere());
                    session.update(etudiant);
                } else if ("refuser".equals(action)) {
                    demande.setStatut(StatutDemande.REFUSE);
                }

                if (commentaire != null && !commentaire.isEmpty()) {
                    demande.setCommentaireAdmin(commentaire);
                }

                session.update(demande);
                transaction.commit();
            }
        }

        // Redirige vers la page des demandes après traitement
        response.sendRedirect("gererDemandes.jsp?message=Demande traitée avec succès.");
    }
}
