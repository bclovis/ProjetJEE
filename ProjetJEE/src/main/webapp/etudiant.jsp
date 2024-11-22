<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Espace Étudiant</title>
    <link rel="stylesheet" type="text/css" href="CSS/etudiant.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <!-- Barre de navigation -->
    <div class="header">
        <img src="Logo_Projet_JEE.PNG" alt="Logo Université" class="logo">
        <div class="icons">
            <span class="icon"><i class="fa-solid fa-circle-user"></i></span>  <!-- Icône du compte utilisateur -->
            <form action="logout" method="post" style="display:inline;">
                <button type="submit" class="icon" title="Déconnexion"><i class="fa-solid fa-power-off"></i></button>  <!-- Icône de déconnexion -->
            </form>
        </div>
    </div>

    <!-- Mise en page principale -->
    <div class="main-content">
        <!-- Menu latéral gauche -->
        <nav class="sidebar">
            <ul>
                <li><a href="consulterEmploiDuTemps.jsp">Consulter mon emploi du temps</a></li>
                <li><a href="messagerie.jsp">Accéder à ma messagerie</a></li>
                <li><a href="voirNotes.jsp">Voir mes notes</a></li>
                <li><a href="choixFiliere.jsp">Faire une demande de choix de filière</a></li> <!-- Nouveau lien -->
            </ul>
        </nav>

        <!-- Zone de contenu principale -->
        <div class="content-area">
            <h1>Bienvenue dans l'espace étudiant</h1>
            <p>Sélectionnez une option à gauche pour afficher les informations.</p>
        </div>
    </div>
</body>
</html>
