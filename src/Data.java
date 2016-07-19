import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class Data {
	private List<String> attributeNames;
	private Hashtable<String, List<String>> attributeValuePairs; //attributes and all the values each attribute can have
	private List<Instance> instances;
	private List<String> outcomes;

	public Data(List<String> attributeNames, Hashtable<String, List<String>> attributeValuePairs, List<String> outcomes, List<Instance> instances){
		//import all the stuff
		this.attributeNames = attributeNames;
		this.attributeValuePairs = attributeValuePairs;
		this.outcomes = outcomes;
		this.instances = instances;
	}
	public Data(){
		this.attributeNames = new ArrayList<String>();
		this.attributeValuePairs = new Hashtable<String, List<String>>();
		this.instances = new ArrayList<Instance>();
		this.outcomes = new ArrayList<String>();
	}
	
	
	public List<String> getAttributeNames() {
		return this.attributeNames;
	}
	public List<String> getOutcomes(){
		return this.outcomes;
	}
	public int getNumInstances() {
		return instances.size();
	}
	
	public Instance getInstanceAt(int i){
		return this.instances.get(i);
	}
	public void addInstance(Instance i){
		this.instances.add(i);
	}
	public Data removeInstance(Instance i){
		Data newData = new Data();
		for(Instance in : instances){
			if(!(i.equals(in))){
				newData.addInstance(in);
			}
		}
		return newData;
	}
	//Return true if all instances have the same classification/outcome (is homogeneous)
	public boolean examplesAreHomogeneous() {
		String outcome1 = this.instances.get(0).getOutcome();
		for(int i = 1; i < this.instances.size(); i++){
			if(!this.instances.get(i).getOutcome().equals(outcome1)){
				return false;
			}
		}
		return true;
	}
	
	
}
