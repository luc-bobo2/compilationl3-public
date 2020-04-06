package util.graph;

import util.intset.IntSet;

import java.util.Stack;

public class ColorGraph {
    public Graph G;                 // Le graphe a colorer
    public int R;                   // Nombre de sommets
    public int K;                   // Nombre de couleurs
    private Stack<Integer> pile;
    public IntSet enleves;          // sommets enlevés
    public IntSet deborde;          // sommets qui débordent
    public int[] couleur;           // Tableau de couleurs
    public Node[] int2Node;         // Accès rapide au nodes
    static int NOCOLOR = -1;

    public ColorGraph(Graph G, int K, int[] phi) {
        this.G = G;
        this.K = K;
        pile = new Stack<Integer>();
        R = G.nodeCount();
        couleur = new int[R];
        enleves = new IntSet(R);
        deborde = new IntSet(R);
        int2Node = G.nodeArray();
        for (int v = 0; v < R; v++) {
            int preColor = phi[v];
            if (preColor >= 0 && preColor < K)
                couleur[v] = phi[v];
            else
                couleur[v] = NOCOLOR;
        }
    }

    /**
     * associe une couleur à tous les sommets se trouvant dans la pile
     */
    public void selection() {
    }

    /**
     * récupère les couleurs des voisins de t
     * @param t Le noeud t
     * @return Les couleurs des voisins
     */
    public IntSet couleursVoisins(int t) {
        return null;
    }

    /**
     * recherche une couleur absente de colorSet
     * @param colorSet
     * @return
     */
    public int choisisCouleur(IntSet colorSet) {
        return 0;
    }

    /**
     * calcule le nombre de voisins du sommet t
     */
    public int nbVoisins(int t) {
        return 0;
    }

    /**
     * simplifie le graphe d'interférence g
     * la simplification consiste à enlever du graphe les temporaires qui ont moins de k voisins
     * et à les mettre dans une pile
     * à la fin du processus, le graphe peut ne pas être vide, il s'agit des temporaires qui ont au moins k voisin
     */
    public int simplification() {
        return 0;
    }

    public void debordement() {
    }

    public void coloration() {
        this.simplification();
        this.debordement();
        this.selection();
    }

    void affiche() {
        System.out.println("vertex\tcolor");
        for (int i = 0; i < R; i++) {
            System.out.println(i + "\t" + couleur[i]);
        }
    }
}
