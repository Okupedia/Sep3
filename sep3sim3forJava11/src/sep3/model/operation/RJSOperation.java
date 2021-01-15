package sep3.model.operation;

import sep3.model.CPU;

public class RJSOperation extends Operation {
    private CPU cpu;
    RJSOperation(CPU cpu) { super(cpu); this.cpu = cpu; }

    public void operate() {
        cpu.getABusSelector().selectFrom(CPU.REG_R7);
        // ALUはAバスの値をそのままSバスへ流す
        cpu.getALU().operate(InstructionSet.OP_THRA);
        // Sバスの値をB0へ送る
        cpu.getSBusSelector().selectTo(CPU.REG_B0);
        // Sバスの値をToオペランドに書き込む To:11110
        writeBack(true);


        useABus(true);
        useBBus(true);
        // ALUはAバスの値をそのままSバスへ流す
        // 両バスの値を足してSバスに渡す
        int i = cpu.getABus().getValue();
        int j = cpu.getBBus().getValue();
        int o = i + j;
        cpu.getSBus().setValue(o & 0xFFFF);
        cpu.getSBusSelector().selectTo(CPU.REG_R7);
    }
}
