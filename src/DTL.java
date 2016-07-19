import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DTL {
	
	public Node root;
	
	public DTL(CSVParserNew csvp){
		//root = new Node();
	}
	/*
	 * returns root node of the decision tree
	 */
	public Node decisionTreeLearning(List<Instance> examples, List<Attribute> attributes, List<Instance> parentExamples, int currentDepth){
		
		//no example has been observed for this combination of attribute values. return default value
		//calculated from plurality classification for the node's parent
		if(examples.isEmpty() || currentDepth >= maxDepth){
			//return node with most common output value among the set of examples
			String pluralityClass = getPluralityValue(parentExamples);
			return Node.newLeaf(pluralityClass);
		}
		//examples are homogeneous. all examples have same label - create new leaf node
		else if(getLabel(examples) != null){
			return Node.newLeaf(getLabel(examples));
		}
		//mixed bag of labels and no attributes left. examples have exactly same description but differing labels.
		//happens due to noise or error in data/domain is nondeterministic/don't have an attribute that will distinguish them
		//return plurality classification of the remaining examples
		else if(attributes.isEmpty()){
			String pluralityClass = getPluralityValue(examples);
			return Node.newLeaf(pluralityClass);
		}
		else{
			//split on best attribute A
			Attribute mostImportant = getMostImportant(examples, attributes);
			List<List<Instance>> splitData = mostImportant.split(examples);
			
			//remove the most important attribute from the list
			List<Attribute> newAttributes = attributes.stream().filter(p -> !p.equals(mostImportant)).collect(Collectors.toList());
			
			//create new 'sub'decision tree with current mostImportant attribute as root
			Node node = new Node(mostImportant);
			//for each value Vk of attribute A
			for(List<Instance> subsetExamples : splitData){
				//
				
			}
			
		}
		
		
	}
	private Attribute getMostImportant(ArrayList<Instance> examples, ArrayList<Attribute> attributes) {
		// TODO Auto-generated method stub
		return null;
	}
}