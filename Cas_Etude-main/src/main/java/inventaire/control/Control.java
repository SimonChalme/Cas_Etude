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
    private DaoStock daoStock = new DaoStock();
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {


        String vueFinale = doAction(request);

        request.getRequestDispatcher(vueFinale).forward(request, response);
    }

    private void syncApplication(){
        StockManager.getInstance().setLesCommandes(daoStock.getCommandes());
        StockManager.getInstance().setLesMaterielsMedicals(daoStock.getMaterielsMedicals());
        StockManager.getInstance().setLesMaterielsLogistiques(daoStock.getMaterielsLogistiques());
    }
    private String doAction(HttpServletRequest request) throws IOException, ServletException {
        String vueFinale = "connexion.jsp";

        String action = request.getParameter("action");

        HttpSession session = request.getSession();
        String nameSession = (String) session.getAttribute("name");

        System.out.println("action: " + action);

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
        if ("Commander Materiel Medical".equals(action)) {
            String nom = request.getParameter("Materiel_nom");
            String type = "medical";
            daoStock.createCommandeMateriel(nom,type);
            syncApplication();
            vueFinale = "medical.jsp";
        }
        if ("Supprimer Materiel Medical".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            int id = Integer.parseInt(idString);
            daoStock.supprimeMateriel(nom,id);
            syncApplication();
            vueFinale = "medical.jsp";
        }

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
        if ("Utilisation Materiel Logistique".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            String utilisation = request.getParameter("utilisation1");
            int id = Integer.parseInt(idString);
            daoStock.updateUtilisation(nom, utilisation, id);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        if ("Utilisation Materiel Medical".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            String utilisation = request.getParameter("utilisation1");
            int id = Integer.parseInt(idString);
            daoStock.updateUtilisation(nom, utilisation, id);
            syncApplication();
            vueFinale = "medical.jsp";
        }

        if ("Commander Materiel Logistique".equals(action)) {
            String nom = request.getParameter("Materiel_nom");
            String type = "logistique";
            daoStock.createCommandeMateriel(nom,type);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        if ("Supprimer Materiel Logistique".equals(action)) {
            String idString = request.getParameter("Materiel_id");
            String nom = request.getParameter("Materiel_nom");
            int id = Integer.parseInt(idString);
            daoStock.supprimeMateriel(nom,id);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

        if ("Supprimer Commande".equals(action)) {
            String ID= request.getParameter("Commande_ID");
            int id = Integer.parseInt(ID);
            String nom = request.getParameter("Commande_Nom");
            daoStock.supprimeCommande(id,nom);
            syncApplication();
            vueFinale = "logistique.jsp";
        }

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
