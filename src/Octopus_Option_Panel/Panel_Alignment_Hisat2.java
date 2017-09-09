package Octopus_Option_Panel;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
import Octopus_UI.UI_Private_Table;

public class Panel_Alignment_Hisat2 {
	private CommonFunc cf;
	private Icon info_img = new ImageIcon(Panel_Alignment_Hisat2.class.getResource("/Octopus_Icon/ToolTip.png"));
	private Icon next_img = new ImageIcon(Panel_Alignment_Hisat2.class.getResource("/Octopus_Icon/Next.png"));
	private Icon prev_img = new ImageIcon(Panel_Alignment_Hisat2.class.getResource("/Octopus_Icon/Previous.png"));
	
	private JPanel mainPanel = new JPanel();
	private JPanel subPanel1 = new JPanel();
	private JPanel subPanel2 = new JPanel();
	private JTextField skipStart_txt;
	private JTextField skipStop_txt;
	private JTextField trim5p_txt;
	private JTextField trim3p_txt;
	private JTextField minMismatch_txt;
	private JTextField maxMismatch_txt;
	private JTextField minSoftclip_txt;
	private JTextField maxSoftclip_txt;
	private JTextField readGOpen_txt;
	private JTextField readGExtend_txt;
	private JTextField refGOpen_txt;
	private JTextField refGExtend_txt;
	private JTextField ambig_txt;
	private CardLayout cl = new CardLayout();
	
	private JRadioButton ignorYes_rb;
	private JRadioButton ignorNo_rb;
	private JRadioButton dontAlignForYes_rb;
	private JRadioButton dontAlignForNo_rb;
	private JRadioButton dontAlignRevYes_rb;
	private JRadioButton dontAlignRevNo_rb;
	private JTextField canonical_txt;
	private JTextField nonCanonical_txt;
	private JTextField minIntron_txt;
	private JTextField maxIntron_txt;
	private JRadioButton notSpliceAlignYes_rb;
	private JRadioButton notSpliceAlignNo_rb;
	
	public Panel_Alignment_Hisat2(DataSet ds){
		cf = new CommonFunc(ds);
		mainPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(12, 23, 452, 590);
		mainPanel.setLayout(cl);
		
		makeFirstPanel();
		makeSecondPanel();
		
		mainPanel.add("First",subPanel1);
		mainPanel.add("Second",subPanel2);
		
		init();
	}
	
	public void init(){
		skipStart_txt.setText("0");
		skipStop_txt.setText("0");
		trim5p_txt.setText("0");
		trim3p_txt.setText("0");
		ambig_txt.setText("1");
		minMismatch_txt.setText("2");
		maxMismatch_txt.setText("6");
		minSoftclip_txt.setText("1");
		maxSoftclip_txt.setText("2");
		readGOpen_txt.setText("5");
		readGExtend_txt.setText("3");
		refGOpen_txt.setText("5");
		refGExtend_txt.setText("3");
		ignorNo_rb.setSelected(true);
		dontAlignForNo_rb.setSelected(true);
		dontAlignRevNo_rb.setSelected(true);
		notSpliceAlignNo_rb.setSelected(true);
		canonical_txt.setText("0");
		nonCanonical_txt.setText("12");
		minIntron_txt.setText("20");
		maxIntron_txt.setText("500000");
		
	}
	
	public boolean checkChangeValue(){
		
		if(!skipStart_txt.getText().equals("0") || !skipStop_txt.getText().equals("0")){
			return true;
		}
				
		if(!trim5p_txt.getText().equals("0") || !trim3p_txt.getText().equals("0")){
			return true;
		}
				
		if(!ambig_txt.getText().equals("1")){
			return true;
		}
		
		if(!minMismatch_txt.getText().equals("2") || !maxMismatch_txt.getText().equals("6")){
			return true;
		}
				
		if(!minSoftclip_txt.getText().equals("1") || !maxSoftclip_txt.getText().equals("2")){
			return true;
		}
		
		if(!readGOpen_txt.getText().equals("5") || !readGExtend_txt.getText().equals("3")){
			return true;
		}
		
		if(!refGOpen_txt.getText().equals("5") || !refGExtend_txt.getText().equals("3")){
			return true;
		}
		
		if(ignorYes_rb.isSelected() || dontAlignForYes_rb.isSelected() || dontAlignRevYes_rb.isSelected() || notSpliceAlignYes_rb.isSelected()){
			return true;
		}
		
		if(!canonical_txt.getText().equals("0") || !nonCanonical_txt.getText().equals("12")){
			return true;
		}
		
		if(!minIntron_txt.getText().equals("20") || !maxIntron_txt.getText().equals("500000")){
			return true;
		}
		
		return false;
	}
	
	public void makeFirstPanel(){
		
		subPanel1.setBackground(Color.WHITE);
		subPanel1.setBounds(12, 23, 452, 590);
		subPanel1.setLayout(null);
		
		JLabel first_lbl = new JLabel("Input");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 300, 15);
		subPanel1.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		subPanel1.add(separator);
		
		JLabel head_lbl = new JLabel("Alignment(Hisat2)");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(290, 20, 150, 15);
		subPanel1.add(head_lbl);
		
		JLabel skipStart_lbl = new JLabel("Skip N read (Do not align) : ");
		skipStart_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipStart_lbl.setBounds(22, 90, 180, 15);
		subPanel1.add(skipStart_lbl);
		
		skipStart_txt = new JTextField();
		skipStart_txt.setColumns(10);
		skipStart_txt.setBounds(220, 85, 114, 25);
		subPanel1.add(skipStart_txt);
		
		JLabel skipStop_lbl = new JLabel("Stop after aligning N reads : ");
		skipStop_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		skipStop_lbl.setBounds(22, 117, 191, 15);
		subPanel1.add(skipStop_lbl);
		
		skipStop_txt = new JTextField();
		skipStop_txt.setColumns(10);
		skipStop_txt.setBounds(220, 112, 114, 25);
		subPanel1.add(skipStop_txt);
		
		JLabel trim5p_lbl = new JLabel("Trim N bases 5' end : ");
		trim5p_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		trim5p_lbl.setBounds(22, 146, 140, 15);
		subPanel1.add(trim5p_lbl);
		
		trim5p_txt = new JTextField();
		trim5p_txt.setColumns(10);
		trim5p_txt.setBounds(220, 141, 114, 25);
		subPanel1.add(trim5p_txt);
		
		trim3p_txt = new JTextField();
		trim3p_txt.setColumns(10);
		trim3p_txt.setBounds(220, 171, 114, 25);
		subPanel1.add(trim3p_txt);
		
		JLabel trim3p_lbl = new JLabel("Trim N bases 3' end : ");
		trim3p_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		trim3p_lbl.setBounds(22, 176, 140, 15);
		subPanel1.add(trim3p_lbl);
		
		JLabel second_lbl = new JLabel("Scoring");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 208, 300, 15);
		subPanel1.add(second_lbl);
		
		JPanel mismatch_panel = new JPanel();
		mismatch_panel.setToolTipText("Max and min penalties for mismatch; lower qual = lower penalty <2,6>");
		mismatch_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Mismatch penalty", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		mismatch_panel.setBackground(Color.WHITE);
		mismatch_panel.setBounds(15, 272, 420, 54);
		subPanel1.add(mismatch_panel);
		mismatch_panel.setLayout(null);
		
		JLabel minMismatch_lbl = new JLabel("Min-Mismatch : ");
		minMismatch_lbl.setBounds(12, 22, 100, 15);
		mismatch_panel.add(minMismatch_lbl);
		minMismatch_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		minMismatch_txt = new JTextField();
		minMismatch_txt.setBounds(122, 17, 70, 25);
		mismatch_panel.add(minMismatch_txt);
		minMismatch_txt.setColumns(10);
		
		JLabel maxMismatch_lbl = new JLabel("Max-Mismatch : ");
		maxMismatch_lbl.setBounds(210, 22, 100, 15);
		mismatch_panel.add(maxMismatch_lbl);
		maxMismatch_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		maxMismatch_txt = new JTextField();
		maxMismatch_txt.setBounds(320, 17, 70, 25);
		mismatch_panel.add(maxMismatch_txt);
		maxMismatch_txt.setColumns(10);
		
		JPanel softclip_panel = new JPanel();
		softclip_panel.setToolTipText("Max and min penalties for soft-clipping; lower qual = lower penalty <1,2>");
		softclip_panel.setLayout(null);
		softclip_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Soft-Clipping penalty", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		softclip_panel.setBackground(Color.WHITE);
		softclip_panel.setBounds(12, 337, 420, 54);
		subPanel1.add(softclip_panel);
		
		JLabel minSoftclip_lbl = new JLabel("Min-softcliping : ");
		minSoftclip_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minSoftclip_lbl.setBounds(12, 22, 105, 15);
		softclip_panel.add(minSoftclip_lbl);
		
		minSoftclip_txt = new JTextField();
		minSoftclip_txt.setColumns(10);
		minSoftclip_txt.setBounds(122, 17, 70, 25);
		softclip_panel.add(minSoftclip_txt);
		
		JLabel maxSoftclip_lbl = new JLabel("Max-softcliping : ");
		maxSoftclip_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxSoftclip_lbl.setBounds(210, 22, 105, 15);
		softclip_panel.add(maxSoftclip_lbl);
		
		maxSoftclip_txt = new JTextField();
		maxSoftclip_txt.setColumns(10);
		maxSoftclip_txt.setBounds(320, 17, 70, 25);
		softclip_panel.add(maxSoftclip_txt);
		
		JPanel read_panel = new JPanel();
		read_panel.setToolTipText("Read gap open, extend penalties (5,3)");
		read_panel.setLayout(null);
		read_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Read gap penalty", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		read_panel.setBackground(Color.WHITE);
		read_panel.setBounds(12, 402, 420, 54);
		subPanel1.add(read_panel);
		
		JLabel readGOpen_lbl = new JLabel("Gap open : ");
		readGOpen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		readGOpen_lbl.setBounds(12, 22, 80, 15);
		read_panel.add(readGOpen_lbl);
		
		readGOpen_txt = new JTextField();
		readGOpen_txt.setColumns(10);
		readGOpen_txt.setBounds(122, 17, 70, 25);
		read_panel.add(readGOpen_txt);
		
		JLabel readGExtend_lbl = new JLabel("Gap extend : ");
		readGExtend_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		readGExtend_lbl.setBounds(210, 22, 84, 15);
		read_panel.add(readGExtend_lbl);
		
		readGExtend_txt = new JTextField();
		readGExtend_txt.setColumns(10);
		readGExtend_txt.setBounds(320, 17, 70, 25);
		read_panel.add(readGExtend_txt);
		
		JPanel reference_panel = new JPanel();
		reference_panel.setToolTipText("Reference gap open, extend penalties (5,3)");
		reference_panel.setLayout(null);
		reference_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Reference gap penalty", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		reference_panel.setBackground(Color.WHITE);
		reference_panel.setBounds(12, 468, 420, 54);
		subPanel1.add(reference_panel);
		
		JLabel refGOpen_lbl = new JLabel("Gap open : ");
		refGOpen_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		refGOpen_lbl.setBounds(12, 22, 81, 15);
		reference_panel.add(refGOpen_lbl);
		
		refGOpen_txt = new JTextField();
		refGOpen_txt.setColumns(10);
		refGOpen_txt.setBounds(122, 17, 70, 25);
		reference_panel.add(refGOpen_txt);
		
		JLabel refGExtend_lbl = new JLabel("Gap extend : ");
		refGExtend_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		refGExtend_lbl.setBounds(210, 22, 90, 15);
		reference_panel.add(refGExtend_lbl);
		
		refGExtend_txt = new JTextField();
		refGExtend_txt.setColumns(10);
		refGExtend_txt.setBounds(320, 17, 70, 25);
		reference_panel.add(refGExtend_txt);
		
		JLabel ambig_lbl = new JLabel("Ambiguous read penalty : ");
		ambig_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		ambig_lbl.setBounds(22, 234, 180, 15);
		subPanel1.add(ambig_lbl);
		
		ambig_txt = new JTextField();
		ambig_txt.setColumns(10);
		ambig_txt.setBounds(220, 229, 114, 25);
		subPanel1.add(ambig_txt);
		
		JLabel next_lbl = new JLabel("");
		next_lbl.setVerticalAlignment(SwingConstants.TOP);
		next_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		next_lbl.setIcon(next_img);
		next_lbl.setBackground(Color.WHITE);
		next_lbl.setBounds(397, 548, 35, 30);
		subPanel1.add(next_lbl);
		
		JLabel skipStart_img = new JLabel("");
		skipStart_img.setIcon(info_img);
		skipStart_img.setToolTipText("Skip the first <int> reads/pairs in the input (none)");
		skipStart_img.setHorizontalAlignment(SwingConstants.LEFT);
		skipStart_img.setBounds(342, 87, 17, 20);
		subPanel1.add(skipStart_img);

		JLabel skipStop_img = new JLabel("");
		skipStop_img.setIcon(info_img);
		skipStop_img.setToolTipText("Stop after first <int> reads/pairs (no limit)");
		skipStop_img.setHorizontalAlignment(SwingConstants.LEFT);
		skipStop_img.setBounds(342, 114, 17, 20);
		subPanel1.add(skipStop_img);
		
		JLabel trim5p_img = new JLabel("");
		trim5p_img.setIcon(info_img);
		trim5p_img.setToolTipText("Trim <int> bases from 5'/left end of reads (0)");
		trim5p_img.setHorizontalAlignment(SwingConstants.LEFT);
		trim5p_img.setBounds(342, 143, 17, 20);
		subPanel1.add(trim5p_img);
		
		JLabel trim3p_img = new JLabel("");
		trim3p_img.setIcon(info_img);
		trim3p_img.setToolTipText("Trim <int> bases from 3'/right end of reads (0)");
		trim3p_img.setHorizontalAlignment(SwingConstants.LEFT);
		trim3p_img.setBounds(342, 173, 17, 20);
		subPanel1.add(trim3p_img);
		
		JLabel ambig_img = new JLabel("");
		ambig_img.setIcon(info_img);
		ambig_img.setToolTipText("Sets penalty for positions where the read, reference, or both, contain an ambiguous character such as N.(1)");
		ambig_img.setHorizontalAlignment(SwingConstants.LEFT);
		ambig_img.setBounds(342, 231, 17, 20);
		subPanel1.add(ambig_img);
		
		next_lbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				cl.show(mainPanel, "Second");
			}
		});
	}
	
	public void makeSecondPanel(){
		subPanel2.setBackground(Color.WHITE);
		subPanel2.setBounds(12, 23, 452, 590);
		subPanel2.setLayout(null);
		
		JLabel first_lbl = new JLabel("Alignment");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 65, 300, 15);
		subPanel2.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		subPanel2.add(separator);
		
		JLabel head_lbl = new JLabel("Alignment");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(360, 20, 100, 15);
		subPanel2.add(head_lbl);
		
		JLabel ignorQuality_lbl = new JLabel("Ignore all quality values : ");
		ignorQuality_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		ignorQuality_lbl.setBounds(22, 90, 180, 15);
		subPanel2.add(ignorQuality_lbl);
		
		ButtonGroup ignorQuality_bg = new ButtonGroup();
		
		ignorYes_rb = new JRadioButton("Yes");
		ignorYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		ignorYes_rb.setBackground(Color.WHITE);
		ignorYes_rb.setBounds(263, 88, 53, 23);
		
		ignorNo_rb = new JRadioButton("No");
		ignorNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		ignorNo_rb.setBackground(Color.WHITE);
		ignorNo_rb.setBounds(318, 88, 45, 23);
		
		ignorQuality_bg.add(ignorYes_rb);
		ignorQuality_bg.add(ignorNo_rb);
		
		subPanel2.add(ignorYes_rb);
		subPanel2.add(ignorNo_rb);
		
		JLabel dontAlignfor_lbl = new JLabel("Do not align forward of read(skip) : ");
		dontAlignfor_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		dontAlignfor_lbl.setBounds(22, 117, 230, 15);
		subPanel2.add(dontAlignfor_lbl);
		
		ButtonGroup dontAlignfor_bg = new ButtonGroup();
		
		dontAlignForYes_rb = new JRadioButton("Yes");
		dontAlignForYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		dontAlignForYes_rb.setBackground(Color.WHITE);
		dontAlignForYes_rb.setBounds(263, 115, 53, 23);
		
		dontAlignForNo_rb = new JRadioButton("No");
		dontAlignForNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		dontAlignForNo_rb.setBackground(Color.WHITE);
		dontAlignForNo_rb.setBounds(318, 115, 45, 23);
		
		dontAlignfor_bg.add(dontAlignForYes_rb);
		dontAlignfor_bg.add(dontAlignForNo_rb);
		
		subPanel2.add(dontAlignForYes_rb);
		subPanel2.add(dontAlignForNo_rb);
		
		JLabel dontAlignRev_lbl = new JLabel("Do not align reverse of read(skip) : ");
		dontAlignRev_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		dontAlignRev_lbl.setBounds(22, 144, 230, 15);
		subPanel2.add(dontAlignRev_lbl);
		
		ButtonGroup dontAlignRev_bg = new ButtonGroup();
		
		dontAlignRevYes_rb = new JRadioButton("Yes");
		dontAlignRevYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		dontAlignRevYes_rb.setBackground(Color.WHITE);
		dontAlignRevYes_rb.setBounds(263, 142, 53, 23);
		
		dontAlignRevNo_rb = new JRadioButton("No");
		dontAlignRevNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		dontAlignRevNo_rb.setBackground(Color.WHITE);
		dontAlignRevNo_rb.setBounds(318, 142, 45, 23);
		
		dontAlignRev_bg.add(dontAlignRevYes_rb);
		dontAlignRev_bg.add(dontAlignRevNo_rb);
		
		subPanel2.add(dontAlignRevYes_rb);
		subPanel2.add(dontAlignRevNo_rb);
		
		JLabel lblSlicedAlignment = new JLabel("Spliced alignment");
		lblSlicedAlignment.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSlicedAlignment.setBounds(12, 182, 300, 15);
		subPanel2.add(lblSlicedAlignment);
		
		
		JPanel penaltySS_panel = new JPanel();
		penaltySS_panel.setToolTipText("<html>Canonical : penalty for a canonical splice site (0)<br>Non-Canonical : Penalty for a non-canonical splice site (12)</html>");
		penaltySS_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Penalty for each splice sites", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		penaltySS_panel.setBackground(Color.WHITE);
		penaltySS_panel.setBounds(12, 252, 420, 54);
		subPanel2.add(penaltySS_panel);
		penaltySS_panel.setLayout(null);
		
		JLabel canonical_lbl = new JLabel("Canonical  : ");
		canonical_lbl.setBounds(12, 25, 80, 15);
		penaltySS_panel.add(canonical_lbl);
		canonical_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel nonCanonical_lbl = new JLabel("Non-canonical  : ");
		nonCanonical_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		nonCanonical_lbl.setBounds(210, 25, 110, 15);
		penaltySS_panel.add(nonCanonical_lbl);
		
		canonical_txt = new JTextField();
		canonical_txt.setBounds(95, 20, 80, 25);
		penaltySS_panel.add(canonical_txt);
		canonical_txt.setColumns(10);
		
		nonCanonical_txt = new JTextField();
		nonCanonical_txt.setColumns(10);
		nonCanonical_txt.setBounds(323, 20, 80, 25);
		penaltySS_panel.add(nonCanonical_txt);
		
		
		JPanel intronLen_panel = new JPanel();
		intronLen_panel.setToolTipText("<html>MIN-Length : Minimum intron length (20)<br>MAX-Length : Maximum intron length (500000)</html>");
		intronLen_panel.setLayout(null);
		intronLen_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Intron length", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		intronLen_panel.setBackground(Color.WHITE);
		intronLen_panel.setBounds(12, 330, 420, 54);
		subPanel2.add(intronLen_panel);
		
		JLabel minIntron_lbl = new JLabel("MIN-Length : ");
		minIntron_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		minIntron_lbl.setBounds(12, 25, 85, 15);
		intronLen_panel.add(minIntron_lbl);
		
		JLabel maxIntron_lbl = new JLabel("MAX-Length : ");
		maxIntron_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		maxIntron_lbl.setBounds(210, 25, 110, 15);
		intronLen_panel.add(maxIntron_lbl);
		
		minIntron_txt = new JTextField();
		minIntron_txt.setColumns(10);
		minIntron_txt.setBounds(95, 20, 80, 25);
		intronLen_panel.add(minIntron_txt);
		
		maxIntron_txt = new JTextField();
		maxIntron_txt.setColumns(10);
		maxIntron_txt.setBounds(323, 20, 80, 25);
		intronLen_panel.add(maxIntron_txt);
		
		JLabel notSpliceAlign_lbl = new JLabel("Do not spliced alignment : ");
		notSpliceAlign_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		notSpliceAlign_lbl.setBounds(22, 217, 230, 15);
		subPanel2.add(notSpliceAlign_lbl);
		
		ButtonGroup notSpliceAlign_bg = new ButtonGroup();
		
		notSpliceAlignYes_rb = new JRadioButton("Yes");
		notSpliceAlignYes_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		notSpliceAlignYes_rb.setBackground(Color.WHITE);
		notSpliceAlignYes_rb.setBounds(263, 215, 53, 23);
		subPanel2.add(notSpliceAlignYes_rb);
		
		notSpliceAlignNo_rb = new JRadioButton("No");
		notSpliceAlignNo_rb.setFont(new Font("Dialog", Font.PLAIN, 12));
		notSpliceAlignNo_rb.setBackground(Color.WHITE);
		notSpliceAlignNo_rb.setBounds(318, 215, 45, 23);
		subPanel2.add(notSpliceAlignNo_rb);
		
		notSpliceAlign_bg.add(notSpliceAlignYes_rb);
		notSpliceAlign_bg.add(notSpliceAlignNo_rb);
		
		JLabel prev_lbl = new JLabel("");
		prev_lbl.setVerticalAlignment(SwingConstants.TOP);
		prev_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		prev_lbl.setIcon(prev_img);
		prev_lbl.setBackground(Color.WHITE);
		prev_lbl.setBounds(397, 548, 35, 30);
		subPanel2.add(prev_lbl);
		
		JLabel ignorQuality_img = new JLabel("");
		ignorQuality_img.setIcon(info_img);
		ignorQuality_img.setToolTipText("Treat all quality values as 30 on Phred scale (off)");
		ignorQuality_img.setHorizontalAlignment(SwingConstants.LEFT);
		ignorQuality_img.setBounds(370, 90, 17, 20);
		subPanel2.add(ignorQuality_img);

		JLabel dontAlignRev_img = new JLabel("");
		dontAlignRev_img.setIcon(info_img);
		dontAlignRev_img.setToolTipText("Do not align forward (original) version of read (off)");
		dontAlignRev_img.setHorizontalAlignment(SwingConstants.LEFT);
		dontAlignRev_img.setBounds(370, 117, 17, 20);
		subPanel2.add(dontAlignRev_img);
		
		JLabel dontAlignfor_img = new JLabel("");
		dontAlignfor_img.setIcon(info_img);
		dontAlignfor_img.setToolTipText("Do not align reverse-complement version of read (off)");
		dontAlignfor_img.setHorizontalAlignment(SwingConstants.LEFT);
		dontAlignfor_img.setBounds(370, 144, 17, 20);
		subPanel2.add(dontAlignfor_img);
		
		JLabel notSpliceAlign_img = new JLabel("");
		notSpliceAlign_img.setIcon(info_img);
		notSpliceAlign_img.setToolTipText("Disable spliced alignment(Off)");
		notSpliceAlign_img.setHorizontalAlignment(SwingConstants.LEFT);
		notSpliceAlign_img.setBounds(370, 217, 17, 20);
		subPanel2.add(notSpliceAlign_img);
		
		prev_lbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e){}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				cl.show(mainPanel, "First");
			}
		});
	}
	

	public JPanel getPanel(){
		return mainPanel;
	}
	
	public String getHisat2Option(){
		String option = "";
		if(!skipStart_txt.getText().toString().equals("0") && cf.checkInteger(skipStart_txt.getText().toString())){
			option = "-s "+skipStart_txt.getText().toString();
		}
		if(!skipStop_txt.getText().toString().equals("0") && cf.checkInteger(skipStop_txt.getText().toString())){
			option = option+ " -u "+skipStop_txt.getText().toString();
		}
		if(!trim5p_txt.getText().toString().equals("0") && cf.checkInteger(trim5p_txt.getText().toString())){
			option = option+" -5 "+trim5p_txt.getText().toString();
		}
		if(!trim3p_txt.getText().toString().equals("0") && cf.checkInteger(trim3p_txt.getText().toString())){
			option = option+" -3 "+trim3p_txt.getText().toString();
		}
		
		if(cf.checkInteger(ambig_txt.getText().toString()) && !ambig_txt.getText().equals("1")){
			option = option+" --np "+ambig_txt.getText().toString();
		}
		if(cf.checkInteger(minMismatch_txt.getText().toString()) && cf.checkInteger(maxMismatch_txt.getText().toString())){
			if(!minMismatch_txt.getText().toString().equals("2") || !maxMismatch_txt.getText().toString().equals("6"))
				option = option+" --mp "+maxMismatch_txt.getText().toString()+","+minMismatch_txt.getText().toString();
		}
		if(cf.checkInteger(minSoftclip_txt.getText().toString()) && cf.checkInteger(maxSoftclip_txt.getText().toString())){
			if(!minSoftclip_txt.getText().toString().equals("1") || !maxSoftclip_txt.getText().toString().equals("2"))
				option = option+" --sp "+minSoftclip_txt.getText().toString()+","+maxSoftclip_txt.getText().toString();
		}
		if(cf.checkInteger(readGOpen_txt.getText().toString()) && cf.checkInteger(readGExtend_txt.getText().toString())){
			if(!readGOpen_txt.getText().toString().equals("5") || !readGExtend_txt.getText().toString().equals("3"))
				option = option+" --rdg "+readGOpen_txt.getText().toString()+","+readGExtend_txt.getText().toString();
		}
		if(cf.checkInteger(refGOpen_txt.getText().toString()) && cf.checkInteger(refGExtend_txt.getText().toString())){
			if(!refGOpen_txt.getText().toString().equals("5") || !refGExtend_txt.getText().toString().equals("3"))
				option = option+" --rfg "+refGOpen_txt.getText().toString()+","+refGExtend_txt.getText().toString();
		}
		
		if(ignorYes_rb.isSelected()){
			option = option + " --ignore-quals"; 
		}
		if(dontAlignRevYes_rb.isSelected()){
			option = option + " --norc"; 
		}
		if(dontAlignForYes_rb.isSelected()){
			option = option + " --nofw"; 
		}
		if(notSpliceAlignYes_rb.isSelected()){
			option = option + " --no-spliced-alignment"; 
		}
		
		if(cf.checkInteger(canonical_txt.getText().toString()) && !canonical_txt.getText().toString().equals("0")){
			option = option + " --pen-cansplice "+canonical_txt.getText().toString(); 
		}
		if(cf.checkInteger(nonCanonical_txt.getText().toString()) && !nonCanonical_txt.getText().toString().equals("12")){
			option = option + " --pen-noncansplice "+nonCanonical_txt.getText().toString(); 
		}
		if(cf.checkInteger(minIntron_txt.getText().toString()) && !minIntron_txt.getText().toString().equals("20")){
			option = option + " --min-intronlen "+minIntron_txt.getText().toString(); 
		}
		if(cf.checkInteger(maxIntron_txt.getText().toString()) && !maxIntron_txt.getText().toString().equals("500000")){
			option = option + " --max-intronlen "+maxIntron_txt.getText().toString(); 
		}
		
		return option;
		
	}
}
