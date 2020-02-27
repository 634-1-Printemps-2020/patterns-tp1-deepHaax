package metier;

import outils.Labyrinthe;

import java.util.*;

public class PyRat {

    final Map<Point, List<Point>> laby = Labyrinthe.getLaby();
    final List<Point> listFromages = Labyrinthe.getFromages();
    final Set<Point> setFromage = new HashSet<Point>();
    final Set<Point> setPoint = new HashSet<Point>();
    final List<Point> listPointVisited = new ArrayList<Point>();
    final List<Point> listPointInatteignables = new ArrayList<Point>();


    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        fillSetFrommage();
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(2,3);
        Point pt2 = new Point(2,2);
        System.out.println((fromageIci(pt1) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position " + pt2);
        System.out.println((passagePossible(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage de " + pt1 + " vers " + pt2);
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position));
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos) {
        if (listFromages.contains(pos)){
            return true;
        }
        return false;
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        if (setFromage.contains(pos)){
            return true;
        }
        return false;
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a) {
        List<Point> listValueOfKey = laby.get(de);
        if(listValueOfKey.contains(a)){
            return true;
        }
        return false;
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        fillSetPoint(de);
        if(setPoint.contains(a)){
            return true;
        }
        return false;
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos) {
        parcoursRecursif(pos);
        for (Map.Entry<Point, List<Point>> entry : laby.entrySet()) {
            if (!listPointVisited.contains(entry.getKey())){ listPointInatteignables.add(entry.getKey());}
        }
        return listPointInatteignables;
    }



    //-------------------
    //OTHER METHOD NEEDED
    //-------------------
    private void fillSetFrommage(){
        for (Point point : listFromages) {
            setFromage.add(point);
        }
    }

    private void fillSetPoint(Point de){
        List<Point> listValueOfKey = laby.get(de);
        for (Point point : listValueOfKey) {
            setPoint.add(point);
        }
    }

    private void parcoursRecursif(Point pos) {
        listPointVisited.add(pos);
        for (Point voisin : laby.get(pos)) { if (!listPointVisited.contains(voisin)) { parcoursRecursif(voisin); } }
        //listPointVisited.remove(pos);
    }
}