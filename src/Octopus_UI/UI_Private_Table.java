package Octopus_UI;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;
import Octopus_Src.P_PreProcessing;

import javax.swing.ListSelectionModel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class UI_Private_Table extends JFrame {
	private DataSet ds;
	private CommonFunc cf;
	private JTable private_table;
	private DefaultTableModel model_Personal;
	private JComboBox rep_cbx;
	
	public UI_Private_Table(DataSet ds, CommonFunc cf) {

		this.ds = ds;
		this.cf = cf;

		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if(ds.getOS().equals("Ubuntu")){
			this.setSize(700, 430);			
		}else{
			this.setSize(700, 465);
		}
		this.setTitle("Octopus-toolkit");

		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		final String colNames[] = { "Multi-Lane", "Forward", "Reverse", "Genome", "Seq type","Strand" };
		model_Personal = new DefaultTableModel(null, colNames) {
			public boolean isCellEditable(int row, int colum) {
				return false;
			}
		};

		ImageIcon image = new ImageIcon("/media/ktm/Extend_HDD1/workspace/Octopus-toolkit3/Img/HelpImg.png");
		
		private_table = new JTable(model_Personal);
		private_table.setFillsViewportHeight(true);
		private_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		private_table.setBounds(31, 58, 568, 200);
		private_table.getColumn("Multi-Lane").setPreferredWidth(110);
		private_table.getColumn("Forward").setPreferredWidth(245);
		private_table.getColumn("Reverse").setPreferredWidth(245);
		private_table.getColumn("Genome").setPreferredWidth(100);
		private_table.getColumn("Seq type").setPreferredWidth(130);
		private_table.getColumn("Strand").setPreferredWidth(160);;
		private_table.getColumn("Multi-Lane").setCellRenderer(celAlignCenter);
		private_table.getColumn("Genome").setCellRenderer(celAlignCenter);
		private_table.getColumn("Seq type").setCellRenderer(celAlignCenter);
		private_table.getColumn("Strand").setCellRenderer(celAlignCenter);

		getContentPane().add(private_table);

		JScrollPane jsp2 = new JScrollPane(private_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp2.setBounds(17, 35, 668, 200);
		getContentPane().add(jsp2);

		JPanel option_panel = new JPanel();
		option_panel.setBackground(Color.WHITE);
		option_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Table Option",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		option_panel.setBounds(17, 240, 510, 155);
		getContentPane().add(option_panel);
		option_panel.setLayout(null);

		JLabel seqtype_lbl = new JLabel("Seq type : ");
		seqtype_lbl.setBounds(25, 62, 120, 15);
		option_panel.add(seqtype_lbl);
		seqtype_lbl.setFont(new Font("Dialog", Font.BOLD, 14));

		JLabel Genome_lbl = new JLabel("Genome : ");
		Genome_lbl.setBounds(25, 31, 120, 15);
		option_panel.add(Genome_lbl);
		Genome_lbl.setFont(new Font("Dialog", Font.BOLD, 14));

		JLabel rep_lbl = new JLabel("Multi-Lane : ");
		rep_lbl.setBounds(25, 95, 120, 15);
		option_panel.add(rep_lbl);
		rep_lbl.setFont(new Font("Dialog", Font.BOLD, 14));

		JComboBox genome_cbx = new JComboBox();
		genome_cbx.setBounds(130, 26, 170, 24);
		option_panel.add(genome_cbx);
		genome_cbx.setBackground(Color.WHITE);
		genome_cbx.setModel(new DefaultComboBoxModel(new String[] {"hg38", "hg19", "hg18", "mm10", "mm9", "dm6", "dm3", "sacCer3", "canFam3", "tair10", "danRer10", "ce11"}));

		JComboBox seqtype_cbx = new JComboBox();
		seqtype_cbx.setBounds(130, 58, 170, 24);
		option_panel.add(seqtype_cbx);
		seqtype_cbx.setModel(new DefaultComboBoxModel(
				new String[] { "ChIP-Seq", "RNA-Seq", "MeDIP-Seq", "ATAC-Seq", "Dnase-Seq", "Mnase-Seq" }));
		seqtype_cbx.setBackground(Color.WHITE);

		rep_cbx = new JComboBox();
		rep_cbx.setBounds(140, 90, 160, 24);
		option_panel.add(rep_cbx);
		rep_cbx.setBackground(Color.WHITE);

		JButton genome_btn = new JButton("Insert");
		genome_btn.setBounds(330, 25, 81, 25);
		option_panel.add(genome_btn);

		JButton seqtype_btn = new JButton("Insert");
		seqtype_btn.setBounds(330, 56, 81, 25);
		option_panel.add(seqtype_btn);

		JButton rep_btn = new JButton("Insert");
		rep_btn.setBounds(330, 89, 81, 25);
		option_panel.add(rep_btn);

		JCheckBox genome_chbx = new JCheckBox("all");
		genome_chbx.setBounds(420, 26, 60, 23);
		option_panel.add(genome_chbx);
		genome_chbx.setBackground(Color.WHITE);

		JCheckBox seqtype_chbx = new JCheckBox("all");
		seqtype_chbx.setBounds(420, 58, 60, 23);
		option_panel.add(seqtype_chbx);
		seqtype_chbx.setBackground(Color.WHITE);

		JCheckBox rep_chbx = new JCheckBox("all");
		rep_chbx.setBounds(420, 91, 60, 23);
		option_panel.add(rep_chbx);
		rep_chbx.setBackground(Color.WHITE);
		
		JLabel strand_lbl = new JLabel("Strand   : ");
		strand_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		strand_lbl.setBounds(25, 128, 100, 15);
		option_panel.add(strand_lbl);
		
		JComboBox strand_cbx = new JComboBox();
		strand_cbx.setModel(new DefaultComboBoxModel(new String[] {"Unstrand", "FR-Firststrand", "FR-Secondstrand"}));
		strand_cbx.setBackground(Color.WHITE);
		strand_cbx.setBounds(130, 122, 170, 24);
		option_panel.add(strand_cbx);
		
		JButton strand_btn = new JButton("Insert");
		strand_btn.setBounds(330, 120, 81, 25);
		option_panel.add(strand_btn);
		
		JCheckBox strand_chbx = new JCheckBox("all");
		strand_chbx.setBackground(Color.WHITE);
		strand_chbx.setBounds(420, 120, 60, 23);
		option_panel.add(strand_chbx);

		JPanel option2_panel = new JPanel();
		option2_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Option",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		option2_panel.setBackground(Color.WHITE);
		option2_panel.setBounds(540, 240, 145, 155);
		getContentPane().add(option2_panel);
		option2_panel.setLayout(null);

		JButton reopen_btn = new JButton("Open");
		
		reopen_btn.setBounds(24, 30, 95, 25);
		option2_panel.add(reopen_btn);

		JButton reset_btn = new JButton("Reset");
		reset_btn.setBounds(24, 70, 95, 25);
		option2_panel.add(reset_btn);

		JButton run_btn = new JButton("Run");
		run_btn.setBounds(24, 110, 95, 25);
		option2_panel.add(run_btn);
		
		JLabel help_img = new JLabel("HELP");
		help_img.setHorizontalAlignment(SwingConstants.LEFT);
		help_img.setToolTipText("Private table help.");
		help_img.setIcon(new ImageIcon(UI_Private_Table.class.getResource("/Octopus_Icon/HelpImg.png")));
		help_img.setBounds(610, 10, 80, 20);
		getContentPane().add(help_img);
		
		help_img.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/tutorial.html#private-data-basic");				
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		});

		// open
		reopen_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cf.openDialog_File_Dir();

				if (ds.getP_Fastq().size() != 0) {
					clearTable();
					makeList();
				}
			}
		});
		
		// insert
		seqtype_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row[] = private_table.getSelectedRows();

				if (seqtype_chbx.isSelected()) {
					for (int i = 0; i < private_table.getRowCount(); i++) {
						model_Personal.setValueAt(seqtype_cbx.getSelectedItem().toString(), i, 4);
						if(!seqtype_cbx.getSelectedItem().toString().equals("RNA-Seq")){
							model_Personal.setValueAt("Not use", i, 5);
						}else{
							if(model_Personal.getValueAt(i, 5) == null){
								model_Personal.setValueAt("", i, 5);
							}else if(model_Personal.getValueAt(i, 5).toString().equals("Not use")){
								model_Personal.setValueAt("", i, 5);
							}
						}
					}
				} else {
					if (row.length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table before run.", "Insert seq type",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (int i = 0; i < row.length; i++) {
							model_Personal.setValueAt(seqtype_cbx.getSelectedItem().toString(), row[i], 4);
							if(!seqtype_cbx.getSelectedItem().toString().equals("RNA-Seq")){
								model_Personal.setValueAt("Not use", row[i], 5);
							}else{
								if(model_Personal.getValueAt(row[i], 5) == null){
									model_Personal.setValueAt("", row[i], 5);
								}else if(model_Personal.getValueAt(row[i], 5).toString().equals("Not use")){
									model_Personal.setValueAt("", row[i], 5);
								}
							}
						}
					}
				}
			}
		});

		// insert
		genome_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row[] = private_table.getSelectedRows();

				if (genome_chbx.isSelected()) {
					for (int i = 0; i < private_table.getRowCount(); i++) {
						model_Personal.setValueAt(genome_cbx.getSelectedItem().toString(), i, 3);
					}
				} else {
					if (row.length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table before run.", "Insert genome",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (int i = 0; i < row.length; i++) {
							model_Personal.setValueAt(genome_cbx.getSelectedItem().toString(), row[i], 3);
						}
					}
				}
			}
		});

		// insert
		rep_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row[] = private_table.getSelectedRows();

				if (rep_chbx.isSelected()) {
					for (int i = 0; i < private_table.getRowCount(); i++) {
						model_Personal.setValueAt(rep_cbx.getSelectedItem().toString(), i, 0);
					}
				} else {
					if (row.length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table before run.", "Insert Multi-Lane",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (int i = 0; i < row.length; i++) {
							model_Personal.setValueAt(rep_cbx.getSelectedItem().toString(), row[i], 0);
						}
					}
				}
			}
		});
		
		// insert
		strand_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row[] = private_table.getSelectedRows();

				if (strand_chbx.isSelected()) {
					for (int i = 0; i < private_table.getRowCount(); i++) {
						if(model_Personal.getValueAt(i, 4).equals("RNA-Seq")){
							model_Personal.setValueAt(strand_cbx.getSelectedItem().toString(), i, 5);
						}else{
							model_Personal.setValueAt("Not use", i, 5);
						}
					}
				} else {
					if (row.length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table before run.", "Insert strand",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (int i = 0; i < row.length; i++) {
							if(model_Personal.getValueAt(row[i], 4).equals("RNA-Seq")){
								model_Personal.setValueAt(strand_cbx.getSelectedItem().toString(), row[i], 5);
							}else{
								model_Personal.setValueAt("Not use", row[i], 5);
							}
						}
					}
				}
			}
		});

		reset_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTable();
			}
		});

		run_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds.setModel(model_Personal);

				P_PreProcessing private_pre = new P_PreProcessing(ds, cf);
				private_pre.checkInputTable();

				if (private_pre.getTableFlag() == true) {
					ds.getMainUI().getUI_Analysis().setVisible(false);
					UI_Octopus_Option ui_sub = new UI_Octopus_Option(ds, cf, "Private");
					ui_sub.setVisible(true);	
				}
			}
		});

		// make Table List
		makeList();
	}

	public void makeList() {

		ArrayList<String[]> data = new ArrayList<>();

		String tmpTable[] = { "", "", "", "", "" };
		int rep = 1;
		String tmpTitle[] = null;
		String fqGz = "";
		boolean forwardFlag = false;
		String str = "";
		
		for (int i = 0; i < ds.getP_Fastq().size(); i++) {
			str = ds.getP_Fastq().get(i);

			if(forwardFlag == true){
				String tmpReverseTitle[] = str.split("_2\\.");
				if(tmpReverseTitle.length == 2){
					if(!tmpTitle[0].equals(tmpReverseTitle[0])){
						rep_cbx.addItem(rep);
						tmpTable[0] = String.valueOf(rep++);
						tmpTable[1] = tmpTitle[0]+"_1."+tmpTitle[1];
						model_Personal.addRow(tmpTable);
						tmpTable[1] = "";
					}
				}else{
					rep_cbx.addItem(rep);
					tmpTable[0] = String.valueOf(rep++);
					model_Personal.addRow(tmpTable);
					//tmpTable[1] = str;
					tmpTable[1] = "";
				}
				forwardFlag = false;
			}
			
			// Paired
			if (str.endsWith("_1.fastq") || str.endsWith("_1.fq")) {
				tmpTable[1] = str;
				tmpTitle = str.split("_1\\.");
				forwardFlag = true;
			} else if (str.endsWith("_2.fastq") || str.endsWith("_2.fq")) {
				rep_cbx.addItem(rep);
				tmpTable[0] = String.valueOf(rep++);
				
				if(tmpTable[1].equals("")){
					tmpTable[1] = str;	
				}else{
					tmpTable[2] = str;
				}
				
				model_Personal.addRow(tmpTable);
				tmpTable[1] = "";
				tmpTable[2] = "";
			} else if (str.endsWith("_1.fastq.gz") || str.endsWith("_1.fq.gz")) {
				tmpTable[1] = str;
				tmpTitle = str.split("_1\\.");
				forwardFlag = true;
			} else if (str.endsWith("_2.fastq.gz") || str.endsWith("_2.fq.gz")) {
				rep_cbx.addItem(rep);
				tmpTable[0] = String.valueOf(rep++);
				
				if(tmpTable[1].equals("")){
					tmpTable[1] = str;	
				}else{
					tmpTable[2] = str;
				}				
				
				model_Personal.addRow(tmpTable);
				tmpTable[1] = "";
				tmpTable[2] = "";
			} 
			// Single
			else if (str.endsWith(".fastq") || str.endsWith(".fq") || str.endsWith(".fastq.gz")
					|| str.endsWith(".fq.gz")) {
				rep_cbx.addItem(rep);
				tmpTable[0] = String.valueOf(rep++);
				tmpTable[1] = str;
				model_Personal.addRow(tmpTable);
				tmpTable[1] = "";
			}
		}
		
		if(!tmpTable[1].equals("")){
			rep_cbx.addItem(rep);
			tmpTable[0] = String.valueOf(rep++);
			tmpTable[1] = str;
			model_Personal.addRow(tmpTable);
			tmpTable[1] = "";
		}
	}

	public void clearTable() {
		for (int i = model_Personal.getRowCount() - 1; i >= 0; i--) {
			model_Personal.removeRow(i);
			rep_cbx.removeItemAt(i);

		}
	}

	public void resetTable() {
		for (int i = 0; i < model_Personal.getRowCount(); i++) {
			model_Personal.setValueAt((i + 1), i, 0);
			model_Personal.setValueAt("", i, 3);
			model_Personal.setValueAt("", i, 4);
			model_Personal.setValueAt("", i, 5);
		}
	}
}
