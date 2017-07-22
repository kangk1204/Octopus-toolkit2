package Octopus_Src;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class HeatmapPlot {
	private DataSet ds;
	private CommonFunc cf;
	private DefaultTableModel model;
	
	public HeatmapPlot(DataSet ds, CommonFunc cf){
		this.ds = ds;
		this.cf = cf;
		model = ds.getModel();
	}
	
	public void makeGraph(){
		String path = ds.getPath();
		String spacePath = path.replace(" ", "\\ ");
		String bedPath;
		String bedName;
		String genome = model.getValueAt(0, 2).toString();
		String bigWigCmd = "";
		String labelName = "";
		String resultPath ="";
		
		ds.getMainProcess().cnt = 1;
		ds.getMainProcess().cntTotal = 1;
		
		ds.writeLogRun("Make the graph(Heatmap, Lineplot) using R\n", true);
		ds.writeLogCmd("# Make the graph(Heatmap, Lineplot) using R \n");
		ds.getMainUI().setProgress(50,"Graph : Heatmap & Lineplot ");
				
		if(ds.getBedIdx() == 0){
			// Select Promoter.bed
			bedPath = spacePath+"Index/Reference/"+genome+".bed";
			bedName = "Promoter_"+genome;
			if(!cf.checkReference("bed",genome)){ // exist:true, Not exist:false
				cf.downLoadReference("bed",genome);	
			}
		}else{
			// Select Sample.bed
			bedPath = ds.getBedPath().get(ds.getBedIdx()-1)[1].replace(" ","\\ ");
			bedName = ds.getBedPath().get(ds.getBedIdx()-1)[0];
			
			for(int i = 0; i < model.getRowCount(); i++){
				int modelIdx = Integer.parseInt(ds.getHmBigWigInfo(model.getValueAt(i, 0).toString()));
				if(bedName.equals(ds.getBigWigInfo().get(modelIdx)[1])){
					bigWigCmd = ds.getBigWigInfo().get(modelIdx)[2];
					labelName = ds.getBigWigInfo().get(modelIdx)[1].replaceAll(".bigWig", "");
				}
			}
		}
		
		System.out.println("Creating Heatmap and Line graph : "+bedName+"_Start");
		
		// mkdir Output Folder 
		String dir[] = {"Result/Graph","Result/Graph/"+bedName};
		cf.makeDirectory(dir);
		resultPath = spacePath+"Result/Graph/"+bedName+"/";
		
		try{
			FileWriter fw = new FileWriter(path + "Script/Make_Graph.sh");
			int cal_bin = 2*Integer.parseInt(ds.getTssRegion()) / Integer.parseInt(ds.getBinNum());
			 
			for(int i = 0; i < model.getRowCount(); i++){
				int modelIdx = Integer.parseInt(ds.getHmBigWigInfo(model.getValueAt(i, 0).toString()));
				if(!bedName.equals(ds.getBigWigInfo().get(modelIdx)[1])){
					int bigWigIdx = Integer.parseInt(ds.getHmBigWigInfo(model.getValueAt(i, 0).toString()));
					if(!bigWigCmd.equals("")){
						bigWigCmd = bigWigCmd + ",";
						labelName = labelName + " ";
					}
					bigWigCmd = bigWigCmd +ds.getBigWigInfo().get(bigWigIdx)[2];
					labelName = labelName + ds.getBigWigInfo().get(bigWigIdx)[1].replace(".bigWig", "");
				}
			}
			fw.write(spacePath + "Tools/Bwtool/bwtool matrix " + ds.getTssRegion() + ":" + ds.getTssRegion()
					+ " -tiled-averages=" + cal_bin + " " + bedPath + " " + bigWigCmd.replace(" ", "\\ ") + " " + resultPath
					+ "bwtool_output\n");
			
			
			fw.write("Rscript "+spacePath+"Tools/SubTool/makeGraph.R "+resultPath+" "+ds.getBinNum()+" "+ds.getTssRegion()+" "+cal_bin +" "+labelName+"\n");
			
			// Log
			writeTableLog(bedName,labelName,genome);
			
			ds.writeLogCmd(spacePath + "Tools/Bwtool/bwtool matrix " + ds.getTssRegion() + ":" + ds.getTssRegion()
					+ " -tiled-averages=" + cal_bin + " " + bedPath + " " + bigWigCmd.replace(" ", "\\ ") + " " + resultPath
					+ "bwtool_output\n");
			ds.writeLogCmd("Rscript "+spacePath+"Tools/SubTool/makeGraph.R "+resultPath+" "+ds.getBinNum()+" "+ds.getTssRegion()+" "+cal_bin +" "+labelName+"\n\n");
			
			fw.flush();
			fw.close();
						
			String cmdMakeGraph1[] = {"chmod","777",path+ "Script/Make_Graph.sh"};
			String cmdMakeGraph2[] = {"sh",path + "Script/Make_Graph.sh"};
			cf.shellCmd(cmdMakeGraph1);
			cf.shellCmd(cmdMakeGraph2);
							
			System.out.println("Creating Heatmap and Line graph : "+bedName+"_End");
			
		}catch(Exception e){
			System.out.println("\nError : Unable to make the Make_Graph.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the Make_Graph.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
		
		ds.writeLogRun("Graphs have been created.\n", true);
		ds.getMainUI().setProgress(100,"Graph : Graphs have been created.");
		JOptionPane.showMessageDialog(null, "Graphs have been created.", "Graph", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void writeTableLog(String bed,String label,String genome){
		
		ds.writeLogCmd("\n# ============================================================\n");
		ds.writeLogCmd("# Annotation(Bed) : "+bed+"\n");
		ds.writeLogCmd("# Sample : "+label.replace(" ", ", ")+"\n");
		ds.writeLogCmd("# Genome : "+genome+"\n");
		ds.writeLogCmd("# TSS region : +-"+ds.getTssRegion()+"bp\n");
		ds.writeLogCmd("# Number of BINs : "+ds.getBinNum()+"\n");
		ds.writeLogCmd("# ============================================================\n\n");
		
	}
}
