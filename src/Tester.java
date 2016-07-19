
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Attribute;
import weka.core.Instances;
public class Tester {
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * Tester.java - read in training data, build classifier (neural network), determine most relevant attributes, 
 * predict outcomes for testing data.
 */
	public static void main(String[] args) {
		int NUMATTRIBUTES = 7; //hardcoded in - using hasName, animalType, intactness, sex, age, breed, color, ageInDays, hour right now
		
		//generate training data
		CSVParserNew csvPTraining = new CSVParserNew("train.csv",true);
		csvPTraining.readCSV();
		String[] uniqueBreeds = getUniqueBreeds(csvPTraining);
		String[] uniqueColors = getUniqueColors(csvPTraining);
		Weka weka = new Weka(NUMATTRIBUTES, uniqueBreeds, uniqueColors);
		Instances trainingSet = weka.getSet(csvPTraining, true);
		
		//generate testing set
		CSVParserNew csvPTesting = new CSVParserNew("test.csv", false);
		csvPTesting.readCSV();
		Instances testingSet = weka.getSet(csvPTesting, false);
		
		
		showInfoGains(trainingSet);
		
		NeuralNet NN = new NeuralNet(trainingSet, 0.05, 0.1, 250, "6");
		NN.trainNeuralNet();
		NN.testNeuralNet(testingSet);
		
		List<String> attributeNames = new ArrayList<String>();
		attributeNames.add("hasName");
		attributeNames.add("animalType");
		attributeNames.add("intactness");
		attributeNames.add("sex");
		attributeNames.add("age");
		attributeNames.add("breed");
		attributeNames.add("color");
		Hashtable<String, List<String>> attributeValuePairs = csvPTraining.convertDataToHashDTL(attributeNames);
		
	}
	
	/**
	 * From the breeds array, return a string array of all the unique breeds (no duplicates)
	 * @param csvP CSVParserNew object with the breed data
	 * @return String array containing all of the unique breeds in the data
	 */
	public static String[] getUniqueBreeds(CSVParserNew csvP){
		String[] uniqueBreeds = new HashSet<String>(Arrays.asList(csvP.breed)).toArray(new String[0]);
		uniqueBreeds = Arrays.copyOfRange(uniqueBreeds, 1, uniqueBreeds.length);
		Arrays.sort(uniqueBreeds);

		System.out.println("num unique breeds: "+ uniqueBreeds.length);
		return uniqueBreeds;
	}
	/**
	 * From the color array, return a string array of all the unique colors (no duplicates)
	 * @param csvP CSVParserNew object with the color data
	 * @return String array containing all of the unique colors in the data
	 */
	public static String[] getUniqueColors(CSVParserNew csvP){
		String[] uniqueColors = new HashSet<String>(Arrays.asList(csvP.color)).toArray(new String[0]);
		uniqueColors = Arrays.copyOfRange(uniqueColors, 1, uniqueColors.length);
		Arrays.sort(uniqueColors);

		System.out.println("num unique colors: "+ uniqueColors.length);
		return uniqueColors;
	}

	/**
	 * Show the importance of each attribute in determining the outcome
	 * @param trainingSet Instances object containing all instances of animals in the training set
	 */
	public static void showInfoGains(Instances trainingSet){
		System.out.println("Info Gain for Each Attribute");
		System.out.println("****************************");
		InfoGainAttributeEval infoGainEval = new InfoGainAttributeEval();
		try {
			infoGainEval.buildEvaluator(trainingSet);
			for(int i = 0; i < trainingSet.numAttributes()-1; i++){
				Attribute currentAtr = trainingSet.attribute(i);
			    double infogain = 0;
				try {
					infogain = infoGainEval.evaluateAttribute(i);
					System.out.printf("%-11s: 	%5.4f\n",currentAtr.toString().split(" ")[1], infogain);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
