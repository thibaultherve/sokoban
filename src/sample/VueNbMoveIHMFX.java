package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class VueNbMoveIHMFX {
    Controleur controleur;
    Label labelMove = new Label("Moves");
    Label labelPoussée = new Label("Pushes");
    CommandeNbMove commande;

    public VueNbMoveIHMFX(Controleur controleur){
        this.controleur = controleur;
        commande= controleur.commandeNbMove();
    }

    public void dessine(){
        int[] tab = commande.exec();
        labelMove.setText("Moves : \n" + tab[0]+"");
        labelMove.setAlignment(Pos.CENTER);
        labelPoussée.setText("Pushes : \n" + tab[1]+"");
        labelPoussée.setAlignment(Pos.CENTER);
    }
}
