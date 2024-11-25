<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gérer le Personnel</title>
</head>
<body>
<h1>Gérer le Personnel</h1>
<p>Choisissez une option :</p>
<ul>
    <li><a href="gererEtudiants">Gérer les Étudiants</a></li>
    <li><a href="gererEnseignants">Gérer les Enseignants</a></li>
    <li><a href="admin.jsp">Retour à l'accueil</a></li>
</ul>
<form action="logout" method="post">
    <button type="submit">Déconnexion</button>
</form>
</body>
</html>