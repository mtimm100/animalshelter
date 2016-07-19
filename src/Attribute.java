
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Attribute {
	private String name;
	private String[] possibleValues;
	
	public Attribute(){
		
	}
	//split the data into two "sublists" - one with the attribute included and one without.
	public List<List<Instance>> split(List<Instance> examples) {
		//create new "result" list of data that has the two sublists
		List<List<Instance>> result = new ArrayList<List<Instance>>();

		Map<Boolean, List<Instance>> sublists = examples.parallelStream().collect(Collectors.partitioningBy(example -> belongsTo(example)));
		//if the list that has the attribute contains instances, add that list to our result list
		if(sublists.get(true).size() > 0){
			result.add(sublists.get(true));
		}
		//else add an empty list of instances
		else{
			result.add(new ArrayList<Instance>());
		}
		//if the list that doesn't have the attribute contains instances add that last to our result list
		if(sublists.get(false).size() > 0){
			result.add(sublists.get(false));
		}
		//else add an empty list of instances
		else{
			result.add(new ArrayList<Instance>());
		}
		//return resulting sublists
		return result;
	}
	//return true if data sample contains the feature
	private boolean belongsTo(Instance example) {
		
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
