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
    <li><a href="gererDemandeInscription.jsp">Gérer les demandes d'inscriptions</a></li>
    <li><a href="creationCompte.jsp">Création de comptes</a></li>
    <li><a href="gererPersonnel.jsp">Gérer le personnel</a></li>
</ul>

<form action="logout" method="post">
    <button type="submit">Déconnexion</button>
</form>

</body>
</html>
