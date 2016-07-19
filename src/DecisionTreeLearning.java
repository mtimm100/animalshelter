import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

public class DecisionTreeLearning {
	
	private DecisionTree tree;
	private String defaultOutcome;
	
	public DecisionTreeLearning(CSVParserNew csvp){
		
		
	}
	public DecisionTreeLearning(DecisionTree tree, String defaultOutcome){
		this.tree = tree;
		this.defaultOutcome = defaultOutcome;
	}
	/*
	 * returns root node of the decision tree
	 */
	public DecisionTree decisionTreeLearning(Data data, List<String> attributeNames, DecisionTree startingTree){
		//No examples left
		if(data.getNumInstances() == 0){
			return startingTree;
		}
		//examples are homogeneous. all examples have same label - create new leaf node with the outcome
		else if(data.examplesAreHomogeneous()){
			return new DecisionTree(data.getInstanceAt(0).getOutcome());
		}
		//mixed bag of labels and no attributes left. examples have exactly same description but differing labels.
		//happens due to noise or error in data/domain is nondeterministic/don't have an attribute that will distinguish them
		//return leaf with plurality classification of the remaining examples
		else if(attributeNames.isEmpty()){
			return new DecisionTree(getPluralityValue(data));
		}
		else{
			//split on best attribute A
			String mostImportantAttribute = getMostImportantAttribute(data, attributeNames);
			DecisionTree tree = new DecisionTree(mostImportantAttribute);
			DecisionTree plurality = new DecisionTree(getPluralityValue(data));
			
			//GET POSSIBLE ATTRIBUTE VALUES
			List<String> values = data.get
			
		}
		
		
	}
	private String getPluralityValue(Data data) {
		// TODO Auto-generated method stub
		return null;
	}
	private String getMostImportantAttribute(Data data, List<String> attributeNames) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
