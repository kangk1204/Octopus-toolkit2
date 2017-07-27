package Octopus_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;
import Octopus_Src.Main_Process;
import javax.swing.JRadioButton;

public class UI_Octopus_Option extends JFrame {
	private DataSet ds;
	private CommonFunc cf;
	private JLabel info_lbl;
	private JTextField cpu_txt;

	public UI_Octopus_Option(DataSet ds, CommonFunc cf, String data) {
		setTitle("Octopus Option");
		setType(Type.UTILITY);
		getContentPane().setBackground(Color.WHITE);
		this.ds = ds;
		this.cf = cf;
		getContentPane().setLayout(null);
		
		if(ds.getOS().equals("Ubuntu")){
			this.setSize(435, 610);
		}else if(ds.getOS().equals("Mint")){
			this.setSize(435, 635);
		}else{
			this.setSize(435, 645);
		}
		
		JPanel remove_panel = new JPanel();
		remove_panel.setBackground(Color.WHITE);
		remove_panel.setBorder(new TitledBorder(null, "Remove Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		remove_panel.setBounds(12, 406, 410, 132);
		getContentPane().add(remove_panel);
		remove_panel.setLayout(null);

		JCheckBox sra_chbx = new JCheckBox(" SRA : (*.sra)");
		sra_chbx.setSelected(true);
		sra_chbx.setFont(new Font("Arial", Font.PLAIN, 14));
		sra_chbx.setBackground(Color.WHITE);
		sra_chbx.setBounds(8, 25, 154, 23);
		sra_chbx.setName("SRA");
		remove_panel.add(sra_chbx);

		JCheckBox fastq_chbx = new JCheckBox(" Fastq : (*.fastq)");
		fastq_chbx.setSelected(true);
		fastq_chbx.setBackground(Color.WHITE);
		fastq_chbx.setFont(new Font("Arial", Font.PLAIN, 14));
		fastq_chbx.setBounds(166, 25, 193, 23);
		fastq_chbx.setName("Fastq");
		remove_panel.add(fastq_chbx);

		JCheckBox fastqc_chbx = new JCheckBox(" Fastqc : (*.html)");
		fastqc_chbx.setFont(new Font("Arial", Font.PLAIN, 14));
		fastqc_chbx.setBackground(Color.WHITE);
		fastqc_chbx.setBounds(8, 60, 154, 23);
		fastqc_chbx.setName("Fastqc");
		remove_panel.add(fastqc_chbx);

		JCheckBox trim_chbx = new JCheckBox(" Trimming : (Trim_*.fastq)");
		trim_chbx.setSelected(true);
		trim_chbx.setFont(new Font("Arial", Font.PLAIN, 14));
		trim_chbx.setBackground(Color.WHITE);
		trim_chbx.setBounds(166, 60, 205, 23);
		trim_chbx.setName("Trim");
		remove_panel.add(trim_chbx);

		JCheckBox bam_chbx = new JCheckBox(" BAM : (*.bam)");
		bam_chbx.setSelected(true);
		bam_chbx.setFont(new Font("Arial", Font.PLAIN, 14));
		bam_chbx.setBackground(Color.WHITE);
		bam_chbx.setBounds(8, 95, 154, 23);
		bam_chbx.setName("Bam");
		remove_panel.add(bam_chbx);

		JCheckBox sort_chbx = new JCheckBox(" Sorted_Bam : (sorted_*.bam)");
		sort_chbx.setFont(new Font("Arial", Font.PLAIN, 14));
		sort_chbx.setBackground(Color.WHITE);
		sort_chbx.setBounds(166, 95, 236, 23);
		sort_chbx.setName("Sorted_Bam");
		remove_panel.add(sort_chbx);

		JPanel info_panel = new JPanel();
		info_panel.setBorder(new LineBorder(Color.DARK_GRAY));
		info_panel.setBackground(Color.WHITE);
		info_panel.setBounds(12, 550, 296, 36);
		getContentPane().add(info_panel);

		info_lbl = new JLabel("");
		info_panel.add(info_lbl);
	
		JButton run_btn = new JButton("RUN");
		run_btn.setBounds(320, 550, 102, 36);
		getContentPane().add(run_btn);

		mouseListen(sra_chbx);
		mouseListen(fastq_chbx);
		mouseListen(fastqc_chbx);
		mouseListen(trim_chbx);
		mouseListen(bam_chbx);
		mouseListen(sort_chbx);
		
		JPanel option_panel = new JPanel();
		option_panel.setBorder(new TitledBorder(null, "Main option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		option_panel.setBackground(Color.WHITE);
		option_panel.setBounds(12, 15, 410, 220);
		getContentPane().add(option_panel);
		option_panel.setLayout(null);
		
		JCheckBox latest_chbx = new JCheckBox("Latest genome version");
		latest_chbx.setSelected(true);
		latest_chbx.setBounds(10, 25, 354, 23);
		option_panel.add(latest_chbx);
		latest_chbx.setBackground(Color.WHITE);
		
		JCheckBox continue_chbx = new JCheckBox("Skip the completed samples");
		continue_chbx.setSelected(true);
		continue_chbx.setBounds(10, 55, 354, 23);
		option_panel.add(continue_chbx);
		continue_chbx.setBackground(Color.WHITE);
		
		JLabel omit_lbl = new JLabel("Omit process : ");
		omit_lbl.setBounds(15, 90, 120, 15);
		option_panel.add(omit_lbl);
		
		JCheckBox omitTrim_chbx = new JCheckBox("Trimming (Trim_Fastq)");
		omitTrim_chbx.setBackground(Color.WHITE);
		omitTrim_chbx.setBounds(121, 86, 180, 23);
		option_panel.add(omitTrim_chbx);
		
		JCheckBox omitSort_chbx = new JCheckBox("Sorting (sorted_bam)");
		omitSort_chbx.setBackground(Color.WHITE);
		omitSort_chbx.setBounds(121, 111, 180, 23);
		option_panel.add(omitSort_chbx);
		
		JLabel cpu_lbl = new JLabel("CPU(Thread) : ");
		cpu_lbl.setBounds(15, 145, 120, 15);
		option_panel.add(cpu_lbl);
		
		JComboBox cpu_cbx = new JComboBox();
		
		Runtime rt = Runtime.getRuntime();
		int pn = rt.availableProcessors();
		String[] cpu = new String[pn+1];
		for(int i = 1; i <= (pn); i++){
			cpu[i-1] = String.valueOf(i);
		}
		cpu[pn] = "Other";
				
		cpu_cbx.setModel(new DefaultComboBoxModel(cpu));
		cpu_cbx.setSelectedIndex(pn-1);
		cpu_cbx.setBackground(Color.WHITE);
		cpu_cbx.setBounds(121, 142, 100, 24);
		option_panel.add(cpu_cbx);
		
		cpu_txt = new JTextField();
		cpu_txt.setEnabled(false);
		cpu_txt.setText("Only Integer.");
		cpu_txt.setBounds(235, 142, 114, 24);
		option_panel.add(cpu_txt);
		cpu_txt.setColumns(10);
		
		JCheckBox fullOption_chbx = new JCheckBox("Adjust all parameters for each step.");
		fullOption_chbx.setBackground(Color.WHITE);
		fullOption_chbx.setBounds(10, 180, 290, 23);
		option_panel.add(fullOption_chbx);
		
		if(ds.getFullOption()){
			fullOption_chbx.setSelected(true);
		}
		
		
		JButton fullOption_btn = new JButton("Edit");
		
		fullOption_btn.setBounds(308, 179, 80, 25);
		option_panel.add(fullOption_btn);
		
		if(fullOption_chbx.isSelected()){
			fullOption_btn.setEnabled(true);			
		}else{
			fullOption_btn.setEnabled(false);
		}
		
		ButtonGroup bg = new ButtonGroup();
		
		JPanel rnaoption_panel = new JPanel();
		rnaoption_panel.setBackground(Color.WHITE);
		rnaoption_panel.setBorder(new TitledBorder(null, "RNA-Seq option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		rnaoption_panel.setBounds(12, 245, 410, 90);
		getContentPane().add(rnaoption_panel);
		rnaoption_panel.setLayout(null);
		
		JLabel rnaAlignTool_lbl = new JLabel("Alignment tool for RNA-seq : ");
		rnaAlignTool_lbl.setBounds(10, 63, 205, 15);
		rnaoption_panel.add(rnaAlignTool_lbl);
		
		JRadioButton hisat2_btn = new JRadioButton("Hisat2");
		hisat2_btn.setBounds(212, 59, 80, 23);
		rnaoption_panel.add(hisat2_btn);
		hisat2_btn.setSelected(true);
		hisat2_btn.setBackground(Color.WHITE);
		bg.add(hisat2_btn);
		
		JRadioButton star_btn = new JRadioButton("STAR (Fast)");
		star_btn.setBounds(286, 59, 110, 23);
		rnaoption_panel.add(star_btn);
		star_btn.setBackground(Color.WHITE);
		bg.add(star_btn);
		
		JLabel public_strand2_lbl = new JLabel("(Only Public Data)");
		public_strand2_lbl.setBounds(270, 30, 130, 15);
		rnaoption_panel.add(public_strand2_lbl);
		
		JComboBox public_strand_cbx = new JComboBox();
		public_strand_cbx.setBounds(115, 25, 148, 24);
		rnaoption_panel.add(public_strand_cbx);
		public_strand_cbx.setBackground(Color.WHITE);
		public_strand_cbx.setModel(new DefaultComboBoxModel(new String[] {"Unstrand", "FR-Firststrand", "FR-Secondstrand"}));
		public_strand_cbx.setSelectedIndex(0);
		
		JLabel public_strand1_lbl = new JLabel("Strand (RNA) : ");
		public_strand1_lbl.setBounds(10, 30, 120, 15);
		rnaoption_panel.add(public_strand1_lbl);
		
		JPanel comp_panel = new JPanel();
		comp_panel.setBackground(Color.WHITE);
		comp_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Compression option", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		comp_panel.setBounds(12, 345, 410, 54);
		getContentPane().add(comp_panel);
		comp_panel.setLayout(null);
		
		JCheckBox gz_chbx = new JCheckBox("Fastq -> Fastq.gz");
		gz_chbx.setSelected(false);
		gz_chbx.setBackground(Color.WHITE);
		gz_chbx.setBounds(8, 25, 160, 23);
		comp_panel.add(gz_chbx);
		
		JCheckBox cram_chbx = new JCheckBox("Bam -> CRAM");
		cram_chbx.setBackground(Color.WHITE);
		cram_chbx.setSelected(false);
		cram_chbx.setBounds(166, 25, 160, 23);
		comp_panel.add(cram_chbx);
		
		fullOption_chbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fullOption_chbx.isSelected()){
					fullOption_btn.setEnabled(true);
				}else{
					fullOption_btn.setEnabled(false);
				}
			}
		});
		
		fullOption_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds.getMainUI().getUI_Tool_Option().setVisible(true);
				if(data.equals("Private")){
					ds.getMainUI().getUI_Tool_Option().start("QC & Trimming");
				}else{
					ds.getMainUI().getUI_Tool_Option().start("Preprocessing");	
				}
				
			}
		});
		
		cpu_cbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cpu_cbx.getSelectedItem().equals("Other")){
					cpu_txt.setEnabled(true);
					cpu_txt.setText("");
				}else{
					cpu_txt.setEnabled(false);
					cpu_txt.setText("Only Integer.");
				}
			}
		});
		
		run_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// save option.
				ds.setLatest(latest_chbx.isSelected());
				ds.setContinueanalyze(continue_chbx.isSelected());
				ds.setOmitStep(omitTrim_chbx.isSelected(), omitSort_chbx.isSelected());
				ds.setPublicStrand(public_strand_cbx.getSelectedItem().toString());				
				ds.setCompressGz(gz_chbx.isSelected());
				ds.setCompressCram(cram_chbx.isSelected());
				
				if(hisat2_btn.isSelected()){
					ds.setAlignTools("Hisat2");
				}else if(star_btn.isSelected()){
					ds.setAlignTools("STAR");
				}
				
				// save Full Parameter.
				if(fullOption_chbx.isSelected()){
					ds.getMainUI().getUI_Tool_Option().saveFullOption();
					ds.setFullOption(true);
				}else{
					ds.setFullOption(false);
				}
				
				boolean checkCpu = true;
				if (cpu_cbx.getSelectedItem().equals("Other")) {
					try {
						int num = Integer.parseInt(cpu_txt.getText());
						if(num <= 0){
							JOptionPane.showMessageDialog(null, "The number of CPU cores cannot be zero.", "Check Option", JOptionPane.INFORMATION_MESSAGE);
							checkCpu = false;
						}else{
							ds.setAnalysisCPU(cpu_txt.getText());
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "The number of CPU cores must be integer.", "Check Option",
								JOptionPane.INFORMATION_MESSAGE);
						checkCpu = false;
					}
				} else {
					ds.setAnalysisCPU(cpu_cbx.getSelectedItem().toString());
				}

				if (checkCpu == true) {
					ds.setRemove(sra_chbx.isSelected(), fastq_chbx.isSelected(), fastqc_chbx.isSelected(),
							trim_chbx.isSelected(), bam_chbx.isSelected(), sort_chbx.isSelected());
					if (data.equals("Private")) {
						ds.setRemove(0, false); // Not Exist SRA (Private)
						ds.setRemove(1, false); // Save Original Fastq (Private)
					}

					setVisible(false);
					
					ds.getMainProcess().prepareRun(data);
				}
			}
		});
		
		if(data.equals("Private")){
			sra_chbx.setEnabled(false);
			fastq_chbx.setEnabled(false);
			public_strand1_lbl.setEnabled(false);
			public_strand2_lbl.setEnabled(false);
			public_strand_cbx.setEnabled(false);
			latest_chbx.setEnabled(false);
			
		}
		
	}
	
	public void mouseListen(JCheckBox chbx) {
		chbx.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				info_lbl.setText("");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				String str = chbx.getName();
				if(str.equals("SRA")){
					info_lbl.setText("Compressed DNA sequence data.");
				}else if(str.equals("Fastq")){
					info_lbl.setText("DNA sequence data.");
				}else if(str.equals("Fastqc")){
					info_lbl.setText("Determined Quality of DNA sequence.");
				}else if(str.equals("Trim")){
					info_lbl.setText("Trimmed DNA sequence data.");
				}else if(str.equals("Bam")){
					info_lbl.setText("Mapped DNA sequence data to genome.");
				}else if(str.equals("Sorted_Bam")){
					info_lbl.setText("Sorted Bam File.");
				}
				else{
					info_lbl.setText(str);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});
	}
}
