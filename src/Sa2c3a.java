import c3a.*;
import sa.*;
import ts.Ts;
import ts.TsItemFct;

public class Sa2c3a  extends SaDepthFirstVisitor<C3aOperand> {

    private Ts table;
    private C3a c3a;
    private Ts tableLocale = null;

    public Sa2c3a (SaNode saRoot, Ts table) {
        this.c3a = new C3a();
        this.table = table;
        saRoot.accept(this);
    }

    public C3a getC3a() {
        return c3a;
    }

    @Override
    public C3aOperand visit(SaDecTab node) {
        // Vide pour le moment
        return null;
    }

    @Override
    public C3aOperand visit(SaDecVar node) {
        // Vide pour le moment
        return null;
    }

    @Override
    public C3aOperand visit(SaExpNot node) {
        // Vide pour le moment
        return null;
    }

    @Override
    public C3aOperand visit(SaExpEqual node) {
        // Vide pour le moment
        return null;
    }

    @Override
    public C3aOperand visit(SaExp node) {
        // Vide pour le moment
        return null;
    }

    @Override
    public C3aOperand visit(SaProg node) {
        node.getFonctions().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstEcriture node) {
        C3aOperand arg = node.getArg().accept(this);
        c3a.ajouteInst(new C3aInstWrite(arg, ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node) {
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
    public C3aOperand visit(SaExpAdd node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aTemp c3aTemp = c3a.newTemp();
        C3aInstAdd add = new C3aInstAdd(op1, op2, c3aTemp, "");
        c3a.ajouteInst(add);
        return c3aTemp;
    }

    @Override
    public C3aOperand visit(SaExpSub node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aTemp c3aTemp = c3a.newTemp();
        C3aInstSub sub = new C3aInstSub(op1, op2, c3aTemp, "");
        c3a.ajouteInst(sub);
        return c3aTemp;
    }

    @Override
    public C3aOperand visit(SaExpMult node) {
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        C3aTemp c3aTemp = c3a.newTemp();
        C3aInstMult mult = new C3aInstMult(op1, op2, c3aTemp, "");
        c3a.ajouteInst(mult);
        return c3aTemp;
    }

    @Override
    public C3aOperand visit(SaExpDiv node) {
        C3aOperand c3aOperand = node.getOp1().accept(this);
        C3aOperand c3aOperand1 = node.getOp2().accept(this);
        C3aTemp c3aTemp = c3a.newTemp();
        C3aInstDiv c3aInstDiv = new C3aInstDiv(c3aOperand ,c3aOperand1, c3aTemp, "");
        c3a.ajouteInst(c3aInstDiv);
        return c3aTemp;
    }

    @Override
    public C3aOperand visit(SaInstSi node) {
        C3aLabel l0 = c3a.newAutoLabel();
        C3aLabel l1 = c3a.newAutoLabel();
        C3aOperand op1 = node.getTest().accept(this);

        // if exp = 0 goto label
        C3aLabel target =  node.getSinon() != null ? l0 : l1;
        c3a.ajouteInst(new C3aInstJumpIfEqual(op1, new C3aConstant(0), target, ""));

        // CODE else
        node.getAlors().accept(this);

        // If else, had the code
        if (node.getSinon() != null) {
            // goto l1
            c3a.ajouteInst(new C3aInstJump(l1, ""));

            // l0:  CODE SINON
            c3a.addLabelToNextInst(l0);
            node.getSinon().accept(this);
        }

        // Add label for next operation
        c3a.addLabelToNextInst(l1);

        return null;
    }

    @Override
    public C3aOperand visit(SaExpInf node) {
        // if op1 < op2 goto l0
        C3aLabel label1 = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);
        c3a.ajouteInst(new C3aInstAffect(new C3aConstant(1), temp, ""));
        c3a.ajouteInst(new C3aInstJumpIfLess(op1, op2, label1, ""));
        c3a.ajouteInst(new C3aInstAffect(new C3aConstant(0), temp, ""));
        c3a.addLabelToNextInst(label1);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpAnd node) {
        C3aLabel label1 = c3a.newAutoLabel();
        C3aLabel label2 = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        // Les deux IF
        c3a.ajouteInst(new C3aInstJumpIfEqual(op1, new C3aConstant(0), label2, ""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(op2, new C3aConstant(0), label2, ""));

        // affect et goto
        c3a.ajouteInst(new C3aInstAffect(new C3aConstant(1), temp, ""));
        c3a.ajouteInst(new C3aInstJump(label1, ""));

        // affect dans l1
        C3aInstAffect c3aInstAffect = new C3aInstAffect(new C3aConstant(0), temp, "");
        c3aInstAffect.setLabel(label2);
        c3a.ajouteInst(c3aInstAffect);

        // l0
        c3a.addLabelToNextInst(label1);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpOr node) {

        C3aLabel label1 = c3a.newAutoLabel();
        C3aLabel label2 = c3a.newAutoLabel();
        C3aTemp temp = c3a.newTemp();
        C3aOperand op1 = node.getOp1().accept(this);
        C3aOperand op2 = node.getOp2().accept(this);

        // Les deux IF
        c3a.ajouteInst(new C3aInstJumpIfNotEqual(op1, new C3aConstant(0), label2, ""));
        c3a.ajouteInst(new C3aInstJumpIfNotEqual(op2, new C3aConstant(0), label2, ""));

        // affect et goto
        c3a.ajouteInst(new C3aInstAffect(new C3aConstant(0), temp, ""));
        c3a.ajouteInst(new C3aInstJump(label1, ""));

        // affect dans l1
        C3aInstAffect c3aInstAffect = new C3aInstAffect(new C3aConstant(1), temp, "");
        c3aInstAffect.setLabel(label2);
        c3a.ajouteInst(c3aInstAffect);

        // l0
        c3a.addLabelToNextInst(label1);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpAppel node) {
        C3aFunction c3aFunction = (C3aFunction) node.getVal().accept(this);
        C3aTemp temp = c3a.newTemp();
        C3aInstCall c3aInstCall = new C3aInstCall(c3aFunction, temp,"");
        c3a.ajouteInst(c3aInstCall);
        return temp;
    }

    @Override
    public C3aOperand visit(SaExpLire node) {
        C3aInstRead c3aInstRead = new C3aInstRead( node.accept(this), "");
        c3a.ajouteInst(c3aInstRead);
        return null;
    }

    @Override
    public C3aOperand visit(SaLInst node) {
        if(node.getTete() != null) node.getTete().accept(this);
        if(node.getQueue() != null) node.getQueue().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaLDec node) {
        if(node.getTete() != null) node.getTete().accept(this);
        if(node.getQueue() != null) node.getQueue().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaLExp node) {
        C3aOperand c3aOperand = node.getTete().accept(this);
        C3aInstParam c3aInstParam = new C3aInstParam(c3aOperand, "");
        c3a.ajouteInst(c3aInstParam);
        if (node.getQueue() != null) node.getQueue().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstAffect node) {
        C3aOperand result = node.getLhs().accept(this);
        C3aOperand op1 = node.getRhs().accept(this);
        C3aInstAffect affect = new C3aInstAffect(op1, result, "");
        c3a.ajouteInst(affect);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstRetour node) {
        c3a.ajouteInst(new C3aInstReturn(node.getVal().accept(this), ""));
        return null;
    }

    @Override
    public C3aOperand visit(SaDecFonc node) {
        TsItemFct tsItemFct = table.getFct(node.getNom());
        c3a.ajouteInst(new C3aInstFBegin(tsItemFct, "Entree de la fonction"));
        tableLocale = table.getTableLocale(node.getNom());
        super.visit(node);
        tableLocale = null;
        c3a.ajouteInst(new C3aInstFEnd("Fin de la fonction"));
        return null;
    }

    @Override
    public C3aOperand visit(SaVarIndicee node) {
        C3aOperand op = node.getIndice().accept(this);
        return new C3aVar(table.getVar(node.getNom()), op);
    }

    @Override
    public C3aOperand visit(SaVarSimple node) {
        C3aVar var;
        if (table.getVar(node.getNom()) == null) {
            var = new C3aVar(tableLocale.getVar(node.getNom()), null);
        } else {
            var = new C3aVar(table.getVar(node.getNom()), null);
        }
        return var;
    }

    @Override
    public C3aOperand visit(SaAppel node) {
        if (node.getArguments() != null) node.getArguments().accept(this);
        return new C3aFunction(table.getFct(node.getNom()));
    }

    @Override
    public C3aOperand visit(SaInstBloc node){
        node.getVal().accept(this);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpInt node){
        return new C3aConstant(node.getVal());
    }

    @Override
    public C3aOperand visit(SaExpVar node) {
        return node.getVar().accept(this);
    }
}
