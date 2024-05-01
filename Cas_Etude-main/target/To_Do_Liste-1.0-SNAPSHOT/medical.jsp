<%@ page import="utilisateur.model.UserManager" %>
<%@ page import="utilisateur.model.Patient" %>
<%@ page import="inventaire.model.StockManager" %>
<%@ page import="inventaire.model.Materiel" %>
<%@ page import="inventaire.model.Commande" %>
<%@ page import="utilisateur.DAO.DaoManager" %><%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Médecin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="medecin.css">
    <style> body { font-family: Arial, sans-serif; margin: 0; padding: 0; } .conteneur-parent { display: flex; flex-direction: column; align-items: center; padding: 20px; } .conteneur-med-1 { background-color: #f1f1f1; border: 1px solid #ddd; padding: 20px; margin-bottom: 20px; width: 100%; box-sizing: border-box; } .conteneur-enfant { display: flex; flex-direction: row; justify-content: space-between; width: 100%; } .conteneur-med-2, .conteneur-med-3 { background-color: #f1f1f1; border: 1px solid #ddd; padding: 20px; width: 48%; box-sizing: border-box; } h1, h2 { margin: 0 0 20px 0; } ul { list-style-type: none; padding: 0; margin: 0; } li { margin-bottom: 20px; } input[type="text"], select {width: 100%; padding: 5px; margin-bottom: 10px; border-radius: 5px; border: 1px solid #ced4da; } input[type="submit"] {  color: white; padding: 5px 10px; border: none; border-radius: 5px; cursor: pointer; width: 100%; } input[type="submit"]:hover { } </style>

</head>
<body>
<div class="conteneur-parent">
    <div class="conteneur-med-1">
        <h1>Page Personnel Médical :</h1>
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
        <div class="conteneur-med-2" style="height : 2500px">
            <h1>Gestion médical</h1>
            <h2> Patients en attente</h2>
            <div style="height: 380px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px;">

                <ul>
                    <%
                        for(Patient patient : UserManager.getInstance().getListeVictimes() ){
                    %>
                    <li>
                        <strong style="color: #333;">Nom </strong>: <%= patient.getNom() %>
                        <br>
                        <strong style="color: #333;"> Prénom </strong> : <%=patient.getPrenom() %>
                        <br>
                        <strong style="color: #333;"> État de Gravité </strong> : <%= patient.getEtatGravite() %>

                        <form method="get" action="./Control">
                            <input type="hidden" name="idPatient" value="<%= patient.getId() %>">
                            <input type="submit" name="action" value="PrendreEnCharge" class="btn btn-primary">
                        </form>
                    </li>

                    <br>
                    <%
                        }
                    %>
                </ul>
            </div>
            <hr />

            <h2> Mes patients</h2>
            <div style="height: 380px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px;">
                <ul>
                    <%
                        for(Patient patient : UserManager.getInstance().getCurrentMedecin().getPatients()){
                    %>
                    <li>
                        <strong style="color: #333;"> Nom </strong> : <%=patient.getNom() %>
                        <br>
                        <strong style="color: #333;"> Prénom </strong> : <%=patient.getPrenom() %>
                        <br>
                        <strong style="color: #333;"> État de Gravité </strong> : <%=patient.getEtatGravite() %>
                        <form method="get" action="./Control">
                            <input type="hidden" name="idPatient" value="<%= patient.getId() %>">
                            <input type="submit" name="action" value="Fin Prise en charge" class="btn btn-danger">
                        </form>
                    </li>
                    <br>
                    <%
                        }
                    %>
                </ul>
            </div>


        </div>

        <div class="conteneur-med-3" style="height : 2500px">
            <h1>Gestion Stock médical</h1>
            <h2> Liste des materiels médicals</h2>
            <form method="get" action="./ControlInventaire">
                <div style="height: 380px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px;border: 2px solid #ddd;border-radius: 10px;">
                    <ul>
                        <%
                            for(Materiel materiel : StockManager.getInstance().getLesMaterielsMedicals() ){
                        %>
                        <li>
                            <strong style="color: #333;"> ID </strong>: <%= materiel.getId() %>
                            <br>
                            <strong style="color: #333;">Nom </strong>: <%= materiel.getNom() %>
                            <br>
                            <strong style="color: #333;">Quantité </strong> :<%= materiel.getQuantite() %>
                            <br>
                            <strong style="color: #333;">Utilisé </strong>:<%= materiel.getUtilise() %>
                            <br>
                            <strong style="color: #333;">Date limite de consommation </strong>:<%= materiel.getDci() %>
                            <br>
                            <strong style="color: #333;">Lot </strong>: <%= materiel.getLot() %>
                            <br>
                            <strong style="color: #333;">classeTherapeutique </strong>: <%= materiel.getClasseTherapeutique() %>
                            <br>
                            <strong style="color: #333;">formDosage </strong>: <%= materiel.getFormDosage() %>
                            <br>
                            <hr />
                        </li>
                        <%} %>
                    </ul>
                </div>
                <hr />
                <h2> Update de la Quantité</h2>
            </form>
            <form method="get" action="./ControlInventaire">
                ID :
                <input type="text" name="Materiel_id" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique" />
                <br>
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                Nouvelle quantité :
                <input type="text" name="Materiel_quantite" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                <input type="submit" name="action" value="Update Stock Medical" class="btn btn-primary"/>
            </form>
            <hr />
            <h2>Update de l'utilisation du matériel</h2>
            <form method="get" action="./ControlInventaire">
                ID :
                <input type="text" name="Materiel_id" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                Utilisé :
                <select name="utilisation1" id="utilisation1" required>
                    <option value="oui">oui</option>
                    <option value="non">non</option>
                </select>
                <br>
                <input type="submit" name="action" value="Utilisation Materiel Medical" class="btn btn-primary" />
            </form>
            <hr />
            <h2>Supprimer du matériel</h2>
            <form method="get" action="./ControlInventaire">
                ID :
                <input type="text" name="Materiel_id" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                <input type="submit" name="action" value="Supprimer Materiel Medical" class="btn btn-danger"/>
            </form>
            <hr />
            <h2>Commander du matériel</h2>
            <form method="get" action="./ControlInventaire">
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                Quantité
                <input type="text" name="Materiel_quantite" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                <input type="submit" name="action" value="Commander Materiel Medical" class="btn btn-primary"/>
            </form>
            <hr />
            <h2> Liste des commandes médicaux</h2>
            <form method="get" action="./ControlInventaire">
                <div style="height: 380px; overflow-y: auto;border: 2px solid #ddd;border-radius: 10px;">
                    <ul>
                        <%
                            for(Commande commande : StockManager.getInstance().getLesCommandes() ){
                                if (commande.getType().equals("medical")){
                        %>
                        <li>
                            <strong style="color: #333;">ID </strong> : <%= commande.getId() %>
                            <br>
                            <strong style="color: #333;">Nom </strong>: <%= commande.getMatCommande() %>
                            <br>
                            <strong style="color: #333;">Quantité </strong>: <%= commande.getQuantite() %>
                            <br>
                            <hr />
                        </li>
                        <%} %>
                        <%} %>
                    </ul>
                </div>
            </form>
            <hr />


        </div>
    </div>
</div>
</body>
</html>


