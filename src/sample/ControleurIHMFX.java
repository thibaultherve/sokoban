package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

public class ControleurIHMFX {
    static Controleur controleur;
    VueIHMFX vue;
    Button reset;
    Button selectFichier;
    Button précédent;
    Button suivant;
    Button undo;
    Button redo;
    Button redoAll;

    ControleurIHMFX(Controleur controleur, VueIHMFX vue) {
        this.controleur = controleur;
        this.vue = vue;

        reset = new Button("Reset");
        reset.setOnAction(new ActionReset());

        selectFichier = new Button("Select Fichier");
        selectFichier.setOnAction(new ActionSelectFichier());

        précédent = new Button("Niveau Précédent");
        précédent.setOnAction(new ActionPrécédent());

        suivant = new Button("Niveau suivant");
        suivant.setOnAction(new ActionSuivant());

        undo = new Button("UNDO");
        undo.setOnAction(new ActionUndo());

        redo = new Button("REDO");
        redo.setOnAction(new ActionRedo());

        redoAll = new Button("RedoALL");
        redoAll.setOnAction(new ActionRedoAll());

        reset.setDisable(true);
        précédent.setDisable(true);
        suivant.setDisable(true);
        undo.setDisable(true);
        redo.setDisable(true);
        redoAll.setDisable(true);

    }

    class ActionRedoAll implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) { controleur.redoAll(); }

    }

    class ActionUndo implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) { controleur.undo(); }

    }

    class ActionRedo implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) { controleur.redo(); }
    }

    class ActionPrécédent implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            controleur.niveauPrécédent();
        }

    }

    class ActionSuivant implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            controleur.niveauSuivant();
        }

    }


    class ActionReset implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            controleur.reset();
        }

    }

    class ActionSelectFichier implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier Sokoban", "*.xsb"); //Filtre pour n'accepter que les fichiers ".xsb"
            fileChooser.getExtensionFilters().setAll(extensionFilter);
            fileChooser.setSelectedExtensionFilter(extensionFilter);

            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); // amene directement au dossier de l'executable
            fileChooser.setInitialDirectory(new File(currentPath));
            File file = fileChooser.showOpenDialog(selectFichier.getScene().getWindow());

            if (file != null) {
                controleur.set_liste_niveaux(file.getAbsolutePath());
                reset.setDisable(false);
                précédent.setDisable(false);
                suivant.setDisable(false);
                undo.setDisable(false);
                redo.setDisable(false);
                redoAll.setDisable(false);
            }
            else
            {
                reset.setDisable(true);
                précédent.setDisable(true);
                suivant.setDisable(true);
                undo.setDisable(true);
                redo.setDisable(true);
                redoAll.setDisable(true);
            }
        }

    }

    static class ActionMove implements EventHandler<KeyEvent> {

        public void handle(KeyEvent event) {
            controleur.move( event.getCode() );
        }
    }
}