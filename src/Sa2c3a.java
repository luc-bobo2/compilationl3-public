import c3a.*;
import sa.*;
import ts.Ts;

public class Sa2c3a  extends SaDepthFirstVisitor<C3aOperand> {

    private Ts table;
    private C3a c3a;

    public Sa2c3a (SaNode saRoot, Ts table) {
        this.c3a = new C3a();
        this.table = table;
        saRoot.accept(this);
    }

    public C3a getC3a() {

        return c3a;
    }

    public C3aOperand visit(SaDecVar node) {
        return new C3aVar(node.tsItem,c3a.);
    }

    public C3aOperand visit(SaDecTab node){
    }

    public C3aOperand visit(SaDecFonc node) {
       // node.accept(this);
        return new C3aFunction(node.tsItem);
    }

    public C3aOperand visit(SaExpSub node) {
    }
}
