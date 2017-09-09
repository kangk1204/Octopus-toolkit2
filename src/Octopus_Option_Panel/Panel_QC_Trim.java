package Octopus_Option_Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;

public class Panel_QC_Trim {
	private CommonFunc cf;
	private Icon info_img = new ImageIcon(Panel_QC_Trim.class.getResource("/Octopus_Icon/ToolTip.png"));
	private JPanel mainPanel = new JPanel();
	private JComboBox kmer_cbx;
	private JComboBox available_Mem_cbx;
	private JRadioButton rdbtnIllumina;
	private JRadioButton rdbtnUser;
	private JRadioButton rdbtnNo;
	private JLabel minLength_lbl;
	private JTextField minLength_txt;
	private JLabel minLength_img;
	private JLabel illumina_seq_img;
	private JPanel sliding_panel;
	private JTextField winSize_txt;
	private JTextField avgQual_txt;
	private JPanel trimQuality_panel;
	private JTextField leading_txt;
	private JTextField trailing_txt;
	private JPanel cutRead_panel;
	private JTextField headcrop_txt;
	private JTextField tailcrop_txt;
	private JPanel illumina_panel;
	private JLabel adapt_lbl;
	private JComboBox adaptSeq_cbx;
	private JTextField seedMismatch_txt;
	private JTextField palindromeThsh_txt;
	private JTextField simpeThsh_txt;
	private JTextField userAdapt_txt;
	private JButton open_Btn;
	private boolean adapter_flag;
	private String userAdaptPath;
		
	public void init(){
		kmer_cbx.setSelectedIndex(5);
		winSize_txt.setText("4");
		avgQual_txt.setText("15");
		leading_txt.setText("3");
		trailing_txt.setText("3");
		headcrop_txt.setText("4");
		tailcrop_txt.setText("20");
		minLength_txt.setText("20");
		adaptSeq_cbx.setSelectedIndex(0);
		seedMismatch_txt.setText("2");
		palindromeThsh_txt.setText("30");
		simpeThsh_txt.setText("30");
		userAdapt_txt.setText("");
		adapter_flag = false;
		
		rdbtnNo.setSelected(true);
		sliding_panel.setBounds(20, 250, 205, 90);
		trimQuality_panel.setBounds(235, 250, 205, 90);
		cutRead_panel.setBounds(20, 355, 420, 55);
		minLength_lbl.setBounds(30, 430, 250, 15);
		minLength_txt.setBounds(272, 425, 114, 25);
		minLength_img.setBounds(392,427,17,20);
		mainPanel.remove(illumina_panel);

		userAdaptPath = "";
	}
	
	public boolean checkChangeValue(){
		
		if(kmer_cbx.getSelectedIndex() != 5){
			return true;
		}
		
		if(available_Mem_cbx.getSelectedIndex() != 0){
			return true;
		}
		
		if(rdbtnIllumina.isSelected()){
			return true;
		}
		
		if(rdbtnUser.isSelected()){
			if(adapter_flag == false){
				JOptionPane.showMessageDialog(null, "Adapter sequence file is not selected.", "Full parameter",JOptionPane.WARNING_MESSAGE);
				return false;
			}else{
				return true;
			}
		}
		
		if(!winSize_txt.getText().equals("4")){
			return true;
		}
		
		if(!avgQual_txt.getText().equals("15")){
			return true;
		}
		
		if(!leading_txt.getText().equals("3")){
			return true;
		}
		
		if(!trailing_txt.getText().equals("3")){
			return true;
		}
		
		if(!headcrop_txt.getText().equals("4")){
			return true;
		}
		
		if(!tailcrop_txt.getText().equals("20")){
			return true;
		}
		
		if(!minLength_txt.getText().equals("20")){
			return true;
		}
		
		
			
		return false;
	}
	
	public Panel_QC_Trim(DataSet ds){
		cf = new CommonFunc(ds);
		mainPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(12, 23, 452, 590);
		mainPanel.setLayout(null);
		
		JLabel first_lbl = new JLabel("Determined Quality of DNA sequence");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 300, 15);
		mainPanel.add(first_lbl);
		
		JLabel second_lbl = new JLabel("Trimmed DNA sequence data");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 190, 250, 15);
		mainPanel.add(second_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainPanel.add(separator);
		
		JLabel head_lbl = new JLabel("Quality Check & Trimming");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(230, 20, 240, 15);
		mainPanel.add(head_lbl);
		
		JLabel available_Mem_lbl = new JLabel("Allocated memory : ");
		available_Mem_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		available_Mem_lbl.setBounds(30, 130, 150, 15);
		mainPanel.add(available_Mem_lbl);
		
		JLabel kmer_lbl = new JLabel("K-Mer : ");
		kmer_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		kmer_lbl.setBounds(30, 95, 90, 15);
		mainPanel.add(kmer_lbl);
		
		ButtonGroup bg = new ButtonGroup();
		
		available_Mem_cbx = new JComboBox();
		available_Mem_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		available_Mem_cbx.setMaximumRowCount(15);
		available_Mem_cbx.setBackground(Color.WHITE);
		available_Mem_cbx.setBounds(180, 125, 150, 24);
		mainPanel.add(available_Mem_cbx);
		
		JLabel avali_lbl = new JLabel();
		avali_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		avali_lbl.setBounds(180, 157, 250, 15);
		mainPanel.add(avali_lbl);
		
		int ram[] = {1,2,4,8,16,32,64,128};
		
		String cmd[] = {"free","-g"};
		String tmpMem[] = cf.shellCmd(cmd).replaceAll("\n", " ").split(" ");
		int usable_mem = 1;
		
		for(int i = 0; i < tmpMem.length; i++){
			if(tmpMem[i].equals("Mem:")){
				usable_mem = Integer.parseInt(tmpMem[i+6]);
				for(int j = 0; j < ram.length; j++){
					if(ram[j] <= usable_mem)
						available_Mem_cbx.addItem(ram[j]);
				}
				break;
			}
		}
		
		avali_lbl.setText("(Measured available memory : "+usable_mem+" Gb)");
		
		kmer_cbx = new JComboBox();
		kmer_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		kmer_cbx.setModel(new DefaultComboBoxModel(new String[] {"2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		kmer_cbx.setMaximumRowCount(15);
		kmer_cbx.setBackground(Color.WHITE);
		kmer_cbx.setBounds(180, 90, 150, 24);
		mainPanel.add(kmer_cbx);
		
		minLength_lbl = new JLabel("Minimum length of reads to be kept : ");
		minLength_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minLength_lbl.setBounds(30, 430, 240, 15);
		mainPanel.add(minLength_lbl);
		
		minLength_txt = new JTextField();
		minLength_txt.setBounds(272, 425, 114, 25);
		mainPanel.add(minLength_txt);
		minLength_txt.setColumns(10);
		
		JLabel wouldYouUse_lbl = new JLabel("Which adapter would you like to use?");
		wouldYouUse_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		wouldYouUse_lbl.setBounds(30, 220, 232, 15);
		mainPanel.add(wouldYouUse_lbl);
		
		rdbtnIllumina = new JRadioButton("Illumina");
		rdbtnIllumina.setFont(new Font("Dialog", Font.PLAIN, 12));
		rdbtnIllumina.setBackground(Color.WHITE);
		rdbtnIllumina.setBounds(265, 216, 72, 23);
		bg.add(rdbtnIllumina);
		
		rdbtnUser = new JRadioButton("User");
		rdbtnUser.setFont(new Font("Dialog", Font.PLAIN, 12));
		rdbtnUser.setBackground(Color.WHITE);
		rdbtnUser.setBounds(338, 216, 60, 23);
		bg.add(rdbtnUser);
		
		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setFont(new Font("Dialog", Font.PLAIN, 12));
		rdbtnNo.setBackground(Color.WHITE);
		rdbtnNo.setBounds(400, 216, 50, 23);
		bg.add(rdbtnNo);
		
		mainPanel.add(rdbtnIllumina);
		mainPanel.add(rdbtnUser);
		mainPanel.add(rdbtnNo);
		
		JLabel kmer_img = new JLabel("");
		kmer_img.setToolTipText("<html>Specifies the length of Kmer to look for in the Kmer content module.<br>Specified Kmer length must be between 2 and 10.<br>Default length is 7 if not specified.</html>");
		kmer_img.setIcon(info_img);
		kmer_img.setHorizontalAlignment(SwingConstants.LEFT);
		kmer_img.setBounds(339, 92, 17, 20);
		mainPanel.add(kmer_img);
		
		JLabel available_Mem_img = new JLabel("");
		available_Mem_img.setIcon(info_img);
		available_Mem_img.setToolTipText("<html>Not total memory of user's computer.<br>Allocated memory : Available memory.</html>");
		available_Mem_img.setHorizontalAlignment(SwingConstants.LEFT);
		available_Mem_img.setBounds(339, 127, 17, 20);
		mainPanel.add(available_Mem_img);
		
		minLength_img = new JLabel("");
		minLength_img.setIcon(info_img);
		minLength_img.setToolTipText("Drop the read if it is below a specified length.");
		minLength_img.setHorizontalAlignment(SwingConstants.LEFT);
		minLength_img.setBounds(392, 427, 17, 20);
		mainPanel.add(minLength_img);
		
		rdbtnIllumina.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(rdbtnIllumina.isSelected()){
					sliding_panel.setBounds(20, 390, 205, 90);
					trimQuality_panel.setBounds(235, 390, 205, 90);
					cutRead_panel.setBounds(20, 485, 420, 55);
					minLength_lbl.setBounds(30, 550, 250, 15);
					minLength_txt.setBounds(272, 545, 114, 25);
					minLength_img.setBounds(392,547,17,20);

					adapt_lbl.setText("Illumina adapter Sequence: ");
					illumina_panel.add(adapt_lbl);
					illumina_panel.setBorder(new TitledBorder(null, "Illumina clip", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					illumina_panel.add(adaptSeq_cbx);
					illumina_panel.add(illumina_seq_img);
					illumina_panel.remove(userAdapt_txt);
					illumina_panel.remove(open_Btn);
					
					mainPanel.add(illumina_panel);
					mainPanel.repaint();
				}
			}
		});
		
		rdbtnUser.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(rdbtnUser.isSelected()){
					
					sliding_panel.setBounds(20, 390, 205, 90);
					trimQuality_panel.setBounds(235, 390, 205, 90);
					cutRead_panel.setBounds(20, 485, 420, 55);
					minLength_lbl.setBounds(30, 550, 250, 15);
					minLength_txt.setBounds(272, 545, 114, 25);
					minLength_img.setBounds(392,547,17,20);
					
					adapt_lbl.setText("User adapter sequence : ");
					illumina_panel.add(adapt_lbl);
					illumina_panel.setBorder(new TitledBorder(null, "User adapter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					illumina_panel.add(userAdapt_txt);
					illumina_panel.add(open_Btn);
					illumina_panel.remove(adaptSeq_cbx);
					illumina_panel.remove(illumina_seq_img);
					
					mainPanel.add(illumina_panel);
					mainPanel.repaint();
				}
			}
		});
		rdbtnNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNo.isSelected()) {
					sliding_panel.setBounds(20, 250, 205, 90);
					trimQuality_panel.setBounds(235, 250, 205, 90);
					cutRead_panel.setBounds(20, 355, 420, 55);
					minLength_lbl.setBounds(30, 430, 250, 15);
					minLength_txt.setBounds(272, 425, 114, 25);
					minLength_img.setBounds(392,427,17,20);
					mainPanel.remove(illumina_panel);
				}
			}
		});
		
		// make Sub Panel
		makeSlidingPanel();
		makeTrimQuality();
		makeCutRead();
		makeIllumina();
		init();
	}
	public void makeSlidingPanel(){
		sliding_panel = new JPanel();
		sliding_panel.setToolTipText("Perform a sliding window trimming, cutting once the average quality within the window falls below a threshold.");
		sliding_panel.setBackground(Color.WHITE);
		sliding_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Sliding Windows", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		sliding_panel.setBounds(20, 250, 205, 90);
		mainPanel.add(sliding_panel);
		sliding_panel.setLayout(null);
		
		JLabel winSize_lbl = new JLabel("Window size : ");
		winSize_lbl.setBounds(15, 25, 120, 15);
		sliding_panel.add(winSize_lbl);
		winSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel avgQual_lbl = new JLabel("Average quality : ");
		avgQual_lbl.setBounds(15, 55, 120, 15);
		sliding_panel.add(avgQual_lbl);
		avgQual_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		winSize_txt = new JTextField();
		winSize_txt.setBounds(125, 20, 65, 25);
		sliding_panel.add(winSize_txt);
		winSize_txt.setColumns(10);
		
		avgQual_txt = new JTextField();
		avgQual_txt.setColumns(10);
		avgQual_txt.setBounds(125, 52, 65, 25);
		sliding_panel.add(avgQual_txt);
	}
	public void makeTrimQuality(){
		trimQuality_panel = new JPanel();
		trimQuality_panel.setToolTipText("<html>LEADING: Cut bases off the start of a read, if below a threshold quality.<br>TRAILING: Cut bases off the end of a read, if below a threshold quality.</html>");
		trimQuality_panel.setLayout(null);
		trimQuality_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Trim quality threshold", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		trimQuality_panel.setBackground(Color.WHITE);
		trimQuality_panel.setBounds(235, 250, 205, 90);
		mainPanel.add(trimQuality_panel);
		
		JLabel leading_lbl = new JLabel("LEADING : ");
		leading_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		leading_lbl.setBounds(20, 25, 70, 15);
		trimQuality_panel.add(leading_lbl);
		
		JLabel trailing_lbl = new JLabel("TRAILING : ");
		trailing_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		trailing_lbl.setBounds(20, 55, 70, 15);
		trimQuality_panel.add(trailing_lbl);
		
		leading_txt = new JTextField();
		leading_txt.setColumns(10);
		leading_txt.setBounds(100, 20, 80, 25);
		trimQuality_panel.add(leading_txt);
		
		trailing_txt = new JTextField();
		trailing_txt.setColumns(10);
		trailing_txt.setBounds(100, 52, 80, 25);
		trimQuality_panel.add(trailing_txt);
	}
	public void makeCutRead(){
		cutRead_panel = new JPanel();
		cutRead_panel.setToolTipText("<html>HEADCROP: Cut the specified number of bases from the start of the read.<br>TAILCROP(CROP): Cut the read to a specified length.</html>");
		cutRead_panel.setLayout(null);
		cutRead_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Cut the read to specified length", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		cutRead_panel.setBackground(Color.WHITE);
		cutRead_panel.setBounds(20, 355, 420, 55);
		mainPanel.add(cutRead_panel);
		
		JLabel headcrop_lbl = new JLabel("HEADCROP  : ");
		headcrop_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		headcrop_lbl.setBounds(15, 25, 90, 15);
		cutRead_panel.add(headcrop_lbl);
		
		JLabel tailcrop_lbl = new JLabel("TAILCROP : ");
		tailcrop_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		tailcrop_lbl.setBounds(208, 25, 80, 15);
		cutRead_panel.add(tailcrop_lbl);
		
		headcrop_txt = new JTextField();
		headcrop_txt.setColumns(10);
		headcrop_txt.setBounds(110, 20, 75, 25);
		cutRead_panel.add(headcrop_txt);
		
		tailcrop_txt = new JTextField();
		tailcrop_txt.setColumns(10);
		tailcrop_txt.setBounds(290, 20, 75, 25);
		cutRead_panel.add(tailcrop_txt);
		
	}
	public void makeIllumina(){
		illumina_panel = new JPanel();
		illumina_panel.setBackground(Color.WHITE);
		illumina_panel.setBorder(new TitledBorder(null, "Illumina clip", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		illumina_panel.setBounds(24, 240, 406, 140);
		illumina_panel.setLayout(null);
		
		adapt_lbl = new JLabel("Illumina adapter Sequence: ");
		adapt_lbl.setBounds(10, 20, 250, 15);
		adapt_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		adaptSeq_cbx = new JComboBox();
		adaptSeq_cbx.setBounds(185, 15, 170, 24);
		adaptSeq_cbx.setModel(new DefaultComboBoxModel(new String[] {"NexteraPE-PE.fa", "TruSeq2-PE.fa", "TruSeq2-SE.fa", "TruSeq3-PE.fa", "TruSeq3-SE.fa", "TruSeq3-PE-2.fa"}));
		adaptSeq_cbx.setBackground(Color.WHITE);
		adaptSeq_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		userAdapt_txt = new JTextField();
		userAdapt_txt.setBounds(185, 15, 135, 24);
		illumina_panel.add(userAdapt_txt);
		userAdapt_txt.setColumns(30);
		
		open_Btn = new JButton("OPEN");
		open_Btn.setBounds(325, 13, 75, 25);
		
		JLabel seedMismatch_lbl = new JLabel("Seed mismatches : ");
		seedMismatch_lbl.setBounds(10, 50, 125, 15);
		illumina_panel.add(seedMismatch_lbl);
		seedMismatch_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		seedMismatch_txt = new JTextField();
		seedMismatch_txt.setBounds(185, 45, 170, 25);
		illumina_panel.add(seedMismatch_txt);
		seedMismatch_txt.setColumns(10);
		
		JLabel palindromeThsh_lbl = new JLabel("Palindrome clip threshold : ");
		palindromeThsh_lbl.setBounds(10, 80, 175, 15);
		illumina_panel.add(palindromeThsh_lbl);
		palindromeThsh_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		palindromeThsh_txt = new JTextField();
		palindromeThsh_txt.setBounds(185, 75, 170, 25);
		illumina_panel.add(palindromeThsh_txt);
		palindromeThsh_txt.setColumns(10);
		
		JLabel simpeThsh_lbl = new JLabel("Simple clip threshold : ");
		simpeThsh_lbl.setBounds(10, 110, 175, 15);
		illumina_panel.add(simpeThsh_lbl);
		simpeThsh_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		simpeThsh_txt = new JTextField();
		simpeThsh_txt.setBounds(185, 105, 170, 25);
		illumina_panel.add(simpeThsh_txt);
		simpeThsh_txt.setColumns(10);
		
		illumina_seq_img = new JLabel("");
		illumina_seq_img.setIcon(info_img);
		illumina_seq_img.setToolTipText("<html>Specifies the path to a fasta file containing all the adapters, PCR sequences etc.<br>The naming of the various sequences within this file determines how they are used.</html>");
		illumina_seq_img.setHorizontalAlignment(SwingConstants.LEFT);
		illumina_seq_img.setBounds(360, 15, 17, 20);
		illumina_panel.add(illumina_seq_img);
		
		JLabel seedMismatch_img = new JLabel("");
		seedMismatch_img.setIcon(info_img);
		seedMismatch_img.setToolTipText("Specifies the maximum mismatch count which will still allow a full match to be performed.");
		seedMismatch_img.setHorizontalAlignment(SwingConstants.LEFT);
		seedMismatch_img.setBounds(360, 45, 17, 20);
		illumina_panel.add(seedMismatch_img);
		
		JLabel palindromeThsh_img = new JLabel("");
		palindromeThsh_img.setIcon(info_img);
		palindromeThsh_img.setToolTipText("Specifies how accurate the match between the two 'adapter ligated' reads must be for PE palindrome read alignment.");
		palindromeThsh_img.setHorizontalAlignment(SwingConstants.LEFT);
		palindromeThsh_img.setBounds(360, 75, 17, 20);
		illumina_panel.add(palindromeThsh_img);
		
		JLabel simpeThsh_img = new JLabel("");
		simpeThsh_img.setIcon(info_img);
		simpeThsh_img.setToolTipText("Specifies how accurate the match between any adapter etc. sequence must be against a read.");
		simpeThsh_img.setHorizontalAlignment(SwingConstants.LEFT);
		simpeThsh_img.setBounds(360, 105, 17, 20);
		illumina_panel.add(simpeThsh_img);
		
		open_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String path = cf.openDialog_File(userAdapt_txt,"Full_Parameter");
				if(userAdapt_txt.getText().length() > 5 && userAdapt_txt.getText().substring(0,6).equals("File :")){
					adapter_flag = true;
					userAdaptPath = path;
				}else{
					adapter_flag = false;
				}
			}
		});

	}
	
	public JPanel getPanel() {
		return mainPanel;
	}

	public int availableMem() {
		return Integer.parseInt(available_Mem_cbx.getSelectedItem().toString());
	}
	
	public String getFastQCOption(){
		String option = "";
		if(!kmer_cbx.getSelectedItem().equals("7")){
			option = "-k "+kmer_cbx.getSelectedItem().toString();	
		}
		return option;
	}
	public String getTrimmomatic(){
		String option = "";
		String path = System.getProperty("user.dir") + "/Octopus-toolkit/Tools/Trimmomatic/adapters/";
		if(rdbtnIllumina.isSelected()){
			if(cf.checkInteger(seedMismatch_txt.getText().toString()) && cf.checkInteger(palindromeThsh_txt.getText().toString()) && cf.checkInteger(simpeThsh_txt.getText().toString())){
				String tmp = "ILLUMINACLIP:"+path+adaptSeq_cbx.getSelectedItem().toString()+":";
				tmp = tmp+seedMismatch_txt.getText().toString()+":"+palindromeThsh_txt.getText().toString()+":"+simpeThsh_txt.getText().toString();
				option = option + " "+tmp;
			}
		}else if(rdbtnUser.isSelected()){
			if (adapter_flag == true) {
				if (cf.checkInteger(seedMismatch_txt.getText().toString())
						&& cf.checkInteger(palindromeThsh_txt.getText().toString())
						&& cf.checkInteger(simpeThsh_txt.getText().toString())) {
					String tmp = "ILLUMINACLIP:" + userAdaptPath + ":";
					tmp = tmp + seedMismatch_txt.getText().toString() + ":" + palindromeThsh_txt.getText().toString()
							+ ":" + simpeThsh_txt.getText().toString();
					option = option + " " + tmp;
				}
			}
		}
		if(cf.checkInteger(winSize_txt.getText().toString()) && cf.checkInteger(avgQual_txt.getText().toString())){
			option = option + " SLIDINGWINDOW:"+winSize_txt.getText().toString()+":"+avgQual_txt.getText().toString();
		}
		if(cf.checkInteger(leading_txt.getText().toString())){
			option = option + " LEADING:"+leading_txt.getText().toString();
		}
		if(cf.checkInteger(trailing_txt.getText().toString())){
			option = option + " TRAILING:"+trailing_txt.getText().toString();
		}
		if(cf.checkInteger(headcrop_txt.getText().toString())){
			option = option + " HEADCROP:"+headcrop_txt.getText().toString();
		}
		if(cf.checkInteger(tailcrop_txt.getText().toString())){
			option = option + " CROP:"+tailcrop_txt.getText().toString();
		}
		if(cf.checkInteger(minLength_txt.getText().toString())){
			option = option + " MINLEN:"+minLength_txt.getText().toString();
		}
		return option;
	}

}
