import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class DecisionTree {
	//tree nodes - string is attribute name
	private Hashtable<String, DecisionTree> nodes;
	private String attributeName;
	
	//each subtree will have an attribute name stored in the root node
	public DecisionTree(String attributeName){
		this.attributeName = attributeName;
		nodes = new Hashtable<String, DecisionTree>();
	}
	
	//new leaf node containing the attribute value and the outcome
	public void newLeaf(String value, String outcome){
		//Nodes will be null because it's at the bottom
		nodes.put(value, null);
	}
	//add a new attribute node to an existing tree
	public void newNode(String attributeName, DecisionTree tree){
		nodes.put(attributeName, tree);
	}
	public Object predict(Instance i) {
		String attributeValue = i.getAttributeValue(attributeName);
		if(nodes.containsKey(attributeValue)) {
			return nodes.get(attributeValue).predict(i);
		} else {
			throw new RuntimeException("Unknown attribute value "
					+ attributeValue);
		}
	}

	public static DecisionTree getStumpFor(Data data, String attributeName, String attributeValue, String returnValueIfMatched, List<String> unmatchedValues, String returnValueIfUnmatched) 
	{
		DecisionTree dt = new DecisionTree(attributeName);
		dt.newLeaf(attributeValue, returnValueIfMatched);
		for (String unmatchedValue : unmatchedValues) {
			dt.newLeaf(unmatchedValue, returnValueIfUnmatched);
		}
		return dt;
	}
	
	public static List<DecisionTree> getStumpsFor(Data data, String returnValueIfMatched, String returnValueIfUnmatched) {
		List<String> attributes = data.getNonTargetAttributes();
		List<DecisionTree> trees = new ArrayList<DecisionTree>();
		for (String attribute : attributes) {
			List<String> values = data.getPossibleAttributeValues(attribute);
			for (String value : values) {
				List<String> unmatchedAttributes = new ArrayList<String>(data.getAttribute)
				//List<String> unmatchedValues = Util.removeFrom(data.getPossibleAttributeValues(attribute), value);

				DecisionTree tree = getStumpFor(data, attribute, value,
						returnValueIfMatched, unmatchedValues,
						returnValueIfUnmatched);
				trees.add(tree);

			}
		}
		return trees;
	}


}
