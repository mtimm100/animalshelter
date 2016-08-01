import java.util.Arrays;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * Weka.java - processes the data from the data arrays so it can be used with the Weka data structures
 * Sets up all data structures necessary for using the Weka methods for classifying/evaluating data.
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class Weka {

	private Instances set;
	private Boolean isTraining;
	private int numAttributes;
	private FastVector fvAttributes;
	private String[] uniqueBreeds;
	private String[] uniqueColors;
	
	/*
	 * Constructor for Weka class. Contains all weka-related actions on objects
	 */
	public Weka(int numAttributes, String[] uniqueBreeds, String[] uniqueColors){
		this.numAttributes = numAttributes;
		this.uniqueBreeds = uniqueBreeds;
		this.uniqueColors = uniqueColors;
	}

	/**
	 * Converts the raw data from the csv parser into a usable format for the Weka algorithm
	 * @param csvP CSVParserNew object containing the data
	 * @param isTraining boolean value signifying if the csvP contains training data or not
	 * @return Instances object containing all animal Instances with their corresponding data from the csvp
	 */
	@SuppressWarnings("unchecked") //sorry
	public Instances getSet(CSVParserNew csvP, Boolean isTraining, int numAnimals){
		this.isTraining = isTraining;
		setupAtr();
		this.set = new Instances("Animals", this.fvAttributes, 30000); //30000 is the initial set capacity
		set.setClassIndex(this.numAttributes); //"class" (output) index is the last element in the set, equal to numAttributes
		insertValues(csvP, numAnimals);
		return this.set;
	}
	
	/**
	 * Set up the possible attributes and class values for the data,
	 * including hasName, animalType, intactness, etc. 
	 */
	@SuppressWarnings("unchecked")
	private void setupAtr(){
		//declare attributes used for input
		//hasName
		FastVector fvHasName = new FastVector(2);
		fvHasName.addElement("NoName");
		fvHasName.addElement("Name");
		Attribute hasNameAtr = new Attribute("hasName", fvHasName);
		
		//animalType
		FastVector fvAnimalType = new FastVector(2);
		fvAnimalType.addElement("Cat");
		fvAnimalType.addElement("Dog");
		Attribute animalTypeAtr = new Attribute("animalType", fvAnimalType);
		
		//intactness
		FastVector fvIntactness = new FastVector(3);
		fvIntactness.addElement("NotIntact");
		fvIntactness.addElement("Intact");
		fvIntactness.addElement("Unknown");
		Attribute intactnessAtr = new Attribute("intactness", fvIntactness);
		
		//sex
		FastVector fvSex = new FastVector(3);
		fvSex.addElement("Female");
		fvSex.addElement("Male");
		fvSex.addElement("Unknown");
		Attribute sexAtr = new Attribute("sex", fvSex);
		
		//age
		FastVector fvAge = new FastVector(4);
		fvAge.addElement("Baby");
		fvAge.addElement("Adult");
		fvAge.addElement("Senior");
		fvAge.addElement("Unknown");
		Attribute ageAtr = new Attribute("age", fvAge);
		
		//breed
		FastVector fvBreed = new FastVector(uniqueBreeds.length);
		for(int i = 0; i < uniqueBreeds.length; i++){
			fvBreed.addElement(uniqueBreeds[i]);
		}
		Attribute breedAtr = new Attribute("breed", fvBreed);
		
		//color
		FastVector fvColor = new FastVector(uniqueColors.length);
		for(int i = 0; i < uniqueColors.length; i++){
			fvColor.addElement(uniqueColors[i]);
		}
		Attribute colorAtr = new Attribute("color", fvColor);
		
		//ageInDays
		Attribute ageInDaysAtr = new Attribute("ageInDays");
		
		//hour
		Attribute hourAtr = new Attribute("hour");
		
		//declare class attributes
		FastVector fvClassVal = new FastVector(5);
		fvClassVal.addElement("Adoption");
		fvClassVal.addElement("Return_to_owner");
		fvClassVal.addElement("Transfer");
		fvClassVal.addElement("Euthanasia");
		fvClassVal.addElement("Died");
		Attribute ClassAttribute = new Attribute("outcome", fvClassVal);
		
		//add them to the fvAttributes fastvector
		this.fvAttributes = new FastVector(this.numAttributes+1);
		fvAttributes.addElement(hasNameAtr); //element 0 = hasName
		fvAttributes.addElement(animalTypeAtr); //element 1 = animalType
		fvAttributes.addElement(intactnessAtr); //element 2 = intactness
		//fvAttributes.addElement(sexAtr); //element 3 = sex
		fvAttributes.addElement(ageAtr); //element 4 = age
		fvAttributes.addElement(breedAtr); //element 5 = breed
		//fvAttributes.addElement(colorAtr); //element 6 = color
		fvAttributes.addElement(ageInDaysAtr); //element 7 = ageInDays
		fvAttributes.addElement(hourAtr); //element 8 = hour
		fvAttributes.addElement(ClassAttribute); //element numAttributes = class/outcome
		
	}

	/**
	 * Grab the values from the dataset and insert them into the instances array "set"
	 * @param csvP CSVParserNew object containing all of the arrays of data from the training or testing set
	 */
	private void insertValues(CSVParserNew csvP, int numAnimals){
		//System.out.println("Inserting values into Instances array...");
		for(int i = 0; i < (numAnimals); i++){
			//create the instance of the animal
			int index = 0;
			Instance animal = new DenseInstance(this.numAttributes+1);
			animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getHasName(i));
			animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getAnimalType(i));
			animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getIntactness(i));
			//animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getSex(i));
			animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getAge(i));
			//insert breed and color here
			if(Arrays.asList(this.uniqueBreeds).contains(csvP.getBreed(i))){
				//breed exists in training set
				animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getBreed(i));
			}
			else{
				//breed doesn't exist in training set, save as "unknown"
				//System.out.println("breed unknown: " + csvP.getBreed(i));
				animal.setValue((Attribute)this.fvAttributes.elementAt(index++), "Unknown");
			}
			/*
			if(Arrays.asList(this.uniqueColors).contains(csvP.getColor(i))){
				//color exists in training set
				animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getColor(i));
			}
			else{
				//color doesn't exist in training set, save as "unknown"
				animal.setValue((Attribute)this.fvAttributes.elementAt(index++), "Unknown");
			}*/
			animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getAgeInDays(i));
			animal.setValue((Attribute)this.fvAttributes.elementAt(index++), csvP.getHour(i));
			
			if(this.isTraining){
				animal.setValue((Attribute)this.fvAttributes.elementAt(this.numAttributes), csvP.getOutcomeType(i));
			}
			
			//add the animal to the set
			this.set.add(animal);
		}
		//System.out.println("Finished inserting values into Instances array.");
	}
	
}
