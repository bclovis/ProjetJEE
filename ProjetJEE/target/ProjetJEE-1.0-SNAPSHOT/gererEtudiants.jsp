<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
    <link rel="stylesheet" href="CSS/gererEtudiant.css">
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
        <th>Filière</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
<c:choose>
    <c:when test="${not empty etudiants}">
    <c:forEach var="etudiant" items="${etudiants}">
        <tr>
            <td>${etudiant.email}</td>
            <td>${etudiant.nom}</td>
            <td>${etudiant.prenom}</td>
            <td>
                <fmt:formatDate value="${etudiant.dateNaissance}" pattern="dd/MM/yyyy" /> <!-- Formater la date -->
            </td>
            <td>${etudiant.filiere}</td>
            <td>
                <button class="btn btn-primary" data-email="${etudiant.email}" onclick="ouvrirFormModifier(this)">ModifierTest</button>
                <button class="btn btn-primary" data-email="${etudiant.email}" onclick="">SupprimerTest</button>
                <form method="post" action="modifierEtudiant">
                    <input type="hidden" name="email" value="${etudiant.email}" />
                    <button type="submit">Modifier</button>
                </form>
                <form method="post" action="supprimerEtudiant">
                    <input type="hidden" name="email" value="${etudiant.email}" />
                    <button type="submit">Supprimer</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </c:when>
    <c:otherwise>
        <tr>
            <td colspan="6">Aucun étudiant trouvé.</td>
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
