package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class MonteurScene {
    ArrayList<Region> hautCentre = new ArrayList<Region>();
    ArrayList<Region> bas = new ArrayList<Region>();
    ArrayList<Region> haut = new ArrayList<>();
    Region centre;
    int largeur = 800;
    int hauteur = 400;

    public MonteurScene setLargeur(int l){
        largeur=l;
        return this;
    }

    public MonteurScene setHauteur(int l) {
        hauteur=l;
        return this;
    }

    public MonteurScene setCentre(Region node) {
        centre = node;
        return this;
    }

    public MonteurScene ajoutBas(Region node) {
        bas.add(node);
        return this;
    }

    public MonteurScene ajoutHaut(Region node){
        haut.add(node);
        return this;
    }

    public MonteurScene ajoutHautCentre(Region node){
        hautCentre.add(node);
        return this;
    }

    Scene retourneScene() {
        assert (centre !=null);
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setMinSize(largeur, hauteur);

        //Setting the padding
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        if (haut.size()!=0) {
            GridPane gridPaneHaut= new GridPane();
            gridPaneHaut.setAlignment(Pos.CENTER);
            gridPaneHaut.setMinSize(largeur, hauteur/8);
            gridPaneHaut.setPadding(new Insets(10, 10, 10, 10));
            //Setting the padding
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            int i=0;
            for (Region n:haut) {
                n.setMinSize(largeur/haut.size(),hauteur/8);
                gridPaneHaut.add(n,i,0);
                i++;
            }
            gridPane.add(gridPaneHaut,0,0);
        }

        if (hautCentre.size()!=0) {
            GridPane gridPaneHautCentre = new GridPane();
            gridPaneHautCentre.setAlignment(Pos.CENTER);
            gridPaneHautCentre.setMinSize(largeur, hauteur/8);
            gridPaneHautCentre.setPadding(new Insets(10, 10, 10, 10));
            //Setting the padding
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            int i=0;
            for (Region n:hautCentre) {
                n.setMinSize(largeur/hautCentre.size(),hauteur/8);
                gridPaneHautCentre.add(n,i,0);
                i++;
            }
            gridPane.add(gridPaneHautCentre,0,1);
        }

        gridPane.add(centre,0,2);
        centre.setMinSize(largeur, hauteur*6/8);

        if (bas.size()!=0) {
            GridPane gridPaneBas = new GridPane();
            gridPaneBas.setAlignment(Pos.CENTER);
            gridPaneBas.setMinSize(largeur, hauteur/8);
            gridPaneBas.setPadding(new Insets(10, 10, 10, 10));
            //Setting the padding
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            int i=0;
            for (Region n:bas) {
                n.setMinSize(largeur/bas.size(),hauteur/8);
                gridPaneBas.add(n,i,0);
                i++;
            }
            gridPane.add(gridPaneBas,0,3);
        }

        return new Scene(gridPane,largeur,hauteur);

    }
}
