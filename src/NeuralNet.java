import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * NeuralNet.java - Sets up multilayer perceptron for classifying the outcomes for the animals
 * Builds classifier and evaluates its accuracy on the training data. Then predicts outcomes for test data.
 * Activated through Tester.java
 * Outputs data to outcomes.arff
 */
public class NeuralNet {
	private MultilayerPerceptron mlp;
	private Instances trainingSet;
	/**
	 * Constructor for neural network
	 * @param trainingSet - the data to be trained on of type Instance (Weka object)
	 * @param learningRate - Learning rate for backpropagation algorithm (between 0-1)
	 * @param momentum - momentum rate for backpropagation algorithm (0-1)
	 * @param trainingTime - training time (number epochs to train through)
	 * @param hiddenLayers - hidden layers to be created for the network. comma separated values of number perceptrons per layer
	 */
	public NeuralNet(Instances trainingSet, double learningRate, double momentum, int trainingTime, String hiddenLayers){
		System.out.println("Setting up neural network...");
		this.trainingSet = trainingSet;
		mlp = new MultilayerPerceptron();
		//set params
		mlp.setLearningRate(learningRate);
		mlp.setMomentum(momentum);
		mlp.setTrainingTime(trainingTime);
		mlp.setHiddenLayers(hiddenLayers); //hiddenLayers is a comma separated string of values, each value is the number of perceptrons in the layer.
		//for 3 perceptrons in 1 layer, pass "3". if 2 hidden layers, one with 4 perceptrons and one with 5, pass "4,5"
		try {
			System.out.println("Building classifier... wait for it...");
			mlp.buildClassifier(trainingSet);
		} catch (Exception e) {
			// failed to build classifier
			System.out.println("Failed to build neural network classifier.");
			e.printStackTrace();
		}
		System.out.println("Neural network is set up.");
	}
	/*
	 * Train the neural network, evaluate the model, and print results
	 */
	public void trainNeuralNet(){
		Evaluation eval;
		try {
			
			eval = new Evaluation(this.trainingSet);
			eval.evaluateModel(mlp, this.trainingSet);
			double[][] confusionMatrix = eval.confusionMatrix();
			
			System.out.println("Neural Network Training Results");
			System.out.println("*******************************");
			//Training root mean squared error
			System.out.println("Root mean squared error: " +eval.errorRate());
			//Summary of training
			System.out.println(eval.toSummaryString());
			System.out.println("\nConfusion Matrix");
			for(int i = 0; i < confusionMatrix.length; i++){
				for(int j = 0; j < confusionMatrix[i].length; j++){
					System.out.print(confusionMatrix[i][j]);
				}
				System.out.println("");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Test neural network on unlabeled data - predict outcomes for the animals in the testing set.
	 * Output the predictions and data to outcomes.arff
	 * @param testingSet Instances object containing all of the animal instances from the test data
	 */
	public void testNeuralNet(Instances testingSet){
		Instances predictedData = new Instances(testingSet);
		System.out.println("testingset: " + testingSet.numInstances());
		for(int i = 0; i < testingSet.numInstances(); i++){
			try {
				double outcome = mlp.classifyInstance(testingSet.instance(i));
				if(outcome == 4.0){
					System.out.println("Outcome for animal ID "+ i + ": " + outcome);
				}
				//will classify it as either 0.0, 1.0, 2.0, 3.0, 4.0
				//System.out.println("Outcome for animal ID "+ i + ": " + outcome);
				predictedData.instance(i).setClassValue(outcome);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		BufferedWriter writer;
		try {
			System.out.println("Writing prediction output to outcomes.arff");
			writer = new BufferedWriter(new FileWriter("outcomes.arff"));
			writer.write(predictedData.toString());
			writer.newLine();
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
