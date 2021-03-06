package sep3.view;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// LEDのひとつ分
@SuppressWarnings("serial")

public class LED extends JLabel {
	static final String dir = "sep3/view/";

	// 点灯時にonFile、消灯時にoffFileをアイコンとするLEDを作成
	public LED(String onFile, String offFile) {
		URL url = this.getClass().getClassLoader().getResource(dir + offFile);
		if (url != null) {
			ImageIcon offIcon = new ImageIcon(url);
			setDisabledIcon(offIcon);
			setPreferredSize(new Dimension(offIcon.getIconWidth(), offIcon.getIconHeight()));
		}

		url = this.getClass().getClassLoader().getResource(dir + onFile);
		if (url != null) {
			ImageIcon onIcon = new ImageIcon(url);
			setIcon(onIcon);
		}
		setEnabled(false);	// 最初は消灯
	}

	public LED() {
		this("bigLED2off.png", "bigLED2off.png");	// 点灯しない飾りのLED
	}

	public void on()  { setEnabled(true); }
	public void off() { setEnabled(false); }
}
