import sa.*;
import ts.Ts;
import ts.TsItemVar;

public class Sa2ts extends SaDepthFirstVisitor <Void> {
    private Ts tableGlobale;

    public Sa2ts (SaNode saRoot) {
        this.tableGlobale = new Ts();
        saRoot.accept(this);
    }

    public Ts getTableGlobale() {
        return this.tableGlobale;
    }

    @Override
    public Void visit(SaDecVar node) {
        System.out.println("hey from visit(SaDecVar node);");
        defaultIn(node);
        this.tableGlobale.addVar(node.getNom(), 1);
        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaDecTab node) {
        System.out.println("hey from visit(SaDecTab node);");
        defaultIn(node);
        this.tableGlobale.addVar(node.getNom(), node.getTaille());
        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaDecFonc node) {
        try {
            System.out.println("hey from visit(SaDecFonc node);");
            defaultIn(node);

            Ts tableLocale = new Ts();

            // Adding the function params
            SaDec param;
            SaLDec paramsList = node.getParametres();
            if (paramsList != null && paramsList.length() > 0) {
                do {
                    param = paramsList.getTete();
                    if (param != null) tableLocale.addParam(param.getNom());
                } while ((paramsList = paramsList.getQueue()) != null);
            }

            // Adding variable to the local table
            if (node.getVariable() != null) {
                Ts tableVariable = new Sa2ts(node.getVariable()).getTableGlobale();
                for(TsItemVar var : tableVariable.variables.values()) {
                    tableLocale.addVar(var.identif, var.taille);
                }
            }

            int nbArgs = node.getParametres() == null ? 0 : node.getParametres().length();
            this.tableGlobale.addFct(node.getNom(), nbArgs, tableLocale, node);
            defaultOut(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Void visit(SaVarSimple node) {
        System.out.println("hey from visit(SaVarSimple node);");
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaVarIndicee node) {
        System.out.println("hey from visit(SaVarIndicee node);");
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaAppel node) {
        System.out.println("hey from visit(SaAppel node);");
        defaultIn(node);
        defaultOut(node);
        return null;
    }
}