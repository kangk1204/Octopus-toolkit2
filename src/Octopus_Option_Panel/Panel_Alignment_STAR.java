package Octopus_Option_Panel;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;

public class Panel_Alignment_STAR {
	private CommonFunc cf;
	private Icon info_img = new ImageIcon(Panel_Alignment_Hisat2.class.getResource("/Octopus_Icon/ToolTip.png"));
	
	private JPanel mainPanel = new JPanel();
	private JTextField alignIntronMin_txt;
	private JTextField alignIntronMax_txt;
	private JTextField alignMatesGapMax_txt;
	private JTextField outfiltermultimapnmax_txt;
	private JTextField outfiltermismatchnmax_txt;
	private JTextField outfiltermismatchnoverlmax_txt;
	
	public void init(){
		alignIntronMin_txt.setText("21");
		alignIntronMax_txt.setText("0");
		alignMatesGapMax_txt.setText("0");
		outfiltermultimapnmax_txt.setText("10");
		outfiltermismatchnmax_txt.setText("10");
		outfiltermismatchnoverlmax_txt.setText("0.3");
		
	}
	
	public boolean checkChangeValue(){
		
		if(!alignIntronMin_txt.getText().equals("21") || !alignIntronMax_txt.getText().equals("0") || !alignMatesGapMax_txt.getText().equals("0")){
			return true;
		}
		
		if(!outfiltermultimapnmax_txt.getText().equals("10") || !outfiltermismatchnmax_txt.getText().equals("10") || !outfiltermismatchnoverlmax_txt.getText().equals("0.3")){
			return true;
		}
		return false;
	}
	
	public Panel_Alignment_STAR(DataSet ds){
		cf = new CommonFunc(ds);
		mainPanel.setBorder(new LineBorder(UIManager.getColor("Button.darkShadow")));
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(12, 23, 452, 442);
		mainPanel.setLayout(null);
		
		JLabel first_lbl = new JLabel("Alignment");
		first_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		first_lbl.setBounds(12, 69, 120, 15);
		mainPanel.add(first_lbl);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(UIManager.getColor("Button.darkShadow"));
		separator.setBounds(1, 45, 474, 12);
		mainPanel.add(separator);
		
		JLabel head_lbl = new JLabel("Alignment(STAR)");
		head_lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		head_lbl.setBounds(300, 20, 150, 15);
		mainPanel.add(head_lbl);
		
		JLabel alignIntronMin_lbl = new JLabel("AlignIntronMin : ");
		alignIntronMin_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignIntronMin_lbl.setBounds(30, 100, 110, 15);
		mainPanel.add(alignIntronMin_lbl);
		
		JLabel alignIntronMax_lbl = new JLabel("AlignIntronMax : ");
		alignIntronMax_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignIntronMax_lbl.setBounds(30, 135, 110, 15);
		mainPanel.add(alignIntronMax_lbl);
		
		JLabel alignMatesGapMax_lbl = new JLabel("AlignMatesGapMax : ");
		alignMatesGapMax_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		alignMatesGapMax_lbl.setBounds(30, 170, 130, 15);
		mainPanel.add(alignMatesGapMax_lbl);
		
		
		alignIntronMin_txt = new JTextField();
		
		alignIntronMin_txt.setBounds(160, 95, 120, 25);
		mainPanel.add(alignIntronMin_txt);
		alignIntronMin_txt.setColumns(10);
		
		alignIntronMax_txt = new JTextField();
	
		alignIntronMax_txt.setColumns(10);
		alignIntronMax_txt.setBounds(160, 130, 120, 25);
		mainPanel.add(alignIntronMax_txt);
		
		alignMatesGapMax_txt = new JTextField();
	
		alignMatesGapMax_txt.setColumns(10);
		alignMatesGapMax_txt.setBounds(160, 165, 120, 25);
		mainPanel.add(alignMatesGapMax_txt);
		
		JLabel second_lbl = new JLabel("Output Filtering");
		second_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		second_lbl.setBounds(12, 215, 140, 15);
		mainPanel.add(second_lbl);
		
		JLabel outfiltermultimapnmax_lbl = new JLabel("OutFilterMultimapNmax : ");
		outfiltermultimapnmax_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		outfiltermultimapnmax_lbl.setBounds(30, 242, 160, 15);
		mainPanel.add(outfiltermultimapnmax_lbl);
		
		JLabel outfiltermismatchnmax_lbl = new JLabel("OutFilterMismatchNmax : ");
		outfiltermismatchnmax_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		outfiltermismatchnmax_lbl.setBounds(30, 277, 160, 15);
		mainPanel.add(outfiltermismatchnmax_lbl);
		
		JLabel outfiltermismatchnoverlmax_lbl = new JLabel("OutFilterMismatchNoverLmax : ");
		outfiltermismatchnoverlmax_lbl.setFont(new Font("Dialog", Font.PLAIN, 12));
		outfiltermismatchnoverlmax_lbl.setBounds(30, 312, 195, 15);
		mainPanel.add(outfiltermismatchnoverlmax_lbl);
		
		outfiltermultimapnmax_txt = new JTextField();
		
		outfiltermultimapnmax_txt.setColumns(10);
		outfiltermultimapnmax_txt.setBounds(221, 237, 120, 25);
		mainPanel.add(outfiltermultimapnmax_txt);
		
		outfiltermismatchnmax_txt = new JTextField();
	
		outfiltermismatchnmax_txt.setColumns(10);
		outfiltermismatchnmax_txt.setBounds(221, 272, 120, 25);
		mainPanel.add(outfiltermismatchnmax_txt);
		
		outfiltermismatchnoverlmax_txt = new JTextField();
		
		outfiltermismatchnoverlmax_txt.setColumns(10);
		outfiltermismatchnoverlmax_txt.setBounds(221, 307, 120, 25);
		mainPanel.add(outfiltermismatchnoverlmax_txt);
		
		
		JLabel alignIntronMin_img = new JLabel("");
		alignIntronMin_img.setHorizontalAlignment(SwingConstants.LEFT);
		alignIntronMin_img.setToolTipText("default: 21 , minimum intron size: genomic gap is considered intron if its\nlength>=alignIntronMin, otherwise it is considered Deletion");
		alignIntronMin_img.setIcon(info_img);
		alignIntronMin_img.setBounds(285, 97, 17, 20);
		mainPanel.add(alignIntronMin_img);
		
		JLabel alignIntronMax_img = new JLabel("");
		alignIntronMax_img.setToolTipText("default: 0 , maximum intron size, if 0, max intron size will be determined by\n(2ˆwinBinNbits)*winAnchorDistNbins");
		alignIntronMax_img.setHorizontalAlignment(SwingConstants.LEFT);
		alignIntronMax_img.setIcon(info_img);
		alignIntronMax_img.setBounds(285, 132, 17, 20);
		mainPanel.add(alignIntronMax_img);
		
		JLabel alignMatesGapMax_img = new JLabel("");
		alignMatesGapMax_img.setToolTipText("default: 0 , maximum gap between two mates, if 0, max intron gap will be determined by (2ˆwinBinNbits)*winAnchorDistNbins");
		alignMatesGapMax_img.setHorizontalAlignment(SwingConstants.LEFT);
		alignMatesGapMax_img.setIcon(info_img);
		alignMatesGapMax_img.setBounds(285, 167, 17, 20);
		mainPanel.add(alignMatesGapMax_img);
		
		JLabel outfiltermultimapnmax_img = new JLabel("");
		outfiltermultimapnmax_img.setToolTipText("default: 10 , int: maximum number of loci the read is allowed to map to. Alignments (all of them) will be output only if the read maps to no more loci than this value. Otherwise no alignments will be output, and the read will be counted as ”mapped to too many loci” in the Log.final.out.");
		outfiltermultimapnmax_img.setHorizontalAlignment(SwingConstants.LEFT);
		outfiltermultimapnmax_img.setIcon(info_img);
		outfiltermultimapnmax_img.setBounds(350, 239, 17, 20);
		mainPanel.add(outfiltermultimapnmax_img);
		
		JLabel outfiltermismatchnmax_img = new JLabel("");
		outfiltermismatchnmax_img.setToolTipText("default: 10 , int: alignment will be output only if it has no more mismatches than this value.");
		outfiltermismatchnmax_img.setHorizontalAlignment(SwingConstants.LEFT);
		outfiltermismatchnmax_img.setIcon(info_img);
		outfiltermismatchnmax_img.setBounds(350, 274, 17, 20);
		mainPanel.add(outfiltermismatchnmax_img);
		
		JLabel outfiltermismatchnoverlmax_img = new JLabel("");
		outfiltermismatchnoverlmax_img.setToolTipText("default: 0.3 , float: alignment will be output only if its ratio of mismatches to *mapped* length is less than or equal to this value.");
		outfiltermismatchnoverlmax_img.setHorizontalAlignment(SwingConstants.LEFT);
		outfiltermismatchnoverlmax_img.setIcon(info_img);
		outfiltermismatchnoverlmax_img.setBounds(350, 309, 17, 20);
		mainPanel.add(outfiltermismatchnoverlmax_img);
		
		// initation. 
		init();
	}
	public JPanel getPanel(){
		return mainPanel;
	}
		
	public String getSTAR(){
		String option = "";
		if(cf.checkInteger(alignIntronMin_txt.getText().toString()) && !alignIntronMin_txt.getText().toString().equals("21")){
			option = "--alignIntronMin "+alignIntronMin_txt.getText().toString();
		}
		if(cf.checkInteger(alignIntronMax_txt.getText().toString()) && !alignIntronMax_txt.getText().toString().equals("0")){
			option = option + " --alignIntronMax "+alignIntronMax_txt.getText().toString();
		}
		if(cf.checkInteger(alignMatesGapMax_txt.getText().toString()) && !alignMatesGapMax_txt.getText().toString().equals("0")){
			option = option + " --alignMatesGapMax "+alignMatesGapMax_txt.getText().toString();
		}
		if(cf.checkInteger(outfiltermultimapnmax_txt.getText().toString()) && !outfiltermultimapnmax_txt.getText().toString().equals("10")){
			option = option + " --outFilterMultimapNmax "+outfiltermultimapnmax_txt.getText().toString();
		}
		if(cf.checkInteger(outfiltermismatchnmax_txt.getText().toString()) && !outfiltermismatchnmax_txt.getText().toString().equals("10")){
			option = option + " --outFilterMismatchNmax "+outfiltermismatchnmax_txt.getText().toString();
		}
		if(cf.checkDouble(outfiltermismatchnoverlmax_txt.getText().toString()) && !outfiltermismatchnoverlmax_txt.getText().toString().equals("0.3")){
			option = option + " --outFilterMismatchNoverLmax "+outfiltermismatchnoverlmax_txt.getText().toString();
		}
		
		return option;
	}
	
}
