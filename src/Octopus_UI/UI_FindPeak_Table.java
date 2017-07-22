package Octopus_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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

public class UI_FindPeak_Table extends JFrame{
	private DataSet ds;
	private CommonFunc cf;
	private JTable findPeak_table;
	private DefaultTableModel model_Personal;
	private Vector<String> sample;
	private JComboBox control_cbx;
	private JList<String> list;
	
	public UI_FindPeak_Table(DataSet ds, CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		if(ds.getOS().equals("Ubuntu")){
			this.setSize(603,560);					
		}else{
			this.setSize(603,595);
		}
		
		this.setTitle("Octopus-toolkit");		
		
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		final String colNames[] = {"Sample","Control","Style","Correspond"};
		model_Personal = new DefaultTableModel(null,colNames){
			public boolean isCellEditable(int row,int colum){
				return false;
			}
		};
		
		findPeak_table = new JTable(model_Personal);
		findPeak_table.setModel(model_Personal);
		findPeak_table.setFillsViewportHeight(true);
		findPeak_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		findPeak_table.setBounds(31, 58, 560, 200);
		findPeak_table.getColumn("Sample").setPreferredWidth(250);
		findPeak_table.getColumn("Control").setPreferredWidth(250);
		findPeak_table.getColumn("Style").setPreferredWidth(250);
		findPeak_table.getColumn("Correspond").setPreferredWidth(150);
		findPeak_table.getColumn("Control").setCellRenderer(celAlignCenter);
		findPeak_table.getColumn("Style").setCellRenderer(celAlignCenter);
		findPeak_table.getColumn("Correspond").setCellRenderer(celAlignCenter);
		
		getContentPane().add(findPeak_table);
		
		JScrollPane jsp2 = new JScrollPane(findPeak_table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp2.setBounds(17, 35, 560, 200);
		getContentPane().add(jsp2);

		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Sample", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBounds(20, 250, 560, 140);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		
		sample = new Vector<String>();
		
		list = new JList<String>(sample);
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
		list.setBounds(10, 20, 364, 108);
		panel.add(list);
		
		JScrollPane jsp3 = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp3.setBounds(10, 20, 364, 108);
		panel.add(jsp3);	
		
		JButton insert_btn = new JButton("Insert");
		insert_btn.setBounds(402, 60, 95, 25);
		panel.add(insert_btn);
		
		JCheckBox insert_chbx = new JCheckBox("all");
		insert_chbx.setBounds(502, 60, 50, 23);
		panel.add(insert_chbx);
		insert_chbx.setBackground(Color.WHITE);
		
		JButton delete_btn = new JButton("Delete");
		delete_btn.setBounds(402, 98, 95, 25);
		panel.add(delete_btn);
		
		JCheckBox delete_chbx = new JCheckBox("all");
		delete_chbx.setBounds(502, 98, 50, 23);
		panel.add(delete_chbx);
		delete_chbx.setBackground(Color.WHITE);
		
		JButton reopen_btn = new JButton("Open");
		reopen_btn.setBounds(402, 20, 95, 25);
		panel.add(reopen_btn);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(null, "Option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(450, 400, 130, 125);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton run_btn = new JButton("Run");
		
		run_btn.setBounds(20, 80, 95, 25);
		panel_1.add(run_btn);
		
		JButton reset_btn = new JButton("Reset");
		
		reset_btn.setBounds(20, 35, 95, 25);
		panel_1.add(reset_btn);
		
		JPanel option_panel = new JPanel();
		option_panel.setBackground(Color.WHITE);
		option_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Table Option", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		option_panel.setBounds(20, 400, 420, 125);
		getContentPane().add(option_panel);
		option_panel.setLayout(null);
		
		JLabel control_lbl = new JLabel("Control : ");
		control_lbl.setFont(new Font("Dialog", Font.PLAIN, 14));
		control_lbl.setBounds(12, 30, 120, 15);
		option_panel.add(control_lbl);
		
		control_cbx = new JComboBox();
		control_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		control_cbx.setBackground(Color.WHITE);
		control_cbx.setBounds(85, 25, 190, 24);
		option_panel.add(control_cbx);

		JButton control_btn = new JButton("Insert");
		control_btn.setBounds(280, 25, 81, 25);
		option_panel.add(control_btn);
		
		JCheckBox control_chbx = new JCheckBox("all");
		control_chbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		control_chbx.setBackground(Color.WHITE);
		control_chbx.setBounds(365, 25, 50, 23);
		option_panel.add(control_chbx);
		
		JLabel style_lbl = new JLabel("Style : ");
		style_lbl.setFont(new Font("Dialog", Font.PLAIN, 14));
		style_lbl.setBounds(12, 60, 120, 15);
		option_panel.add(style_lbl);
		
		JComboBox style_cbx = new JComboBox();
		style_cbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		style_cbx.setModel(new DefaultComboBoxModel(new String[] {"Transcription Factor", "Histone", "Dnase", "Cytosine methylation"}));
		style_cbx.setBackground(Color.WHITE);
		style_cbx.setBounds(85, 55, 190, 24);
		option_panel.add(style_cbx);
		
		JButton style_btn = new JButton("Insert");
		
		style_btn.setBounds(280, 55, 81, 25);
		option_panel.add(style_btn);
		
		JCheckBox style_chbx = new JCheckBox("all");
		style_chbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		style_chbx.setBackground(Color.WHITE);
		style_chbx.setBounds(365, 55, 50, 23);
		option_panel.add(style_chbx);
		
		JCheckBox fullOption_chbx = new JCheckBox("Use the full parameter for each tool.");
		fullOption_chbx.setFont(new Font("Dialog", Font.PLAIN, 12));
		fullOption_chbx.setBackground(Color.WHITE);
		fullOption_chbx.setBounds(12, 90, 260, 23);
		option_panel.add(fullOption_chbx);
		
		JButton edit_btn = new JButton("Edit");
		edit_btn.setBounds(280, 88, 81, 25);
		edit_btn.setEnabled(false);
		option_panel.add(edit_btn);
		
		fullOption_chbx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fullOption_chbx.isSelected()){
					edit_btn.setEnabled(true);
				}else{
					edit_btn.setEnabled(false);
				}
			}
		});
		
		edit_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ds.getMainUI().getUI_Tool_Option().setVisible(true);
				ds.getMainUI().getUI_Tool_Option().start("ChIP-Seq/Histone");
			}
		});
		
		JLabel help_img = new JLabel("HELP");
		help_img.setToolTipText("Peak calling table help.");
		help_img.setIcon(new ImageIcon(UI_FindPeak_Table.class.getResource("/Octopus_Icon/HelpImg.png")));
		help_img.setBounds(515, 10, 60, 18);
		getContentPane().add(help_img);
		
		help_img.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/tutorial.html#peak-calling");
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		});
		
		// RE-OPEN
		reopen_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cf.openDialog_Dir("PeakCalling");
				if(ds.getTagInfo().size() != 0){
					clearTable();
					makeList();
				}else{
					JOptionPane.showMessageDialog(null, "No such file or directory. (TagDirectory : 03_Tag)", "File Open",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		// Sample Delete
		delete_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (delete_chbx.isSelected()) {
					for (int i = model_Personal.getRowCount() - 1; i >= 0; i--) {
						model_Personal.removeRow(i);
					}
				} else {
					if (findPeak_table.getSelectedRows().length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table before run.", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						int selectedRow[] = findPeak_table.getSelectedRows();
						if (selectedRow.length > 0) {
							for (int i = selectedRow.length - 1; i >= 0; i--) {
								model_Personal.removeRow(selectedRow[i]);
							}
						}
					}
				}
			}
		});
		
		// Sample Insert
		insert_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		
				int rowCnt = model_Personal.getRowCount();
				String tmp[] = new String[4];
				for (int k = 0; k < 4; k++) {
					tmp[k] = "";
				}

				if (insert_chbx.isSelected()) {
					// Insert All
					for (int i = 0; i < sample.size(); i++) {
						if (rowCnt != 0) {
							boolean sameListFlag = false;
							for (int j = 0; j < rowCnt; j++) {
								if (sample.get(i).equals(model_Personal.getValueAt(j, 0))) {
									sameListFlag = true;
								}
							}

							if (sameListFlag == false) {
								tmp[0] = (String) sample.get(i);
								model_Personal.addRow(tmp);
							}
						} else {
							tmp[0] = (String) sample.get(i);
							model_Personal.addRow(tmp);
						}
					}

				} else {
					if (list.getSelectedValues().length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the sample before run.", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						Object selectedList[] = list.getSelectedValues();
						for (int i = 0; i < selectedList.length; i++) {
							if (rowCnt != 0) {
								boolean sameListFlag = false;
								for (int j = 0; j < rowCnt; j++) {
									if (selectedList[i].equals(model_Personal.getValueAt(j, 0))) {
										sameListFlag = true;
									}
								}

								if (sameListFlag == false) {
									tmp[0] = (String) selectedList[i];
									model_Personal.addRow(tmp);
								}
							} else {
								tmp[0] = (String) selectedList[i];
								model_Personal.addRow(tmp);
							}
						}
					}
				}
			}
		});
		
		// Control Insert
		control_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (model_Personal.getRowCount() != 0) {
					if (control_chbx.isSelected()) {
						for (int i = 0; i < model_Personal.getRowCount(); i++) {
							model_Personal.setValueAt(control_cbx.getSelectedItem(), i, 1);
						}
					} else {
						if (findPeak_table.getSelectedRows().length == 0) {
							JOptionPane.showMessageDialog(null, "Please select the table before run.", "Check Table",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							int selectedRow[] = findPeak_table.getSelectedRows();
							for (int i = 0; i < selectedRow.length; i++) {
								model_Personal.setValueAt(control_cbx.getSelectedItem(), selectedRow[i], 1);
							}
						}
					}
				}
			}
		});
		
		// Style Insert
		style_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (model_Personal.getRowCount() != 0) {
					if (style_chbx.isSelected()) {
						for (int i = 0; i < model_Personal.getRowCount(); i++) {
							model_Personal.setValueAt(style_cbx.getSelectedItem(), i, 2);
						}
					} else {
						if (findPeak_table.getSelectedRows().length == 0) {
							JOptionPane.showMessageDialog(null, "Please select the table before run.", "Insert Table",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							int selectedRow[] = findPeak_table.getSelectedRows();
							for (int i = 0; i < selectedRow.length; i++) {
								model_Personal.setValueAt(style_cbx.getSelectedItem(), selectedRow[i], 2);
							}
						}
					}
				}
			}
		});
		
		// Reset Button
		reset_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < model_Personal.getRowCount(); i++){
					model_Personal.setValueAt("", i, 1);
					model_Personal.setValueAt("", i, 2);
					model_Personal.setValueAt("", i, 3);
				}
			}
		});
		
		// Run Button
		run_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!checkTable()){
					int checkResult = JOptionPane.showConfirmDialog(null,"Do you want to run for peak calling?","Peak Calling", JOptionPane.YES_NO_OPTION);
					if(fullOption_chbx.isSelected()){
						ds.getMainUI().getUI_Tool_Option().saveFullOption();
						ds.setFullOption(true);
					}else{
						ds.setFullOption(false);
					}
					if(checkResult == 0){
						ds.setModel(model_Personal);
						ds.getMainUI().getUI_FindPeak().setVisible(false);
						ds.getMainUI().peakcalling();
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
		control_cbx.removeAllItems();
		list.updateUI();
	}
	
	public void makeList() {
		
		for (int i = 0; i < ds.getTagInfo().size(); i++) {
			ds.setHmTagInfo(ds.getTagInfo().get(i)[0], String.valueOf(i));
			
			if (!ds.getTagInfo().get(i)[3].equals("RN")) {
				sample.add(ds.getTagInfo().get(i)[0]);
				control_cbx.addItem(ds.getTagInfo().get(i)[0]);
			}
		}
		list.updateUI();
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
	}
	
	public boolean checkTable(){
		boolean valid_Flag = false; // true : Error
		
		if (model_Personal.getRowCount() == 0) {
			// Not Exist Sample in Table
			JOptionPane.showMessageDialog(null, "User must insert the Sample.", "Check Table",
					JOptionPane.INFORMATION_MESSAGE);
			valid_Flag = true;
		} else {
			// Exist Sample
			for (int i = 0; i < model_Personal.getRowCount(); i++) {
				if (findPeak_table.getValueAt(i, 2) == "") {
					valid_Flag = true;
					model_Personal.setValueAt("X", i, 3);
				}else{
					model_Personal.setValueAt("O", i, 3);
				}
			}
			if(valid_Flag == true){
				JOptionPane.showMessageDialog(null, "User must insert the style.", "Check Table",JOptionPane.INFORMATION_MESSAGE);	
			}
			
		}
				
		if(valid_Flag == false){
			for(int i = 0; i < model_Personal.getRowCount(); i++){
				String seq = "";
				String genome = "";
				for(int j = 0; j < ds.getTagInfo().size(); j++){
					if(findPeak_table.getValueAt(i, 0).equals(ds.getTagInfo().get(j)[0])){
						seq = ds.getTagInfo().get(j)[3];
						genome = ds.getTagInfo().get(j)[4];
						break;
					}
				}
				if(findPeak_table.getValueAt(i, 1) != ""){
					String control_seq = "";
					String control_genome = "";
					String control_str = "";
					for(int j = 0; j < ds.getTagInfo().size(); j++){
						if(findPeak_table.getValueAt(i, 1).equals(ds.getTagInfo().get(j)[0])){
							control_str = ds.getTagInfo().get(j)[0];
							control_seq = ds.getTagInfo().get(j)[3];
							control_genome = ds.getTagInfo().get(j)[4];
							break;
						}
					}
					
					if(!seq.equals(control_seq)){
						JOptionPane.showMessageDialog(null, "Please check the sequencing information.\nSample : "+model_Personal.getValueAt(i, 0)+"("+seq+")\nControl : "+control_str+"("+control_seq+")", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
						valid_Flag = true;
						model_Personal.setValueAt("X", i, 3);
						break;
					}
					
					if(!genome.equals(control_genome)){
						JOptionPane.showMessageDialog(null, "Please check the genome information.\nSample : "+model_Personal.getValueAt(i, 0)+"("+genome+")\nControl : "+control_str+"("+control_genome+")", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
						valid_Flag = true;
						model_Personal.setValueAt("X", i, 3);
						break;
					}
				}
				if(valid_Flag == false){
					model_Personal.setValueAt("O", i, 3);	
				}
			}
			
			if(valid_Flag == false){
				JOptionPane.showMessageDialog(null, "Table check completed.", "Check Table", JOptionPane.INFORMATION_MESSAGE);
			}	
		}
		return valid_Flag;

	}
}
