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
        .info {
            margin-bottom: 20px;
            font-size: 18px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h1>Emploi du Temps</h1>

<form method="get" action="AfficherEmploiDuTempsServlet">
    <label for="filiere">Choisir une filière :</label>
    <select id="filiere" name="filiere">
        <option value="" selected>Filière</option>
        <option value="Mathématiques">Mathématiques</option>
        <option value="Informatique">Informatique</option>
    </select>

    <label for="semaine">Choisir une semaine :</label>
    <select id="semaine" name="semaine">
        <option value="" selected>Semaine</option>
        <% for (int i = 1; i <= 32; i++) { %>
        <option value="<%= i %>">Semaine <%= i %></option>
        <% } %>
    </select>

    <button type="submit">Afficher</button>
    <button type="reset">Réinitialiser</button>
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
            <%= emploiParJourEtHeure.containsKey(jour) &&
                    emploiParJourEtHeure.get(jour).containsKey(heure)
                    ? emploiParJourEtHeure.get(jour).get(heure)
                    : "" %>
        </td>
        <% } %>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>
</body>
</html>
