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
        node.getVariables().accept(this);
        node.getFonctions().accept(this);
        return null;
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
        C3aOperand arg = node.getArg().accept(this);
        c3a.ajouteInst(new C3aInstWrite(arg, ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
        /*
            $labelTest: do test
            if $test = 0 goto $labelEnd
            do code
            goto $labelTest
            $labelEnd: next
         */
        C3aLabel labelTest = c3a.newAutoLabel();
        C3aLabel labelEnd = c3a.newAutoLabel();
        c3a.addLabelToNextInst(labelTest);
        C3aOperand testResult = node.getTest().accept(this);
        c3a.ajouteInst(new C3aInstJumpIfEqual(testResult, new C3aConstant(0), labelEnd, ""));
        node.getFaire().accept(this);
        c3a.ajouteInst(new C3aInstJump(labelTest, ""));
        c3a.addLabelToNextInst(labelEnd);
        return null;
    }

    @Override
    public C3aOperand visit(SaLInst node) {
        if(node.getTete() != null) {
            node.getTete().accept(this);
            node.getQueue().accept(this);
        }
        return null;
    }

    @Override
    public C3aOperand visit(SaDecVar node) {
        throw new UnsupportedOperationException(); // TODO
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        C3aOperand result = node.getLhs().accept(this);
        C3aOperand op1 = node.getRhs().accept(this);
        C3aInstAffect affect = new C3aInstAffect(op1, result, "");
        c3a.ajouteInst(affect);
        return null; // TODO
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
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aInstAdd add = new C3aInstAdd(op1, op2, c3a.newAutoLabel(), "");
        c3a.ajouteInst(add);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpSub node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aInstSub sub = new C3aInstSub(op1, op2, c3a.newAutoLabel(), "");
        c3a.ajouteInst(sub);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpMult node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aInstMult mult = new C3aInstMult(op1, op2, c3a.newAutoLabel(), "");
        c3a.ajouteInst(mult);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpDiv node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aInstDiv div = new C3aInstDiv(op1, op2, c3a.newAutoLabel(), "");
        c3a.ajouteInst(div);
        return null;
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
        C3aLabel l0 = c3a.newAutoLabel();
        C3aLabel l1 = c3a.newAutoLabel();

        // if exp = 0 goto l0
        C3aOperand op1 = node.getTest().accept(this);
        c3a.ajouteInst(new C3aInstJumpIfEqual(op1, new C3aConstant(0), l0, ""));
        // CODE ALORS
        node.getAlors().accept(this);
        // goto l1
        c3a.ajouteInst(new C3aInstJump(l1, ""));

        // l0:  CODE SINON
        c3a.addLabelToNextInst(l0);
        node.getSinon().accept(this);

        // Add label for next operation
        c3a.addLabelToNextInst(l1);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        c3a.ajouteInst(new C3aInstReturn(node.getVal().accept(this), ""));
        return null;
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
