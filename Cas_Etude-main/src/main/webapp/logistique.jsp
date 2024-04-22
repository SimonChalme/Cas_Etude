<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 18/04/2024
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="inventaire.model.StockManager" %>
<%@ page import="utilisateur.DAO.DaoManager" %>
<%@ page import="inventaire.model.Materiel" %>
<%@ page import="inventaire.model.Commande" %>
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
        <h1>Page Personnel Logistique :</h1>
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
            <h1>Gestion Stock Logistique</h1>
            <h2> Liste des materiels logistiques</h2>
            <form method="get" action="./ControlInventaire">
                <div style="height: 380px; overflow-y: auto;">
                    <ul>
                        <%
                            for(Materiel materiel : StockManager.getInstance().getLesMaterielsLogistiques() ){
                        %>
                        <li>
                            Id : <%= materiel.getId() %>
                            <br>
                            Nom : <%= materiel.getNom() %>
                            <br>
                            Quantité :<%= materiel.getQuantite() %>
                            <br>
                            Utilise :<%= materiel.getUtilise() %>
                            <br>
                        </li>
                        <%} %>
                    </ul>
                </div>
            </form>
            <h2> Update Quantité</h2>
            <form method="get" action="./ControlInventaire">
                ID :
                <input type="text" name="Materiel_id" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                Nouvelle quantité :
                <input type="text" name="Materiel_quantite" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                <input type="submit" name="action" value="Update Stock Logistique" />
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
                <input type="submit" name="action" value="Utilisation Materiel Logistique" />
            </form>
            <hr />

            <h2>Commander du materiel</h2>
            <form method="get" action="./ControlInventaire">

                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                <input type="submit" name="action" value="Commander Materiel Logistique" />
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
                <input type="submit" name="action" value="Supprimer Materiel Logistique" />
            </form>
        </div>
        <div class="conteneur-med-3">
            <h1>Gestion commande</h1>
            <h2> Liste des commandes</h2>
            <form method="get" action="./ControlInventaire">
                <div style="height: 380px; overflow-y: auto;">
                    <ul>
                        <%
                            for(Commande commande : StockManager.getInstance().getLesCommandes() ){
                        %>
                        <li>
                            ID : <%= commande.getId() %>
                            <br>
                            Nom : <%= commande.getMatCommande() %>
                            <br>
                        </li>
                        <%} %>
                    </ul>
                </div>
            </form>
            <h2>Supprimer la commande</h2>
            Ne supprimer la commande que lorsque vous avez ajouter le matériel
            <form method="get" action="./ControlInventaire">
                ID :
                <input type="text" name="Commande_ID" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique">
                <br>
                Nom :
                <input type="text" name="Commande_Nom" required >
                <br>
                <input type="submit" name="action" value="Supprimer Commande" />
            </form>
            <h2> Ajouter du matériel dans les stocks</h2>
            Si des informations demandées ne sont pas disponibles, veuillez remplir la case par 0 :
            <form method="get" action="./ControlInventaire">
                Nom :
                <input type="text" name="Materiel_nom" required/>
                <br>
                Quantité :
                <input type="text" name="Materiel_quantite" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                Utilisé :
                <select name="utilisation" id="utilisation" required>
                    <option value="non">non</option>
                    <option value="oui">oui</option>
                </select>
                <br>
                Type :
                <select name="Materiel_type" id="Materiel_type" required>
                    <option value="medical">medical</option>
                    <option value="logistique">logistique</option>
                </select>
                <br>
                date limite de consommation (format jj/mm/aaaa):
                <input type="text" name="Materiel_dci" required required pattern="(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((20)\d\d)" title="Veuillez entrer une date au format jj/mm/aaaa"/>
                <br>
                formDosage :
                <input type="text" name="Materiel_formDosage" required/>
                <br>
                classeTherapeutique :
                <input type="text" name="Materiel_classe" required/>
                <br>
                lot :
                <input type="text" name="Materiel_lot" required pattern="\d+(\.\d+)?" title="Veuillez entrer une valeur numérique"/>
                <br>
                <input type="submit" name="action" value="Ajout Materiel" />
            </form>
            <hr />

        </div>
    </div>
</div>
</body>
</html>
