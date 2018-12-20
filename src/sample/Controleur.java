package sample;

import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Controleur implements Sujet {
    private static Controleur singleton;


    public static Controleur getControleur() {
        if (singleton == null)
            singleton = new Controleur(new FacadeModele());
        return singleton;
    }

    FacadeModele facadeModele;
    ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    private Controleur(FacadeModele facadeModele) {
        this.facadeModele = facadeModele;
    }

    public void abonne(Observateur observateur) {
        observateurs.add(observateur);
    }

    @Override
    public void notifie() {
        for (Observateur observateur:observateurs)
            observateur.actualise();
    }

    public void undo(){
        facadeModele.undo();
        notifie();
    }

    public void redo(){
        facadeModele.redo();
        notifie();
    }

    public void redoAll(){

        ModeleDo modeleDo = facadeModele.get_modeleDo();
        ModeleNbMove modeleNbMove = facadeModele.get_modeleNbMove();
        modeleNbMove.reset();

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for(int i=0; i <= modeleDo.get_lastIndex(); i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }

                    int finalI = i;
                    modeleNbMove.move(modeleDo.listeMouvements.get(finalI));
                    notifie();
                }
                return null;
            }
        };
        new Thread(sleeper).start();
    }

    public void move(KeyCode c) {
        boolean win = facadeModele.move(c);
        notifie();
        if(win) {

            niveauSuivant();
        }
    }

    public void reset() {
        facadeModele.reset();
        notifie();
    }

    public CommandeNiveau commandeGetNiveau() {
        return new CommandeNiveau() {
            @Override
            public Niveau exec() {
                return facadeModele.getNiveau();
            }
        };
    }

    public CommandeNbMove commandeNbMove(){
        return new CommandeNbMove(){
            @Override
            public int[] exec(){
                int[] tab = new int[2];
                tab[0] = facadeModele.nbMove();
                tab[1] = facadeModele.nbPoussée();
                return tab;
            }
        };

    }

    public void set_liste_niveaux(String chemin) {
        facadeModele.set_liste_niveaux(chemin);
        notifie();
    }

    public void niveauPrécédent() {
        facadeModele.niveauPrécédent();
        notifie();
    }

    public void niveauSuivant() {
        facadeModele.niveauSuivant();
        notifie();
    }
}
