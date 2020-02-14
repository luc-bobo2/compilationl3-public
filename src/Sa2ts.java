import sa.*;
import ts.Ts;

public class Sa2ts extends SaDepthFirstVisitor <Void> {
    private SaNode root;
    private Ts tableGlobale;

    public Sa2ts (SaNode saRoot) {
        this.root = saRoot;
    }

    public Ts getTableGlobale() {
        this.tableGlobale = new Ts();
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
