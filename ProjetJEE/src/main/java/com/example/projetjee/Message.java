package com.example.projetjee;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Clé primaire

    @Column(nullable = false)
    private String sender; // Expéditeur du message

    @Column(nullable = false)
    private String recipient; // Destinataire du message

    @Column(nullable = false)
    private String subject; // Sujet du message

    @Column(nullable = false, length = 1000)
    private String content; // Contenu du message

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt; // Date d'envoi du message

    // Constructeurs
    public Message() {
    }

    public Message(String sender, String recipient, String subject, String content, LocalDateTime sentAt) {
        this.sender = sender;
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
        this.sentAt = sentAt;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}


