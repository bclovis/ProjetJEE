<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Associer un Professeur à une Matière</title>
</head>
<body>
<h1>Associer un Professeur à une Matière</h1>

<!-- Formulaire pour associer un professeur et une matière -->
<form action="AssocierProfesseurMatiereServlet" method="post">
    <label for="professeur">Professeur :</label>
    <select name="professeur_email" id="professeur">
        <option value="">-- Choisir un professeur --</option>
        <c:forEach var="prof" items="${professeurs}">
            <option value="${prof.email}">${prof.nom} ${prof.prenom}</option>
        </c:forEach>
    </select>
    <br>

    <label for="matiere">Matière :</label>
    <select name="matiere_id" id="matiere">
        <option value="">-- Choisir une matière --</option>
        <c:forEach var="matiere" items="${matieres}">
            <option value="${matiere.id}">${matiere.nom}</option>
        </c:forEach>
    </select>
    <br>

    <button type="submit">Associer</button>
</form>

<!-- Barre de recherche -->
<form action="AssocierProfesseurMatiereServlet" method="get">
    <input type="text" name="keyword" placeholder="Rechercher prof/matière" value="${keyword}">
    <button type="submit">Rechercher</button>
</form>

<!-- Bouton pour réinitialiser la recherche -->
<form action="AssocierProfesseurMatiereServlet" method="get" style="display: inline;">
    <button type="submit">Réinitialiser</button>
</form>

<!-- Tableau des associations existantes -->
<h2>Associations existantes</h2>
<table border="1">
    <thead>
    <tr>
        <th>Professeur</th>
        <th>Matière</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="assoc" items="${associations}">
        <tr>
            <td>${assoc[1]} ${assoc[2]}</td> <!-- Nom et prénom du professeur -->
            <td>${assoc[3]}</td>             <!-- Nom de la matière -->
            <td>
                <form action="SupprimerAssociationServlet" method="post">
                    <input type="hidden" name="id" value="${assoc[0]}"/>
                    <button type="submit">Supprimer</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="admin.jsp">Retour à l'accueil</a>
</body>
</html>
