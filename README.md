# animalshelter
Background: Every year, animal shelters across the U.S. give approximately 7.6 million animals a chance at finding a forever home and starting a new life (ASPCA 2016). Some of these animals are adopted or  reunited with their owners, while many others face less desirable fates. Certain traits in animals may affect their outcomes at shelters, and knowing which characteristics have the most significance would allow shelters to focus their efforts on the animals who may have a lower chance of getting adopted. 

Goal: Develop a system that will utilize machine learning algorithms to predict the outcomes of animals brought to a shelter as well as gain insight into which characteristics carry the most weight in determining their outcomes.

Algorithms used: Neural Network and Decision Tree Learning
Data on runtime/accuracy/infogain recorded in the xlsx file.
Training and testing data provided from Austin Animal Center through Kaggle.com
Outcomes predicted by neural network stored in outcomes.arff

How to run:
Make sure you have Weka.jar in the same directory as the source code - needed for Neural Network

cd to the directory containing all the source code
copy and paste:
javac -cp weka.jar Tester.java CSVParserNew.java NeuralNet.java Weka.java Data.java DecisionTree.java DecisionTreeLearning.java Instance.java
java -cp weka.jar:. Tester

It will then run the program showing the training/testing set size etc information.
The DTL and ANN will then run, building classifiers on the training set, then outputting the predicted outcomes to outcomes.arff.

Please see the AnimalShelterPoster.pdf for a graphical analysis of the results.

Findings:
DATA:
Intact animals are significantly less likely to be adopted compared to spayed/neutered animals. Spayed/neutered animals are less likely to be transferred to another shelter - this could be due to the shelter's resources for fixing animals (perhaps they do not have the ability to spay/neuter all of the intact animals, so they transfer them to a shelter that can do it). Intact animals are more likely to be euthanized than spayed/neutered animals. Named animals also had a higher rate of adoption compared to animals that had no name given.

ALGORITHMS:
Increasing the number of epochs reduces the error rate for the neural network. The neural network takes a longer time to run compared to the decision tree learning algorithm, and its runtime grows at a significantly greater rate than the DTL algorithm. The proportion of animal outcomes correctly classified by the neural network plateaus after a hundred or so examples. This could be due to the way I parameterized it, so further experimentation should be done to find an optimal parameterization for this problem. The DTL algorithm continues to improve given more and more examples, though not significantly. Adding decision tree pruning may combat some of the overfitting issues happening with that algorithm. 

Further work includes implementing more machine learning algorithms to compare results, adding the decision tree pruning, and implementing a GUI for viewing visualizations of shelter data and predicting outcomes of new animals. Shelters could use the GUI system for incoming animals to see what new animals may need more help in getting adopted. This includes giving the animal a name and spaying/neutering the animal if it is intact. 

The meaning of the animals' names may also have an effect - for example, naming a cat "Lucifer" (my cat's name) vs. "Tiger" (everyone else's cats' name) may affect people's opinion of the animal. Knowing if this is an important consideration would be useful to shelters, since it would show what types of names may increase the animals' likelihoods of adoption.



