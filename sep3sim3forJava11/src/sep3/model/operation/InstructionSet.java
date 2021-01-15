package sep3.model.operation;

import sep3.misc.Factory;
import sep3.model.CPU;

// SEP-3 の命令セット
public class InstructionSet extends Factory<Integer, Operation> {
	public static final int OP_HLT  = 0x00;		// 値は、操作コードのビット列に従う
	public static final int OP_CLR  = 0x04;//
	public static final int OP_ASL  = 0x08;// 32
	public static final int OP_ASR  = 0x09;// 32
	public static final int OP_LSL  = 0x0C;// 33
	public static final int OP_LSR  = 0x0D;// 32
	public static final int OP_ROL  = 0x0E;// 36
	public static final int OP_ROR  = 0x0F;// 36
	public static final int OP_MOV  = 0x10;//
	public static final int OP_JMP  = 0x11;//
	public static final int OP_RET  = 0x12;//
	public static final int OP_RIT  = 0x13;//
	public static final int OP_ADD  = 0x14;//
	public static final int OP_RJP  = 0x15;// 17 なくても実装的できるけど
	public static final int OP_SUB  = 0x18;//
	public static final int OP_CMP  = 0x1B;//
	public static final int OP_NOP  = 0x1C;//
	public static final int OP_OR   = 0x20;//
	public static final int OP_XOR  = 0x21;//
	public static final int OP_AND  = 0x22;//
	public static final int OP_BIT  = 0x23;//
	public static final int OP_JSR  = 0x2C;//
	public static final int OP_RJS  = 0x2D;// RFPを読んでもOK
	public static final int OP_SVC  = 0x2E;//
	public static final int OP_BRN  = 0x30;// 22 ここは必要
	public static final int OP_BRZ  = 0x31;//
	public static final int OP_BRV  = 0x32;//
	public static final int OP_BRC  = 0x33;//
	public static final int OP_RBN  = 0x38;//
	public static final int OP_RBZ  = 0x39;//
	public static final int OP_RBV  = 0x3A;//
	public static final int OP_RBC  = 0x3B;//
	public static final int OP_INC  = 0x100;		// 命令とは直接関係がなく、内部でだけ使う＋１動作
	public static final int OP_DEC  = 0x101;		// 命令とは直接関係がなく、内部でだけ使う－１動作
	public static final int OP_THRA = 0x102;		// 命令とは直接関係がなく、Aバスのデータを素通しする動作
	public static final int OP_THRB = 0x103;		// 命令とは直接関係がなく、Bバスのデータを素通しする動作
	public static final int OP_ADD2 = 0x104;		// ADDと同じだが、PSW更新せず
	public static final int OP_ILL  = 0x200;		// 不正な命令

	private Operation illop;

	public InstructionSet(CPU cpu) {
		makeItem(Integer.valueOf(OP_HLT),  new HltOperation(cpu));
		makeItem(Integer.valueOf(OP_MOV),  new MovOperation(cpu));
		makeItem(Integer.valueOf(OP_JMP),  new JmpOperation(cpu));
		makeItem(Integer.valueOf(OP_ADD),  new AddOperation(cpu));
		makeItem(Integer.valueOf(OP_SUB),  new SubOperation(cpu));
		makeItem(Integer.valueOf(OP_NOP),  new NopOperation(cpu));
		makeItem(Integer.valueOf(OP_INC),  new IncOperation(cpu));
		makeItem(Integer.valueOf(OP_DEC),  new DecOperation(cpu));
		makeItem(Integer.valueOf(OP_THRA), new ThraOperation(cpu));
		makeItem(Integer.valueOf(OP_THRB), new ThrbOperation(cpu));
		makeItem(Integer.valueOf(OP_ADD2), new Add2Operation(cpu));
		illop = new IllOperation(cpu);
		makeItem(Integer.valueOf(OP_ILL),  illop);
		//以下自分で作成したもの
		makeItem(Integer.valueOf(OP_CLR), new CLROperation(cpu));
		makeItem(Integer.valueOf(OP_ASL), new ASLOperation(cpu));
		makeItem(Integer.valueOf(OP_ASR), new ASROperation(cpu));
		makeItem(Integer.valueOf(OP_RET), new RETOperation(cpu));
		makeItem(Integer.valueOf(OP_RJP), new RJPOperation(cpu));
		makeItem(Integer.valueOf(OP_CMP), new CMPOperation(cpu));
		makeItem(Integer.valueOf(OP_OR), new OROperation(cpu));
		makeItem(Integer.valueOf(OP_XOR), new XOROperation(cpu));
		makeItem(Integer.valueOf(OP_AND), new ANDOperation(cpu));
		makeItem(Integer.valueOf(OP_BIT), new BITOperation(cpu));
		makeItem(Integer.valueOf(OP_JSR), new JSROperation(cpu));
		makeItem(Integer.valueOf(OP_RJS), new RJSOperation(cpu));
		makeItem(Integer.valueOf(OP_SVC), new SVCOperation(cpu));
		makeItem(Integer.valueOf(OP_BRN), new BRNOperation(cpu));
		makeItem(Integer.valueOf(OP_BRZ), new BRZOperation(cpu));
		makeItem(Integer.valueOf(OP_BRC), new BRCOperation(cpu));
		makeItem(Integer.valueOf(OP_BRV), new BRVOperation(cpu));
		makeItem(Integer.valueOf(OP_RBN), new RBNOperation(cpu));
		makeItem(Integer.valueOf(OP_RBZ), new RBZOperation(cpu));
		makeItem(Integer.valueOf(OP_RBC), new RBCOperation(cpu));
		makeItem(Integer.valueOf(OP_RBV), new RBVOperation(cpu));
	}

	// 用意されている命令であるかどうかのチェック（不正命令のチェック）
	public boolean isLegalInstruction(int opcode) {
		Operation v;
		switch (opcode) {
		case OP_INC: case OP_DEC: case OP_THRA: case OP_THRB: case OP_ADD2: case OP_ILL:
			v = null;
			break;
		default:
			//v = getItem(new Integer(opcode));
			v = getItem(Integer.valueOf(opcode));
			break;
		}
		return v != null;
	}
	// 操作コードを指定して、命令クラスを取得する
	public Operation getOperation(int opcode) {
		Operation v;
		//v = getItem(new Integer(opcode));
		v = getItem(Integer.valueOf(opcode));
		if (v == null) {
			v = illop;
		}
		return v;
	}
}
