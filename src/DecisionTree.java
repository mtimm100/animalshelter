
import java.util.Hashtable;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * DecisionTree.java - Decision tree data structure for Decision Tree Learning algorithm
 * Stores all of the attribute values in the tree with each leaf representing an outcome type.
 * Created using DTL algorithm. 
 * Can predict outcomes of new instances by branching on the attribute values stored in the tree
 */
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
	//add a new attribute value node to an existing tree
	public void newNode(String attributeName, DecisionTree tree){
		nodes.put(attributeName, tree);
	}
	/*
	 * Search tree through all attribute values of the instance until it reaches the leaf for that set of attribute values
	 */
	public String predict(Instance i) {
		String attributeValue = i.getAttributeValue(attributeName);
		if(attributeValue == null){
			//reached leaf
			return attributeName; //will be the outcome
		}
		if(nodes.containsKey(attributeValue)) {
			if(nodes.get(attributeValue) == null){
				//reached leaf node in tree. return outcome
				System.out.println(attributeName);
				return attributeName;
			}
			//keep going until you reach a leaf
			return nodes.get(attributeValue).predict(i);
		} else {
			throw new RuntimeException("Unknown attribute value "
					+ attributeValue);
		}
	}
	/*
	 * call toString method that keeps track of the current depth for nice indentation/spacing
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return toString(1, new StringBuffer());
	}
	/*
	 * get the attribute value stored in a node
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/*
	 * Create a string representation of the tree - is VERY LARGE for this problem
	 * can be printed out to see structure
	 */
	public String toString(int depth, StringBuffer buf) {
		if (attributeName != null) {
			//indentation determined by current depth in tree
			for(int i = 0; i < depth; i++){
				buf.append("\t");
			}
			buf.append("***");
			// print the attribute value stored in each node
			buf.append( attributeName + " \n");
			for (String attributeValue : nodes.keySet()) {
				for(int i = 0; i < depth+1; i++){
					buf.append("\t");
				}
				buf.append("+"+attributeValue);
				buf.append("\n");
				DecisionTree child = nodes.get(attributeValue);
				buf.append(child.toString(depth + 1, new StringBuffer()));
			}
		}
		return buf.toString();
	}


}
