package Octopus_Src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.apache.commons.io.FileUtils;

public class PreProcessing {
	
	private DataSet ds;
	private CommonFunc cf;
	
	public PreProcessing(DataSet ds,CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
	}
	
	public void use_Prefetch(){
		ds.getMainUI().setProgress(10, "Prefetch : "+ ds.getGSMInfo()[0]);
		ds.writeLogCmd("# Download SRA format file using the Prefetch\n");
		
		ds.getMainUI().setRunningPrograss(true);
		
		String[] fold = {
				"/Result/" +  ds.getGSE() + "/00_Fastq",
				"/Result/" +  ds.getGSE() + "/00_SRA",
				"/Result/" +  ds.getGSE() + "/00_Download",
				};
		
		cf.makeDirectory(fold);

		String resultPrefetch = "";
		String tmpSrr[] = ds.getGSMInfo()[4].split(" ");

		ds.writeLogRun("Preprocessing : Prefetch (Start)\n", true);

		if (tmpSrr.length > 1) {
			// Multi
			System.out.print("Public("+ds.getGSM().get(ds.getMainProcess().cnt-1)+") : Prefetch -> ");
			for (int i = 0; i < tmpSrr.length; i++) {
				String writeCmd = "";
				if (ds.getFullOption()) {
					String prefetch_Cmd[] = makePrefetchCmd(tmpSrr[i]);
					for (int j = 0; j < prefetch_Cmd.length; j++) {
						writeCmd = writeCmd + prefetch_Cmd[j] + " ";
					}
					resultPrefetch= cf.shellCmd(prefetch_Cmd);
				} else {
					String prefetch_Cmd[] = {
							ds.getPath() + "/Tools/sratoolkit/bin/prefetch", tmpSrr[i], "-X", "1000G",
							"--output-directory", ds.getAnalysisPath() + "/00_Download/"};
					writeCmd = ds.getPath() + "/Tools/sratoolkit/bin/prefetch "+tmpSrr[i]+" -X 1000G --output-directory "+ds.getAnalysisPath() + "/00_Download/";
					resultPrefetch = cf.shellCmd(prefetch_Cmd);
				}

				// Log
				ds.writeLogCmd(writeCmd + "\n\n");
			}

		} else {
			// Single
			System.out.print("Public("+ds.getGSM().get(ds.getMainProcess().cnt-1)+") : Prefetch -> ");
			String writeCmd = "";
			if (ds.getFullOption()) {
				String prefetch_Cmd[] = makePrefetchCmd(ds.getGSMInfo()[4]);
				for (int j = 0; j < prefetch_Cmd.length; j++) {
					writeCmd = writeCmd + prefetch_Cmd[j] + " ";
				}
				resultPrefetch = cf.shellCmd(prefetch_Cmd);
			}else{
				String prefetch_Cmd[] = {ds.getPath() + "/Tools/sratoolkit/bin/prefetch", ds.getGSMInfo()[4], "-X", "1000G",
						"--output-directory", ds.getAnalysisPath() + "/00_Download/"};	
				writeCmd = ds.getPath() + "/Tools/sratoolkit/bin/prefetch "+ds.getGSMInfo()[4]+" -X 1000G --output-directory "+ds.getAnalysisPath() + "/00_Download/";
				resultPrefetch = cf.shellCmd(prefetch_Cmd);

			}

			// Log
			ds.writeLogCmd(writeCmd + "\n\n");
		}
	
		if (resultPrefetch.substring(1, 8).equals("Session")) {
			// Error
			ds.getErrLog().setStepErr(0, true);
			System.out.println("\nError : Can't access ncbi ftp server. (Err006-1)");
		}

		ds.writeLogRun("Preprocessing : Prefetch (End)\n", true);
	
	}
	
	public String[] makePrefetchCmd(String srr){
		String fullOption = ds.getToolOPtion(0); //prefetch option;
		String tmp[] = fullOption.split(" ");
		String testCmd[];
		
		int start = 0;
		testCmd = new String[6];
		testCmd[0] = ds.getPath() + "/Tools/sratoolkit/bin/prefetch";
		testCmd[1] = srr;
		testCmd[2] = "-X";
		testCmd[3] = "1000G";
		testCmd[4] = "--output-directory";
		testCmd[5] = ds.getAnalysisPath()+"/00_Download/";
		
		return testCmd;
	}
	
	
	public void use_FastqDump(){
		ds.writeLogCmd("# Convert SRA to Fastq using the Fastq-dump\n");
		ds.writeLogRun("Preprocessing : Fastq-Dump (Start)\n", true);
		ds.getMainUI().setProgress(20,"Fastq-dump : "+ds.getGSMInfo()[0]);
		
		String find_Cmd[] = {"find",ds.getAnalysisPath()+"00_Download/","-name","*.sra"};
		String sra = cf.shellCmd(find_Cmd);
		String tmpSra[] = sra.split("\n");
		
		Arrays.sort(tmpSra, String.CASE_INSENSITIVE_ORDER);
		
		String tmpDir[] = {"mkdir",ds.getAnalysisPath()+"/00_Fastq/tmp"};
		cf.shellCmd(tmpDir);
		
		for(int i = 0; i < tmpSra.length; i++){
			String writeCmd = "";
			System.out.print("Fastq-Dump -> ");
			
			if(ds.getFullOption()){
				String fastq_dump_Cmd[] = makeFastqDumpCmd(tmpSra[i]);
				for(int j = 0; j < fastq_dump_Cmd.length; j++){
					writeCmd = writeCmd+fastq_dump_Cmd[j]+" ";
				}
				cf.shellCmd(fastq_dump_Cmd);
			}else{
				String fastq_dump_Cmd[] = {ds.getPath() + "/Tools/sratoolkit/bin/fastq-dump","-O",ds.getAnalysisPath()+"/00_Fastq/tmp","--split-3",tmpSra[i]};
				writeCmd = ds.getPath()+"Tools/sratoolkit/bin/fastq-dump -O "+ds.getAnalysisPath()+"00_Fastq/tmp --split-3 "+tmpSra[i];
				cf.shellCmd(fastq_dump_Cmd);
			}
			
			// Log
			ds.writeLogCmd(writeCmd+"\n\n");			
		}
		
		
		// Remove Tmp Folder
		try {
			if(ds.getRemove(0) == false){
				for(int i = 0; i < tmpSra.length; i++){
					File file = new File(tmpSra[i]);
					String[] tmp = tmpSra[i].split("/");
					File fileToMove = new File(ds.getAnalysisPath()+"00_SRA/"+tmp[tmp.length-1]);
					file.renameTo(fileToMove);
				}
			}
			FileUtils.deleteDirectory(new File(ds.getAnalysisPath()	+ "00_Download/"));
			
			if(ds.getRemove(0)){
				cf.removeFile("SRA");	
			}
			
		} catch (IOException e) {
			
		}
		
		ds.writeLogRun("Preprocessing : Fastq-Dump (End)\n", true);
	}

	public String[] makeFastqDumpCmd(String sra){
		String fullOption = ds.getToolOPtion(1); //fastq-dump option;
		String tmp[] = fullOption.split(" ");
		String testCmd[];
		
		int start = 0;
		if(tmp[0].equals("")){
			testCmd = new String[4+tmp.length];
			start = 1;
		}else{
			testCmd = new String[5+tmp.length];
		}
		testCmd[0] = ds.getPath() + "/Tools/sratoolkit/bin/fastq-dump";
		int idx = 1;
		for(int i = start; i < tmp.length; i++){
			testCmd[idx++] = tmp[i];
		}
		testCmd[idx++] = "-O";
		testCmd[idx++] = ds.getAnalysisPath()+"/00_Fastq/tmp";
		testCmd[idx++] = "--split-3";
		testCmd[idx++] = sra;
		
		return testCmd;
	}
	
	public void mergeFastq() {
		File dir = new File(ds.getAnalysisPath() + "00_Fastq/tmp/");
		File[] fastqList = dir.listFiles();
		String spacepath = ds.getAnalysisPath().replace(" ", "\\ ");

		ArrayList<String> single = new ArrayList<>();
		ArrayList<String> forward = new ArrayList<>();
		ArrayList<String> reverse = new ArrayList<>();

		// Extract Fastq, Single/Paired End
		for (int i = 0; i < fastqList.length; i++) {
			String str = fastqList[i].getName().toLowerCase();
			if (str.endsWith("_1.fastq") || str.endsWith("_1.fq")) {
				forward.add(fastqList[i].toString());
			} else if (str.endsWith("_2.fastq") || str.endsWith("_2.fq")) {
				reverse.add(fastqList[i].toString());
			} else if (str.endsWith(".fastq") || str.endsWith(".fq")) {
				single.add(fastqList[i].toString());
			}
		}
		// make Command
		boolean flag_merge = false;
		try {
			FileWriter fw = new FileWriter(ds.getPath() + "Script/mergeFastq.sh");
			// Paired-End
			if (forward.size() != 0) {
				String fr = "";
				String rv = "";
				
				Collections.sort(forward);
				Collections.sort(reverse);

				try {
					for (int i = 0; i < forward.size(); i++) {
						fr = fr + forward.get(i).replace(" ", "\\ ") + " ";
						rv = rv + reverse.get(i).replace(" ", "\\ ") + " ";
					}
				} catch (Exception e) {
					System.out.println("\nError : Not matched paired Fastq, Fr_Count(" + forward.size() + "),Rv_Count("
							+ reverse.size() + ") (Err010)");
				}
				if (forward.size() == 1) {
					fw.write("mv " + fr + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_1.fastq\n");
					fw.write("mv " + rv + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" +ds.getGSMInfo()[0] + "_2.fastq\n");

					// Log
					ds.writeLogCmd("mv " + fr + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_1.fastq\n");
					ds.writeLogCmd("mv " + rv + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_2.fastq\n\n");

				} else {
					ds.writeLogRun("Preprocessing : Merge the Fastq files. (Start)\n", true);
					ds.writeLogCmd("# Merge Fastq format data.\n");
					ds.getMainUI().setProgress(30, "Merge Fastq : " + ds.getGSMInfo()[0]);

					String merge_CmdFr = "cat " + fr + " > " + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0]
							+ "_1.fastq\n";
					String merge_CmdRv = "cat " + rv + " > " + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0]
							+ "_2.fastq\n";
					fw.write(merge_CmdFr);
					fw.write(merge_CmdRv);

					// Log
					ds.writeLogCmd("cat " + fr + " > " + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_1.fastq\n");
					ds.writeLogCmd("cat " + rv + " > " + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_2.fastq\n\n");
					
					flag_merge = true;

				}
				// Save Fastq Path
				ds.setFastq(ds.getAnalysisPath() + "/00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_1.fastq",
						ds.getAnalysisPath() + "/00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + "_2.fastq");
			} else {
				// Single-End

				String se = "";
				Collections.sort(single);
				
				
				for (int i = 0; i < single.size(); i++) {
					se = se + single.get(i).replace(" ", "\\ ") + " ";
				}
				if (single.size() == 0) {
					// Error
					ds.getErrLog().setStepErr(1, true);
					System.out.println("\nError : Can't find converted Fastq File. (Err006-2)");
				} else if (single.size() == 1) {
					fw.write("mv " + se + spacepath + "00_Fastq/"+ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + ".fastq");

					// Log
					ds.writeLogCmd("mv " + se + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + ".fastq\n\n");
				} else {

					ds.writeLogRun("Preprocessing : Merge the Fastq files. (Start)\n", true);
					ds.writeLogCmd("# Merge Fastq format data.\n");
					ds.getMainUI().setProgress(30, "Merge Fastq : " + ds.getGSMInfo()[0]);

					fw.write("cat " + se + " > " + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + ".fastq\n");

					// Log
					ds.writeLogCmd("cat " + se + " > " + spacepath + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + ".fastq\n\n");
					
					flag_merge = true;
				}
				// Save Fastq Path
				ds.setFastq(ds.getAnalysisPath() + "00_Fastq/" +ds.getRunGSM()+"_" + ds.getGSMInfo()[0] + ".fastq", "");
			}

			fw.flush();
			fw.close();

			String cmdInstall1[] = { "chmod", "777", ds.getPath() + "/Script/mergeFastq.sh" };
			String cmdInstall2[] = { "sh", ds.getPath() + "/Script/mergeFastq.sh" };

			cf.shellCmd(cmdInstall1);
			cf.shellCmd(cmdInstall2);
			
			if(flag_merge == true){
				ds.writeLogRun("Preprocessing : Merge the Fastq files. (End)\n", true);				
			}
			
			new File(ds.getAnalysisPath() + "00_Fastq/tmp").delete();

		} catch (Exception e) {
			System.out.println("\nError : Unable to make the mergeFastq.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the mergeFastq.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
	}
}