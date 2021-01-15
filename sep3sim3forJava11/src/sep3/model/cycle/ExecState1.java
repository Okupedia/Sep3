package sep3.model.cycle;

import sep3.Model;
import sep3.model.CPU;
import sep3.model.Decoder;
import sep3.model.Memory;

/*
JSR やRJS 命令で（戻り番地をスタックに積むために）主記憶装置にアクセスしている間、CPU の中では仕事
がない。その空き時間を利用して、ジャンプ先アドレスをPC に格納してやればよさそうだ。このとき、ジャン
プ先アドレスがどこにあるかというと、JSR ならFrom オペランドがそうだからB0 レジスタ内にある。そして、
RJS ならFrom とTo のオペランドの和を取るのだから、B0 とMDR 内である。
お～っと待った！この場合に限っては、MDR に必要なデータがない。なぜならMDR にあるのは、To オペラ
ンドを読み出すとき、つまり(R6)+の処理をしたときにメモリから取り出した値だからだ。この場合に真に必要な
のは、変位（これはB0 にある）と足す相手としてのPC の値である。したがって、RJS のときは、B0 とR7 を
足すようにしなければならない。
To オペランドをメモリに書き込む作業をする状態において、JSR やRJS 命令のときに限って例外的に行うこ
の作業を、追加しよう。
 */

public class ExecState1 extends State{//To間接アドレッシングの時は
    @Override
    public State clockstep(Model model) {
        CPU cpu = model.getCPU();
        cpu.getRegister(CPU.REG_SC).setInitValue(StateFactory.SC_EX1);

        model.getDataBusSelector().selectFrom(CPU.REG_MDR);
        model.getMemory().access(Memory.MEM_WR);

        return cpu.getStateFactory().getState(StateFactory.SC_IF0);
    }
}
