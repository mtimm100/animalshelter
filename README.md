# animalshelter
Background: Every year, animal shelters across the U.S. give approximately 7.6 million animals a chance at finding a forever home and starting a new life (ASPCA 2016). Some of these animals are adopted or  reunited with their owners, while many others face less desirable fates. Certain traits in animals may affect their outcomes at shelters, and knowing which characteristics have the most significance would allow shelters to focus their efforts on the animals who may have a lower chance of getting adopted. 

Goal: Develop a system that will utilize machine learning algorithms to predict the outcomes of animals brought to a shelter as well as gain insight into which characteristics carry the most weight in determining their outcomes.

Algorithms used: Neural Network and Decision Tree Learning
Data on runtime/accuracy/infogain recorded in the xlsx file.
Training and testing data provided from Austin Animal Shelter through Kaggle.com
Outcomes predicted by neural network stored in outcomes.arff

How to run:
Make sure you have Weka.jar in the same directory as the source code - needed for Neural Network

cd to the directory containing all the source code
copy and paste:
javac -cp weka.jar Tester.java CSVParserNew.java NeuralNet.java Weka.java Data.java DecisionTree.java DecisionTreeLearning.java Instance.java
java -cp weka.jar:. Tester

It will then run the program showing the training/testing set size etc information.
The artificial network will then begin running, displaying accuracy of classifications and the elapsed time it took once it finishes. 
This may take a while considering the dataset is large.
Once that finishes, the DTL algorithm will run almost instantaneously, producing an accuracy and elapsed time on the screen.


