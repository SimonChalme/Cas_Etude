<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="utilisateur.DAO.DaoManager" %>
<%@ page import="utilisateur.model.UserManager" %>
<%@ page import="utilisateur.model.Patient" %>
<%@ page import="utilisateur.model.Mission" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Secouristes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="securite.css">
    <style> body { font-family: Arial, sans-serif; } .conteneur-parent { display: flex; flex-direction: column; align-items: center; margin-top: 50px; } .conteneur-med-1 { background-color: #f1f1f1; border: 1px solid #ddd; padding: 20px; border-radius: 10px; width: 100%; margin-bottom: 20px; } .conteneur-enfant { display: flex; justify-content: space-between; width: 100%; } .conteneur-med-2, .conteneur-med-3 { background-color: #f1f1f1; border: 1px solid #ddd; padding: 20px; border-radius: 10px; width: 48%;height:800px; } h1, h2 { margin-top: 0; margin-bottom: 20px; } ul { list-style: none; padding: 0; margin: 0; } li { margin-bottom: 10px; } input[type="text"], select { width: 100%; padding: 5px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #ced4da; } input[type="submit"] { background-color: #007bff; color: white; padding: 5px 10px; border: none; border-radius: 5px; cursor: pointer; width: 100%; } input[type="submit"]:hover { background-color: #0069d9; } .btn-danger, .btn-primary { width: auto; margin-top: 10px; } </style>

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
            <h2>Information mission</h2>
            <form  method="get" action="./Control">
                <div style="height: 600px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px;">
                    <ul>
                        <%
                            for(Mission mission : UserManager.getInstance().getListeMissions()){

                        %>
                        <li>
                            <strong style="color: #333;"> ID </strong> : <%= mission.getIdMission() %>
                            <br>
                            <strong style="color: #333;">Nom </strong>: <%= mission.getNomMission() %>
                            <br>
                            <strong style="color: #333;">Localisation </strong>: <%= mission.getLocalisation() %>
                            <br>
                            <strong style="color: #333;"> Nombre de victimes </strong><%= mission.getNbVictimes() %>
                            <br>
                            <hr />
                        </li>
                        <%} %>
                    </ul>
                </div>

            </form>
        </div>
        <div class="conteneur-med-3">
            <h2>Ajout de patients</h2>
            <form  method="get" action="./Control">
                Nouvel utilisateur
                id :<input type="text" name="id" id="cid" required pattern="[0-9]+" min="0">
                nom : <input type="text" name="nom" id="nom" required>
                prenom : <input type="text" name="prenom" id="prenom" required>
                état de gravité : <input type="number" id="etet" name="etat" min="1" max="10" required>
                <input type="submit" name="action" value="addPatient" class="btn btn-success">
            </form>
            <hr />
            <h2>Liste des nouveaux patients non affectés</h2>
            <form  method="get" action="./Control">
                <div style="height: 300px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px;">
                    <ul>
                        <%
                            for(Patient patient : UserManager.getInstance().getListeVictimes()){

                        %>
                        <li>
                            <strong style="color: #333;">ID </strong> : <%= patient.getId() %>
                            <br>
                            <strong style="color: #333;">Nom </strong>: <%= patient.getNom() %>
                            <br>
                            <strong style="color: #333;">Prénom </strong>: <%= patient.getPrenom() %>
                            <br>
                            <strong style="color: #333;">État de gravité </strong>: <%= patient.getEtatGravite() %>
                            <br>
                            <hr />
                        </li>
                        <%} %>
                    </ul>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
