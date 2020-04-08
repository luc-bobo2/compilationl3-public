package ig;

import fg.FgSolution;
import nasm.Nasm;
import nasm.NasmInst;
import util.graph.Graph;
import util.graph.Node;
import util.graph.NodeList;
import util.intset.IntSet;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

public class Ig {
    public Graph graph;     // Le graphe d'interference
    public FgSolution fgs;  // Les ensembles in et out
    public int regNb;       // Le nombre de registre fictifs
    public Nasm nasm;       // Le pre nasm
    public Node int2Node[];

    public Ig(FgSolution fgs) {
        this.fgs = fgs;
        this.graph = new Graph();
        this.nasm = fgs.nasm;
        this.regNb = this.nasm.getTempCounter();
        this.int2Node = new Node[regNb];
        this.construction();
    }

    public void construction() {
        // Creation des noeuds
        for (int i = 0; i < this.regNb; ++i) {
            this.int2Node[i] = graph.newNode();
        }

        for (NasmInst inst : nasm.listeInst) {
            IntSet in = fgs.in.get(inst);
            IntSet out = fgs.out.get(inst);

            // Parcours les arrêtes en entrée pour trouver celles qui interfèrent
            for (int r = 0; r < in.getSize(); ++r) {
                if (!in.isMember(r)) continue;
                for (int r2 = 0; r2 < in.getSize(); ++r2) {
                    if (r == r2 || !in.isMember(r2)) continue;
                    graph.addEdge(int2Node[r], int2Node[r2]);
                }
            }

            // Parcours les arrêtes en sortie pour trouver celles qui interfèrent
            for (int r = 0; r < out.getSize(); ++r) {
                if (!out.isMember(r)) continue;
                for (int r2 = 0; r2 < out.getSize(); ++r2) {
                    if (r == r2 || !out.isMember(r2)) continue;
                    graph.addEdge(int2Node[r], int2Node[r2]);
                }
            }
        }
    }

    public int[] getPrecoloredTemporaries() {
        int[] phi = new int[int2Node.length];
        Arrays.fill(phi, 0);
        return phi;
    }

    public void allocateRegisters() {
    }

    public void affiche(String baseFileName) {
        String fileName;
        PrintStream out = System.out;

        if (baseFileName != null) {
            try {
                baseFileName = baseFileName;
                fileName = baseFileName + ".ig";
                out = new PrintStream(fileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        for (int i = 0; i < regNb; i++) {
            Node n = this.int2Node[i];
            out.print(n + " : ( ");
            for (NodeList q = n.succ(); q != null; q = q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.println(")");
        }
    }
}
