<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <link rel="stylesheet" href="login.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@600&display=swap" rel="stylesheet">
</head>
<body>
    <img src="Logo_Projet_JEE.PNG" alt="Logo" class="logo">
    <h2>Log In</h2>
    <form action="login" method="post">
        <div class="form-group">
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" required>
            <br><br>
        </div>

        <div class="form-group">
            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required>
            <br><br>
        </div>


        <div class="radio-group">
        <label>Rôle :</label>
            <input type="radio" id="etudiant" name="role" value="etudiant" required>
            <label for="etudiant">Étudiant</label>

            <input type="radio" id="enseignant" name="role" value="enseignant">
            <label for="enseignant">Enseignant</label>

            <input type="radio" id="admin" name="role" value="admin">
            <label for="admin">Administrateur</label>
            <br><br>
        </div>

        <button type="submit">Se connecter</button>
    </form>
<%-- Affichage d'un message d'erreur si la connexion échoue --%>
<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>
</body>
</html>
