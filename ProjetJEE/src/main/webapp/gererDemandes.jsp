<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Gérer les Demandes de Filière</title>
</head>
<body>
<h2>Gérer les Demandes de Filière</h2>

<form action="gererDemandes" method="get">
    <input type="text" name="recherche" placeholder="Rechercher une demande..." />
    <input type="submit" value="Rechercher" />
</form>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Email Étudiant</th>
        <th>Filière</th>
        <th>Statut</th>
        <th>Date de Demande</th>
        <th>Commentaire Admin</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${not empty demandes}">
            <c:forEach var="demande" items="${demandes}">
                <tr>
                    <td>${demande.id}</td>
                    <td>${demande.etudiantEmail}</td>
                    <td>${demande.filiere}</td>
                    <td>${demande.statut}</td>
                    <td>${demande.dateDemande}</td>
                    <td>${demande.commentaireAdmin}</td>
                    <td>
                        <form method="post" action="gererDemandes">
                            <input type="hidden" name="demandeId" value="${demande.id}" />
                            <input type="text" name="commentaire" placeholder="Commentaire admin..." />
                            <button type="submit" name="action" value="commenter">Ajouter/Modifier le commentaire</button>
                            <button type="submit" name="action" value="accepter">Accepter</button>
                            <button type="submit" name="action" value="refuser">Refuser</button>
                            <button type="submit" name="action" value="supprimer">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="7">Aucune demande trouvée.</td>
            </tr>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>
<a href="admin.jsp">Retour à l'accueil</a>
<a href="gererDemandes">Afficher toute la liste</a>
</body>
</html>
