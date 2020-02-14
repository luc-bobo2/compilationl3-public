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

    // START
    @Override
    public void caseStart(Start node) {
        apply(node.getPProgramme());
    }

    // PROGRAMME
    @Override
    public void caseAAvecvarProgramme(AAvecvarProgramme node) {
        SaLDec variables = (SaLDec) apply(node.getOptdecvar());
        SaLDec fonctions = (SaLDec) apply(node.getListedecfonc());
        this.returnValue = new SaProg(variables, fonctions);
    }
    @Override
    public void caseASansvarProgramme(ASansvarProgramme node) {
        SaLDec fonctions = (SaLDec) apply(node.getListedecfonc());
        this.returnValue = new SaProg(null, fonctions);
    }

    // LISTE DES VARIABLES DU PROGRAMME
    @Override
    public void caseAOptdecvar(AOptdecvar node) {
        apply(node.getListedecvar());
    }

    // LISTE DE DECLARATION DE VARS
    @Override
    public void caseADecvarldecvarListedecvar(ADecvarldecvarListedecvar node) {
        SaDec tete = (SaDec) apply(node.getDecvar());
        SaLDec queue = (SaLDec) apply(node.getListedecvarbis());
        this.returnValue = new SaLDec(tete, queue);
    }
    @Override
    public void caseADecvarListedecvar(ADecvarListedecvar node) {
        SaDec tete = (SaDec) apply(node.getDecvar());
        this.returnValue = new SaLDec(tete, null);
    }
    @Override
    public void caseADecvarldecvarListedecvarbis(ADecvarldecvarListedecvarbis node) {
        SaDec tete = (SaDec) apply(node.getDecvar());
        SaLDec queue = (SaLDec) apply(node.getListedecvarbis());
        this.returnValue = new SaLDec(tete, queue);
    }
    @Override
    public void caseADecvarListedecvarbis(ADecvarListedecvarbis node) {
        SaDec tete = (SaDec) apply(node.getDecvar());
        this.returnValue = new SaLDec(tete, null);
    }

    // DECLARATION VARIABLE
    @Override
    public void caseADecvarentierDecvar(ADecvarentierDecvar node) {
        this.returnValue = new SaDecVar(node.getId().getText());
    }
    @Override
    public void caseADecvartableauDecvar(ADecvartableauDecvar node) {
        String nom = node.getId().getText();
        int taille = Integer.parseInt(node.getNombre().getText());
        this.returnValue = new SaDecTab(nom, taille);
    }

    // LISTE DE DECLARATION DE FONCTIONS
    @Override
    public void caseALdecfoncrecListedecfonc(ALdecfoncrecListedecfonc node) {
        SaDecFonc tete = (SaDecFonc) apply(node.getDecfonc());
        SaLDec queue = (SaLDec) apply(node.getListedecfonc());
        this.returnValue = new SaLDec(tete, queue);
    }
    @Override
    public void caseALdecfoncfinalListedecfonc(ALdecfoncfinalListedecfonc node) {
        this.returnValue = null;
    }

    // DECLARATION DE FONCTION
    @Override
    public void caseADecvarinstrDecfonc(ADecvarinstrDecfonc node) {
        String nom = node.getId().getText();
        SaLDec parametres = (SaLDec) apply(node.getListeparam());
        SaLDec variables = (SaLDec) apply(node.getOptdecvar());
        SaInstBloc corps = (SaInstBloc) apply(node.getInstrblock());
        this.returnValue = new SaDecFonc(nom, parametres, variables, corps);
    }
    @Override
    public void caseAInstrDecfonc(AInstrDecfonc node) {
        String nom = node.getId().getText();
        SaLDec parametres = (SaLDec) apply(node.getListeparam());
        SaInstBloc corps = (SaInstBloc) apply(node.getInstrblock());
        this.returnValue = new SaDecFonc(nom, parametres, null, corps);
    }

    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        this.returnValue = null;
    }
    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        this.returnValue = apply(node.getListedecvar());
    }

    // GRAMMAIRE DES INSTRUCTIONS
    @Override
    public void caseAAffectInstr(AAffectInstr node) {
        apply(node.getAffect());
    }
    @Override
    public void caseABlockinstrInstr(ABlockinstrInstr node) {
        apply(node.getInstrblock());
    }
    @Override
    public void caseAAppelfctInstr(AAppelfctInstr node) {
        apply(node.getInstrappel());
    }
    @Override
    public void caseAIfInstr(AIfInstr node) {
        apply(node.getSiblock());
    }
    @Override
    public void caseAWhileInstr(AWhileInstr node) {
        apply(node.getWhile());
    }
    @Override
    public void caseAEmptyInstr(AEmptyInstr node) {
        apply(node.getInstrvide());
    }
    @Override
    public void caseAEcrireInstr(AEcrireInstr node) {
        apply(node.getEcriture());
    }
    @Override
    public void caseARetouriInstr(ARetouriInstr node) {
        apply(node.getRetourinstr());
    }

    // LISTE D'INSTRUCTIONS
    @Override
    public void caseALinstrecListeinstr(ALinstrecListeinstr node) {
        SaInst tete = (SaInst) apply(node.getInstr());
        SaLInst queue = (SaLInst) apply(node.getListeinstr());
        this.returnValue = new SaLInst(tete, queue);
    }
    @Override
    public void caseALinstfinalListeinstr(ALinstfinalListeinstr node) {
        this.returnValue = null;
    }

    // INSTRUCTION VIDE
    @Override
    public void caseAInstrvide(AInstrvide node) {
        this.returnValue = null;
    }

    // INSTRUCTION APPEL
    @Override
    public void caseAInstrappel(AInstrappel node) {
        this.returnValue = apply(node.getAppelfct());
    }

    // INSTRUCTION APPEL DE FONCTION
    @Override
    public void caseAParamAppelfct(AParamAppelfct node) {
        String nom = node.getId().getText();
        SaLExp listeExp = (SaLExp) apply(node.getListeexp());
        this.returnValue = new SaAppel(nom, listeExp);
    }
    @Override
    public void caseANoparamAppelfct(ANoparamAppelfct node) {
        String nom = node.getId().getText();
        this.returnValue = new SaAppel(nom, null);
    }

    // INSTRUCTION SINON
    @Override
    public void caseASinonSiblock(ASinonSiblock node) {
        SaExp test = (SaExp) apply(node.getExp());
        SaInstBloc si = (SaInstBloc) apply(node.getInstrblock());
        SaInstBloc sinon = (SaInstBloc) apply(node.getSinonblock());
        this.returnValue = new SaInstSi(test, si, sinon);
    }
    @Override
    public void caseASiSiblock(ASiSiblock node) {
        SaExp test = (SaExp) apply(node.getExp());
        SaInstBloc si = (SaInstBloc) apply(node.getInstrblock());
        this.returnValue = new SaInstSi(test, si, null);
    }
    @Override
    public void caseASinonSinonblock(ASinonSinonblock node) {
        this.returnValue = apply(node.getInstrblock());
    }

    // INSTRUCTION TANT QUE
    @Override
    public void caseAWhileWhile(AWhileWhile node) {
        SaExp test = (SaExp) apply(node.getExp());
        SaInstBloc faire = (SaInstBloc) apply(node.getInstrblock());
        this.returnValue = new SaInstTantQue(test, faire);
    }

    // INSTRUCTION RETOUR
    @Override
    public void caseAReturnRetourinstr(AReturnRetourinstr node) {
        SaExp val = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstRetour(val);
    }

    // INSTRUCTION AFFECTATION
    @Override
    public void caseAAffectAffect(AAffectAffect node) {
        SaVar var = (SaVar) apply(node.getVar());
        SaExp val = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstAffect(var, val);
    }

    // BLOC D'INSTRUCTIONS
    @Override
    public void caseABlockinstrInstrblock(ABlockinstrInstrblock node) {
        SaLInst listeInstr = (SaLInst) apply(node.getListeinstr());
        this.returnValue = new SaInstBloc(listeInstr);
    }

    // INSTRUCTION APPEL ecrire()
    @Override
    public void caseAFctecrireEcriture(AFctecrireEcriture node) {
        SaExp exp = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstEcriture(exp);
    }

    // APPEL VARIABLE
    @Override
    public void caseASimpleVar(ASimpleVar node) {
        String nom = node.getId().getText();
        this.returnValue = new SaVarSimple(nom);
    }
    @Override
    public void caseATabVar(ATabVar node) {
        String nom = node.getId().getText();
        SaExp exp = (SaExp) apply(node.getExp());
        this.returnValue = new SaVarIndicee(nom, exp);
    }

    // Declaration d'une liste d'expressions
    @Override
    public void caseARecursifListeexp(ARecursifListeexp node) {
        node.getExp().apply(this);
        SaExp tete = ((SaExp) this.returnValue);
        node.getBis().apply(this);
        SaLExp queue = ((SaLExp) this.returnValue);

        this.returnValue = new SaLExp(tete, queue);
    }

    @Override
    public void caseASimpleListeexp(ASimpleListeexp node) {
        node.getExp().apply(this);
        SaExp tete = ((SaExp) this.returnValue);
        this.returnValue = new SaLExp(tete, null);
    }

    @Override
    public void caseAEndBis(AEndBis node) {
        node.getExp().apply(this);
        SaExp tete = ((SaExp) this.returnValue);
        this.returnValue = new SaLExp(tete, null);
    }

    @Override
    public void caseARecursifBis(ARecursifBis node) {
        node.getExp().apply(this);
        SaExp tete = ((SaExp) this.returnValue);
        node.getBis().apply(this);
        SaLExp queue = ((SaLExp) this.returnValue);

        this.returnValue = new SaLExp(tete, queue);
    }

    // GRAMMAIRE DES EXPRESSIONS ARITHMETIQUES
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
    public void caseANumberE0(ANumberE0 node) {
        this.returnValue = new SaExpInt(Integer.parseInt(node.getNombre().getText()));
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