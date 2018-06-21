package Octopus_Src;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;

public class CommonFunc {
	private DataSet ds;
	private static int size = 1024;
	private static final String errMsg = "Warning attempting to launch web browser";
	private ArrayList<String[]> tmpBamInfo;

	public CommonFunc(DataSet ds) {
		this.ds = ds;
		ds.setPath(System.getProperty("user.dir") + "/Octopus-toolkit/");
		tmpBamInfo = new ArrayList<>();
	}

	public void getPC_info() {
		String path = ds.getPath();
		ds.setMemory(10);

		long freeSpace = new File(path).getUsableSpace();
		ds.setHddCapacity((int) (freeSpace / 1000 / 1000 / 1000));
	}

	public boolean checkRequiredTool() {

		// change the R detectiong pathway

		boolean requiredToolFlag = false;
		String cmd[] = { "which", "R" };
		String result_cmd = shellCmd(cmd);
				
		if (ds.getOS().equals("Fedora")) {
			if (result_cmd.contains("no R")) {
				JOptionPane.showMessageDialog(null,
						"Please install the R application with the following command:\n sudo yum install R",
						"Check R tool", JOptionPane.INFORMATION_MESSAGE);
				requiredToolFlag = false;
			} else {
				// R version >= 3.1
				String cmd2[] = { "R", "--version" };
				String rInfo[] = shellCmd(cmd2).split(" ");
				String rVersion[] = rInfo[2].split("\\.");

				if (Integer.parseInt(rVersion[0]) >= 3 && Integer.parseInt(rVersion[1]) >= 1) {
					requiredToolFlag = true;
				} else {
					JOptionPane.showMessageDialog(null, "Octopus-toolkit requires R version 3.1.0 or higher.", "Check R Version",
							JOptionPane.INFORMATION_MESSAGE);
					requiredToolFlag = false;
				}
			}
		} else if(ds.getOS().equals("CentOS")){
			if (result_cmd.contains("no R")) {
				JOptionPane.showMessageDialog(null,
						"Please install the R application with the following command:\n sudo yum install epel-release\nsudo yum install R",
						"Check R tool", JOptionPane.INFORMATION_MESSAGE);
				requiredToolFlag = false;
			} else {
				// R version >= 3.1
				String cmd2[] = { "R", "--version" };
				String rInfo[] = shellCmd(cmd2).split(" ");
				String rVersion[] = rInfo[2].split("\\.");

				if (Integer.parseInt(rVersion[0]) >= 3 && Integer.parseInt(rVersion[1]) >= 1) {
					requiredToolFlag = true;
				} else {
					JOptionPane.showMessageDialog(null, "Octopus-toolkit requires R version 3.1.0 or higher.", "Check R Version",
							JOptionPane.INFORMATION_MESSAGE);
					requiredToolFlag = false;
				}
			}
		}else {

			// Ubuntu , Mint
			if (result_cmd.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Please install the R application with the follwing command:\n sudo apt-get install r-base",
						"Check R tool", JOptionPane.INFORMATION_MESSAGE);
				requiredToolFlag = false;
			} else {
				// R version >= 3.1
				String cmd2[] = { "R", "--version" };
				String rInfo[] = shellCmd(cmd2).split(" ");
				String rVersion[] = rInfo[2].split("\\.");

				if (Integer.parseInt(rVersion[0]) >= 3 && Integer.parseInt(rVersion[1]) >= 1) {
					requiredToolFlag = true;
				} else {
					JOptionPane.showMessageDialog(null, "Octopus-toolkit requires R version 3.1.0 or higher.", "Check R Version",
							JOptionPane.INFORMATION_MESSAGE);
					requiredToolFlag = false;
				}
			}
		}

		return requiredToolFlag;
	}

	public String shellCmd(String[] command) {
		StringBuffer output = new StringBuffer();
		String installStr = "";

		Runtime runtime = Runtime.getRuntime();
		Process process;
		try {
			process = runtime.exec(command);
			InputStream is = process.getInputStream();
			InputStream eis = process.getErrorStream();

			InputStreamReader isr = new InputStreamReader(is);
			InputStreamReader esr = new InputStreamReader(eis);

			BufferedReader br = new BufferedReader(isr);
			BufferedReader ebr = new BufferedReader(esr);

			String line;
			while ((line = br.readLine()) != null || (line = ebr.readLine()) != null) {
				output.append(line + "\n");
				if (line.contains("incorrect password")) {
					installStr = "incorrect password";
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String output_rm = output.toString();
		for (int i = 0; i < 100; i++) {
			output_rm = output_rm.replaceAll("  ", " ");
		}

		if (installStr.equals("")) {
			return output_rm;
		} else {
			return installStr;
		}

	}

	public void makeDirectory(String dir[]) {

		for (int i = 0; i < dir.length; i++) {
			File mkdir = new File(ds.getPath() + dir[i]);
			if (!mkdir.exists()) {
				mkdir.mkdirs();
			}
		}
	}

	public String openDialog_File(JTextField text, String function) {
		// Public
		// GSE or GSM List File.
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/Octopus-toolkit/");
		
		if(function.equals("Full_Parameter")){
			fc.setMultiSelectionEnabled(false);			
		}else{
			fc.setMultiSelectionEnabled(true);
		}
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int returnVal = fc.showOpenDialog(new Frame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			if(function.equals("Full_Parameter")){
				File file = fc.getSelectedFile();
				text.setText("File : " + file.getName());
				return file.getPath();				
			}else{
				File files[] = fc.getSelectedFiles();

				text.setText("File : " + files[0].getName());
				ds.setInputText(files[0].getName());
				for (int i = 0; i < files.length; i++) {
					ds.setFilePath(files[i].getPath());
				}
				return "";				
			}
		} else {
			System.out.println("Canceled");
			text.setText("");
			ds.setInputText("");
			ds.getFilePath().clear();
			return null;
		}
	}

	public void openDialog_File_Dir() {
		// Private
		// Folder including Fastq, or Fastq
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/Octopus-toolkit/");
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int returnVal = fc.showOpenDialog(new Frame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File files[] = fc.getSelectedFiles();

			// init
			ds.initP_Fastq();
			String tmp[];
			
			for (int i = 0; i < files.length; i++) {

				if (files[i].isDirectory()) {
					File fileList[] = files[i].listFiles();
					Arrays.sort(fileList);
					for (int j = 0; j < fileList.length; j++) {
						setP_Fastq(fileList[j]);
					}
					tmp = files[i].getPath().split("/");
				} else {
					setP_Fastq(files[i]);
					tmp = files[i].getParent().split("/");
				}
				
				if (i == 0) {
					String dir = "P_" + tmp[tmp.length - 1];
					ds.setResultDirName(dir.replaceAll(" ", ""));
				}
			}
			
		} else {
			System.out.println("Canceled");
			ds.getP_Fastq().clear();
		}
	}
	
	public void setP_Fastq(File fastq) {

		String file_lower = fastq.getName().toLowerCase();
		if (file_lower.endsWith(".fq") || file_lower.endsWith(".fastq") || file_lower.endsWith(".fastq.gz")
				|| file_lower.endsWith(".fq.gz")) {
			String originFolder = fastq.getParent().replaceAll(" ", "\\ ");
			String originFile = fastq.getName();
			String tmpTitle = "";
			String f_name = "";

			if (file_lower.endsWith(".fq")) {
				f_name = originFile.substring(0, originFile.length() - 3);
				tmpTitle = checkSymbol(f_name);

				File changeFile = new File(fastq.getParent() + "/" + tmpTitle + ".fq");
				fastq.renameTo(changeFile);
				ds.setP_Fastq(tmpTitle + ".fq");
				ds.setHmP_Fastq(tmpTitle + ".fq", originFolder + "/" + tmpTitle + ".fq");
			} else if (file_lower.endsWith(".fastq")) {
				// fastq
				f_name = originFile.substring(0, originFile.length() - 6);
				tmpTitle = checkSymbol(f_name);

				File changeFile = new File(fastq.getParent() + "/" + tmpTitle + ".fastq");
				fastq.renameTo(changeFile);
				ds.setP_Fastq(tmpTitle + ".fastq");
				ds.setHmP_Fastq(tmpTitle + ".fastq", originFolder + "/" + tmpTitle + ".fastq");
			} else if (file_lower.endsWith(".fastq.gz")) {
				f_name = originFile.substring(0, originFile.length() - 9);
				tmpTitle = checkSymbol(f_name);

				File changeFile = new File(fastq.getParent() + "/" + tmpTitle + ".fastq.gz");
				fastq.renameTo(changeFile);
				ds.setP_Fastq(tmpTitle + ".fastq.gz");
				ds.setHmP_Fastq(tmpTitle + ".fastq.gz", originFolder + "/" + tmpTitle + ".fastq.gz");
			} else {
				// .fq.gz
				f_name = originFile.substring(0, originFile.length() - 6);
				tmpTitle = checkSymbol(f_name);

				File changeFile = new File(fastq.getParent() + "/" + tmpTitle + ".fq.gz");
				fastq.renameTo(changeFile);
				ds.setP_Fastq(tmpTitle + ".fq.gz");
				ds.setHmP_Fastq(tmpTitle + ".fq.gz", originFolder + "/" + tmpTitle + ".fq.gz");
			}

		}

	}

	public void openDialog_Dir(String subAnalysis) {
		// FindPeak or Graph or IGV(Visualization Tool)
		JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/Octopus-toolkit/");
		fc.setMultiSelectionEnabled(true);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(new Frame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File files[] = fc.getSelectedFiles();
			// init
			if (subAnalysis.equals("PeakCalling")) {
				ds.initTagInfo();

				for (int i = 0; i < files.length; i++) {
					searchTagDirectory(files[i].getPath());
				}
			} else {
				// Graph , IGV
				ds.initGraph();
				tmpBamInfo.clear();
				for (int i = 0; i < files.length; i++) {
					searchGraphFiles(files[i].getPath());
				}
				if (tmpBamInfo.size() > 0) {
					Boolean flag = false;
					for (int i = 0; i < tmpBamInfo.size(); i++) {
						for (int j = 0; j < ds.getBigWigInfo().size(); j++) {
							if (ds.getBigWigInfo().get(j)[5].equals("bam")) {
								String str = "sorted_" + tmpBamInfo.get(i)[0];
								if (str.equals(ds.getBigWigInfo().get(j)[0])) {
									flag = true;
									break;
								}
							}
						}

						if (flag == false) {
							ds.setBigWigInfo(tmpBamInfo.get(i));
						} else {
							flag = false;
						}
					}
				}
			}
		} else {
			System.out.println("Canceled");
			ds.initTagInfo();
			ds.initGraph();
		}
	}

	public void searchTagDirectory(String path) {
		File f = new File(path);
		File list[] = f.listFiles();

		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					if (list[i].getName().equals("03_Tag")) {
						File tag[] = list[i].listFiles();
						for (int j = 0; j < tag.length; j++) {
							String tmp[] = new String[6];
							
							String tmpTag[] = tag[j].getName().split("\\.");
							if (tmpTag.length > 3) {
								tmp[0] = tmpTag[0]; // Title
								tmp[1] = tag[j].getName(); // TagNamee
								tmp[2] = tag[j].getPath(); // TagPath
								tmp[3] = tmpTag[1]; // Seq(CH:chip/RN:rna ...)
								tmp[4] = tmpTag[3]; // Genome

								String tmpTagPath[] = tag[j].getPath().split("/");
								for (int k = 0; k < tmpTagPath.length; k++) {
									if (tmpTagPath[k].equals("03_Tag")) {
										tmp[5] = tmpTagPath[k - 1]; // ResultName,
																	// (GSE/GSM/P_)
										break;
									}
								}
								// set Tag Information
								ds.setTagInfo(tmp);
							}
						}
					} else {
						searchTagDirectory(list[i].getPath());
					}
				}
			}
		}
	}

	public void searchGraphFiles(String path) {
		File f = new File(path);
		File list[] = f.listFiles();

		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				if (list[i].isDirectory()) {
					searchGraphFiles(list[i].getPath());
				} else {
					if (list[i].getName().endsWith(".bed")) {
						String cmd[] = { "du", "-b", list[i].getPath() };
						String bedCapacity[] = shellCmd(cmd).split("\t");
						String tmpBed[] = list[i].getName().split("\\.");
						if (tmpBed.length > 4 && Integer.parseInt(bedCapacity[0]) > 1) {
							if (tmpBed[2].equals("SE") || tmpBed[2].equals("PE")) {
								String tmp[] = new String[2];
								tmp[0] = list[i].getName().replaceAll(".bed", "");
								tmp[1] = list[i].getPath();
								ds.setBedPath(tmp);
							}
						}
					} else if (list[i].getName().endsWith(".bigWig") || list[i].getName().endsWith(".bam")) {
						String tmp[] = new String[6];
						String tmpBigWig[] = list[i].getName().split("\\.");
						if (tmpBigWig.length > 4) {
							tmp[0] = tmpBigWig[0]; // Title
							tmp[1] = list[i].getName().replaceAll(".bigWig", ""); // bigWigNamee
							tmp[2] = list[i].getPath(); // bigWIgPath
							tmp[3] = tmpBigWig[1]; // Seq(CH:chip/RN:rna ...)
							tmp[4] = tmpBigWig[3]; // Genome
							if (list[i].getName().endsWith(".bigWig")) {
								tmp[5] = "bigWig";
							} else {
								tmp[5] = "bam";
							}

							if (list[i].getName().endsWith(".bam")) {
								if (tmp[0].length() > 7 && tmp[0].substring(0, 7).equals("sorted_")) {
									// set bigWig Information
									ds.setBigWigInfo(tmp);
								} else {
									tmpBamInfo.add(tmp);
								}
							} else {
								// set bigWig Information
								ds.setBigWigInfo(tmp);
							}
						}
					}
				}
			}
		}
	}

	public boolean checkStepPass() {
		boolean stepFlag = false;

		for (int i = 0; i < ds.getErrLog().getStepErr().length; i++) {
			if (ds.getErrLog().getStepErr(i) == true) {
				stepFlag = true;
				break;
			}
		}

		return stepFlag;
	}

	public boolean checkHDDCapacity() {
		boolean hddFlag = false;

		long freeSpace = new File(ds.getPath()).getUsableSpace();

		if (freeSpace / 1000 / 1000 / 1000 > 10) {
			hddFlag = true;
		}

		return hddFlag;
	}

	public boolean checkReference(String format, String genome) {
		String filePath = ds.getPath() + "Index/Reference/" + genome + "." + format;
		File file = new File(filePath);
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static void fileUrlReadAndDownload(String fileAddress, String localFileName, String downloadDir) {
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;
		try {
			URL Url;
			byte[] buf;
			int byteRead;
			int byteWritten = 0;
			Url = new URL(fileAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(downloadDir + localFileName));
			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while ((byteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, byteRead);
				byteWritten += byteRead;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			try {
				is.close();
				outStream.close();
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	public static void fileUrlDownload(String fileAddress, String downloadDir, String downloadGsm) {
		int slashIndex = fileAddress.lastIndexOf('/');
		int periodIndex = fileAddress.lastIndexOf('.');
		String fileName = downloadGsm;
		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fileAddress.length() - 1) {
			fileUrlReadAndDownload(fileAddress, fileName, downloadDir);
		} else {
			System.err.println("OverData Download : No such file or directory");
		}
	}

	public void removeFile(String step) {
			if (step.equals("SRA")) {
				try {
					FileUtils.deleteDirectory(new File(ds.getAnalysisPath() + "00_SRA/"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			} else if (step.equals("Fastq")) {
				if (ds.getReverseFastq().equals("")) {
					// Single
					new File(ds.getForwardFastq()).delete();
				} else {
					new File(ds.getForwardFastq()).delete();
					new File(ds.getReverseFastq()).delete();
				}
			} else if (step.equals("Fastqc")) {
				try {
					FileUtils.deleteDirectory(new File(ds.getAnalysisPath() + "01_Fastqc/"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			} else if (step.equals("Trim")) {
				if (!ds.getOmitStep("Trim")) {
					// Already change Trim.fastq Path
					if (ds.getReverseFastq().equals("")) {
						// Single
						new File(ds.getForwardFastq()).delete();
					} else {
						new File(ds.getForwardFastq()).delete();
						new File(ds.getReverseFastq()).delete();
					}
				} else if (ds.getRemove(1) == true) {
					if (ds.getReverseFastq().equals("")) {
						// Single
						new File(ds.getForwardFastq()).delete();
					} else {
						new File(ds.getForwardFastq()).delete();
						new File(ds.getReverseFastq()).delete();
					}
				}
			} else if (step.equals("Bam")) {
				new File(ds.getBamFile().replace("sorted_", "")).delete();
			} else if (step.equals("Sort")) {
				// Already change Sorted_bam Path
				new File(ds.getBamFile()).delete();
				new File(ds.getBamFile() + ".bai").delete();
			}
	}

	public String makeLabel(boolean rep,String data) {

		String label = "";

		label = ds.getGSMInfo()[0] + "." + ds.getGSMInfo()[2].substring(0, 2).toUpperCase() + ".";

		if (ds.getReverseFastq().equals("")) {
			// Single
			label = label + "SE." + ds.getGSMInfo()[1];
		} else {
			// Paired
			label = label + "PE." + ds.getGSMInfo()[1];
		}

		if (rep == true) {
			label = label + ".rep";
		}
		
		if(data.equals("Public")){
			label = ds.getRunGSM()+"_"+label;
		}

		return label;
	}

	public String convertGenome(String genome) {
		String tmpGenome = "";
		if (genome.equals("CH"))
			tmpGenome = "ChIP-Seq";
		else if (genome.equals("RN"))
			tmpGenome = "RNA-Seq";
		else if (genome.equals("ME"))
			tmpGenome = "MeDIP-Seq";
		else if (genome.equals("AT"))
			tmpGenome = "ATAC-Seq";
		else if (genome.equals("DN"))
			tmpGenome = "Dnase-Seq";
		else // MN
			tmpGenome = "Mnase-Seq";
		return tmpGenome;
	}

	public void downLoadReference(String format, String genome) {

		if (format.equals("fasta")) {

			String downloadUrl = "";
			if (genome.equals("tair10")){
				downloadUrl = "143.248.14.23/Octopus-Sub/Tair10/tair10.2bit";
			} else {
				downloadUrl = "http://hgdownload.soe.ucsc.edu/goldenPath/" + genome + "/bigZips/" + genome + ".2bit";
			}
			String cmd[] = { "wget", downloadUrl, "-O", ds.getPath() + "Index/Reference/" + genome + ".2bit", "-o",
					ds.getPath() + "Script/Downlog" };
			String cmd2[] = { ds.getPath() + "Tools/SubTool/twoBitToFa",
					ds.getPath() + "Index/Reference/" + genome + ".2bit",
					ds.getPath() + "Index/Reference/" + genome + ".fasta" };
			shellCmd(cmd);
			shellCmd(cmd2);

			new File(ds.getPath()+"Index/Reference/"+genome+".2bit").delete();
			
			String chrList = "";
			
			//Fasta Filtering (Remove chrUn, chrx_gl002342_random)
			if(genome.equals("hg38") || genome.equals("hg19") || genome.equals("hg18")){
				chrList = "hg.list";
			}else if(genome.equals("mm10") || genome.equals("mm9")){
				chrList = "mm.list";
			}else if(genome.equals("dm6") || genome.equals("dm3")){
				chrList = "dm.list";
			}else if(genome.equals("canFam3")){
				chrList = "canFam3.list";
			}else if(genome.equals("sacCer3")){
				chrList = "sacCer3.list";
			}else if(genome.equals("danRer10")){
				chrList = "danRer10.list";
			}
			
			
			if(!chrList.equals("")){
				downloadUrl = "143.248.14.23/Octopus-Sub/chromosome/"+chrList;
				
				String list[] = { "wget", downloadUrl, "-O", ds.getPath() + "Index/Reference/" + chrList, "-o",
						ds.getPath() + "Script/Downlog" };
				
				String python = "python";
				if(ds.getOS().equals("Fedora")){
					python = "python3";
				}
				
				String cmd3[] = { python, ds.getPath() + "Tools/SubTool/Extract_Chr_Sequence.py", "-i",
						ds.getPath() + "Index/Reference/" + genome + ".fasta", "-o",
						ds.getPath() + "Index/Reference/" + genome + "2.fasta", "-l",
						ds.getPath() + "Index/Reference/" + chrList };		

				String cmd4[] = {"mv",ds.getPath() + "Index/Reference/" + genome + "2.fasta",ds.getPath() + "Index/Reference/" + genome + ".fasta"};
				
				shellCmd(list);
				shellCmd(cmd3);
				new File(ds.getPath() + "Index/Reference/" + genome + ".fasta").delete();
				shellCmd(cmd4);
				new File(ds.getPath() + "Index/Reference/"+chrList).delete();

			}
			
			// Download GTF
			downloadUrl = "143.248.14.23/Octopus-Sub/GTF/"+genome+".gtf";
			String cmd5[] = { "wget", downloadUrl, "-O", ds.getPath() + "Index/Reference/" + genome + ".gtf", "-o",
					ds.getPath() + "Script/Downlog" };
			
			shellCmd(cmd5);
			
		} else if(format.equals("bed")){
			// Bed
			String downLoadUrl = "143.248.14.23/Octopus-Sub/Bed/" + genome + ".bed";
			String cmd[] = { "wget", downLoadUrl, "-O", ds.getPath() + "Index/Reference/" + genome + ".bed", "-o",
					ds.getPath() + "/Script/Downlog" };
			
			shellCmd(cmd);
			
		}
	}

	public Point getCenter(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point p = new Point();
		p.x = (screenSize.width - width) / 2;
		p.y = (screenSize.height - height) / 2;

		if (p.x < 0) {
			p.x = 0;
		}
		if (p.y < 0) {
			p.y = 0;
		}
		return p;
	}

	public void compressGz(String format, String data) {
		ds.writeLogRun("Convert : Compress fastq to gzip (Start)\n", true);

		if (data.equals("Public")) {
			if (format.equals("Fastq")) {
				if (ds.getReverseFastq().equals("")) {
					String cmdFor[] = { "gzip", ds.getAnalysisPath() + "00_Fastq/" + ds.getRunGSM()+"_"+ds.getGSMInfo()[0] + ".fastq" };
					shellCmd(cmdFor);
				} else {
					String cmdFor[] = { "gzip", ds.getAnalysisPath() + "00_Fastq/" + ds.getRunGSM()+"_"+ds.getGSMInfo()[0] + "_1.fastq" };
					String cmdRev[] = { "gzip", ds.getAnalysisPath() + "00_Fastq/" + ds.getRunGSM()+"_"+ds.getGSMInfo()[0] + "_2.fastq" };
					shellCmd(cmdFor);
					shellCmd(cmdRev);
				}
			} else { // Trim
				if (ds.getReverseFastq().equals("")) {
					String cmdFor[] = { "gzip", ds.getForwardFastq() };
					shellCmd(cmdFor);
				} else {
					String cmdFor[] = { "gzip", ds.getForwardFastq() };
					String cmdRev[] = { "gzip", ds.getReverseFastq() };
					shellCmd(cmdFor);
					shellCmd(cmdRev);
				}
			}
		} else { // Private
			if (format.equals("Trim")) {
				if (ds.getReverseFastq().equals("")) {
					String cmdFor[] = { "gzip", ds.getForwardFastq() };
					shellCmd(cmdFor);
				} else {
					String cmdFor[] = { "gzip", ds.getForwardFastq() };
					String cmdRev[] = { "gzip", ds.getReverseFastq() };
					shellCmd(cmdFor);
					shellCmd(cmdRev);
				}
			}
		}

		ds.writeLogRun("Convert : Compress fastq to gzip (End)\n", true);

	}

	public boolean checkInteger(String value) {
		try {
			int num = Integer.parseInt(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean checkDouble(String value) {
		try {
			double num = Double.parseDouble(value);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public String checkSymbol(String txt) {

		txt = txt.replace("(", "[");
		txt = txt.replace(")", "]");
		txt = txt.replace("&amp;", "[and]");
		txt = txt.replace("&", "[and]");
		txt = txt.replace("/", "-");
		txt = txt.replace(",", "-");
		txt = txt.replace(" ", "-");
		txt = txt.replace("--", "-");
		txt = txt.replace(";", "-");
		txt = txt.replace("Δ", "[del]");
		txt = txt.replace("'", "");
		txt = txt.replace(".", "_");

		if (txt.indexOf("Reference-Epigenome:") >= 0) {
			txt.replace("Reference-Epigenome:", "");
		}

		return txt;
	}

	public void openURL(String url) {
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
				openURL.invoke(null, new Object[] { url });
			} else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else { // assume Unix or Linux
				String[] browsers = { "chrome", "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					Runtime.getRuntime().exec(new String[] { browser, url });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, errMsg + ":\n" + e.getLocalizedMessage());
		}
	}

	public void installGenome(String genome) { // For Homer

		ds.writeLogRun("Download : Reference genome for Homer (Start)\n", true);

		String organism = "";
		String genomeUrl[] = new String[3];

		if (genome.equals("hg38") || genome.equals("hg19") || genome.equals("hg18")) {
			organism = "human";
		} else if (genome.equals("mm10") || genome.equals("mm9")) {
			organism = "mouse";
		} else if (genome.equals("dm6") || genome.equals("dm3")) {
			organism = "fly";
		} else if (genome.equals("sacCer3")) {
			organism = "yeast";
		} else if (genome.equals("canFam3")) {
			organism = "dog";
		} else if (genome.equals("tair10")) {
			organism = "arabidopsis";
		} else if (genome.equals("danRer10")) {
			organism = "zebrafish";
		} else if (genome.equals("ce11")) {
			organism = "worm";
		}

		String path = ds.getPath();
		String spacePath = ds.getPath();
		String line;
		try {
			FileWriter fw = new FileWriter(path + "Script/downloadGenome.sh");
			
			try {
				FileReader updataFile = new FileReader(path + "/Tools/Homer/update.txt");
				BufferedReader tmpBuffer = new BufferedReader(updataFile);

				int urlIdx = 0;

				while ((line = tmpBuffer.readLine()) != null) {
					String tmp[] = line.split("\t");

					if (tmp[0].equals(genome) || tmp[0].equals(organism)) {
						genomeUrl[urlIdx++] = line;
					}
				}
			} catch (Exception e) {
				System.out.println("\nError : Unable to make the update.txt file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to make the update.txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
			}
			for (int i = 0; i < genomeUrl.length; i++) {
				if (genomeUrl[i] != null) {
					String tmp[] = genomeUrl[i].split("\t");

					fw.write("wget " + tmp[3] + " -O " + spacePath + "/Tools/" + tmp[0] + ".zip -o " + spacePath
							+ "Script/downLog\n");
					fw.write("unzip -o " + spacePath + "/Tools/" + tmp[0] + ".zip -d " + spacePath + "/Tools/Homer/\n");
					fw.write("rm " + spacePath + "/Tools/" + tmp[0] + ".zip\n");
					fw.flush();

					// Log
					ds.writeLogCmd("wget " + tmp[3] + " -O " + spacePath + "/Tools/" + tmp[0] + ".zip -o " + spacePath
							+ "Script/downLog\n");
					ds.writeLogCmd(
							"unzip -o " + spacePath + "/Tools/" + tmp[0] + ".zip -d " + spacePath + "/Tools/Homer/\n");
					ds.writeLogCmd("rm " + spacePath + "/Tools/" + tmp[0] + ".zip\n");
				}
			}

			// make config
			FileReader fr = new FileReader(path + "Tools/Homer/config.txt");
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw2 = new FileWriter(path + "Tools/Homer/tmpConfig.txt");

			while ((line = br.readLine()) != null) {
				if (line.equals("PROMOTERS")) {
					fw2.write(genomeUrl[0] + "\n");
				} else if (line.equals("GENOMES")) {
					fw2.write(genomeUrl[1] + "\n");
				} else if (line.equals("SETTINGS")) {
					fw2.write(genomeUrl[2] + "\n");
				}
				fw2.write(line + "\n");
				fw2.flush();
			}
			fw2.close();
			br.close();
			fr.close();

			fw.write("mv " + spacePath + "Tools/Homer/tmpConfig.txt " + spacePath + "Tools/Homer/config.txt \n");

			fw.close();
			String cmdDownload1[] = { "chmod", "777", path + "/Script/downloadGenome.sh" };
			String cmdDownload2[] = { "sh", path + "/Script/downloadGenome.sh" };
			shellCmd(cmdDownload1);
			shellCmd(cmdDownload2);

			//Change Homer TAIR10 Chromosome Label
			if(genome.equals("tair10")){

				String tair_cmd[] = {"wget","143.248.14.23/Octopus-Sub/Tair10/tair10.zip","-O",path + "/Tools/Homer/tair10.zip","-o",path	+ "/Script/Downlog"};
				String tair_cmd2[] = {"unzip","-o",path + "/Tools/Homer/tair10.zip","-d",path + "/Tools/Homer/data/genomes/tair10/"};
				shellCmd(tair_cmd);
				shellCmd(tair_cmd2);
				new File(path+"Tools/Homer/tair10.zip").delete();
			}
						
			ds.writeLogRun("Download : Reference genome for Homer (End)\n", true);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\nError : Unable to use the config.txt file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to use the config.txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
			return;
		}
		
	}

	public boolean checkHomerGenome(String genome) {
		String filePath = ds.getPath() + "Tools/Homer/data/genomes/" + genome;

		File file = new File(filePath);

		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}
}