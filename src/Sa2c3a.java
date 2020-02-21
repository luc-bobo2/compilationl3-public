import c3a.C3a;
import c3a.C3aInstAdd;
import c3a.C3aOperand;
import sa.SaDecVar;
import sa.SaDepthFirstVisitor;
import sa.SaNode;
import sa.SaProg;
import ts.Ts;

public class Sa2c3a  extends SaDepthFirstVisitor<C3aOperand> {

    Ts table;
    private C3a c3a;

    public Sa2c3a (SaNode saRoot, Ts table) {
        c3a = new C3a();
        saRoot.accept(this);
        this.table = table;
    }

    public C3a getC3a() {
        return c3a;
    }

    public C3aOperand visit(SaDecVar node) {
        return node
    }
}
