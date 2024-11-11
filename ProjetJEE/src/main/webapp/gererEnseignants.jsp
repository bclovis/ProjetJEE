<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" import="java.util.List, com.example.projetjee.Enseignant" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Liste des Enseignants</title>
</head>
<body>
<h1>Liste des Enseignants</h1>
<table border="1">
  <tr>
    <th>Email</th>
    <th>Nom</th>
    <th>Prénom</th>
    <th>Date de Naissance</th>
    <th>Action</th>
  </tr>
  <%
    List<Enseignant> enseignants = (List<Enseignant>) request.getAttribute("enseignants");
    if (enseignants != null) {
      for (Enseignant enseignant : enseignants) {
  %>
  <tr>
    <td><%= enseignant.getEmail() %></td>
    <td><%= enseignant.getNom() %></td>
    <td><%= enseignant.getPrenom() %></td>
    <td><%= enseignant.getDateNaissance() %></td>
    <td><a href="modifierEnseignant?email=<%= enseignant.getEmail() %>">Modifier</a></td>
  </tr>
  <%
      }
    }
  %>
</table>
<a href="gererPersonnel.jsp">Retour à la gestion du personnel</a>
</body>
</html>
