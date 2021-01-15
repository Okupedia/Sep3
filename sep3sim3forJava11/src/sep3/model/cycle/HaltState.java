package sep3.model.cycle;

import sep3.*;
import sep3.model.CPU;

public class HaltState extends State {

	//HaltState(int s) { super(s); }

	@Override
	public State clockstep(Model model) {
		CPU cpu = model.getCPU();
		model.getCPU().getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_HLT);
		//return this;
		return cpu.getStateFactory().getState(StateFactory.SC_FF0);
	}

}
