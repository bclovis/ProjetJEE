<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Gérer les Enseignants</title>
  <link rel="stylesheet" href="CSS/gererEtudiant.css">
</head>
<body>
<h1>Liste des Enseignants</h1>

<!-- Barre de recherche -->
<div class="search-bar">
  <form id="gerer-enseignant-form" method="get">
    <input type="text" id="search-input" name="keyword" value="${param.keyword}" placeholder="Rechercher un enseignant...">
    <button type="submit">Rechercher</button>
  </form>
</div>
s
<!-- Affichage des enseignants -->
<table>
  <thead>
  <tr>
    <th>Email</th>
    <th>Nom</th>
    <th>Prénom</th>
    <th>Date de Naissance</th>
    <th>Action</th>
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
          <td>
            <fmt:formatDate value="${enseignant.dateNaissance}" pattern="dd/MM/yyyy" /> <!-- Formater la date -->
          </td>
          <td>
            <button class="btn btn-primary" data-email="${etudiant.email}" onclick="ouvrirFormModifier(this)">ModifierTest</button>
            <button class="btn btn-primary" data-email="${etudiant.email}" onclick="">SupprimerTest</button>
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

<!-- Pagination -->
<div class="pagination">
  <c:forEach begin="1" end="${totalPages}" var="i">
    <a href="gererEtudiants?page=${i}&keyword=${param.keyword}"
       class="${i == currentPage ? 'current-page' : ''}">${i}</a>
  </c:forEach>
</div>

<a href="admin.jsp" class="return-home">Retour à l'accueil</a>
</body>
</html>
