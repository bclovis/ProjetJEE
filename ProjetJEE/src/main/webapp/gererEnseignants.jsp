<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Gérer les Enseignants</title>
</head>
<body>
<h2>Gérer les Enseignants</h2>

<form action="gererEnseignants" method="get">
  <input type="text" name="recherche" placeholder="Rechercher un enseignant..." />
  <input type="submit" value="Rechercher" />
</form>

<table border="1">
  <thead>
  <tr>
    <th>Email</th>
    <th>Nom</th>
    <th>Prénom</th>
    <th>Date de Naissance</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:choose>
    <c:when test="${not empty enseignants}">
      <c:forEach var="enseignant" items="${enseignants}">
        <tr>
          <td>${enseignant.email}</td>
          <td>${enseignant.nom}</td>
          <td>${enseignant.prenom}</td>
          <td>${enseignant.dateNaissance}</td>
          <td>
            <form method="post" action="modifierEnseignant">
              <input type="hidden" name="email" value="${enseignant.email}" />
              <button type="submit">Modifier</button>
            </form>
            <form method="post" action="supprimerEnseignant">
              <input type="hidden" name="email" value="${enseignant.email}" />
              <button type="submit">Supprimer</button>
            </form>
          </td>
        </tr>
      </c:forEach>
    </c:when>
    <c:otherwise>
      <tr>
        <td colspan="5">Aucun enseignant trouvé.</td>
      </tr>
    </c:otherwise>
  </c:choose>
  </tbody>
</table>
<a href="gererPersonnel.jsp">Retour à la gestion du personnel</a>
<a href="admin.jsp">Retour à l'accueil</a>
</body>
</html>
