package sep3.model.operation;
import sep3.model.CPU;

// 通常のSUB命令用
public class SubOperation extends Operation {
	private CPU cpu;
	SubOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
	public void operate() {
		// AバスにMDRの値を、BバスへB0の値を出力する
		useABus(true);
		useBBus(true);

		// 両バスの値を引いてSバスに渡す。ToオペランドからFromオペランドをひく。
		int i = cpu.getABus().getValue();
		int j = cpu.getBBus().getValue();
		int o = i-j; //変更箇所

		// PSWの更新
		int p = psw_NZ(o);
		// オーバーフローするのは、iとjの符号ビットが異なり、jとoの符号ビットが異なる場合(正：jとoが一致する時)
		boolean diffMSBin  = bit(i, 0x8000) != bit(j, 0x8000);
		boolean diffMSBout = bit(j, 0x8000) == bit(o, 0x8000); //変更箇所
		if (diffMSBin && diffMSBout)			{ p |= CPU.PSW_V; }
		if (j>i)					{ p |= CPU.PSW_C; } //変更箇所
		cpu.getRegister(CPU.REG_PSW).setValue(p);

		// キャリーがあったら捨てて、Sバスの値をToオペランドに書き込む
		cpu.getSBus().setValue(o & 0xFFFF);
		writeBack(true);
	}
}
