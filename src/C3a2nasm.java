import c3a.*;
import nasm.*;
import ts.Ts;
import ts.TsItemFct;

public class C3a2nasm implements C3aVisitor<NasmOperand> {
    private C3a c3a;
    private Nasm nasm;
    private Ts tableGlobale;
    private TsItemFct currentFct = null;

    public C3a2nasm(C3a c3a, Ts table) {
        this.c3a = c3a;
        this.nasm = new Nasm(table);
        this.tableGlobale = table;

        init();
        for (C3aInst inst : c3a.listeInst) {
            inst.accept(this);
        }
    }

    private void init() {
        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);
        NasmRegister ebx = nasm.newRegister();
        ebx.colorRegister(Nasm.REG_EBX);
        nasm.setTempCounter(0);

        nasm.ajouteInst(new NasmCall(null, new NasmLabel("main"), ""));
        nasm.ajouteInst(new NasmMov(null, ebx, new NasmConstant(0), " valeur de retour du programme"));
        nasm.ajouteInst(new NasmMov(null, eax, new NasmConstant(1), ""));
        nasm.ajouteInst(new NasmInt(null, ""));
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
        NasmOperand dest = inst.result.accept(this);

        nasm.ajouteInst(new NasmMov(label, dest, op1, ""));
        nasm.ajouteInst(new NasmAdd(null, dest, op2, ""));
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

        if (inst.result != null) {
            NasmOperand dest = inst.result.accept(this);
            nasm.ajouteInst(new NasmPop(null, dest, "récupération de la valeur de retour"));
        }

        final int nbArgs = tableGlobale.getFct(inst.op1.val.identif).nbArgs;
        if (nbArgs > 0) {
            nasm.ajouteInst(new NasmAdd(null, esp, new NasmConstant(4*nbArgs), "désallocation des arguments"));
        }
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstFBegin inst) {
        NasmLabel label = new NasmLabel(inst.val.identif);
        NasmRegister esp = nasm.newRegister();
        esp.colorRegister(Nasm.REG_ESP);
        NasmRegister ebp = nasm.newRegister();
        ebp.colorRegister(Nasm.REG_EBP);

        /*if (!inst.val.identif.equals("main"))
            nasm.setTempCounter(0);*/

        // Set the current function
        this.currentFct = inst.val;

        NasmConstant localVarSize = new NasmConstant(4 * this.currentFct.getTable().nbVar());
        nasm.ajouteInst(new NasmPush(label, ebp,"sauvegarde la valeur de ebp"));
        nasm.ajouteInst(new NasmMov(null, ebp, esp, "nouvelle valeur de ebp"));
        nasm.ajouteInst(new NasmSub(null, esp, localVarSize, "allocation des variables locales"));
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
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        nasm.ajouteInst(new NasmMov(label , dest, op1, ""));
        nasm.ajouteInst(new NasmMul(null, dest, op2 ,""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstSub inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        nasm.ajouteInst(new NasmMov(label ,dest, op1, ""));
        nasm.ajouteInst(new NasmSub(null, dest, op2 ,""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstDiv inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        NasmRegister tmp = nasm.newRegister();

        nasm.ajouteInst(new NasmMov(label, eax, op1, ""));
        nasm.ajouteInst(new NasmMov(null, tmp, op2, ""));
        nasm.ajouteInst(new NasmDiv(null, tmp, ""));
        nasm.ajouteInst(new NasmMov(null, dest, eax, ""));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstAffect inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand src = inst.op1.accept(this);
        NasmOperand dest = inst.result.accept(this);

        nasm.ajouteInst(new NasmMov(label, dest, src, "Affect"));
        return dest;
    }

    @Override
    public NasmOperand visit(C3aInstFEnd inst) {
        NasmLabel label = getLabel(inst);

        NasmRegister esp = nasm.newRegister();
        esp.colorRegister(Nasm.REG_ESP);
        NasmRegister ebp = nasm.newRegister();
        ebp.colorRegister(Nasm.REG_EBP);

        NasmConstant localVarSize = new NasmConstant(4*this.currentFct.getTable().nbVar());
        nasm.ajouteInst(new NasmAdd(label, esp, localVarSize, "désallocation des variables locales"));
        nasm.ajouteInst(new NasmPop(null, ebp, "restaure la valeur de ebp"));
        nasm.ajouteInst(new NasmRet(null, ""));

        this.currentFct = null;
        //this.nasm.setTempCounter(0);
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJump inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand address = inst.result.accept(this);
        nasm.ajouteInst(new NasmJmp(label, address, "Jump"));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfEqual inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        if (op1 instanceof NasmRegister) {
            nasm.ajouteInst(new NasmCmp(label, op1, op2, "JumpIfEqual 1"));
        } else {
            NasmRegister tmp = nasm.newRegister();
            nasm.ajouteInst(new NasmMov(label, tmp, op1, "JumpIfEqual 1"));
            nasm.ajouteInst(new NasmCmp(null, tmp, op2, "on passe par un registre temporaire"));
        }
        nasm.ajouteInst(new NasmJe(null, dest, "JumpIfEqual 2"));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfNotEqual inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        if (op1 instanceof NasmRegister) {
            nasm.ajouteInst(new NasmCmp(label, op1, op2, "jumpIfNotEqual 1"));
        } else {
            NasmRegister tmp = nasm.newRegister();
            nasm.ajouteInst(new NasmMov(label, tmp, op1, "jumpIfNotEqual 1"));
            nasm.ajouteInst(new NasmCmp(null, tmp, op2, "on passe par un registre temporaire"));
        }
        nasm.ajouteInst(new NasmJne(null, dest, "jumpIfNotEqual 2"));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstJumpIfLess inst) {
        NasmOperand label = getLabel(inst);
        NasmOperand op1 = inst.op1.accept(this);
        NasmOperand op2 = inst.op2.accept(this);
        NasmOperand dest = inst.result.accept(this);

        if (op1 instanceof NasmRegister) {
            nasm.ajouteInst(new NasmCmp(label, op1, op2, "JumpIfLess 1"));
        } else {
            NasmRegister tmp = nasm.newRegister();
            nasm.ajouteInst(new NasmMov(label, tmp, op1, "JumpIfLess 1"));
            nasm.ajouteInst(new NasmCmp(null, tmp, op2, "on passe par un registre temporaire"));
        }
        nasm.ajouteInst(new NasmJl(null, dest, "JumpIfLess 2"));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstParam inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand src = inst.op1.accept(this);

        nasm.ajouteInst(new NasmPush(label, src, "Param"));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstReturn inst) {
        NasmLabel label = getLabel(inst);
        NasmOperand val = inst.op1.accept(this);

        NasmRegister ebp = nasm.newRegister();
        ebp.colorRegister(Nasm.REG_EBP);
        NasmAddress retour = new NasmAddress(ebp, '+', new NasmConstant(2));

        nasm.ajouteInst(new NasmMov(label, retour, val, "ecriture de la valeur de retour"));
        return null;
    }

    @Override
    public NasmOperand visit(C3aInstWrite inst) {
        NasmLabel label = getLabel(inst);

        NasmOperand src = inst.op1.accept(this);
        NasmRegister eax = nasm.newRegister();
        eax.colorRegister(Nasm.REG_EAX);

        nasm.ajouteInst(new NasmMov(label, eax, src,"Write 1"));
        nasm.ajouteInst(new NasmCall(null, new NasmLabel("iprintLF"), "Write 2"));
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
        return new NasmConstant(oper.val);
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
        // Param ?
        final boolean isGlobal = this.tableGlobale.variables.containsKey(oper.item.identif);

        if (oper.item.isParam) {
            NasmRegister ebp = nasm.newRegister();
            ebp.colorRegister(Nasm.REG_EBP);

            return new NasmAddress(ebp, '+', new NasmConstant(2+(this.currentFct.nbArgs - oper.item.adresse)));
        } else if (!isGlobal) {
            NasmRegister ebp = nasm.newRegister();
            ebp.colorRegister(Nasm.REG_EBP);

            return new NasmAddress(ebp, '-', new NasmConstant(1+oper.item.adresse));
        } else {
            // Tab ?
            if (oper.item.taille > 1) {
                return new NasmAddress(new NasmLabel(oper.item.identif), '+', oper.index.accept(this));
            } else {
                return new NasmAddress(new NasmLabel(oper.item.identif));
            }
        }
    }

    @Override
    public NasmOperand visit(C3aFunction oper) {
        return new NasmLabel(oper.val.identif);
    }
}
