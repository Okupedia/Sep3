package sep3.model.operation;

import sep3.model.CPU;

public class CLROperation extends Operation{
    private CPU cpu;
    CLROperation(CPU cpu) { super(cpu); this.cpu = cpu; }

    public void operate() {

        // AバスもBバスも使わない
        useABus(false);
        useBBus(false);

        // Sバスには、Aバスの値をそのまま渡す（Aバスは使わないので0になっている）
        cpu.getSBus().setValue(cpu.getABus().getValue());

        writeBack(true);
    }
}
