<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Création de Compte</title>
</head>
<body>
<h1>Création de Compte</h1>
<form action="CreerCompteServlet" method="post">
    <label for="typeCompte">Type de compte :</label>
    <select name="typeCompte" id="typeCompte" required>
        <option value="etudiant">Étudiant</option>
        <option value="enseignant">Enseignant</option>
    </select>
    <br><br>

    <label for="nom">Nom :</label>
    <input type="text" id="nom" name="nom" required>
    <br><br>

    <label for="prenom">Prénom :</label>
    <input type="text" id="prenom" name="prenom" required>
    <br><br>

    <label for="email">Email :</label>
    <input type="email" id="email" name="email" required>
    <br><br>

    <label for="dateNaissance">Date de Naissance :</label>
    <input type="date" id="dateNaissance" name="dateNaissance" required>
    <br><br>

    <label for="mdp">Mot de Passe :</label>
    <input type="password" id="mdp" name="mdp" required>
    <br><br>

    <button type="submit">Créer le compte</button>
</form>
</body>
</html>