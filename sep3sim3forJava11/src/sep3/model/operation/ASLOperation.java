package sep3.model.operation;

import sep3.model.CPU;

public class ASLOperation extends Operation {
    private CPU cpu;
    ASLOperation(CPU cpu) { super(cpu); this.cpu = cpu; }

    public void operate() {

        // MDRからAバスに送る
        cpu.getABusSelector().selectFrom(CPU.REG_MDR);
        int i = cpu.getABus().getValue();
        int o = i * 2;

        // PSWの更新
        int p = psw_NZ(o);
        // TODO : ここの条件はaddのものだから変えなきゃいけない
        // オーバーフローするのは、iとjの符号ビットが同じで、oの符号ビットと異なる場合
        //boolean sameMSBin  = bit(i, 0x8000) == bit(j, 0x8000);
        boolean diffMSBout = bit(i, 0x8000) != bit(o, 0x8000);
        //if (sameMSBin && diffMSBout)			{ p |= CPU.PSW_V; }
        if (bit(o, 0x10000))					{ p |= CPU.PSW_C; }

        cpu.getRegister(CPU.REG_PSW).setValue(p);
        // キャリーがあったら捨てて、Sバスの値をToオペランドに書き込む
        cpu.getSBus().setValue(o & 0xFFFF);
        writeBack(true);

    }
}
