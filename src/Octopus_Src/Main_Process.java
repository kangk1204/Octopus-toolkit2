package Octopus_Src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Main_Process {
	private DataSet ds;
	private CommonFunc cf;
	public int cnt = 1;
	public int cntTotal = 1;

	
	public Main_Process(DataSet ds,CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
	}
	
	public void run_checkData(String inputGSE ,String data){ 
	
		ds.getSubRunLog().clear();
		
		if(data.equals("Public")){
			
			Parsing parse = new Parsing(ds, cf);
			System.out.println("\nThe analysis has been started ("+inputGSE.replace(" ", "").toUpperCase()+")");
			parse.startParse(inputGSE.replace(" ", ""));
			cnt = 0;
			cntTotal = ds.getGSM().size();
			ds.getMainUI().setGseLabel("- "+ds.getGSE()+" -");
			
			String[] dir = {"/Result/" +  ds.getGSE()};
			if(ds.getGSM().size() > 0){
				cf.makeDirectory(dir);
				ds.setLogAnalyzeInfo();	
			}
			ds.setAnalysisPath(ds.getPath()+"/Result/"+ds.getGSE()+"/");
			
			for (int i = 0; i < ds.getGSM().size(); i++) {
					ds.getErrLog().initErr();
					cnt++;
					ds.initGSMInfo();
					// (Latest Genome CheckBox)
					parse.resetAccessCnt();
					parse.getGSMInfo(ds.getGSM().get(i), ds.getLatest());
					ds.setRunGSM("");
					
					
					if (parse.getCheckGSMFlag() == true && ds.getGSMInfo()[0] != null) {
						if(checkAnalzedData() == false){
					
						ds.getMainUI().setRunningPrograss(true);
						PreProcessing pre = new PreProcessing(ds, cf);

						// Log (Option)
						ds.writeLogRun("Analysis : " + ds.getGSE() + " :\n" + ds.getGSMInfo()[0] + "("
								+ ds.getGSM().get(i) + ")(" + cnt + "/" + cntTotal + ")\n", true);
						writeOptionLog(ds.getGSM().get(i));
						
						ds.setRunGSM(ds.getGSM().get(i));

						// check remain Hdd capacity (Up to 2Gb)
						if (cf.checkHDDCapacity() == true) {
							
							pre.use_Prefetch();
							if (cf.checkStepPass()) {
								System.out.println("Step (Prefetch) : Err006-1");
								ds.getMainUI().setSimpleLbl("Err006-1");
								ds.writeLogRun("Error : Unable to access the NCBI ftp server.<a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-1\">[Err006-1]</a> \n\n", true);
								ds.getMainUI().setProgress(100, ds.getGSMInfo()[0] + " : Error Prefetch...");
								ds.writeLogInfo("[Unable to access the NCBI ftp server. (Err006-1)]");
								ds.setAnalyzeFlag();
								String tmp[] = {ds.getGSM().get(i),"Err006-1"};
								ds.setSubRunLog(tmp);
								break;
							}
						} else {
							ds.getMainUI().setSimpleLbl("Err005");
							ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
							ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
							ds.writeLogInfo("[Not enough disk space. (Err005)]");
							System.out.println(ds.getErrLog().getHddCapacity());
							ds.setAnalyzeFlag();
							String tmp[] = {ds.getGSM().get(i),"Err005"};
							ds.setSubRunLog(tmp);
							break;
						}

						// check remain Hdd capacity (Up to 2Gb)
						if (cf.checkHDDCapacity() == true) {
							pre.use_FastqDump();
						} else {
							ds.getMainUI().setSimpleLbl("Err005");
							ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
							ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
							ds.writeLogInfo("[Not enough disk space. (Err005)]");
							System.out.println(ds.getErrLog().getHddCapacity());
							ds.setAnalyzeFlag();
							String tmp[] = {ds.getGSM().get(i),"Err005"};
							ds.setSubRunLog(tmp);
							break;
						}

						// check remain Hdd capacity (Up to 2Gb)
						if (cf.checkHDDCapacity() == true) {
							pre.mergeFastq();
							if (cf.checkStepPass()) {
								System.out.println("Step (Fastq-dump) : Err006-2");
								ds.getMainUI().setSimpleLbl("Err006-2");
								ds.writeLogRun("Error : Unable to find any FASTQ files. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-2\">[Err006-2]</a> \n\n", true);
								ds.getMainUI().setProgress(100, ds.getGSMInfo()[0] + " : Error Fastq-dump...");
								ds.writeLogInfo("[Unable to find any FASTQ files. (Err006-2)]");
								ds.setAnalyzeFlag();
								String tmp[] = {ds.getGSM().get(i),"Err006-2"};
								ds.setSubRunLog(tmp);

								break;
							}
						} else {
							ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
							ds.getMainUI().setSimpleLbl("Err005");
							ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
							ds.writeLogInfo("[Not enough disk space. (Err005)]");
							System.out.println(ds.getErrLog().getHddCapacity());
							ds.setAnalyzeFlag();
							String tmp[] = {ds.getGSM().get(i),"Err005"};
							ds.setSubRunLog(tmp);

							break;
						}

						// Common Analysis Workflow (Quality Check ~ Visualization)
						run_commonAnalysis("Public");

						ds.getMainUI().setRunningPrograss(false);
						
					}else{
						System.out.println("Already analyzed : "+ ds.getGSMInfo()[0]+"("+ds.getGSM().get(i)+")");
						ds.writeLogRun("Already analyzed : "+ds.getGSMInfo()[0]+"\n", true);
						ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Already analyzed");
					} 
				}else {
					// Err Write. (Log)
					
					if (ds.getErrLog().getAccessPage().equals("Err001 : Unable to access the NCBI : " + ds.getGSM().get(i))) {
						System.out.println("Err Message ("+ds.getGSM().get(i)+") : " + ds.getErrLog().getAccessPage() + "\n");
						ds.getMainUI().setProgress(100, ds.getGSM().get(i) + " : Err001");
						
						ds.getMainUI().setSimpleLbl("Err001");
						ds.writeLogRun("Error : Unable to access the NCBI. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-001\">[Err001]</a>\n\n", true);
						ds.writeLogCmd("Err Message : " + ds.getErrLog().getAccessPage() + "\n");
						ds.writeLogInfo("[Unable to access the NCBI. (Err001)]");
						ds.setAnalyzeFlag();

					} else if(ds.getErrLog().getExistErr().equals("Err002 : Unable to find GSMs (or GSE) : "+ds.getGSM().get(i))){
						System.out.println("Err Message ("+ds.getGSM().get(i)+") : " + ds.getErrLog().getAccessPage() + "\n");
						ds.getMainUI().setProgress(100, ds.getGSM().get(i) + " : Err002");
						
						ds.getMainUI().setSimpleLbl("Err002");
						ds.writeLogRun("Error : Unable to find GSMs (or GSE) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-002\">[Err002]</a> \n\n", true);
						ds.writeLogCmd("Err Message : " + ds.getErrLog().getGSMInfoErr() + "\n");
						ds.writeLogInfo("[Unable to find GSMs (or GSE) (Err002)]");
						ds.setAnalyzeFlag();
					} else {
						System.out.println("Err Message ("+ds.getGSM().get(i)+") : " + ds.getErrLog().getGSMInfoErr() + "\n");
						String subErr = ds.getErrLog().getGSMInfoErr().substring(ds.getErrLog().getGSMInfoErr().length()-9, ds.getErrLog().getGSMInfoErr().length()-1);
						ds.getMainUI().setProgress(100, ds.getGSM().get(i) + " : "+subErr);
						
						ds.getMainUI().setSimpleLbl(subErr);
						ds.writeLogRun("Error : GSM information is incorrect. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-004\">["+subErr+"]</a> : "+ds.getGSM().get(i)+"\n", true);
						ds.writeLogCmd("Err Message : " + ds.getErrLog().getGSMInfoErr() + "\n");
						ds.writeLogInfo("[GSM information is incorrect. ("+subErr+")]");
						ds.setAnalyzeFlag();
					}
				}
			}
			
			// GSE or GSM(Single) analysis Status
			if(ds.getSubRunLog().size() > 0){
				System.out.println("The analysis for ["+inputGSE.replace(" ", "")+"] has been completed but the following samples were terminated. Please see the log file : ");
				ds.writeLogRun(ds.getSubRunLog().size()+" out of "+ds.getGSM().size()+" samples were terminated due to errors:\n", true);
				
				for(int i = 0; i < ds.getSubRunLog().size(); i++){
					System.out.println(ds.getSubRunLog().get(i)[0]+" : "+ ds.getSubRunLog().get(i)[1]);
					ds.writeLogRun(ds.getSubRunLog().get(i)[0]+" : "+ ds.getSubRunLog().get(i)[1]+"\n", true);
				}
				
				System.out.println("The Path of Results : "+ds.getAnalysisPath()+inputGSE+".txt (Log)");
				
			}else if(ds.getAnalyzeFlag() == true){
				System.out.println("The analysis for ["+inputGSE.replace(" ", "")+"] has been completed but the following samples were terminated. Please see the log file : ");
				if(!ds.getErrLog().getAccessPage().equals("")){
					System.out.println(inputGSE.replace(" ", "")+" : Err001");
				}else if(!ds.getErrLog().getExistErr().equals("")){
					System.out.println(inputGSE.replace(" ", "")+" : Err002");
				}else if(!ds.getErrLog().getExperimentErr().equals("")){
					System.out.println(inputGSE.replace(" ", "")+" : Err003");
				}else{
					System.out.println(inputGSE.replace(" ", ""));
				}
				
				System.out.println("The Path of Results : "+ds.getAnalysisPath()+inputGSE+".txt (Log)");
			
			}else{
				System.out.println("The analysis for ["+inputGSE.replace(" ", "")+"] has been completed successfully.");
				System.out.println("The Path of Results : "+ds.getAnalysisPath());
				
			}
			
		}else{
			// Private

			cnt = 0;
			cntTotal = ds.getPrivateData().size();
			
			String[] dir = {"/Result/" +  ds.getResultDirName()};
			cf.makeDirectory(dir);
			
			ds.setLogAnalyzeInfo();
			
			System.out.println("\nStart analysis. ("+ds.getGSE()+")");
			
			for(int i = 0; i < ds.getPrivateData().size(); i++){
					ds.setAnalysisPath(ds.getPath()+"/Result/"+ds.getResultDirName()+"/");
					ds.getErrLog().initErr();
					cnt++;
					ds.initGSMInfo();
					ds.setGSMInfo(0, ds.getPrivateData().get(i)[2]); // Title : Sample
					ds.setGSMInfo(1, ds.getPrivateData().get(i)[0]); // Organism : sacCer3
					ds.setGSMInfo(2, ds.getPrivateData().get(i)[1].toUpperCase()); // Library : ChIP
					ds.setFastq(ds.getPrivateData().get(i)[3], ds.getPrivateData().get(i)[4]);
					ds.setGSMInfo(5, ds.getPrivateData().get(i)[5]); // Strand : Not use / Unstrand / FR ...
					ds.setPairSymbol(ds.getPrivateData().get(i)[6]);
					// Log (Option)
					ds.writeLogRun(ds.getGSE() + " : " + ds.getGSMInfo()[0] + "\n", true);
					ds.writeLogRun("Analysis : " + ds.getGSE() + " : " + ds.getGSMInfo()[0] + "(" + cnt + "/" + cntTotal+ ")\n", true);
					writeOptionLog(ds.getGSMInfo()[0]);

				if (checkAnalzedData() == false) {
					// Run Private (QC -> Mapping)
					System.out.print("Private("+ds.getPrivateData().get(i)[2]+") : ");
					run_commonAnalysis("Private");
					ds.getMainUI().setRunningPrograss(false);
					
				}else{
					System.out.println("Already analyzed : "+ds.getGSMInfo()[0]);
					ds.writeLogRun("Already analyzed : "+ds.getGSMInfo()[0]+"\n", true);
					ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Already analyzed");
				}
			}	
			
			// Private analysis Status
			if(ds.getSubRunLog().size() > 0){
				System.out.println("\nThe analysis for ["+ds.getGSE()+"] has been completed but the following samples were terminated. Please see the log file : ");
				ds.writeLogRun(ds.getSubRunLog().size()+" out of "+ds.getPrivateData().size()+" samples were terminated due to errors:\n", true);
				
				for(int j = 0; j < ds.getSubRunLog().size(); j++){
					System.out.println(ds.getSubRunLog().get(j)[0]+" : "+ ds.getSubRunLog().get(j)[1]);
					ds.writeLogRun(ds.getSubRunLog().get(j)[0]+" : "+ ds.getSubRunLog().get(j)[1]+"\n", true);
				}
				
				System.out.println("The Path of Results : "+ds.getAnalysisPath()+ds.getGSE()+".txt (Log)");

			}else{
				System.out.println("The analysis for ["+ds.getGSE()+"] has been completed successfully.");
				System.out.println("The Path of Results : "+ds.getAnalysisPath());
			}
		}
		
		if(ds.getGSM().size() > 0){
			ds.closeAnalyzeInfo();
		}
		ds.getMainUI().setRunningPrograss(false);
	}
	
	public void run_commonAnalysis(String data){
		
		ds.getMainUI().setGseLabel("- "+ds.getGSE()+" -");
		
		
		QC_Trimming qc_t = new QC_Trimming(ds, cf);
		
		// check remain Hdd capacity (Up to 2Gb)
		if (cf.checkHDDCapacity() == true) {
			qc_t.use_Fastqc(data);
			
			if(cf.checkStepPass()){
				System.out.println("Step (FastQC) : Err006-3");
				ds.getMainUI().setSimpleLbl("Err006-3");
				ds.writeLogRun("Error : Unable to find any fastqc file. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-3\">[Err006-3]</a> \n\n", true);
				ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Error FastQC...");
				ds.writeLogInfo("[Unable to find any fastqc file. (Err006-3)]");
				ds.setAnalyzeFlag();
				if(data.equals("Private")){
					String tmp[] = {ds.getGSMInfo()[0],"Err006-3"};
					ds.setSubRunLog(tmp);
				}else{
					String tmp[] = {ds.getRunGSM(),"Err006-3"};
					ds.setSubRunLog(tmp);					
				}

				return;
			}
		}else{
			ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
			ds.getMainUI().setSimpleLbl("Err005");
			ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
			ds.writeLogInfo("[Not enough disk space. (Err005)]");
			System.out.println(ds.getErrLog().getHddCapacity());
			ds.setAnalyzeFlag();
			
			if(data.equals("Private")){
				String tmp[] = {ds.getGSMInfo()[0],"Err005"};
				ds.setSubRunLog(tmp);
			}else{
				String tmp[] = {ds.getRunGSM(),"Err005"};
				ds.setSubRunLog(tmp);
			}

			return;
		}
		
		if (!ds.getOmitStep("Trim")){
			
			// check remain Hdd capacity (Up to 2Gb)
			if (cf.checkHDDCapacity() == true) {
				qc_t.use_Trimming(data);
				if(cf.checkStepPass()){
					System.out.println("Step (Trimmomatic) : Err006-4");
					ds.getMainUI().setSimpleLbl("Err006-4");
					ds.writeLogRun("Error : The Trimming tool is not working. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-4\">[Err006-4]</a> \n\n", true);
					ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Error Trimmomatic...");
					ds.writeLogInfo("[The Trimming tool is not working. (Err006-4)]");
					ds.setAnalyzeFlag();
					if(data.equals("Private")){
						String tmp[] = {ds.getGSMInfo()[0],"Err006-4"};
						ds.setSubRunLog(tmp);
					} else {
						String tmp[] = { ds.getRunGSM(), "Err006-4" };
						ds.setSubRunLog(tmp);
					} 
					return;
				}
				File fr = new File(ds.getForwardFastq());
				long L = fr.length();
				if(L == 0){
					System.out.println("Error : No reads are available after trimming.");
					ds.getMainUI().setSimpleLbl("Err006-4");
					ds.writeLogRun("Error : No reads are available after trimming. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-4\">[Err006-4]</a> \n\n", true);
					ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Error Trimmomatic...");
					ds.writeLogInfo("[No reads are available after trimming. (Err006-4)]");
					ds.setAnalyzeFlag();
					if(data.equals("Private")){
						String tmp[] = {ds.getGSMInfo()[0],"Err006-4"};
						ds.setSubRunLog(tmp);
					} else {
						String tmp[] = { ds.getRunGSM(), "Err006-4" };
						ds.setSubRunLog(tmp);
					}
					return;
				}
				
			} else {
				ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
				ds.getMainUI().setSimpleLbl("Err005");
				ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
				ds.writeLogInfo("[Not enough disk space. (Err005)]");
				System.out.println(ds.getErrLog().getHddCapacity());
				ds.setAnalyzeFlag();
				if(data.equals("Private")){
					String tmp[] = {ds.getGSMInfo()[0],"Err005"};
					ds.setSubRunLog(tmp);
				} else {
					String tmp[] = { ds.getRunGSM(), "Err005" };
					ds.setSubRunLog(tmp);
				}
				return;
			}
		}
		
		// check remain Hdd capacity (Up to 2Gb)
		Mapping map;
		if (cf.checkHDDCapacity() == true) {
			map = new Mapping(ds, cf,data);
		}
		else{
			ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
			ds.getMainUI().setSimpleLbl("Err005");
			ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
			ds.writeLogInfo("[Not enough disk space. (Err005)]");
			System.out.println(ds.getErrLog().getHddCapacity());
			ds.setAnalyzeFlag();
			if(data.equals("Private")){
				String tmp[] = {ds.getGSMInfo()[0],"Err005"};
				ds.setSubRunLog(tmp);
			} else {
				String tmp[] = { ds.getRunGSM(), "Err005" };
				ds.setSubRunLog(tmp);
			}
			return;
		}
		
		// check remain Hdd capacity (Up to 2Gb)
		if (cf.checkHDDCapacity() == true) {
			map.Mapping();
			
			if(cf.checkStepPass()){
				System.out.println("Step ("+ds.getAlignTools()+") : Err006-5");
				ds.getMainUI().setSimpleLbl("Err006-5");
				ds.writeLogRun("Error : The alignment tool is not working. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-5\">[Err006-5]</a> \n\n", true);
				ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Error "+ds.getAlignTools()+"...");
				ds.writeLogInfo("[The alignment tool is not working. (Err006-5)]");
				ds.setAnalyzeFlag();
				if(data.equals("Private")){
					String tmp[] = {ds.getGSMInfo()[0],"Err006-5"};
					ds.setSubRunLog(tmp);
				} else {
					String tmp[] = { ds.getRunGSM(), "Err006-5" };
					ds.setSubRunLog(tmp);
				}
				return;
			}
		}
		else{
			ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
			ds.getMainUI().setSimpleLbl("Err005");
			ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
			ds.writeLogInfo("[Not enough disk space. (Err005)]");
			System.out.println(ds.getErrLog().getHddCapacity());
			ds.setAnalyzeFlag();
			if(data.equals("Private")){
				String tmp[] = {ds.getGSMInfo()[0],"Err005"};
				ds.setSubRunLog(tmp);
			} else {
				String tmp[] = { ds.getRunGSM(), "Err005" };
				ds.setSubRunLog(tmp);
			}
			return;
		}
		
		if (!ds.getOmitStep("Sort")) {
			
			if (cf.checkHDDCapacity() == true) {
				map.Sorting();
				
				if(cf.checkStepPass()){
					System.out.println("Step (Sorting) : Err006-6");
					ds.getMainUI().setSimpleLbl("Err006-6");
					ds.writeLogRun("Error : The sorting tool is not working. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-006-6\">[Err006-6]</a> \n\n", true);
					ds.getMainUI().setProgress(100, ds.getGSMInfo()[0]+" : Error Sorting...");
					ds.writeLogInfo("[The sorting tool is not working. (Err006-6)]");
					ds.setAnalyzeFlag();
					if(data.equals("Private")){
						String tmp[] = {ds.getGSMInfo()[0],"Err006-6"};
						ds.setSubRunLog(tmp);
					} else {
						String tmp[] = { ds.getRunGSM(), "Err006-6" };
						ds.setSubRunLog(tmp);
					}
					return;
				}
			} else {
				ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
				ds.getMainUI().setSimpleLbl("Err005");
				ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
				ds.writeLogInfo("[Not enough disk space. (Err005)]");
				System.out.println(ds.getErrLog().getHddCapacity());
				ds.setAnalyzeFlag();
				if(data.equals("Private")){
					String tmp[] = {ds.getGSMInfo()[0],"Err005"};
					ds.setSubRunLog(tmp);
				} else {
					String tmp[] = { ds.getRunGSM(), "Err005" };
					ds.setSubRunLog(tmp);
				}
				return;
			}
		}else{
			//Convert bam to Cram
			if(ds.getCompressCram() == true){
				map.bamToCram();				
			}
		}
		
		Visualization vi = new Visualization(ds, cf);
		if(cf.checkHDDCapacity() == true){
			vi.use_Homer(data);
		}else{
			ds.getErrLog().setHddCapacity("Err005 : Not enough disk space.");
			ds.getMainUI().setSimpleLbl("Err005");
			ds.writeLogRun("Error : Not enough disk space. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-005\">[Err005]</a>\n\n", true);
			ds.writeLogInfo("[Not enough disk space. (Err005)]");
			System.out.println(ds.getErrLog().getHddCapacity());
			ds.setAnalyzeFlag();
			if(data.equals("Private")){
				String tmp[] = {ds.getGSMInfo()[0],"Err005"};
				ds.setSubRunLog(tmp);
			} else {
				String tmp[] = { ds.getRunGSM(), "Err005" };
				ds.setSubRunLog(tmp);
			}
			return;
		}
		System.out.println();
		ds.writeLogRun("Analysis : Completed "+ds.getGSMInfo()[0]+"\n", true);
		ds.writeLogInfo("[Completed]");

	}
	
	public void prepareRun(String data){
		
		ds.initAnalyzeFlag();
		
		if(ds.getMainUI().getRunThread() == false){
		Thread auto = new Thread(){
			public void run(){
				ds.getMainUI().setRunThread(true);
		
					if (data.equals("Public")) {
						// Open File
						if (!ds.getInputText().equals("") && ds.getInputText().length() > 4 && ds.getInputText().substring(0, 4).equals("File")) {

							for (int i = 0; i < ds.getFilePath().size(); i++) {
								System.out.println(ds.getFilePath().get(i));
							}

							for (int i = 0; i < ds.getFilePath().size(); i++) {
								FileReader fr;
								try {
									fr = new FileReader(ds.getFilePath().get(i));
									BufferedReader br = new BufferedReader(fr);
									String line;

									while ((line = br.readLine()) != null) {
										if (line.length() > 2) {
											if (line.substring(0, 3).toUpperCase().equals("GSM")
													|| line.substring(0, 3).toUpperCase().equals("GSE")) {
												// Parsing Start
												String inputGSE = line;
												run_checkData(inputGSE, "Public");

											} else if(line.charAt(0) == '#'){
												continue;
											}else {
												System.out.println("Open file : " + ds.getFilePath().get(i));
												System.out.println("Wrong Data : " + line
														+ "\nPlease check the GSE/GSM accession number list. (Public data)");
											}
										}
									}
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									//e1.printStackTrace();
								}
							}
						} else {
							if (!ds.getInputText().equals("")) {
								// Input GSE/GSM Number
								String inputTxt = ds.getInputText().replace(" ", "");
								run_checkData(inputTxt, "Public");
							}
						}

				}else{
					run_checkData("", "Private");	
				}
				ds.getMainUI().setRunThread(false);
				
				if(ds.getAnalyzeFlag()){
					System.out.println("\n[The analysis has been failed]");
				}else{
					System.out.println("\n[The analysis has been completed successfully]");					
				}
			}
		};
		auto.start();
		
		}
	}
	
	public boolean checkAnalzedData(){
		boolean checkFlag = false;
		File f = new File(ds.getPath()+"Result/"+ds.getGSE()+"/"+ds.getGSE()+".txt");
		
		if(f.exists()){
			try {
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);

				String line;
				
				while ((line = br.readLine()) != null) {
					String tmp[] = line.split("\t");
					String alignTool = "Hisat2";
					if(tmp[4].equals("RNA-SEQ")){
						alignTool = ds.getAlignTools();
					}
					if (!tmp[0].equals("")) {
						if (!f.getName().subSequence(0, 2).equals("P_")) {
							//public
							if (ds.getGSMInfo()[0].equals(tmp[3]) && ds.getGSMInfo()[1].equals(tmp[5])
									&& ds.getGSMInfo()[2].equals(tmp[4]) && alignTool.equals(tmp[6])) {
								if (tmp[7].equals("[Completed]")) {
									checkFlag = true;
									break;
								}
							}
						}else{
							// Private
							if (ds.getGSMInfo()[0].equals(tmp[2]) && ds.getGSMInfo()[1].equals(tmp[4])
									&& ds.getGSMInfo()[2].equals(tmp[3]) && alignTool.equals(tmp[5])) {
								if (tmp[6].equals("[Completed]")) {
									checkFlag = true;
									break;
								}
							}
						}
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		}
		
		if(ds.getContinueAnalyze()){
			return checkFlag;	
		}else{
			return false;
		}
		
	}
	
	public void writeOptionLog(String gsm){
		// Log
		ds.writeLogCmd("\n# ============================================================\n");
		ds.writeLogCmd("# "+ds.getGSE()+" : " + gsm +"(" + cnt + "/" + cntTotal + ")\n");
		if(ds.getLatest()){
			ds.writeLogCmd("# Latest Genome Version.\n");
		}else{
			ds.writeLogCmd("# Old Genome Version.\n");
		}
		ds.writeLogCmd("# Analyze the data in succession : "+ds.getContinueAnalyze()+"\n");
		ds.writeLogCmd("# Omit Process (Trimming) : "+ds.getOmitStep("Trim")+"\n");
		ds.writeLogCmd("# Omit Process (Sorting) : "+ds.getOmitStep("Sort")+"\n");
		ds.writeLogCmd("# CPU : "+ds.getAnalysisCPU()+"\n");
		ds.writeLogCmd("# Remove Files : ");
		for(int i = 0; i < 6; i++){
			if(ds.getRemove(i) == true && i == 0){
				ds.writeLogCmd("SRA ");
			}else if(ds.getRemove(i) == true && i == 1){
				ds.writeLogCmd("Fastq ");
			}else if(ds.getRemove(i) == true && i == 2){
				ds.writeLogCmd("Fastqc ");
			}else if(ds.getRemove(i) == true && i == 3){
				ds.writeLogCmd("Trimming ");
			}else if(ds.getRemove(i) == true && i == 4){
				ds.writeLogCmd("BAM ");
			}else if(ds.getRemove(i) == true && i == 5){
				ds.writeLogCmd("Sorted_Bam ");
			}
		}
		
		ds.writeLogCmd("\n# Title : "+ds.getGSMInfo()[0]+"\n");
		ds.writeLogCmd("# Organism : "+ds.getGSMInfo()[1]+"\n");
		ds.writeLogCmd("# Library : "+ds.getGSMInfo()[2]+"\n");
		if(ds.getGSE().substring(0,2).equals("P_")){
			ds.writeLogCmd("# Strand : "+ds.getGSMInfo()[5]+"\n");
		}else{
			ds.writeLogCmd("# Strand : "+ds.getPublicStrand()+"\n");
		}
		if(ds.getGSMInfo()[2].toUpperCase().equals("RNA-SEQ")){
			ds.writeLogCmd("# Mapping Tool : "+ds.getAlignTools()+"\n");	
		}else{
			ds.writeLogCmd("# Mapping Tool : Hisat2\n");
		}
		
		ds.writeLogCmd("# ============================================================\n\n");
	}
}
