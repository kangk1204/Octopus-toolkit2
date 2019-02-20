package Octopus_Src;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	private static String newVersion;
	public static void main(String args[]){

		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
/*                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
*/                
                String version = "2.1.3";
        		System.out.println("[Octopus-toolkit."+version+"] ");
        		newVersion = version;
        				
        		DataSet ds = new DataSet();
        		CommonFunc cf = new CommonFunc(ds);

        		// Check New Octopus-toolkit version
        		if(checkUpdateVersion(version, cf)){
        			int checkResult = JOptionPane.showConfirmDialog(null,"Octopus-toolkit has a new version("+newVersion+").\n Do you want to download new Octopus-toolkit?","Octopus-toolkit ("+version+")", JOptionPane.YES_NO_OPTION);
        			if(checkResult == 0){
        				String newVersionCmd[] = {"wget","http://octopus-toolkit2.readthedocs.io/en/latest/_downloads/Octopus-toolkit.zip","-O","Octopus-toolkit.zip"};
        				String decompressCmd[] = {"unzip","-o","Octopus-toolkit.zip","-d","./"};
        				String mvCmd1[] = {"mv","Octopus-toolkit/Octopus-toolkit.jar","./"};
        				String mvCmd2[] = {"mv","Octopus-toolkit/README.md","./"};
        				cf.shellCmd(newVersionCmd);
        				cf.shellCmd(decompressCmd);
        				cf.shellCmd(mvCmd1);
        				cf.shellCmd(mvCmd2);
        				
        				File f = new File(ds.getPath()+"/Tools/SubTool/makeGraph.R");
        				if(f.exists()){
        					f.delete();
        				}
        				
        				JOptionPane.showMessageDialog(null, "Downloading the latest version of Octopus-toolkit.\nPlease rerun the Octopus-toolkit.", "Octopus-toolkit", JOptionPane.INFORMATION_MESSAGE);
        				System.exit(0);
        			}
        		}
        		
        		// Check tool to analysis 
        		CheckProgram cp;
        		
        		// Start Mint
        		if(checkRequired_OS(ds, cf)){
        			cp = new CheckProgram(ds, cf);
        		}else{
        			System.exit(0);
        		}

           }
        });
		
		
		
	} 
	
	public static boolean checkUpdateVersion(String v, CommonFunc cf){
		
		String versionCmd[] = {"wget","143.248.14.23/Octopus-Sub/Octopus-toolkit_Version.txt","-O","Octopus-toolkit_Version.txt"};
		cf.shellCmd(versionCmd);
		
		FileReader fr;
		try {
			fr = new FileReader("Octopus-toolkit_Version.txt");
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			boolean updateTool = false;
			
			while((line = br.readLine())!= null){
				String tmp[] = line.split("\t");
				newVersion = tmp[0];
				if(v.compareTo(tmp[0]) < 0){
					updateTool = true;
				}				
				break;
			}
			
			fr.close();
			br.close();
			new File("Octopus-toolkit_Version.txt").delete();
			return updateTool;
		
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean checkRequired_OS(DataSet ds, CommonFunc cf){
		// Check OS
		FileWriter fw;
		try {
			fw = new FileWriter("OS_Info.sh");
			fw.write("grep . /etc/*-release");
			fw.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("\nError : Unable to make the OS_Info.sh file due to unknown reasons (Err009)");
			ds.writeLogRun("Error : Unable to make the OS_Info.sh file due to unknown reasons (Err009) <a href=\"http://octopus-toolkit2.readthedocs.io/en/latest/error.html#err-009\">[Err009]</a> \n", true);
		}
		
		String cmd_Os1[] = {"chmod","777","OS_Info.sh"};
		String cmd_Os2[] = {"sh","OS_Info.sh"};
		
		cf.shellCmd(cmd_Os1);
		String result_cmd[] = cf.shellCmd(cmd_Os2).split("\n");
		
		new File("OS_Info.sh").delete();

		System.out.println("[Checking to see if the necessary applications are installed to run the program]");
		
		boolean os_flag = false;
		
		for(int i = 0; i < result_cmd.length; i++){
			if(result_cmd[i].contains("Ubuntu")){
				ds.setOS("Ubuntu");
				os_flag = true;
				break;
			}else if(result_cmd[i].contains("Mint")){ 
				ds.setOS("Mint");
				os_flag = true;
				break;
			}else if(result_cmd[i].contains("Fedora")){
				ds.setOS("Fedora");
				System.out.println("[Fedora takes time to check the requirement.]");
				os_flag = true;
				break;
			}else if(result_cmd[i].contains("CentOS")){
				ds.setOS("CentOS");
				System.out.println("[CentOS takes time to check the requirement.]");
				os_flag = true;
				break;
			}
		}
		
		if(os_flag == false){
			JOptionPane.showMessageDialog(null, "This OS is not supported by the Octopus-toolkit.\nplease contact us using address below.\nE-mail : Octopustoolkit@gmail.com", "Check the OS", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
		
		// Check Required library
		String lib_name = "";
		
		if(ds.getOS().equals("Fedora") || ds.getOS().equals("CentOS") ){
			String fedoraListCmd[] = {"yum","list"};
			String resultList =cf.shellCmd(fedoraListCmd);
			String tmpTable[] = resultList.split("\n");
			ArrayList<String> tmpArray = merge_yum_list(tmpTable);
			String requiredTool[] = {"zlib-devel.x86_64 ","libpng-devel.x86_64 ","ncurses-devel.x86_64 ","gcc-c++.x86_64 ","bzip2-devel.x86_64 ","xz-devel.x86_64 "};
//,"libpng12-devel.x86_64 "

			for(int i = 0; i < requiredTool.length; i++){
				boolean checkFlag = false;
				for(int j = 0; j < tmpArray.size(); j++){
					if(tmpArray.get(j).contains(requiredTool[i])){
						String tmpLine[] = tmpArray.get(j).split(" ");
						if(tmpLine[tmpLine.length-1].charAt(0) == '@'){
							checkFlag = true;
							break;
						}
					}
				}
				
				if(checkFlag == false){
					if (requiredTool[i].equals("zlib-devel.x86_64 ")) {
						lib_name = lib_name + "zlib-devel : sudo yum install zlib-devel.x86_64\n";
					} else if (requiredTool[i].equals("libpng-devel.x86_64 ")) {
						lib_name = lib_name + "libpng-devel : sudo yum install libpng-devel.x86_64\n";
					}else if (requiredTool[i].equals("libpng12-devel.x86_64 ")) {
						lib_name = lib_name + "libpng12-devel : sudo yum install libpng12-devel.x86_64\n";
					}else if (requiredTool[i].equals("ncurses-devel.x86_64 ")) {
						lib_name = lib_name + "ncurses : sudo yum install ncurses-devel.x86_64\n";
					} else if (requiredTool[i].equals("gcc-c++.x86_64 ")) {
						lib_name = lib_name + "gcc : sudo yum install gcc-c++\n";
					} else if (requiredTool[i].equals("bzip2-devel.x86_64 ")) {
						lib_name = lib_name + "bzip2-devel : sudo yum install bzip2-devel\n";
					} else if (requiredTool[i].equals("xz-devel.x86_64 ")) {
						lib_name = lib_name + "xz-devel : sudo yum install xz-devel\n";
					}
				}
			
			}
		}else{
			// Ubuntu, Mint
			String ubuntuListCmd[] = {"dpkg","-l"};
			String resultList =cf.shellCmd(ubuntuListCmd);
			String tmpTable[] = resultList.split("\n");
			String requiredTool[] = {"zlib1g-dev","libpng12-dev","libncurses5-dev","g++","liblzma-dev","libbz2-dev"};
				
			
			// 14.04,16.04 : libpng12-dev / 18.04 : libpng-dev
			if(ds.getOS().equals("Ubuntu")){
				for(int i = 0; i < result_cmd.length; i++){
					if(result_cmd[i].contains("18.04")){
						requiredTool[1] = "libpng-dev";
						ds.setOS_Version("18.04");
						break;
					}
				}
			}
			
			
			for(int i = 0; i < requiredTool.length; i++){
				boolean checkFlag = false;
				for(int j = 0; j < tmpTable.length; j++){
					if(tmpTable[j].contains(requiredTool[i])){
						checkFlag = true;
						break;
					}
				}
				
				if(checkFlag == false){
					if(requiredTool[i].equals("zlib1g-dev")){
						lib_name = lib_name + "zlib : sudo apt-get install zlib1g-dev\n";
					}else if(requiredTool[i].equals("libpng12-dev")){
						lib_name = lib_name + "libpng : sudo apt-get install libpng12-dev\n";
					}else if(requiredTool[i].equals("libpng-dev")){
						lib_name = lib_name + "libpng : sudo apt-get install libpng-dev\n";
					}else if(requiredTool[i].equals("libncurses5-dev")){
						lib_name = lib_name + "libncurses : sudo apt-get install libncurses5-dev\n";
					}else if(requiredTool[i].equals("g++")){
						lib_name = lib_name + "g++ : sudo apt-get install build-essential\n";
					}else if(requiredTool[i].equals("liblzma-dev")){
						lib_name = lib_name + "liblzma : sudo apt-get install liblzma-dev\n";
					}else if(requiredTool[i].equals("libbz2-dev")){
						lib_name = lib_name + "libbz2 : sudo apt-get install libbz2-dev\n";
					}
				}	
			}
		}
		
		if(lib_name.equals("")){
			return true;
		}else{
			JOptionPane.showMessageDialog(null, "Prerequisite tools are not installed. Please use the command below : (Err007-1) \n"+lib_name, "Check required library", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
	}
	public static ArrayList<String> merge_yum_list(String[] list){
		ArrayList<String> tmp = new ArrayList<>();
		
		int idx = 0;
		for(int i = 0; i < list.length; i++){
			
			String tmpLine[] = list[i].split(" ");
			if(tmpLine[0].equals("")){
				String temp = tmp.get(idx-1)+list[i];
				tmp.set(idx-1, temp);
			}else{
				tmp.add(list[i]);
				idx++;
			}
		}
				
		return tmp;
	}
}
