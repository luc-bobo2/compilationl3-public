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
        super.caseAOuExp(node);
    }

    @Override
    public void caseAE6Exp(AE6Exp node) {
        super.caseAE6Exp(node);
    }

    @Override
    public void caseAEtE6(AEtE6 node) {
        super.caseAEtE6(node);
    }

    @Override
    public void caseAE5E6(AE5E6 node) {
        super.caseAE5E6(node);
    }

    @Override
    public void caseAEgalE5(AEgalE5 node) {
        super.caseAEgalE5(node);
    }

    @Override
    public void caseAInfE5(AInfE5 node) {
        super.caseAInfE5(node);
    }

    @Override
    public void caseAE4E5(AE4E5 node) {
        super.caseAE4E5(node);
    }

    @Override
    public void caseAPlusE4(APlusE4 node) {
        super.caseAPlusE4(node);
    }

    @Override
    public void caseAMoinsE4(AMoinsE4 node) {
        super.caseAMoinsE4(node);
    }

    @Override
    public void caseAE3E4(AE3E4 node) {
        super.caseAE3E4(node);
    }

    @Override
    public void caseAFoisE3(AFoisE3 node) {
        super.caseAFoisE3(node);
    }

    @Override
    public void caseADiviserE3(ADiviserE3 node) {
        super.caseADiviserE3(node);
    }

    @Override
    public void caseAE2E3(AE2E3 node) {
        super.caseAE2E3(node);
    }

    @Override
    public void caseAExclE2(AExclE2 node) {
        super.caseAExclE2(node);
    }

    @Override
    public void caseAE1E2(AE1E2 node) {
        super.caseAE1E2(node);
    }

    @Override
    public void caseAParentheseE1(AParentheseE1 node) {
        super.caseAParentheseE1(node);
    }

    @Override
    public void caseAE0E1(AE0E1 node) {
        super.caseAE0E1(node);
    }

    @Override
    public void caseANumberE0(ANumberE0 node) {
        super.caseANumberE0(node);
    }

    @Override
    public void caseAAppelfctE0(AAppelfctE0 node) {
        super.caseAAppelfctE0(node);
    }

    @Override
    public void caseALireE0(ALireE0 node) {
        super.caseALireE0(node);
    }

    @Override
    public void caseAVariableE0(AVariableE0 node) {
        super.caseAVariableE0(node);
    }
}