package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VueIHMFX {

    CommandeNiveau commandeNiveau;
    Niveau niveau;
    Map<Integer, Image> images;
    GridPane gridPane = new GridPane();
    Label labelNom;
    ArrayList<ArrayList<Pane>> listeCases;


    public VueIHMFX(Controleur controleur) throws FileNotFoundException {
        commandeNiveau = controleur.commandeGetNiveau();
        niveau = commandeNiveau.exec();

        labelNom = new Label(niveau.getNom());
        labelNom.setAlignment(Pos.CENTER);
        labelNom.setFont(new Font(70));

        gridPane.setPrefSize(1000,1000);

        images = new HashMap<>();
        images.put(35, new Image(new FileInputStream("img/mur.png"), 50, 50, true, true));
        images.put(36, new Image(new FileInputStream("img/caisse.png"), 50, 50, true, true));
        images.put(42, new Image(new FileInputStream("img/caisseV.png"), 50, 50, true, true));
        images.put(43, new Image(new FileInputStream("img/personnage_on_goal.png"), 50, 50, true, true));
        images.put(64, new Image(new FileInputStream("img/personnage.png"), 50, 50, true, true));
        images.put(32, new Image(new FileInputStream("img/vide.png"), 50, 50, true, true));
        images.put(46, new Image(new FileInputStream("img/goal.png"), 50, 50, true, true));

        initListeCases();
        dessine();
    }

    private void initListeCases()
    {
        listeCases = new ArrayList<>();
        for (int i = 0; i < niveau.get_level().size(); i++)
        {
            ArrayList<Pane> listeTmp = new ArrayList<>();
            for (int j = 0; j < niveau.get_level().get(i).size(); j++) //Pour chaque élément du tableau
            {
                Pane pane = new Pane();
                listeTmp.add(pane);
                gridPane.add(pane, j, i);
            }
            listeCases.add(listeTmp);
        }
    }

    private void reset()
    {
        for (int i = 0; i < niveau.get_level().size(); i++)
        {
            for (int j = 0; j < niveau.get_level().get(i).size(); j++) //Pour chaque élément du tableau
            {
                listeCases.get(i).get(j).getChildren().clear();
            }
        }
        niveau = commandeNiveau.exec();
        labelNom.setText(niveau.getNom());
        initListeCases();
    }

    public void dessine() {
        if( !(niveau.equals(commandeNiveau.exec())) )
            reset();

        for (int i = 0; i < niveau.get_level().size(); i++)
        {
            for (int j = 0; j < niveau.get_level().get(i).size(); j++) //Pour chaque élément du tableau
            {
                listeCases.get(i).get(j).getChildren().setAll(new ImageView(images.get(niveau.get_level().get(i).get(j))));
            }
        }
    }
}
