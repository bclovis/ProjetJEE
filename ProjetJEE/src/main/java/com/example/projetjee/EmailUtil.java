package com.example.projetjee;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtil {

    private static final String SMTP_HOST = "smtp.gmail.com"; // Exemple avec Gmail
    private static final String SMTP_PORT = "587";
    private static final String USERNAME = "notificationcytech@gmail.com";
    private static final String PASSWORD = "notification.password";

    public static void sendEmail(String toEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email envoyé avec succès à " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
