<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Messages</title>
</head>
<body>
<h1>Messagerie</h1>

<!-- Formulaire d'envoi de message -->
<form method="post" action="messageServlet">
  <label for="recipient">Destinataire :</label>
  <input type="email" id="recipient" name="recipient" required><br><br>

  <label for="subject">Sujet :</label>
  <input type="text" id="subject" name="subject" required><br><br>

  <label for="content">Message :</label><br>
  <textarea id="content" name="content" rows="5" cols="30" required></textarea><br><br>

  <button type="submit">Envoyer</button>
</form>

<hr>

<!-- Liste des messages reçus -->
<h2>Messages reçus</h2>
<c:if test="${empty messages}">
  <p>Aucun message reçu.</p>
</c:if>
<ul>
  <c:forEach var="message" items="${messages}">
    <li>
      <strong>De :</strong> ${message.sender}<br>
      <strong>Sujet :</strong> ${message.subject}<br>
      <strong>Date :</strong> ${message.sentAt}<br>
      <p>${message.content}</p>
    </li>
    <hr>
  </c:forEach>
</ul>
</body>
</html>
