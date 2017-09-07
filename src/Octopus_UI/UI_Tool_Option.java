package Octopus_UI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import Octopus_Option_Panel.Panel_Alignment_Hisat2;
import Octopus_Option_Panel.Panel_Alignment_STAR;
import Octopus_Option_Panel.Panel_PeakCalling;
import Octopus_Option_Panel.Panel_Preprocessing;
import Octopus_Option_Panel.Panel_QC_Trim;
import Octopus_Option_Panel.Panel_Visualization;
import Octopus_Src.DataSet;

public class UI_Tool_Option extends JFrame{
	private DataSet ds;
	private JTree tree = new JTree();
	private JTextArea jta = new JTextArea();
	private CardLayout cl = new CardLayout();
	private JPanel mainPanel;
	private Panel_Preprocessing panel_pre;
	private Panel_QC_Trim panel_qc;
	private Panel_Alignment_Hisat2 panel_align_hisat2;
	private Panel_Alignment_STAR panel_align_star;
	private Panel_Visualization panel_visual;
	private Panel_PeakCalling panel_peakcall;
	
	public UI_Tool_Option(DataSet ds){
		this.ds = ds;
		panel_pre = new Panel_Preprocessing(ds);
		panel_qc = new Panel_QC_Trim(ds);
		panel_align_hisat2 = new Panel_Alignment_Hisat2(ds);
		panel_align_star = new Panel_Alignment_STAR(ds);
		
		panel_visual = new Panel_Visualization(ds);
		panel_peakcall = new Panel_PeakCalling(ds);
		
		if(ds.getOS().equals("Ubuntu")){
			this.setSize(668,700);	
		}else if(ds.getOS().equals("Mint")){
			this.setSize(668, 725);
		}else{
			this.setSize(668,735);
		}
		this.setTitle("Octopus-toolkit full parameter");
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frm = this.getSize();
		
		int xpos = (int)(screen.getWidth()/2-frm.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2-frm.getHeight()/2);
		
		this.setLocation(xpos, ypos);
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Analysis Tools");
		DefaultMutableTreeNode step1 = new DefaultMutableTreeNode("Preprocessing");
		DefaultMutableTreeNode step2= new DefaultMutableTreeNode("QC & Trimming");
		DefaultMutableTreeNode step3 = new DefaultMutableTreeNode("Alignment");
		DefaultMutableTreeNode step4 = new DefaultMutableTreeNode("Visualization");
		DefaultMutableTreeNode step5 = new DefaultMutableTreeNode("PeakCalling");
		
		DefaultMutableTreeNode tool1 = new DefaultMutableTreeNode("TagDirectory");
		DefaultMutableTreeNode tool2 = new DefaultMutableTreeNode("MakeBigWig");
		DefaultMutableTreeNode tool3 = new DefaultMutableTreeNode("ChIP-Seq/Histone");
		DefaultMutableTreeNode tool4 = new DefaultMutableTreeNode("Peak Filter");
		DefaultMutableTreeNode tool5 = new DefaultMutableTreeNode("Other analysis");
		DefaultMutableTreeNode tool6 = new DefaultMutableTreeNode("Annotation");
		DefaultMutableTreeNode tool7 = new DefaultMutableTreeNode("Hisat2");
		DefaultMutableTreeNode tool8 = new DefaultMutableTreeNode("STAR");
				
		root.add(step1);
		root.add(step2);
		root.add(step3);
		root.add(step4);
		root.add(step5);
		step4.add(tool1);
		step4.add(tool2);
		step5.add(tool3);
		step5.add(tool4);
		step5.add(tool5);
		step5.add(tool6);
		step3.add(tool7);
		step3.add(tool8);
		
		
				
		tree = new JTree(root);
		tree.setSelectionRow(1);
		tree.setBackground(Color.WHITE);
		tree.setBorder(new LineBorder(null));
		tree.setBounds(10, 212, 200, 322);
		
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new LineBorder(null));
		
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(62, 250, 291, 257);
		mainPanel.setLayout(cl);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.08);
		splitPane.setBounds(12, 12, 644, 600);
		splitPane.setLeftComponent(tree);
		splitPane.setRightComponent(mainPanel);
		
		getContentPane().add(splitPane);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				String selectItem = tree.getLastSelectedPathComponent().toString();

				if(!(selectItem.equals("Analysis Tools") ||selectItem.equals("Alignment") || selectItem.equals("Visualization") ||selectItem.equals("PeakCalling"))){
					if(selectItem.equals("Preprocessing")){
						cl.show(mainPanel, "Preprocessing");
					}else if(selectItem.equals("QC & Trimming")){
						cl.show(mainPanel, "QC & Trimming");
					}else if(selectItem.equals("Hisat2")){
						cl.show(mainPanel, "Hisat2");
					}else if(selectItem.equals("STAR")){
						cl.show(mainPanel, "STAR");
					}else if(selectItem.equals("TagDirectory")){
						cl.show(mainPanel, "TagDirectory");
					}else if(selectItem.equals("MakeBigWig")){
						cl.show(mainPanel, "MakeBigWig");
					}else if(selectItem.equals("ChIP-Seq/Histone")){
						cl.show(mainPanel, "ChIP-Seq/Histone");
					}else if(selectItem.equals("Peak Filter")){
						cl.show(mainPanel, "Peak Filter");
					}else if(selectItem.equals("Other analysis")){
						cl.show(mainPanel, "Other analysis");
					}else if(selectItem.equals("Annotation")){
						cl.show(mainPanel, "Annotation");
					}
				}
			}
		});
		
		JPanel panel_sub = new JPanel();
		panel_sub.setBounds(12, 640, 644, 61);
		getContentPane().add(panel_sub);
		panel_sub.setLayout(null);
		
		JButton apply_btn = new JButton("Apply");
		apply_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectItem = tree.getLastSelectedPathComponent().toString();
				if(selectItem == null){
					selectItem = "Preprocessing";
				}
				
				if(selectItem.equals("Preprocessing")){
					String asperaOption = panel_pre.getAsperaOption();
					String fastqdumpOption = panel_pre.getFastqDump();
					ds.setToolOption(0, asperaOption);
					ds.setToolOption(1, fastqdumpOption);
					
				}else if(selectItem.equals("QC & Trimming")){
					String fastqcOption = panel_qc.getFastQCOption();
					String trimOption = panel_qc.getTrimmomatic();
					ds.setToolOption(2, fastqcOption);
					ds.setToolOption(3, trimOption);
					
				}else if(selectItem.equals("Hisat2") || tree.getSelectionRows()[0] == 3){
					String hisatOption = panel_align_hisat2.getHisat2Option();
					ds.setToolOption(4, hisatOption);
					
				}else if(selectItem.equals("STAR")){
					String starOption = panel_align_star.getSTAR();
					ds.setToolOption(5, starOption);
					
				}else if(selectItem.equals("TagDirectory") || tree.getSelectionRows()[0] == 4){
					String tagDirectoryOption = panel_visual.getMakeTagDirectoryOption();
					ds.setToolOption(6, tagDirectoryOption);
					
				}else if(selectItem.equals("MakeBigWig")){
					String makeBigWigOption = panel_visual.getMakeUCSCfileOption();
					ds.setToolOption(7, makeBigWigOption);
					
				}else if(selectItem.equals("ChIP-Seq/Histone") || selectItem.equals("Peak Filter") || selectItem.equals("Other analysis") || tree.getSelectionRows()[0] == 5){
					String findPeakOption = panel_peakcall.getFindPeakOption();
					ds.setToolOption(8, findPeakOption);
				
				}else if(selectItem.equals("Annotation")){
					String annotatePeakOption = panel_peakcall.getAnnotatePeakOption();
					ds.setToolOption(9, annotatePeakOption);
				}
				
				ds.setFullOption(true);
				JOptionPane.showMessageDialog(null, "The selected options applied.", "Full parameter",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		apply_btn.setBounds(372, 5, 80, 25);
		panel_sub.add(apply_btn);
		
		JButton default_btn = new JButton("Default");
		default_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectItem = tree.getLastSelectedPathComponent().toString();
				if(selectItem == null){
					selectItem = "Preprocessing";
				}
				
				if(selectItem.equals("Preprocessing")){
					panel_pre.init();
				}else if(selectItem.equals("QC & Trimming")){
					panel_qc.init();
				}else if(selectItem.equals("Hisat2") || tree.getSelectionRows()[0] == 3){
					panel_align_hisat2.init();
				}else if(selectItem.equals("STAR")){
					panel_align_star.init();
				}else if(selectItem.equals("TagDirectory") || tree.getSelectionRows()[0] == 4){
					panel_visual.initTag();
				}else if(selectItem.equals("MakeBigWig")){
					panel_visual.initBw();
				}else if(selectItem.equals("ChIP-Seq/Histone") || selectItem.equals("Peak Filter") || selectItem.equals("Other analysis") || tree.getSelectionRows()[0] == 5){
					panel_peakcall.initPeakCall();
				}else if(selectItem.equals("Annotation")){
					panel_peakcall.initAnno();
				}
				
				JOptionPane.showMessageDialog(null, "The default option selected.", "Full parameter",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		default_btn.setBounds(457, 5, 90, 25);
		panel_sub.add(default_btn);
		
		JButton close_btn = new JButton("OK");
		close_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds.getMainUI().setToolOptionVisible();
			}
		});
		close_btn.setBounds(552, 5, 80, 25);
		panel_sub.add(close_btn);
		
		getProprecessing();
		getQC();
		getAlignment();
		getVisualization();
		getPeakCalling();
		
	}
	public void start(String step){
		if(step.equals("Preprocessing")){
			tree.setSelectionRow(1);	
		}else if(step.equals("QC & Trimming")){
			tree.setSelectionRow(2);
		}else if(step.equals("Hisat2")){
			tree.setSelectionRow(3);
		}else if(step.equals("TagDirectory")){
			tree.setSelectionRow(4);
		}else if(step.equals("ChIP-Seq/Histone")){
			tree.setSelectionRow(5);
		}
		
		cl.show(mainPanel, step);
	}
	
	public void getProprecessing(){
		JPanel preprocessing_panel = panel_pre.getPanel();
		mainPanel.add("Preprocessing",preprocessing_panel);
	}
	
	public void getQC(){
		JPanel qc_panel = panel_qc.getPanel();
		mainPanel.add("QC & Trimming",qc_panel);
	}
	public void getAlignment(){
		JPanel align_panel = panel_align_hisat2.getPanel();
		JPanel align_panel2 = panel_align_star.getPanel();
		mainPanel.add("Hisat2",align_panel);
		mainPanel.add("STAR",align_panel2);
	}
	public void getVisualization(){
		JPanel tag_panel = panel_visual.getTagPanel();
		JPanel bw_Panel = panel_visual.getBwPanel();
		mainPanel.add("TagDirectory",tag_panel);
		mainPanel.add("MakeBigWig",bw_Panel);
	}
	public void getPeakCalling(){
		JPanel chipHistone_panel = panel_peakcall.getChIPHistone();
		JPanel filter_panel = panel_peakcall.getPeakFilter();
		JPanel anotherTool_panel = panel_peakcall.getAnother();
		JPanel annotation_panel = panel_peakcall.getAnnotation();
		mainPanel.add("ChIP-Seq/Histone", chipHistone_panel);
		mainPanel.add("Peak Filter",filter_panel);
		mainPanel.add("Other analysis", anotherTool_panel);
		mainPanel.add("Annotation", annotation_panel);
	}
	
	public void saveFullOption(){
		ds.setToolOption(0, panel_pre.getAsperaOption());
		ds.setToolOption(1, panel_pre.getFastqDump());
		ds.setToolOption(2, panel_qc.getFastQCOption());
		ds.setToolOption(3, panel_qc.getTrimmomatic());
		ds.setToolOption(4, panel_align_hisat2.getHisat2Option());
		ds.setToolOption(5, panel_align_star.getSTAR());
		ds.setToolOption(6, panel_visual.getMakeTagDirectoryOption());
		ds.setToolOption(7, panel_visual.getMakeUCSCfileOption());
		ds.setToolOption(8, panel_peakcall.getFindPeakOption());
		ds.setToolOption(9, panel_peakcall.getAnnotatePeakOption());	
	}
	public int getAvailableMem(){
		return panel_qc.availableMem();
	}
}
