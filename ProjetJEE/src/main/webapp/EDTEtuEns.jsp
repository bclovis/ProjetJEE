<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Emploi du Temps</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #333;
            text-align: center;
        }

        table {
            width: 100%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ddd;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        form {
            margin: 20px auto;
            text-align: center;
        }

        label, select, button {
            font-size: 16px;
            padding: 10px;
            margin: 5px;
        }

        .no-data {
            text-align: center;
            color: #777;
            font-size: 18px;
        }

    </style>
</head>
<body>
<h1>Emploi du Temps</h1>

<!-- Formulaire pour changer de semaine -->
<form method="get" action="AfficherEDTEtuEnsServlet">
    <label for="semaine">Semaine :</label>
    <select id="semaine" name="semaine">
        <%
            // Récupérer la semaine actuelle passée par la servlet
            int semaine = (int) request.getAttribute("semaine");
            for (int i = 1; i <= 36; i++) {
        %>
        <option value="<%= i %>" <%= (i == semaine) ? "selected" : "" %>>Semaine <%= i %></option>
        <% } %>
    </select>
    <button type="submit">Changer de semaine</button>
</form>

<!-- Tableau pour afficher l'emploi du temps -->
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
        // Récupérer l'Emploi du Temps sous forme de Map
        Map<String, Map<String, String>> emploiParJourEtHeure =
                (Map<String, Map<String, String>>) request.getAttribute("emploiParJourEtHeure");

        // Si l'emploi du temps est vide ou null, afficher un message
        if (emploiParJourEtHeure == null || emploiParJourEtHeure.isEmpty()) {
    %>
    <tr>
        <td colspan="6">Aucun emploi du temps disponible pour cette semaine.</td>
    </tr>
    <%
    } else {
        // Définir les heures de cours et les jours de la semaine
        String[] heures = {"08h-10h", "10h-12h", "12h-14h", "14h-16h", "16h-18h"};
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi"};

        // Parcourir chaque heure pour créer les lignes du tableau
        for (String heure : heures) {
    %>
    <tr>
        <td><%= heure %></td>
        <%
            // Parcourir chaque jour pour remplir le tableau
            for (String jour : jours) {
                // Vérifier si un cours existe pour ce jour et cette heure
                String contenu = emploiParJourEtHeure.containsKey(jour) &&
                        emploiParJourEtHeure.get(jour).containsKey(heure)
                        ? emploiParJourEtHeure.get(jour).get(heure)
                        : "Aucun cours";
        %>
        <td>
            <div><%= contenu %></div>
        </td>
        <%
            } // Fin boucle jours
        %>
    </tr>
    <%
            } // Fin boucle heures
        }
    %>
    </tbody>
</table>

</body>
</html>
