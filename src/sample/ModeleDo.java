package sample;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ModeleDo implements Modele {
    Modele modeleNbMove;
    ArrayList<KeyCode> listeMouvements;
    ArrayList<Boolean> listeMouvementsABougeCaisse;
    int index;  // Permet de savoir où est-ce qu'on est dans l'Arraylist
    int lastIndex;

    public ModeleDo(ModeleNbMove modele){
        this.modeleNbMove = modele;
        listeMouvements = new ArrayList<>();
        listeMouvementsABougeCaisse = new ArrayList<>();
        index = -1;
        lastIndex = index;
    }

    @Override
    public Niveau getNiveau() {
        return modeleNbMove.getNiveau();
    }

    @Override
    public int move(KeyCode c) {
        int rt = modeleNbMove.move(c);
        if (rt == 1 || rt == 2){
            listeMouvements.add(index+1, c);
            listeMouvementsABougeCaisse.add(index+1, rt==2);
            index++;
            lastIndex = index;
        }
        return rt;
    }

    @Override
    public void reset() {
        modeleNbMove.reset();
        listeMouvements = new ArrayList<>();
        listeMouvementsABougeCaisse = new ArrayList<>();
        index = -1;
        lastIndex = index;
    }

    @Override
    public void setActuel(int n) {
        modeleNbMove.setActuel(n);
        listeMouvements = new ArrayList<>();
        listeMouvementsABougeCaisse = new ArrayList<>();
    }

    @Override
    public void set_liste_niveaux(String chemin) {
        modeleNbMove.set_liste_niveaux(chemin);
    }

    @Override
    public boolean win() {
        return modeleNbMove.win();
    }

    public void undo(){
        if( index >= 0 ){
            KeyCode undo = listeMouvements.get(index);
            int x = 0;
            int y = 0;

            switch(undo){
                case UP:
                    x = 1;
                    break;
                case DOWN:
                    x = -1;
                    break;
                case LEFT:
                    y = 1;
                    break;
                case RIGHT:
                    y = -1;
                    break;
            }
            ((ModeleNbMove)modeleNbMove).nbMove--;
            if (listeMouvementsABougeCaisse.get(index))
                ((ModeleNbMove)modeleNbMove).nbPoussée--;
            getNiveau().undoMove(x, y, listeMouvementsABougeCaisse.get(index));
            index--;

        }
    }

    public void redo(){
        if(index != lastIndex){
            index++;
            KeyCode redo = listeMouvements.get(index);
            listeMouvements.add(redo);
            modeleNbMove.move(redo);
        }
    }

    public int get_lastIndex() { return lastIndex; }


}
