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
import java.time.LocalDateTime;
import java.util.List;

public class MessageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres du formulaire
        String sender = (String) request.getSession().getAttribute("email"); // L'utilisateur connecté
        String recipient = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");

        if (sender == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Vérification si l'email du destinataire existe dans la base de données
        boolean emailExists = false;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Vérifier dans Etudiant
            String hqlEtudiant = "FROM Etudiant WHERE email = :email";
            Query<Etudiant> queryEtudiant = session.createQuery(hqlEtudiant, Etudiant.class);
            queryEtudiant.setParameter("email", recipient);
            if (!queryEtudiant.list().isEmpty()) {
                emailExists = true;
            }

            // Vérifier dans Enseignant si non trouvé dans Etudiant
            if (!emailExists) {
                String hqlEnseignant = "FROM Enseignant WHERE email = :email";
                Query<Enseignant> queryEnseignant = session.createQuery(hqlEnseignant, Enseignant.class);
                queryEnseignant.setParameter("email", recipient);
                if (!queryEnseignant.list().isEmpty()) {
                    emailExists = true;
                }
            }
        }

        if (!emailExists) {
            // Si l'email n'existe pas, on définit un message d'erreur
            request.getSession().setAttribute("confirmationMessage", "L'email du destinataire n'existe pas.");
            response.sendRedirect("messageServlet");
            return;
        }

        // Création du message
        Message message = new Message(sender, recipient, subject, content, LocalDateTime.now());

        // Sauvegarde dans la base de données
        String confirmationMessage;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
            // Message de confirmation
            confirmationMessage = "Le message a été envoyé avec succès à " + recipient + ".";
        } catch (Exception e) {
            // En cas d'échec, définir un message d'erreur
            confirmationMessage = "Erreur lors de l'envoi du message. Veuillez réessayer.";
            e.printStackTrace();
        }

        // Stocker le message de confirmation dans la session (pour un affichage unique)
        request.getSession().setAttribute("confirmationMessage", confirmationMessage);

        // Redirection vers la même page
        response.sendRedirect("messageServlet");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération de l'email de l'utilisateur connecté
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupérer le numéro de page (par défaut : 1)
        int page = 1;
        int messagesPerPage = 5; // Nombre maximum de messages par page
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Message> messages;
        long totalMessages;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Récupération du nombre total de messages pour l'utilisateur
            String countHql = "SELECT COUNT(*) FROM Message WHERE recipient = :email";
            Query<Long> countQuery = session.createQuery(countHql, Long.class);
            countQuery.setParameter("email", email);
            totalMessages = countQuery.uniqueResult();

            // Récupération des messages paginés
            String hql = "FROM Message WHERE recipient = :email ORDER BY sentAt DESC";
            Query<Message> query = session.createQuery(hql, Message.class);
            query.setParameter("email", email);
            query.setFirstResult((page - 1) * messagesPerPage);
            query.setMaxResults(messagesPerPage);
            messages = query.list();
        }

        String confirmationMessage = (String) request.getSession().getAttribute("confirmationMessage");
        if (confirmationMessage != null) {
            request.setAttribute("confirmationMessage", confirmationMessage);
            request.getSession().removeAttribute("confirmationMessage"); // Effacer après affichage
        }

        // Passage des données à la JSP
        request.setAttribute("messages", messages);
        request.setAttribute("confirmationMessage", confirmationMessage);
        request.getSession().removeAttribute("confirmationMessage"); // Effacer après affichage
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", (int) Math.ceil((double) totalMessages / messagesPerPage));

        request.getRequestDispatcher("/messagerie.jsp").forward(request, response);
    }
}

