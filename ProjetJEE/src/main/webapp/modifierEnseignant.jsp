<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="com.example.projetjee.Enseignant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier Enseignant</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .form-container {
            width: 300px;
            margin: 0 auto;
        }
        .form-container label {
            display: block;
            margin: 10px 0 5px;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>Modifier Enseignant</h1>
    <form action="modifierEnseignant" method="post">
        <input type="hidden" name="email" value="<%= request.getParameter("email") %>" />
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
    </form>
    <a href="gererEnseignants">Retour</a>
</div>
</body>
</html>
