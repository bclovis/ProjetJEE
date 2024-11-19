<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
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
<h1>Liste des Étudiants</h1>

<!-- Barre de recherche -->
<div class="search-bar">
    <form action="gererEtudiants" method="get">
        <input type="text" name="keyword" value="${param.keyword}" placeholder="Rechercher un étudiant...">
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

<script>
    // Gérer la recherche d'étudiants
    document.getElementById('search-form').addEventListener('submit', function (e) {
        e.preventDefault();
        const formData = new FormData(this);
        const params = new URLSearchParams(formData).toString();

        // Charger les résultats dans #dynamic-content
        fetch('gererEtudiants?' + params)
            .then(response => response.text())
            .then(html => {
                document.getElementById('dynamic-content').innerHTML = html;
            })
            .catch(err => console.error('Erreur lors de la recherche d\'étudiants :', err));
    });

    // Modifier un étudiant
    function modifierEtudiant(email) {
        loadPage('/modifierEtudiant', { email });
    }

    // Supprimer un étudiant avec confirmation
    function supprimerEtudiant(email) {
        if (confirm('Voulez-vous vraiment supprimer cet étudiant ?')) {
            fetch(`/supprimerEtudiant?email=${encodeURIComponent(email)}`, { method: 'POST' })
                .then(response => response.text())
                .then(html => {
                    document.getElementById('dynamic-content').innerHTML = html;
                })
                .catch(err => console.error('Erreur lors de la suppression de l\'étudiant :', err));
        }
    }

    // Gérer la pagination
    function navigatePage(page) {
        loadPage('/gererEtudiants', { page });
    }

</script>

</html>
