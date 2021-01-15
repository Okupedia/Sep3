package sep3.model.cycle;

import sep3.Model;
import sep3.model.CPU;
import sep3.model.operation.InstructionSet;

public class FetchTo0 extends State{
    @Override
    public State clockstep(Model model) {
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_TF0);
        //モードによって流れを変える
        int mode = cpu.getDecoder().getToMode();


        switch (mode){
            case 0x0://レジスタ
                Move(cpu);
                return cpu.getStateFactory().getState(StateFactory.SC_EX0);

            case 0x1://レジスタ間接
                Move(cpu);
                return cpu.getStateFactory().getState(StateFactory.SC_TF1);

            case 0x2://プレデクリメントレジスタ間接
                // PCの値をAバスに流す
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getToRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値を+1してSバスに流す
                cpu.getALU().operate(InstructionSet.OP_DEC);
                // Sバスの値をPCに送る
                cpu.getSBusSelector().selectTo(cpu.getDecoder().getToRegister());
                Move(cpu);
                return cpu.getStateFactory().getState(StateFactory.SC_TF1);

            case 0x3://ポストインクリメントレジスタ間接
                Move(cpu);
                // PCの値をAバスに流す
                cpu.getABusSelector().selectFrom(cpu.getDecoder().getToRegister());
                // Bバスへのゲートはすべて閉じる
                cpu.getBBusSelector().selectFrom();
                // ALUはAバスの値を+1してSバスに流す
                cpu.getALU().operate(InstructionSet.OP_INC);
                // Sバスの値をPCに送る
                cpu.getSBusSelector().selectTo(cpu.getDecoder().getToRegister());
                return cpu.getStateFactory().getState(StateFactory.SC_TF1);
        }
        return cpu.getStateFactory().getState(StateFactory.SC_ILL);
    }

    void Move(CPU cpu){
        // レジスタからAバスへ
        cpu.getABusSelector().selectFrom(cpu.getDecoder().getToRegister());
        // Bバスへのゲートはすべて閉じる
        cpu.getBBusSelector().selectFrom();
        // ALUはAバスの値をそのままSバスへ流す
        cpu.getALU().operate(InstructionSet.OP_THRA);
        // Sバスの値をMAR, MDRへ送る
        cpu.getSBusSelector().selectTo(CPU.REG_MAR, CPU.REG_MDR);
    }
}
