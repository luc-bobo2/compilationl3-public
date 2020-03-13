import c3a.*;
import nasm.Nasm;
import ts.Ts;

public class C3a2nasm implements C3aVisitor<Void> {
    public C3a2nasm(C3a c3a, Ts table) {
    }

    public Nasm getNasm() {
        return null;
    }

    @Override
    public Void visit(C3aInstAdd inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstCall inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstFBegin inst) {
        return null;
    }

    @Override
    public Void visit(C3aInst inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstJumpIfLess inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstMult inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstRead inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstSub inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstAffect inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstDiv inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstFEnd inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstJumpIfEqual inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstJumpIfNotEqual inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstJump inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstParam inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstReturn inst) {
        return null;
    }

    @Override
    public Void visit(C3aInstWrite inst) {
        return null;
    }

    @Override
    public Void visit(C3aConstant oper) {
        return null;
    }

    @Override
    public Void visit(C3aLabel oper) {
        return null;
    }

    @Override
    public Void visit(C3aTemp oper) {
        return null;
    }

    @Override
    public Void visit(C3aVar oper) {
        return null;
    }

    @Override
    public Void visit(C3aFunction oper) {
        return null;
    }
}
