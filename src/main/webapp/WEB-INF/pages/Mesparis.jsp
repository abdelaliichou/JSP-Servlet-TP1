<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Liste De Paris</title>

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        h2 {
            color: #333;
        }

        p {
            color: red;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin-top: 10px;
        }

        li {
            margin: 10px 0;
            background-color: #fff;
            padding: 10px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        a {
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
        }

        a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
<h2>${sessionScope.user.login}</h2>
<h2>Mes paris</h2>

<p>${requestScope.error}</p>

<ul>
    <c:forEach var="pari" items="${requestScope.paris}">
        <li>sport : ${pari.match.sport} - ${pari.match.equipe1} vs ${pari.match.equipe2} - le ${pari.match.quand} . Mise de ${pari.montant} dur ${pari.vainqueur} <a href="/pel/annuler?id=${pari.idPari}">annuler</a></li>
    </c:forEach>
</ul>
<a href="/pel/menu">Retourner au menu</a>
</body>
</html>
