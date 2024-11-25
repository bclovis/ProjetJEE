<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Notes</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>Gestion des Notes</h1>

<!-- Affichage des erreurs -->
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>

<!-- Tableau des notes -->
<h2>Liste des Notes</h2>
<table>
    <thead>
    <tr>
        <th>Nom de l'élève</th>
        <th>Matière</th>
        <th>Note</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="note" items="${notes}">
        <tr>
            <td>${note.etudiant.nom}</td> <!-- Affichage du nom de l'élève -->
            <td>${note.matiere.nom}</td> <!-- Affichage du nom de la matière -->
            <td>${note.note}</td> <!-- Affichage de la note -->
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Formulaire pour ajouter une note -->
<h2>Ajouter une Note</h2>
<form method="post" action="ajouterNote">
    <label for="emailEtudiant">Email de l'élève :</label><br>
    <input type="text" id="emailEtudiant" name="emailEtudiant" required><br><br>

    <label for="matiere">Matière :</label><br>
    <select id="matiere" name="matiere" required>
        <c:forEach var="matiere" items="${matieres}">
            <option value="${matiere.nom}">${matiere.nom}</option>
        </c:forEach>
    </select><br><br>

    <label for="note">Note :</label><br>
    <input type="number" step="0.01" id="note" name="note" required><br><br>

    <button type="submit">Ajouter</button>
</form>
</body>
</html>
