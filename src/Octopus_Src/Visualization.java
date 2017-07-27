package Octopus_Src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.BatchUpdateException;
import java.util.ArrayList;

public class Visualization {
	private DataSet ds;
	private CommonFunc cf;
	
	public Visualization(DataSet ds,CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		
		if(ds.getGSMInfo()[2].equals("RNA-SEQ")){
			String dir[] = {"/Result/" +  ds.getGSE()+"/03_RNA_RPKM_Count","/Result/" +  ds.getGSE()+"/03_RNA_RPKM_Count/RPKM","/Result/" +  ds.getGSE()+"/03_RNA_RPKM_Count/Count"};
			cf.makeDirectory(dir);
		}
		String dir[] = {"/Result/" +  ds.getGSE()+"/03_Tag/","/Result/" +  ds.getGSE()+"/04_BigWig/"};
		cf.makeDirectory(dir);
	}
	
	public void use_Homer(String dataType){
		
		System.out.print("Homer -> ");
		ds.writeLogRun("Visualization : Homer (Start)\n", true);
		
		ds.writeLogCmd("# Make the visualization file using the Homer.\n");
		ds.getMainUI().setProgress(90, "Visualization : "+ds.getGSMInfo()[0]);
			
		String path = ds.getPath();
		String tagDir = ds.getLabelTitle();
		String genome = ds.getGSMInfo()[1];
		String tagPath = ds.getAnalysisPath().replace(" ", "\\ ") + "03_Tag/"+tagDir+"/";
		String spacePath = path.replace(" ", "\\ ");
		String spacePath2 = ds.getAnalysisPath().replace(" ", "\\ ");
		String makeUCSCOption = "";
		String strand = "";
		
		if(dataType.equals("Public")){
			if(!ds.getPublicStrand().equals("Unstrand")){
				strand = "-sspe -flip";
			}
		} else { // Private
			if (!ds.getGSMInfo()[5].equals("Unstrand")) {
				strand = "-sspe -flip";
			}
		}

		try {
			FileWriter fw = new FileWriter(ds.getPath()+"/Script/Make_BigWig.sh");
			
			// Check Exist Genome.chrom.sizes
			
			if(!cf.checkReference("chrom.sizes",ds.getGSMInfo()[1])){
				if(ds.getGSMInfo()[1].equals("tair10")){
					fw.write("wget http://dkucombio.ipdisk.co.kr:80/publist/VOL1/Public/Octopus-Sub/Tair10/tair10.chrom.sizes -O "+spacePath+"Index/Reference/"+genome+".chrom.sizes -o "+spacePath+"Script/Downlog\n");
					ds.writeLogCmd("wget http://dkucombio.ipdisk.co.kr:80/publist/VOL1/Public/Octopus-Sub/Tair10/tair10.chrom.sizes -O "+spacePath+"Index/Reference/"+genome+".chrom.sizes -o "+spacePath+"Script/Downlog\n\n");

				}else{
					fw.write("wget http://hgdownload.soe.ucsc.edu/goldenPath/"+genome+"/bigZips/"+genome+".chrom.sizes -O "+spacePath+"Index/Reference/"+genome+".chrom.sizes -o "+spacePath+"Script/Downlog\n");
					ds.writeLogCmd("wget http://hgdownload.soe.ucsc.edu/goldenPath/"+genome+"/bigZips/"+genome+".chrom.sizes -O "+spacePath+"Index/Reference/"+genome+".chrom.sizes -o "+spacePath+"Script/Downlog\n\n");
				}
			}
						
			fw.write("export Octopus_Homer="+spacePath+"Tools/Homer/bin/\n");
			fw.write("export Octopus_SubTool="+spacePath+"Tools/SubTool/\n");
			fw.write("export Octopus_samtools="+spacePath+"Tools/Samtools/\n");
			fw.write("export Octopus_bwtool="+spacePath+"Tools/Bwtools/\n");
			fw.write("export PATH=$PATH:\"$Octopus_Homer\":\"$Octopus_SubTool\":\"$Octopus_samtools\":\"$Octopus_bwtool\"\n");
			fw.flush();
			
			
			String fullOptionTagDirectory = ds.getToolOPtion(6);
			String fullOptionUCSCfile = ds.getToolOPtion(7);
			
			//makeTagDirectory
			if(ds.getFullOption()){
				fw.write(spacePath+"Tools/Homer/bin/makeTagDirectory "+ tagPath +" "+ds.getBamFile().replace(" ","\\ ") +" "+strand+" "+fullOptionTagDirectory+"\n");
			}else{
				fw.write(spacePath+"Tools/Homer/bin/makeTagDirectory "+ tagPath +" "+ds.getBamFile().replace(" ","\\ ") +" "+strand+"\n");	
			}
			
			//removeOutOfBoundsReads.pl
			fw.write(spacePath+"Tools/Homer/bin/removeOutOfBoundsReads.pl "+ tagPath +" "+genome + " -chromSizes "+spacePath+"/Index/Reference/"+genome+".chrom.sizes\n");
			
			// makeUCSCFile
			String ucscFile = "";
			if(ds.getFullOption()){
				if(ds.getGSMInfo()[2].equals("RNA-SEQ")){
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" "+fullOptionUCSCfile+" -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -style rnaseq -strand both -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig"; 
				}else if(ds.getGSMInfo()[2].equals("MEDIP-SEQ")){
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" "+fullOptionUCSCfile+" -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -style methylated -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig";
				}else if(ds.getGSMInfo()[2].equals("DNASE-SEQ")){
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" "+fullOptionUCSCfile+" -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -style dnase -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig";
				}else{
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" "+fullOptionUCSCfile+" -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig";
				}
			}else{
				if(ds.getGSMInfo()[2].equals("RNA-SEQ")){
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" -fsize 1e10 -fragLength given -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -style rnaseq -strand both -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig"; 
				}else if(ds.getGSMInfo()[2].equals("MEDIP-SEQ")){
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" -fsize 1e10 -fragLength 150 -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -style methylated -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig";
				}else if(ds.getGSMInfo()[2].equals("DNASE-SEQ")){
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" -fsize 1e10 -fragLength 150 -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -style dnase -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig";
				}else{
					ucscFile = spacePath+"Tools/Homer/bin/makeUCSCfile "+ tagPath +" -fsize 1e10 -fragLength 150 -bigWig "+spacePath+"/Index/Reference/"+genome+".chrom.sizes -o "+spacePath2+"/04_BigWig/"+tagDir+".bigWig";
				}	
			}
			
			fw.write(ucscFile+"\n");
			fw.flush();
			
			//Log
			ds.writeLogCmd("export Octopus_Homer="+spacePath+"Tools/Homer/bin/\n");
			ds.writeLogCmd("export Octopus_SubTool="+spacePath+"Tools/SubTool/\n");
			ds.writeLogCmd("export Octopus_samtools="+spacePath+"Tools/Samtools/\n");
			ds.writeLogCmd("export Octopus_bwtool="+spacePath+"Tools/Bwtools/\n");
			ds.writeLogCmd("export PATH=$PATH:\"$Octopus_Homer\":\"$Octopus_SubTool\":\"$Octopus_samtools\":\"$Octopus_bwtool\"\n\n");
			ds.writeLogCmd(spacePath+"Tools/Homer/bin/makeTagDirectory "+ tagPath +" "+ds.getBamFile().replace(" ","\\ ") +"\n");
			ds.writeLogCmd(spacePath+"Tools/Homer/bin/removeOutOfBoundsReads.pl "+ tagPath +" "+genome + " -chromSizes "+spacePath+"/Index/Reference/"+genome+".chrom.sizes\n");
			ds.writeLogCmd(ucscFile+"\n");			
			
			
			if(ds.getGSMInfo()[2].equals("RNA-SEQ")){
				// Install Genome for Homer
				if(!cf.checkHomerGenome(genome)){
					cf.installGenome(genome);
				}
				fw.write(spacePath+"/Tools/Homer/bin/analyzeRepeats.pl rna "+genome+" -strand both -count exons -d "+tagPath+" -rpkm > "+spacePath2+"03_RNA_RPKM_Count/RPKM/"+tagDir+".txt\n");
				fw.write(spacePath+"/Tools/Homer/bin/analyzeRepeats.pl rna "+genome+" -strand both -count exons -d "+tagPath+" -noadj > "+spacePath2+"/03_RNA_RPKM_Count/Count/"+tagDir+".txt\n");
				fw.flush();
				
				//Log
				ds.writeLogCmd(spacePath+"/Tools/Homer/bin/analyzeRepeats.pl rna "+genome+" -strand both -count exons -d "+tagPath+" -rpkm > "+spacePath2+"03_RNA_RPKM_Count/RPKM/"+tagDir+".txt\n");
				ds.writeLogCmd(spacePath+"/Tools/Homer/bin/analyzeRepeats.pl rna "+genome+" -strand both -count exons -d "+tagPath+" -noadj > "+spacePath2+"/03_RNA_RPKM_Count/Count/"+tagDir+".txt\n\n");
			}
			
			fw.close();
			
			String cmdMakeBigWig1[] = {"chmod","777",path + "/Script/Make_BigWig.sh"};
			String cmdMakeBigWig2[] = {"sh",path + "/Script/Make_BigWig.sh"};
			cf.shellCmd(cmdMakeBigWig1);
			cf.shellCmd(cmdMakeBigWig2);
				
			//Remove File
			if(ds.getOmitStep("Sort")){
				if(ds.getRemove(4) == true){
					cf.removeFile("Bam");
				}	
			}else{
				if(ds.getRemove(5) == true){
					cf.removeFile("Sort");
				}
			}
			
			ds.writeLogRun("Visualization : Homer (End)\n", true);
			
		} catch (IOException e) {
			System.out.println("\nError : Unable to make the Make_BigWig.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the Make_BigWig.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
				
		// Merge RPKM/Count
		if(ds.getGSMInfo()[2].equals("RNA-SEQ")){
			calcTotalRPKM("RPKM",tagDir);
			calcTotalRPKM("Count",tagDir);
		}

		System.out.println("Completed.");
		ds.getMainUI().setRunningPrograss(false);
		ds.getMainUI().setProgress(100,"Complete : "+ds.getGSMInfo()[0]);
		
	}
	
	public void calcTotalRPKM(String type, String tagName){
		
		String spacePath = ds.getAnalysisPath().replace(" ", "\\ ");
		String dataPath = spacePath+"03_RNA_RPKM_Count/"+type+"/";
		
		String genome[] = tagName.split("\\.");

		File total = new File(dataPath + "Total_" + genome[genome.length - 2] + ".txt");
		
		String sortedcmd[] = {"sort",dataPath+tagName+".txt","-o",dataPath+"tmp.txt"};

		cf.shellCmd(sortedcmd);
		String line;
		
		try {
			FileReader fr = new FileReader(dataPath + "tmp.txt");
			BufferedReader br = new BufferedReader(fr);

			if (total.exists()) {
				// exist.
				ArrayList<String> data = new ArrayList<>();
				FileReader fr2 = new FileReader(total);
				BufferedReader br2 = new BufferedReader(fr2);
				
				// save Total
				while((line = br2.readLine())!= null){
					data.add(line);
				}
				fr2.close();
				br2.close();
				
				int idx = 0;
				FileWriter fw = new FileWriter(total);
				// Merge tmp.txt
				while((line = br.readLine())!= null){
					String tmp[] = line.split("\t");
					
					if(idx == 0){
						fw.write(data.get(idx)+"\t"+tagName+"\n");
						idx++;
						if (!line.substring(0, 10).equals("Transcript")) {
							fw.write(data.get(idx)+"\t"+tmp[tmp.length-1]+"\n");
							fw.flush();
							idx++;
						}else{
							idx++;
						}
					}else{
						if (!line.substring(0, 10).equals("Transcript")) {
							fw.write(data.get(idx)+"\t"+tmp[tmp.length-1]+"\n");
							fw.flush();
							idx++;
						}
					}
				}
				
				fw.close();
			} else {
				// not exist.

				FileWriter fw = new FileWriter(total);
				String header = "Transcript_ID\tchr\tstart\tend\tstrand\tLength\tCopies\tAnnotation/Divergence\t";
				fw.write(header + tagName + "\n");

				while ((line = br.readLine()) != null) {
					if (!line.substring(0, 10).equals("Transcript")) {
						fw.write(line + "\n");
						fw.flush();
					}
				}
				fw.close();

			}
			br.close();
			fr.close();
		} catch (IOException e) {
			System.out.println("\nError : Unable to make the RPKM/Count tmp file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the RPKM/Count tmp file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
		
		new File(dataPath+"tmp.txt").delete();
	}
	
	
	
}
