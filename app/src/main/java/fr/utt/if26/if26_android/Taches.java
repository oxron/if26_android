package fr.utt.if26.if26_android;

public class Taches {
    public int id;
    public String tache;
    public int liste;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public int getListe() {
        return liste;
    }

    public void setListe(int liste) {
        this.liste = liste;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return tache;
    }
}
