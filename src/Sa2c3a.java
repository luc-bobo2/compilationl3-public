import c3a.C3a;
import c3a.C3aOperand;
import sa.SaDepthFirstVisitor;
import sa.SaNode;
import sa.SaProg;
import ts.Ts;

public class Sa2c3a  extends SaDepthFirstVisitor<C3aOperand> {
/*    SaNode saRoot;
    Ts table;*/
    private C3a c3a;

    public Sa2c3a (SaNode saRoot) {
        c3a = new C3a();
/*        this.saRoot = saRoot;
        this.table = table;*/
        saRoot.accept(this);
    }

    public C3a getC3a() {
        return c3a;
    }

    public C3aOperand visit(SaProg node) {

    }
}
