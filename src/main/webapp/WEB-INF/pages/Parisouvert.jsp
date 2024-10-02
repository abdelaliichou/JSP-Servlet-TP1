<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Paris Ouvert</title>

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

        ul {
            list-style-type: none;
            padding: 0;
            margin-top: 10px;
        }

        li {
            background-color: #fff;
            padding: 10px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 10px;
        }

        a {
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
        }

        a:hover {
            color: #0056b3;
        }

        a.parier-link {
            display: inline-block;
            margin-left: 10px;
            padding: 5px 10px;
            background-color: #007BFF;
            color: #fff;
            border-radius: 5px;
            text-decoration: none;
        }

        a.parier-link:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<h2>${sessionScope.user.login}</h2>
<h2>Matchs :</h2>

<ul>
    <c:forEach var="match" items="${requestScope.matchs}">
        <li>sport : ${match.sport} - ${match.equipe1} vs ${match.equipe2}
            <a href="/pel/parier?id=${match.idMatch}" class="parier-link">Parier</a>
        </li>
    </c:forEach>
</ul>

<a href="/pel/menu">Retourner au menu</a>
</body>
</html>
