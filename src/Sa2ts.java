import sa.*;
import ts.Ts;

public class Sa2ts extends SaDepthFirstVisitor <Void> {
    private SaNode root;
    private Ts tableGlobale;

    public Sa2ts (SaNode saRoot) {
        this.root = saRoot;
        this.tableGlobale = null;
    }

    public SaNode getRoot() {
        return root;
    }

    public void setRoot(SaNode root) {
        if (this.root == root) return;
        this.root = root;
        this.tableGlobale = null;
    }

    public Ts getTableGlobale() {
        // Create the table only if it haven't been created (compute heavy)
        if (this.tableGlobale == null) {
            this.tableGlobale = new Ts();
            // TODO, do the things !
        }

        return this.tableGlobale;
    }

    @Override
    public Void visit(SaDecVar node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecTab node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaDecFonc node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaVarSimple node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaVarIndicee node) {
        return super.visit(node);
    }

    @Override
    public Void visit(SaAppel node) {
        return super.visit(node);
    }
}
