package inventaire.control;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import inventaire.DAO.DaoStock;
import inventaire.model.StockManager;

@WebServlet(name = "ControlInventaire", value = "/ControlInventaire")

public class Control extends HttpServlet {

    // initialisation du gestionnaire de base de données
    private DaoStock daoStock = new DaoStock();

    // Méthode de traitement des requêtes GET
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {


        String vueFinale = doAction(request);

        request.getRequestDispatcher(vueFinale).forward(request, response);
    }

    // synchronisation de l'application
    private void syncApplication(){
        StockManager.getInstance().setLesCommandes(daoStock.getCommandes());
        StockManager.getInstance().setLesMaterielsMedicals(daoStock.getMaterielsMedicals());
        StockManager.getInstance().setLesMaterielsLogistiques(daoStock.getMaterielsLogistiques());
    }

    // Méthode de traitement des requêtes DO
    private String doAction(HttpServletRequest request) throws IOException, ServletException {
        String vueFinale = "connexion.jsp";

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        String nameSession = (String) session.getAttribute("name");

        System.out.println("action: " + action);

        // traitement de l'action "mise à jour de la quantité d'un matériel médical". On récupère l'id, le nom et la quantité du matériel à update et on met à jour les informations dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Update Stock Medical".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            String quantiteString = request.getParameter("Materiel_quantite");
            //conversion version en double
            int quantite = Integer.parseInt(quantiteString);
            int id = Integer.parseInt(idString);
            daoStock.updateStock(nom, quantite,id);
            syncApplication();
            vueFinale = "medical.jsp";
        }

        // traitement de l'action "commander du matériel médical". On récupère le nom, la quantité du matériel à commander et on crée une commande dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Commander Materiel Medical".equals(action)) {
            String nom = request.getParameter("Materiel_nom");
            String quantiteString = request.getParameter("Materiel_quantite");
            String type = "medical";
            int quantite = Integer.parseInt(quantiteString);
            daoStock.createCommandeMateriel(nom,type,quantite);
            syncApplication();
            vueFinale = "medical.jsp";
        }

        // traitement de l'action "supprimer du matériel médical". On récupère l'id et le nom du matériel à supprimer et on supprime le matériel de la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Supprimer Materiel Medical".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            int id = Integer.parseInt(idString);
            daoStock.supprimeMateriel(nom,id);
            syncApplication();
            vueFinale = "medical.jsp";
        }

        // traitement de l'action "mise à jour de la quantité d'un matériel logistique". On récupère l'id, le nom et la quantité du matériel à update et on met à jour les informations dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Update Stock Logistique".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            String quantiteString = request.getParameter("Materiel_quantite");
            int quantite = Integer.parseInt(quantiteString);
            int id = Integer.parseInt(idString);
            daoStock.updateStock(nom, quantite, id);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        // traitement de l'action "mise à jour de l'utilisation d'un matériel logistique". On récupère l'id, le nom et le nouvel état d'utilisation du matériel à update et on met à jour les informations dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Utilisation Materiel Logistique".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            String utilisation = request.getParameter("utilisation1");
            int id = Integer.parseInt(idString);
            daoStock.updateUtilisation(nom, utilisation, id);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        // traitement de l'action "mise à jour de l'utilisation d'un matériel médical". On récupère l'id, le nom et le nouvel état d'utilisation du matériel à update et on met à jour les informations dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Utilisation Materiel Medical".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            String utilisation = request.getParameter("utilisation1");
            int id = Integer.parseInt(idString);
            daoStock.updateUtilisation(nom, utilisation, id);
            syncApplication();
            vueFinale = "medical.jsp";
        }

        // traitement de l'action "commander du matériel logistique". On récupère le nom, la quantité du matériel à commander et on crée une commande dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Commander Materiel Logistique".equals(action)) {
            String nom = request.getParameter("Materiel_nom");
            String quantiteString = request.getParameter("Materiel_quantite");
            String type = "logistique";
            int quantite = Integer.parseInt(quantiteString);
            daoStock.createCommandeMateriel(nom,type,quantite);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        // traitement de l'action "supprimer du matériel logistique". On récupère l'id et le nom du matériel à supprimer et on supprime le matériel de la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Supprimer Materiel Logistique".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            int id = Integer.parseInt(idString);
            daoStock.supprimeMateriel(nom,id);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        // traitement de l'action "supprimer une commande". On récupère l'id et le nom de la commande à supprimer et on supprime la commande de la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Supprimer Commande".equals(action)) {
            String ID= request.getParameter("Commande_ID");
            int id = Integer.parseInt(ID);
            String nom = request.getParameter("Commande_Nom");
            daoStock.supprimeCommande(id,nom);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        // traitment de l'action "ajouter un matériel". On récupère les informations du matériel à ajouter et on ajoute le matériel dans la base de données
        // on sychronise ensuite l'application pour gérér l'affichage dynamique sur la page
        if ("Ajout Materiel".equals(action)) {
            String nom = request.getParameter("Materiel_nom");
            String quantiteString = request.getParameter("Materiel_quantite");
            String utilisation = request.getParameter("utilisation");
            String type = request.getParameter("Materiel_type");
            String dci = request.getParameter("Materiel_dci");
            String formDosage = request.getParameter("Materiel_formDosage");
            String classeTherapeutique = request.getParameter("Materiel_classe");
            String lotString = request.getParameter("Materiel_lot");
            //conversion version en double
            int quantite = Integer.parseInt(quantiteString);
            int lot = Integer.parseInt(lotString);
            daoStock.ajoutMateriel(nom, quantite, utilisation, type, dci, formDosage, classeTherapeutique, lot);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        return vueFinale;
    }

    public void destroy() {
    }
}
