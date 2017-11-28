package Octopus_Src;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parsing {
	private DataSet ds;
	private CommonFunc cf;
	private Boolean existFlag;
	private Boolean checkExperimentFlag;
	private Boolean checkOverDataFlag;
	private Boolean checkGsmFlag;
	
	private String overDataUrl;
	private Boolean inputGSM;
	private static int size = 1024;
	private int accessCnt = 0;
	private int accessCnt2 = 0;
	
	private String subGSM;
	
	public Parsing(DataSet ds, CommonFunc cf) {
		this.ds = ds;
		this.cf = cf;
		existFlag = false; // false : not exist, true : exist
		checkExperimentFlag = false;
		checkExperimentFlag = false;
		overDataUrl="";	// Up to 2000 Data.
		checkOverDataFlag = false; // false : under, true : over
		inputGSM = false; // false : Input GSE , true : Input GSM
		checkGsmFlag = true; // false : GSM Err
	}

	public void startParse(String gseNum) {
		ds.setGSE(gseNum.toUpperCase());
		ds.getGSM().clear();
		
		ds.getMainUI().setProgress(5, "Parsing : "+ gseNum);
		ds.getMainUI().setRunningPrograss(true);
		
		// Check page & experiment type
		accessCnt = 0;
		Elements text = checkPage();
		
		if(existFlag == true && checkExperimentFlag == true){
			// Download overData
			if(checkOverDataFlag == true){
				fileUrlDownload(overDataUrl,ds.getPath()+"/Result/",gseNum);
			}
			
			// get GSM Number Info
			if(inputGSM == false){
				getGSM(text);
			}else{
				ds.getGSM().add(ds.getGSE());
			}
		}else{
			// Add Error Message
			// Err Write. (Log)
			String[] dir = {"/Result/" +  ds.getGSE()};
			cf.makeDirectory(dir);
			ds.setLogAnalyzeInfo();
			ds.initGSMInfo();
			
			if (!ds.getErrLog().getAccessPage().equals("")) {
				ds.writeLogRun("Error : Unable to access the NCBI. <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-001\">[Err001]</a> \n\n", true);
				ds.writeLogCmd("Err Message : " + ds.getErrLog().getAccessPage() + "\n");
				ds.writeLogInfo("[Unable to access the NCBI. (Err001)]");
				ds.getMainUI().setSimpleLbl("Err001");
				ds.setAnalyzeFlag();
			} else if(!ds.getErrLog().getExistErr().equals("")){
				ds.writeLogRun("Error : Unable to find GSMs (or GSE) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-002\">[Err002]</a> \n\n", true);
				ds.writeLogCmd("Err Message : " + ds.getErrLog().getGSMInfoErr() + "\n");
				ds.writeLogInfo("[Unable to find GSMs (or GSE) (Err002)]");
				ds.getMainUI().setSimpleLbl("Err002");
				ds.setAnalyzeFlag();
			} else{
				ds.writeLogRun("Error : Unable to analyze the data (this type of NGS data is not currently supported)<a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-003\">[Err003]</a> \n\n", true);
				ds.writeLogCmd("Err Message : " + ds.getErrLog().getExperimentErr() + "\n");
				ds.writeLogInfo("[Unable to analyze the data (this type of NGS data is not currently supported) (Err003)]");
				ds.getMainUI().setSimpleLbl("Err003");
				ds.setAnalyzeFlag();
			}
			
			ds.closeAnalyzeInfo();
			
		}
		
		ds.getMainUI().setRunningPrograss(false);
	}

	
	public Elements checkPage(){
		ds.getErrLog().initErr();
		try {
			Document doc = Jsoup.connect("http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=" + ds.getGSE()).timeout(5000).get();
			Elements text = doc.select("td");
			
			//check page(exist or not exist)
			for (Element e : text) {
				
				if(!e.text().equals("")){
					if(e.text().equals("Series "+ds.getGSE())){
						existFlag = true;
						break;
					}
				}
			}
						
			// Input : GSM
			if(ds.getGSE().length() > 3 && ds.getGSE().substring(0,3).equals("GSM")){
				checkExperimentFlag = true;
				inputGSM = true;
				existFlag = true;
			}else{
				// Input : GSE
				if (existFlag == true) {
					// check Experiment type
					boolean tmpFlag = false;
					for (Element e : text) {
						if (tmpFlag == true) {
							if (e.text().equals("Summary")) {
								// break;
							} else if (e.text().contains("Expression profiling by high throughput sequencing") || e.text()
									.contains("Genome binding/occupancy profiling by high throughput sequencing")) {
								checkExperimentFlag = true;
								break;
							}
						} else {
							if (e.text().equals("Experiment type")) {
								tmpFlag = true;
							}
						}
					}
					if(checkExperimentFlag == false){
						ds.getErrLog().setExperimentErr("Err003 : Unable to analyze the data (this type of NGS data is not currently supported)");
						System.out.println(ds.getErrLog().getExperimentErr());
						ds.setAnalyzeFlag();
					}
				}else{
					ds.getErrLog().setExistErr("Err002 : Unable to find GSMs (or GSE) : "+ds.getGSE());
					System.out.println(ds.getErrLog().getExistErr());
					ds.setAnalyzeFlag();
					
				}
				
				// check overData (Up to 850 Data)
				if(ds.getAnalyzeFlag() == false){
					Elements text2 = doc.select("a");
					for (Element e : text2) {
						if (e.text().equals("You can also download a list of all accessions here")) {
							String tmp[] = e.outerHtml().split("\"");

							overDataUrl = "https://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=" + ds.getGSE()
									+ "&targ=self&view=brief&form=text";
							checkOverDataFlag = true;
						}
					}
				}
			}
			
			return text;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(accessCnt == 100){
				ds.getErrLog().setAccessPage("Err001 : Unable to access the NCBI : "+ds.getGSE());
				System.out.println("Error : Unable to access the NCBI : "+ds.getGSE() + " (Err001)");
				ds.getMainUI().setSimpleLbl("Err001");
				accessCnt = 0;
				return null;
			}else{
				accessCnt++;
				Elements element = checkPage();
				return element;
			}
 		}
	}
	
	public void getGSM(Elements text){
		ArrayList<String> gsmNum = new ArrayList<>();
		
		
		if(checkOverDataFlag == true){
			FileReader fr;
			try {
				fr = new FileReader(ds.getPath()+"/Result/"+ds.getGSE()+".txt");
				BufferedReader br = new BufferedReader(fr);
				
				String line;
				while((line = br.readLine())!= null){
					if(line.contains("!Series_sample_id")){
						String getGSM = line.split("=")[1].replace("" , "");
						gsmNum.add(getGSM);
					}
				}
				
				br.close();
				fr.close();
				
				// Delete GSM File
				File f = new File(ds.getPath()+"/Result/"+ds.getGSE()+".txt");
				f.delete();
			}catch (Exception e) {
				System.out.println("\nError : Unable to read the /Result/"+ds.getGSE()+".txt file due to unknown reasons (Err009)");
				ds.writeLogRun("Error : Unable to read the /Result/"+ds.getGSE()+".txt file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
			} 		
		}else{
			for (Element e : text) {
				if (e.text().length() > 4 && e.text().substring(0, 3).equals("GSM")) {
					String tmp[] = e.text().split(" ");
					if (tmp.length == 1) {
						gsmNum.add(e.text());
					}
				}
			}
		}
		ds.setGSM(gsmNum);
	}
	
	public void getGSMInfo(String gsm,boolean latestGenome){
		// latestGenome : (O)true, (x) false
		checkGsmFlag = true;
		Document doc;
		
		subGSM = gsm;
		
		try {
			doc = Jsoup.connect("http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc=" + gsm).timeout(5000).get();
			Elements text = doc.select("td");
					
			for(Element e : text){
				if(e.text().equals("Title")){
					String str = cf.checkSymbol(e.nextElementSibling().ownText());
					ds.setGSMInfo(0, str);
				}else if(e.text().equals("Organism") || e.text().equals("Organisms")){
					ds.setGSMInfo(1, e.nextElementSibling().text());
				}else if(e.text().equals("Library strategy")){
					ds.setGSMInfo(2, e.nextElementSibling().ownText().toUpperCase());
				}else if(e.text().equals("Instrument model")){
					String tmp[] = e.nextElementSibling().ownText().split(" ");
					ds.setGSMInfo(3, tmp[0]); // Illumina
				}else if(e.text().equals("SRA Experiment")){
					accessCnt2 = 0;
					ds.setGSMInfo(4, newSrrAddress(gsm));
				}
			}
			
			
			if(ds.getGSMInfo()[0] == null){
				//error
				ds.getErrLog().setExistErr("Err002 : Unable to find GSMs (or GSE) : "+ds.getGSE());
				System.out.println(ds.getErrLog().getExistErr());
				checkGsmFlag = false;
				ds.setAnalyzeFlag();
				String tmp[] = {gsm,"Err002"};
				ds.setSubRunLog(tmp);
			}else{
				// check GSM Information. (Organism, Library Strategy, Instrument Model, SRA Url)
				checkGSMInfo();
				// convert Organism to Genome
				ds.setGSMInfo(1, convertGenome(ds.getGSMInfo()[1],latestGenome));
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(accessCnt == 100){
				ds.getErrLog().setAccessPage("Err001 : Unable to access the NCBI : "+gsm);
				System.out.println("Error : Unable to access the NCBI : "+gsm + " (Err001)");
				accessCnt = 0;
				ds.setAnalyzeFlag();
				String tmp[] = {gsm,"Err001"};
				ds.setSubRunLog(tmp);
			}else{
				accessCnt++;
				checkGsmFlag = false;
				getGSMInfo(gsm, latestGenome);
			}
		}
	}
	
	public String convertGenome(String genome, Boolean latestGenome){
		String tmp_Genome = genome;
		
		// Latest Check Box
		if(latestGenome){
			if(tmp_Genome.equals("Homo sapiens")){
				tmp_Genome = "hg38";
			}else if(tmp_Genome.equals("Mus musculus")){
				tmp_Genome = "mm10";
			}else if(tmp_Genome.equals("Drosophila melanogaster")){
				tmp_Genome = "dm6";
			}else if(tmp_Genome.equals("Saccharomyces cerevisiae")){
				tmp_Genome = "sacCer3";
			}else if(tmp_Genome.equals("Canis lupus familiaris")){
				tmp_Genome = "canFam3";
			}else if(tmp_Genome.equals("Arabidopsis thaliana")){
				tmp_Genome = "tair10";
			}else if(tmp_Genome.equals("Danio rerio")){
				tmp_Genome = "danRer10";
			}else if(tmp_Genome.equals("Caenorhabditis elegans")){
				tmp_Genome = "ce11";
			}
		}else{
			if(tmp_Genome.equals("Homo sapiens")){
				tmp_Genome = "hg19";
			}else if(tmp_Genome.equals("Mus musculus")){
				tmp_Genome = "mm9";
			}else if(tmp_Genome.equals("Drosophila melanogaster")){
				tmp_Genome = "dm3";
			}else if(tmp_Genome.equals("Saccharomyces cerevisiae")){
				tmp_Genome = "sacCer3";
			}else if(tmp_Genome.equals("Canis lupus familiaris")){
				tmp_Genome = "canFam3";
			}else if(tmp_Genome.equalsIgnoreCase("Arabidopsis thaliana")){
				tmp_Genome = "tair10";
			}else if(tmp_Genome.equals("Danio rerio")){
				tmp_Genome = "danRer10";
			}else if(tmp_Genome.equals("Caenorhabditis elegans")){
				tmp_Genome = "ce11";
			}
		}
		
		return tmp_Genome;
	}
	

	
	public void checkGSMInfo(){
		String organism[] = {"Homo sapiens","Mus musculus","Drosophila melanogaster","Saccharomyces cerevisiae","Canis lupus familiaris","Arabidopsis thaliana","Danio rerio","Caenorhabditis elegans"};
		String library[] = {"CHIP-SEQ","RNA-SEQ","MEDIP-SEQ","ATAC-SEQ","DNASE-SEQ","MNASE-SEQ"}; 
		boolean tmpFlag = false;
		
		for (int i = 0; i < organism.length; i++) {
			if (ds.getGSMInfo()[1].equals(organism[i])) {
				tmpFlag = true;
				break;
			}
		}
		
		if(tmpFlag == false){
			ds.getErrLog().setGSMInfoErr("Err004 : Unable to analze the data (this organism of NGS data is not currently supported) : "+ds.getGSMInfo()[1]+"(Err004-1)");
			checkGsmFlag = false;
			ds.setAnalyzeFlag();
			String tmp[] = {subGSM,"Err004-1"};
			ds.setSubRunLog(tmp);
			return;
		}
		
		tmpFlag = false;
		for (int j = 0; j < library.length; j++) {
			if(ds.getGSMInfo()[2] == null){
				break;
			}
			if (ds.getGSMInfo()[2].equals(library[j])) {
				tmpFlag = true;
				break;
			}
		}
		if(tmpFlag == false){
			if(ds.getGSMInfo()[2] == null){
				ds.getErrLog().setGSMInfoErr("Err004 : Unable to analze the data (this library strategy of NGS data is not currently supported) : Microarray(Err004-2)");			
			}else{
				ds.getErrLog().setGSMInfoErr("Err004 : Unable to analze the data (this library strategy of NGS data is not currently supported) : "+ds.getGSMInfo()[2]+"(Err004-2)");	
			}
			checkGsmFlag = false;
			ds.setAnalyzeFlag();
			String tmp[] = {subGSM,"Err004-2"};
			ds.setSubRunLog(tmp);
			return;
		}
		
		if(!ds.getGSMInfo()[3].equals("Illumina")){
			ds.getErrLog().setGSMInfoErr("Err004 : Unable to analze the data (this instrument model. of NGS data is not currently supported) : "+ds.getGSMInfo()[3]+"(Err004-3)");
			checkGsmFlag = false;
			ds.setAnalyzeFlag();
			String tmp[] = {subGSM,"Err004-3"};
			ds.setSubRunLog(tmp);
			return;
		}

		if(ds.getGSMInfo()[4].equals("")){
			ds.getErrLog().setGSMInfoErr("Err004 : Unable to access the FTP Address(Err004-4)");
			checkGsmFlag = false;
			ds.setAnalyzeFlag();
			String tmp[] = {subGSM,"Err004-4"};
			ds.setSubRunLog(tmp);
			return;
		}
	}
	
	public static void fileUrlReadAndDownload(String fileAddress, String localFileName, String downloadDir){
		OutputStream outStream = null;
		URLConnection uCon = null;
		InputStream is = null;
		try{
			URL Url;
			byte[] buf;
			int byteRead;
			int byteWritten = 0;
			Url = new URL(fileAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(downloadDir +localFileName));
			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[size];
			while((byteRead = is.read(buf))!= -1){
				outStream.write(buf,0,byteRead);
				byteWritten += byteRead;
			}
		}catch(Exception e){
			//e.printStackTrace();
		}finally{
			try{
				is.close();
				outStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void fileUrlDownload(String fileAddress, String downloadDir, String downloadGsm){
		int slashIndex = fileAddress.lastIndexOf('/');
		int periodIndex = fileAddress.lastIndexOf('.');
		String fileName = downloadGsm+".txt";
		if(periodIndex >= 1 && slashIndex >= 0 && slashIndex < fileAddress.length() -1){
			fileUrlReadAndDownload(fileAddress, fileName, downloadDir);
		}else{
			System.err.println("OverData Download : No such file or directory");
		}
	}
	
	public String newSrrAddress(String gsm){
		String srr_address = "";
		
		try {
			Document doc = Jsoup.connect("https://www.ncbi.nlm.nih.gov/sra/?term="+gsm).timeout(5000).get();
			Elements text = doc.select("td");
			
			for (Element e : text) {
				if (e.text().length() > 2) {
					if (e.text().toString().substring(0, 3).equals("SRR")) {

						String srr = e.text().toString();
						String srrTmp = e.text().toString().substring(0, 6);
						String address_tmp = "/sra/sra-instant/reads/ByRun/sra/SRR/" + srrTmp + "/" + srr + "/" + srr
								+ ".sra";

						if (srr_address.equals("")) {
							srr_address = address_tmp;
						} else {
							srr_address += " " + address_tmp;
						}
					}
				}
			}
			return srr_address;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (accessCnt2 == 100) {
				ds.getErrLog().setAccessPage("Err001 : Unable to access the NCBI : " + gsm);
				System.out.println("Error : Unable to access the NCBI : " + gsm + " (Err001)");
				accessCnt2 = 0;
				ds.setAnalyzeFlag();
				String tmp[] = { gsm, "Err001" };
				ds.setSubRunLog(tmp);
			} else {
				accessCnt2++;
				checkGsmFlag = false;
				newSrrAddress(gsm);
			}
		}
		return srr_address;
	}
	
	public boolean getCheckGSMFlag(){
		return checkGsmFlag;
	}
	public void resetAccessCnt(){
		accessCnt = 0;
	}
}
