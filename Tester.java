
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
public class Tester {
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * Tester.java - read in training data, build classifier (neural network), determine most relevant attributes, 
 * predict outcomes for testing data.
 */
	public static void main(String[] args) {
		int NUMATTRIBUTES = 7; //using hasName, animalType, intactness, (NOT sex), age, breed, (NOT color), ageInDays, hour 
		
		//generate training data
		CSVParserNew csvPTraining = new CSVParserNew("train.csv",true);
		csvPTraining.readCSV();
		String[] uniqueBreeds = getUniqueBreeds(csvPTraining);
		String[] uniqueColors = getUniqueColors(csvPTraining);
		Weka weka = new Weka(NUMATTRIBUTES, uniqueBreeds, uniqueColors);
		//generate testing set
		CSVParserNew csvPTesting = new CSVParserNew("test.csv", false);
		csvPTesting.readCSV();
		
		System.out.println("Running ANN*****************");
		/*
		//Train neural network with increasing sizes of learning data
		for (int i = 1; (i*10) <= 1000; i++){
			Instances trainingSet = weka.getSet(csvPTraining, true, i*10);
			//showInfoGains(trainingSet);
			long lStartTime = System.currentTimeMillis();
			NeuralNet NN = new NeuralNet(trainingSet, 0.05, 0.1, 250, "6");
			NN.evalNeuralNet();
			//print elapsed time in ms
			long lEndTime = System.currentTimeMillis();

			long difference = lEndTime - lStartTime;

			//System.out.println(difference);
		}*/
		/*
		//test number of epochs vs. error rate
		for (int i = 1; i < 500; i++){
			Instances trainingSet = weka.getSet(csvPTraining, true, csvPTraining.getNumAnimals());
			NeuralNet NN = new NeuralNet(trainingSet, 0.05, 0.1, i, "6");
			NN.evalNeuralNet();
		}
		Instances trainingSet = weka.getSet(csvPTraining, true, csvPTraining.getNumAnimals());
		showInfoGains(trainingSet);
		NeuralNet NN = new NeuralNet(trainingSet, 0.05, 0.1, 150, "6");
		NN.evalNeuralNet();*/
		Instances trainingSet = weka.getSet(csvPTraining, true, csvPTraining.getNumAnimals());
		long lStartTime = System.currentTimeMillis();
		NeuralNet NN = new NeuralNet(trainingSet, 0.05, 0.1, 150, "6");
		System.out.println("ANN Accuracy: "+ NN.evalNeuralNet());
		//print elapsed time in ms
		long lEndTime = System.currentTimeMillis();

		long difference = lEndTime - lStartTime;

		System.out.println("Elapsed time (ANN): " + difference+ " ms");
		/*
		//test the learning rate vs. error rate
		for(double i = 4; i < 10; i = i+.5){
			NeuralNet NN1 = new NeuralNet(trainingSet, i/10, 0.1, 150, "6");
			NN1.evalNeuralNet();
		}
		/*
		Instances trainingSet = weka.getSet(csvPTraining, true, csvPTraining.getNumAnimals());
		ArffSaver saver = new ArffSaver();
		saver.setInstances(trainingSet);
		try {
			saver.setFile(new File("./data/animals.arff"));
			saver.writeBatch();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		/*
		showInfoGains(trainingSet);
		NeuralNet NN = new NeuralNet(trainingSet, 0.05, 0.1, 250, "6");
		NN.evalNeuralNet();
		*/
		
		/*
		Instances testingSet = weka.getSet(csvPTesting, false, csvPTesting.getNumAnimals());
		NN.testNeuralNet(testingSet);
		*/
		
		System.out.println("Running DTL***************************");
		List<String> attributeNames = new ArrayList<String>();
		attributeNames.add("hasName");
		attributeNames.add("animalType");
		attributeNames.add("intactness");
		attributeNames.add("sex");
		attributeNames.add("age");
		attributeNames.add("breed");
		attributeNames.add("color");
		Hashtable<String, List<String>> attributesAndTheirPossibleValues = csvPTraining.getAttributesAndPossibleValues(attributeNames);
		List<String> outcomes = csvPTraining.getPossibleOutcomeValues();
		List<Instance> instances = csvPTraining.populateInstances();
		//split data with outcomes into 2 sets: training set and testing set
		List<Instance> trainingSetDTL = instances.subList(0, (int)(csvPTraining.getNumAnimals()*.8));
		Data dataTrain = new Data(attributeNames, attributesAndTheirPossibleValues, outcomes, trainingSetDTL);
		
		List<Instance> testingSet = instances.subList((int)(csvPTraining.getNumAnimals()*.8),csvPTraining.getNumAnimals());
		Data dataTest = new Data(attributeNames, attributesAndTheirPossibleValues, outcomes, testingSet);
		lStartTime = System.currentTimeMillis();
		DecisionTreeLearning dtl = new DecisionTreeLearning();
		dtl.train(dataTrain);     
		System.out.println("DTL Accuracy: " + dtl.test(dataTest));
		//print elapsed time in ms
		lEndTime = System.currentTimeMillis();

		difference = lEndTime - lStartTime;

		System.out.println("Elapsed time (DTL): " + difference+ " ms");
		
		/*
		for(int i = 1; (i*10) <= 1000; i++){
			List<Instance> trainingSet1 = instances.subList(0, i*10);
			Data dataTrain = new Data(attributeNames, attributesAndTheirPossibleValues, outcomes, trainingSet1);
			
			List<Instance> testingSet = instances.subList((int)(csvPTraining.getNumAnimals()*.8),csvPTraining.getNumAnimals());
			Data dataTest = new Data(attributeNames, attributesAndTheirPossibleValues, outcomes, testingSet);
			
			long lStartTime = System.currentTimeMillis();
			DecisionTreeLearning dtl = new DecisionTreeLearning();
			dtl.train(dataTrain);     
			dtl.test(dataTest);
			//System.out.println(dtl.test(dataTest));
			//print elapsed time in ms
			long lEndTime = System.currentTimeMillis();

			long difference = lEndTime - lStartTime;

			System.out.println(difference);
		}
		*/
		
		
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

		//System.out.println("num unique breeds: "+ uniqueBreeds.length);
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

		//System.out.println("num unique colors: "+ uniqueColors.length);
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
