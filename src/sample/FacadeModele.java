package sample;

import javafx.scene.input.KeyCode;

public class FacadeModele {
    ModeleNbMove modeleNbMove = new ModeleNbMove(new ModeleConcret());
    ModeleDo modeleDo = new ModeleDo(modeleNbMove);

    public boolean move(KeyCode c) {
        modeleDo.move(c);
        return modeleDo.win();

    }

    public void reset() {
        modeleDo.reset();
    }

    public Niveau getNiveau() {
        return modeleDo.getNiveau();
    }

    public ModeleDo get_modeleDo() { return modeleDo; }

    public ModeleNbMove get_modeleNbMove() { return modeleNbMove; }

    public void set_liste_niveaux(String chemin) {
        modeleDo.set_liste_niveaux(chemin);
    }

    public void niveauPrécédent() {
        modeleDo.setActuel(-1);
    }

    public void niveauSuivant(){
        modeleDo.setActuel(1);
    }

    public int nbMove(){
        return modeleNbMove.nbMove;
    }

    public int nbPoussée(){
        return modeleNbMove.nbPoussée;
    }

    public void undo() {
        modeleDo.undo();
    }

    public void redo() {
        modeleDo.redo();
    }
}
