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
		new UseAndDef();
		new InAndOut();
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
			// Parcours des instructions
			for (NasmInst instructions : nasm.listeInst) {
				instructions.accept(this);
			}
		}

		public void newRegister (NasmInst inst) {

			if (inst.source instanceof NasmRegister  && inst.source.isGeneralRegister()) {
				final NasmRegister source = (NasmRegister) inst.source;
				if (inst.srcUse) use.get(inst).add(source.val);
				if (inst.srcDef) def.get(inst).add(source.val);
			}
			if (inst.destination instanceof NasmRegister && inst.destination.isGeneralRegister()) {
				final NasmRegister destination = (NasmRegister) inst.destination;
				if (inst.destDef) def.get(inst).add(destination.val);
				if (inst.destUse) use.get(inst).add(destination.val);
			}
		}


		public void createDefUse(NasmInst inst)
		{
			def.put(inst,new IntSet(fg.inst2Node.size()));
			use.put(inst,new IntSet(fg.inst2Node.size()));
		}

		public void createInOut(NasmInst inst) {
			in.put(inst,new IntSet(fg.inst2Node.size()));
			out.put(inst,new IntSet(fg.inst2Node.size()));
		}

		@Override
		public Void visit(NasmAdd inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmCall inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmDiv inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmJe inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmJle inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmJne inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmMul inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmOr inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmCmp inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmInst inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;		}

		@Override
		public Void visit(NasmJge inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmJl inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmNot inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmPop inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmRet inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmXor inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmAnd inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmJg inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmJmp inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmMov inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmPush inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmSub inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		@Override
		public Void visit(NasmEmpty inst) {
			createDefUse(inst);
			newRegister(inst);
			createInOut(inst);
			return null;
		}

		// A laisser vide

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
		public Void visit(NasmRegister operand) { return null; }
	}

	private class InAndOut implements NasmVisitor<Void> {

		// Permet d'iterer
		private boolean conditionSortie = false;
		public InAndOut() {
			while (!conditionSortie) {
				// sortie après la boucle si tout va bien, sinon conditionSortie = faux à la fin du calcul iteratif
				iterNum++;
				conditionSortie = true;
				for (NasmInst instructions : nasm.listeInst) {
					instructions.accept(this);
				}

			}
		}

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

			// Les 4 premières instructions sont faites dans la classe DefAndUse
			// 5 et 6 est le while + for  dans le constructeur
			//7
			IntSet inCopy = in.get(inst).copy();
			//8
			IntSet outCopy = out.get(inst).copy();
			// Use copy car bug sinon
			IntSet useCopy = use.get(inst).copy();
			//9
			IntSet inResult = useCopy.union(out.get(inst).copy().minus(def.get(inst)));
			in.put(inst, inResult);

			//10

			IntSet outResult = new IntSet(fg.inst2Node.size());
			NodeList list = fg.inst2Node.get(inst).succ();

			while(list != null) {
				outResult.union(in.get(fg.node2Inst.get(list.head)));
				list = list.tail;
			}

			out.put(inst,outResult);

			//12
			if(!inCopy.equal(inResult) || !outCopy.equal(outResult)) conditionSortie = false;

		}


		@Override
		public Void visit(NasmAdd inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmCall inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmDiv inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJe inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJle inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJne inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmMul inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmOr inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmCmp inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmInst inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJge inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJl inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmNot inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmPop inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmRet inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmXor inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmAnd inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJg inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmJmp inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmMov inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmPush inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmSub inst) {
			calculIteratif(inst);
			return null;
		}

		@Override
		public Void visit(NasmEmpty inst) {
			calculIteratif(inst);
			return null;
		}


		// A laisser vide

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


