<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Page Enseignant</title>
</head>
<body>
<h1>Bienvenue sur l'espace des Enseignants</h1>

<p>Bonjour, enseignant !</p>
<p>Voici les actions disponibles pour vous :</p>
<ul>
    <li><a href="ajouterNote.jsp">Ajouter une note</a></li>
    <li><a href="voirEmploiDuTemps.jsp">Voir mon emploi du temps</a></li>
    <li><a href="voirMesClasses.jsp">Voir mes classes</a></li>
</ul>

<form action="logout" method="post">
    <button type="submit">Déconnexion</button>
</form>

</body>
</html>
