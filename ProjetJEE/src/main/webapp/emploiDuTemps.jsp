<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Emploi du Temps</title>
    <link rel="stylesheet" href="CSS/emploiDuTemps.css?v=3">
</head>
<body>
<h1>Emploi du Temps</h1>

<!-- Affichage des messages -->
<c:if test="${not empty message}">
    <p class="message">${message}</p>
</c:if>
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<form method="get" action="AfficherEmploiDuTempsServlet">
    <div class="form-group">
        <label for="filiere">Choisir une filière :</label>
        <select id="filiere" name="filiere">
            <option value="" <%= "Toutes".equals(request.getAttribute("filiereNom")) ? "selected" : "" %>>Toutes</option>
            <option value="Mathématiques" <%= "Mathématiques".equals(request.getAttribute("filiereNom")) ? "selected" : "" %>>Mathématiques</option>
            <option value="Informatique" <%= "Informatique".equals(request.getAttribute("filiereNom")) ? "selected" : "" %>>Informatique</option>
        </select>
    </div>
    <br>

    <div class="form-group">
        <label for="semaine">Choisir une semaine :</label>
        <select id="semaine" name="semaine">
            <% Integer semaineAttrib = (Integer) request.getAttribute("semaine"); %>
            <% for (int i = 1; i <= 36; i++) { %>
            <option value="<%= i %>" <%= (semaineAttrib != null && i == semaineAttrib) ? "selected" : "" %>>Semaine <%= i %></option>
            <% } %>
        </select>
    </div>
    <br>

    <button type="submit" class="button">Afficher</button>
</form>

<div class="info">
    <p>Filière : <%= request.getAttribute("filiereNom") %></p>
    <p>Semaine : <%= request.getAttribute("semaine") %></p>
</div>

<table>
    <thead>
    <tr>
        <th>Heures</th>
        <th>Lundi</th>
        <th>Mardi</th>
        <th>Mercredi</th>
        <th>Jeudi</th>
        <th>Vendredi</th>
    </tr>
    </thead>
    <tbody>
    <%
        Map<String, Map<String, String>> emploiParJourEtHeure =
                (Map<String, Map<String, String>>) request.getAttribute("emploiParJourEtHeure");
        Map<String, Map<String, Integer>> emploiIdParJourEtHeure =
                (Map<String, Map<String, Integer>>) request.getAttribute("emploiIdParJourEtHeure");

        if (emploiParJourEtHeure == null || emploiParJourEtHeure.isEmpty()) {
    %>
    <tr>
        <td colspan="6">Aucun emploi du temps disponible pour la sélection actuelle.</td>
    </tr>
    <%
    } else {
        String[] heures = {"08h-10h", "10h-12h", "12h-14h", "14h-16h", "16h-18h"};
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};
        for (String heure : heures) {
    %>
    <tr>
        <td><%= heure %></td>
        <% for (String jour : jours) { %>
        <%
            String contenu = emploiParJourEtHeure.containsKey(jour) &&
                    emploiParJourEtHeure.get(jour).containsKey(heure)
                    ? emploiParJourEtHeure.get(jour).get(heure)
                    : "Aucun cours";

            Integer coursId = emploiIdParJourEtHeure.containsKey(jour) &&
                    emploiIdParJourEtHeure.get(jour).containsKey(heure)
                    ? emploiIdParJourEtHeure.get(jour).get(heure)
                    : null;
            String cellClass;
            if ("Pause".equals(contenu)) {
                cellClass = "pause-cell";
            } else if ("Aucun cours".equals(contenu)) {
                cellClass = "empty-cell";
            } else {
                cellClass = "filled-cell";
            }
        %>
        <td class="<%= cellClass %>">
            <div><%= contenu %></div>
            <% if (coursId != null) { %>
            <form action="SupprimerCoursServlet" method="post" style="display: inline;">
                <input type="hidden" name="coursId" value="<%= coursId %>" />
                <button type="submit" class="action-button delete">Supprimer</button>
            </form>
            <form action="DeplacerCoursServlet" method="get" style="display: inline;">
                <input type="hidden" name="coursId" value="<%= coursId %>" />
                <button type="submit" class="action-button">Déplacer</button>
            </form>
            <% } %>
        </td>
        <% } %>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<!-- Bouton flottant pour ajouter un cours -->
<a href="ajouterCours" class="add-course-button" title="Ajouter un cours">Ajouter un cours</a>
<br>
<a href="admin.jsp" class="return-home">Retour à l'accueil</a>
</body>
</html>
