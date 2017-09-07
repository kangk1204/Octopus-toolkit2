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
	private JTextField maxRate_txt;
	private JTextField minRate_txt;
	private JComboBox maxRate_cbx;
	private JComboBox minRate_cbx;
	private JComboBox overwrite_cbx;
	private JTextField minRead_txt;
	private JComboBox alignUn_cbx;
	private JComboBox qualityConvert_cbx;
	private JRadioButton biologicalYes_rdb;
	private JRadioButton biologicalNo_rdb;
		
	public void init(){
		maxRate_txt.setText("Only Integer");
		maxRate_cbx.setSelectedIndex(0);
		minRate_txt.setText("Only Integer");
		minRate_cbx.setSelectedIndex(0);
		overwrite_cbx.setSelectedIndex(1);
		minRead_txt.setText("Only Integer");
		alignUn_cbx.setSelectedIndex(0);
		qualityConvert_cbx.setSelectedIndex(0);
		biologicalYes_rdb.setSelected(false);
		biologicalNo_rdb.setSelected(true);
	}
	
	public Panel_Preprocessing(DataSet ds){
		cf = new CommonFunc(ds);
		mainPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(12, 23, 452, 442);
		mainPanel.setLayout(null);
		
		JLabel first_lbl = new JLabel("Transfer rate");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 69, 120, 15);
		mainPanel.add(first_lbl);
		
		JLabel second_lbl = new JLabel("Convert Sra to Fastq (Filtering)");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 228, 250, 15);
		mainPanel.add(second_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainPanel.add(separator);
		
		JLabel head_lbl = new JLabel("Preprocessing");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(320, 20, 126, 15);
		mainPanel.add(head_lbl);
		
		
		maxRate_txt = new JTextField();
		maxRate_txt.setBounds(123, 95, 120, 25);
		mainPanel.add(maxRate_txt);
		maxRate_txt.setColumns(10);
		
		JLabel maxRate_lbl = new JLabel("MAX-RATE : ");
		maxRate_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxRate_lbl.setBounds(30, 100, 90, 15);
		mainPanel.add(maxRate_lbl);
		
		JLabel minRate_lbl = new JLabel("MIN-RATE : ");
		minRate_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minRate_lbl.setBounds(30, 135, 90, 15);
		mainPanel.add(minRate_lbl);
		
		minRate_txt = new JTextField();
		minRate_txt.setColumns(10);
		minRate_txt.setBounds(123, 130, 120, 25);
		mainPanel.add(minRate_txt);
		
		maxRate_cbx = new JComboBox();
		maxRate_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxRate_cbx.setModel(new DefaultComboBoxModel(new String[] {"Gb", "Mb", "Kb"}));
		maxRate_cbx.setBackground(Color.WHITE);
		maxRate_cbx.setBounds(255, 95, 70, 24);
		mainPanel.add(maxRate_cbx);
		
		minRate_cbx = new JComboBox();
		minRate_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		minRate_cbx.setModel(new DefaultComboBoxModel(new String[] {"Gb", "Mb", "Kb"}));
		minRate_cbx.setBackground(Color.WHITE);
		minRate_cbx.setBounds(255, 130, 70, 24);
		mainPanel.add(minRate_cbx);
		
		JLabel overwrite_lbl = new JLabel("Overwrite : ");
		overwrite_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		overwrite_lbl.setBounds(30, 170, 90, 15);
		mainPanel.add(overwrite_lbl);
		
		overwrite_cbx = new JComboBox();
		overwrite_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		overwrite_cbx.setModel(new DefaultComboBoxModel(new String[] {"Never", "Always", "Older", "Diff"}));
		overwrite_cbx.setBackground(Color.WHITE);
		overwrite_cbx.setBounds(123, 165, 200, 24);
		mainPanel.add(overwrite_cbx);
		
		JLabel minRead_lbl = new JLabel("MIN-Read Length : ");
		minRead_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minRead_lbl.setBounds(30, 260, 120, 15);
		mainPanel.add(minRead_lbl);
		
		minRead_txt = new JTextField();
		minRead_txt.setColumns(10);
		minRead_txt.setBounds(220, 256, 185, 25);
		mainPanel.add(minRead_txt);
		
		JLabel alignUn_lbl = new JLabel("Aligned or unaligned reads : ");
		alignUn_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignUn_lbl.setBounds(30, 295, 200, 15);
		mainPanel.add(alignUn_lbl);
		
		alignUn_cbx = new JComboBox();
		alignUn_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignUn_cbx.setModel(new DefaultComboBoxModel(new String[] {"NotUse", "Both", "Aligned", "Unaligned"}));
		alignUn_cbx.setBackground(Color.WHITE);
		alignUn_cbx.setBounds(220, 290, 185, 24);
		mainPanel.add(alignUn_cbx);
		
		JLabel qualityConvert_lbl = new JLabel("Quality conversion (offset) : ");
		qualityConvert_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		qualityConvert_lbl.setBounds(30, 330, 200, 15);
		mainPanel.add(qualityConvert_lbl);
		
		qualityConvert_cbx = new JComboBox();
		qualityConvert_cbx.setModel(new DefaultComboBoxModel(new String[] {"33 : Sanger / Illumina 1.9", "64 : Illumina 1.5"}));
		qualityConvert_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		qualityConvert_cbx.setBackground(Color.WHITE);
		qualityConvert_cbx.setBounds(220, 324, 185, 24);
		mainPanel.add(qualityConvert_cbx);
		
		JLabel biologicalR_lbl = new JLabel("Dump biological reads(Only) : ");
		biologicalR_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		biologicalR_lbl.setBounds(30, 365, 200, 15);
		mainPanel.add(biologicalR_lbl);
		
		ButtonGroup bg = new ButtonGroup();
		
		biologicalYes_rdb = new JRadioButton("Yes");
		biologicalYes_rdb.setFont(new Font("Dialog", Font.PLAIN, 12));
		biologicalYes_rdb.setBackground(Color.WHITE);
		biologicalYes_rdb.setBounds(220, 361, 60, 23);
		bg.add(biologicalYes_rdb);
		
		biologicalNo_rdb = new JRadioButton("No");
		biologicalNo_rdb.setFont(new Font("Dialog", Font.PLAIN, 12));
		biologicalNo_rdb.setBackground(Color.WHITE);
		biologicalNo_rdb.setBounds(287, 361, 45, 23);
		bg.add(biologicalNo_rdb);
		
		mainPanel.add(biologicalYes_rdb);
		mainPanel.add(biologicalNo_rdb);
		
		JLabel maxRate_img = new JLabel("");
		maxRate_img.setHorizontalAlignment(SwingConstants.LEFT);
		maxRate_img.setToolTipText("Max transfer rate.");
		maxRate_img.setIcon(info_img);
		maxRate_img.setBounds(332, 97, 17, 20);
		mainPanel.add(maxRate_img);
		
		JLabel minRate_img = new JLabel("");
		minRate_img.setToolTipText("Min transfer rate.");
		minRate_img.setIcon(info_img);
		minRate_img.setHorizontalAlignment(SwingConstants.LEFT);
		minRate_img.setBounds(332, 132, 17, 20);
		mainPanel.add(minRate_img);
		
		JLabel overwrite_img = new JLabel("");
		overwrite_img.setToolTipText("Overwrite method.");
		overwrite_img.setIcon(info_img);
		overwrite_img.setHorizontalAlignment(SwingConstants.LEFT);
		overwrite_img.setBounds(332, 167, 17, 20);
		mainPanel.add(overwrite_img);
		
		JLabel minRead_img = new JLabel("");
		minRead_img.setToolTipText("Filter by sequence length >= <len>");
		minRead_img.setIcon(info_img);
		minRead_img.setHorizontalAlignment(SwingConstants.LEFT);
		minRead_img.setBounds(411, 258, 17, 20);
		mainPanel.add(minRead_img);
		
		JLabel alignUn_img = new JLabel("");
		alignUn_img.setToolTipText("<html>Aligned : Dump only aligned sequences.<br>UnAligned : Dump only unaligned sequences.</html>");
		alignUn_img.setIcon(info_img);
		alignUn_img.setHorizontalAlignment(SwingConstants.LEFT);
		alignUn_img.setBounds(411, 292, 17, 20);
		mainPanel.add(alignUn_img);
		
		JLabel qualityConvert_img = new JLabel("");
		qualityConvert_img.setToolTipText("Offset to use for quality conversion, default is 33");
		qualityConvert_img.setIcon(info_img);
		qualityConvert_img.setHorizontalAlignment(SwingConstants.LEFT);
		qualityConvert_img.setBounds(411, 326, 17, 20);
		mainPanel.add(qualityConvert_img);
		
		JLabel biologicalR_img = new JLabel("");
		biologicalR_img.setToolTipText("Dump only biological reads : Skip technical");
		biologicalR_img.setIcon(info_img);
		biologicalR_img.setHorizontalAlignment(SwingConstants.LEFT);
		biologicalR_img.setBounds(335, 363, 17, 20);
		mainPanel.add(biologicalR_img);
		
		init();
	}
	
	public JPanel getPanel(){
		return mainPanel;
	}
	
	public String getAsperaOption(){
		String option = "";
		if(cf.checkInteger(maxRate_txt.getText().toString()) && !maxRate_txt.getText().toString().equals("Only Integer")){
			option = "-l "+maxRate_txt.getText().toString()+maxRate_cbx.getSelectedItem().toString().charAt(0);
		}
		if(cf.checkInteger(minRate_txt.getText().toString()) && !minRate_txt.getText().toString().equals("Only Integer")){
			option = option + " -m "+minRate_txt.getText().toString()+minRate_cbx.getSelectedItem().toString().charAt(0);
		}
		return option+" --overwrite="+overwrite_cbx.getSelectedItem().toString();
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
