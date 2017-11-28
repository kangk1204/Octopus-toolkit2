package Octopus_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;

public class UI_Password extends JFrame implements WindowListener{
	private static JPasswordField pw_txt;
	private CommonFunc cf;
	private DataSet ds;
	private String step;

	public UI_Password(DataSet ds, CommonFunc cf,String step){
		this.cf = cf;
		this.ds = ds;
		this.step = step;
		String title = "";
		if(step.equals("CheckProgram")){
			title = "Install tools";
		}else{
			title = "Install R package";
		}
		this.setTitle(title);
		this.setResizable(false);
		this.getContentPane().setLayout(null);
		this.addWindowListener(this);
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 400, 190);
		
		panel.setLayout(null);
		
		JLabel pw_lbl = new JLabel("User Password : ");
		pw_lbl.setFont(new Font("Dialog", Font.PLAIN, 13));
		pw_lbl.setBounds(20, 80, 110, 15);
		panel.add(pw_lbl);
		
		pw_txt = new JPasswordField();
		pw_txt.setBounds(130, 75, 250, 25);
		panel.add(pw_txt);
		
		this.getContentPane().add(panel);
		
		JLabel descript1_lable = new JLabel("Octopus-toolkit need to User's password to install ");
		descript1_lable.setBackground(Color.WHITE);
		descript1_lable.setBounds(20, 23, 400, 15);
		panel.add(descript1_lable);
		
		String descript = "";
		if(step.equals("CheckProgram")){
			descript = "tools for analysis.";
		}else{
			descript = "R packages in the Graph step.";
		}
			
		JLabel descript2_lable = new JLabel(descript);
		descript2_lable.setBounds(20, 43, 350, 15);
		panel.add(descript2_lable);
		
		JSeparator pw_separator = new JSeparator();
		pw_separator.setBounds(20, 135, 357, 2);
		panel.add(pw_separator);
		
		JButton ok_btn = new JButton("OK");
		ok_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String savePw = pw_txt.getText().toString();
				ds.setPassword(savePw);

				// Check PW
				FileWriter fw;
				try {
					fw = new FileWriter(ds.getPath() + "Script/Check_Password.sh");
					fw.write("echo " + savePw + " | sudo -S cat /usr/bin/cp\n");
					fw.flush();

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("\nError : Unable to make the Check_Password.sh file due to unknown reasons (Err009)");
					ds.writeLogRun("Error : Unable to make the Check_Password.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
				}

				String cmd_Pw1[] = { "chmod", "777", ds.getPath() + "/Script/Check_Password.sh" };
				String cmd_Pw2[] = { "sh", ds.getPath() + "/Script/Check_Password.sh" };
				cf.shellCmd(cmd_Pw1);
				String resultStr = cf.shellCmd(cmd_Pw2);
				
				if (step.equals("CheckProgram")) {
					if (resultStr.equals("incorrect password")) {
						JOptionPane.showMessageDialog(null, "The password you entered is incorrect. (Err008)",
								"Check Password", JOptionPane.INFORMATION_MESSAGE);
					} else {
						ds.getCheckProgram().installTool(true);
					}

				} else {
					//Graph
					if (resultStr.equals("incorrect password")) {
						JOptionPane.showMessageDialog(null, "The password you entered is incorrect. (Err008)",
								"Check Password", JOptionPane.INFORMATION_MESSAGE);
					} else {
						ds.getMainUI().setPasswordVisible(true);
					}
				}

			}
		});
		ok_btn.setBounds(198, 150, 85, 25);
		panel.add(ok_btn);
		
		JButton cancel_btn = new JButton("Cancel");
		cancel_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				
				if(step.equals("CheckProgram")){
					ds.getCheckProgram().installTool(false);
				}else{
					ds.getMainUI().setPasswordVisible(false);	
				}
					
			
			}
		});
		cancel_btn.setBounds(295, 150, 85, 25);
		panel.add(cancel_btn);
		
		final JCheckBox pw_chbx = new JCheckBox("Show password");
		pw_chbx.setBackground(Color.WHITE);
		pw_chbx.setBounds(130, 104, 140, 23);
		panel.add(pw_chbx);
		
		pw_chbx.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(pw_chbx.isSelected()){
					pw_txt.setEchoChar((char)0);
				}else{
					pw_txt.setEchoChar('*');
				}
			}
		});
		
		Point p = cf.getCenter(this.getWidth(), this.getHeight());
		this.setLocation(p.x, p.y);
		
		if(ds.getOS().equals("Fedora")){
			this.setSize(400,225); // +35				
		}else if(ds.getOS().equals("Mint")){
			this.setSize(400,215); // +25
		}else{
			this.setSize(400,190);
		}
		
	}
	
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
		if(step.equals("CheckProgram")){
			ds.closeLog();
			System.exit(0);			
		}
	}
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}
