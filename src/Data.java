import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * Data.java - Stores a representation of the data that can be used by the DTL algorithm
 * Also includes operations on the data required by the DTL algorithm, 
 * including the attribute information gain calculation
 */
public class Data {
	private List<String> attributeNames;
	
	private Hashtable<String, List<String>> attributesAndTheirValues; //attributes and all the values each attribute can have
	private List<Instance> instances;
	
	private List<String> outcomes;

	public Data(List<String> attributeNames, Hashtable<String, List<String>> attributesAndTheirValues, List<String> outcomes, List<Instance> instances){
		//import all the stuff
		this.attributeNames = attributeNames;
		this.attributesAndTheirValues = attributesAndTheirValues;
		this.outcomes = outcomes;
		this.instances = instances;
	}
	public Data(){
		this.attributeNames = new ArrayList<String>();
		this.attributesAndTheirValues = new Hashtable<String, List<String>>();
		this.instances = new ArrayList<Instance>();
		this.outcomes = new ArrayList<String>();
	}
	public List<Instance> getInstances() {
		return instances;
	}
	public List<String> getPossibleAttributeValues(String attribute){
		return attributesAndTheirValues.get(attribute);
	}
	public Hashtable<String, List<String>> getAttributesAndTheirValues() {
		return attributesAndTheirValues;
	}
	public void setAttributesAndTheirValues(Hashtable<String, List<String>> attributesAndTheirValues) {
		this.attributesAndTheirValues = attributesAndTheirValues;
	}
	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}
	public void setOutcomes(List<String> outcomes) {
		this.outcomes = outcomes;
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
	//get all attributes that aren't the outcome
	public List<String> getNonTargetAttributes() {
		return this.attributeNames;
	}
	//copy over current instances with the matching value for the attribute specified into a new data object
	public Data replicateData(String attribute, String value) {
		Data newData = new Data();
		newData.setAttributeNames(this.getAttributeNames());
		newData.setAttributesAndTheirValues(attributesAndTheirValues);
		newData.setOutcomes(this.getOutcomes());
		for(Instance i : this.instances){
			if(i.getAttributeValue(attribute).equals(value)){
				newData.addInstance(i);
			}
		}
		return newData;
	}
	//create new data object with same list of attributes as previous data but no instances
	public Data newData(){
		Data newData = new Data();
		newData.setAttributeNames(this.getAttributeNames());
		newData.setAttributesAndTheirValues(attributesAndTheirValues);
		newData.setOutcomes(this.getOutcomes());
		return newData;
	}
	/*
	 * Return a new list containing the strings in the old list minus the valueToRemove
	 */
	public List<String> removeFrom(List<String> oldList, String valueToRemove){
		List<String> newList = new ArrayList<String>();
		for(int i = 0; i < oldList.size(); i++){
			if(!oldList.get(i).equals(valueToRemove)){
				newList.add(oldList.get(i));
			}
		}
		return newList;
	}
	/*
	 * return the info gain that particular attribute gives
	 */
	public double calculateGainFor(String attribute) {
		//find entropy before
		double entropyBefore = 0;
		//hashtable will hold the number of instances having each outcome
		Hashtable<String, Integer> countsOfEachOutcome = this.getCountsOfEachOutcome(this.getInstances());
		for (String outcome : countsOfEachOutcome.keySet()){
			//calculate -p(i)*log2(p(i)) for each outcome type, add to sum for entropyBefore
			double probi = (double)countsOfEachOutcome.get(outcome) / (double)instances.size(); //probability of that outcome = #instances with that outcome/#instances in set
			//System.out.println("probability of " + outcome + ": "+ probi);
			entropyBefore += -1*(probi) * (Math.log(probi) / Math.log(2)); //log2(probi) = log10(probi) / log10(2)
		}
		//System.out.println("Entropy before " + attribute + ": " + entropyBefore);
		
		//find entropy after
		Hashtable<String, Data> splitInstancesOnAtrVal = splitByAttributeValue(attribute);
		double sizeBefore = instances.size();
		double weightedAvgChildrenEntropy = 0;
		//for each child group of instances with value "valueOfAttribute" find their entropy(grouped by their value for that attribute)
		for (String valueOfAttribute : splitInstancesOnAtrVal.keySet()) {
			//get the number of instances for each outcome in the child
			Hashtable<String, Integer> numEachOutcome = getCountsOfEachOutcome(splitInstancesOnAtrVal.get(valueOfAttribute).getInstances());
			double childSize = splitInstancesOnAtrVal.get(valueOfAttribute).instances.size();
			double childEntropy = 0;
			for(String outcome : numEachOutcome.keySet()){
				double probi = numEachOutcome.get(outcome) / childSize;
				childEntropy += -1*(probi) * (Math.log(probi) / Math.log(2)); //log2(probi) = log10(probi) / log10(2)
			}
			weightedAvgChildrenEntropy += (childSize/sizeBefore) * childEntropy;
		}
		return entropyBefore - weightedAvgChildrenEntropy;
	}
	/*
	* for each instance, check the value for that attribute. store it with the instances that have the same value
	* for that attribute
	*/
	private Hashtable<String, Data> splitByAttributeValue(String attribute) {
		Hashtable<String, Data> results = new Hashtable<String, Data>();
		for (Instance i : instances) {
			//add the instance with that value to the table
			String val = i.getAttributeValue(attribute);
			//System.out.println("Current attribute " + attribute + " value: " + i.getAttributeValue(attribute));
			if (results.containsKey(val)) {
				results.get(val).addInstance(i);
			} else {
				//if results doesn't have that value yet create a new subdata set for that value's instances and add the first one it saw
				Data ds = newData();
				ds.addInstance(i);
				results.put(val, ds);
			}
		}
		return results;
	}
	
	/*
	 * for each instance, check the outcome, increase count of instances for that outcome
	 */
	private Hashtable<String, Integer> getCountsOfEachOutcome(List<Instance> theseInstances) {
		Hashtable<String, Integer> results = new Hashtable<String, Integer>();
		for (Instance i : theseInstances) {
			//add the instance with that outcome to the table
			String outcome = i.getOutcome();
			//increment count each time it finds a match
			if (results.containsKey(outcome)) {
				results.put(outcome, results.get(outcome) + 1);
			} else {
				//if results doesn't have that outcome yet add it in with a value of 1
				results.put(outcome, 1);
			}
		}
		return results;
	}
	
	
}
