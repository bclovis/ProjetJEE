<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" type="text/css" href="CSS/gererEtudiant.css">
</head>
<body>
<h1>Liste des Étudiants</h1>

<!-- Barre de recherche -->
<div class="search-bar">
    <form id="gerer-etudiant-form" method="get">
        <input type="text" id="search-input" name="keyword" value="${param.keyword}" placeholder="Rechercher un étudiant...">
        <button type="submit">Rechercher</button>
    </form>
</div>

<!-- Affichage des étudiants -->
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
    <c:forEach var="etudiant" items="${etudiants}">
        <tr>
            <td>${etudiant.email}</td>
            <td>${etudiant.nom}</td>
            <td>${etudiant.prenom}</td>
            <td>${etudiant.dateNaissance}</td>
            <td>
                <button class="btn btn-primary" data-email="${etudiant.email}" onclick="ouvrirFormModifier(this)">ModifierTest</button>
                <button class="btn btn-primary" data-email="${etudiant.email}" onclick="">SupprimerTest</button>
                <a href="modifierEtudiant?email=${etudiant.email}">Modifier</a> |
                <a href="supprimerEtudiant?email=${etudiant.email}" onclick="return confirm('Voulez-vous vraiment supprimer cet étudiant ?');">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Pagination -->
<div class="pagination">
    <c:forEach begin="1" end="${totalPages}" var="i">
        <a href="gererEtudiants?page=${i}&keyword=${param.keyword}"
           class="${i == currentPage ? 'current-page' : ''}">${i}</a>
    </c:forEach>
</div>

<a href="gererPersonnel.jsp">Retour à la gestion du personnel</a>
</body>
</html>
