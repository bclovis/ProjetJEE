<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Espace Enseignant</title>
    <link rel="stylesheet" type="text/css" href="CSS/enseignant.css">
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
                <li><a href="ajouterNote">Ajouter une note</a></li>
                <li><a href="AfficherEDTEtuEnsServlet">Voir mon emploi du temps</a></li>
            </ul>
        </nav>

        <!-- Zone de contenu principale -->
        <div class="content-area">
            <h1>Bienvenue sur l'espace enseignant</h1>
            <p>Sélectionnez une option à gauche pour afficher les informations.</p>
        </div>
    </div>
</body>
</html>
