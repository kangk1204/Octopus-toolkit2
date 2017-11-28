package Octopus_UI;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Octopus_Src.CommonFunc;

public class UI_ProgressBar extends JFrame {
	private JProgressBar progressBar;
	private JLabel lblToolInstalling;
	private CommonFunc cf;

	public UI_ProgressBar(CommonFunc cf) {
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int checkResult = JOptionPane.showConfirmDialog(null,"Octopus-toolkit is running...\nDo you want to terminate?","Octopus-toolkit", JOptionPane.YES_NO_OPTION);
				if(checkResult == 0){
					System.exit(0);
				}	
			}
		});

		setTitle("Installing...");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.cf = cf;
		setForeground(new Color(255, 255, 255));
		getContentPane().setForeground(new Color(255, 255, 255));
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setAutoRequestFocus(false);
		this.setSize(0, 0);

		getContentPane().setLayout(null);
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Dialog", Font.BOLD, 14));
		progressBar.setIndeterminate(true);
		progressBar.setForeground(UIManager.getColor("info"));
		progressBar.setBackground(Color.WHITE);
		progressBar.setBounds(1, 29, 298, 30);
		progressBar.setValue(10);
		getContentPane().add(progressBar);

		lblToolInstalling = new JLabel("Tool : Installing...");
		lblToolInstalling.setBackground(new Color(255, 255, 255));
		lblToolInstalling.setHorizontalAlignment(SwingConstants.CENTER);
		lblToolInstalling.setFont(new Font("Dialog", Font.BOLD, 14));
		lblToolInstalling.setBounds(0, 10, 300, 15);
		getContentPane().add(lblToolInstalling);

		this.setVisible(true);
	}

	public void setProgress(int value, String tool) {
		Point p = cf.getCenter(this.getWidth(), this.getHeight());
		this.setLocation(p.x, p.y);
		progressBar.setValue(value);
		progressBar.repaint();
		lblToolInstalling.setText(tool + " : Installing...");
	}

}