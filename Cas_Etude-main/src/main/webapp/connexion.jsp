<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 13:47
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 09:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page import="utilisateur.model.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Page de connexion</title>
    <link rel="stylesheet" type="text/css" href="apparence.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1 class="text-center">Logiciel escrim</h1>
    <form method="get" action="./Control">
        <div class="form-group">
            <label for="nom">Nom :</label>
            <input type="text" class="form-control" id="nom" name="nom" required>
        </div>
        <div class="form-group">
            <label for="mdp">Mot de passe :</label>
            <input type="password" class="form-control" id="mdp" name="mdp" required>
        </div>
        <input type="submit" name="action" value="Connexion" class="btn btn-primary" />
    </form>
</div>
</body>
</html>

