package Octopus_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import Octopus_Src.CheckProgram;
import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;
import Octopus_Src.HeatmapPlot;
import Octopus_Src.Main_Process;
import Octopus_Src.PeakCalling;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;

public class UI_Octopus extends JFrame implements WindowListener{
	DataSet ds;
	CommonFunc cf;
	CheckProgram cp;
	Main_Process mp;
	UI_Tool_Option ui_tool;
	private JTextField input_TextField;
	private Boolean guide_Flag = true;
		
	private JLabel prBar_Lbl;
	private JLabel simple_Lbl;
	private JButton preprocessing_Btn;
	private JButton quality_Btn;
	private JButton mapping_Btn;
	private JButton visual_Btn;
	private JLabel gse_label;
	private UI_Private_Table ui_Pt;
	private UI_Octopus_Option ui_Oo;
	private UI_FindPeak_Table ui_fp;
	private UI_IGV_Table ui_igv;
	private UI_Graph_Table ui_g;
	private JProgressBar prBar;
	private JProgressBar prBar_run;
	private boolean runThread = false;
	private JScrollPane jsp;
	private JTextArea msg_Area;
	private JEditorPane editor;
	private JLabel guide_Lbl;
	
	public UI_Octopus(DataSet ds, CommonFunc cf,CheckProgram cp){
		getContentPane().setBackground(Color.WHITE);
		this.ds = ds;
		this.cf = cf;
		this.cp = cp;
		ui_tool = new UI_Tool_Option(ds);
		mp = new Main_Process(ds, cf);
		ds.setMainProcess(mp);
		setResizable(false);
		getContentPane().setLayout(null);
		
		if(ds.getOS().equals("Ubuntu")){
			this.setSize(390,550);	
		}else{
			this.setSize(390,565);
		}
		
		this.setLocation(100, 50);
		this.addWindowListener(this);
		this.setTitle("Octopus-toolkit");		
		
		setMainPane();
		
	}
	
	public void setMainPane(){
		JLabel input_Lbl = new JLabel("Input : ");
		input_Lbl.setFont(new Font("Dialog", Font.BOLD, 15));
		input_Lbl.setBounds(14, 17, 150, 15);
		getContentPane().add(input_Lbl);
		
		input_TextField = new JTextField();
		input_TextField.setHorizontalAlignment(SwingConstants.LEFT);
		input_TextField.setColumns(20);
		input_TextField.setBackground(Color.WHITE);
		input_TextField.setBounds(78, 12, 213, 25);
		getContentPane().add(input_TextField);
		
		JButton open_Btn = new JButton("OPEN");
		
		open_Btn.setBounds(303, 12, 75, 25);
		getContentPane().add(open_Btn);
		
		JButton run_Btn = new JButton("RUN");
		
		run_Btn.setBounds(303, 47, 75, 25);
		getContentPane().add(run_Btn);
		
		guide_Lbl = new JLabel("▼ Detail");
		guide_Lbl.setFont(new Font("Dialog", Font.BOLD, 12));
		guide_Lbl.setBounds(12, 52, 80, 15);
		getContentPane().add(guide_Lbl);
		guide_Lbl.setName("guide_Lbl");
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 86, 366, 170);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		prBar = new JProgressBar();
		prBar.setBackground(UIManager.getColor("Button.highlight"));
		prBar.setStringPainted(true);
		
		prBar.setBounds(12, 125, 342, 30);
		panel.add(prBar);
		
		prBar_run = new JProgressBar();
		prBar_run.setBackground(UIManager.getColor("Button.light"));
		prBar_run.setForeground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		prBar_run.setEnabled(false);
		prBar_run.setBounds(12, 155, 342, 8);
		panel.add(prBar_run);
		
		prBar_Lbl = new JLabel("Click open button.");
		prBar_Lbl.setBackground(Color.WHITE);
		prBar_Lbl.setHorizontalAlignment(SwingConstants.CENTER);
		prBar_Lbl.setBounds(12, 105, 342, 15);
		panel.add(prBar_Lbl);
		
		preprocessing_Btn = new JButton("");
		preprocessing_Btn.setBackground(Color.WHITE);
		preprocessing_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Preprocessing.png")));
		preprocessing_Btn.setBounds(15, 12, 67, 73);
		preprocessing_Btn.setName("preprocessing_Btn");
		panel.add(preprocessing_Btn);
		
		quality_Btn = new JButton("");
		quality_Btn.setBackground(Color.WHITE);
		quality_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/QC.png")));
		quality_Btn.setBounds(105, 12, 67, 73);
		quality_Btn.setName("quality_Btn");
		panel.add(quality_Btn);
		
		mapping_Btn = new JButton("");
		mapping_Btn.setBackground(Color.WHITE);
		mapping_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Mapping.png")));
		mapping_Btn.setBounds(195, 12, 67, 73);
		mapping_Btn.setName("mapping_Btn");
		panel.add(mapping_Btn);
		
		visual_Btn = new JButton("");
		visual_Btn.setBackground(Color.WHITE);
		visual_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Visualization.png")));
		visual_Btn.setBounds(285, 12, 67, 73);
		visual_Btn.setName("visual_Btn");
		panel.add(visual_Btn);
		
		mouseListen(guide_Lbl);
		mouseListen(preprocessing_Btn);
		mouseListen(quality_Btn);
		mouseListen(mapping_Btn);
		mouseListen(visual_Btn);
		
		gse_label = new JLabel("");
		gse_label.setHorizontalAlignment(SwingConstants.CENTER);
		gse_label.setBackground(Color.WHITE);
		gse_label.setBounds(12, 90, 342, 15);
		panel.add(gse_label);
		
		JPanel simple_panel = new JPanel();
		
		simple_panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		simple_panel.setBackground(Color.WHITE);
		simple_panel.setBounds(78, 44, 213, 25);
		getContentPane().add(simple_panel);
		
		simple_Lbl = new JLabel("Click open button");
		simple_panel.add(simple_Lbl);
		
		JPanel runinfo_Panel = new JPanel();
		runinfo_Panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Running Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		runinfo_Panel.setBackground(Color.WHITE);
		runinfo_Panel.setBounds(12, 270, 366, 220);
		getContentPane().add(runinfo_Panel);
		runinfo_Panel.setLayout(null);

		editor = new JEditorPane();
		editor.setBackground(Color.WHITE);
		editor.setEditable(false);
		editor.setEditorKit(JEditorPane.createEditorKitForContentType("text/html"));

		jsp = new JScrollPane(editor);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jsp.setBounds(8, 30, 350, 180);
		runinfo_Panel.add(jsp);
		
		// add Menu Bar
		setMenuBar();
		
		// Open Button
		open_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(runThread == false){
					cf.openDialog_File(input_TextField,"");
				}
			}
		});
		// Run Button
		run_Btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (runThread == false) {
					if (input_TextField.getText().equals("")) {
						if (runThread == false) {
							cf.openDialog_File(input_TextField,"");
							if (!input_TextField.getText().equals("")) {
								ds.setInputText(input_TextField.getText().replace(" ", ""));
								ui_Oo = new UI_Octopus_Option(ds, cf, "Public");
								ui_Oo.setVisible(true);
							}
						}
					} else {
						ds.setInputText(input_TextField.getText().replace(" ", ""));
						ui_Oo = new UI_Octopus_Option(ds, cf, "Public");
						ui_Oo.setVisible(true);
					}
				}
			}
		});
	}
	
	
	public void windowSetSize(int x, int y){
		this.setSize(x,y);
	}
	
	public void setMenuBar(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnAnalysis = new JMenu("Analysis");
		menuBar.add(mnAnalysis);
		
		JMenuItem mntmPrivateData = new JMenuItem("Private Data");
		mnAnalysis.add(mntmPrivateData);
		
		JMenuItem mntmPeakCalling = new JMenuItem("Peak Calling");
		mnAnalysis.add(mntmPeakCalling);
		
		JMenuItem mntmGraph = new JMenuItem("Graph");
		mnAnalysis.add(mntmGraph);
		
		JMenuItem mntmIgv = new JMenuItem("IGV");
		mnAnalysis.add(mntmIgv);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmManualtutorial = new JMenuItem("Manual (Tutorial)");
		mnHelp.add(mntmManualtutorial);
		
		JMenuItem mntmErrorCode = new JMenuItem("Error Code");
		mnHelp.add(mntmErrorCode);
		
		JMenuItem mntmOctopustoolkit = new JMenuItem("Homepage");
		mnHelp.add(mntmOctopustoolkit);
		
		
		//Exit - Menu
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(runThread == true){
					int checkResult = JOptionPane.showConfirmDialog(null,"Octopus-toolkit is running...\nDo you want to terminate?","Octopus-toolkit", JOptionPane.YES_NO_OPTION);
					if(checkResult == 0){
						ds.closeLog();
						System.exit(0);
					}	
				}else{
					ds.closeLog();
					System.exit(0);
				}
			}
		});
		
		
		// Private 
		mntmPrivateData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (runThread == false) {
					cf.openDialog_File_Dir();

					if (ds.getP_Fastq().size() != 0) {
						ui_Pt = new UI_Private_Table(ds, cf);
						ui_Pt.setVisible(true);
					}else{
						JOptionPane.showMessageDialog(null, "No such file or directory. (Fastq format, fq, fastq.gz or fq.gz)",
								"File Open", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		// Peak Calling
		mntmPeakCalling.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (runThread == false) {

					cf.openDialog_Dir("PeakCalling");
					if (ds.getTagInfo().size() != 0) {
						ui_fp = new UI_FindPeak_Table(ds, cf);
						ui_fp.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "No such file or directory. (TagDirectory : 03_Tag)",
								"File Open", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		// Graph
		mntmGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(runThread == false){
					
					if (!cf.checkRequiredTool()) {
					//	System.exit(0);
					} else {
						if (cp.checkRPackage() == false) {

							Thread auto = new Thread() {
								public void run() {
									setRunningPrograss(true);
									ui_g = new UI_Graph_Table(ds, cf);
									setProgress(65,"Installed : R Packages");
									setRunningPrograss(true);
									cp.downLoadTool("R", ds.getPath());
								
								}
							};
							
							auto.start();
						
						}else{
							cf.openDialog_Dir("Graph");
							if (ds.getBigWigInfo().size() != 0) {
								ui_g = new UI_Graph_Table(ds, cf);
								ui_g.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(null, "No such file or directory. (BigWig Format)",
										"File Open", JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
				}
			}
		});
		
		// IGV
		mntmIgv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (runThread == false) {
					cf.openDialog_Dir("IGV");
					if (ds.getBigWigInfo().size() != 0) {
						ui_igv = new UI_IGV_Table(ds, cf);
						ui_igv.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "No such file or directory. (BigWig Format)", "File Open",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		
		mntmManualtutorial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Open Web Site
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/tutorial.html");
			}
		});
		
		
		mntmErrorCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/error.html");
			}
		});
		
		mntmOctopustoolkit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/");
			}
		});
		
		editor.addHyperlinkListener(new HyperlinkListener() {
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				HyperlinkEvent.EventType type = e.getEventType();

				if (type == HyperlinkEvent.EventType.ACTIVATED) {
					String tmpURL = e.getURL().toString();
					cf.openURL(tmpURL);
				}
			}
		});
	}

	public void setProgress(int value, String label){
		//33
		prBar.setValue(value);
		
		String tmp[] = label.split(" : ");
		
		if(!label.contains("Error")){
			String str = tmp[1];
			if(str.length() > 6){
				str = str.substring(0, 7)+"..";
			}
			if(value < 100){
				simple_Lbl.setText("Analyzing : "+str+" ("+ds.getMainProcess().cnt+"/"+ds.getMainProcess().cntTotal+")");	
			}else{
				simple_Lbl.setText("Completed : "+str+" ("+ds.getMainProcess().cnt+"/"+ds.getMainProcess().cntTotal+")");
			}
		}
		if(label.length() >= 33){
			prBar_Lbl.setText(label.substring(0, 33)+"... ("+ds.getMainProcess().cnt+"/"+ds.getMainProcess().cntTotal+")");
		}else{
			prBar_Lbl.setText(label+" ("+ds.getMainProcess().cnt+"/"+ds.getMainProcess().cntTotal+")");	
		}
	}
	public void setRunInfoWindowLog(String str) {
		String preStr = editor.getText().replace("\n ", "");
		String htmlStr1[] = preStr.split("<body>   ");
		String htmlStr2[] = htmlStr1[1].split(" </body>");
		String writeStr;
		if(htmlStr2[0].charAt(0) == '<'){
			writeStr = str.toString();
		}else{
			writeStr = htmlStr2[0] + str.toString();			
		}
		editor.setText(writeStr+"<br>");
		
		int pos = editor.getDocument().getLength();;
		editor.setCaretPosition(pos);		
		
	}
	
	
	public void peakcalling(){
		Thread autoPeakCalling = new Thread() {
			public void run() {
				
				ds.getMainUI().setRunThread(true);
				ds.getMainUI().setRunningPrograss(true);
				
				PeakCalling pc = new PeakCalling(ds, cf);
				pc.use_findPeak();
				
				ds.getMainUI().setRunningPrograss(false);
				ds.getMainUI().setRunThread(false);
			}
		};
		autoPeakCalling.start();
	}
	public void makeGraph(){
		Thread autoGraph = new Thread() {
			public void run() {
				
				ds.getMainUI().setRunThread(true);
				ds.getMainUI().setRunningPrograss(true);
				
				HeatmapPlot hp = new HeatmapPlot(ds, cf);
				hp.makeGraph();
			
				ds.getMainUI().setRunningPrograss(false);
				ds.getMainUI().setRunThread(false);
			}
		};
		autoGraph.start();
	}
	
	public void setSimpleLbl(String str){
		simple_Lbl.setText(str);
	}
	public void setGseLabel(String label){
		gse_label.setText(label);
	}
	public void setRunningPrograss(boolean flag){
		prBar_run.setIndeterminate(flag);
	}	
	public UI_Private_Table getUI_Analysis(){
		return ui_Pt;
	}
	public UI_Octopus_Option getUI_Octopus_Option(){
		return ui_Oo;
	}
	public UI_FindPeak_Table getUI_FindPeak(){
		return ui_fp;
	}
	public UI_IGV_Table getUI_IGV(){
		return ui_igv;
	}
	public UI_Graph_Table getUI_Graph(){
		return ui_g;
	}
	public UI_Tool_Option getUI_Tool_Option(){
		return ui_tool;
	}
	public boolean getRunThread(){
		return runThread;
	}
	public void setRunThread(boolean flag){
		runThread = flag;
	}
	public void setToolOptionVisible(){
		ui_tool.setVisible(false);
	}
	

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		ds.closeLog();
		System.exit(0);
	}
	
	public void mouseListen(JButton btn) {
		btn.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {
				if(btn.getName().equals("preprocessing_Btn")){
					preprocessing_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Preprocessing.png")));
				}else if(btn.getName().equals("quality_Btn")){
					quality_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/QC.png")));
				}else if(btn.getName().equals("mapping_Btn")){
					mapping_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Mapping.png")));
				}else if(btn.getName().equals("visual_Btn")){
					visual_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Visualization.png")));
				}
			}
			public void mouseEntered(MouseEvent e) {
				if(btn.getName().equals("preprocessing_Btn")){
					preprocessing_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Preprocessing_Info.png")));
				}else if(btn.getName().equals("quality_Btn")){
					quality_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/QC_Info.png")));		
				}else if(btn.getName().equals("mapping_Btn")){
					mapping_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Mapping_Info.png")));
				}else if(btn.getName().equals("visual_Btn")){
					visual_Btn.setIcon(new ImageIcon(UI_Octopus.class.getResource("/Octopus_Icon/Visualization_Info.png")));
				}
			}
			public void mouseClicked(MouseEvent e) {
				if(btn.getName().equals("preprocessing_Btn")){
					ui_tool.setVisible(true);
					ui_tool.start("Preprocessing");				
				}else if(btn.getName().equals("quality_Btn")){
					ui_tool.setVisible(true);
					ui_tool.start("QC & Trimming");					
				}else if(btn.getName().equals("mapping_Btn")){
					ui_tool.setVisible(true);
					ui_tool.start("Hisat2");					
				}else if(btn.getName().equals("visual_Btn")){
					ui_tool.setVisible(true);
					ui_tool.start("TagDirectory");
				}
			}
		});
	}
	public void mouseListen(JLabel lbl) {
		lbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				if(guide_Flag == false){
					windowSetSize(390, 530);
					guide_Lbl.setText("▼ Detail");
					guide_Flag = true;
				}else{
					windowSetSize(390, 105);
					guide_Lbl.setText("▲ Detail");
					guide_Flag = false;
				}
				
			}
		});
	}
	
	
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
}