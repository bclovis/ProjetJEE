<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Mes Notes</title>
  <link rel="stylesheet" href="CSS/voirNote.css?v=3">
</head>
<body>
<div class="container">
  <h1>Mes Notes</h1>

  <c:choose>
    <c:when test="${empty notesParMatiere}">
      <p>Aucune note disponible.</p>
    </c:when>
    <c:otherwise>
      <table>
        <thead>
        <tr>
          <th>Note (/20)</th>
          <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <!-- Affichage de la moyenne générale -->
        <tr>
          <td colspan="2" style="font-weight: bold; text-align: center;">
            Moyenne générale : ${moyenneGenerale} / 20
          </td>
        </tr>

        <!-- Affichage des notes par matière -->
        <c:forEach items="${notesParMatiere}" var="matiereEntry">
          <c:set var="matiere" value="${matiereEntry.key}" />
          <c:set var="notes" value="${matiereEntry.value}" />

          <!-- Ligne pour la moyenne de la matière -->
          <tr>
            <td colspan="2" style="font-weight: bold; text-align: center;">
                ${matiere} - Moyenne : ${moyennesParMatiere[matiere]} / 20
            </td>
          </tr>

          <!-- Lignes pour les notes de la matière -->
          <c:forEach items="${notes}" var="note">
            <tr>
              <td>${note.note} / 20</td>
              <td><fmt:formatDate value="${note.date}" pattern="dd/MM/yyyy" /></td>
            </tr>
          </c:forEach>
        </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

  <div class="actions">
    <form action="voirNotes" method="get">
      <input type="hidden" name="action" value="download">
      <button type="submit">Télécharger le relevé de notes</button>
    </form>

    <!-- Bouton aligné à droite -->
    <button onclick="window.location.href='etudiant.jsp'">Retour à l'accueil</button>
  </div>
</div>
</body>
</html>
