import sa.*;
import ts.Ts;

public class Sa2ts extends SaDepthFirstVisitor <Void> {
    private SaNode root;
    private Ts tableGlobale;

    public Sa2ts (SaNode saRoot) {
        this.root = saRoot;
        this.tableGlobale = null;
    }

    public SaNode getRoot() {
        return root;
    }

    public void setRoot(SaNode root) {
        if (this.root == root) return;
        this.root = root;
        this.tableGlobale = null;
    }

    public Ts getTableGlobale() {
        // Create the table only if it haven't been created (compute heavy)
        if (this.tableGlobale == null) {
            this.tableGlobale = new Ts();
            System.out.println(this.root);
            this.root.accept(this);
        }

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

            SaDec var;
            SaLDec varList = node.getVariable();
            if (varList != null) {

                while (true) {
                    var = varList.getTete();
                    if (var != null) {
                        if (var instanceof SaDecVar)
                            tableLocale.addVar(var.getNom(), 1);
                        else
                            tableLocale.addVar(var.getNom(), ((SaDecTab) var).getTaille());
                    }

                    varList = varList.getQueue();
                    if (varList == null) break;
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
