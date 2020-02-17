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

            // Declare the local table
            Ts tableLocale = new Ts();
            // Store the number of parameters, default to 0
            int nbParams = 0;

            // Adding parameters to the local table
            if (node.getParametres() != null) {
                // Update the parameters count
                nbParams = node.getParametres().length();
                Ts parametersTable = new Sa2ts(node.getParametres()).tableGlobale;
                for(TsItemVar var : parametersTable.variables.values()) {
                    tableLocale.addParam(var.identif);
                }
            }

            // Adding variable to the local table
            if (node.getVariable() != null) {
                Ts tableVariable = new Sa2ts(node.getVariable()).tableGlobale;
                for(TsItemVar var : tableVariable.variables.values()) {
                    tableLocale.addVar(var.identif, var.taille);
                }
            }

            // Finally add the function to the table
            this.tableGlobale.addFct(node.getNom(), nbParams, tableLocale, node);
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
