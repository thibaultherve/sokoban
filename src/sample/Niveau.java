package sample;

import java.util.ArrayList;

class Niveau {
    private String nom;
    private ArrayList<ArrayList<Integer>> level;
    private int posx;
    private int posy;

    Niveau(String _nom){
        nom = _nom;
        level = new ArrayList<>();
    }

    String getNom() {return nom;}

    Niveau(String _nom, ArrayList<ArrayList<Integer>> clone, int _posx, int _posy) {
        nom = _nom;
        level = clone;
        posx = _posy;
        posy = _posx;
    }

    Niveau(Niveau niveau) {
        nom = niveau.nom;
        level = new ArrayList<>();
        // Copie du level
        for(int i=0; i < niveau.get_level().size(); i++ ){
            ArrayList<Integer> tmp = new ArrayList<>(niveau.get_level().get(i));
            level.add(tmp);
        }
        posx = niveau.posx;
        posy = niveau.posy;
    }

    void afficher(){
        System.out.println("Nom : " + nom);
        System.out.println("Pos départ : ("+posx+","+posy+")");
        for (ArrayList<Integer> aLevel : level) {
            for (Integer anALevel : aLevel) {
                System.out.print(anALevel +", ");
            }
            System.out.println();
        }
    }

    ArrayList<ArrayList<Integer>> get_level(){
        return level;
    }

    int move(int x, int y){
        /*
        RT 0 = pas de déplacement
        RT 1 = déplacement de soko
        RT 2 = déplacement de soko et d'une caisse
         */
        int tailleX = level.size();
        int tailleY = level.get(posx).size();
        //Check si on sort des ArrayLists
        if ( (posx+x >= tailleX || posx+x < 0) || (posy+y >= tailleY || posy+y < 0) ){
            return 0;
        }
        int nextX = posx+x;
        int nextY = posy+y;
        int nextCase = level.get(nextX).get(nextY);
        int nextNextCase = -1;
        // Check si la case suivante est un mur
        if( nextCase == 35){
            // Pas de déplacement
            return 0;
        }
        // Avec cette initialisation de base on a un mouvement classique dans le vide
        int actuelRpl = 32; // On remplace le soko (64) par du vide (32)
        int nextRpl = 64; // On remplace la prochaine case par un soko (64)
        int nextNextRpl = 36;
        // Check si on est sur un goal (43) alors on remplace par un goal (46)
        if (level.get(posx).get(posy) == 43){
            actuelRpl = 46;
        }
        // Check si la case suivante est un goal (46)
        if( nextCase == 46 ){
            nextRpl = 43;
        }
        // Check si la case suivante est une caisse (36) ou une caisse goal (42)
        if( (nextCase == 36) || (nextCase == 42)){
            nextNextCase = level.get(nextX+x).get(nextY+y);
            // Check si la case suivante suivante est un mur (35) ou une autre caisse (36 / 42)
            if( (nextNextCase == 35) || (nextNextCase == 36) || (nextNextCase == 42) ){
                return 0;
            }
            // Check si la case suivante suivante est un goal (46)
            if( nextNextCase == 46 ){
                // Check si la case suivante est une caisse (36) ou une caisse goal (42)
                if( nextCase == 42 ){
                    nextRpl = 43;
                }
                nextNextRpl = 42;
            }
            // Check si la case suivante est une caisse goal et case suivante suivante est vide
            if( nextCase == 42 && nextNextCase == 32){
                nextRpl = 43;
                nextNextRpl = 36;
            }
        }
        level.get(posx).set(posy, actuelRpl);
        posx += x;
        posy += y;
        level.get(posx).set(posy, nextRpl);
        if(nextNextCase != -1){
            level.get(posx+x).set(posy+y, nextNextRpl);
            return 2;
        }
        return 1;
    }


    public boolean win() {
        for (ArrayList<Integer> aLevel : level) {
            for (Integer anALevel : aLevel) {
                if( anALevel == 46 || anALevel == 43){
                    return false;
                }
            }
        }
        return true;
    }

    public void undoMove(int x, int y, boolean caisse_bougee) {
        // Position de la caisse à bouger
        int xCaisse = posx-x;
        int yCaisse = posy-y;
        move(x, y);

        if (caisse_bougee)
        if( (level.get(xCaisse).get(yCaisse) == 42) || (level.get(xCaisse).get(yCaisse) == 36) ){
            // Nouvelle position de la caisse
            int newX = posx-x;
            int newY = posy-y;
            // Nouvelles représentations des cases
            int nextCase = 36;
            int oldCase = 32;

            // Si on a une caisse goal, on remplace par un goal
            if( level.get(xCaisse).get(yCaisse) == 42 ){
                oldCase = 46;
            }

            // Si on a un goal sur la prochaine case
            if( level.get(newX).get(newY) == 46){
                nextCase = 42;
            }

            level.get(xCaisse).set(yCaisse, oldCase);
            level.get(newX).set(newY, nextCase);
        }
    }
}
