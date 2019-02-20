package Octopus_Src;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import Octopus_UI.UI_Octopus;

public class DataSet {
	
	private UI_Octopus ui_main;
	private Main_Process mp;
	private CheckProgram cp;
	
	private String os;
	private String os_version;
	private int memory;
	private int hdd_capacity;
	private int os_bit;
	private String path;
	private DataSet_Err err;
	private boolean fullOption;
	private String password;
			
	// Input Data
	private String gseNum;
	private ArrayList<String> gsmNum;
	private String gsmInfo[];
	private String inputText;
	private String runGSM;
	
	// Open File
	private ArrayList<String> filePath;
	
	// Preprocessing
	private String analysisPath; // ..Result/GSEXXXXX/
	private String forward;
	private String reverse;
	private String pairSymbol;
	
	// QualityCheck & Trimming
	private String encoding_fr;
	private String encoding_rv;
	
	// Mapping
	private String bamFile;
	private String labelTitle;
	
	
	// Private Data
	private Map<String,String> hmP_Fastq; // FileName , 
	private ArrayList<String> p_Fastq;
	private DefaultTableModel model_Personal;
	private ArrayList<String[]> privateData;
	private String resultDirName; // Result Folder Name
	
	
	// SubRun - Octopus-Option
	private boolean remove[]; //0:sra, 1:fastq, 2:fastqc, 3:trim, 4:bam, 5:sorted_bam
	private boolean latestGenome;
	private boolean continueAnalyze;
	private boolean omit_Trim;
	private boolean omit_sort;
	private String cpu;
	private boolean helpFlag;
	private String public_strand;
	private String alignTool;
	private boolean compress_gz;
	private boolean compress_cram;
	
	// FindPeak
	private ArrayList<String[]> tagInfo;	//0:title, 1:TagName, 2:TagPath, 3: Seq, 4: genome 5: FolderName(GSE,GSM,P_)
	private Map<String,String> hmTagInfo;
	
	// Graph / IGV
	private ArrayList<String[]> bedPath; // 0:title, 1:bedPath
	private ArrayList<String[]> bigWigInfo; //0:title, 1:bigWigName, 2:bigWigPath, 3: Seq, 4: genome
	private Map<String,String> hmBigWigInfo;
	
	// Graph
	private int bedIdx;
	private String tssRegion;
	private String binNum;
	
	
	// Log
	private FileWriter log_Cmd;
	private FileWriter log_Run;
	private FileWriter log_analyzeInfo;
	
	private String toolOption[];
	private boolean analyze_flag;
	private ArrayList<String[]> subRunLog;
	
	public DataSet(){
		fullOption = false;
		memory = 0;
		hdd_capacity = 0;
		os_bit = 64;
		path = "";
		gseNum = "";
		gsmNum = new ArrayList<>();
		err = new DataSet_Err();
		filePath = new ArrayList<>();
		analysisPath = "";
		forward = "";
		reverse = "";
		encoding_fr = "sanger";
		encoding_rv = "sanger";
		bamFile = "";
		inputText = "";
		labelTitle = "";
		password = "";
		os = "";
		os_version="";
		runGSM = "";
		
		hmP_Fastq = new HashMap<String,String>();
		p_Fastq = new ArrayList<>();
		privateData = new ArrayList<String[]>();
		resultDirName = "";
		
		remove = new boolean[6];
		latestGenome = true;
		continueAnalyze = true;
		omit_Trim = false;
		omit_sort = false;
		cpu = "4";
		public_strand = "Unstrand";
		alignTool = "Hisat2";
		
		tagInfo = new ArrayList<>();
		hmTagInfo = new HashMap<String,String>();
		
		bedPath = new ArrayList<>();
		bigWigInfo = new ArrayList<>();
		hmBigWigInfo = new HashMap<String,String>();
		
		bedIdx = 0;
		tssRegion = "";
		binNum = "";
		
		toolOption = new String[10];
		compress_gz = false;
		compress_cram = false;
		
		analyze_flag = false;
		subRunLog = new ArrayList<>();
	}

	public void setMainUI(UI_Octopus ui){
		ui_main = ui;
	}
	public void setMainProcess(Main_Process mp){
		this.mp = mp;
	}
	public void setCheckProgram(CheckProgram cp){
		this.cp = cp;
	}
	public void initGSMInfo(){
		gsmInfo = new String[6]; 
		//Public : 0:Title,1:Organism,2:Library,3:Instrument,4:sraUrl, 5:null(Strand Option)
		//Private: 0:Title,1:Organism,2:Library,3:forward,4:reverse, 5:strand
		
		forward = "";
		reverse = "";
		encoding_fr = "sanger";
		encoding_rv = "sanger";
		bamFile = "";
		pairSymbol = "";
		
	}
	public void initP_Fastq(){
		hmP_Fastq.clear();
		p_Fastq.clear();
		privateData.clear();
	}
	
	public void initTagInfo(){
		tagInfo.clear();
		hmTagInfo.clear();
	}
	public void initGraph(){
		bedPath.clear();
		bigWigInfo.clear();
		hmBigWigInfo.clear();
		bedIdx = 0;
		tssRegion = "";
		binNum = "";
	}
	public void setHelpFlag(Boolean flag){
		helpFlag = flag;
	}
	
	public void setFullOption(boolean flag){
		fullOption = flag;
	}
	public void setRunGSM(String gsm){
		runGSM = gsm;
	}
	//=====
	
	public void setMemory(int mem){
		memory = mem;
	}
	public void setHddCapacity(int hdd){
		hdd_capacity = hdd;
	}
	public void setOs_bit(int os){
		os_bit = os;
	}
	public void setPath(String p){
		path = p;
	}
	public void setGSE(String gse){
		gseNum = gse;
	}
	public void setGSM(ArrayList<String> gsm){
		gsmNum = gsm;
	}
	public void setGSMInfo(int idx, String info){
		gsmInfo[idx] = info;
	}
	public void setFilePath(String path){
		filePath.add(path);
	}
	public void setAnalysisPath(String path){
		analysisPath = path;
	}
	public void setFastq(String fr, String rv){
		forward = fr;
		reverse = rv;
	}
	public void setEncoding(String frEncoding, String rvEncoding){
		encoding_fr = frEncoding;
		encoding_rv = rvEncoding;
	}
	public void setBamFile(String bam){
		bamFile = bam;
	}
	public void setHmP_Fastq(String key, String value){
		hmP_Fastq.put(key, value);
	}
	
	public void setP_Fastq(String file){
		p_Fastq.add(file);
	}
	
	public void setModel(DefaultTableModel model){
		model_Personal = model;
	}
	public void setResultDirName(String name){
		resultDirName = name;
		gseNum = name;
	}
	
	public void setRemove(boolean op1,boolean op2,boolean op3,boolean op4,boolean op5,boolean op6){
		remove[0] = op1;
		remove[1] = op2;
		remove[2] = op3;
		remove[3] = op4;
		remove[4] = op5;
		remove[5] = op6;
	}
	public void setLatest(boolean latest){
		latestGenome = latest;
	}
	public void setContinueanalyze(boolean cont){
		continueAnalyze = cont;
	}
	public void setOmitStep(boolean trim, boolean sort){
		omit_Trim = trim;
		omit_sort = sort;
	}
	public void setAnalysisCPU(String select_cpu){
		cpu = select_cpu;
	}
	
	public void setPublicStrand(String strand){
		public_strand = strand;
	}
	
	public void setRemove(int idx, boolean op){
		remove[idx] = op;
	}
	
	public void setInputText(String text){
		inputText = text;
	}
	public void setLabelTitle(String title){
		labelTitle = title;
	}
	public void setTagInfo(String tag[]){
		tagInfo.add(tag);
	}
	public void setHmTagInfo(String key, String value){
		hmTagInfo.put(key, value);
	}
	public void setBedPath(String path[]){
		bedPath.add(path);
	}
	public void setBigWigInfo(String bw[]){
		bigWigInfo.add(bw);
	}
	public void setHmBigWigInfo(String key, String value){
		hmBigWigInfo.put(key, value);
	}
	public void setBedIdx(int num){
		bedIdx = num;
	}
	public void setTssRegion(String tss){
		tssRegion = tss;
	}
	public void setBinNum(String bin){
		binNum = bin;
	}
	public void setLog_Cmd(){				
		Date now = new Date();
		String tmp2[] =now.toString().split(" ");
		String dateLog = tmp2[5]+"_"+tmp2[1]+"_"+tmp2[2];
		
		try{
			log_Cmd = new FileWriter(path+"Log/Command/"+dateLog+".cmd.txt",true);
			log_Cmd.write("\n# "+now+"\n");
			
			log_Run = new FileWriter(path+"Log/Run/"+dateLog+".run.txt",true);
			log_Run.write("\n# "+now+"\n");
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("\nError : Unable to make the "+dateLog+".txt files due to unknown reasons (Err009)");
			writeLogRun("Error : Unable to make the "+dateLog+".txt files due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
	}
	public void closeLog(){
		try {
			
			Date now = new Date();
			String tmp[] =now.toString().split(" ");
						
			log_Cmd.write("# Octopus-toolkit is terminated.\n");
			log_Cmd.flush();
			log_Run.write("["+tmp[3]+"] Octopus-toolkit is terminated.\n");
			log_Run.flush();
			log_Cmd.close();
			log_Run.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	public void writeLogCmd(String str){
		try {
			log_Cmd.write(str);
			log_Cmd.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("\nError : Unable to make the log.cmd.txt file due to unknown reasons (Err009)");
			writeLogRun("Error : Unable to make the log.cmd.txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
			
		}
	}
	
	public void writeLogRun(String str, boolean time){
		try{
			Date now = new Date();
			String tmp[] = now.toString().split(" ");
			
			if(time == true){
				log_Run.write("["+tmp[3]+"] "+str);
				if(ui_main != null)
					ui_main.setRunInfoWindowLog("["+tmp[3]+"] "+str);
			}else{
				log_Run.write("-"+str);
				if(ui_main != null)
					ui_main.setRunInfoWindowLog("-"+str);
			}
			log_Run.flush();
			
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("\nError : Unable to make the log.run.txt file due to unknown reasons (Err009)");
			writeLogRun("Error : Unable to make the log.run.txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);

			
		}
	}
	
	public void setLogAnalyzeInfo(){				
		try{
			log_analyzeInfo = new FileWriter(path+"Result/"+gseNum+"/"+gseNum+".txt",true);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("\nError : Unable to make the "+gseNum+".txt file due to unknown reasons (Err009)");
			writeLogRun("Error : Unable to make the "+gseNum+".txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}	
	}
	
	public void writeLogInfo(String str){
		try{
			Date now = new Date();
			String time[] = now.toString().split(" ");
			String continueStr = "Continue Analyze("+continueAnalyze+")";
			String timeStr = time[5]+"_"+time[1]+"_"+time[2]+"_"+time[3]+"\t"+continueStr;
			String tmp = "";
			String mapTool = "";
			
			if (gsmInfo[2] != null) {
				if (gsmInfo[2].toUpperCase().equals("RNA-SEQ")) {
					mapTool = alignTool;
				} else {
					mapTool = "Hisat2";
				}

				if (gsmNum.size() != 0) { // Public
					tmp = runGSM + "\t" + gsmInfo[0] + "\t" + gsmInfo[2] + "\t" + gsmInfo[1] + "\t" + mapTool + "\t"
							+ str + "\n";
				} else { // Private
					tmp = gsmInfo[0] + "\t" + gsmInfo[2] + "\t" + gsmInfo[1] + "\t" + mapTool + "\t" + str + "\n";
				}

				log_analyzeInfo.write(timeStr + "\t" + tmp);
				log_analyzeInfo.flush();
			}else {
				log_analyzeInfo.write(timeStr+"\t"+gseNum+"\t-\t-\t-\t-\t"+str+"\n");
				log_analyzeInfo.flush();
			}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("\nError : Unable to make the "+gseNum+".txt file due to unknown reasons (Err009)");
			writeLogRun("Error : Unable to make the "+gseNum+".txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
	}
	public void closeAnalyzeInfo(){
		try {
			log_analyzeInfo.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void setToolOption(int idx, String op){
		toolOption[idx] = op;
		// 0 : aspera
		// 1 : fastq-dump(SRA-toolkit)
		// 2 : fastqc
		// 3 : trimmomatic
		// 4 : hisat2-align(hisat2)
		// 5 : star
		// 6 : makeTagDirectory(Homer)
		// 7 : makeUCSCfile(Homer)
		// 8 : findPeak(Homer)
		// 9 : annotatePeak(Homer)
	}
	
	public void setPassword(String pw){
		password = pw;
	}
	public void setOS(String os){
		this.os = os;
	}
	public void setOS_Version(String os_version){
		this.os_version = os_version;
	}
	
	public void setAlignTools(String tool){
		alignTool = tool;
	}
	
	public void setCompressGz(boolean check){
		compress_gz = check;
	}
	
	public void setCompressCram(boolean check){
		compress_cram = check;
	}
	
	public void initAnalyzeFlag(){
		analyze_flag = false;
	}
	public void setAnalyzeFlag(){
		analyze_flag = true;
	}
	public void setSubRunLog(String gsm[]){
		subRunLog.add(gsm);
	}
	
	public void setPairSymbol(String symbol){
		pairSymbol = symbol;
	}
	//=====
	
	public UI_Octopus getMainUI(){
		return ui_main;
	}
	public Main_Process getMainProcess(){
		return mp;
	}
	public CheckProgram getCheckProgram(){
		return cp;
	}
		
	public int getMemory(){
		return memory;
	}
	public int getHddCapacity(){
		return hdd_capacity;
	}
	public int getOs_bit(){
		return os_bit;
	}
	public String getPath(){
		return path;
	}
	public DataSet_Err getErrLog(){
		return err;
	}
	
	public String getGSE(){
		return gseNum;
	}
	public ArrayList<String> getGSM(){
		return gsmNum;
	}
	public String[] getGSMInfo(){
		return gsmInfo;
	}
	public ArrayList<String> getFilePath(){
		return filePath;
	}
	public String getAnalysisPath(){
		return analysisPath;
	}
	public String getForwardFastq(){
		return forward;
	}
	public String getReverseFastq(){
		return reverse;
	}
	public String getEncodingFr(){
		return encoding_fr;
	}
	public String getEncodingRv(){
		return encoding_rv;
	}
	public String getBamFile(){
		return bamFile;
	}
	public String getHmP_Fastq(String key){
		return hmP_Fastq.get(key);
	}
	public ArrayList<String> getP_Fastq(){
		return p_Fastq;
	}
	public DefaultTableModel getModel(){
		return model_Personal;
	}
	public ArrayList<String[]> getPrivateData(){
		return privateData;
	}
	public String getResultDirName(){
		return resultDirName;
	}
	public boolean getRemove(int idx){
		return remove[idx];
	}
	public String getInputText(){
		return inputText;
	}
	public String getLabelTitle(){
		return labelTitle;
	}
	public ArrayList<String[]> getTagInfo(){
		return tagInfo;
	}
	public String getHmTagInfo(String key){
		return hmTagInfo.get(key);
	}
	public ArrayList<String[]> getBedPath(){
		return bedPath;
	}
	public ArrayList<String[]> getBigWigInfo(){
		return bigWigInfo;
	}
	public String getHmBigWigInfo(String key){
		return hmBigWigInfo.get(key);
	}
	public int getBedIdx(){
		return bedIdx;
	}
	public String getTssRegion(){
		return tssRegion;
	}
	public String getBinNum(){
		return binNum;
	}
	public boolean getLatest(){
		return latestGenome;
	}
	public boolean getContinueAnalyze(){
		return continueAnalyze;
	}
	public boolean getOmitStep(String step){
		if(step.equals("Trim"))
			return omit_Trim;
		else
			return omit_sort;
	}
	public String getAnalysisCPU(){
		return cpu;
	}
	
	public String getPublicStrand(){
		return public_strand;
	}
	public boolean getHelpFlag(){
		return helpFlag;
	}
	public String getToolOPtion(int idx){
		return toolOption[idx];
	}
	public boolean getFullOption(){
		return fullOption;
	}
	public String getPassword(){
		return password;
	}
	public String getOS(){
		return os;
	}
	public String getOS_Version(){
		return os_version;
	}
	public String getAlignTools(){
		return alignTool;
	}
	public String getRunGSM(){
		return runGSM;
	}
	public boolean getCompressGz(){
		return compress_gz;
	}
	public boolean getCompressCram(){
		return compress_cram;
	}
	public boolean getAnalyzeFlag(){
		return analyze_flag;
	}
	public ArrayList<String[]> getSubRunLog(){
		return subRunLog;
	}
	public String getPairSymbol(){
		return pairSymbol;
	}
}