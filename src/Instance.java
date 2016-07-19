import java.util.Hashtable;

public class Instance {
	//attribute name-value pairs. both stored as strings
	private Hashtable<String, String> attributeValues;
	private String outcome;
	public Instance(Hashtable<String, String> info, String outcome){
		this.attributeValues = new Hashtable<String, String>();
		this.outcome = outcome;
	}
	public Instance(Hashtable<String, String> info){
		this.attributeValues = new Hashtable<String, String>();
		this.outcome = null;
	}
	
	//return attribute value of the attribute passed
	//return as a string
	public String getAttributeValue(String attributeName) {
		return attributeValues.get(attributeName);
	}
	
	public String getOutcome(){
		return this.outcome;
	}
	
}
