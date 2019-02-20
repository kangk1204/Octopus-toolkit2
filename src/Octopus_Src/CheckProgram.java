package Octopus_Src;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Dialog.ModalExclusionType;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Octopus_UI.UI_Octopus;
import Octopus_UI.UI_ProgressBar;

public class CheckProgram {
	private DataSet ds;
	private CommonFunc cf;
	private ArrayList<String> toolList;
	private UI_Octopus ui;
	private UI_ProgressBar prBar;
	
	public CheckProgram(DataSet ds, CommonFunc cf) {
		this.ds = ds;
		this.cf = cf;
		ds.setCheckProgram(this);
		ui = new UI_Octopus(ds,cf,this);
		ds.setMainUI(ui);
		
		prBar = new UI_ProgressBar(cf);
		
		toolList = new ArrayList<String>();
		String dir[] = { "Tools", "Script", "Index", "Result", "Log", "Tools/SubTool", "Index/Reference","Index/Hisat2","Index/STAR", "Log/Command", "Log/Run"};

		// make Directory
		cf.makeDirectory(dir);
		
		// make Log file.
		ds.setLog_Cmd();
				
		// search tool.
		ds.writeLogRun("Checking required programs for analysis.\n",true);
		toolSearch();

		if (toolList.size() != 0) {
			System.out.println("\nThe following tools will be installed:\n");
			ds.writeLogRun("The following tools will be installed:\n",true);
			for (int j = 0; j < toolList.size(); j++) {
				ds.writeLogRun(toolList.get(j)+"\n",false);
				System.out.println(toolList.get(j));
			}
			// Download, Installation
			ds.writeLogRun("Installing programs for analysis.\n",true);

			installTool(true);

		}else{
			prBar.setVisible(false);
			//JOptionPane.showMessageDialog(null, "Octopus-toolkit is ready.", "Check Program",JOptionPane.INFORMATION_MESSAGE);
			ui.setVisible(true);
			ds.writeLogRun("Octopus-toolkit is ready.\n",true);
		}
	}
	
	public boolean toolSearch() {
		toolList.clear();
		boolean checkTool = false;
		
		String[] tool_list = { "makeTagDirectory", "makeUCSCfile", "findPeaks",
				"pos2bed.pl", "annotatePeaks.pl", "analyzeRepeats.pl", "removeOutOfBoundsReads.pl",
				"fastq-dump", "fastqc", "twoBitToFa", "bedGraphToBigWig", "makeGraph.R","Extract_Chr_Sequence.py",
				"igv.jar", "samtools", "bwtool","trimmomatic.jar","hisat2-build-l","hisat2-align-l","STAR", "ascp" };

		ArrayList<String> tool_check = new ArrayList<String>();
		int cnt = 1;
		
		for (int i = 0; i < tool_list.length; i++) {
			String find_cmd[] = {"find",ds.getPath() + "/Tools/","-name",tool_list[i]};
						
			if (cf.shellCmd(find_cmd).equals("")) {
				if (i < 7) {
					tool_check.add("Homer");
				} else if (i == 7) {
					tool_check.add("Sra-toolkit");
				} else if (i == 8) {
					tool_check.add("Fastqc");
				}  else if (i >= 9 && i <= 12) {
					tool_check.add("SubTool");
				} else if (i == 13) {
					tool_check.add("IGV");
				} else if (i == 14) {
					tool_check.add("samtools");
				} else if (i == 15) {
					tool_check.add("bwtool");
				} else if (i == 16){
					tool_check.add("Trimmomatic");
				} else if (i >= 17 && i <= 18){
					tool_check.add("Hisat2");
				} else if (i == 19){
					tool_check.add("STAR");
				} else if (i == 20){
					tool_check.add("Aspera");
				}
			}
		}

		HashSet<String> tool_checkSet = new HashSet<String>(tool_check);
		toolList = new ArrayList<String>(tool_checkSet);

		int property = 0;
		for (int i = 0; i < toolList.size(); i++) {
			if (tool_check.size() > 1) {
				if (toolList.get(i).equals("bwtool")) {
					String tmp = toolList.get(property);
					toolList.set(property++, "bwtool");
					toolList.set(i, tmp);
				}
			}
		}
		
		if(toolList.size() != 0){
			checkTool = true;
		}
		
		return checkTool;

	}

	public void installTool(boolean btnFlag) {
		
		if(btnFlag == true){
			
			prBar.setVisible(true);
			if(ds.getOS().equals("Fedora") || ds.getOS().equals("CentOS")){
				prBar.setSize(300,95);	
			}else if(ds.getOS().equals("Mint")){
				prBar.setSize(300,63);
			}
			else{
				prBar.setSize(300,60);
			}
			
			Thread autoInstall = new Thread() {
				public void run() {
					for (int i = 0; i < toolList.size(); i++) {
						int value = i + ((int) (100 / toolList.size()) * i);
						prBar.setProgress(value - 1, toolList.get(i));
						System.out.println("Install : " + toolList.get(i));
						downLoadTool(toolList.get(i), ds.getPath());

					}
					reCheckAndRun();
				}
			};
			autoInstall.start();
		}else{
			ds.closeLog();
			System.exit(0);
		}
	}


	public void downLoadTool(String tool, String path) {
		String fileName = path + "/Script/Download_Program.sh";
		String spacePath = path.replace(" ", "\\ ");

		File file = new File(fileName);
		if (tool.equals("Homer")) {
			String homer_cmd[] = {"wget","143.248.14.23/Octopus-Sub/Homer.zip","-O",path + "/Tools/Homer.zip","-o",path	+ "/Script/Downlog"};
			String homer_cmd2[] = {"unzip","-o",path + "/Tools/Homer.zip","-d",path + "/Tools/"};
			String homer_cmd3[] = {"perl",path + "/Tools/Homer/configureHomer.pl","-make"};
			cf.shellCmd(homer_cmd);
			cf.shellCmd(homer_cmd2);
			cf.shellCmd(homer_cmd3);
			new File(path+"Tools/Homer.zip").delete();			
		}  else if (tool.equals("Sra-toolkit")) {
			String sra_cmd[] = {"wget","143.248.14.23/Octopus-Sub/sratoolkit.zip","-O",path + "/Tools/sratoolkit.zip","-o",path	+ "/Script/Downlog"};
			String sra_cmd2[] = {"unzip","-o",path + "/Tools/sratoolkit.zip","-d",path + "/Tools/"};
			cf.shellCmd(sra_cmd);
			cf.shellCmd(sra_cmd2);
			new File(path+"Tools/sratoolkit.zip").delete();
		} else if (tool.equals("Trimmomatic")) {
			String trim_cmd[] = {"wget","143.248.14.23/Octopus-Sub/Trimmomatic.zip","-O",path + "/Tools/Trimmomatic.zip","-o",path	+ "/Script/Downlog"};
			String trim_cmd2[] = {"unzip","-o",path + "/Tools/Trimmomatic.zip","-d",path + "/Tools/"};
			cf.shellCmd(trim_cmd);
			cf.shellCmd(trim_cmd2);
			new File(path+"Tools/Trimmomatic.zip").delete();
		}else if (tool.equals("Hisat2")) {
			String hisat_cmd[] = {"wget","143.248.14.23/Octopus-Sub/hisat2.zip","-O",path + "/Tools/hisat2.zip","-o",path	+ "/Script/Downlog"};
			String hisat_cmd2[] = {"unzip","-o",path + "/Tools/hisat2.zip","-d",path + "/Tools/"};
			cf.shellCmd(hisat_cmd);
			cf.shellCmd(hisat_cmd2);
			new File(path+"Tools/hisat2.zip").delete();
		}else if (tool.equals("STAR")) {
			String star_cmd[] = {"wget","143.248.14.23/Octopus-Sub/STAR.zip","-O",path + "/Tools/STAR.zip","-o",path	+ "/Script/Downlog"};
			String star_cmd2[] = {"unzip","-o",path + "/Tools/STAR.zip","-d",path + "/Tools/"};
			cf.shellCmd(star_cmd);
			cf.shellCmd(star_cmd2);
			new File(path+"Tools/STAR.zip").delete();
		}
		else if (tool.equals("Aspera")) {
			String aspera_cmd[] = {"wget","143.248.14.23/Octopus-Sub/aspera.zip","-O",path + "/Tools/aspera.zip","-o",path	+ "/Script/Downlog"};
			String aspera_cmd2[] = {"unzip","-o",path + "/Tools/aspera.zip","-d",path + "/Tools/"};
			cf.shellCmd(aspera_cmd);
			cf.shellCmd(aspera_cmd2);
			new File(path+"Tools/aspera.zip").delete();
		} else if (tool.equals("Fastqc")) {
			String fastqc_cmd[] = {"wget","143.248.14.23/Octopus-Sub/FastQC.zip","-O",path + "/Tools/FastQC.zip","-o",path	+ "/Script/Downlog"};
			String fastqc_cmd2[] = {"unzip","-o",path + "/Tools/FastQC.zip","-d",path + "/Tools/"};
			String fastqc_cmd3[] = {"chmod","777",path + "/Tools/FastQC/fastqc"};
			cf.shellCmd(fastqc_cmd);
			cf.shellCmd(fastqc_cmd2);
			cf.shellCmd(fastqc_cmd3);
			new File(path+"Tools/FastQC.zip").delete();
		} else if (tool.equals("IGV")) {
			try {
				FileWriter fw = new FileWriter(file);
				fw.write("wget 143.248.14.23/Octopus-Sub/IGV.zip -O " + spacePath + "/Tools/IGV.zip -o " + spacePath + "/Script/Downlog\n");
				fw.write("unzip -o " + spacePath + "/Tools/IGV.zip -d " + spacePath + "/Tools/\n");
				fw.write("rm " + spacePath + "/Tools/IGV.zip\n");

				fw.close();
				String cmdInstall1[] = {"chmod","777",path + "/Script/Download_Program.sh"};
				String cmdInstall2[] = {"sh",path + "/Script/Download_Program.sh"};
				cf.shellCmd(cmdInstall1);
				cf.shellCmd(cmdInstall2);

			} catch (Exception e) {
				System.out.println("\nError : Unable to make the Download_Program.sh(IGV) file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to make the Download_Program.sh(IGV) file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);				
			}
		} else if(tool.equals("R")){
			
			try {
				FileWriter fw = new FileWriter(file);
				
				fw.write("wget 143.248.14.23/Octopus-Sub/RPackage.zip -O " + spacePath + "/Tools/RPackage.zip -o " + spacePath + "/Script/Downlog\n");
				fw.write("unzip " + spacePath + "/Tools/RPackage.zip -d " + spacePath + "/Tools/\n");

				String rPackage[] = { "colorspace_1.3-2", "dichromat_2.0-0", "Rcpp_0.12.18", "plyr_1.8.4",
						"munsell_0.5.0", "labeling_0.3", "RColorBrewer_1.1-2", "scales_0.4.1", "gtable_0.2.0",
						"pheatmap_1.0.10", "stringi_1.2.4", "magrittr_1.5", "stringr_1.2.0", "digest_0.6.16",
						"reshape2_1.4.3", "lazyeval_0.2.1","assertthat_0.2.0","tibble_1.3.0","ggplot2_2.2.1" };
				
				String dir[] = {"Tools/Rlib"};
				cf.makeDirectory(dir);
				
				String packageCmd = "";
				
				for (int i = 0; i < rPackage.length; i++) {
					packageCmd = "R CMD INSTALL "+spacePath + "/Tools/" + rPackage[i] + ".tar.gz --library="+spacePath+"/Tools/Rlib\n";
					fw.write(packageCmd + "\n");
					fw.flush();
				}


				for (int i = 0; i < rPackage.length; i++) {
					fw.write("rm " + spacePath + "/Tools/" + rPackage[i] + ".tar.gz\n");
					fw.flush();
				}
				
				fw.write("rm " + spacePath + "/Tools/RPackage.zip\n");
				fw.close();
				
				String cmdInstall1[] = {"chmod", "777",path + "/Script/Download_Program.sh"};
				String cmdInstall2[] = {"sh",path + "/Script/Download_Program.sh"};

				cf.shellCmd(cmdInstall1);
				cf.shellCmd(cmdInstall2);
				
				// Re- Check R Package
				if (checkRPackage() == true) {
					// open dialog
					ds.getMainUI().setRunningPrograss(false);
					ds.getMainUI().setProgress(100, "Completed : R Packages");
					
					cf.openDialog_Dir("Graph");
					if (ds.getBigWigInfo().size() != 0) {
						ds.getMainUI().getUI_Graph().setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "No such file or directory. (BigWig)",
								"File Open", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					ds.getMainUI().setRunningPrograss(false);
					ds.getMainUI().setProgress(100, "Error : R Packages");
					JOptionPane.showMessageDialog(null, "Some R packages are not installed. (Err007-2)\nPlease install the packages and rerun the program", "Check R Package",
							JOptionPane.INFORMATION_MESSAGE);
				}
				
				
			} catch (Exception e) {
				System.out.println("\nError : Unable to make the Download_Program.sh(R) file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to make the Download_Program.sh(R) file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
			}
			
		}
		else if (tool.equals("bwtool")) {
			try {
				FileWriter fw = new FileWriter(file);
				fw.write("wget 143.248.14.23/Octopus-Sub/Bwtool.zip -O " + spacePath + "/Tools/Bwtool.zip -o " + spacePath + "/Script/Downlog\n");
				fw.write("unzip -o " + spacePath + "/Tools/Bwtool.zip -d " + spacePath + "/Tools/\n");
				fw.write("wget 143.248.14.23/Octopus-Sub/libbeato.zip -O " + spacePath + "/Tools/libbeato.zip -o " + spacePath + "/Script/Downlog\n");
				fw.write("unzip -o " + spacePath + "/Tools/libbeato.zip -d " + spacePath + "/Tools/\n");
				
				fw.write("cd " + spacePath + "/Tools/libbeato/\n");
				fw.write("./configure --prefix="+spacePath + "/Tools/libbeato CFLAGS=\"-g -O0 -I"+spacePath + "/Tools/libbeato/include\" LDFLAGS=-L"+spacePath + "/Tools/libbeato/lib\n");
				fw.write("make\n");
				fw.write("make install\n");
				fw.write("cd " + spacePath + "/Tools/Bwtool/\n");
				fw.write("./configure --prefix="+spacePath + "/Tools/libbeato CFLAGS=\"-g -O0 -I"+spacePath + "/Tools/libbeato/include\" LDFLAGS=-L"+spacePath + "/Tools/libbeato/lib\n");
				fw.write("make\n");
				fw.write("make install\n");
				
				fw.write("rm " + spacePath + "/Tools/Bwtool.zip\n");
				fw.write("rm " + spacePath + "/Tools/libbeato.zip*\n");
				fw.close();
				String cmdInstall1[] = {"chmod","777",path + "/Script/Download_Program.sh"};
				String cmdInstall2[] = {"sh",path + "/Script/Download_Program.sh"};

				cf.shellCmd(cmdInstall1);
				cf.shellCmd(cmdInstall2);

			} catch (Exception e) {
				System.out.println("\nError : Unable to make the Download_Program.sh(bwtool) file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to make the Download_Program.sh(bwtool) file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);

			}
		} else if (tool.equals("samtools")) {
			try {
				FileWriter fw = new FileWriter(file);
				fw.write("wget 143.248.14.23/Octopus-Sub/Samtools.zip -O " + spacePath + "/Tools/Samtools.zip -o " + spacePath + "/Script/Downlog\n");
				fw.write("unzip -o " + spacePath + "/Tools/Samtools.zip -d " + spacePath + "/Tools/\n");
				
				fw.write("cd " + spacePath + "/Tools/Samtools/\n");
				fw.write("./configure\n");
				fw.write("make\n");
				fw.write("make install DESTDIR="+spacePath+"/Tools/Samtools/\n");
				fw.write("rm " + spacePath + "/Tools/Samtools.zip*\n");
	
				fw.close();
				String cmdInstall1[] = {"chmod","777",path + "/Script/Download_Program.sh"};
				String cmdInstall2[] = {"sh",path + "/Script/Download_Program.sh"};

				cf.shellCmd(cmdInstall1);
				cf.shellCmd(cmdInstall2);

			} catch (Exception e) {
				System.out.println("\nError : Unable to make the Download_Program.sh(samtools) file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to make the Download_Program.sh(samtools) file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);

			}
		}

		else if (tool.equals("SubTool")) {
			try {
				FileWriter fw = new FileWriter(file);
				fw.write("wget 143.248.14.23/Octopus-Sub/twoBitToFa -O " + spacePath + "/Tools/SubTool/twoBitToFa\n");
				fw.write("wget 143.248.14.23/Octopus-Sub/bedGraphToBigWig -O " + spacePath + "/Tools/SubTool/bedGraphToBigWig\n");
				fw.write("wget 143.248.14.23/Octopus-Sub/Extract_Chr_Sequence.py -O " + spacePath + "/Tools/SubTool/Extract_Chr_Sequence.py\n");
				fw.write("wget 143.248.14.23/Octopus-Sub/makeGraph.R -O " + spacePath + "/Tools/SubTool/makeGraph.R\n");
				fw.write("chmod 777 " + spacePath + "/Tools/SubTool/*\n");

				fw.flush();
				fw.close();

				String cmdInstall1[] = {"chmod","777",path + "/Script/Download_Program.sh"};
				String cmdInstall2[] = {"sh",path + "/Script/Download_Program.sh"};

				cf.shellCmd(cmdInstall1);
				cf.shellCmd(cmdInstall2);
				
			} catch (Exception e) {
				System.out.println("\nError : Unable to make the Download_Program.sh(SubTool) file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to make the Download_Program.sh(SubTool) file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
			}
		}
		new File(path + "/Script/Downlog").delete();
		
	}
	
	public boolean checkRPackage(){		
		// Check R Package

		String pack[] = { "colorspace", "dichromat", "Rcpp", "plyr", "munsell", "labeling", "RColorBrewer",
				"scales", "gtable", "pheatmap", "stringi", "magrittr", "stringr", "digest", "reshape2","lazyeval","assertthat","tibble",
				"ggplot2" };
		boolean checkPackFlag = true;
		
		for (int i = 0; i < pack.length; i++) {
			File f = new File(ds.getPath()+"/Tools/Rlib/"+pack[i]);
			
			if(!f.exists()){
				checkPackFlag = false;
				break;
			}
		}
		return checkPackFlag;
	}
	
	public void reCheckAndRun(){
		prBar.setVisible(false);
		
		if(toolSearch()){
			JOptionPane.showMessageDialog(null, "Some required programs are not installed. Please check the hdd space and network connection and run the program again. (Err007-2)", "Check Program", JOptionPane.INFORMATION_MESSAGE);
			ds.closeLog();
			System.exit(0);	
		}else{
			//JOptionPane.showMessageDialog(null, "Octopus-toolkit is ready.", "Check Program",JOptionPane.INFORMATION_MESSAGE);
			ui.setVisible(true);
			ds.writeLogRun("Octopus-toolkit is ready.\n",true);
		}
	}
}
