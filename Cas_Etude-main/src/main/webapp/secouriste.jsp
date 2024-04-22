<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="utilisateur.DAO.DaoManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logistique</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="securite.css">

</head>
<body>
<div class="conteneur-parent">
    <div class="conteneur-med-1">
        <h1>Page Secouriste :</h1>
        <%
            HttpSession session1 = request.getSession();
            String name = (String) session1.getAttribute("name");
        %>
        <p>Nom de l'utilisateur de la session : <%= name %></p>
        <form method="get" action="./Control">
            <input type="submit" name="action" value="Deconnexion" class="btn btn-danger">
        </form>
        <%
            DaoManager daoManager = new DaoManager();
            String occupe = daoManager.getOccupeByNom(name);
        %>
        occupé : <%= occupe %>
        <form method="get" action="./Control">
            <select name="etat" id="etat" required>
                <option value="oui">oui</option>
                <option value="non">non</option>
            </select>
            <input type="submit" name="action" value="majOccupe" class="btn btn-primary">
        </form>

    </div>
    <div class="conteneur-enfant">
        <div class="conteneur-med-2">
            <h1>information mission</h1>
        </div>
        <div class="conteneur-med-3">
            <h1>Ajout de patients</h1>
            <form  method="get" action="./Control">
                Nouvel utilisateur
                id :<input type="text" name="id" id="cid" pattern="[0-9]+" min="0">
                nom : <input type="text" name="nom" id="nom">
                prenom : <input type="text" name="prenom" id="prenom">
                état de gravité : <input type="number" id="etet" name="etat" min="1" max="10">
                <input type="submit" name="action" value="addPatient" class="btn btn-success">
            </form>
        </div>
    </div>
</div>
</body>
</html>
