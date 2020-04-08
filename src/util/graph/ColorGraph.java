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
    public IntSet C;
    public int[] couleur;           // Tableau de couleurs
    public Node[] int2Node;         // Accès rapide au nodes
    public static int NOCOLOR = -1;

    public ColorGraph(Graph G, int K, int[] phi) {
        this.G = G;
        this.K = K;
        pile = new Stack<Integer>();
        R = G.nodeCount();
        couleur = new int[R];
        enleves = new IntSet(R);
        deborde = new IntSet(R);
        C = new IntSet(K+1);
        for (int i = 1; i <= K; ++i) {
            C.add(i);
        }
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
     * récupère les couleurs des voisins de t
     * @param t Le noeud t
     * @return Les couleurs des voisins
     */
    public IntSet couleursVoisins(int t) {
        IntSet couleursVoisins = new IntSet(nbVoisins(t));
        // Parcour les prédécesseurs puis les suivants
        // Pour chacun on parcours jusqu'à ce qu'il n'y ai tail == null
        for (NodeList list :  new NodeList[]{int2Node[t].preds, int2Node[t].succs}) {
            do {
                if (list.head != null)
                    couleursVoisins.add(list.head.mykey);
            } while ((list = list.tail) != null);
        }
        return couleursVoisins;
    }

    /**
     * recherche une couleur absente de colorSet
     */
    public int choisisCouleur(IntSet colorSet) {
        for (int color = 0; color < colorSet.getSize(); ++color) {
            if (!colorSet.isMember(color))
                return color;
        }
        throw new Error("no color available");
    }

    /**
     * calcule le nombre de voisins du sommet t
     */
    public int nbVoisins(int t) {
        int nbVoisins = 0;
        for (NodeList list :  new NodeList[]{int2Node[t].preds, int2Node[t].succs}) {
            do {
                if (list.head != null)
                    ++nbVoisins;
            } while ((list = list.tail) != null);
        }
        return nbVoisins;
    }

    private int choisis_sommet() {
        for (int i = 0; i < pile.size(); ++i) {
            if (!pile.contains(i)) return i;
        }
        throw new Error("auncun sommet");
    }

    /**
     * Algorithm 5
     */
    public void simplification() {
        pile = new Stack<>();
        int nbPreColore = 0;
        for (int v : couleur) {
            if (v != NOCOLOR)
                ++nbPreColore;
        }
        int N = R - nbPreColore;

        boolean modif = true;
        while (pile.size() != N && modif) {
            modif = false;
            for (int s = 0, l = int2Node.length; s < l; ++s) {
                // Ignore les sommets enlevés
                if (enleves.isMember(s)) continue;

                if (nbVoisins(s) < K && couleur[s] == NOCOLOR) {
                    pile.push(s);
                    enleves.add(s);
                    modif = true;
                }
            }
        }
    }

    /**
     * Algorithm 4
     */
    public void debordement() {
        // deborde←∅
        deborde = new IntSet(R);
        while (pile.size() != R) {
            // s←choisis_sommet
            int s = choisis_sommet();
            // empile(s)
            pile.push(s);
            //S←S−{s}
            enleves.add(s);
            // deborde=deborde∪{s}
            IntSet tmp = new IntSet(deborde.getSize());
            tmp.add(s);
            deborde = deborde.union(tmp);
            // Simplifie
            simplification();
        }
    }

    /**
     * Algorithm 6
     * associe une couleur à tous les sommets se trouvant dans la pile
     */
    public void selection() {
        while (pile.size() > 0) {
            int s = pile.pop();
            IntSet CV = couleursVoisins(s);
            if (CV.getSize() != K) {
                couleur[s] = choisisCouleur(this.C.minus(CV));
            }
        }
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
