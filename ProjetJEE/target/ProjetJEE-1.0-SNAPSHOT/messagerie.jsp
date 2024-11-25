<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Messagerie</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f2f2f2;
      margin: 0;
      padding: 0;
    }
    .container {
      width: 80%;
      margin: auto;
      padding: 20px;
      background-color: #ffffff;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      margin-top: 30px;
    }
    .message-block {
      border: 1px solid #ddd;
      border-radius: 8px;
      margin-bottom: 20px;
      padding: 15px;
      background-color: #f9f9f9;
    }
    .message-block h3 {
      margin: 0 0 10px 0;
      color: #333;
    }
    .message-block p {
      margin: 5px 0;
    }
    .confirmation-message {
      margin-bottom: 20px;
      padding: 15px;
      background-color: #ffffff;
      border: 1px solid #aaaaaa;
      color: #000000;
      border-radius: 8px;
      animation: fadeOut 8s forwards; /* Animation pour masquer le message */
    }
    @keyframes fadeOut {
      0% {
        opacity: 1;
      }
      80% {
        opacity: 0;
      }
      100% {
        opacity: 0;
        display: none;
      }
    }
    .form-container {
      margin-top: 30px;
      padding: 20px;
      background-color: #f9f9f9;
      border-radius: 8px;
      border: 1px solid #ddd;
    }
    .form-container h2 {
      margin: 0 0 20px 0;
    }
    input, textarea {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
      box-sizing: border-box;
    }
    button {
      padding: 10px 20px;
      background-color: #4CAF50;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    button:hover {
      background-color: #45a049;
    }
    .actions {
      margin-top: 30px;
      text-align: center;
    }
    .pagination {
      display: flex;
      justify-content: center;
      margin-top: 20px;
    }
    .pagination a {
      margin: 0 5px;
      padding: 8px 16px;
      text-decoration: none;
      color: #333;
      border: 1px solid #ddd;
      border-radius: 4px;
    }
    .pagination a.active {
      background-color: #4CAF50;
      color: white;
      border: 1px solid #4CAF50;
    }
    .pagination a:hover {
      background-color: #ddd;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Messagerie</h1>
  <c:if test="${not empty confirmationMessage}">
    <div class="confirmation-message">
        ${confirmationMessage}
    </div>
  </c:if>


  <!-- Bloc pour afficher les messages reçus -->
  <c:choose>
    <c:when test="${empty messages}">
      <p>Aucun message reçu.</p>
    </c:when>
    <c:otherwise>
      <c:forEach items="${messages}" var="message">
        <div class="message-block">
          <h3>Sujet : ${message.subject}</h3>
          <p><strong>De :</strong> ${message.sender}</p>
          <p><strong>Reçu le :</strong> ${message.sentAt}</p>
          <p>${message.content}</p>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>

  <!-- Formulaire pour envoyer un message -->
  <div class="form-container">
    <h2>Envoyer un nouveau message</h2>
    <form action="messageServlet" method="post">
      <label for="recipient">Destinataire :</label>
      <input type="text" id="recipient" name="recipient" placeholder="Entrez l'email du destinataire" required>

      <label for="subject">Sujet :</label>
      <input type="text" id="subject" name="subject" placeholder="Entrez le sujet du message" required>

      <label for="content">Contenu :</label>
      <textarea id="content" name="content" rows="5" placeholder="Écrivez votre message ici..." required></textarea>

      <button type="submit">Envoyer</button>
    </form>
  </div>

  <!-- Pagination -->
  <c:if test="${totalPages > 1}">
    <div class="pagination">
      <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="messageServlet?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
      </c:forEach>
    </div>
  </c:if>
</div>

  <!-- Bouton Retour à l'accueil étudiant -->
  <div class="actions">
    <button onclick="window.location.href='etudiant.jsp'">Retour à l'accueil étudiant</button>
  </div>
</div>
</body>
</html>
