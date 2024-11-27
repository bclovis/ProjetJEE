<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Déplacer un Cours</title>
    <link rel="stylesheet" href="CSS/deplacerCours.css">
</head>
<body>
<h1>Déplacer un Cours</h1>

<form action="DeplacerCoursServlet" class="deplace-form" method="post">
    <input type="hidden" name="coursId" value="${cours.id}" />
    <label for="jour">Nouveau Jour :</label>
    <select id="jour" name="jour">
        <option value="Lundi">Lundi</option>
        <option value="Mardi">Mardi</option>
        <option value="Mercredi">Mercredi</option>
        <option value="Jeudi">Jeudi</option>
        <option value="Vendredi">Vendredi</option>
    </select><br>

    <label for="heure">Nouvelle Heure :</label>
    <select id="heure" name="heure">
        <option value="08h-10h">08h-10h</option>
        <option value="10h-12h">10h-12h</option>
        <option value="12h-14h">12h-14h</option>
        <option value="14h-16h">14h-16h</option>
        <option value="16h-18h">16h-18h</option>
    </select><br>

    <label for="semaine">Nouvelle Semaine :</label>
    <input type="number" id="semaine" name="semaine" min="1" max="36" value="1" /><br>

    <button type="submit">Déplacer</button>
    <br><br>
    <a href="admin.jsp" class="return-home">Retour à l'accueil</a>
</form>
</body>
</html>
