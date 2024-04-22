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
        <div class="conteneur-med-2">
            <h1>Gestion médical</h1>
            <h2> Patients en attente</h2>
            <div style="height: 380px; overflow-y: auto;">

                <ul>
                    <%
                        for(Patient patient : UserManager.getInstance().getListeVictimes() ){
                    %>
                    <li>
                        Nom : <%= patient.getNom() %>
                        <br>
                        État de Gravité : <%= patient.getEtatGravite() %>

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
            <div style="height: 380px; overflow-y: auto;">
                <ul>
                    <%
                        for(Patient patient : UserManager.getInstance().getCurrentMedecin().getPatients()){
                    %>
                    <li>
                        Nom : <%=patient.getNom() %>
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

        <div class="conteneur-med-3">
            <h1>Gestion Stock médical</h1>
            <h2> Liste des materiels médicals</h2>
            <form method="get" action="./ControlInventaire">
                <div style="height: 380px; overflow-y: auto;">
                    <ul>
                        <%
                            for(Materiel materiel : StockManager.getInstance().getLesMaterielsMedicals() ){
                        %>
                        <li>
                            ID : <%= materiel.getId() %>
                            <br>
                            Nom : <%= materiel.getNom() %>
                            <br>
                            Quantité :<%= materiel.getQuantite() %>
                            <br>
                            Utilisé :<%= materiel.getUtilise() %>
                            <br>
                            Date limite de consommation :<%= materiel.getDci() %>
                            <br>
                            Lot : <%= materiel.getLot() %>
                            <br>
                            classeTherapeutique : <%= materiel.getClasseTherapeutique() %>
                            <br>
                            formDosage : <%= materiel.getFormDosage() %>
                            <br>
                        </li>
                        <%} %>
                    </ul>
                </div>
                <hr />
                <h2> Update Quantité</h2>
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
                <input type="submit" name="action" value="Update Stock Medical" />
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
                <input type="submit" name="action" value="Utilisation Materiel Medical" />
            </form>
            <hr />
            <h2>Supprimer du materiel</h2>
            <form method="get" action="./ControlInventaire">
                ID :
                <input type="text" name="Materiel_id" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                <input type="submit" name="action" value="Supprimer Materiel Medical" />
            </form>
            <hr />
            <h2>Commander du materiel</h2>
            <form method="get" action="./ControlInventaire">
                Nom :
                <input type="text" name="Materiel_nom" required/>

                <br>
                <input type="submit" name="action" value="Commander Materiel Medical" />
            </form>
            <hr />
            <h2> Liste des commandes médicaux</h2>
            <form method="get" action="./ControlInventaire">
                <div style="height: 380px; overflow-y: auto;">
                    <ul>
                        <%
                            for(Commande commande : StockManager.getInstance().getLesCommandes() ){
                                if (commande.getType().equals("medical")){
                        %>
                        <li>
                            ID : <%= commande.getId() %>
                            <br>
                            Nom : <%= commande.getMatCommande() %>
                            <br>
                        </li>
                        <%} %>
                        <%} %>
                    </ul>
                </div>
            </form>



        </div>
    </div>
</div>
</body>
</html>


