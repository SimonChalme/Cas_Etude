package utilisateur.model;

public class Mission {
    private int idMission;
    private String nomMission;
    private String localisation;
    private int nbVictimes;

    private int nbSecoursite;

    public int getIdMission() {
        return idMission;
    }

    public void setIdMission(int idMission) {
        this.idMission = idMission;
    }

    public String getNomMission() {
        return nomMission;
    }

    public void setNomMission(String nomMission) {
        this.nomMission = nomMission;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNbVictimes() {
        return nbVictimes;
    }

    public void setNbVictimes(int nbVictimes) {
        this.nbVictimes = nbVictimes;
    }

    public int getNbSecoursite() {
        return nbSecoursite;
    }

    public void setNbSecoursite(int nbSecoursite) {
        this.nbSecoursite = nbSecoursite;
    }

    public int getCurrNbSecoursite() {
        return currNbSecoursite;
    }

    public void setCurrNbSecoursite(int currNbSecoursite) {
        this.currNbSecoursite = currNbSecoursite;
    }

    private int  currNbSecoursite;

    public Mission(int idMission, String nomMission, String localisation, int nbVictimes, int nbSecoursite, int currNbSecoursite) {
        this.idMission = idMission;
        this.nomMission = nomMission;
        this.localisation = localisation;
        this.nbVictimes = nbVictimes;
        this.nbSecoursite = nbSecoursite;
        this.currNbSecoursite = currNbSecoursite;
    }




}
