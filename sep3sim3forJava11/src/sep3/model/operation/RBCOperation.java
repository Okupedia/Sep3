package sep3.model.operation;

import sep3.model.CPU;

public class RBCOperation extends Operation {
    private CPU cpu;
    RBCOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
    public void operate() {
        int psw = cpu.getRegister(cpu.REG_PSW).getValue();
        if((psw&cpu.PSW_C) != 1){
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
        // AバスにMDRの値を、BバスへB0の値を出力する
        useABus(true);
        useBBus(true);

        // 両バスの値を足してSバスに渡す
        int i = cpu.getABus().getValue();
        int j = cpu.getBBus().getValue();
        int o = i + j;

        cpu.getSBus().setValue(o);
        writeBack(true);
    }
}
