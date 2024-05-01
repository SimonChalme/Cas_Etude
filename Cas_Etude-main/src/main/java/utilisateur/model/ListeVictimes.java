package utilisateur.model;


import java.util.ArrayList;

public class ListeVictimes {
    private ArrayList<Patient> lesVictimes;

    public ListeVictimes() {
        // ici j'immagine qu'on devra initialiser avec des requetes sur la BDD
        lesVictimes = new ArrayList<Patient>();
    }
    public ArrayList<Patient> getLesVictimes() {
        return lesVictimes;
    }

    public void setLesVictimes(ArrayList<Patient> lesVictimes) {
        this.lesVictimes = lesVictimes;
    }

    public void ajoutVictime(Patient victime) {
        lesVictimes.add(victime);
    }

}
