import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * DecisionTreeLearning.java - Decision tree learning algorithm for building a decision tree for classifying outcomes of animals
 * Creates a tree by splitting instances by the values of the most important attribute (determined by measuring infogain), 
 * creating subtrees that continue to split until all instances in each leaf node have the same classification
 * Can predict outcomes of new instances by searching through the decision tree until a leaf node (outcome) is reached
 */
public class DecisionTreeLearning {
	
	private DecisionTree decisionTree;
	private String defaultOutcome;
	
	/*
	 * Constructor for DTL
	 */
	public DecisionTreeLearning(){
		this.defaultOutcome = "Unable to classify";
	}
	/*
	 * constructor for DTL with a default outcome
	 */
	public DecisionTreeLearning(DecisionTree tree, String defaultOutcome){
		this.decisionTree = tree;
		this.defaultOutcome = defaultOutcome;
	}
	/*
	 * run DTL algorithm on the data passed in. build decision tree.
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
			
			//for each possible value for the most important attribute
			List<String> values = data.getPossibleAttributeValues(mostImportantAttribute);
			for(String currentVal : values){
				//get new examples (data) list that only takes the examples where the most important attribute = currentvalue
				Data filteredData = data.replicateData(mostImportantAttribute, currentVal);
				//remove that attribute value from the list of attributes for the next round
				List<String> newAttributes = data.removeFrom(attributeNames, mostImportantAttribute);
				DecisionTree subTree = decisionTreeLearning(filteredData, newAttributes, plurality);
				//add a branch to the tree with label for most important attribute = currentvalue and subtree subtree
				tree.newNode(currentVal, subTree);
			}
			return tree;
		}
	}
	/*
	 * Returns the outcome string that occurs the most in the set of instances remaining in the data
	 */
	private String getPluralityValue(Data data) {
		List<String> outcomes = new ArrayList<String>();
		for(Instance i : data.getInstances()){
			outcomes.add(i.getOutcome());
		}
		//find the mode of the outcomes
		HashMap<String, Integer> outcomeCounts = new HashMap<String, Integer>();
		int max = 0;
		List<String> maxElems = new ArrayList<String>();
		for(String outcome : outcomes){
			if(outcomeCounts.containsKey(outcome)){
				//increment count of outcome
				outcomeCounts.put(outcome, outcomeCounts.get(outcome) + 1);
			}
			else{
				outcomeCounts.put(outcome, 1);
			}
			if(outcomeCounts.get(outcome) > max){
				//new mode, reassign max value to the number of current outcome there are in the map
				max = outcomeCounts.get(outcome);
				maxElems.clear();
				maxElems.add(outcome);
			}
			else if(outcomeCounts.get(outcome) == max){
				//tie for current max. add to list - will break ties randomly at the end if list > 1
				maxElems.add(outcome);
			}
		}
		if(maxElems.size() > 1){
			//tie for the mode. break tie randomly
			Random r = new Random();
			int tieBreaker = r.nextInt(maxElems.size());
			return maxElems.get(tieBreaker);
		}
		//else return the only mode
		return maxElems.get(0);
	}
	/*
	 * Return the string name of the most important attribute remaining in the attributeNames based on the
	 * information gain formula calculated on the examples in the data
	 */
	private String getMostImportantAttribute(Data data, List<String> attributeNames) {
		double mostGain = 0.0;
		String mostImportantAttribute = attributeNames.get(0);
		for (String attr : attributeNames) {
			double gain = data.calculateGainFor(attr);
			if (gain > mostGain) {
				mostGain = gain;
				mostImportantAttribute = attr;
			}
		}
		return mostImportantAttribute;
	}
	/*
	 * Train the decision tree on a dataset of instances
	 */
	public void train(Data data) {
        // get attribute names from dataset
		List<String> attributes = data.getAttributeNames();
		//run decision tree learning algorithm
		this.decisionTree = decisionTreeLearning(data, attributes, new DecisionTree(this.defaultOutcome));
	}
	/*
	 * Test the decision tree on a dataset with known outcomes.
	 * Return the accuracy of the decision tree
	 */
	public double test(Data data) {
        int totalNum = 0;
        int correctNum = 0;
        for (Instance i : data.getInstances()) {
            if (i.getOutcome().equals(this.decisionTree.predict(i))) {
            	totalNum++;
            	correctNum++;
            } else {
                totalNum++;
            }
        }
        return (double)correctNum/(double)totalNum;
	}
	/*
	 * Predict the outcome of an instance. return the outcome type as a string
	 */
	public String predict(Instance i) {
		//no trained tree yet
        if (this.decisionTree == null) {
        	throw new RuntimeException("DecisionTreeLearner has not yet been trained with an example set.");
        }
        return this.decisionTree.predict(i);
	}
	/*
	 * return the decision tree created by the DTL algorithm
	 */
	public DecisionTree getDecisionTree(){
		return this.decisionTree;
	}
}
