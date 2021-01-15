package sep3.model.cycle;

import sep3.Model;
import sep3.model.CPU;
import sep3.model.Memory;
import sep3.model.operation.InstructionSet;

public class FetchFrom2 extends State {
    @Override
    public State clockstep(Model model) {
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_FF2);

        //System.out.println("%% FF2 %%");

        // MAR をアドレスバスに流す
        model.getAddrBusSelector().selectFrom(CPU.REG_MAR);
        // メモリを読み出してデータバスへ出力
        model.getMemory().access(Memory.MEM_RD);
        // データバスからMDRに流す
        model.getDataBusSelector().selectTo(CPU.REG_MDR);

        /*
        if(cpu.getDecoder().getFromMode() == 0x3){
            // PCの値をAバスに流す
            cpu.getABusSelector().selectFrom(cpu.getDecoder().getFromRegister());
            // Bバスへのゲートはすべて閉じる
            cpu.getBBusSelector().selectFrom();
            // ALUはAバスの値を+1してSバスに流す
            cpu.getALU().operate(InstructionSet.OP_INC);
            // Sバスの値をPCに送る
            cpu.getSBusSelector().selectTo(CPU.REG_PC);
            //cpu.getSBusSelector().selectTo(cpu.getDecoder().getFromRegister());
        }
        */

        return cpu.getStateFactory().getState(StateFactory.SC_FF1);
    }
}
