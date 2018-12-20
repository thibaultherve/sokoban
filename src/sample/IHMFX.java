package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class IHMFX extends Application implements Observateur {
    VueIHMFX vue;
    VueNbMoveIHMFX vueNbMove;

    public void actualise(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vueNbMove.dessine();
                vue.dessine();
            }
        });
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controleur controleur = Controleur.getControleur();
        controleur.abonne(this);

        vue = new VueIHMFX(controleur);
        vue.gridPane.setAlignment(Pos.CENTER);
        ControleurIHMFX controleurIHMFX = new ControleurIHMFX(controleur, vue);

        vueNbMove = new VueNbMoveIHMFX(controleur);
        vueNbMove.labelPoussée.setAlignment(Pos.CENTER);
        vueNbMove.labelMove.setAlignment(Pos.CENTER);


        /* montage de la scene */
        MonteurScene monteurScene = new MonteurScene();

        //TODO AFFICHER / SUPPRIMER LES BOUTONS PAS ACCESSIBLE
        Scene scene = monteurScene.
                ajoutHautCentre(vue.labelNom).
                setCentre(vue.gridPane).
                ajoutHaut(controleurIHMFX.selectFichier).
                ajoutHaut(controleurIHMFX.précédent).
                ajoutHaut(controleurIHMFX.suivant).
                ajoutBas(controleurIHMFX.reset).
                ajoutBas(controleurIHMFX.undo).
                ajoutBas(controleurIHMFX.redo).
                ajoutBas(controleurIHMFX.redoAll).
                ajoutBas(vueNbMove.labelMove).
                ajoutBas(vueNbMove.labelPoussée).
                setLargeur(800).
                setHauteur(800).
                retourneScene();

        primaryStage.setScene(scene);

        primaryStage.setTitle("Sokoban");
        primaryStage.setMaximized(true);
        primaryStage.show();

        // Prise en charge des entrées clavier
        scene.setOnKeyPressed(new ControleurIHMFX.ActionMove());
    }


    public void lance() {
        launch(new String[]{});
    }
}

