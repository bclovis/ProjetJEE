<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Page Administrateur</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 30px;
        }
        h1 {
            color: #333;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        ul li {
            margin: 10px 0;
        }
        a {
            color: #007bff;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .logout-button {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Bienvenue sur l'espace Administrateur</h1>

    <p>Bonjour, administrateur ! Voici les actions disponibles pour vous :</p>
    <ul>
        <li><a href="gererDemandeInscription.jsp">Gérer les demandes d'inscriptions</a></li>
        <li><a href="creationCompte.jsp">Création de comptes</a></li>
        <li><a href="gererPersonnel.jsp">Gérer le personnel</a></li>
        <li><a href="gererDemandes.jsp">Gérer les demandes de filière</a></li> <!-- Nouveau lien -->
    </ul>

    <form action="logout" method="post" class="logout-button">
        <button type="submit">Déconnexion</button>
    </form>
</div>
</body>
</html>
