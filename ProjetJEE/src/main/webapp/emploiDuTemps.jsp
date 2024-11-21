<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Emploi du Temps</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid black;
            text-align: center;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        td {
            vertical-align: top;
        }
        .info {
            margin-bottom: 20px;
            font-size: 18px;
            font-weight: bold;
        }
        .navigation {
            margin-top: 20px;
        }
        .message {
            color: green;
            font-weight: bold;
            margin-bottom: 20px;
        }
        .error {
            color: red;
            font-weight: bold;
            margin-bottom: 20px;
        }
        /* Style pour le bouton flottant */
        .add-course-button {
            position: fixed;
            bottom: 20px;
            right: 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 15px 20px;
            border-radius: 50%;
            font-size: 16px;
            cursor: pointer;
            box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.2);
            text-decoration: none;
            text-align: center;
        }
        .add-course-button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<h1>Emploi du Temps</h1>

<!-- Affichage des messages de confirmation ou d'erreur -->
<c:if test="${not empty message}">
    <p class="message">${message}</p>
</c:if>
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<form method="get" action="AfficherEmploiDuTempsServlet">
    <label for="filiere">Choisir une filière :</label>
    <select id="filiere" name="filiere">
        <option value="" <%= "Toutes".equals(request.getAttribute("filiereNom")) ? "selected" : "" %>>Toutes</option>
        <option value="Mathématiques" <%= "Mathématiques".equals(request.getAttribute("filiereNom")) ? "selected" : "" %>>Mathématiques</option>
        <option value="Informatique" <%= "Informatique".equals(request.getAttribute("filiereNom")) ? "selected" : "" %>>Informatique</option>
    </select>

    <label for="semaine">Choisir une semaine :</label>
    <select id="semaine" name="semaine">
        <% Integer semaineAttrib = (Integer) request.getAttribute("semaine"); %>
        <% for (int i = 1; i <= 36; i++) { %>
        <option value="<%= i %>" <%= (semaineAttrib != null && i == semaineAttrib) ? "selected" : "" %>>Semaine <%= i %></option>
        <% } %>
    </select>

    <button type="submit">Afficher</button>
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
        <td>
            <%
                String contenu = emploiParJourEtHeure.containsKey(jour) &&
                        emploiParJourEtHeure.get(jour).containsKey(heure)
                        ? emploiParJourEtHeure.get(jour).get(heure)
                        : "Aucun cours";
            %>
            <div><%= contenu %></div>
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
<a href="ajouterCours" class="add-course-button" title="Ajouter un cours">+</a>

</body>
</html>
