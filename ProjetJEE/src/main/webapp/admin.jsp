<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Page Administrateur</title>
</head>
<body>
<h1>Bienvenue sur l'espace Administrateur</h1>

<p>Bonjour, administrateur !</p>
<p>Voici les actions disponibles pour vous :</p>
<ul>
    <li><a href="gererCours.jsp">Gérer les cours</a></li>
    <li><a href="gererDemandeInscription">Gérer les demandes d'inscriptions</a></li>
    <li><a href="creationCompte">Création de comptes</a></li>
    <li><a href="gererPersonnel">Gérer le personnels</a></li>
</ul>

<form action="logout" method="post">
    <button type="submit">Déconnexion</button>
</form>

</body>
</html>
