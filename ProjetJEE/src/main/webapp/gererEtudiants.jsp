<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gérer les Étudiants</title>
</head>
<body>
<h2>Gérer les Étudiants</h2>

<form action="gererEtudiants" method="get">
    <input type="text" name="recherche" placeholder="Rechercher un étudiant..." />
    <input type="submit" value="Rechercher" />
</form>

<table border="1">
    <thead>
    <tr>
        <th>Email</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Date de Naissance</th>
        <th>Filière</th>
        <th>Actions</th>
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
                    <td>${etudiant.dateNaissance}</td>
                    <td>${etudiant.filiere}</td>
                    <td>
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
<a href="gererPersonnel.jsp">Retour à la gestion du personnel</a>
<a href="admin.jsp">Retour à l'accueil</a>
</body>
</html>
