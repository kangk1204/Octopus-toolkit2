package Octopus_UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Vector;

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

public class UI_IGV_Table extends JFrame{
	private DataSet ds;
	private CommonFunc cf;
	private JTable igv_table;
	private DefaultTableModel model_Personal;
	private Vector<String> sample;
	private JList<String> list;
	private JComboBox genome_cbx;
	
	public UI_IGV_Table(DataSet ds, CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		
		getContentPane().setBackground(Color.WHITE);
		setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	
		if(ds.getOS().equals("Ubuntu")){
			this.setSize(550,500);					
		}else{
			this.setSize(550,535);
		}
		
		
		this.setTitle("Octopus-toolkit");
		
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);
		
		final String colNames[] = {"Sample","Seq type","Genome","File format"};
		model_Personal = new DefaultTableModel(null,colNames){
			public boolean isCellEditable(int row,int colum){
				return false;
			}
		};
		
		igv_table = new JTable(model_Personal);
		igv_table.setModel(model_Personal);
		igv_table.setFillsViewportHeight(true);
		igv_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		igv_table.setBounds(31, 58, 560, 200);
		igv_table.getColumn("Sample").setPreferredWidth(280);
		igv_table.getColumn("Seq type").setPreferredWidth(70);
		igv_table.getColumn("Genome").setPreferredWidth(70);
		igv_table.getColumn("File format").setPreferredWidth(70);
		igv_table.getColumn("Seq type").setCellRenderer(celAlignCenter);
		igv_table.getColumn("Genome").setCellRenderer(celAlignCenter);
		igv_table.getColumn("File format").setCellRenderer(celAlignCenter);
		
		getContentPane().add(igv_table);
		
		JScrollPane table_jsp = new JScrollPane(igv_table,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table_jsp.setBounds(17, 35, 515, 200);
		getContentPane().add(table_jsp);
		
		JPanel sample_panel = new JPanel();
		sample_panel.setBackground(Color.WHITE);
		sample_panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Sample (bigWig)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		sample_panel.setBounds(20, 250, 515, 140);
		getContentPane().add(sample_panel);
		sample_panel.setLayout(null);
		
		
		sample = new Vector<String>();
		
		list = new JList<String>(sample);
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
		list.setBounds(10, 20, 340, 108);
		sample_panel.add(list);
		
		JScrollPane list_jsp = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		list_jsp.setBounds(10, 20, 340, 108);
		sample_panel.add(list_jsp);	
		
		JButton reopen_btn = new JButton("Open");
		
		reopen_btn.setBounds(358, 20, 95, 25);
		sample_panel.add(reopen_btn);
		
		JButton insert_btn = new JButton("Insert");
		
		insert_btn.setBounds(358, 60, 95, 25);
		sample_panel.add(insert_btn);
		
		JButton delete_btn = new JButton("Delete");
		delete_btn.setBounds(358, 98, 95, 25);
		sample_panel.add(delete_btn);
		
		JCheckBox insert_chbx = new JCheckBox("all");
		insert_chbx.setBackground(Color.WHITE);
		insert_chbx.setBounds(458, 60, 50, 23);
		sample_panel.add(insert_chbx);
		
		JCheckBox delete_chbx = new JCheckBox("all");
		delete_chbx.setBackground(Color.WHITE);
		delete_chbx.setBounds(458, 98, 50, 23);
		sample_panel.add(delete_chbx);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Table Option", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(20, 402, 515, 65);
		getContentPane().add(panel);
		
		JLabel genome_lbl = new JLabel("Genome : ");
		genome_lbl.setFont(new Font("Dialog", Font.BOLD, 14));
		genome_lbl.setBounds(12, 30, 120, 15);
		panel.add(genome_lbl);
		
		genome_cbx = new JComboBox();
		genome_cbx.setBackground(Color.WHITE);
		genome_cbx.setBounds(95, 25, 190, 24);
		panel.add(genome_cbx);
		
		JButton run_btn = new JButton("Run");
		run_btn.setBounds(300, 25, 81, 25);
		panel.add(run_btn);
		
		JLabel help_img = new JLabel("HELP");
		help_img.setToolTipText("IGV table help.");
		help_img.setIcon(new ImageIcon(UI_IGV_Table.class.getResource("/Octopus_Icon/HelpImg.png")));
		help_img.setBounds(470, 10, 60, 18);
		getContentPane().add(help_img);
		
		help_img.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				cf.openURL("http://octopus-toolkit2.readthedocs.io/en/latest/tutorial.html#igv");
			}
			public void mouseReleased(MouseEvent e){}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
		});
			
		// Reopen Button
		reopen_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cf.openDialog_Dir("IGV");
				if(ds.getBigWigInfo().size() != 0){
					clearTable();
					makeList();
				}else{
					JOptionPane.showMessageDialog(null, "No such file or directory. (BigWig Format)", "File Open",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		// Insert Button
		insert_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCnt = model_Personal.getRowCount();
				String tmp[] = new String[4];
				for(int k = 0; k < 4; k++){
					tmp[k] = "";
				}
				
				if (insert_chbx.isSelected()) {
					// Insert All
					for(int i = 0; i < sample.size(); i++){
						int idx = Integer.parseInt(ds.getHmBigWigInfo((String) sample.get(i)));
						tmp[0] = ds.getBigWigInfo().get(idx)[0];
						tmp[1] = cf.convertGenome(ds.getBigWigInfo().get(idx)[3]);
						tmp[2] = ds.getBigWigInfo().get(idx)[4];
						tmp[3] = ds.getBigWigInfo().get(idx)[5];
						if (rowCnt != 0) {
							boolean sameListFlag = false;
							for (int j = 0; j < rowCnt; j++) {
								if (sample.get(i).equals(model_Personal.getValueAt(j, 0)+"."+model_Personal.getValueAt(j, 3))) {
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
						JOptionPane.showMessageDialog(null, "Please select the sample to be inserted in the sample window.", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						for (int i = 0; i < selectedList.length; i++) {
							int idx = Integer.parseInt(ds.getHmBigWigInfo(String.valueOf(selectedList[i])));
							tmp[0] = ds.getBigWigInfo().get(idx)[0];
							tmp[1] = cf.convertGenome(ds.getBigWigInfo().get(idx)[3]);
							tmp[2] = ds.getBigWigInfo().get(idx)[4];
							tmp[3] = ds.getBigWigInfo().get(idx)[5];
							if (rowCnt != 0) {
								boolean sameListFlag = false;
								for (int j = 0; j < rowCnt; j++) {
									if (selectedList[i].equals(model_Personal.getValueAt(j, 0)+"."+model_Personal.getValueAt(j, 3))) {
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
				
				for(int i = 0; i < model_Personal.getRowCount(); i++){
					if(genome_cbx.getItemCount() == 0){
						genome_cbx.addItem(model_Personal.getValueAt(i, 2));
					}else{
						boolean itemFlag = false;
						for(int j = 0; j < genome_cbx.getItemCount(); j++){
							if(genome_cbx.getItemAt(j).equals(model_Personal.getValueAt(i, 2))){
								itemFlag = true;
								break;
							}
						}
						if(itemFlag == false){
							genome_cbx.addItem(model_Personal.getValueAt(i, 2));
						}
					}
				}
				
			}
		});
		
		// Delete_Btn
		delete_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (delete_chbx.isSelected()) {
					for (int i = model_Personal.getRowCount() - 1; i >= 0; i--) {
						model_Personal.removeRow(i);
					}
					genome_cbx.removeAllItems();
				} else {
					if (igv_table.getSelectedRows().length == 0) {
						JOptionPane.showMessageDialog(null, "Please select the table.", "Check Table",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						int selectedRow[] = igv_table.getSelectedRows();
						if (selectedRow.length > 0) {
							for (int i = selectedRow.length - 1; i >= 0; i--) {
								model_Personal.removeRow(selectedRow[i]);
							}
							genome_cbx.removeAllItems();

							for (int i = 0; i < model_Personal.getRowCount(); i++) {
								if (genome_cbx.getItemCount() == 0) {
									genome_cbx.addItem(model_Personal.getValueAt(i, 2));
								} else {
									boolean itemFlag = false;
									for (int j = 0; j < genome_cbx.getItemCount(); j++) {
										if (genome_cbx.getItemAt(j).equals(model_Personal.getValueAt(i, 2))) {
											itemFlag = true;
											break;
										}
									}
									if (itemFlag == false) {
										genome_cbx.addItem(model_Personal.getValueAt(i, 2));
									}
								}
							}
						}
					}

				}
			}
		});
		
		// Run Button
		run_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(model_Personal.getRowCount() == 0){
					JOptionPane.showMessageDialog(null, "User must insert the Sample.", "Check Table",JOptionPane.INFORMATION_MESSAGE);
				}else{
					
					ArrayList<String[]> bamInfo = checkBam();

					if(bamInfo.size() != 0){
						String list = "";
						for(int i = 0; i < bamInfo.size(); i++){
							list = list + bamInfo.get(i)[0]+" : "+bamInfo.get(i)[1]+"\n";
						}
						int res = JOptionPane.showConfirmDialog(null, "Some bam files require the following process.\n"+list+"Would you like to convert some bam file to run IGV?", "Check Table",JOptionPane.INFORMATION_MESSAGE);
						ds.getMainUI().getUI_IGV().setVisible(false);
						if(res == 0){
							sorting(bamInfo);
							JOptionPane.showMessageDialog(null, "Octopus-toolkit is running IGV\nIt takes some time to set the IGV application.", "IGV Table",JOptionPane.INFORMATION_MESSAGE);
							makeIGV();
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Octopus-toolkit is running IGV\nIt takes some time to set the IGV application.", "IGV Table",JOptionPane.INFORMATION_MESSAGE);
						makeIGV();
					}
				}
			}
			
		});
	
		makeList();
	}
	
	public void sorting(ArrayList<String[]> data){
		
		int distance = 90 / data.size();
		ds.writeLogRun("Sorting : Samtools (Start)\n", true);
		
		for(int i = 0; i < data.size(); i++){
			if(data.get(i)[1].equals("Sorting")){
				ds.getMainUI().setRunningPrograss(true);
				ds.getMainUI().setProgress((distance*0)+distance,"Sorting : "+ data.get(i)[0]);
				int idx = Integer.parseInt(ds.getHmBigWigInfo(String.valueOf(data.get(i)[0])));
				String bam = ds.getBigWigInfo().get(idx)[2];
				File f = new File(bam);
				String sorted_bam = f.getParent()+"/sorted_"+f.getName();
				
				String samtoolSort_Cmd[] = {ds.getPath()+"Tools/Samtools/samtools","sort",bam,"-@",ds.getAnalysisCPU(),"-o",sorted_bam};
				String samtoolIndex_Cmd[] = {ds.getPath()+ "Tools/Samtools/samtools","index",sorted_bam};
				cf.shellCmd(samtoolSort_Cmd);
				ds.getMainUI().setProgress((distance*0)+distance,"Indexing : "+ data.get(i)[0]);
				cf.shellCmd(samtoolIndex_Cmd);
				
				String[] exchangeInfo = ds.getBigWigInfo().get(idx);
				exchangeInfo[0] = "sorted_"+exchangeInfo[0];
				exchangeInfo[1] = "sorted_"+exchangeInfo[1];
				exchangeInfo[2] = sorted_bam;
				ds.getBigWigInfo().set(idx, exchangeInfo);
				
			}else{
				// Indexing
				ds.getMainUI().setProgress((distance*0)+distance,"Indexing : "+ data.get(i)[0]);
				int idx = Integer.parseInt(ds.getHmBigWigInfo(String.valueOf(model_Personal.getValueAt(i, 0)+"."+model_Personal.getValueAt(i, 3))));
				String sorted_bam = ds.getBigWigInfo().get(idx)[2];
				
				String samtoolIndex_Cmd[] = {ds.getPath() + "Tools/Samtools/samtools","index",sorted_bam};
				cf.shellCmd(samtoolIndex_Cmd);
			}
		}
		ds.getMainUI().setRunningPrograss(false);
		ds.getMainUI().setProgress(100,"Complete : "+data.get(data.size()-1)[0]);
	
		ds.writeLogRun("Sorting : Samtools (End)\n", true);
	}
	
	public void makeIGV(){
		ds.getMainUI().getUI_IGV().setVisible(false);
		ds.writeLogRun("Visualization : Integrative Genomics Viewer(IGV) (Start)\n", true);
		ds.writeLogCmd("# Visualization using the Integrative Genomics Viewer(IGV)\n");
		try {
			FileWriter fw = new FileWriter(ds.getPath() + "Script/igvBatch");
			for (int i = 0; i < model_Personal.getRowCount(); i++) {
				int idx = Integer.parseInt(ds.getHmBigWigInfo(String.valueOf(model_Personal.getValueAt(i, 0)+"."+model_Personal.getValueAt(i, 3))));
				fw.write("load \""+ds.getBigWigInfo().get(idx)[2]+"\"\n");
				fw.flush();
			}
			
			fw.close();
			
			String item = genome_cbx.getSelectedItem().toString();
			String cmd_igv[] = {ds.getPath()+"Tools/IGV/igv.sh","-g",item,"-b",ds.getPath() + "Script/igvBatch"};
			
			//Log
			ds.writeLogCmd(ds.getPath()+"Tools/IGV/igv.sh -g "+item+" -b "+ds.getPath()+"Script/igvBatch\n\n");

			cf.shellCmd(cmd_igv);
			
			//new File(ds.getPath() + "Script/igvBatch.sh").delete();
			ds.writeLogRun("Visualization : Integrative Genomics Viewer(IGV) (End)\n", true);			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("\nError : Unable to make the igvBatch file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the igvBatch file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}

	}
	
	public ArrayList<String[]> checkBam(){
		
		ArrayList<String[]> data = new ArrayList<>();
		
		for (int i = 0; i < model_Personal.getRowCount(); i++) {
			if(model_Personal.getValueAt(i, 3).equals("bam")){
				String fileName = model_Personal.getValueAt(i, 0)+"."+model_Personal.getValueAt(i, 3);
				if(fileName.length() > 6 && fileName.substring(0, 7).equals("sorted_")){
					int num = Integer.parseInt(ds.getHmBigWigInfo(String.valueOf(fileName)));
					File f = new File(ds.getBigWigInfo().get(num)[2]+".bai");
					if(!f.exists()){
						String tmp[] = {fileName,"Indexing"};
						data.add(tmp);
					}
				}else{
					String tmp[] = {fileName,"Sorting"};
					data.add(tmp);
				}
			}
		}

		return data;
	}
	
	public void clearTable(){
		for(int i = model_Personal.getRowCount()-1; i >= 0 ; i--){
			model_Personal.removeRow(i);
		}
		sample.clear();
		genome_cbx.removeAllItems();
		list.updateUI();
	}
	
	public void makeList() {
		
		for (int i = 0; i < ds.getBigWigInfo().size(); i++) {
			ds.setHmBigWigInfo(ds.getBigWigInfo().get(i)[0]+"."+ds.getBigWigInfo().get(i)[5], String.valueOf(i));
			sample.add(ds.getBigWigInfo().get(i)[0]+"."+ds.getBigWigInfo().get(i)[5]);
			
		}
		list.updateUI();
		list.setBorder(UIManager.getBorder("List.focusCellHighlightBorder"));
	}
	
	
}
