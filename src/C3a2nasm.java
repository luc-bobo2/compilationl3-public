import c3a.*;
import nasm.*;
import ts.Ts;

public class C3a2nasm implements C3aVisitor<NasmOperand> {
    private Nasm nasm;

    public C3a2nasm(C3a c3a, Ts table) {
        nasm = new Nasm(table);
    }

    public Nasm getNasm() {
        return this.nasm;
    }

    private NasmLabel getLabel(C3aInst inst) {
        return (inst.label != null) ? (NasmLabel)inst.label.accept(this) : null;
    }

    @Override
    public NasmOperand visit(C3aInstAdd inst) {
        // Récupération du label
        NasmOperand label = getLabel(inst);
        
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label, eax, op1, ""));
        nasm.ajouteInst(new NasmAdd(null, op1, op2, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstCall inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand address = inst.op1.accept(this);

        NasmRegister esp = nasm.newRegister();
        esp.colorRegister(Nasm.REG_ESP);
        nasm.ajouteInst(new NasmSub(label, esp, new NasmConstant(4), "allocation mémoire pour la valeur de retour"));
        nasm.ajouteInst(new NasmCall(label, address, ""));

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);
        nasm.ajouteInst(new NasmPop(null, eax, "récupération de la valeur de retour"));

        if (inst.result != null) {
            NasmOperand dest = inst.result.accept(this);
            nasm.ajouteInst(new NasmMov(null, dest, eax, ""));
        }
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFBegin inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInst inst) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstMult inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op1.accept(this);
        NasmOperand dest = inst.result.accept(this);

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label ,eax, op1, ""));
        nasm.ajouteInst(new NasmMul(null, eax, op2 ,""));
        nasm.ajouteInst(new NasmMov(null, dest, eax ,""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstSub inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op1.accept(this);
        NasmOperand dest = inst.result.accept(this);

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label ,eax, op1, ""));
        nasm.ajouteInst(new NasmSub(null, eax, op2 ,""));
        nasm.ajouteInst(new NasmMov(null, dest, eax ,""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstDiv inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);
        NasmRegister ebx = nasm.newRegister();
        ebx.colorRegister(Nasm.REG_EBX);


        nasm.ajouteInst(new NasmMov(label, eax, op1, ""));
        nasm.ajouteInst(new NasmMov(null, ebx, op2, ""));
        nasm.ajouteInst(new NasmDiv(null, ebx, ""));
        //TODO result
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstAffect inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand src = inst.op1.accept(this);
        NasmOperand dest = inst.result.accept(this);

        nasm.ajouteInst(new NasmMov(label, dest, src, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFEnd inst) {
        NasmLabel label = getLabel(inst);
        //TODO
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJump inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand address = inst.result.accept(this);
        nasm.ajouteInst(new NasmJmp(label, address, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfEqual inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand address = inst.result.accept(this);
        nasm.ajouteInst(new NasmJe(label, address, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfNotEqual inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand address = inst.result.accept(this);
        nasm.ajouteInst(new NasmJne(label, address, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfLess inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand address = inst.result.accept(this);

        nasm.ajouteInst(new NasmJl(label, address, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstParam inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand src = inst.op1.accept(this);

        nasm.ajouteInst(new NasmPush(label, src, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstReturn inst) {
        NasmLabel label = getLabel(inst);

        //TODO
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstWrite inst) {
        NasmLabel label = getLabel(inst);

        NasmOperand src = inst.op1.accept(this);
        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label, eax, src,""));
        nasm.ajouteInst(new NasmCall(null, new NasmLabel("iprintlf"), ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstRead inst) {
        NasmLabel label = getLabel(inst);
        //TODO
        return null;
    }

    @Override
    public NasmOperand visit(C3aConstant oper) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aLabel oper) {
        return new NasmLabel(oper.toString());
    }

    @Override
    public NasmOperand visit(C3aTemp oper) {
        return new NasmRegister(oper.num);
    }

    @Override
    public NasmOperand visit(C3aVar oper) {
        return null;
    }

    @Override
    public NasmOperand visit(C3aFunction oper) {
        return null;
    }
}
