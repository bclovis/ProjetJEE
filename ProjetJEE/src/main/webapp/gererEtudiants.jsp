<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.List, com.example.projetjee.Etudiant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Étudiants</title>
</head>
<body>
<h1>Liste des Étudiants</h1>
<table border="1">
    <tr>
        <th>Email</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Date de Naissance</th>
        <th>Action</th>
    </tr>
    <%
        List<Etudiant> etudiants = (List<Etudiant>) request.getAttribute("etudiants");
        if (etudiants != null) {
            for (Etudiant etudiant : etudiants) {
    %>
    <tr>
        <td><%= etudiant.getEmail() %></td>
        <td><%= etudiant.getNom() %></td>
        <td><%= etudiant.getPrenom() %></td>
        <td><%= etudiant.getDateNaissance() %></td>
        <td><a href="modifierEtudiant?email=<%= etudiant.getEmail() %>">Modifier</a></td>
    </tr>
    <%
            }
        }
    %>
</table>
<a href="gererPersonnel.jsp">Retour à la gestion du personnel</a>
</body>
</html>
