package Octopus_Src;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PeakCalling {
	private DataSet ds;
	private CommonFunc cf;
	private DefaultTableModel model;
	
	public PeakCalling(DataSet ds, CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		model = ds.getModel();
	}
	
	public void use_findPeak(){
		String path = ds.getPath();
		String spacePath = path.replace(" ", "\\ ");

		ds.getMainUI().setGseLabel("Peak Calling");
		
		ds.writeLogCmd("\n# Calling peaks using the Homer\n");
		ds.getMainProcess().cntTotal = model.getRowCount();

		int percent = 100/(model.getRowCount()+1);
		String sample = "";
		if(!cf.checkHomerGenome(ds.getTagInfo().get(0)[4])){
			ds.getMainUI().setProgress(0,"Peak Calling : Install_Genome");
			cf.installGenome(ds.getTagInfo().get(0)[4]);
		}
		// Re-Check
		if (cf.checkHomerGenome(ds.getTagInfo().get(0)[4])) {
			for(int i = 0; i < model.getRowCount(); i++){		
				ds.getMainProcess().cnt = (i+1);
				
				sample = String.valueOf(model.getValueAt(i, 0));
				String control = String.valueOf(model.getValueAt(i, 1));
				String style = String.valueOf(model.getValueAt(i, 2));
				
				int sampleIdx = Integer.parseInt(ds.getHmTagInfo(sample));
				int controlIdx = 0;
				
				String samplePath = "Result/"+ ds.getTagInfo().get(sampleIdx)[5]+"/";
			
				
				//Log
				System.out.println("Peak Calling : " + sample + "_Start");
				ds.getMainUI().setProgress(((i*percent)+percent),"Peak Calling : "+sample);
				ds.writeLogRun("Peak Calling : Homer (Start)\n", true);
				writeTableLog(i);
				
				if(!control.equals("")){
					controlIdx = Integer.parseInt(ds.getHmTagInfo(control));
				}
				
				String dir[] = {samplePath+"05_Analysis/",samplePath+"05_Analysis/Annotation",samplePath+"05_Analysis/Bed"};
				cf.makeDirectory(dir);
				samplePath = spacePath +"Result/"+ ds.getTagInfo().get(sampleIdx)[5]+"/";
				
				
				try{
					FileWriter fw = new FileWriter(path + "Script/PeakCalling.sh");
				
					fw.write("export Octopus_Homer="+spacePath+"/Tools/Homer/bin/\n");
					fw.write("export Octopus_SubTool="+spacePath+"/Tools/SubTool/\n");
					fw.write("export Octopus_samtools="+spacePath+"/Tools/Samtools/\n");
					fw.write("export Octopus_bwtool="+spacePath+"/Tools/Bwtools/\n");
					fw.write("export PATH=$PATH:\"$Octopus_Homer\":\"$Octopus_SubTool\":\"$Octopus_samtools\":\"$Octopus_bwtool\"\n");
					fw.flush();
					
					String tagPath = ds.getTagInfo().get(sampleIdx)[2].replace(" ", "\\ ");
					String controlPath = ds.getTagInfo().get(controlIdx)[2].replace(" ", "\\ ");
					String findpeak = "";
					String pos2bed = "";
					String annotate = "";
					String fullOptionFindPeak = ds.getToolOPtion(8);
					String fullOptionAnno = ds.getToolOPtion(9);
					
					
					if(control.equals("")){

						if(ds.getFullOption()){
							if(style.equals("Transcription Factor"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" "+fullOptionFindPeak+" -style factor -o auto";
							else if(style.equals("Histone"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" "+fullOptionFindPeak+" -style histone -o auto";
							else if(style.equals("Dnase"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" "+fullOptionFindPeak+" -style dnase -o auto";
							else
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" "+fullOptionFindPeak+" -style mC -o auto";
						}else{
							if(style.equals("Transcription Factor"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style factor -o auto";
							else if(style.equals("Histone"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style histone -o auto";	
							else if(style.equals("Dnase"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style dnase -o auto";	
							else
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style mC -o auto";	
						}	
					}else{
						if(ds.getFullOption()){
							if(style.equals("Transcription Factor"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" "+fullOptionFindPeak+" -style factor -o auto";
							else if(style.equals("Histone"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" "+fullOptionFindPeak+" -style histone -o auto";	
							else if(style.equals("Dnase"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" "+fullOptionFindPeak+" -style dnase -o auto";	
							else
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" "+fullOptionFindPeak+" -style mC -o auto";	
							
						}else{
							if(style.equals("Transcription Factor"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style factor -o auto";
							else if(style.equals("Histone"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style histone -o auto";	
							else if(style.equals("Dnase"))
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style dnase -o auto";	
							else
								findpeak = spacePath+"Tools/Homer/bin/findPeaks " + tagPath+" -i "+controlPath +" -F 2 -fdr 0.01 -P 0.01 -minDist 100 -style mC -o auto";	
						}
					}
					
					if(ds.getFullOption()){
						if(style.equals("Transcription Factor") || style.equals("Dnase") ){
							pos2bed = spacePath+"Tools/Homer/bin/pos2bed.pl "+ tagPath+"/peaks.txt -o "+samplePath + "05_Analysis/Bed/"+ds.getTagInfo().get(sampleIdx)[1]+".bed";
							annotate = spacePath+"Tools/Homer/bin/annotatePeaks.pl "+ tagPath+"/peaks.txt "+ ds.getTagInfo().get(sampleIdx)[4]+" "+fullOptionAnno+" > "+ samplePath + "05_Analysis/Annotation/"+ds.getTagInfo().get(sampleIdx)[1]+"_annotation.txt";
						}else{
							pos2bed = spacePath+"Tools/Homer/bin/pos2bed.pl "+ tagPath+"/regions.txt -o "+samplePath + "05_Analysis/Bed/"+ds.getTagInfo().get(sampleIdx)[1]+".bed";
							annotate = spacePath+"Tools/Homer/bin/annotatePeaks.pl "+ tagPath+"/regions.txt "+ ds.getTagInfo().get(sampleIdx)[4]+" "+fullOptionAnno+" > "+ samplePath + "05_Analysis/Annotation/"+ds.getTagInfo().get(sampleIdx)[1]+"_annotation.txt";
						}
					}else{
						if(style.equals("Transcription Factor") || style.equals("Dnase") ){
							pos2bed = spacePath+"Tools/Homer/bin/pos2bed.pl "+ tagPath+"/peaks.txt -o "+samplePath + "05_Analysis/Bed/"+ds.getTagInfo().get(sampleIdx)[1]+".bed";
							annotate = spacePath+"Tools/Homer/bin/annotatePeaks.pl "+ tagPath+"/peaks.txt "+ ds.getTagInfo().get(sampleIdx)[4]+" > "+ samplePath + "05_Analysis/Annotation/"+ds.getTagInfo().get(sampleIdx)[1]+"_annotation.txt";
						}else{
							pos2bed = spacePath+"Tools/Homer/bin/pos2bed.pl "+ tagPath+"/regions.txt -o "+samplePath + "05_Analysis/Bed/"+ds.getTagInfo().get(sampleIdx)[1]+".bed";
							annotate = spacePath+"Tools/Homer/bin/annotatePeaks.pl "+ tagPath+"/regions.txt "+ ds.getTagInfo().get(sampleIdx)[4]+" > "+ samplePath + "05_Analysis/Annotation/"+ds.getTagInfo().get(sampleIdx)[1]+"_annotation.txt";
						}	
					}
				
					fw.write(findpeak+"\n");
					fw.write(pos2bed+"\n");
					fw.write(annotate+"\n");
					
					fw.flush();
					fw.close();
					
					String cmdUsefindPeaks1[] = {"chmod","777",path + "Script/PeakCalling.sh"};
					String cmdUsefindPeaks2[] = {"sh",path+ "Script/PeakCalling.sh"};
					cf.shellCmd(cmdUsefindPeaks1);
					cf.shellCmd(cmdUsefindPeaks2);
					
					//Log 
					ds.writeLogCmd("export Octopus_Homer="+spacePath+"/Tools/Homer/bin/\n");
					ds.writeLogCmd("export Octopus_SubTool="+spacePath+"/Tools/SubTool/\n");
					ds.writeLogCmd("export Octopus_samtools="+spacePath+"/Tools/Samtools/\n");
					ds.writeLogCmd("export Octopus_bwtool="+spacePath+"/Tools/Bwtools/\n");
					ds.writeLogCmd("export PATH=$PATH:\"$Octopus_Homer\":\"$Octopus_SubTool\":\"$Octopus_samtools\":\"$Octopus_bwtool\"\n\n");
					ds.writeLogCmd(findpeak+"\n");
					ds.writeLogCmd(pos2bed+"\n");
					ds.writeLogCmd(annotate+"\n\n");
					
				}catch (Exception e) {
					// TODO: handle exception
					System.out.println("\nError : Unable to make the PeakCalling.sh file due to unknown reasons (Err009)");
					ds.writeLogRun("Error : Unable to make the PeakCalling.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
				}
				
				System.out.println("Peak Calling : "+ sample + "_End");
				ds.writeLogRun("Peak Calling : Homer (End)\n", true);
				
			}
			
			ds.getMainUI().setProgress(100,"Peak Calling : "+sample);
			JOptionPane.showMessageDialog(null, "Completed : Peak Calling.", "Peak Calling.", JOptionPane.INFORMATION_MESSAGE);
		}else{
			// Install Error : Homer Site Error
			System.out.println("Error : Can't access to Homer Site. (Err001)");
		}
		
		
	}
	
	public void writeTableLog(int idx){
		
		ds.writeLogCmd("\n# ============================================================\n");
		ds.writeLogCmd("# Sample : "+String.valueOf(model.getValueAt(idx, 0))+"\n");
		ds.writeLogCmd("# Control : "+String.valueOf(model.getValueAt(idx, 1))+"\n");
		ds.writeLogCmd("# Style : "+String.valueOf(model.getValueAt(idx, 2))+"\n");
		ds.writeLogCmd("# ============================================================\n\n");
		
	}
}
