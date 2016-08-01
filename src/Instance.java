import java.util.Hashtable;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * Instance.java - Stores information about an animal instance, including the attribute values
 * and the outcome of the animal if it is in the training set.
 * Used by the DTL algorithm.
 */
public class Instance {
	//attribute name-value pairs. both stored as strings
	private Hashtable<String, String> attributeValues;
	private String outcome;
	public Instance(Hashtable<String, String> info, String outcome){
		this.attributeValues = info;
		this.outcome = outcome;
	}
	public Instance(Hashtable<String, String> info){
		this.attributeValues = info;
		this.outcome = null;
	}
	/*
	 * allow functionality to set outcome for training set
	 */
	public void setOutcome(String outcome){
		this.outcome = outcome;
	}
	//return attribute value of the attribute passed
	//return as a string
	public String getAttributeValue(String attributeName) {
		return attributeValues.get(attributeName);
	}
	/*
	 * return the outcome of the instance 
	 */
	public String getOutcome(){
		return this.outcome;
	}
	
}
