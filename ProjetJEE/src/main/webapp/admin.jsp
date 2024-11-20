<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Espace Administrateur</title>
    <link rel="stylesheet" type="text/css" href="CSS/admin.css">
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
                <li><a href="#" data-page="">Gérer les demandes d'inscriptions</a></li>
                <li><a href="#" data-page="creationCompte.jsp">Création de comptes</a></li>
                <li>
                    <a href="#" data-toogle="submenu" onclick="deroulerSubmenu(event)">Gérer le personnel</a>
                    <ul class="submenu" style="display: none;">
                        <li><a href="#" data-page="gererEtudiants.jsp">Gérer les étudiants</a></li>
                        <li><a href="#" data-page="gererEnseignants.jsp">Gérer les enseignants</a></li>
                    </ul>
                </li>
            </ul>
        </nav>

        <!-- Zone de contenu principale -->
        <div class="content-area">
            <h1 id="default-message">Bienvenue dans l'espace administrateur</h1>
            <p id="default-message-paragraph">Sélectionnez une option à gauche pour afficher les informations.</p>
            <div id="dynamic-content"></div>
        </div>
    </div>
</body>

<script>
    // Fonction pour gérer le chargement dynamique des pages
    function loadPage(page) {
        fetch(page)
            .then(response => response.text())
            .then(html => {
                // Remplir la zone dynamique avec le contenu de la page
                document.getElementById('dynamic-content').innerHTML = html;
            })
            .catch(err => console.error('Erreur lors du chargement de la page :', err));
    }

    function deroulerSubmenu(event) {
        event.preventDefault(); // Empêche le lien de rediriger
        const submenu = event.target.nextElementSibling; // Sélectionne le sous-menu (ul)
        if (submenu) {
            submenu.style.display = submenu.style.display === 'none' ? 'block' : 'none';
        }
    }

    // Ajoute un écouteur d'événement à chaque lien du menu
    document.querySelectorAll('.sidebar a').forEach(link => {
        link.addEventListener('click', function (e) {
            const isSubmenuToggle = this.getAttribute('data-toggle') === 'submenu';
            if (isSubmenuToggle) {
                // Ne pas exécuter la logique AJAX pour les sous-menus
                return;
            }

            e.preventDefault(); // Empêche le comportement par défaut
            const page = this.getAttribute('data-page'); // Récupère le nom de la page
            if (page) {
                loadPage(page); // Charger la page via AJAX
            }
        });
    });

    // Gestionnaire d'envoi de formulaire pour la création de compte
    document.addEventListener('submit', function (e) {
        const form = e.target;
        if (form.id === 'creation-compte-form') {
            e.preventDefault(); // Empêche la soumission classique

            // Récupère les données du formulaire
            const formData = new FormData(form);

            // Envoie les données à la servlet via AJAX
            fetch('creer-compte-servlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    typeCompte: form.typeCompte.value,
                    nom: form.nom.value,
                    prenom: form.prenom.value,
                    email: form.email.value,
                    dateNaissance: form.dateNaissance.value,
                    mdp: form.mdp.value
                })
            })
                .then(response => response.text())
                .then(message => {
                    const content = document.getElementById("dynamic-content");
                    if (!content) {
                        console.error("L'élément dynamic-content n'existe pas.");
                        return;
                    }

                    // Option stricte pour insérer du texte de manière sécurisée
                    if (message && message.trim() !== "") {
                        // Si le message est valide
                        content.innerHTML = "<p>" + message + "</p>" +
                            "<button onclick=\"loadPage('creationCompte.jsp')\">Retour</button>";
                    }})
                .catch(err => {
                    console.error('Erreur lors de la création du compte :', err);
                    document.getElementById('dynamic-content').innerHTML = `
                <p style="color:red;">Erreur lors de la création du compte</p>
                <button onclick="loadPage('creationCompte.jsp')">Réessayer</button>
            `       ;
            });
        }
    });

</script>

</html>
