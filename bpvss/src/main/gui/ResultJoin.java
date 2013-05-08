package main.gui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ResultJoin extends JFrame {

	private String path;

	public ResultJoin(String path) {
		this.path = path;

		init();
		setTitle("Unión de shares");
		setSize(600, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}

	public void init() {

		JPanel basic = new JPanel();
		basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
		add(basic);

		JPanel buttonPanel = new JPanel(new BorderLayout(0, 0));
		ImageIcon icon = new ImageIcon(path + "joinShare.png");
		JLabel label = new JLabel(icon);
		buttonPanel.add(label);
		basic.add(buttonPanel);

	}

	public void run() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ResultJoin gui = new ResultJoin(path);
				gui.setVisible(true);
			}
		});
	}

}
