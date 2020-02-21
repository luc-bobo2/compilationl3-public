import sa.*;
import ts.Ts;
import ts.TsItemFct;
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

    private boolean varExist(String nom) {
        final TsItemVar local = this.tableLocale.getVar(nom);
        final TsItemVar global = this.tableGlobale.getVar(nom);

        // Case where we have a local var matching
        if (local != null) {
            return local.taille == 1;
        } else {
            // Case where we don't have a local var matching but a global one is matching
            if (global != null) {
                return global.taille == 1;
            }
        }

        return false;
    }

    private boolean tabExist(String nom) {
        final TsItemVar local = this.tableLocale.getVar(nom);
        final TsItemVar global = this.tableGlobale.getVar(nom);

        // Case where we have a local var matching
        if (local != null) {
            return local.taille > 1;
        } else {
            // Case where we don't have a local var matching but a global one is matching
            if (global != null) {
                return global.taille > 1;
            }
        }

        return false;
    }

    private boolean funcExist(String nom) {
        return this.tableGlobale.getFct(nom) != null;
    }

    private void _assert(boolean value, CompilerError errorToThrow) {
        if (!value) throw new CompilerException(errorToThrow);
    }

    @Override
    public Void visit(SaDecVar node) {
        defaultIn(node);

        // Assert var don't exist then add it to the table
        final String nom = node.getNom();
        _assert(!varExist(nom), CompilerError.VAR_ALREADY_DECLARED);
        this.tableGlobale.addVar(nom, 1);

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaDecTab node) {
        defaultIn(node);

        final String nom = node.getNom();
        _assert(!tabExist(nom), CompilerError.TAB_ALREADY_DECLARED);
        this.tableGlobale.addVar(nom, node.getTaille());

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaDecFonc node) {
        defaultIn(node);

        // assert func don't exist
        _assert(!funcExist(node.getNom()), CompilerError.FUNCTION_ALREADY_DECLARED);

        // Init local table
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

        _assert(varExist(node.getNom()), CompilerError.CALL_TO_UNDEFINED_VAR);

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaVarIndicee node) {
        defaultIn(node);

        _assert(tabExist(node.getNom()), CompilerError.CALL_TO_UNDEFINED_TAB);

        defaultOut(node);
        return null;
    }

    @Override
    public Void visit(SaAppel node) {
        defaultIn(node);

        _assert(funcExist(node.getNom()), CompilerError.CALL_TO_UNDEFINED_FUNCTION);

        defaultOut(node);
        return null;
    }
}
