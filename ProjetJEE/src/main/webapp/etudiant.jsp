<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Espace Étudiant</title>
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
    <h1>Bienvenue dans l'espace étudiant</h1>

    <p>Bonjour, étudiant ! Voici les options disponibles :</p>

    <ul>
        <li><a href="consulterEmploiDuTemps.jsp">Consulter mon emploi du temps</a></li>
        <li><a href="inscrireCours.jsp">S'inscrire à des cours</a></li>
        <li><a href="messagerie.jsp">Accéder à ma messagerie</a></li>
        <li><a href="etudiant/voirNote.jsp">Voir mes notes</a></li>
    </ul>

    <form action="logout" method="post" class="logout-button">
        <button type="submit">Déconnexion</button>
    </form>
</div>
</body>
</html>
