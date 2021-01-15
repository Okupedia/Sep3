package sep3.model.operation;

import sep3.model.CPU;

public class RJPOperation extends Operation {
    private CPU cpu;
    RJPOperation(CPU cpu) { super(cpu); this.cpu = cpu; }
    public void operate() {
        // AバスにMDRの値を、BバスへB0の値を出力する
        useABus(true);
        useBBus(true);

        // 両バスの値を足してSバスに渡す。PSWの更新はしない
        int i = cpu.getABus().getValue();
        int j = cpu.getBBus().getValue();
        int o = i + j;
        //System.out.println("RJP i="+String.format("%1$04x", (i & 0xFFFF)));
        //System.out.println("RJP j="+String.format("%1$04x", (j & 0xFFFF)));
        //System.out.println("RJP o="+String.format("%1$04x", (o & 0xFFFF)));
        cpu.getSBus().setValue(o & 0xFFFF);

        // Sバスの値をToオペランドに書き込む
        writeBack(true);
    }
}
