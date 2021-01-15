package sep3.controller;

import sep3.Model;
import sep3.View;
import sep3.misc.SimObserver;
import sep3.model.Register;

public class RegisterObserver implements SimObserver {
	private Model model;
	private View  view;
	private boolean when;
	private int row;		// LCDの表示位置（行）
	private int col;		// LCDの表示位置（列）

	public RegisterObserver(Model m, View v, boolean w, int r, int c) {
		model = m; view = v; when = w; row = r; col = c;
	}

	public void update(Object o) {
		//System.out.println("enter Observer of Register #" + rn);
		// モデル上でレジスタ値が変更されたら、該当するLEDアレイの表示を変更する。
		// ただし、表示切り替えスイッチがwhenの時に限る。
		Register r = (Register) o;
		if (model.getDispSW().isOn() == when) {
			view.getLCD().getLED7segment(row, col).setValue(r.getValue());
			//System.out.println("show(" + row + ", " + col + ") as " + String.format("%1$04x", (r.getValue() & 0xFFFF)).toUpperCase());
		}
	}
}
