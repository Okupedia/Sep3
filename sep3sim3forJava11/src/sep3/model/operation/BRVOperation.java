package sep3.model.operation;

import sep3.model.CPU;

public class BRVOperation extends Operation {
    private CPU cpu;
    BRVOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
    public void operate() {
        int psw = cpu.getRegister(cpu.REG_PSW).getValue();
        if((psw&cpu.PSW_V) != 1){
            // AバスもBバスも使わない
            useABus(false);
            useBBus(false);

            // Bバスの値をそのままSバスに出力する
            int i = cpu.getBBus().getValue();
            cpu.getSBus().setValue(i);

            // Sバスの値は捨てる
            writeBack(false);
            return;
        }
        // Aバスは使わない、BバスへB0の値を出力する
        useABus(false);
        useBBus(true);
        cpu.getBBusSelector().selectFrom(CPU.REG_B0);

        // Bバスの値をそのままSバスへ渡す
        cpu.getSBus().setValue(cpu.getBBus().getValue());

        // Sバスの値をToオペランドに書き込む
        writeBack(true);
    }
}
