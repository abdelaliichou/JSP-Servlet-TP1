<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Print Match</title>

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
            margin: 10px 0;
        }

        form {
            max-width: 300px;
            margin: 20px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin: 10px 0;
            font-weight: bold;
        }

        select, input {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            display: block;
            text-decoration: none;
            color: #007BFF;
            font-weight: bold;
            margin-top: 20px;
        }

        a:hover {
            color: #0056b3;
        }
    </style>
</head>
<body>
<h2>${sessionScope.user.login}</h2>

<p>Vous voulez parier sur le match :${sessionScope.match.sport} - ${sessionScope.match.equipe1} vs ${sessionScope.match.equipe2} le ${sessionScope.match.quand}</p>
<p style="color: red;">${requestScope.error}</p>

<form action="/pel/parier" method="get">
    <label for="verdict">Verdict du match :</label>
    <select name="verdict" id="verdict">
        <option value="null">null</option>
        <option value="${sessionScope.match.equipe1}">${sessionScope.match.equipe1}</option>
        <option value="${sessionScope.match.equipe2}">${sessionScope.match.equipe2}</option>
    </select>
    <br>
    <label for="montant">Montant :</label>
    <input type="number" name="montant" id="montant">
    <br>
    <input type="submit" value="Parier">
</form>

<a href="/pel/menu">Retourner au menu</a>
</body>
</html>
