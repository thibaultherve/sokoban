package sample;

import javafx.scene.input.KeyCode;

public interface Modele {
    public Niveau getNiveau();
    public int move(KeyCode c);
    public void reset();
    void setActuel(int n);
    void set_liste_niveaux(String chemin);
    public boolean win();
}
