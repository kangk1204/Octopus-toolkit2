package Octopus_UI;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Octopus_Src.CommonFunc;
import Octopus_Src.DataSet;
import java.awt.Font;
import java.awt.Point;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class UI_Graph_Table extends JFrame{
	private DataSet ds;
	private CommonFunc cf;
	private JTable graph_table;
	private DefaultTableModel model_Personal;
	private Vector<String> sample;
	private JList<String> list;
	private JComboBox bed_cbx;
	
	public UI_Graph_Table(DataSet ds, CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		if(ds.getOS().equals("Ubuntu")){
			this.setSize(505,550);					
		}else{
			this.setSize(505,585);		
		}
		this.setTitle("Octopus-toolkit");
		
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		final String colNames[] = {"Sample","Seq type","Genome"};
		model_Personal = new DefaultTableModel(null,colNames){
			public boolean isCellEditable(int row,int colum){
				return false;
			}
		};
		
		graph_table = new JTable(model_Personal);
		graph_table.setModel(model_Personal);
		graph_table.setFillsViewportHeight(true);
		graph_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		graph_table.setBounds(31, 58, 450, 200);
		graph_table.getColumn("Sample").setPreferredWidth(280);
		graph_table.getColumn("Seq type").setPreferredWidth(70);
		graph_table.getColumn("Genome").setPreferredWidth(70);
		graph_table.getColumn("Seq type").setCellRenderer(celAlignCenter);
		graph_table.getColumn("Genome").setCellRenderer(celAlignCenter);
		
		getContentPane().add(graph_table);
		
		JScrollPane table_jsp = new JScrollPane(graph_table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table_jsp.setBounds(17, 70, 470, 200);
		getContentPane().add(table_jsp);
		
		JLabel lblAnnotation = new JLabel("Annotation (bed) : ");
		lblAnnotation.setFont(new Font("Dialog", Font.BOLD, 14));
		lblAnnotation.setBounds(17, 40, 150, 15);
		getContentPane().add(lblAnnotation);
		
		bed_cbx = new JComboBox();
		bed_cbx .setBackground(Color.WHITE);
		bed_cbx .setBounds(174, 35, 310, 24);
		getContentPane().add(bed_cbx);
		
		
		JPanel sample_panel = new JPanel();
		sample_panel.setBackground(Color.WHITE);
		sample_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Sample (bigWig)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		sample_panel.setBounds(17, 280, 470, 140);
		getContentPane().add(sample_panel);
		sample_panel.setLayout(null);
		
		sample = new Vector<String>();
		
		list = new JList<String>(sample);
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
		list.setBounds(10, 20, 300, 108);
		sample_panel.add(list);
		
		JScrollPane list_jsp = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		list_jsp.setBounds(10, 20, 300, 108);
		sample_panel.add(list_jsp);	
		
		JButton reopen_btn = new JButton("Open");
		
		reopen_btn.setBounds(320, 20, 95, 25);
		sample_panel.add(reopen_btn);
		
		JButton insert_btn = new JButton("Insert");
		insert_btn.setBounds(320, 60, 95, 25);
		sample_panel.add(insert_btn);
		
		JButton delete_btn = new JButton("Delete");
		delete_btn.setBounds(320, 98, 95, 25);
		sample_panel.add(delete_btn);
		
		JCheckBox insert_chbx = new JCheckBox("all");
		insert_chbx.setBackground(Color.WHITE);
		insert_chbx.setBounds(420, 60, 45, 23);
		sample_panel.add(insert_chbx);
		
		JCheckBox delete_chbx = new JCheckBox("all");
		delete_chbx.setBackground(Color.WHITE);
		delete_chbx.setBounds(420, 98, 45, 23);
		sample_panel.add(delete_chbx);
		
		JPanel graph_option = new JPanel();
		graph_option.setLayout(null);
		graph_option.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Table Option", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		graph_option.setBackground(Color.WHITE);
		graph_option.setBounds(17, 430, 470, 82);
		getContentPane().add(graph_option);
		
		JLabel tss_lbl = new JLabel("TSS Region : ");
		tss_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		tss_lbl.setBounds(20, 25, 120, 15);
		graph_option.add(tss_lbl);
		
		JLabel bin_lbl = new JLabel("Number of BINs : ");
		bin_lbl.setBounds(20, 52, 130, 15);
		graph_option.add(bin_lbl);
		
		JComboBox tss_cbx = new JComboBox();
		tss_cbx.setModel(new DefaultComboBoxModel(new String[] {"1000", "2000", "5000", "10000"}));
		tss_cbx.setBackground(Color.WHITE);
		tss_cbx.setBounds(158, 20, 150, 24);
		graph_option.add(tss_cbx);
		
		JComboBox bin_cbx = new JComboBox();
		bin_cbx.setBackground(Color.WHITE);
		bin_cbx.setModel(new DefaultComboBoxModel(new String[] {"50", "100", "200"}));
		bin_cbx.setBounds(158, 47, 150, 24);
		graph_option.add(bin_cbx);
		
		JButton btnRun = new JButton("Run");
		btnRun.setBounds(320, 47, 95, 25);
		graph_option.add(btnRun);
		
		JLabel help_img = new JLabel("HELP");
		help_img.setToolTipText("Graph table help.");
		help_img.setIcon(new ImageIcon(UI_Graph_Table.class.getResource("/Octopus_Icon/HelpImg.png")));
		help_img.setBounds(425, 10, 60, 18);
		getContentPane().add(help_img);
		
		help_img.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/tutorial.html#graph");
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		});
		
		// RE-Open
		reopen_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cf.openDialog_Dir("Graph");
				if(ds.getBigWigInfo().size() != 0){
					clearTable();
					makeList();
				}else{
					JOptionPane.showMessageDialog(null, "No such file or directory. (BigWig Format)", "File Open",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		// Insert button
		insert_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCnt = model_Personal.getRowCount();
				String tmp[] = new String[3];
				for(int k = 0; k < 3; k++){
					tmp[k] = "";
				}
				
				if (insert_chbx.isSelected()) {
					// Insert All
					for(int i = 0; i < sample.size(); i++){
						int idx = Integer.parseInt(ds.getHmBigWigInfo((String) sample.get(i)));
						tmp[0] = ds.getBigWigInfo().get(idx)[0];
						tmp[1] = cf.convertGenome(ds.getBigWigInfo().get(idx)[3]);
						tmp[2] = ds.getBigWigInfo().get(idx)[4];
						if (rowCnt != 0) {
							boolean sameListFlag = false;
							for (int j = 0; j < rowCnt; j++) {
								if (sample.get(i).equals(model_Personal.getValueAt(j, 0))) {
									sameListFlag = true;
								}
							}

							if (sameListFlag == false) {
								model_Personal.addRow(tmp);
							}
						} else {
							model_Personal.addRow(tmp);
						}
					}
					
				} else {
					Object selectedList[] = list.getSelectedValues();
					if (selectedList.length == 0) {
						JOptionPane.showMessageDialog(null,
								"Please select the sample before run.", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (int i = 0; i < selectedList.length; i++) {
							int idx = Integer.parseInt(ds.getHmBigWigInfo(String.valueOf(selectedList[i])));
							tmp[0] = ds.getBigWigInfo().get(idx)[0];
							tmp[1] = cf.convertGenome(ds.getBigWigInfo().get(idx)[3]);
							tmp[2] = ds.getBigWigInfo().get(idx)[4];
							if (rowCnt != 0) {
								boolean sameListFlag = false;
								for (int j = 0; j < rowCnt; j++) {
									if (selectedList[i].equals(model_Personal.getValueAt(j, 0))) {
										sameListFlag = true;
									}
								}

								if (sameListFlag == false) {
									model_Personal.addRow(tmp);
								}
							} else {
								model_Personal.addRow(tmp);
							}
						}
					}
				}
			}
		});
		
		// Delete button
		delete_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (delete_chbx.isSelected()) {
					for (int i = model_Personal.getRowCount() - 1; i >= 0; i--) {
						model_Personal.removeRow(i);
					}
				} else {
					if (graph_table.getSelectedRows().length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table before run.", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						int selectedRow[] = graph_table.getSelectedRows();
						if (selectedRow.length > 0) {
							for (int i = selectedRow.length - 1; i >= 0; i--) {
								model_Personal.removeRow(selectedRow[i]);
							}
						}
					}
				}

			}
		});
		
		// Run Button
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Add Check Code (Seq, Genome)  
				if(!checkTable()){
					int checkResult = JOptionPane.showConfirmDialog(null,"Do you want to make heatmap and line plot.","Graph", JOptionPane.YES_NO_OPTION);
					if(checkResult == 0){
						ds.setModel(model_Personal);
						ds.setBedIdx(bed_cbx.getSelectedIndex());
						ds.setTssRegion(tss_cbx.getSelectedItem().toString());
						ds.setBinNum(bin_cbx.getSelectedItem().toString());
						ds.getMainUI().getUI_Graph().setVisible(false);
						ds.getMainUI().makeGraph();
					}
				}
			}
		});
		
		makeList();
		
	}
	public void clearTable(){
		for(int i = model_Personal.getRowCount()-1; i >= 0 ; i--){
			model_Personal.removeRow(i);
		}
		sample.clear();
		bed_cbx.removeAllItems();
		list.updateUI();
	}
	
	public void makeList() {
		
		bed_cbx.addItem("Promoter.bed");
		for(int i = 0; i < ds.getBedPath().size(); i++){
			bed_cbx.addItem(ds.getBedPath().get(i)[0]);
		}
		
		for (int i = 0; i < ds.getBigWigInfo().size(); i++) {
			if (ds.getBigWigInfo().get(i)[5].equals("bigWig")) {
				ds.setHmBigWigInfo(ds.getBigWigInfo().get(i)[0], String.valueOf(i));
				if (!ds.getBigWigInfo().get(i)[3].equals("RN")) {
					sample.add(ds.getBigWigInfo().get(i)[0]);
				}
			}
		}
		list.updateUI();
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
	}
	
	public boolean checkTable(){
		boolean valid_Flag = false; // true : Error
		
		if(model_Personal.getRowCount() == 0){
			JOptionPane.showMessageDialog(null, "User must insert the Sample.", "Check Table",
					JOptionPane.INFORMATION_MESSAGE);
			valid_Flag = true;
		}else{
			String seq = String.valueOf(model_Personal.getValueAt(0, 1));
			String genome = String.valueOf(model_Personal.getValueAt(0, 2));
			
			for(int i = 1; i < model_Personal.getRowCount(); i++){
				String tmpSeq = String.valueOf(model_Personal.getValueAt(i, 1));
				String tmpGenome = String.valueOf(model_Personal.getValueAt(i, 2));
				
				if(!seq.equals(tmpSeq)){
					JOptionPane.showMessageDialog(null, "Sequencing information does not match.\n"+seq+" != "+tmpSeq, "Check Table",
							JOptionPane.INFORMATION_MESSAGE);
					valid_Flag = true;
					break;
				}else if(!genome.equals(tmpGenome)){
					JOptionPane.showMessageDialog(null, "Genome information does not match.\n"+genome+" != "+tmpGenome, "Check Table",
							JOptionPane.INFORMATION_MESSAGE);
					valid_Flag = true;
					break;
				}
			}	
		}
		
		return valid_Flag;
	}
}
