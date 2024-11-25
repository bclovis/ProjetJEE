<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
  <title>Ajouter un Cours</title>
  <link rel="stylesheet" href="CSS/ajouterCours.css">
</head>
<body>
<h1>Ajouter un Cours</h1>

<!-- Afficher les messages -->
<c:if test="${not empty message}">
  <p style="color: green; font-weight: bold;">${message}</p>
</c:if>
<c:if test="${not empty error}">
  <p style="color: red; font-weight: bold;">${error}</p>
</c:if>

<form action="ajouterCours" method="post">
  <label for="filiere">Filière :</label>
  <select id="filiere" name="filiere" required>
    <c:forEach var="filiere" items="${filieres}">
      <option value="${filiere.nom}">${filiere.nom}</option>
    </c:forEach>
  </select><br>

  <label for="semestre">Semestre :</label>
  <select id="semestre" name="semestre" required>
    <option value="1">1</option>
    <option value="2">2</option>
  </select><br>

  <label for="matiere">Matière :</label>
  <select id="matiere" name="matiere" required>
    <c:forEach var="matiere" items="${matieres}">
      <option value="${matiere.id}">${matiere.nom}</option>
    </c:forEach>
  </select><br>

  <label for="professeur">Professeur :</label>
  <select id="professeur" name="professeur" required>
    <c:forEach var="enseignant" items="${enseignants}">
      <option value="${enseignant.email}">${enseignant.nom} ${enseignant.prenom}</option>
    </c:forEach>
  </select><br>

  <label for="jour">Jour :</label>
  <select id="jour" name="jour" required>
    <c:forEach var="jour" items="${jours}">
      <option value="${jour}">${jour}</option>
    </c:forEach>
  </select><br>

  <label for="heure">Heure :</label>
  <select id="heure" name="heure" required>
    <c:forEach var="heure" items="${heures}">
      <option value="${heure}">${heure}</option>
    </c:forEach>
  </select><br>

  <label for="semaine">Semaine :</label>
  <select id="semaine" name="semaine" required>
    <c:forEach var="semaine" items="${semaines}">
      <option value="${semaine}">Semaine ${semaine}</option>
    </c:forEach>
  </select><br>

  <button type="submit">Ajouter</button>
</form>
</body>
</html>
