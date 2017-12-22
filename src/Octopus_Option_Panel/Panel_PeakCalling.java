package Octopus_Option_Panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;

public class Panel_PeakCalling{
	private CommonFunc cf;
	private Icon info_img = new ImageIcon(Panel_PeakCalling.class.getResource("/Octopus_Icon/ToolTip.png"));
	private Icon next_img = new ImageIcon(Panel_PeakCalling.class.getResource("/Octopus_Icon/Next.png"));
	private Icon prev_img = new ImageIcon(Panel_PeakCalling.class.getResource("/Octopus_Icon/Previous.png"));
	
	private JPanel mainChIPHistone = new JPanel();
	private JTextField peakSize_txt;
	private JTextField minDist_txt;
	private JTextField gSize_txt;
	private JTextField fragLen_txt;
	private JTextField InputFragLen_txt;
	private JTextField tag_txt;
	private JTextField inputTag_txt;
	private JTextField norm_txt;
	private JTextField regionRes_txt;
	private JComboBox fragLen_cbx;
	private JComboBox InputFragLen_cbx;
	private JRadioButton regionYes_rb;
	private JRadioButton regionNo_rb;
	private JCheckBox center_chbx;
	
	private JPanel mainPeakFilter = new JPanel();
	private JTextField fcInput_txt;
	private JTextField poiInput_txt;
	private JTextField fcLocal_txt;
	private JTextField poiLocal_txt;
	private JTextField fcUnique_txt;
	private JTextField localSize_txt;
	private JTextField InputSize_txt;
	private JTextField fdr_txt;
	private JTextField poiCutoff_txt;
	private JTextField tagThsh_txt;
	private JTextField ntagThsh_txt;
	
	private JPanel mainAnother = new JPanel();
	private CardLayout cl = new CardLayout();
	JPanel subPanel1 = new JPanel();
	private JTextField methylThsh_txt;
	private JTextField minCytosine_txt;
	private	JRadioButton methyl_rb;
	private	JRadioButton unMethyl_rb;
	
	private JPanel mainAnnotation = new JPanel();
	private JTextField fragLenAnno_txt;
	private JTextField peakSize1_txt;
	private JTextField peakSize2_txt;
	private JTextField maxTag_txt;
	private JTextField nfrSize_txt;
	private JComboBox fragLenAnno_cbx;
	private JComboBox peakSize_cbx; 
	private JLabel nfrSize_lbl; 
	private JRadioButton useCpGYes_rb;
	private JRadioButton useCpGNo_rb;
	private JRadioButton useNfrYes_rb;
	private JRadioButton useNfrNo_rb;
	private JRadioButton skipTssYes_rb;
	private JRadioButton skipTssNo_rb;
	private JRadioButton skipGenomeNo_rb;
	private JRadioButton skipGenomeYes_rb;
	
	
	public Panel_PeakCalling(DataSet ds){
		cf = new CommonFunc(ds);
		makeChIP_Histone();
		makePeakFilter();
		makeAnother();
		makeAnnotation();
		
		initPeakCall();
		initAnno();
	}
	
	public void initPeakCall(){
		
		// chip-seq/histone
		peakSize_txt.setText("0");
		minDist_txt.setText("0");
		gSize_txt.setText("2e9");
		fragLen_txt.setText("only integer");
		fragLen_txt.setEnabled(false);
		InputFragLen_txt.setText("only integer");
		InputFragLen_txt.setEnabled(false);
		fragLen_cbx.setSelectedIndex(0);
		InputFragLen_cbx.setSelectedIndex(0);
		tag_txt.setText("0");
		inputTag_txt.setText("0");
		norm_txt.setText("10000000");
		regionNo_rb.setSelected(true);
		regionRes_txt.setEnabled(false);
		regionRes_txt.setText("4");
		center_chbx.setSelected(false);
		
		// peak filter
		fcInput_txt.setText("4.0");
		poiInput_txt.setText("0.0001");
		fcLocal_txt.setText("4.0");
		poiLocal_txt.setText("0.0001");
		fcUnique_txt.setText("2.0");
		localSize_txt.setText("10000");
		InputSize_txt.setText("0");
		fdr_txt.setText("0.001");
		poiCutoff_txt.setText("0.001");
		tagThsh_txt.setText("25");
		ntagThsh_txt.setText("1e7");
	
		// Other analysis
		methylThsh_txt.setText("0");
		minCytosine_txt.setText("6");
		unMethyl_rb.setSelected(true);

	}
	
	public void initAnno(){
		fragLenAnno_cbx.setSelectedIndex(0);
		fragLenAnno_txt.setEnabled(false);
		fragLenAnno_txt.setText("Only Integer");
		peakSize_cbx.setSelectedIndex(0);
		peakSize1_txt.setText("Only Integer");
		peakSize2_txt.setEnabled(false);
		peakSize2_txt.setText("Only Integer");
		maxTag_txt.setText("0");
		useCpGNo_rb.setSelected(true);
		useNfrNo_rb.setSelected(true);
		nfrSize_txt.setEnabled(false);
		nfrSize_txt.setText("Only Integer");
		skipTssNo_rb.setSelected(true);
		skipGenomeNo_rb.setSelected(true);
		
	}
	
	public boolean checkChangeValue(){

		// chip-seq/histone
		if(!peakSize_txt.getText().equals("0") || !minDist_txt.getText().equals("0") || !gSize_txt.getText().equals("2e9")){
			return true;
		}
		
		if(!fragLen_txt.getText().equals("only integer") && fragLen_cbx.getSelectedIndex() != 0){
			return true;
		}
		if(!InputFragLen_txt.getText().equals("only integer") && InputFragLen_cbx.getSelectedIndex() != 0){
			return true;
		}
			
		if(!tag_txt.getText().equals("0") || !inputTag_txt.getText().equals("0") || !norm_txt.getText().equals("10000000")){
			return true;
		}
		
		if(regionYes_rb.isSelected() || center_chbx.isSelected()){
			return true;
		}
		
		//peak filter
		if(!fcInput_txt.getText().equals("4.0") || !poiInput_txt.getText().equals("0.0001")){
			return true;
		}
		
		if(!fcLocal_txt.getText().equals("4.0") || !poiLocal_txt.getText().equals("0.0001")){
			return true;
		}
		
		if(!fcUnique_txt.getText().equals("2.0")){
			return true;
		}
		
		if(!localSize_txt.getText().equals("10000") || !InputSize_txt.getText().equals("0") || !fdr_txt.getText().equals("0.001") || !poiCutoff_txt.getText().equals("0.001")){
			return true;
		}
		
		if(!tagThsh_txt.getText().equals("25") || !ntagThsh_txt.getText().equals("1e7")){
			return true;
		}
		
		//other analysis
		if(methyl_rb.isSelected()){
			return true;
		}
		
		if(!methylThsh_txt.getText().equals("0") || !minCytosine_txt.getText().equals("6")){
			return true;
		}
		
		//annotation

		maxTag_txt.setText("0");
		useCpGNo_rb.setSelected(true);
		useNfrNo_rb.setSelected(true);
		nfrSize_txt.setText("Only Integer");
		skipTssNo_rb.setSelected(true);
		skipGenomeNo_rb.setSelected(true);
		
		if(!fragLenAnno_txt.getText().equals("only integer") && fragLenAnno_cbx.getSelectedIndex() != 0){
			return true;
		}
		
		if(peakSize_cbx.getSelectedIndex() != 0){
			return true;
		}
		
		if(!maxTag_txt.getText().equals("0")){
			return true;
		}
		
		if(useCpGYes_rb.isSelected() || useNfrYes_rb.isSelected() || skipTssYes_rb.isSelected() || skipGenomeYes_rb.isSelected()){
			return true;
		}
		
		return false;
	}
	
	public void makeChIP_Histone(){
		mainChIPHistone.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainChIPHistone.setBackground(Color.WHITE);
		mainChIPHistone.setBounds(12, 23, 452, 590);
		mainChIPHistone.setLayout(null);
		
		
		JLabel first_lbl = new JLabel("ChIP-Seq / Histone");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 180, 15);
		mainChIPHistone.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainChIPHistone.add(separator);
		
		JLabel head_lbl = new JLabel("Peak Calling");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(330, 20, 110, 15);
		mainChIPHistone.add(head_lbl);
		
		JLabel peakSize_lbl = new JLabel("Peak size : ");
		peakSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		peakSize_lbl.setBounds(25, 95, 120, 15);
		mainChIPHistone.add(peakSize_lbl);
		
		peakSize_txt = new JTextField();
		peakSize_txt.setColumns(10);
		peakSize_txt.setBounds(180, 90, 90, 25);
		mainChIPHistone.add(peakSize_txt);
		
		JLabel minDist_lbl = new JLabel("MIN-Distance : ");
		minDist_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minDist_lbl.setBounds(25, 125, 120, 15);
		mainChIPHistone.add(minDist_lbl);
		
		minDist_txt = new JTextField();
		minDist_txt.setColumns(10);
		minDist_txt.setBounds(180, 120, 90, 25);
		mainChIPHistone.add(minDist_txt);
		
		JLabel gSize_lbl = new JLabel("Genome Size : ");
		gSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		gSize_lbl.setBounds(25, 155, 120, 15);
		mainChIPHistone.add(gSize_lbl);
		
		gSize_txt = new JTextField();
		gSize_txt.setColumns(10);
		gSize_txt.setBounds(180, 150, 90, 25);
		mainChIPHistone.add(gSize_txt);
		
		JLabel fragLen_lbl = new JLabel("Fragment Length : ");
		fragLen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLen_lbl.setBounds(25, 185, 120, 15);
		mainChIPHistone.add(fragLen_lbl);
		
		fragLen_txt = new JTextField();
		fragLen_txt.setColumns(10);
		fragLen_txt.setBounds(280, 180, 90, 25);
		mainChIPHistone.add(fragLen_txt);
		
		fragLen_cbx = new JComboBox();
		fragLen_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLen_cbx.setModel(new DefaultComboBoxModel(new String[] {"auto", "Other"}));
		fragLen_cbx.setBackground(Color.WHITE);
		fragLen_cbx.setBounds(180, 180, 90, 24);
		mainChIPHistone.add(fragLen_cbx);
		
		JLabel InputFragLen_lbl = new JLabel("Input Fragment Length : ");
		InputFragLen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		InputFragLen_lbl.setBounds(25, 215, 155, 15);
		mainChIPHistone.add(InputFragLen_lbl);
		
		InputFragLen_cbx = new JComboBox();
		InputFragLen_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		InputFragLen_cbx.setModel(new DefaultComboBoxModel(new String[] {"auto", "Other"}));
		InputFragLen_cbx.setBackground(Color.WHITE);
		InputFragLen_cbx.setBounds(180, 210, 90, 24);
		mainChIPHistone.add(InputFragLen_cbx);
		
		InputFragLen_txt = new JTextField();
		InputFragLen_txt.setColumns(10);
		InputFragLen_txt.setBounds(280, 210, 90, 25);
		mainChIPHistone.add(InputFragLen_txt);
		
		JPanel tag_Panel = new JPanel();
		tag_Panel.setToolTipText("<html>Maximum tags per bp to count, 0 = no limit, default: auto<br>Maximum tags per bp to count in input, 0 = no limit, default: auto.</html>");
		tag_Panel.setLayout(null);
		tag_Panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Tags per bp to count", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		tag_Panel.setBackground(Color.WHITE);
		tag_Panel.setBounds(12, 249, 420, 55);
		mainChIPHistone.add(tag_Panel);
		
		JLabel tag_lbl = new JLabel("Tag : ");
		tag_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		tag_lbl.setBounds(25, 25, 60, 15);
		tag_Panel.add(tag_lbl);
		
		JLabel inputTag_lbl = new JLabel("Input tag : ");
		inputTag_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		inputTag_lbl.setBounds(200, 25, 80, 15);
		tag_Panel.add(inputTag_lbl);
		
		tag_txt = new JTextField();
		tag_txt.setColumns(10);
		tag_txt.setBounds(83, 20, 75, 25);
		tag_Panel.add(tag_txt);
		
		inputTag_txt = new JTextField();
		inputTag_txt.setColumns(10);
		inputTag_txt.setBounds(280, 20, 75, 25);
		tag_Panel.add(inputTag_txt);
		
		JLabel norm_lbl = new JLabel("Tag count to normalize : ");
		norm_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		norm_lbl.setBounds(25, 330, 160, 15);
		mainChIPHistone.add(norm_lbl);
		
		norm_txt = new JTextField();
		norm_txt.setColumns(10);
		norm_txt.setBounds(180, 325, 90, 25);
		mainChIPHistone.add(norm_txt);
		
		JLabel region_lbl = new JLabel("Find enriched regions of variable length : ");
		region_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		region_lbl.setBounds(25, 362, 280, 15);
		mainChIPHistone.add(region_lbl);
		
		ButtonGroup bg = new ButtonGroup();
		
		regionYes_rb = new JRadioButton("Yes");
		regionYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		regionYes_rb.setBackground(Color.WHITE);
		regionYes_rb.setBounds(295, 360, 55, 23);
		mainChIPHistone.add(regionYes_rb);
		
		regionNo_rb = new JRadioButton("No");
		regionNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		regionNo_rb.setBackground(Color.WHITE);
		regionNo_rb.setBounds(350, 360, 45, 23);
		mainChIPHistone.add(regionNo_rb);
		
		bg.add(regionYes_rb);
		bg.add(regionNo_rb);
		
		JLabel regionRes_lbl = new JLabel("- Region resolution : ");
		regionRes_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		regionRes_lbl.setBounds(48, 394, 130, 15);
		mainChIPHistone.add(regionRes_lbl);
		
		regionRes_txt = new JTextField();
		regionRes_txt.setColumns(10);
		regionRes_txt.setBounds(180, 389, 90, 25);
		mainChIPHistone.add(regionRes_txt);
		
		center_chbx = new JCheckBox("Use the Centers peaks");
		center_chbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		center_chbx.setBackground(Color.WHITE);
		center_chbx.setVerticalAlignment(SwingConstants.TOP);
		center_chbx.setBounds(25, 422, 193, 20);
		mainChIPHistone.add(center_chbx);
		
		JTextArea center_info_lbl = new JTextArea();
		center_info_lbl.setLineWrap(true);
		center_info_lbl.setWrapStyleWord(true);
		center_info_lbl.setText("Centers peaks on maximum tag overlap and calculates focus ratios.");
		center_info_lbl.setBounds(46, 450, 364, 41);
		mainChIPHistone.add(center_info_lbl);
		
		JLabel peakSize_img = new JLabel("");
		peakSize_img.setIcon(info_img);
		peakSize_img.setToolTipText("Peak size, default: auto = 0.");
		peakSize_img.setHorizontalAlignment(SwingConstants.LEFT);
		peakSize_img.setBounds(280, 92, 17, 20);
		mainChIPHistone.add(peakSize_img);
		
		JLabel minDist_img = new JLabel("");
		minDist_img.setIcon(info_img);
		minDist_img.setToolTipText("Minimum distance between peaks, default: peak size x2.");
		minDist_img.setHorizontalAlignment(SwingConstants.LEFT);
		minDist_img.setBounds(280, 122, 17, 20);
		mainChIPHistone.add(minDist_img);
		
		JLabel gSize_img = new JLabel("");
		gSize_img.setIcon(info_img);
		gSize_img.setToolTipText("Set effective mappable genome size, default: 2e9.");
		gSize_img.setHorizontalAlignment(SwingConstants.LEFT);
		gSize_img.setBounds(280, 152, 17, 20);
		mainChIPHistone.add(gSize_img);
		
		JLabel fragLen_img = new JLabel("");
		fragLen_img.setIcon(info_img);
		fragLen_img.setToolTipText("Approximate fragment length, default: auto <int | auto>");
		fragLen_img.setHorizontalAlignment(SwingConstants.LEFT);
		fragLen_img.setBounds(382, 182, 17, 20);
		mainChIPHistone.add(fragLen_img);
		
		JLabel InputFragLen_img = new JLabel("");
		InputFragLen_img.setIcon(info_img);
		InputFragLen_img.setToolTipText("Approximate fragment length of input tags, default: auto <int | auto>");
		InputFragLen_img.setHorizontalAlignment(SwingConstants.LEFT);
		InputFragLen_img.setBounds(382, 212, 17, 20);
		mainChIPHistone.add(InputFragLen_img);
		
		JLabel norm_img = new JLabel("");
		norm_img.setIcon(info_img);
		norm_img.setToolTipText("Tag count to normalize to, default 10000000.");
		norm_img.setHorizontalAlignment(SwingConstants.LEFT);
		norm_img.setBounds(280, 327, 17, 20);
		mainChIPHistone.add(norm_img);
		
		JLabel region_img = new JLabel("");
		region_img.setIcon(info_img);
		region_img.setToolTipText("<html>Extends start/stop coordinates to cover full region considered \"enriched\"<br>Number of fractions peaks are divided in when extending 'regions', def: 4</html>");
		region_img.setHorizontalAlignment(SwingConstants.LEFT);
		region_img.setBounds(402, 362, 17, 20);
		mainChIPHistone.add(region_img);
		
		
		fragLen_cbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fragLen_cbx.getSelectedItem().toString().equals("Other")){
					fragLen_txt.setEnabled(true);
				}else{
					fragLen_txt.setEnabled(false);
				}
			}
		});
		
		InputFragLen_cbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(InputFragLen_cbx.getSelectedItem().toString().equals("Other")){
					InputFragLen_txt.setEnabled(true);
				}else{
					InputFragLen_txt.setEnabled(false);
				}
			}
		});
		
		regionYes_rb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(regionYes_rb.isSelected()){
					regionRes_txt.setEnabled(true);
				}
			}
		});
		
		regionNo_rb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(regionNo_rb.isSelected()){
					regionRes_txt.setEnabled(false);
				}
			}
		});
		
	}
	
	public void makePeakFilter(){
		mainPeakFilter.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainPeakFilter.setBackground(Color.WHITE);
		mainPeakFilter.setBounds(12, 23, 452, 590);
		mainPeakFilter.setLayout(null);
		
		
		JLabel first_lbl = new JLabel("Peak Filtering");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 180, 15);
		mainPeakFilter.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainPeakFilter.add(separator);
		
		JLabel head_lbl = new JLabel("Peak Calling");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(330, 20, 110, 15);
		mainPeakFilter.add(head_lbl);
		
		JLabel fcInput_lbl = new JLabel("Fold Enrichment (Input): ");
		fcInput_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fcInput_lbl.setBounds(22, 90, 180, 15);
		mainPeakFilter.add(fcInput_lbl);
		
		JLabel poiInput_lbl = new JLabel("- poisson p-value threshold : ");
		poiInput_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		poiInput_lbl.setBounds(30, 120, 190, 15);
		mainPeakFilter.add(poiInput_lbl);
		
		JLabel fcLocal_lbl = new JLabel("Fold Enrichment (local): ");
		fcLocal_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fcLocal_lbl.setBounds(22, 150, 180, 15);
		mainPeakFilter.add(fcLocal_lbl);
		
		JLabel poiLocal_lbl = new JLabel("- poisson p-value threshold : ");
		poiLocal_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		poiLocal_lbl.setBounds(30, 180, 190, 15);
		mainPeakFilter.add(poiLocal_lbl);
		
		fcInput_txt = new JTextField();
		fcInput_txt.setColumns(10);
		fcInput_txt.setBounds(235, 85, 75, 25);
		mainPeakFilter.add(fcInput_txt);
		
		poiInput_txt = new JTextField();
		poiInput_txt.setColumns(10);
		poiInput_txt.setBounds(235, 115, 75, 25);
		mainPeakFilter.add(poiInput_txt);
		
		fcLocal_txt = new JTextField();
		fcLocal_txt.setColumns(10);
		fcLocal_txt.setBounds(235, 145, 75, 25);
		mainPeakFilter.add(fcLocal_txt);
		
		poiLocal_txt = new JTextField();
		poiLocal_txt.setColumns(10);
		poiLocal_txt.setBounds(235, 175, 75, 25);
		mainPeakFilter.add(poiLocal_txt);
		
		JLabel fcUnique_lbl = new JLabel("Fold Enrichment (unique tag) : ");
		fcUnique_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fcUnique_lbl.setBounds(22, 210, 220, 15);
		mainPeakFilter.add(fcUnique_lbl);
		
		fcUnique_txt = new JTextField();
		fcUnique_txt.setColumns(10);
		fcUnique_txt.setBounds(235, 205, 75, 25);
		mainPeakFilter.add(fcUnique_txt);
		
		JLabel localSize_lbl = new JLabel("Local Size (Local tag): ");
		localSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		localSize_lbl.setBounds(22, 264, 220, 15);
		mainPeakFilter.add(localSize_lbl);
		
		JLabel InputSize_lbl = new JLabel("Input Size (Input tag): ");
		InputSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		InputSize_lbl.setBounds(22, 294, 220, 15);
		mainPeakFilter.add(InputSize_lbl);
		
		localSize_txt = new JTextField();
		localSize_txt.setColumns(10);
		localSize_txt.setBounds(235, 259, 75, 25);
		mainPeakFilter.add(localSize_txt);
		
		InputSize_txt = new JTextField();
		InputSize_txt.setColumns(10);
		InputSize_txt.setBounds(235, 289, 75, 25);
		mainPeakFilter.add(InputSize_txt);
		
		JLabel fdr_lbl = new JLabel("False Discovery Rate : ");
		fdr_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fdr_lbl.setBounds(22, 324, 220, 15);
		mainPeakFilter.add(fdr_lbl);
		
		JLabel poiCutoff_lbl = new JLabel("Poisson p-value cutoff : ");
		poiCutoff_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		poiCutoff_lbl.setBounds(22, 354, 220, 15);
		mainPeakFilter.add(poiCutoff_lbl);
		
		fdr_txt = new JTextField();
		fdr_txt.setColumns(10);
		fdr_txt.setBounds(235, 319, 75, 25);
		mainPeakFilter.add(fdr_txt);
		
		poiCutoff_txt = new JTextField();
		poiCutoff_txt.setColumns(10);
		poiCutoff_txt.setBounds(235, 349, 75, 25);
		mainPeakFilter.add(poiCutoff_txt);
		
		JPanel threshold_Panel = new JPanel();
		threshold_Panel.setToolTipText("<html>Set # of tags to define a peak, default: 25<br>Set # of normalized tags to define a peak, by default uses 1e7 for norm.</html>");
		threshold_Panel.setLayout(null);
		threshold_Panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Threshold to define a peak", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		threshold_Panel.setBackground(Color.WHITE);
		threshold_Panel.setBounds(12, 403, 420, 100);
		mainPeakFilter.add(threshold_Panel);
		
		JLabel tagThsh_lbl = new JLabel("Set # of tags : ");
		tagThsh_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		tagThsh_lbl.setBounds(20, 30, 191, 15);
		threshold_Panel.add(tagThsh_lbl);
		
		tagThsh_txt = new JTextField();
		tagThsh_txt.setColumns(10);
		tagThsh_txt.setBounds(224, 25, 75, 25);
		threshold_Panel.add(tagThsh_txt);
		
		JLabel ntagThsh_lbl = new JLabel("Set # of normalized tags : ");
		ntagThsh_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		ntagThsh_lbl.setBounds(20, 65, 191, 15);
		threshold_Panel.add(ntagThsh_lbl);
		
		ntagThsh_txt = new JTextField();
		ntagThsh_txt.setColumns(10);
		ntagThsh_txt.setBounds(224, 60, 75, 25);
		threshold_Panel.add(ntagThsh_txt);
		
		JLabel fcInput_img = new JLabel("");
		fcInput_img.setIcon(info_img);
		fcInput_img.setToolTipText("Fold enrichment over input tag count, default: 4.0.");
		fcInput_img.setHorizontalAlignment(SwingConstants.LEFT);
		fcInput_img.setBounds(323, 87, 17, 20);
		mainPeakFilter.add(fcInput_img);
		
		JLabel poiInput_img = new JLabel("");
		poiInput_img.setIcon(info_img);
		poiInput_img.setToolTipText("Poisson p-value threshold relative to input tag count, default: 0.0001.");
		poiInput_img.setHorizontalAlignment(SwingConstants.LEFT);
		poiInput_img.setBounds(323, 117, 17, 20);
		mainPeakFilter.add(poiInput_img);
		
		JLabel fcLocal_img = new JLabel("");
		fcLocal_img.setIcon(info_img);
		fcLocal_img.setToolTipText("Fold enrichment over local tag count, default: 4.0.");
		fcLocal_img.setHorizontalAlignment(SwingConstants.LEFT);
		fcLocal_img.setBounds(323, 147, 17, 20);
		mainPeakFilter.add(fcLocal_img);
		
		JLabel poiLocal_img = new JLabel("");
		poiLocal_img.setIcon(info_img);
		poiLocal_img.setToolTipText("Poisson p-value threshold relative to local tag count, default: 0.0001.");
		poiLocal_img.setHorizontalAlignment(SwingConstants.LEFT);
		poiLocal_img.setBounds(323, 177, 17, 20);
		mainPeakFilter.add(poiLocal_img);
		
		JLabel fcUnique_img = new JLabel("");
		fcUnique_img.setIcon(info_img);
		fcUnique_img.setToolTipText("Fold enrichment limit of expected unique tag positions, default: 2.0.");
		fcUnique_img.setHorizontalAlignment(SwingConstants.LEFT);
		fcUnique_img.setBounds(323, 207, 17, 20);
		mainPeakFilter.add(fcUnique_img);
		
		JLabel localSize_img = new JLabel("");
		localSize_img.setIcon(info_img);
		localSize_img.setToolTipText("Region to check for local tag enrichment, default: 10000.");
		localSize_img.setHorizontalAlignment(SwingConstants.LEFT);
		localSize_img.setBounds(323, 261, 17, 20);
		mainPeakFilter.add(localSize_img);
		
		JLabel InputSize_img = new JLabel("");
		InputSize_img.setIcon(info_img);
		InputSize_img.setToolTipText("Size of region to search for control tags, default: 2x peak size.");
		InputSize_img.setHorizontalAlignment(SwingConstants.LEFT);
		InputSize_img.setBounds(323, 291, 17, 20);
		mainPeakFilter.add(InputSize_img);
		
		JLabel fdr_img = new JLabel("");
		fdr_img.setIcon(info_img);
		fdr_img.setToolTipText("False discovery rate, default = 0.001.");
		fdr_img.setHorizontalAlignment(SwingConstants.LEFT);
		fdr_img.setBounds(323, 321, 17, 20);
		mainPeakFilter.add(fdr_img);
		
		JLabel poiCutoff_img = new JLabel("");
		poiCutoff_img.setIcon(info_img);
		poiCutoff_img.setToolTipText("Set poisson p-value cutoff, default: uses fdr.");
		poiCutoff_img.setHorizontalAlignment(SwingConstants.LEFT);
		poiCutoff_img.setBounds(323, 351, 17, 20);
		mainPeakFilter.add(poiCutoff_img);
		
		
	}
	
	public void makeAnother(){
		mainAnother.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainAnother.setBackground(Color.WHITE);
		mainAnother.setBounds(12, 23, 452, 590);
		mainAnother.setLayout(cl);
		
		makeFirstPanel();
		
		mainAnother.add("First",subPanel1);
		
	}
	
	public void makeFirstPanel(){

		subPanel1.setBackground(Color.WHITE);
		subPanel1.setBounds(12, 23, 452, 590);
		subPanel1.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		subPanel1.add(separator);
		
		JLabel head_lbl = new JLabel("Peak Calling");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(330, 20, 110, 15);
		subPanel1.add(head_lbl);
		
		JLabel second_lbl = new JLabel("MethylC-Seq/BS-Seq");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 65, 180, 15);
		subPanel1.add(second_lbl);
		
		JLabel methylThsh_lbl = new JLabel("Methyl Threshold : ");
		methylThsh_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		methylThsh_lbl.setBounds(25, 137, 120, 15);
		subPanel1.add(methylThsh_lbl);
		
		methylThsh_txt = new JTextField();
		methylThsh_txt.setColumns(10);
		methylThsh_txt.setBounds(190, 132, 90, 25);
		subPanel1.add(methylThsh_txt);
		
		JLabel minCytosine_lbl = new JLabel("Min cytosine per Methyl : ");
		minCytosine_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minCytosine_lbl.setBounds(25, 167, 170, 15);
		subPanel1.add(minCytosine_lbl);
		
		minCytosine_txt = new JTextField();
		minCytosine_txt.setColumns(10);
		minCytosine_txt.setBounds(190, 162, 90, 25);
		subPanel1.add(minCytosine_txt);
		
		JLabel findRegion_lbl = new JLabel("Find Region : ");
		findRegion_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		findRegion_lbl.setBounds(25, 107, 150, 15);
		subPanel1.add(findRegion_lbl);
		
		ButtonGroup bg = new ButtonGroup();
			
		methyl_rb = new JRadioButton("MethylC");
		methyl_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		methyl_rb.setBackground(Color.WHITE);
		methyl_rb.setBounds(190, 101, 80, 23);
		subPanel1.add(methyl_rb);
		
		unMethyl_rb = new JRadioButton("UnMethylC");
		unMethyl_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		unMethyl_rb.setBackground(Color.WHITE);
		unMethyl_rb.setBounds(280, 101, 90, 23);
		subPanel1.add(unMethyl_rb);
		
		bg.add(methyl_rb);
		bg.add(unMethyl_rb);
		
		JLabel findRegion_img = new JLabel("");
		findRegion_img.setIcon(info_img);
		findRegion_img.setToolTipText("Find unmethylated/methylated regions, default: -unmethyC");
		findRegion_img.setHorizontalAlignment(SwingConstants.LEFT);
		findRegion_img.setBounds(380, 103, 17, 20);
		subPanel1.add(findRegion_img);
		
		JLabel methylThsh_img = new JLabel("");
		methylThsh_img.setIcon(info_img);
		methylThsh_img.setToolTipText("Methylation threshold of regions, default: avg methylation/2");
		methylThsh_img.setHorizontalAlignment(SwingConstants.LEFT);
		methylThsh_img.setBounds(290, 134, 17, 20);
		subPanel1.add(methylThsh_img);
		
		JLabel minCytosine_img = new JLabel("");
		minCytosine_img.setIcon(info_img);
		minCytosine_img.setToolTipText("Minimum number of cytosines per methylation peak, default: 6");
		minCytosine_img.setHorizontalAlignment(SwingConstants.LEFT);
		minCytosine_img.setBounds(290, 164, 17, 20);
		subPanel1.add(minCytosine_img);

	}
	
	
	public void makeAnnotation(){
		mainAnnotation.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainAnnotation.setBackground(Color.WHITE);
		mainAnnotation.setBounds(12, 23, 452, 590);
		mainAnnotation.setLayout(null);
		
		JLabel first_lbl = new JLabel("Annotation");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 180, 15);
		mainAnnotation.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainAnnotation.add(separator);
		
		JLabel head_lbl = new JLabel("Peak Calling");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(330, 20, 110, 15);
		mainAnnotation.add(head_lbl);
		
		JLabel fragLenAnno_lbl = new JLabel("Fragment Length : ");
		fragLenAnno_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLenAnno_lbl.setBounds(22, 95, 130, 15);
		mainAnnotation.add(fragLenAnno_lbl);
		
		fragLenAnno_txt = new JTextField();
		fragLenAnno_txt.setColumns(10);
		fragLenAnno_txt.setBounds(245, 90, 120, 25);
		mainAnnotation.add(fragLenAnno_txt);
		
		fragLenAnno_cbx = new JComboBox();
		fragLenAnno_cbx.setModel(new DefaultComboBoxModel(new String[] {"auto", "Other"}));
		fragLenAnno_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		fragLenAnno_cbx.setBackground(Color.WHITE);
		fragLenAnno_cbx.setBounds(145, 90, 90, 24);
		mainAnnotation.add(fragLenAnno_cbx);
		
		JLabel peakSize_lbl = new JLabel("Peak Size : ");
		peakSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		peakSize_lbl.setBounds(22, 125, 130, 15);
		mainAnnotation.add(peakSize_lbl);
		
		peakSize_cbx = new JComboBox();
		peakSize_cbx.setModel(new DefaultComboBoxModel(new String[] {"Value", "Region", "given"}));
		peakSize_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		peakSize_cbx.setBackground(Color.WHITE);
		peakSize_cbx.setBounds(145, 120, 90, 24);
		mainAnnotation.add(peakSize_cbx);
		
		peakSize1_txt = new JTextField();
		peakSize1_txt.setColumns(10);
		peakSize1_txt.setBounds(245, 120, 85, 25);
		mainAnnotation.add(peakSize1_txt);
		
		peakSize2_txt = new JTextField();
		peakSize2_txt.setColumns(10);
		peakSize2_txt.setBounds(342, 120, 85, 25);
		mainAnnotation.add(peakSize2_txt);
		
		JLabel label = new JLabel("~");
		label.setFont(new Font("Dialog", Font.PLAIN, 12));
		label.setBounds(330, 125, 10, 15);
		mainAnnotation.add(label);
		
		JLabel maxTag_lbl = new JLabel("MAX-Tag : ");
		maxTag_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxTag_lbl.setBounds(22, 155, 130, 15);
		mainAnnotation.add(maxTag_lbl);
		
		maxTag_txt = new JTextField();
		maxTag_txt.setColumns(10);
		maxTag_txt.setBounds(145, 150, 90, 25);
		mainAnnotation.add(maxTag_txt);
		
		JLabel useCpG_lbl = new JLabel("Calculate CpG : ");
		useCpG_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		useCpG_lbl.setBounds(22, 190, 130, 15);
		mainAnnotation.add(useCpG_lbl);
		
		ButtonGroup bg = new ButtonGroup();
		
		useCpGYes_rb = new JRadioButton("Yes");
		useCpGYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		useCpGYes_rb.setBackground(Color.WHITE);
		useCpGYes_rb.setBounds(145, 187, 55, 23);
		mainAnnotation.add(useCpGYes_rb);
		
		useCpGNo_rb = new JRadioButton("No");
		useCpGNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		useCpGNo_rb.setBackground(Color.WHITE);
		useCpGNo_rb.setBounds(200, 187, 45, 23);
		mainAnnotation.add(useCpGNo_rb);
		
		bg.add(useCpGNo_rb);
		bg.add(useCpGYes_rb);
		
		JLabel useNfr_lbl = new JLabel("Use nuclesome free region : ");
		useNfr_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		useNfr_lbl.setBounds(22, 233, 185, 15);
		mainAnnotation.add(useNfr_lbl);
		
		final ButtonGroup bg2 = new ButtonGroup();
		
		useNfrYes_rb = new JRadioButton("Yes");
		useNfrYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		useNfrYes_rb.setBackground(Color.WHITE);
		useNfrYes_rb.setBounds(221, 231, 55, 23);
		mainAnnotation.add(useNfrYes_rb);
		
		useNfrNo_rb = new JRadioButton("No");
		useNfrNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		useNfrNo_rb.setBackground(Color.WHITE);
		useNfrNo_rb.setBounds(276, 231, 45, 23);
		mainAnnotation.add(useNfrNo_rb);
		
		bg2.add(useNfrNo_rb);
		bg2.add(useNfrYes_rb);
		
		nfrSize_lbl = new JLabel("- Nuclesome free region Size : ");
		nfrSize_lbl.setEnabled(false);
		nfrSize_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		nfrSize_lbl.setBounds(40, 267, 190, 15);
		mainAnnotation.add(nfrSize_lbl);
		
		nfrSize_txt = new JTextField();
		nfrSize_txt.setColumns(10);
		nfrSize_txt.setBounds(245, 263, 90, 25);
		mainAnnotation.add(nfrSize_txt);
		
		JLabel skipTss_lbl = new JLabel("Skip TSS Annotation : ");
		skipTss_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipTss_lbl.setBounds(22, 315, 185, 15);
		mainAnnotation.add(skipTss_lbl);
		
		JLabel skipGenome_lbl = new JLabel("Skip Genome Annotation : ");
		skipGenome_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipGenome_lbl.setBounds(22, 345, 185, 15);
		mainAnnotation.add(skipGenome_lbl);
		
		ButtonGroup bg3 = new ButtonGroup();
		
		skipTssYes_rb = new JRadioButton("Yes");
		skipTssYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipTssYes_rb.setBackground(Color.WHITE);
		skipTssYes_rb.setBounds(221, 312, 55, 23);
		mainAnnotation.add(skipTssYes_rb);
		
		skipTssNo_rb = new JRadioButton("No");
		skipTssNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipTssNo_rb.setBackground(Color.WHITE);
		skipTssNo_rb.setBounds(276, 312, 45, 23);
		mainAnnotation.add(skipTssNo_rb);
		
		bg3.add(skipTssNo_rb);
		bg3.add(skipTssYes_rb);
		
		ButtonGroup bg4 = new ButtonGroup();
		
		skipGenomeYes_rb = new JRadioButton("Yes");
		skipGenomeYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipGenomeYes_rb.setBackground(Color.WHITE);
		skipGenomeYes_rb.setBounds(221, 342, 55, 23);
		mainAnnotation.add(skipGenomeYes_rb);
		
		skipGenomeNo_rb = new JRadioButton("No");
		skipGenomeNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipGenomeNo_rb.setBackground(Color.WHITE);
		skipGenomeNo_rb.setBounds(276, 342, 45, 23);
		mainAnnotation.add(skipGenomeNo_rb);
		
		bg4.add(skipGenomeNo_rb);
		bg4.add(skipGenomeYes_rb);
		
		JLabel fragLenAnno_img = new JLabel("");
		fragLenAnno_img.setIcon(info_img);
		fragLenAnno_img.setToolTipText("Fragment length, default=auto, might want to set to 1 for 5'RNA");
		fragLenAnno_img.setHorizontalAlignment(SwingConstants.LEFT);
		fragLenAnno_img.setBounds(374, 92, 17, 20);
		mainAnnotation.add(fragLenAnno_img);
		
		JLabel peakSize_img = new JLabel("");
		peakSize_img.setIcon(info_img);
		peakSize_img.setToolTipText("<html>Value : Peak size[from center of peak], default=inferred from peak file<br>Region : size #,# (i.e. -size -10,50 count tags from -10 bp to +50 bp from center)<br>given : count tags etc. using the actual regions - for variable length regions</html>");
		peakSize_img.setHorizontalAlignment(SwingConstants.LEFT);
		peakSize_img.setBounds(435, 122, 17, 20);
		mainAnnotation.add(peakSize_img);
		
		JLabel maxTag_img = new JLabel("");
		maxTag_img.setIcon(info_img);
		maxTag_img.setToolTipText("(maximum number of tags to count per bp, default=0 [no maximum]");
		maxTag_img.setHorizontalAlignment(SwingConstants.LEFT);
		maxTag_img.setBounds(247, 152, 17, 20);
		mainAnnotation.add(maxTag_img);
		
		JLabel useCpG_img = new JLabel("");
		useCpG_img.setIcon(info_img);
		useCpG_img.setToolTipText("Calculate CpG/GC content");
		useCpG_img.setHorizontalAlignment(SwingConstants.LEFT);
		useCpG_img.setBounds(247, 190, 17, 20);
		mainAnnotation.add(useCpG_img);
		
		JLabel useNfr_img = new JLabel("");
		useNfr_img.setIcon(info_img);
		useNfr_img.setToolTipText("report nuclesome free region scores instead of tag counts");
		useNfr_img.setHorizontalAlignment(SwingConstants.LEFT);
		useNfr_img.setBounds(323, 233, 17, 20);
		mainAnnotation.add(useNfr_img);
		
		JLabel skipTss_img = new JLabel("");
		skipTss_img.setIcon(info_img);
		skipTss_img.setToolTipText("Skip TSS annotation");
		skipTss_img.setHorizontalAlignment(SwingConstants.LEFT);
		skipTss_img.setBounds(330, 314, 17, 20);
		mainAnnotation.add(skipTss_img);
		
		JLabel skipGenome_img = new JLabel("");
		skipGenome_img.setIcon(info_img);
		skipGenome_img.setToolTipText("Skip genome annotation step");
		skipGenome_img.setHorizontalAlignment(SwingConstants.LEFT);
		skipGenome_img.setBounds(330, 344, 17, 20);
		mainAnnotation.add(skipGenome_img);
		
		fragLenAnno_cbx.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(fragLenAnno_cbx.getSelectedItem().toString().equals("Other")){
					fragLenAnno_txt.setEnabled(true);
				}else{
					fragLenAnno_txt.setEnabled(false);
				}
			}
		});
		
		peakSize_cbx.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(peakSize_cbx.getSelectedItem().toString().equals("Value")){
					peakSize1_txt.setEnabled(true);
					peakSize2_txt.setEnabled(false);
				}else if(peakSize_cbx.getSelectedItem().toString().equals("Region")){
					peakSize1_txt.setEnabled(true);
					peakSize2_txt.setEnabled(true);
				}else{
					peakSize1_txt.setEnabled(false);
					peakSize2_txt.setEnabled(false);
				}
			}
		});

		useNfrYes_rb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(useNfrYes_rb.isSelected()){
					nfrSize_lbl.setEnabled(true);
					nfrSize_txt.setEnabled(true);
				}
			}
		});
		
		useNfrNo_rb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(useNfrNo_rb.isSelected()){
					nfrSize_lbl.setEnabled(false);
					nfrSize_txt.setEnabled(false);
				}
			}
		});
	}
	
	public JPanel getChIPHistone(){
		return mainChIPHistone;
	}

	public JPanel getPeakFilter(){
		return mainPeakFilter;
	}

	public JPanel getAnother(){
		return mainAnother;
	}
	
	public JPanel getAnnotation(){
		return mainAnnotation;
	}
	
	
	public String getFindPeakOption(){
		String option = "";
		
		if(cf.checkInteger(peakSize_txt.getText().toString()) && !peakSize_txt.getText().toString().equals("0")){
			option = "-size "+peakSize_txt.getText().toString();
		}
		if(cf.checkInteger(minDist_txt.getText().toString()) && !minDist_txt.getText().toString().equals("0")){
			option = option + " -minDist "+minDist_txt.getText().toString();
		}
		if(cf.checkInteger(gSize_txt.getText().toString()) || !gSize_txt.getText().toString().equals("2e9")){
			option = option + " -gsize "+gSize_txt.getText().toString();
		}
		if(fragLen_cbx.getSelectedItem().equals("auto")){
			option = option + " -fragLength auto";
		}else{
			if(cf.checkInteger(fragLen_txt.getText().toString())){
				option = option + " -fragLength "+fragLen_txt.getText().toString();
			}
		}
		if(InputFragLen_cbx.getSelectedItem().equals("auto")){
			option = option + " -inputFragLength auto";
		}else{
			if(cf.checkInteger(InputFragLen_txt.getText().toString())){
				option = option + " -inputFragLength "+InputFragLen_txt.getText().toString();
			}
		}
		if(cf.checkInteger(tag_txt.getText().toString()) && !tag_txt.getText().toString().equals("0")){
			option = option + " -tbp "+tag_txt.getText().toString();
		}
		if(cf.checkInteger(inputTag_txt.getText().toString()) && !inputTag_txt.getText().toString().equals("0")){
			option = option + " -inputtbp "+inputTag_txt.getText().toString();
		}
		if(cf.checkInteger(norm_txt.getText().toString()) && !norm_txt.getText().toString().equals("10000000")){
			option = option + " -norm "+norm_txt.getText().toString();
		}
		if(regionYes_rb.isSelected()){
			option = option + " -region";
			if(cf.checkInteger(regionRes_txt.getText().toString()) && !regionRes_txt.getText().toString().equals("4")){
				option = option + " -regionRes "+regionRes_txt.getText().toString();
			}
		}
		if(center_chbx.isSelected()){
			option = option + " -center";
		}
		
		// Filtering
		if(cf.checkDouble(fcInput_txt.getText().toString()) && !fcInput_txt.getText().toString().equals("4.0")){
			option = option + " -F "+fcInput_txt.getText().toString();
		}
		if(cf.checkDouble(poiInput_txt.getText().toString()) && !poiInput_txt.getText().toString().equals("0.0001")){
			option = option + " -P "+poiInput_txt.getText().toString();	
		}
		if(cf.checkDouble(fcLocal_txt.getText().toString()) && !fcLocal_txt.getText().toString().equals("4.0")){
			option = option + " -L "+fcLocal_txt.getText().toString();
		}
		if(cf.checkDouble(poiLocal_txt.getText().toString()) && !poiLocal_txt.getText().toString().equals("0.0001")){
			option = option + " -LP "+poiLocal_txt.getText().toString();	
		}
		if(cf.checkDouble(fcUnique_txt.getText().toString()) && !fcUnique_txt.getText().toString().equals("2.0")){
			option = option + " -C "+fcUnique_txt.getText().toString();	
		}
		if(cf.checkInteger(localSize_txt.getText().toString()) && !localSize_txt.getText().toString().equals("10000")){
			option = option + " -localSize "+localSize_txt.getText().toString();
		}
		if(cf.checkInteger(InputSize_txt.getText().toString()) && !InputSize_txt.getText().toString().equals("0")){
			option = option + " -inputSize "+InputSize_txt.getText().toString();
		}
		if(cf.checkDouble(fdr_txt.getText().toString()) && !fdr_txt.getText().toString().equals("0.001")){
			option = option + " -fdr "+fdr_txt.getText().toString();
		}
		if(cf.checkDouble(poiCutoff_txt.getText().toString()) && !poiCutoff_txt.getText().toString().equals("0.001")){
			option = option + " -poisson "+poiCutoff_txt.getText().toString();
		}
		if(cf.checkInteger(tagThsh_txt.getText().toString()) && !tagThsh_txt.getText().toString().equals("25")){
			option = option + " -tagThreshold "+tagThsh_txt.getText().toString();
		}
		if(!ntagThsh_txt.getText().toString().equals("1e7"))
			option = option + " -ntagThreshold "+ntagThsh_txt.getText().toString();
		
		// Other analysis
		if(methyl_rb.isSelected()){
			option = option + " -methylC";
		}else{
			option = option + " -unmethylC";
		}
		if(cf.checkInteger(methylThsh_txt.getText().toString()) && !methylThsh_txt.getText().toString().equals("0")){
			option = option + " -mCthresh "+methylThsh_txt.getText().toString();
		}
		if(cf.checkInteger(minCytosine_txt.getText().toString()) && !minCytosine_txt.getText().toString().equals("6")){
			option = option + " -minNumC "+minCytosine_txt.getText().toString();
		}
		
		return option;
	}
	
	public String getAnnotatePeakOption(){
		String option = "";
		if(fragLenAnno_cbx.getSelectedItem().equals("auto")){
			option = "-fragLength auto";
		}else{
			if(cf.checkInteger(fragLenAnno_txt.getText().toString())){
				option = "-fragLength "+fragLenAnno_txt.getText().toString();
			}
		}
		if(peakSize_cbx.getSelectedItem().equals("given")){
			option = option + " -size given";
		}else if(peakSize_cbx.getSelectedItem().equals("Value")){
			if(cf.checkInteger(peakSize1_txt.getText().toString())){
				option = option + " -size "+peakSize1_txt.getText().toString();
			}
		}else{
			if(cf.checkInteger(peakSize1_txt.getText().toString()) && cf.checkInteger(peakSize2_txt.getText().toString())){
				option = option + " -size "+peakSize1_txt.getText().toString()+","+peakSize2_txt.getText().toString();
			}
		}
		
		if(cf.checkInteger(maxTag_txt.getText().toString()) && !maxTag_txt.getText().toString().equals("0")){
			option = option + " -pc "+maxTag_txt.getText().toString();
		}
		if(useCpGYes_rb.isSelected()){
			option = option + " -CpG";
		}
		if(useNfrYes_rb.isSelected()){
			option = option + " -nfr";
			if(cf.checkDouble(nfrSize_txt.getText().toString())){
				option = option + " -nfrSize "+nfrSize_txt.getText().toString();
			}
		}
		if(skipTssYes_rb.isSelected()){
			option = option + " -noann";
		}
		if(skipGenomeYes_rb.isSelected()){
			option = option + " -nogene";
		}
		return option;
	}
}
