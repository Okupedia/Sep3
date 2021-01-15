package sep3.model.cycle;

import sep3.Model;
import sep3.model.CPU;
import sep3.model.Memory;
import sep3.model.operation.InstructionSet;

public class FetchTo1 extends State {
    @Override
    public State clockstep(Model model) {
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_TF1);


        // MAR をアドレスバスに流す
        model.getAddrBusSelector().selectFrom(CPU.REG_MAR);


        if(cpu.getDecoder().getOpCode() == InstructionSet.OP_MOV){
            model.getMemory().mov_access(Memory.MEM_RD);
        }else {
            // メモリを読み出してデータバスへ出力
            model.getMemory().access(Memory.MEM_RD);
        }

        // データバスからMDRに流す
        model.getDataBusSelector().selectTo(CPU.REG_MDR);

        return cpu.getStateFactory().getState(StateFactory.SC_EX0);
    }
}
