import sa.*;
import ts.Ts;
import ts.TsItemVar;

public class Sa2ts extends SaDepthFirstVisitor <Void> {
    private int depth;
    private Ts tableGlobale;

    public Sa2ts (SaNode saRoot) {
        this.depth = 0;
        this.tableGlobale = new Ts();
        saRoot.accept(this);
    }

    public Ts getTableGlobale() {
        return this.tableGlobale;
    }

    @Override
    public void defaultIn(SaNode node) {
        System.out.printf("Visiting | depth: %03d | node type: %s.%n", depth, node.getClass().getCanonicalName());
        this.depth += 1;
    }

    @Override
    public void defaultOut(SaNode node) {
        System.out.printf("Exiting  | depth: %03d | node type: %s.%n", depth, node.getClass().getCanonicalName());
        this.depth -= 1;
    }

    @Override
    public Void visit(SaDecVar node) {
        defaultIn(node);
        this.tableGlobale.addVar(node.getNom(), 1);
        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaDecTab node) {
        defaultIn(node);
        this.tableGlobale.addVar(node.getNom(), node.getTaille());
        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaDecFonc node) {
        defaultIn(node);

        // Save the global table and replace it with a new table local to the function
        Ts saveTable = this.tableGlobale;
        this.tableGlobale = new Ts();

        // Store the number of parameters, default to 0
        int nbParams = 0;

        // Adding parameters to the local table
        if (node.getParametres() != null) {
            // Update the parameters count
            nbParams = node.getParametres().length();
            Ts parametersTable = new Sa2ts(node.getParametres()).tableGlobale;
            for(TsItemVar var : parametersTable.variables.values()) {
                this.tableGlobale.addParam(var.identif);
            }
        }

        // Adding variable to the local table
        if (node.getVariable() != null) {
            node.getVariable().accept(this);
        }

        // Visit the function body
        node.getCorps().accept(this);

        // Restore the global table
        Ts tableLocale = this.tableGlobale;
        this.tableGlobale = saveTable;

        // Add the function to the table
        this.tableGlobale.addFct(node.getNom(), nbParams, tableLocale, node);

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaVarSimple node) {
        defaultIn(node);
        return null;
    }

    @Override
    public Void visit(SaVarIndicee node) {
        defaultIn(node);
        return null;
    }

    @Override
    public Void visit(SaAppel node) {
        defaultIn(node);
        defaultOut(node);
        return null;
    }
}
