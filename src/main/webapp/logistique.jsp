<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logistique</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="logistique.css">

</head>
<body>
<div class="conteneur-parent">
    <div class="conteneur-med-1">
        <h1>informations</h1>
        <form method="get" action="./Control">
            <input type="submit" name="action" value="Deconnexion" class="btn btn-danger">
        </form>
        <form method="get" action="./Control">
            occupé : <select name="etat" id="etat" required>
            <option value="oui">oui</option>
            <option value="non">non</option>
        </select>
            <input type="submit" name="action" value="majOccupe" class="btn btn-primary">
        </form>
    </div>
    <div class="conteneur-enfant">
        <div class="conteneur-med-2">
            <h1>Gestion des colis</h1>
        </div>
        <div class="conteneur-med-3">
            <h1>Gestion commande</h1>
        </div>
    </div>
</div>
</body>
</html>
