package Octopus_Option_Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class Panel_Visualization {
	private CommonFunc cf;
	private Icon info_img = new ImageIcon(Panel_Visualization.class.getResource("/Octopus_Icon/ToolTip.png"));
	private JPanel mainTagPanel = new JPanel();
	private JPanel mainBwPanel = new JPanel();
	private JTextField fragLen_txt;
	private JTextField tbp_txt;
	private JTextField minLen_txt;
	private JTextField maxLen_txt;
	private JTextField fsize_txt;
	private JComboBox fragLen2_cbx;
	private JTextField fragLen2_txt;
	private JTextField resolution_txt;
	private JTextField norTotalNum_txt;
	private JTextField standLen_txt;
	private JTextField minTag_txt;
	private JTextField maxTag_txt;
	private JRadioButton flipYes_rb;
	private JRadioButton flipNo_rb;
	
	private JRadioButton negPlotYes_rb;
	private JRadioButton negPlotNo_rb;
	private JComboBox resolution_cbx; 
	
	public Panel_Visualization(DataSet ds){
		cf = new CommonFunc(ds);
		makeTagpanel();
		makeBwpanel();
		initTag();
		initBw();
		
	}
	
	public void initTag(){
		fragLen_txt.setText("Int or given");
		tbp_txt.setText("0");
		minLen_txt.setText("0");
		maxLen_txt.setText("0");
	}
	public void initBw(){
		fsize_txt.setText("1e10");
		fragLen2_cbx.setSelectedIndex(0);
		fragLen2_txt.setText("1e10");
		resolution_txt.setText("1");
		resolution_cbx.setSelectedIndex(0);
		resolution_cbx.setEnabled(false);
		minTag_txt.setText("0");
		maxTag_txt.setText("0");
		negPlotNo_rb.setSelected(true);
		norTotalNum_txt.setText("1e7");
		standLen_txt.setText("100");
	}
	
	public void makeTagpanel(){
		mainTagPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainTagPanel.setBackground(Color.WHITE);
		mainTagPanel.setBounds(12, 23, 452, 590);
		mainTagPanel.setLayout(null);
		
		
		JLabel first_lbl = new JLabel("Create tag directory");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 180, 15);
		mainTagPanel.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainTagPanel.add(separator);
		
		JLabel head_lbl = new JLabel("Visualization");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(330, 20, 110, 15);
		mainTagPanel.add(head_lbl);
		
		JLabel fragLen_lbl = new JLabel("Fragment-Length : ");
		fragLen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLen_lbl.setBounds(22, 100, 120, 15);
		mainTagPanel.add(fragLen_lbl);
		
		fragLen_txt = new JTextField();
		fragLen_txt.setColumns(10);
		fragLen_txt.setBounds(169, 95, 90, 25);
		mainTagPanel.add(fragLen_txt);
		
		JLabel flagLen_info_lbl = new JLabel("(given : read lengths)");
		flagLen_info_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		flagLen_info_lbl.setBounds(265, 100, 140, 15);
		mainTagPanel.add(flagLen_info_lbl);
		
		JLabel tbp_lbl = new JLabel("Maximum tags per bp : ");
		tbp_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		tbp_lbl.setBounds(22, 135, 150, 15);
		mainTagPanel.add(tbp_lbl);
		
		tbp_txt = new JTextField();
		tbp_txt.setColumns(10);
		tbp_txt.setBounds(169, 130, 90, 25);
		mainTagPanel.add(tbp_txt);
		
		JPanel keepReadLen_panel = new JPanel();
		keepReadLen_panel.setToolTipText("Filter reads with lengths outside this range.");
		keepReadLen_panel.setLayout(null);
		keepReadLen_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Length of the read to keep", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		keepReadLen_panel.setBackground(Color.WHITE);
		keepReadLen_panel.setBounds(12, 210, 420, 55);
		mainTagPanel.add(keepReadLen_panel);
		
		JLabel minLen_lbl = new JLabel("MIN-LEN  : ");
		minLen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minLen_lbl.setBounds(15, 25, 90, 15);
		keepReadLen_panel.add(minLen_lbl);
		
		JLabel maxLen_lbl = new JLabel("MAX-LEN : ");
		maxLen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxLen_lbl.setBounds(208, 25, 80, 15);
		keepReadLen_panel.add(maxLen_lbl);
		
		minLen_txt = new JTextField();
		minLen_txt.setColumns(10);
		minLen_txt.setBounds(110, 20, 75, 25);
		keepReadLen_panel.add(minLen_txt);
		
		maxLen_txt = new JTextField();
		maxLen_txt.setColumns(10);
		maxLen_txt.setBounds(290, 20, 75, 25);
		keepReadLen_panel.add(maxLen_txt);
		
		JLabel flip_lbl = new JLabel("Flip the strands of each read : ");
		flip_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		flip_lbl.setBounds(22, 170, 200, 15);
		mainTagPanel.add(flip_lbl);
		
		ButtonGroup bg = new ButtonGroup();
		
		flipYes_rb = new JRadioButton("Yes");
		flipYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		flipYes_rb.setBackground(Color.WHITE);
		flipYes_rb.setBounds(230, 168, 53, 23);
		mainTagPanel.add(flipYes_rb);
		
		flipNo_rb = new JRadioButton("No");
		flipNo_rb.setSelected(true);
		flipNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		flipNo_rb.setBackground(Color.WHITE);
		flipNo_rb.setBounds(290, 168, 45, 23);
		mainTagPanel.add(flipNo_rb);
		
		bg.add(flipYes_rb);
		bg.add(flipNo_rb);
		
		JLabel fragLen_img = new JLabel("");
		fragLen_img.setToolTipText("<html>Set estimated fragment length - given: use read lengths)<br>By default treats the sample as a single read ChIP-Seq experiment <int | given></html>");
		fragLen_img.setIcon(info_img);
		fragLen_img.setHorizontalAlignment(SwingConstants.LEFT);
		fragLen_img.setBounds(408, 98, 17, 20);
		mainTagPanel.add(fragLen_img);
		
		JLabel tbp_img = new JLabel("");
		tbp_img.setIcon(info_img);
		tbp_img.setToolTipText("Maximum tags per bp, default: no maximum.");
		tbp_img.setHorizontalAlignment(SwingConstants.LEFT);
		tbp_img.setBounds(265, 132, 17, 20);
		mainTagPanel.add(tbp_img);
		
		JLabel flip_img = new JLabel("");
		flip_img.setIcon(info_img);
		flip_img.setToolTipText("Flip strand of each read, i.e. might want to use with some RNA-seq.");
		flip_img.setHorizontalAlignment(SwingConstants.LEFT);
		flip_img.setBounds(341, 170, 17, 20);
		mainTagPanel.add(flip_img);
	}
	
	public void makeBwpanel(){
		
		mainBwPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainBwPanel.setBackground(Color.WHITE);
		mainBwPanel.setBounds(12, 23, 452, 590);
		mainBwPanel.setLayout(null);
		
		JLabel first_lbl = new JLabel("Make visualization data (bigWig)");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 260, 15);
		mainBwPanel.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainBwPanel.add(separator);
		
		JLabel head_lbl = new JLabel("Visualization");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(330, 20, 110, 15);
		mainBwPanel.add(head_lbl);
		
		JLabel fsize_lbl = new JLabel("Size of the bigwig files : ");
		fsize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fsize_lbl.setBounds(22, 95, 150, 15);
		mainBwPanel.add(fsize_lbl);
		
		fsize_txt = new JTextField();
		fsize_txt.setColumns(10);
		fsize_txt.setBounds(180, 90, 90, 25);
		mainBwPanel.add(fsize_txt);
		
		JLabel fragLen2_lbl = new JLabel("Fragment Length : ");
		fragLen2_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLen2_lbl.setBounds(22, 128, 150, 15);
		mainBwPanel.add(fragLen2_lbl);
		
		fragLen2_txt = new JTextField();
		fragLen2_txt.setEnabled(false);
		fragLen2_txt.setColumns(10);
		fragLen2_txt.setBounds(280, 123, 90, 25);
		mainBwPanel.add(fragLen2_txt);
		
		fragLen2_cbx = new JComboBox();
		fragLen2_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLen2_cbx.setBackground(Color.WHITE);
		fragLen2_cbx.setModel(new DefaultComboBoxModel(new String[] {"auto", "given", "Other"}));
		fragLen2_cbx.setBounds(180, 123, 90, 24);
		mainBwPanel.add(fragLen2_cbx);
		
		JLabel resolution_lbl = new JLabel("Resolution : ");
		resolution_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		resolution_lbl.setBounds(22, 161, 80, 15);
		mainBwPanel.add(resolution_lbl);
		
		resolution_txt = new JTextField();
		resolution_txt.setColumns(10);
		resolution_txt.setBounds(180, 156, 90, 25);
		mainBwPanel.add(resolution_txt);
		
		resolution_cbx = new JComboBox();
		resolution_cbx.setEnabled(false);
		resolution_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		resolution_cbx.setModel(new DefaultComboBoxModel(new String[] {"Max coverage", "Avg coverage"}));
		resolution_cbx.setBackground(Color.WHITE);
		resolution_cbx.setBounds(280, 156, 125, 24);
		mainBwPanel.add(resolution_cbx);
		
		JLabel norTotalNum_lbl = new JLabel("Normalize the total number of reads : ");
		norTotalNum_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		norTotalNum_lbl.setBounds(22, 353, 240, 15);
		mainBwPanel.add(norTotalNum_lbl);
		
		norTotalNum_txt = new JTextField();
		norTotalNum_txt.setColumns(10);
		norTotalNum_txt.setBounds(280, 348, 90, 25);
		mainBwPanel.add(norTotalNum_txt);
		
		JLabel standLen_lbl = new JLabel("Set the standand length (Off = 0) : ");
		standLen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		standLen_lbl.setBounds(22, 386, 230, 15);
		mainBwPanel.add(standLen_lbl);
		
		standLen_txt = new JTextField();
		standLen_txt.setColumns(10);
		standLen_txt.setBounds(280, 381, 90, 25);
		mainBwPanel.add(standLen_txt);
		
		JLabel second_lbl = new JLabel("Normalization");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 314, 120, 15);
		mainBwPanel.add(second_lbl);
		
		JPanel tagCount_Panel = new JPanel();
		tagCount_Panel.setToolTipText("<html>Maximum tags per bp to count, default: no limit<br>Minimum tags per bp to count, default: no limit</html>");
		tagCount_Panel.setLayout(null);
		tagCount_Panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Tags per bp to count", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		tagCount_Panel.setBackground(Color.WHITE);
		tagCount_Panel.setBounds(12, 190, 420, 55);
		mainBwPanel.add(tagCount_Panel);
		
		JLabel minTag_lbl = new JLabel("MIN-Tag : ");
		minTag_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minTag_lbl.setBounds(15, 25, 90, 15);
		tagCount_Panel.add(minTag_lbl);
		
		JLabel maxTag_lbl = new JLabel("MAX-Tag : ");
		maxTag_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxTag_lbl.setBounds(208, 25, 80, 15);
		tagCount_Panel.add(maxTag_lbl);
		
		minTag_txt = new JTextField();
		minTag_txt.setColumns(10);
		minTag_txt.setBounds(110, 20, 75, 25);
		tagCount_Panel.add(minTag_txt);
		
		maxTag_txt = new JTextField();
		maxTag_txt.setColumns(10);
		maxTag_txt.setBounds(290, 20, 75, 25);
		tagCount_Panel.add(maxTag_txt);
		
		JLabel negPlot_lbl = new JLabel("Plot negative values : ");
		negPlot_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		negPlot_lbl.setBounds(22, 266, 150, 15);
		mainBwPanel.add(negPlot_lbl);
		
		ButtonGroup bg2 = new ButtonGroup();
		
		negPlotYes_rb = new JRadioButton("Yes");
		negPlotYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		negPlotYes_rb.setBackground(Color.WHITE);
		negPlotYes_rb.setBounds(255, 263, 55, 23);
		mainBwPanel.add(negPlotYes_rb);
		
		negPlotNo_rb = new JRadioButton("No");
		negPlotNo_rb.setSelected(true);
		negPlotNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		negPlotNo_rb.setBackground(Color.WHITE);
		negPlotNo_rb.setBounds(310, 263, 45, 23);
		mainBwPanel.add(negPlotNo_rb);
		
		bg2.add(negPlotYes_rb);
		bg2.add(negPlotNo_rb);
		
		ButtonGroup bg3 = new ButtonGroup();
		
		JLabel fsize_img = new JLabel("");
		fsize_img.setIcon(info_img);
		fsize_img.setToolTipText("Size of file, when gzipped, default: 1e10, i.e. no reduction.");
		fsize_img.setHorizontalAlignment(SwingConstants.LEFT);
		fsize_img.setBounds(280, 91, 17, 20);
		mainBwPanel.add(fsize_img);
		
		JLabel fragLen2_img = new JLabel("");
		fragLen2_img.setIcon(info_img);
		fragLen2_img.setToolTipText("Approximate fragment length, default: auto <int | auto | given>");
		fragLen2_img.setHorizontalAlignment(SwingConstants.LEFT);
		fragLen2_img.setBounds(380, 124, 17, 20);
		mainBwPanel.add(fragLen2_img);
		
		JLabel resolution_img = new JLabel("");
		resolution_img.setIcon(info_img);
		resolution_img.setToolTipText("<html>Resolution, in bp, of file, default: 1.<br>Report average coverage if resolution is larger than 1bp, default: max is reported</html>");
		resolution_img.setHorizontalAlignment(SwingConstants.LEFT);
		resolution_img.setBounds(415, 158, 17, 20);
		mainBwPanel.add(resolution_img);
		
		JLabel negPlot_img = new JLabel("");
		negPlot_img.setIcon(info_img);
		negPlot_img.setToolTipText("Plot negative values, i.e. for - strand transcription.");
		negPlot_img.setHorizontalAlignment(SwingConstants.LEFT);
		negPlot_img.setBounds(360, 264, 17, 20);
		mainBwPanel.add(negPlot_img);
		
		JLabel norTotalNum_img = new JLabel("");
		norTotalNum_img.setIcon(info_img);
		norTotalNum_img.setToolTipText("Total number of tags to normalize experiment to, default: 1e7.");
		norTotalNum_img.setHorizontalAlignment(SwingConstants.LEFT);
		norTotalNum_img.setBounds(380, 350, 17, 20);
		mainBwPanel.add(norTotalNum_img);
		
		JLabel standLen_img = new JLabel("");
		standLen_img.setIcon(info_img);
		standLen_img.setToolTipText("Expected length of fragment to normalize to [0=off], default: 100");
		standLen_img.setHorizontalAlignment(SwingConstants.LEFT);
		standLen_img.setBounds(380, 383, 17, 20);
		mainBwPanel.add(standLen_img);
		
		fragLen2_cbx.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(fragLen2_cbx.getSelectedItem().toString().equals("Other")){
					fragLen2_txt.setEnabled(true);
				}else{
					fragLen2_txt.setEnabled(false);
				}
			}
		});
		
		resolution_txt.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(resolution_txt.getText().toString().equals("1")){
					resolution_cbx.setEnabled(false);
				}else{
					resolution_cbx.setEnabled(true);
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
	}
	
	public JPanel getTagPanel() {
		return mainTagPanel;
	}
	public JPanel getBwPanel() {
		return mainBwPanel;
	}
	
	public String getMakeTagDirectoryOption(){
		String option = "";
		
		if(fragLen_txt.getText().toString().toLowerCase().equals("given") || cf.checkInteger(fragLen_txt.getText().toString())){
			option = "-fragLength "+fragLen_txt.getText().toString();
		}
		if(cf.checkInteger(tbp_txt.getText().toString()) && !tbp_txt.getText().toString().equals("0")){
			option = option + " -tbp "+tbp_txt.getText().toString();
		}
		if(flipYes_rb.isSelected()){
			option = option + " -flip";
		}
		if(cf.checkInteger(minLen_txt.getText().toString()) && !minLen_txt.getText().toString().equals("0")){
			option = option + " -minlen "+minLen_txt.getText().toString();
		}
		if(cf.checkInteger(maxLen_txt.getText().toString()) && !maxLen_txt.getText().toString().equals("0")){
			option = option + " -maxlen "+maxLen_txt.getText().toString();
		}
		return option;
	}
	public String getMakeUCSCfileOption(){
		String option = "";
		
		if(!fsize_txt.getText().toString().equals("1e10")){
			option = "-fsize "+fsize_txt.getText().toString();
		}		
		if(fragLen2_cbx.getSelectedItem().toString().equals("Other")){
			option = option+ " -fragLength "+fragLen2_txt.getText().toString();
		}else{
			option = option+ " -fragLength "+fragLen2_cbx.getSelectedItem().toString();
		}
		
		if(!resolution_txt.getText().toString().equals("1")){
			if(cf.checkInteger(resolution_txt.getText().toString())){
				option = option+ " -res "+resolution_txt.getText().toString();
				if(resolution_cbx.getSelectedIndex() == 1){
					option = option+ " -avg";
				}
			}
		}
		
		if(cf.checkInteger(minTag_txt.getText().toString()) && !minTag_txt.getText().toString().equals("0")){
			option = option + " -mintbp "+minTag_txt.getText().toString();
		}
		if(cf.checkInteger(maxTag_txt.getText().toString()) && !maxTag_txt.getText().toString().equals("0")){
			option = option + " -tbp "+maxTag_txt.getText().toString();
		}
		
		if(negPlotYes_rb.isSelected()){
			option = option + " -neg";
		}
		if(!norTotalNum_txt.getText().toString().equals("1e7")){
			option = option + " -norm "+norTotalNum_txt.getText().toString();
		}
		if(cf.checkInteger(standLen_txt.getText().toString()) && ! standLen_txt.getText().toString().equals("100")){
			option  = option + " -normLength "+standLen_txt.getText().toString();
		}
		
		return option;
		
	}
}
