<%@ page import="utilisateur.model.UserManager" %>
<%@ page import="utilisateur.model.Mission" %>
<%@ page import="utilisateur.model.Secouristes" %><%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="admin.css">
</head>
<body>
<div class="conteneur-1">
    <h1>Page Admin :</h1>
    <%
        HttpSession session1 = request.getSession();
        String name = (String) session1.getAttribute("name");
    %>
    <p>Nom de l'utilisateur de la session : <%= name %></p>
    <form method="get" action="./Control">
        <input type="submit" name="action" value="Deconnexion" class="btn btn-danger">
    </form>

</div>
<div class="conteneur-2">
    <h1>Gestion personel</h1>
    <form  method="get" action="./Control">
        Nouvel utilisateur
        id :<input type="text" name="id" id="cid" pattern="[0-9]+" min="0">
        nom : <input type="text" name="nom" id="nom" required>
        prenom : <input type="text" name="prenom" id="prenom" required>
        mdp : <input type="text" name="mdp" id="mdp" required>
        role : <select name="role" id="role" required>
        <option value="admin">admin</option>
        <option value="logistique">logistique</option>
        <option value="medical">medical</option>
        <option value="secouriste">secouriste</option>
    </select>
        <input type="submit" name="action" value="addUser" class="btn btn-success">
    </form>

    <form  method="get" action="./Control">
        Supprimer utilisateur
        id :<input type="text" name="id" id="cid" pattern="[0-9]+" min="0">
        <input type="submit" name="action" value="supUser" class="btn btn-danger">
    </form>

    <form  method="get" action="./Control">
        Update utilisateur
        id :<input type="text" name="id" id="cid" pattern="[0-9]+" min="0">
        nom : <input type="text" name="nom" id="nom" required>
        prenom : <input type="text" name="prenom" id="prenom" required>
        mdp : <input type="text" name="mdp" id="mdp" required>
        role : <select name="role" id="role" required>
        <option value="admin">admin</option>
        <option value="logistique">logistique</option>
        <option value="medical">medical</option>
        <option value="secouriste">secouriste</option>
    </select>
        <input type="submit" name="action" value="updateUser" class="btn btn-primary">
    </form>
</div>

<div class="conteneur-4">
    <h1>Gestion des interventions</h1>
    <h2> Creation de mission </h2>
    <form  method="get" action="./Control">
        id :<input type="text" name="idMission" id="idMission" pattern="[0-9]+" min="0">
        nom : <input type="text" name="nom" id="nom">
        localisation: <input type="text" name="loc" id="loc">
        nbVictimes :<input type="text" name="nbVict" id="nbVict" pattern="[0-9]+" min="0">
        nbSecouriste :<input type="text" name="nbSecour" id="nbSecour" pattern="[0-9]+" min="0">
        <input type="submit" name="action" value="createMission" class="btn btn-primary">
    </form>
    <h2> Gestion de mission</h2>
    <div style="height: 380px; overflow-y: auto;">
        <ul>
            <%
                for(Mission mission: UserManager.getInstance().getListeMissions()){
            %>
            <li>
                nom : <%=mission.getNomMission() %>
                nombre de victimes restantes <%=mission.getNbVictimes() %>
                nombre de secouriste nécessaires<%=mission.getNbSecoursite() %>
                nombre de secoursite sur l'intervention<%=mission.getCurrNbSecoursite() %>
            </li>
            <br>
            <%
                }
            %>
        </ul>
    </div>
    <h2>
        affectation de secouriste
        <form  method="get" action="./Control">
            <select name="libre" id="libre" required>
                <%
                    for(Secouristes secour: UserManager.getInstance().getSecourLibre()){
                %>
                <option value="<%=secour.getId()%>"><%=secour.getNom()%> <%=secour.getPrenom()%></option>
                <%
                    }
                %>
            </select>
            id mission: <input type="text" name="id" id="id">
            <input type="submit" name="action" value="affecter" class="btn btn-primary">
        </form>

    </h2>

</div>
</body>
</html>
