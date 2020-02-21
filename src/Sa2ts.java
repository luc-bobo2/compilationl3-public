import sa.*;
import ts.Ts;
import ts.TsItemVar;

public class Sa2ts extends SaDepthFirstVisitor <Void> {
    private int depth;
    private Ts tableLocale;
    private Ts tableGlobale;

    public Sa2ts (SaNode saRoot) {
        this.depth = 0;
        this.tableLocale = new Ts();
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

        // Init la table locale
        this.tableLocale = new Ts();
        // Store the number of parameters, default to 0
        int nbParams = 0;

        // Adding parameters to the local table
        if (node.getParametres() != null) {
            // Update the parameters count
            nbParams = node.getParametres().length();
            Ts parametersTable = new Sa2ts(node.getParametres()).tableGlobale;
            for (TsItemVar param : parametersTable.variables.values()) {
                this.tableLocale.addParam(param.identif);
            }
        }

        // Adding variables to the local table
        if (node.getVariable() != null) {
            Ts variablesTable = new Sa2ts(node.getVariable()).tableGlobale;
            for (TsItemVar var : variablesTable.variables.values()) {
                this.tableLocale.addVar(var.identif, var.taille);
            }
        }

        // Visit the function body
        node.getCorps().accept(this);

        // Add the function to the table
        this.tableGlobale.addFct(node.getNom(), nbParams, this.tableLocale, node);

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaVarSimple node) {
        defaultIn(node);

        String nom = node.getNom();
        if (this.tableLocale.getVar(nom) == null && this.tableGlobale.getVar(nom) == null) {
            throw new CompilerException(CompilerError.CALL_TO_UNDEFINED_VAR);
        }

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaVarIndicee node) {
        defaultIn(node);

        String nom = node.getNom();
        if (this.tableLocale.getVar(nom) == null && this.tableGlobale.getVar(nom) == null) {
            throw new CompilerException(CompilerError.CALL_TO_UNDEFINED_VAR_INDICEE);
        }

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaAppel node) {
        defaultIn(node);

        String nom = node.getNom();
        if (this.tableGlobale.getFct(nom) == null) {
            throw new CompilerException(CompilerError.CALL_TO_UNDEFINED_FUNCTION);
        }

        defaultOut(node);
        return null;
    }
}
