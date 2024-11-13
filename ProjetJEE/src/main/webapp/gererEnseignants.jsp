<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Enseignants</title>
  <style>
    .search-bar {
      margin-bottom: 15px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    table, th, td {
      border: 1px solid black;
    }
    .pagination a {
      margin: 0 5px;
      text-decoration: none;
    }
    .pagination a.current-page {
      font-weight: bold;
    }
  </style>
</head>
<body>
<h1>Liste des Enseignants</h1>

<!-- Barre de recherche -->
<div class="search-bar">
  <form action="gererEnseignants" method="get">
    <input type="text" name="keyword" value="${param.keyword}" placeholder="Rechercher un enseignant...">
    <button type="submit">Rechercher</button>
  </form>
</div>

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
  <c:forEach var="enseignant" items="${enseignants}">
    <tr>
      <td>${enseignant.email}</td>
      <td>${enseignant.nom}</td>
      <td>${enseignant.prenom}</td>
      <td>${enseignant.dateNaissance}</td>
      <td>
        <a href="modifierEnseignant?email=${enseignant.email}">Modifier</a> |
        <a href="supprimerEnseignant?email=${enseignant.email}" onclick="return confirm('Voulez-vous vraiment supprimer cet enseignant ?');">Supprimer</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<!-- Pagination -->
<div class="pagination">
  <c:forEach begin="1" end="${totalPages}" var="i">
    <a href="gererEnseignants?page=${i}&keyword=${param.keyword}"
       class="${i == currentPage ? 'current-page' : ''}">${i}</a>
  </c:forEach>
</div>

<a href="gererPersonnel.jsp">Retour à la gestion du personnel</a>
</body>
</html>
