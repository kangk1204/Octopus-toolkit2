package Octopus_UI;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalExclusionType;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window.Type;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Octopus_Src.CommonFunc;

public class UI_ProgressBar extends JFrame {
	private JProgressBar progressBar;
	private JLabel lblToolInstalling;
	private CommonFunc cf;
	
	
	public UI_ProgressBar(CommonFunc cf) {
		setType(Type.POPUP);
		this.cf = cf;
		setForeground(new Color(255, 255, 255));
		getContentPane().setForeground(new Color(255, 255, 255));
		setAlwaysOnTop(true);
		setModalExclusionType(ModalExclusionType.TOOLKIT_EXCLUDE);
		setAutoRequestFocus(false);
		this.setSize(0,0);


		getContentPane().setLayout(null);
		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Dialog", Font.BOLD, 14));
		progressBar.setIndeterminate(true);
		progressBar.setForeground(UIManager.getColor("info"));
		progressBar.setBackground(Color.WHITE);
		progressBar.setBounds(0, 30, 300, 30);
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

	

	public void setProgress(int value,String tool) {
		Point p = cf.getCenter(this.getWidth(), this.getHeight());
		this.setLocation(p.x, p.y);
		progressBar.setValue(value);
		progressBar.repaint();
		lblToolInstalling.setText(tool+" : Installing...");
		
	}
}