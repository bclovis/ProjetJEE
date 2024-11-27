<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Demande de Filière</title>
  <link rel="stylesheet" href="CSS/choixFiliere.css">
</head>
<body>
<h1>Choix de Filière</h1>
<form action="demandeFiliere" method="post">
  <label for="filiere">Choisissez une filière :</label>
  <select name="filiere" id="filiere">
    <option value="Informatique">Informatique</option>
    <option value="Mathematiques">Mathématiques</option>
  </select>
  <button type="submit">Envoyer ma demande</button>
</form>

<a class="return-home" href="etudiant.jsp">Retour à l'accueil</a>
</body>
</html>
'