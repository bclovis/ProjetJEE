<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Mes Notes</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f2f2f2;
      margin: 0;
      padding: 0;
    }
    .container {
      width: 80%;
      margin: auto;
      padding: 20px;
      background-color: #ffffff;
      border-radius: 8px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      margin-top: 30px;
    }
    table {
      width: 100%;
      border-collapse: collapse;
    }
    table, th, td {
      border: 1px solid black;
    }
    th, td {
      padding: 10px;
      text-align: left;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>Mes Notes</h1>

  <c:choose>
    <c:when test="${empty notes}">
      <p>Aucune note disponible.</p>
    </c:when>
    <c:otherwise>
      <table>
        <thead>
        <tr>
          <th>Matière</th>
          <th>Note</th>
          <th>Date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${notes}" var="note">
          <tr>
            <td>${note.cours.matiere}</td>
            <td>${note.note}</td>
            <td><fmt:formatDate value="${note.date}" pattern="dd/MM/yyyy" /></td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </c:otherwise>
  </c:choose>

  <a href="../etudiant.jsp">Retour à l'accueil étudiant</a>
</div>
</body>
</html>
