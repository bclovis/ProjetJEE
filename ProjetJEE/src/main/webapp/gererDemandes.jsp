<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Gérer les Demandes de Filière</title>
</head>
<body>
<h1>Demandes de Filière</h1>
<table border="1">
  <thead>
  <tr>
    <th>Étudiant</th>
    <th>Filière actuelle</th>
    <th>Filière demandée</th>
    <th>Statut</th>
    <th>Commentaire</th>
    <th>Action</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="demande" items="${demandes}">
    <tr>
      <td>${demande.etudiant.email}</td>
      <td>${etudiantFiliereMap[demande.etudiant.email]}</td> <!-- Nouvelle colonne -->
      <td>${demande.filiere}</td>
      <td>${demande.statut}</td>
      <td>${demande.commentaireAdmin}</td>
      <td>
        <form action="gererDemandeFiliere" method="post">
          <input type="hidden" name="id" value="${demande.id}">
          <input type="hidden" name="action" value="accepter">
          <textarea name="commentaire" placeholder="Ajouter un commentaire"></textarea>
          <button type="submit">Accepter</button>
        </form>
        <form action="gererDemandeFiliere" method="post">
          <input type="hidden" name="id" value="${demande.id}">
          <input type="hidden" name="action" value="refuser">
          <textarea name="commentaire" placeholder="Ajouter un commentaire"></textarea>
          <button type="submit">Refuser</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
