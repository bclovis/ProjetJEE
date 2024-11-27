<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Messagerie</title>
  <link rel="stylesheet" href="CSS/messagerie.css?v=3">
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

    <%
      // Récupérer le rôle de l'utilisateur depuis la session
      String role = (String) session.getAttribute("role");
      String accueilPage = "etudiant.jsp"; // Par défaut, rediriger vers la page étudiant
      if ("enseignant".equals(role)) {
        accueilPage = "enseignant.jsp";
      }
    %>
    <center><a href="<%= accueilPage %>" class="return-home">Retour à l'accueil</a></center>
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
</body>
</html>
