package Octopus_Src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class QC_Trimming {
	private DataSet ds;
	private CommonFunc cf;
	private boolean replicate;
	
	public QC_Trimming(DataSet ds,CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		replicate = false;
		
		String[] fold = {"/Result/" +  ds.getGSE() + "/01_Fastqc",
				"/Result/" +  ds.getGSE() + "/02_Bam"};
		
		cf.makeDirectory(fold);
	}
	
	public void use_Fastqc(String data){
		ds.writeLogCmd("# Check the quality of Fastq using the FastQC\n");
		ds.getMainUI().setProgress(40, "FastQC : " + ds.getGSMInfo()[0]);
		
		ds.getMainUI().setRunningPrograss(true);
		String fileName = ds.getPath() + "Script/mergeFastq.sh";
		String spacePath = ds.getAnalysisPath().replace(" ", "\\ ");
		
		try {
			FileWriter fw = new FileWriter(fileName);
			// Merge for Private Replicate Data. 
			if(ds.getForwardFastq().split("  ").length > 1){
				
				ds.writeLogRun("Preprocessing : Processed Replication.(Start)\n", true);
				
				String tmp[] = ds.getForwardFastq().split("  ");
				
				if(ds.getReverseFastq().equals("")){
					// Single
					if(tmp[0].endsWith(".gz")){
						fw.write("cat "+ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+".fastq.gz\n");
						ds.setFastq(ds.getAnalysisPath()+"00_Fastq/"+ds.getGSMInfo()[0]+".fastq.gz", "");
						
						//Log
						ds.writeLogCmd("cat "+ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+".fastq.gz\n\n");
					}else{
						fw.write("cat "+ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+".fastq\n");
						ds.setFastq(ds.getAnalysisPath()+"00_Fastq/"+ds.getGSMInfo()[0]+".fastq", "");	

						//Log
						ds.writeLogCmd("cat "+ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+".fastq\n\n");
					}
				}else{
					//paired
					if(tmp[0].endsWith(".gz")){
						fw.write("cat "+ds.getForwardFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_1.fastq.gz\n");
						fw.write("cat "+ds.getReverseFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_2.fastq.gz\n");
						ds.setFastq(ds.getAnalysisPath()+"00_Fastq/"+ds.getGSMInfo()[0]+"_1.fastq.gz", ds.getAnalysisPath()+"00_Fastq/"+ds.getGSMInfo()[0]+"_2.fastq.gz");
						
						//Log
						ds.writeLogCmd("cat "+ds.getForwardFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_1.fastq.gz\n");
						ds.writeLogCmd("cat "+ds.getForwardFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_2.fastq.gz\n\n");
						
					}else{
						fw.write("cat "+ds.getForwardFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_1.fastq\n");
						fw.write("cat "+ds.getReverseFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_2.fastq\n");
						ds.setFastq(ds.getAnalysisPath()+"00_Fastq/"+ds.getGSMInfo()[0]+"_1.fastq", ds.getAnalysisPath()+"00_Fastq/"+ds.getGSMInfo()[0]+"_2.fastq");
						
						//Log
						ds.writeLogCmd("cat "+ds.getForwardFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_1.fastq\n");
						ds.writeLogCmd("cat "+ds.getForwardFastq().replace(" ","\\ ").replace("\\ \\ ", " ") + " > "+ spacePath+"00_Fastq/"+ds.getGSMInfo()[0]+"_2.fastq\n\n");
					}
				}
				replicate = true;
				
				fw.flush();
				fw.close();
				
				String cmdQC1[] = {"chmod","777",ds.getPath()+ "/Script/mergeFastq.sh"};
				String cmdQC2[] = {"sh",ds.getPath() + "/Script/mergeFastq.sh"};

				cf.shellCmd(cmdQC1);
				cf.shellCmd(cmdQC2);
				
				ds.writeLogRun("Preprocessing : Processed Replication.(End)\n", true);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\nError : Unable to make the mergeFastq.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the mergeFastq.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
		
		try {
			ds.writeLogRun("Quality Control : FastQC (Start)\n", true);
			System.out.print("Fastqc -> ");
			FileWriter fw = new FileWriter(ds.getPath() + "Script/FastQC.sh");
			// Single-End
			String fr = "";
			String rv = "";
			String spacePath2 = ds.getPath().replace(" ","\\ ");
			int availableMem = ds.getMainUI().getUI_Tool_Option().getAvailableMem();
			int memPerCPU = (availableMem*1000)/Integer.parseInt(ds.getAnalysisCPU());
			String fullOption = ds.getToolOPtion(2); //fastqc
							
			if(ds.getReverseFastq().equals("")){	
				if(data.equals("Public")){
					fr = spacePath +"00_Fastq/"+ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+".fastq";
				}else{
					fr = ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ");
				}

				String fastqc_Cmd = "";
				
				if(ds.getFullOption()){
					fastqc_Cmd = spacePath2+"Tools/FastQC/fastqc "+ fr +" -alloMem "+memPerCPU+ " -o " + spacePath+"01_Fastqc -t "+ds.getAnalysisCPU()+" --extract "+fullOption;
				}else{
					fastqc_Cmd = spacePath2+"Tools/FastQC/fastqc "+ fr +" -alloMem "+memPerCPU+ " -o " + spacePath+"01_Fastqc -t "+ds.getAnalysisCPU()+" --extract";	
				}
				
				fw.write(fastqc_Cmd+"\n");
				
				//Log
				ds.writeLogCmd(fastqc_Cmd+"\n\n");
			}else{
				// Paired-End
				if(data.equals("Public")){
					fr = spacePath +"00_Fastq/"+ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+"_1.fastq";
					rv = spacePath +"00_Fastq/"+ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+"_2.fastq";
				}else{
					fr = ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ");
					rv = ds.getReverseFastq().replace(" ", "\\ ").replace("\\ \\ ", " ");
				}
				
				String fastqc_Cmd_Fr = "";
				String fastqc_Cmd_Rv = "";
				
				if(ds.getFullOption()){
					fastqc_Cmd_Fr = spacePath2+"Tools/FastQC/fastqc "+ fr +" -alloMem "+memPerCPU+ " -o " + spacePath+"01_Fastqc -t "+ds.getAnalysisCPU()+" --extract "+fullOption;
					fastqc_Cmd_Rv = spacePath2+"Tools/FastQC/fastqc "+ rv +" -alloMem "+memPerCPU+ " -o " + spacePath+"01_Fastqc -t "+ds.getAnalysisCPU()+" --extract "+fullOption;
				}else{
					fastqc_Cmd_Fr = spacePath2+"Tools/FastQC/fastqc "+ fr +" -alloMem "+memPerCPU+ " -o " + spacePath+"01_Fastqc -t "+ds.getAnalysisCPU()+" --extract";
					fastqc_Cmd_Rv = spacePath2+"Tools/FastQC/fastqc "+ rv +" -alloMem "+memPerCPU+ " -o " + spacePath+"01_Fastqc -t "+ds.getAnalysisCPU()+" --extract";
				}
				
				fw.write(fastqc_Cmd_Fr+"\n");
				fw.write(fastqc_Cmd_Rv+"\n");

				//Log
				ds.writeLogCmd(fastqc_Cmd_Fr+"\n");
				ds.writeLogCmd(fastqc_Cmd_Rv+"\n\n");
			}
			
			fw.flush();
			fw.close();
			
			
			String cmdFastqc1[] = {"chmod","777",ds.getPath()+ "/Script/FastQC.sh"};
			String cmdFastqc2[] = {"sh",ds.getPath() + "/Script/FastQC.sh"};

			cf.shellCmd(cmdFastqc1);
			cf.shellCmd(cmdFastqc2);
			
			// Get Encoding Information;
			checkEncoding(data);

			//Remove Fastqc File
			if(ds.getRemove(2)){
				cf.removeFile("Fastqc");	
			}
			
		}catch(Exception e){
			System.out.println("\nError : Unable to make the FastQC.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the FastQC.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
		
		ds.writeLogRun("Quality Control : FastQC (End)\n", true);
	}
	
	public void checkEncoding(String data){
		if(data.equals("Public")){
			if(ds.getReverseFastq().equals("")){
				//Single-end
				String encoding = getEncodeInFastqc(ds.getRunGSM()+"_"+ds.getGSMInfo()[0]);
				ds.setEncoding(encoding, "");
				
			}else{
				//Paired-end
				String encoding_1 = getEncodeInFastqc(ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+"_1");
				String encoding_2 = getEncodeInFastqc(ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+"_2");
				ds.setEncoding(encoding_1, encoding_2);
			}	
		}else{
			if(ds.getReverseFastq().equals("")){
				//Single-end
				String encoding = getEncodeInFastqc(ds.getGSMInfo()[0]);
				ds.setEncoding(encoding, "");
				
			}else{
				//Paired-end
				String encoding_1 = getEncodeInFastqc(ds.getGSMInfo()[0]+"_1");
				String encoding_2 = getEncodeInFastqc(ds.getGSMInfo()[0]+"_2");
				ds.setEncoding(encoding_1, encoding_2);
			}			
		}		
	}
	
	public String getEncodeInFastqc(String filePath){
		
		String address = ds.getAnalysisPath()+"01_Fastqc/"+filePath;
		String encoding = "sanger";
		try{
			FileReader fr = new FileReader(address+"_fastqc/fastqc_data.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			while((line = br.readLine())!= null){
				String tmp[] = line.split("\t");
				if (tmp[0].equals("Encoding")) {
					String tmp2[] = tmp[tmp.length-1].split(" ");
					double version = Double.parseDouble(tmp2[tmp2.length-1]);
					if (version >= 1.8) {
					// 	Phred33
					} else if(version >= 1.3 && version <= 1.7) {
						encoding = "illumina";
					}else{
						encoding = "solexa";
					}
					break;
				}
			}
			
			br.close();
			fr.close();
		
		}catch(Exception e){
			ds.getErrLog().setStepErr(2, true);
		}
		
		return encoding;
	}
	
	public void use_Trimming(String data){
		ds.writeLogCmd("# Trim the Fastq data using the Trimmomatic.\n");
		ds.writeLogRun("Trimming : Trimmomatic (Start)\n", true);
		ds.getMainUI().setProgress(50, "Trimming : " + ds.getGSMInfo()[0]);
		
		System.out.print("Trimmomatic -> ");
		String spacePath = ds.getAnalysisPath().replace(" ", "\\ ");
		String trimFr = "";
		String trimRv = "";
		String encoding = "";
		
		try {
			
			FileWriter fw = new FileWriter(ds.getPath() + "Script/Trimming.sh");
			String fullOption = ds.getToolOPtion(3); // 3 : trimmomatic
			
			if(ds.getReverseFastq().equals("")){
				//Single-end
				String fq = "";
				if(data.equals("Public")){
					fq = spacePath+"00_Fastq/"+ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+".fastq";
				}else{
					fq = ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ");
				}
				
				if(ds.getEncodingFr().equals("sanger")){
					encoding = "33";
				}else{
					encoding = "64";
				}
				
				trimFr = spacePath+"00_Fastq/Trim_"+cf.makeLabel(replicate,data)+".fastq";
				String trimmomatic_Cmd = "";
				if(ds.getFullOption()){
					trimmomatic_Cmd = "java -jar "+ds.getPath().replace(" ", "\\ ")+"Tools/Trimmomatic/trimmomatic.jar SE -phred"+encoding+" -threads "+ds.getAnalysisCPU()+" " + fq + " " +trimFr+" "+fullOption;						
				}else{
					trimmomatic_Cmd = "java -jar "+ds.getPath().replace(" ", "\\ ")+"Tools/Trimmomatic/trimmomatic.jar SE -phred"+encoding+" -threads "+ds.getAnalysisCPU()+" " + fq + " " +trimFr+" LEADING:3 TRAILING:3 SLIDINGWINDOW:4:15 MINLEN:20";	
				}			
				fw.write(trimmomatic_Cmd+"\n");
				
				//Log
				ds.writeLogCmd(trimmomatic_Cmd+"\n\n");
			}else{
				//Paired-end
				String fr = "";
				String rv = "";
				if(data.equals("Public")){
					fr = spacePath+"00_Fastq/"+ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+"_1.fastq";
					rv = spacePath+"00_Fastq/"+ds.getRunGSM()+"_"+ds.getGSMInfo()[0]+"_2.fastq";
						
				}else{
					fr = ds.getForwardFastq().replace(" ", "\\ ").replace("\\ \\ ", " ");
					rv = ds.getReverseFastq().replace(" ", "\\ ").replace("\\ \\ ", " ");
				}
				
				if(ds.getEncodingFr().equals("sanger")){
					encoding = "33";
				}else{
					encoding = "64";
				}
				
				trimFr = spacePath+"00_Fastq/Trim_"+cf.makeLabel(replicate,data)+"_1.fastq";
				trimRv = spacePath+"00_Fastq/Trim_"+cf.makeLabel(replicate,data)+"_2.fastq";
				String unpairFr = spacePath + "00_Fastq/UnPair_"+ds.getGSMInfo()[0]+"_1.fastq";
				String unpairRv = spacePath + "00_Fastq/UnPair_"+ds.getGSMInfo()[0]+"_2.fastq";
				
				String trimmomatic_Cmd = "";
				if(ds.getFullOption()){
					trimmomatic_Cmd = "java -jar "+ds.getPath().replace(" ", "\\ ")+"Tools/Trimmomatic/trimmomatic.jar PE -phred"+encoding+" -threads "+ds.getAnalysisCPU()+" " + fr +" "+ rv +" "+ trimFr +" "+unpairFr+" "+trimRv+" "+unpairRv+" "+fullOption;
				}else{
					trimmomatic_Cmd = "java -jar "+ds.getPath().replace(" ", "\\ ")+"Tools/Trimmomatic/trimmomatic.jar PE -phred"+encoding+" -threads "+ds.getAnalysisCPU()+" " + fr +" "+ rv +" "+ trimFr +" "+unpairFr+" "+trimRv+" "+unpairRv+" LEADING:3 TRAILING:3 SLIDINGWINDOW:4:15 MINLEN:36";
				}
				fw.write(trimmomatic_Cmd+"\n");
				
				//Log
				ds.writeLogCmd(trimmomatic_Cmd+"\n\n");
			}
			
			fw.flush();
			fw.close();
			
			String cmdTrim1[] = {"chmod","777",ds.getPath()+ "/Script/Trimming.sh"};
			String cmdTrim2[] = {"sh",ds.getPath() + "/Script/Trimming.sh"};

			cf.shellCmd(cmdTrim1);
			cf.shellCmd(cmdTrim2);
			
			if(!trimRv.equals("")){
				new File(spacePath + "00_Fastq/UnPair_"+ds.getGSMInfo()[0]+"_1.fastq").delete();
				new File(spacePath + "00_Fastq/UnPair_"+ds.getGSMInfo()[0]+"_2.fastq").delete();				
			}
			
			FileUtils.deleteDirectory(new File(ds.getAnalysisPath()	+ "00_Fastq/tmp/"));
			
			
		}catch (Exception e) {
			System.out.println("\nError : Unable to make the Trimming.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the Trimming.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
				
		//Remove File
		if(ds.getRemove(1) == true){
			cf.removeFile("Fastq");	
		}else{
			if(ds.getCompressGz() == true){
				ds.getMainUI().setProgress(55, "Compress : " + ds.getGSMInfo()[0]);
				cf.compressGz("Fastq", data);
			}			
		}
		
		// Update Trim_.Fastq
		ds.setFastq(trimFr, trimRv);
		
		File fr = new File(trimFr);
		if(!fr.exists()){
			ds.getErrLog().setStepErr(3, true);
			System.out.println("\n Error: Trimmomatic Didn't work. (Err006-4)");
		}
		
		ds.writeLogRun("Trimming : Trimmomatic (End)\n", true);
		
	}
}