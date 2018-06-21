package Octopus_Src;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class P_PreProcessing {

	private DataSet ds;
	private CommonFunc cf;
	private DefaultTableModel model;
	private boolean tableFlag = true;
	
	public boolean getTableFlag(){
		return tableFlag;
	}
	
	public P_PreProcessing(DataSet ds, CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		model = ds.getModel();
		
		String[] fold = {
				"/Result/" +  ds.getResultDirName() + "/00_Fastq"};
		
		cf.makeDirectory(fold);

	}
	
	public void checkInputTable(){
		
		ds.writeLogRun("Preprocessing : Check private information. (Start)\n", true);
		
		ArrayList<String> rep = new ArrayList<>();
		boolean privateTable = true; 
		
		// check Cell (Genome/Sequencing)
		for(int i = 0; i < model.getRowCount(); i++){
			if(model.getValueAt(i, 3).toString().equals("")){ 
				privateTable = false;
				JOptionPane.showMessageDialog(null, "Please select a reference genome for your data.", "Private table",	JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Please select a reference genome for your data.");
				tableFlag = false;
				break;
			}else if(model.getValueAt(i, 4).toString().equals("")){
				privateTable = false;
				JOptionPane.showMessageDialog(null, "Please specify the sequencing type of your data.", "Private table",	JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Please specify the sequencing type of your data.");
				tableFlag = false;
				break;
				
			}else if(model.getValueAt(i, 5).toString().equals("")){
				privateTable = false;
				JOptionPane.showMessageDialog(null, "Please select an appropriate strand information for your data.", "Private table",	JOptionPane.INFORMATION_MESSAGE);
				System.out.println("Please select an appropriate strand information for your data.");
				tableFlag = false;
				break;			
			}			
		}
	
		if(privateTable == true){
			// Arrange Rep Number
			for (int i = 0; i < model.getRowCount(); i++) {
				String str = String.valueOf(model.getValueAt(i, 0));
				Boolean checkRep = false;
				for (int j = 0; j < rep.size(); j++) {
					if (str.equals(rep.get(j))) {
						checkRep = true;
						break;
					}
				}
				if (checkRep == false) {
					rep.add(str);
				}
			}

			// Exist Replicate -> Merge
			if (rep.size() != model.getRowCount()) {
				mergeFile(rep);
			}else{
				// GSMInfo arrange
				saveTable();
			}
		}
		
		ds.writeLogRun("Preprocessing : Check private information. (End)\n", true);
	}
	
	public void mergeFile(ArrayList<String> rep) {

		for (int i = 0; i < rep.size(); i++) {
			String genome = "";
			String seq = "";
			String title = "";
			String library_end = ""; // SE/PE
			String replicate = rep.get(i);
			String forward = "";
			String reverse = "";
			String strand = "";
			String pairSymbol = "";

			for (int j = 0; j < model.getRowCount(); j++) {
				// replicate Number Check.
				if (replicate.equals(model.getValueAt(j, 0).toString())) {
					// First Replicate Sample (tmpSave : genome, seq)
					if (genome.equals("")) {
						genome = model.getValueAt(j, 3).toString();
						seq = model.getValueAt(j, 4).toString();
						strand = model.getValueAt(j, 5).toString();

						if (model.getValueAt(j, 2).toString().equals("")) { // Single-End
							if (model.getValueAt(j, 1).toString().endsWith(".fq")) {
								title = model.getValueAt(j, 1).toString().replace(".fq", "");
							} else if (model.getValueAt(j, 1).toString().endsWith(".fastq")) {
								title = model.getValueAt(j, 1).toString().replace(".fastq", "");
							} else if (model.getValueAt(j, 1).toString().endsWith(".fastq.gz")) {
								title = model.getValueAt(j, 1).toString().replace(".fastq.gz", "");
							} else {
								// .fq.gz
								title = model.getValueAt(j, 1).toString().replace(".fq.gz", "");
							}
							library_end = "Single-End";
							forward = forward + ds.getHmP_Fastq(model.getValueAt(j, 1).toString()) + "  ";
						} else { // Paired-End
							if (model.getValueAt(j, 1).toString().endsWith("_1_val_1.fq")) {
								title = model.getValueAt(j, 1).toString().replace("_1_val_1.fq", "");
								pairSymbol = "V";
							} else if (model.getValueAt(j, 1).toString().endsWith("_1_val_1.fastq")) {
								title = model.getValueAt(j, 1).toString().replace("_1_val_1.fastq", "");
								pairSymbol = "V";
							} else if (model.getValueAt(j, 1).toString().endsWith("_1_val_1.fastq.gz")) {
								title = model.getValueAt(j, 1).toString().replace("_1_val_1.fastq.gz", "");
								pairSymbol = "V";
							} else if(model.getValueAt(j, 1).toString().endsWith("_1_val_1.fq.gz")){
								title = model.getValueAt(j, 1).toString().replace("_1_val_1.fq.gz", "");
								pairSymbol = "V";
							}
							else if (model.getValueAt(j, 1).toString().endsWith("_1.fq")) {
								title = model.getValueAt(j, 1).toString().replace("_1.fq", "");
								pairSymbol = "N";
							} else if (model.getValueAt(j, 1).toString().endsWith("_1.fastq")) {
								title = model.getValueAt(j, 1).toString().replace("_1.fastq", "");
								pairSymbol = "N";
							} else if (model.getValueAt(j, 1).toString().endsWith("_1.fastq.gz")) {
								title = model.getValueAt(j, 1).toString().replace("_1.fastq.gz", "");
								pairSymbol = "N";
							} else if(model.getValueAt(j, 1).toString().endsWith("_1.fq.gz")){
								title = model.getValueAt(j, 1).toString().replace("_1.fq.gz", "");
								pairSymbol = "N";
							}
							
							else if (model.getValueAt(j, 1).toString().endsWith("_R1.fq")) {
								title = model.getValueAt(j, 1).toString().replace("_R1.fq", "");
								pairSymbol = "R";
							} else if (model.getValueAt(j, 1).toString().endsWith("_R1.fastq")) {
								title = model.getValueAt(j, 1).toString().replace("_R1.fastq", "");
								pairSymbol = "R";
							} else if (model.getValueAt(j, 1).toString().endsWith("_R1.fastq.gz")) {
								title = model.getValueAt(j, 1).toString().replace("_R1.fastq.gz", "");
								pairSymbol = "R";
							} else{
								// _R1.fq.gz
								title = model.getValueAt(j, 1).toString().replace("_R1.fq.gz", "");
								pairSymbol = "R";
							}

							library_end = "Paired-End";
							forward = forward + ds.getHmP_Fastq(model.getValueAt(j, 1).toString()) + "  ";
							reverse = reverse + ds.getHmP_Fastq(model.getValueAt(j, 2).toString()) + "  ";
						}
					} else {
						// check SE/PE
						String tmpLibrary = model.getValueAt(j, 2).toString().equals("") ? "Single-End" : "Paired-End";
						if (genome.equals(model.getValueAt(j, 3).toString())
								&& seq.equals(model.getValueAt(j, 4).toString()) && library_end.equals(tmpLibrary) && strand.equals(model.getValueAt(j, 5))) {
							// genome / sequencing / library
							if (model.getValueAt(j, 2).toString().equals("")) { // Single-End
								forward = forward + ds.getHmP_Fastq(model.getValueAt(j, 1).toString()) + "  ";
							} else { // Paired-End
								forward = forward + ds.getHmP_Fastq(model.getValueAt(j, 1).toString()) + "  ";
								reverse = reverse + ds.getHmP_Fastq(model.getValueAt(j, 2).toString()) + "  ";
							}
						} else {
							System.out.println("Fail : Private Option Table");
							JOptionPane.showMessageDialog(null, "Multi-lane number is not matched.", "Private table",	JOptionPane.INFORMATION_MESSAGE);
							tableFlag = false;
							break;
						}
					}
				}
			}
			// in For . End Rep Data
			if (tableFlag == true) {

				String tmpInfo[] = new String[7];
				tmpInfo[0] = genome;
				tmpInfo[1] = seq;
				tmpInfo[2] = title;
				tmpInfo[5] = strand;
				tmpInfo[6] = pairSymbol;

				String tmpFr[] = forward.split("  ");
				String tmpFr2[] = tmpFr[0].split("\\.");
				String extentionFr = tmpFr2[tmpFr2.length - 1];
				String tmpRv[] = reverse.split("  ");
				String tmpRv2[] = tmpRv[0].split("\\.");
				String extentionRv = tmpRv2[tmpRv2.length - 1];

				if (tmpFr.length > 1) {
					for (int j = 1; j < tmpFr.length; j++) {
						tmpFr2 = tmpFr[j].split("\\.");
						if (!extentionFr.equals(tmpFr2[tmpFr2.length - 1])) {
							tableFlag = false;

							System.out.println("File extension must be the same.");
							break;
						}
					}

					if (tmpRv.length > 1) {
						for (int j = 1; j < tmpRv.length; j++) {
							tmpRv2 = tmpRv[j].split("\\.");
							if (!extentionRv.equals(tmpRv2[tmpRv2.length - 1])) {
								tableFlag = false;

								System.out.println("File extension must be the same.");
								break;
							}
						}
					}

					if (tableFlag == false) {
						break;
					} else {
						tmpInfo[3] = forward;
						tmpInfo[4] = reverse;
					}
				}else{
					tmpInfo[3] = forward;
					tmpInfo[4] = reverse;
				}
				
				// add info
				ds.getPrivateData().add(tmpInfo);
			}
		}
	}
	
	public void saveTable(){
		for(int j = 0; j < model.getRowCount(); j++){
			String tmpInfo[] = new String[7];
			tmpInfo[0] = model.getValueAt(j, 3).toString();
			tmpInfo[1] = model.getValueAt(j, 4).toString();
			tmpInfo[5] = model.getValueAt(j, 5).toString();
			if(model.getValueAt(j, 2).toString().equals("")){ // Single-End
				if(model.getValueAt(j, 1).toString().endsWith(".fq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace(".fq", "");	
				}else if(model.getValueAt(j, 1).toString().endsWith(".fastq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace(".fastq", "");
				}else if(model.getValueAt(j, 1).toString().endsWith(".fastq.gz")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace(".fastq.gz", "");
				}else{
					//.fq.gz
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace(".fq.gz", "");
				}
				tmpInfo[3] = ds.getHmP_Fastq(model.getValueAt(j, 1).toString());
				tmpInfo[4] = "";
			}else{
				if(model.getValueAt(j, 1).toString().endsWith("_1_val_1.fq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1_val_1.fq", "");
					tmpInfo[6] = "V";
				}else if(model.getValueAt(j, 1).toString().endsWith("_1_val_1.fastq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1_val_1.fastq", "");
					tmpInfo[6] = "V";
				}else if(model.getValueAt(j, 1).toString().endsWith("_1_val_1.fastq.gz")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1_val_1.fastq.gz", "");
					tmpInfo[6] = "V";
				}else if(model.getValueAt(j, 1).toString().endsWith("_1_val_1.fq.gz")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1_val_1.fq.gz", "");
					tmpInfo[6] = "V";
				}
				else if(model.getValueAt(j, 1).toString().endsWith("_1.fq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1.fq", "");
					tmpInfo[6] = "N";
				}else if(model.getValueAt(j, 1).toString().endsWith("_1.fastq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1.fastq", "");
					tmpInfo[6] = "N";
				}else if(model.getValueAt(j, 1).toString().endsWith("_1.fastq.gz")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1.fastq.gz", "");
					tmpInfo[6] = "N";
				}else if(model.getValueAt(j, 1).toString().endsWith("_1.fq.gz")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_1.fq.gz", "");
					tmpInfo[6] = "N";
				}
				else if(model.getValueAt(j, 1).toString().endsWith("_R1.fq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_R1.fq", "");
					tmpInfo[6] = "R";
				}else if(model.getValueAt(j, 1).toString().endsWith("_R1.fastq")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_R1.fastq", "");
					tmpInfo[6] = "R";
				}else if(model.getValueAt(j, 1).toString().endsWith("_R1.fastq.gz")){
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_R1.fastq.gz", "");
					tmpInfo[6] = "R";
				}else{
					// _R1.fq.gz
					tmpInfo[2] = model.getValueAt(j, 1).toString().replace("_R1.fq.gz", "");
					tmpInfo[6] = "R";
				}
				
							
				tmpInfo[3] = ds.getHmP_Fastq(model.getValueAt(j, 1).toString());
				tmpInfo[4] = ds.getHmP_Fastq(model.getValueAt(j, 2).toString());
			}
			
			ds.getPrivateData().add(tmpInfo);
		}
	}
}