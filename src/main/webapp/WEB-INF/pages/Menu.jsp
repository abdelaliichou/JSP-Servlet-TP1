<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Pari en ligne</title>

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
        }

        li {
            margin: 10px 0;
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

<h2>Bonjour, ${sessionScope.user.login}!</h2>
<h2>Menu</h2>

<ul>
    <li><a href="/pel/parisouverts">Afficher les matchs sur lesquels parier</a></li>
    <li><a href="/pel/mesparis">Afficher mes paris</a></li>
    <li><a href="/pel/logout">Deconnexion</a></li>
</ul>

</body>
</html>
