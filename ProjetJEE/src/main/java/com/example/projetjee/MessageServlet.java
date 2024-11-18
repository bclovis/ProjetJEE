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

        // Création du message
        Message message = new Message(sender, recipient, subject, content, LocalDateTime.now());

        // Sauvegarde dans la base de données
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        }

        // Redirection vers la même page pour voir les messages mis à jour
        response.sendRedirect("etudiant/messagerie.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération de l'email de l'utilisateur connecté
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupération des messages reçus
        List<Message> messages;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Message WHERE recipient = :email ORDER BY sentAt DESC";
            Query<Message> query = session.createQuery(hql, Message.class);
            query.setParameter("email", email);
            messages = query.list();
        }

        // Passage des messages à la JSP
        request.setAttribute("messages", messages);
        request.getRequestDispatcher("/etudiant/messagerie.jsp").forward(request, response);
    }
}
