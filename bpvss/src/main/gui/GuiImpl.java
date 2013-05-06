package main.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class GuiImpl extends JFrame {

	private static final long serialVersionUID = -2301605985365458803L;
	private String path = "";
	private String name = "";

	public GuiImpl() {

		menu();
		init();

		setTitle("BPVSS");
		setSize(500, 400);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				messageExit();
			}
		});
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	}

	public void init() {

		JPanel basic = new JPanel();
		basic.setLayout(new BoxLayout(basic, BoxLayout.Y_AXIS));
		add(basic);

		JPanel buttonPanel = new JPanel(new BorderLayout(0, 0));

		JPanel participantPanel = new JPanel(new FlowLayout());
		participantPanel.setBorder(BorderFactory
				.createEmptyBorder(0, 25, 0, 25));
		JLabel label = new JLabel("Participants");
		JTextField input = new JTextField();
		input.setPreferredSize(new Dimension(50, 20));
		input.setEditable(true);

		JPanel startPanel = new JPanel(new FlowLayout());
		JButton startButton = new JButton("Start");
		startPanel.add(startButton);

		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
		JTextPane pane = new JTextPane();

		pane.setContentType("text/html");
		String text = "<p><b>BPVSS Algorithm</b></p>"
				+ "<p>First of all, you must load a picture going to File > Load or pressing Ctrl-L. "
				+ "After that, type a number of participants and click on start.</p>";
		pane.setText(text);
		pane.setEditable(false);
		textPanel.add(pane);

		basic.add(textPanel);

		participantPanel.add(label);
		participantPanel.add(input);
		startPanel.add(startButton);
		buttonPanel.add(participantPanel, BorderLayout.NORTH);
		buttonPanel.add(startPanel);
		basic.add(buttonPanel);

		// JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//
		// JButton ntip = new JButton("Next Tip");
		// ntip.setMnemonic(KeyEvent.VK_N);
		// JButton close = new JButton("Close");
		// close.setMnemonic(KeyEvent.VK_C);
		//
		// bottom.add(ntip);
		// bottom.add(close);
		// basic.add(bottom);
		//
		// bottom.setMaximumSize(new Dimension(450, 0));

	}

	public void messageExit() {
		int confirmed = JOptionPane.showConfirmDialog(null,
				"Are you sure you want to exit?", "Exit",
				JOptionPane.YES_NO_OPTION);

		if (confirmed == JOptionPane.YES_OPTION) {
			dispose();
		}
	}

	public void menu() {

		JMenuBar menuBar = new JMenuBar();
		String pathIcon = "C:/Users/Javier/icons/";
		ImageIcon exitIcon = new ImageIcon(pathIcon + "exit.jpg");
		ImageIcon loadIcon = new ImageIcon(pathIcon + "open.jpg");

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);

		JMenuItem loadItem = new JMenuItem("Load", loadIcon);
		loadItem.setMnemonic(KeyEvent.VK_L);
		loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		loadItem.setToolTipText("Load a picture");
		loadItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					path = file.getParent();
					name = file.getName();
					System.out.println(path);
					System.out.println(name);
				}
			}
		});

		JMenuItem exitItem = new JMenuItem("Exit", exitIcon);
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		exitItem.setToolTipText("Exit application");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				messageExit();
			}
		});

		file.add(loadItem);
		file.add(exitItem);
		menuBar.add(file);
		setJMenuBar(menuBar);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GuiImpl gui = new GuiImpl();
				gui.setVisible(true);
			}
		});

	}

}
