package sep3.model.operation;

import sep3.model.CPU;

public class SVCOperation extends Operation {
    private CPU cpu;
    SVCOperation(CPU cpu) { super(cpu); this.cpu = cpu; }

    public void operate() {
        cpu.getABusSelector().selectFrom(CPU.REG_R7);
        // Bバスへのゲートはすべて閉じる
        cpu.getBBusSelector().selectFrom();
        // ALUはAバスの値をそのままSバスへ流す
        cpu.getALU().operate(InstructionSet.OP_THRA);
        // Sバスの値をToオペランドに書き込む To:11110
        writeBack(true);


        useABus(true);
        // ALUはAバスの値をそのままSバスへ流す
        cpu.getALU().operate(InstructionSet.OP_THRA);
        cpu.getSBusSelector().selectTo(CPU.REG_R7);
        //TODO:ITフラグのセット




    }
}
