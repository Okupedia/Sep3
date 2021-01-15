package sep3.model.cycle;

import sep3.Model;
import sep3.model.CPU;
import sep3.model.operation.InstructionSet;

public class FetchFrom1 extends State{
    @Override
    public State clockstep(Model model) {
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_FF1);

        //System.out.println("%% FF1 %%");
        // MDRからAバスに送る
        cpu.getABusSelector().selectFrom(CPU.REG_MDR);
        // ALUはAバスの値をそのままSバスへ流す
        cpu.getALU().operate(InstructionSet.OP_THRA);
        // Sバスの値をB0へ送る
        cpu.getSBusSelector().selectTo(CPU.REG_B0);


        return cpu.getStateFactory().getState(StateFactory.SC_TF0);
    }
}
