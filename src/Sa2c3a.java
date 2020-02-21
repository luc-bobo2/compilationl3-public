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

    @Override
    public C3aOperand visit(SaProg node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaDecTab node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExp node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpInt node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpVar node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstEcriture node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaLInst node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaDecVar node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaLDec node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaAppel node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpAppel node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpAdd node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpSub node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpMult node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpDiv node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpInf node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpEqual node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpAnd node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpOr node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpNot node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaExpLire node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstBloc node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstSi node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        C3aOperand c3a = node.getVal().accept(this);
        return new C3aInstReturn(c3a, null);
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaLExp node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        return new C3aFunction(table.getFct(node.getNom()));
    }

    @Override
    public C3aOperand visit(SaVarIndicee node) {
        C3aOperand op = node.getIndice().accept(this);
        return new C3aVar(table.getVar(node.getNom()), op);
    }

    @Override
    public C3aOperand visit(SaVarSimple node) {
        return new C3aVar(table.getVar(node.getNom()), null);
    }
}
