package fg;

import nasm.*;
import util.graph.Graph;
import util.graph.Node;
import util.graph.NodeList;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Fg implements NasmVisitor<Void> {
    public Nasm nasm;
    public Graph graph;
    Map<NasmInst, Node> inst2Node;
    Map<Node, NasmInst> node2Inst;
    Map<String, NasmInst> label2Inst;

    public Fg(Nasm nasm) {
        this.nasm = nasm;
        this.inst2Node = new HashMap<NasmInst, Node>();
        this.node2Inst = new HashMap<Node, NasmInst>();
        this.label2Inst = new HashMap<String, NasmInst>();
        this.graph = new Graph();

        // Pour chaque inst, on l'ajoute à nos Map
        nasm.listeInst.forEach(inst -> {
            Node node = graph.newNode();
            this.inst2Node.put(inst, node);
            this.node2Inst.put(node, inst);
            if (inst.label != null) {
                this.label2Inst.put(inst.label.toString(), inst);
            }
        });

        // On fait visiter chaque noeud pour créer les arcs
        nasm.listeInst.forEach(inst -> inst.accept(this));
    }

    public void affiche(String baseFileName) {
        String fileName;
        PrintStream out = System.out;

        if (baseFileName != null) {
            try {
                baseFileName = baseFileName;
                fileName = baseFileName + ".fg";
                out = new PrintStream(fileName);
            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }

        for (NasmInst nasmInst : nasm.listeInst) {
            Node n = this.inst2Node.get(nasmInst);
            out.print(n + " : ( ");
            for (NodeList q = n.succ(); q != null; q = q.tail) {
                out.print(q.head.toString());
                out.print(" ");
            }
            out.println(")\t" + nasmInst);
        }
    }

    public Void visit(NasmAdd inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmCall inst) {
        String fctName = ((NasmLabel) inst.address).val;

        if (fctName.equals("iprintLF"))
            return null;
        else {
            Node src = inst2Node.get(inst);
            graph.addEdge(src, inst2Node.get(label2Inst.get(fctName)));
        }
        return null;
    }

    public Void visit(NasmDiv inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmMul inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmOr inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmCmp inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmInst inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJge inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJl inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJg inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJe inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJle inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJne inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmJmp inst) {
        Node src = inst2Node.get(inst);
        Node dest = inst2Node.get(label2Inst.get(((NasmLabel)inst.address).val));
        graph.addEdge(src, dest);
        return null;
    }

    public Void visit(NasmNot inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmPop inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmRet inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmXor inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmAnd inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmMov inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmPush inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmSub inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmEmpty inst) {
        Node src = inst2Node.get(inst);
        final int l = src.label()+1;
        if (l < graph.nodeArray().length)
            graph.addEdge(src, graph.nodeArray()[l]);
        return null;
    }

    public Void visit(NasmAddress operand) {
        return null;
    }

    public Void visit(NasmConstant operand) {
        return null;
    }

    public Void visit(NasmLabel operand) {
        return null;
    }

    public Void visit(NasmRegister operand) {
        return null;
    }
}
