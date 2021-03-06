import sa.*;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {
    private SaNode returnValue;

    public SaNode getRoot() {
        return this.returnValue;
    }

    /**
     * Wrapper to explore a node.
     * Allow to save a bunch of code and time.
     *
     * @param sc The node to explore
     * @return The "returnValue" content
     */
    private SaNode apply(Switchable sc) {
        sc.apply(this);
        return this.returnValue;
    }

    // START
    @Override
    public void caseStart(Start node) {
        apply(node.getPProgramme());
    }

    // Définition du programme
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

    // Liste des var globale du programme
    @Override
    public void caseAOptdecvar(AOptdecvar node) {
        apply(node.getListedecvar());
    }

    // Liste de declarations de vars
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

    // Declaration d'une variable
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

    // Liste de declarations de fonctions
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

    // Declaration d'une fonction
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

    // Liste des paramètre de la déclaration d'une fonction
    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        this.returnValue = null;
    }
    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        this.returnValue = apply(node.getListedecvar());
    }


    // Les différentes instructions possibles
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

    // Liste d'instructions
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

    // Instruction vide
    @Override
    public void caseAInstrvide(AInstrvide node) {
        this.returnValue = null;
    }

    // Instruction d'appel de fonction
    @Override
    public void caseAInstrappel(AInstrappel node) {
        this.returnValue = apply(node.getAppelfct());
    }

    // Appel d'une  fonction
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

    // Instruction SI ALORS SINON
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

    // Instruction TANT QUE
    @Override
    public void caseAWhileWhile(AWhileWhile node) {
        SaExp test = (SaExp) apply(node.getExp());
        SaInstBloc faire = (SaInstBloc) apply(node.getInstrblock());
        this.returnValue = new SaInstTantQue(test, faire);
    }

    // Instruction RETOUR
    @Override
    public void caseAReturnRetourinstr(AReturnRetourinstr node) {
        SaExp val = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstRetour(val);
    }

    // Instruction d'affectation
    @Override
    public void caseAAffectAffect(AAffectAffect node) {
        SaVar var = (SaVar) apply(node.getVar());
        SaExp val = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstAffect(var, val);
    }

    // Bloc d'instructions
    @Override
    public void caseABlockinstrInstrblock(ABlockinstrInstrblock node) {
        SaLInst listeInstr = (SaLInst) apply(node.getListeinstr());
        this.returnValue = new SaInstBloc(listeInstr);
    }

    // Instruction d'appel à ECRIRE
    @Override
    public void caseAFctecrireEcriture(AFctecrireEcriture node) {
        SaExp exp = (SaExp) apply(node.getExp());
        this.returnValue = new SaInstEcriture(exp);
    }


    // GRAMMAIRE DES DECLARATIONS DE VARIABLES ET FONCTIONS
    // Appel d'une variable
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
        SaExp tete = (SaExp) apply(node.getExp());
        SaLExp queue = (SaLExp) apply(node.getBis());

        this.returnValue = new SaLExp(tete, queue);
    }
    @Override
    public void caseASimpleListeexp(ASimpleListeexp node) {
        SaExp tete = (SaExp) apply(node.getExp());
        this.returnValue = new SaLExp(tete, null);
    }
    @Override
    public void caseAEndBis(AEndBis node) {
        SaExp tete = (SaExp) apply(node.getExp());
        this.returnValue = new SaLExp(tete, null);
    }
    @Override
    public void caseARecursifBis(ARecursifBis node) {
        SaExp tete = (SaExp) apply(node.getExp());
        SaLExp queue = (SaLExp) apply(node.getBis());

        this.returnValue = new SaLExp(tete, queue);
    }


    // Expressions arithmétiques
    @Override
    public void caseAOuExp(AOuExp node) {
        SaExp op1 = (SaExp) apply(node.getExp());
        SaExp op2 = (SaExp) apply(node.getE6());

        this.returnValue = new SaExpOr(op1, op2);
    }
    @Override
    public void caseAE6Exp(AE6Exp node) {
        this.returnValue = apply(node.getE6());
    }

    @Override
    public void caseAEtE6(AEtE6 node) {
        SaExp op1 = (SaExp) apply(node.getE6());
        SaExp op2 = (SaExp) apply(node.getE5());

        this.returnValue = new SaExpAnd(op1, op2);
    }
    @Override
    public void caseAE5E6(AE5E6 node) {
        this.returnValue = apply(node.getE5());
    }

    @Override
    public void caseAEgalE5(AEgalE5 node) {
        SaExp op1 = (SaExp) apply(node.getE5());
        SaExp op2 = (SaExp) apply(node.getE4());

        this.returnValue = new SaExpEqual(op1, op2);
    }
    @Override
    public void caseAInfE5(AInfE5 node) {
        SaExp op1 = (SaExp) apply(node.getE5());
        SaExp op2 = (SaExp) apply(node.getE4());

        this.returnValue = new SaExpInf(op1, op2);
    }
    @Override
    public void caseAE4E5(AE4E5 node) {
        this.returnValue = apply(node.getE4());
    }

    @Override
    public void caseAPlusE4(APlusE4 node) {
        SaExp op1 = (SaExp) apply(node.getE4());
        SaExp op2 = (SaExp) apply(node.getE3());

        this.returnValue = new SaExpAdd(op1, op2);
    }
    @Override
    public void caseAMoinsE4(AMoinsE4 node) {
        SaExp op1 = (SaExp) apply(node.getE4());
        SaExp op2 = (SaExp) apply(node.getE3());

        this.returnValue = new SaExpSub(op1, op2);
    }
    @Override
    public void caseAE3E4(AE3E4 node) {
        this.returnValue = apply(node.getE3());
    }

    @Override
    public void caseAFoisE3(AFoisE3 node) {
        SaExp op1 = (SaExp) apply(node.getE3());
        SaExp op2 = (SaExp) apply(node.getE2());

        this.returnValue = new SaExpMult(op1, op2);
    }
    @Override
    public void caseADiviserE3(ADiviserE3 node) {
        SaExp op1 = (SaExp) apply(node.getE3());
        SaExp op2 = (SaExp) apply(node.getE2());

        this.returnValue = new SaExpDiv(op1, op2);
    }
    @Override
    public void caseAE2E3(AE2E3 node) {
        this.returnValue = apply(node.getE2());
    }

    @Override
    public void caseAExclE2(AExclE2 node) {
        SaExp op = (SaExp) apply(node.getE1());
        this.returnValue = new SaExpNot(op);
    }
    @Override
    public void caseAE1E2(AE1E2 node) {
        this.returnValue = apply(node.getE1());
    }

    @Override
    public void caseAParentheseE1(AParentheseE1 node) {
        this.returnValue = apply(node.getExp());
    }
    @Override
    public void caseAE0E1(AE0E1 node) {
        this.returnValue = apply(node.getE0());
    }

    @Override
    public void caseANumberE0(ANumberE0 node) {
        this.returnValue = new SaExpInt(Integer.parseInt(node.getNombre().getText()));
    }
    @Override
    public void caseAAppelfctE0(AAppelfctE0 node) {
        SaAppel appel = (SaAppel) apply(node.getAppelfct());

        this.returnValue = new SaExpAppel(appel);
    }
    @Override
    public void caseALireE0(ALireE0 node) {
        this.returnValue = new SaExpLire();
    }
    @Override
    public void caseAVariableE0(AVariableE0 node) {
        SaVar var = (SaVar) apply(node.getVar());

        this.returnValue = new SaExpVar(var);
    }
}