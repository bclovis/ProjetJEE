<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="com.example.projetjee.Model.Enseignant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier Enseignant</title>
    <link rel="stylesheet" href="CSS/modifierEtudiant.css">
</head>
<body>
<h1>Modifier les informations de l'enseignant</h1>
<div class="form-container">
    <form action="modifierEnseignant" method="post">
        <input type="hidden" name="email" value="<%= request.getAttribute("email") %>" />

        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="<%= request.getAttribute("nom") %>" required>

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" value="<%= request.getAttribute("prenom") %>" required>

        <label for="dateNaissance">Date de Naissance :</label>
        <input type="date" id="dateNaissance" name="dateNaissance" value="<%= request.getAttribute("dateNaissance") %>" required>

        <label for="mdp">Mot de Passe :</label>
        <input type="password" id="mdp" name="mdp" value="<%= request.getAttribute("mdp") %>" required>
        <br><br>

        <button type="submit">Modifier</button>
        <a href="admin.jsp">Retour à la liste</a>
    </form>
    <!--
    <a href="gererEnseignants">Retour</a>
    -->
</div>
</body>
</html>
