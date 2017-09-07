package Octopus_Src;

public class DataSet_Err {
	private String accessPageErr;
	private String existErr;
	private String experimentErr;
	private String gsminfoErr;
	private String hddCapacityErr;
	private String asperaErr;
	private boolean stepErr[];
	private String step;
	
	
	public DataSet_Err(){
		initErr();
	}
	
	public void initErr(){
		accessPageErr = "";
		existErr = "";
		experimentErr = "";
		gsminfoErr = "";
		asperaErr = "";
		hddCapacityErr = "";
		stepErr = new boolean[6];
		step = "";
	}
	
	public void setAccessPage(String err){
		accessPageErr = err;
	}
	
	public void setExistErr(String err){
		existErr = err;
	}
	public void setExperimentErr(String err){
		experimentErr = err;
	}
	public void setGSMInfoErr(String err){
		gsminfoErr = err;
	}
	public void setHddCapacity(String err){
		hddCapacityErr = err;
	}
	public void setAsperaErr(String err){
		asperaErr = err;
	}
	public void setStepErr(int idx, boolean flag){
		stepErr[idx] = flag;
	}
	public void setStep(String step){
		this.step = step;
	}
	
	//===================================
	public String getAccessPage(){
		return accessPageErr;
	}
	public String getExistErr(){
		return existErr;
	}
	public String getExperimentErr(){
		return experimentErr;
	}
	public String getGSMInfoErr(){
		return gsminfoErr;
	}
	public String getHddCapacity(){
		return hddCapacityErr;
	}
	public String getAsperaErr(){
		return asperaErr;
	}
	public boolean[] getStepErr(){
		return stepErr;
	}
	public boolean getStepErr(int idx){
		return stepErr[idx];
	}
	public String getStep(){
		return step;
	}
	
}
