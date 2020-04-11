package Octopus_Option_Panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;

public class Panel_Preprocessing{
	private Icon info_img = new ImageIcon(Panel_Preprocessing.class.getResource("/Octopus_Icon/ToolTip.png"));
	private CommonFunc cf;
	
	private JPanel mainPanel = new JPanel();
	private JTextField minRead_txt;
	private JComboBox alignUn_cbx;
	private JComboBox qualityConvert_cbx;
	private JRadioButton biologicalYes_rdb;
	private JRadioButton biologicalNo_rdb;
		
	public void init(){
		minRead_txt.setText("Only Integer");
		alignUn_cbx.setSelectedIndex(0);
		qualityConvert_cbx.setSelectedIndex(0);
		biologicalYes_rdb.setSelected(false);
		biologicalNo_rdb.setSelected(true);
	}
	
	public boolean checkChangeValue(){
	
		if(!minRead_txt.getText().equals("Only Integer")){
			return true;
		}
		
		if(alignUn_cbx.getSelectedIndex() != 0){
			return true;
		}
		
		if(qualityConvert_cbx.getSelectedIndex() != 0){
			return true;
		}
		
		if(biologicalYes_rdb.isSelected()){
			return true;
		}
		
		return false;
	}
	
	public Panel_Preprocessing(DataSet ds){
		cf = new CommonFunc(ds);
		mainPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(12, 23, 452, 442);
		mainPanel.setLayout(null);
		
		JLabel second_lbl = new JLabel("Convert Sra to Fastq (Filtering)");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 69, 250, 15);
		mainPanel.add(second_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainPanel.add(separator);
		
		JLabel head_lbl = new JLabel("Preprocessing");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(320, 20, 126, 15);
		mainPanel.add(head_lbl);
						
		JLabel minRead_lbl = new JLabel("MIN-Read Length : ");
		minRead_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minRead_lbl.setBounds(30, 101, 120, 15);
		mainPanel.add(minRead_lbl);
		
		minRead_txt = new JTextField();
		minRead_txt.setColumns(10);
		minRead_txt.setBounds(220, 97, 185, 25);
		mainPanel.add(minRead_txt);
		
		JLabel alignUn_lbl = new JLabel("Aligned or unaligned reads : ");
		alignUn_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignUn_lbl.setBounds(30, 136, 200, 15);
		mainPanel.add(alignUn_lbl);
		
		alignUn_cbx = new JComboBox();
		alignUn_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignUn_cbx.setModel(new DefaultComboBoxModel(new String[] {"NotUse", "Both", "Aligned", "Unaligned"}));
		alignUn_cbx.setBackground(Color.WHITE);
		alignUn_cbx.setBounds(220, 131, 185, 24);
		mainPanel.add(alignUn_cbx);
		
		JLabel qualityConvert_lbl = new JLabel("Quality conversion (offset) : ");
		qualityConvert_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		qualityConvert_lbl.setBounds(30, 171, 200, 15);
		mainPanel.add(qualityConvert_lbl);
		
		qualityConvert_cbx = new JComboBox();
		qualityConvert_cbx.setModel(new DefaultComboBoxModel(new String[] {"33 : Sanger / Illumina 1.9", "64 : Illumina 1.5"}));
		qualityConvert_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		qualityConvert_cbx.setBackground(Color.WHITE);
		qualityConvert_cbx.setBounds(220, 165, 185, 24);
		mainPanel.add(qualityConvert_cbx);
		
		JLabel biologicalR_lbl = new JLabel("Dump biological reads(Only) : ");
		biologicalR_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		biologicalR_lbl.setBounds(30, 206, 200, 15);
		mainPanel.add(biologicalR_lbl);
		
		ButtonGroup bg = new ButtonGroup();
		
		biologicalYes_rdb = new JRadioButton("Yes");
		biologicalYes_rdb.setFont(new Font("Dialog", Font.PLAIN, 12));
		biologicalYes_rdb.setBackground(Color.WHITE);
		biologicalYes_rdb.setBounds(220, 202, 60, 23);
		bg.add(biologicalYes_rdb);
		
		biologicalNo_rdb = new JRadioButton("No");
		biologicalNo_rdb.setFont(new Font("Dialog", Font.PLAIN, 12));
		biologicalNo_rdb.setBackground(Color.WHITE);
		biologicalNo_rdb.setBounds(287, 202, 45, 23);
		bg.add(biologicalNo_rdb);
		
		mainPanel.add(biologicalYes_rdb);
		mainPanel.add(biologicalNo_rdb);
		
		JLabel minRead_img = new JLabel("");
		minRead_img.setToolTipText("Filter by sequence length >= <len>");
		minRead_img.setIcon(info_img);
		minRead_img.setHorizontalAlignment(SwingConstants.LEFT);
		minRead_img.setBounds(411, 99, 17, 20);
		mainPanel.add(minRead_img);
		
		JLabel alignUn_img = new JLabel("");
		alignUn_img.setToolTipText("<html>Aligned : Dump only aligned sequences.<br>UnAligned : Dump only unaligned sequences.</html>");
		alignUn_img.setIcon(info_img);
		alignUn_img.setHorizontalAlignment(SwingConstants.LEFT);
		alignUn_img.setBounds(411, 133, 17, 20);
		mainPanel.add(alignUn_img);
		
		JLabel qualityConvert_img = new JLabel("");
		qualityConvert_img.setToolTipText("Offset to use for quality conversion, default is 33");
		qualityConvert_img.setIcon(info_img);
		qualityConvert_img.setHorizontalAlignment(SwingConstants.LEFT);
		qualityConvert_img.setBounds(411, 167, 17, 20);
		mainPanel.add(qualityConvert_img);
		
		JLabel biologicalR_img = new JLabel("");
		biologicalR_img.setToolTipText("Dump only biological reads : Skip technical");
		biologicalR_img.setIcon(info_img);
		biologicalR_img.setHorizontalAlignment(SwingConstants.LEFT);
		biologicalR_img.setBounds(335, 204, 17, 20);
		mainPanel.add(biologicalR_img);
		
		init();
	}
	
	public JPanel getPanel(){
		return mainPanel;
	}
	
	public String getFastqDump(){
		String option = "";
		if(cf.checkInteger(minRead_txt.getText().toString()) && !minRead_txt.getText().toString().equals("Only Integer")){
			option = "-M "+minRead_txt.getText().toString();
		}
		
		if(alignUn_cbx.getSelectedItem().equals("Both")){
			option = option + " --aligned --unaligned";
		}else if(alignUn_cbx.getSelectedItem().equals("Aligned")){
			option = option + " --aligned";
		}else if(alignUn_cbx.getSelectedItem().equals("UnAligned")){
			option = option + " --unaligned";
		}
		
		if(qualityConvert_cbx.getSelectedIndex() == 1){ //64
			option = option + " -Q 64";
		}
		
		if(biologicalYes_rdb.isSelected()){
			option = option + " --skip-technical";
		}
		
		return option;
	}
}
