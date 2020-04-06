package fg;
import util.graph.*;
import nasm.*;
import util.intset.*;
import java.io.*;
import java.util.*;

public class FgSolution {
	int iterNum = 0;
	public Nasm nasm;
	Fg fg;
	public Map<NasmInst, IntSet> use;
	public Map<NasmInst, IntSet> def;
	public Map<NasmInst, IntSet> in;
	public Map<NasmInst, IntSet> out;

	public FgSolution(Nasm nasm, Fg fg) {
		this.nasm = nasm;
		this.fg = fg;
		this.use = new HashMap<NasmInst, IntSet>();
		this.def = new HashMap<NasmInst, IntSet>();
		this.in = new HashMap<NasmInst, IntSet>();
		this.out = new HashMap<NasmInst, IntSet>();
	}

	public void affiche(String baseFileName) {
		String fileName;
		PrintStream out = System.out;

		if (baseFileName != null) {
			try {
				baseFileName = baseFileName;
				fileName = baseFileName + ".fgs";
				out = new PrintStream(fileName);
			} catch (IOException e) {
				System.err.println("Error: " + e.getMessage());
			}
		}

		out.println("iter num = " + iterNum);
		for (NasmInst nasmInst : this.nasm.listeInst) {
			out.println("use = " + this.use.get(nasmInst) + " def = " + this.def.get(nasmInst) + "\tin = " + this.in.get(nasmInst) + "\t \tout = " + this.out.get(nasmInst) + "\t \t" + nasmInst);
		}
	}

	private class UseAndDef implements NasmVisitor<Void> {

		public UseAndDef() {
			// parcours
			for (NasmInst instructions : nasm.listeInst) {
				instructions.accept(this);
			}
		}

		@Override
		public Void visit(NasmAdd inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmCall inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmDiv inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmJe inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmJle inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmJne inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmMul inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmOr inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmCmp inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmInst inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;		}

		@Override
		public Void visit(NasmJge inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmJl inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmNot inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmPop inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmRet inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmXor inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmAnd inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmJg inst) {
			def.put(inst, new IntSet(fg.inst2Node.size()));
			use.put(inst, new IntSet(fg.inst2Node.size()));
			in.put(inst, new IntSet(fg.inst2Node.size()));
			out.put(inst, new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmJmp inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmMov inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmPush inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmSub inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		@Override
		public Void visit(NasmEmpty inst) {
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
			return null;
		}

		//TODO A confirmer qu'il ne faut pas renvoyer un null.

		@Override
		public Void visit(NasmAddress operand) {
			// return null;
			return operand.accept(this);
		}

		@Override
		public Void visit(NasmConstant operand) {
			// return null;
			return operand.accept(this);
		}

		@Override
		public Void visit(NasmLabel operand) {
			// return null;
			return operand.accept(this);
		}

		@Override
		public Void visit(NasmRegister operand) {
			// return null;
			return operand.accept(this);
		}
	}

	private class InAndOut implements NasmVisitor<Void> {

		public void calculIteratif (NasmInst inst) {
			/*  1: for all s do
				2: in(s) = {}
				3: out(s) = {}
				4: end for
				5: repeat
				6: for all s do
				7: in0(s) = in(s)
				8: out0(s) = out(s)
				9: in(s) = use(s) ∪ (out(s) − def(s))
				10: out(n) = ∪s∈succ(s)in(s)
				11: end for
				12: until in0
				(s) = in(s) et out0
				(s) = out(s), ∀s
			 */

				IntSet outCopy = out.get(inst).copy();
				IntSet inCopy = in.get(inst).copy();
				IntSet resultTemp = use.get(inst).union(out.get(inst).minus(def.get(inst)));

		}

		
		@Override
		public Void visit(NasmAdd inst) {
			return null;
		}

		@Override
		public Void visit(NasmCall inst) {
			return null;
		}

		@Override
		public Void visit(NasmDiv inst) {
			return null;
		}

		@Override
		public Void visit(NasmJe inst) {
			return null;
		}

		@Override
		public Void visit(NasmJle inst) {
			return null;
		}

		@Override
		public Void visit(NasmJne inst) {
			return null;
		}

		@Override
		public Void visit(NasmMul inst) {
			return null;
		}

		@Override
		public Void visit(NasmOr inst) {
			return null;
		}

		@Override
		public Void visit(NasmCmp inst) {
			return null;
		}

		@Override
		public Void visit(NasmInst inst) {
			return null;
		}

		@Override
		public Void visit(NasmJge inst) {
			return null;
		}

		@Override
		public Void visit(NasmJl inst) {
			return null;
		}

		@Override
		public Void visit(NasmNot inst) {
			return null;
		}

		@Override
		public Void visit(NasmPop inst) {
			return null;
		}

		@Override
		public Void visit(NasmRet inst) {
			return null;
		}

		@Override
		public Void visit(NasmXor inst) {
			return null;
		}

		@Override
		public Void visit(NasmAnd inst) {
			return null;
		}

		@Override
		public Void visit(NasmJg inst) {
			return null;
		}

		@Override
		public Void visit(NasmJmp inst) {
			return null;
		}

		@Override
		public Void visit(NasmMov inst) {
			return null;
		}

		@Override
		public Void visit(NasmPush inst) {
			return null;
		}

		@Override
		public Void visit(NasmSub inst) {
			return null;
		}

		@Override
		public Void visit(NasmEmpty inst) {
			return null;
		}

		@Override
		public Void visit(NasmAddress operand) {
			return null;
		}

		@Override
		public Void visit(NasmConstant operand) {
			return null;
		}

		@Override
		public Void visit(NasmLabel operand) {
			return null;
		}

		@Override
		public Void visit(NasmRegister operand) {
			return null;
		}
	}
}

    
