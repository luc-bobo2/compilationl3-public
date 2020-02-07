import sa.*;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {
    private SaNode returnValue;

    /**
     * FONCTIONNEL NE PAS SUPPRIMER JUSQUA PLUS FONCTIONNEL
     */
    public SaNode getRoot() {
        return this.returnValue;
    }

    private SaNode apply(Switchable sc) {
        sc.apply(this);
        return this.returnValue;
    }

    @Override
    public void caseASimpleDeclvar(ASimpleDeclvar node) {
        int val = Integer.parseInt(node.getId().getText());
        this.returnValue = new SaExpInt(val);
    }

    @Override
    public void caseAFctecrireEcriture(AFctecrireEcriture node) {
        SaExp exp = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstEcriture(exp);
    }

    /*
        PLUS FONCTIONNEL
     */

    @Override
    public void caseStart(Start node) {
        apply(node.getPProgramme());
    }

    @Override
    public void caseAAvecvarProgramme(AAvecvarProgramme node) {
        super.caseAAvecvarProgramme(node);
    }

    @Override
    public void caseASansvarProgramme(ASansvarProgramme node) {
        super.caseASansvarProgramme(node);
    }

    @Override
    public void caseAOptdecvar(AOptdecvar node) {
        super.caseAOptdecvar(node);
    }

    @Override
    public void caseADecvarldecvarListedecvar(ADecvarldecvarListedecvar node) {
        super.caseADecvarldecvarListedecvar(node);
    }

    @Override
    public void caseADecvarListedecvar(ADecvarListedecvar node) {
        super.caseADecvarListedecvar(node);
    }

    @Override
    public void caseADecvarldecvarListedecvarbis(ADecvarldecvarListedecvarbis node) {
        super.caseADecvarldecvarListedecvarbis(node);
    }

    @Override
    public void caseADecvarListedecvarbis(ADecvarListedecvarbis node) {
        super.caseADecvarListedecvarbis(node);
    }

    @Override
    public void caseADecvarentierDecvar(ADecvarentierDecvar node) {
        super.caseADecvarentierDecvar(node);
    }

    @Override
    public void caseADecvartableauDecvar(ADecvartableauDecvar node) {
        super.caseADecvartableauDecvar(node);
    }

    @Override
    public void caseALdecfoncrecListedecfonc(ALdecfoncrecListedecfonc node) {
        super.caseALdecfoncrecListedecfonc(node);
    }

    @Override
    public void caseALdecfoncfinalListedecfonc(ALdecfoncfinalListedecfonc node) {
        super.caseALdecfoncfinalListedecfonc(node);
    }

    @Override
    public void caseADecvarinstrDecfonc(ADecvarinstrDecfonc node) {
        super.caseADecvarinstrDecfonc(node);
    }

    @Override
    public void caseAInstrDecfonc(AInstrDecfonc node) {
        super.caseAInstrDecfonc(node);
    }

    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        super.caseASansparamListeparam(node);
    }

    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        super.caseAAvecparamListeparam(node);
    }

    @Override
    public void caseAAffectInstr(AAffectInstr node) {
        super.caseAAffectInstr(node);
    }

    @Override
    public void caseABlockinstrInstr(ABlockinstrInstr node) {
        super.caseABlockinstrInstr(node);
    }

    @Override
    public void caseAAppelfctInstr(AAppelfctInstr node) {
        super.caseAAppelfctInstr(node);
    }

    @Override
    public void caseAIfInstr(AIfInstr node) {
        super.caseAIfInstr(node);
    }

    @Override
    public void caseAWhileInstr(AWhileInstr node) {
        super.caseAWhileInstr(node);
    }

    @Override
    public void caseAEmptyInstr(AEmptyInstr node) {
        super.caseAEmptyInstr(node);
    }

    @Override
    public void caseAEcrireInstr(AEcrireInstr node) {
        super.caseAEcrireInstr(node);
    }

    @Override
    public void caseARetouriInstr(ARetouriInstr node) {
        super.caseARetouriInstr(node);
    }

    @Override
    public void caseALinstrecListeinstr(ALinstrecListeinstr node) {
        super.caseALinstrecListeinstr(node);
    }

    @Override
    public void caseALinstfinalListeinstr(ALinstfinalListeinstr node) {
        super.caseALinstfinalListeinstr(node);
    }

    @Override
    public void caseAInstrvide(AInstrvide node) {
        super.caseAInstrvide(node);
    }

    @Override
    public void caseAInstrappel(AInstrappel node) {
        super.caseAInstrappel(node);
    }

    @Override
    public void caseAParamAppelfct(AParamAppelfct node) {
        super.caseAParamAppelfct(node);
    }

    @Override
    public void caseANoparamAppelfct(ANoparamAppelfct node) {
        super.caseANoparamAppelfct(node);
    }

    @Override
    public void caseASinonSiblock(ASinonSiblock node) {
        super.caseASinonSiblock(node);
    }

    @Override
    public void caseASiSiblock(ASiSiblock node) {
        super.caseASiSiblock(node);
    }

    @Override
    public void caseASinonSinonblock(ASinonSinonblock node) {
        super.caseASinonSinonblock(node);
    }

    @Override
    public void caseAWhileWhile(AWhileWhile node) {
        super.caseAWhileWhile(node);
    }

    @Override
    public void caseAReturnRetourinstr(AReturnRetourinstr node) {
        super.caseAReturnRetourinstr(node);
    }

    @Override
    public void caseAAffectAffect(AAffectAffect node) {
        super.caseAAffectAffect(node);
    }

    @Override
    public void caseABlockinstrInstrblock(ABlockinstrInstrblock node) {
        super.caseABlockinstrInstrblock(node);
    }



    @Override
    public void caseATabVar(ATabVar node) {
        super.caseATabVar(node);
    }

    @Override
    public void caseASimpleVar(ASimpleVar node) {
        super.caseASimpleVar(node);
    }

    @Override
    public void caseATabDeclvar(ATabDeclvar node) {
        super.caseATabDeclvar(node);
    }



    @Override
    public void caseARecursifListeexp(ARecursifListeexp node) {
        super.caseARecursifListeexp(node);
    }

    @Override
    public void caseASimpleListeexp(ASimpleListeexp node) {
        super.caseASimpleListeexp(node);
    }

    @Override
    public void caseAEndBis(AEndBis node) {
        super.caseAEndBis(node);
    }

    @Override
    public void caseARecursifBis(ARecursifBis node) {
        super.caseARecursifBis(node);
    }

    @Override
    public void caseAOuExp(AOuExp node) {
        SaExp op1;
        SaExp op2;
        node.getE6().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getExp().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpOr(op1, op2);
    }

    @Override
    public void caseAE6Exp(AE6Exp node) {
        node.getE6().apply(this);
    }

    @Override
    public void caseAEtE6(AEtE6 node) {
        SaExp op1;
        SaExp op2;
        node.getE5().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE6().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpAnd(op1, op2);
    }

    @Override
    public void caseAE5E6(AE5E6 node) {
        node.getE5().apply(this);
    }

    @Override
    public void caseAEgalE5(AEgalE5 node) {
        SaExp op1;
        SaExp op2;
        node.getE4().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE5().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpEqual(op1, op2);
    }

    @Override
    public void caseAInfE5(AInfE5 node) {
        SaExp op1;
        SaExp op2;
        node.getE4().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE5().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpInf(op1, op2);
    }

    @Override
    public void caseAE4E5(AE4E5 node) {
        node.getE4().apply(this);
    }

    @Override
    public void caseAPlusE4(APlusE4 node) {
        SaExp op1;
        SaExp op2;
        node.getE3().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE4().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpAdd(op1, op2);
    }

    @Override
    public void caseAMoinsE4(AMoinsE4 node) {
        SaExp op1;
        SaExp op2;
        node.getE3().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE4().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpSub(op1, op2);
    }

    @Override
    public void caseAE3E4(AE3E4 node) {
        node.getE3().apply(this);
    }

    @Override
    public void caseAFoisE3(AFoisE3 node) {
        SaExp op1;
        SaExp op2;
        node.getE2().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE3().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpMult(op1, op2);
    }

    @Override
    public void caseADiviserE3(ADiviserE3 node) {
        SaExp op1;
        SaExp op2;
        node.getE2().apply(this);
        op1 = (SaExp)this.returnValue;
        node.getE3().apply(this);
        op2 = (SaExp)this.returnValue;
        this.returnValue = new SaExpDiv(op1, op2);
    }

    @Override
    public void caseAE2E3(AE2E3 node) {
        node.getE2().apply(this);
    }

    @Override
    public void caseAExclE2(AExclE2 node) {
        SaExp op1;
        node.getE1().apply(this);
        op1 = (SaExp)this.returnValue;
        this.returnValue = new SaExpNot(op1);
    }

    @Override
    public void caseAE1E2(AE1E2 node) {
        node.getE1().apply(this);
    }

    @Override
    public void caseAParentheseE1(AParentheseE1 node) {
        node.getExp().apply(this);
    }

    @Override
    public void caseAE0E1(AE0E1 node) {
        node.getE0().apply(this);
    }

    @Override
    public void caseAAppelfctE0(AAppelfctE0 node) {
        SaAppel op1;
        node.getAppelfct().apply(this);
        op1 = (SaAppel) this.returnValue;
        this.returnValue = new SaExpAppel(op1);
    }

    @Override
    public void caseALireE0(ALireE0 node) {
        this.returnValue = new SaExpLire();
    }

    @Override
    public void caseAVariableE0(AVariableE0 node) {
        SaVar var1;
        node.getVar().apply(this);
        var1 = (SaVar)this.returnValue;
        this.returnValue = new SaExpVar(var1);
    }
}