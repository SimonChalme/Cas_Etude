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
<div class="conteneur-1" style="height : 100px; display: flex;justify-content: space-between; background-color: #f1f1f1; border: 1px solid #ddd;">
    <div>
        <h1>Page Admin :</h1>
        <%
            HttpSession session1 = request.getSession();
            String name = (String) session1.getAttribute("name");
        %>
        <p>Nom de l'utilisateur de la session : <%= name %></p>
    </div>
    <div style="align-self: flex-end">
        <form method="get" action="./Control">
            <input type="submit" name="action" value="Deconnexion" class="btn btn-danger">
        </form>
    </div>


</div>
<div class="conteneur-2" style="height: 1200px; padding: 20px;background-color: #f1f1f1; border: 1px solid #ddd;">
    <h1>Gestion personel</h1>
    <form class="form-group" method="get" action="./Control">
        <h2>Nouvel utilisateur</h2>
        <label>id :</label>
        <input type="text" name="id" id="cid" class="form-control" required pattern="[0-9]+" min="0">
        <label>nom :</label>
        <input type="text" name="nom" id="nom" class="form-control" required>
        <label>prenom :</label>
        <input type="text" name="prenom" id="prenom" class="form-control" required>
        <br>
        <label>mdp :</label>
        <input type="text" name="mdp" id="mdp" class="form-control" required>
        <label>role :</label>
        <select name="role" id="role" class="form-control" required>
            <option value="admin">admin</option>
            <option value="logistique">logistique</option>
            <option value="medical">medical</option>
            <option value="secouriste">secouriste</option>
        </select>
        <input type="submit" name="action" value="addUser" class="btn btn-success">
    </form>

    <form class="form-group" method="get" action="./Control">
        <h2>Supprimer utilisateur</h2>
        <label>id :</label>
        <input type="text" name="id" id="cid" class="form-control" required pattern="[0-9]+" min="0">
        <input type="submit" name="action" value="supUser" class="btn btn-danger">
    </form>

    <form class="form-group" method="get" action="./Control">
        <h2>Update utilisateur</h2>
        <label>id :</label>
        <input type="text" name="id" id="cid" class="form-control" required pattern="[0-9]+" min="0">
        <label>nom :</label>
        <input type="text" name="nom" id="nom" class="form-control" required>
        <label>prenom :</label>
        <input type="text" name="prenom" id="prenom" class="form-control" required>
        <br>
        <label>mdp :</label>
        <input type="text" name="mdp" id="mdp" class="form-control" required>
        <label>role :</label>
        <select name="role" id="role" class="form-control" required>
            <option value="admin">admin</option>
            <option value="logistique">logistique</option>
            <option value="medical">medical</option>
            <option value="secouriste">secouriste</option>
        </select>
        <input type="submit" name="action" value="updateUser" class="btn btn-primary">
    </form>
</div>


<div class="conteneur-3" style="height: 1000px; padding: 20px;background-color: #f1f1f1; border: 1px solid #ddd;">
    <h1>Gestion des interventions</h1>
    <h2>Creation de mission</h2>
    <form class="form-group" method="get" action="./Control">
        <label>id :</label>
        <input type="text" name="idMission" id="idMission" class="form-control" required pattern="[0-9]+" min="0">
        <label>nom :</label>
        <input type="text" name="nom" id="nom" class="form-control">
        <label>localisation :</label>
        <input type="text" name="loc" id="loc" class="form-control">
        <label>nbVictimes :</label>
        <input type="text" name="nbVict" id="nbVict" class="form-control" required pattern="[0-9]+" min="0">
        <label>nbSecouriste :</label>
        <input type="text" name="nbSecour" id="nbSecour" class="form-control" required pattern="[0-9]+" min="0">
        <input type="submit" name="action" value="createMission" class="btn btn-primary">
    </form>
    <h2>Gestion de mission</h2>
    <div style="height: 430px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px; border: 1px solid #ddd;">
        <ul style="list-style-type: none; padding: 0;">
            <%
                for(Mission mission: UserManager.getInstance().getListeMissions()){
            %>
            <li style="border-bottom: 1px solid #ddd; padding: 10px 0;">
                <strong style="color: #333;">id :</strong> <%=mission.getIdMission() %>
                <br>
                <strong style="color: #333;">nom :</strong> <%=mission.getNomMission() %>
                <br>
                <strong style="color: #333;">nombre de victimes restantes :</strong> <%=mission.getNbVictimes() %>
                <br>
                <strong style="color: #333;">nombre de secouriste nécessaires :</strong> <%=mission.getNbSecoursite() %>
                <br>
                <strong style="color: #333;">nombre de secoursite sur l'intervention :</strong> <%=mission.getCurrNbSecoursite() %>
            </li>
            <br>
            <%
                }
            %>
        </ul>
    </div>
</div>

<div class="conteneur-4" style="height: 240px; padding: 20px;background-color: #f1f1f1;border: 1px solid #ddd;">
    <h2 style="color: #333;">Affectation de secouriste</h2>
    <form class="form-group" method="get" action="./Control">
        <label for="libre">Secouriste disponible :</label>
        <select name="libre" id="libre" class="form-control" required>
            <%
                for(Secouristes secour: UserManager.getInstance().getSecourLibre()){
            %>
            <option value="<%=secour.getId()%>"><%=secour.getNom()%> <%=secour.getPrenom()%></option>
            <%
                }
            %>
        </select>
        <label for="id">id mission :</label>
        <input type="text" name="id" id="id" class="form-control" required pattern="\d+(\.\d+)?">
        <input type="submit" name="action" value="affecter" class="btn btn-primary">
    </form>
</div>

</body>
</html>
