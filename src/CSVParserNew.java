import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/*
 * Mikayla Timm
 * Summer Undergraduate Research Program 2016
 * AnimalShelter project
 * CSVParserNew.java - reads the data from the csv files for the training and testing sets and 
 * converts it into a usable format for the algorithm in the form of many arrays.
 */
public class CSVParserNew {
	private String fileName;
	private final int largeNum = 30000; //initial "size" of dataset. will count actual number of animals as it reads
	private int numAnimals;
	private Boolean isTraining;
	private String hasName[];
	private String outcomeType[];
	private String animalType[];
	private String intactness[];	
	private String sex[];
	private String age[];
	public String breed[];
	public String color[];
	private int ageInDays[];
	private int hour[];
	
	/*
	 * Constructor - pass in the filename containing the csv data and a boolean value 
	 * indicating whether or not the file is training data
	 */
	public CSVParserNew(String fileName, Boolean isTraining){
		this.fileName = fileName;
		this.isTraining = isTraining;
		this.numAnimals = 0;
		hasName = new String[largeNum];
		outcomeType = new String[largeNum];
		animalType = new String[largeNum];
		intactness = new String[largeNum];
		sex = new String[largeNum];
		age = new String[largeNum];
		breed = new String[largeNum];
		color = new String[largeNum];
		ageInDays = new int[largeNum];
		hour = new int[largeNum];
		
	}
	/*
	 * Return a list of Instance objects - each instance is an item in the training set
	 */
	public List<Instance> populateInstances(){
		List<Instance> instances = new ArrayList<Instance>();
		for(int i = 0; i < this.numAnimals; i++){
			Hashtable<String, String> attributeValuePairs = new Hashtable<String, String>();
			attributeValuePairs.put("hasName", hasName[i]);
			attributeValuePairs.put("animalType", animalType[i]);
			attributeValuePairs.put("intactness", intactness[i]);
			attributeValuePairs.put("sex", sex[i]);
			attributeValuePairs.put("age", age[i]);
			attributeValuePairs.put("breed", breed[i]);
			attributeValuePairs.put("color", color[i]);
			Instance in = new Instance(attributeValuePairs);
			
			if(this.isTraining){
				in.setOutcome(this.outcomeType[i]);
			}
			instances.add(in);
		}
		return instances;
	}
	/*
	 * Return a hashtable mapping the name of each attribute to all the possible values that attribute can have 
	 */
	public Hashtable<String, List<String>> getAttributesAndPossibleValues(List<String> attributesToAdd){
		Hashtable<String, List<String>> data = new Hashtable<String, List<String>>();
		if(attributesToAdd.contains("hasName")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				if(!l.contains(this.hasName[i])){
					l.add(this.hasName[i]);
				}
			}
			data.put("hasName", l);
		}
		if(attributesToAdd.contains("animalType")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				if(!l.contains(this.animalType[i])){
					l.add(this.animalType[i]);
				}
			}
			data.put("animalType", l);
		}
		if(attributesToAdd.contains("intactness")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				if(!l.contains(this.intactness[i])){
					l.add(this.intactness[i]);
				}
			}
			data.put("intactness", l);
		}
		if(attributesToAdd.contains("sex")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				
					//System.out.println("sex is " + this.sex[i]);
				
				if(!l.contains(this.sex[i])){
					l.add(this.sex[i]);
				}
			}
			data.put("sex", l);
		}	
		if(attributesToAdd.contains("age")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				if(!l.contains(this.age[i])){
					l.add(this.age[i]);
				}
			}
			data.put("age", l);
		}	
		if(attributesToAdd.contains("breed")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				if(!l.contains(this.breed[i])){
					l.add(this.breed[i]);
				}
			}
			data.put("breed", l);
		}
		if(attributesToAdd.contains("color")){
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < this.numAnimals; i++){
				if(!l.contains(this.color[i])){
					l.add(this.color[i]);
				}
			}
			data.put("color", l);
		}
		return data;
	}
	/*
	 * Get possible outcome values
	 */
	public List<String> getPossibleOutcomeValues(){
		List<String> l = new ArrayList<String>();
		for(int i = 0; i < this.outcomeType.length; i++){
			if(!l.contains(this.outcomeType[i])){
				l.add(this.outcomeType[i]);
			}
		}
		return l;
	}
	/*
	 * getters
	 */
	public String getHasName(int index) {
		return hasName[index];
	}

	public String getOutcomeType(int index) {
		return outcomeType[index];
	}

	public String getAnimalType(int index) {
		return animalType[index];
	}

	public String getIntactness(int index) {
		return intactness[index];
	}

	public String getSex(int index) {
		return sex[index];
	}

	public String getAge(int index) {
		return age[index];
	}
	
	public int getAgeInDays(int index){
		return ageInDays[index];
	}
	
	public String getBreed(int index){
		return breed[index];
	}
	
	public String getColor(int index){
		return color[index];
	}
	
	public int getHour(int index){
		return hour[index];
	}

	public int getNumAnimals() {
		return numAnimals;
	}
	
	/*
	 * Method for reading the CSV, converting the data into a usable format, and saving it in the many arrays in
	 * double format. 
	 */
	public void readCSV(){
		System.out.println("Reading data from " + this.fileName + "...");
		BufferedReader rdr = null;
		String currentLine = "";
		try{
			rdr = new BufferedReader(new FileReader(fileName));
			rdr.readLine(); //skip first line of headers
			while((currentLine = rdr.readLine()) != null){
				//split current line by commas into an array of Strings holding all the attributes of the animal
				String[] animal = currentLine.split(","); 
				storeAnimalData(animal);
				
			}
		}catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(rdr != null){
				try {
					rdr.close(); //close the buffered reader
					System.out.println("num animals: " + numAnimals);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Stores the data for the singular "animal" string array passed in from readCSV
	 * puts the data that has been converted into double format in each of the arrays at the current animal's location
	 * @param animal Line of data taken from the CSV file for that animal. In the form of a string array - each element holds one attribute of the data
	 */
	private void storeAnimalData(String[] animal){
		if(this.isTraining){
			storeTraining(animal);
		}
		else{
			storeTesting(animal);
		}
		this.numAnimals++; //increment number of animals stored
	}

	/**
	 * Store the data for the animal in the training set
	 * Animal string array contains a value for each attribute
	 * @param animal - string array holding a value for each attribute
	 */
	private void storeTraining(String[] animal){
		
		/* String data split up as follows:
		0 = animal ID (not used right now)
		1 = name
		2 = date time 
		3 = outcome type
		4 = outcome subtype (not used right now)
		5 = animal type (cat/dog)
		6 = sex upon outcome (spayed/neutered male/female OR Unknown)
		7 = age upon outcome (# month/months/day/days/year/years) 
		8 = breed not decided on what to do yet
		9 = color not decided on what to do yet
		*/
		if(animal[1].length() < 1){
			hasName[numAnimals] = "NoName"; //animal has no name
		}
		else hasName[numAnimals] = "Name"; //animal has a name
		 
		outcomeType[numAnimals] = animal[3]; //save outcome type 
		animalType[numAnimals] = animal[5]; //save animal type (Cat or Dog)
		intactness[numAnimals] = getIntactness(animal[6]);
		sex[numAnimals] = getSex(animal[6]);
		age[numAnimals] = getAge(animal[7], animalType[numAnimals]);
		breed[numAnimals] = getBreed(animal[8]);
		color[numAnimals] = getColor(animal[9]);
		ageInDays[numAnimals] = getAgeInDays(animal[7]); 
		hour[numAnimals] = getHour(animal[2]); //save hour in the date time
	}
	/**
	 * Store the testing data for the animal in the arrays
	 * @param animal - string array holding a value for each attribute except outcome
	 */
	private void storeTesting(String[] animal){
		/* String data split up as follows:
		0 = animal ID (not used right now)
		1 = name
		2 = date time (not used right now)
		3 = animal type (cat/dog)
		4 = sex upon outcome (spayed/neutered male/female OR Unknown)
		5 = age upon outcome (# month/months/day/days/year/years) 
		6 = breed not decided on what to do yet
		7 = color not decided on what to do yet
		*/
		if(animal[1].length() < 1){
			hasName[numAnimals] = "NoName"; //animal has no name
		}
		else hasName[numAnimals] = "Name"; //animal has a name
		
		animalType[numAnimals] = animal[3].trim(); //save animal type (Cat or Dog)
		intactness[numAnimals] = getIntactness(animal[4]);
		sex[numAnimals] = getSex(animal[4]);
		age[numAnimals] = getAge(animal[5], animalType[numAnimals]);
		breed[numAnimals] = getBreed(animal[6]);
		color[numAnimals] = getColor(animal[7]);
		ageInDays[numAnimals] = getAgeInDays(animal[5]);
		hour[numAnimals] = getHour(animal[2]);
	}
	/**
	 * Converts the sex string from the data into an int representing the intactness.
	 * @param sex Neutered/Intact Male/Female OR Unknown
	 * @return NotIntact, Intact, Unknown
	 */
	private String getIntactness(String sex){
		//Not intact
		//Intact
		//Unknown
		String sexArray[] = sex.trim().split(" "); //split the string into however many words there are. only want first word
		if(sexArray[0].equals("Neutered") || sexArray[0].equals("Spayed")){
			//animal is not intact
			return "NotIntact";
		}
		else if(sexArray[0].equals("Intact")){
			//animal is intact 
			return "Intact"; 
		}
		else{
			return "Unknown"; //Unknown intactness - generally entire sex is unknown in this case
		}
	}
	/**
	 * Converts the sex string from the data into an int representing the sex.
	 * @param sex Neutered/Intact Male/Female OR Unknown
	 * @return Female, Male, Unknown
	 */
	private String getSex(String sex){
		//Female 
		//Male
		//Unknown
		String sexArray[] = sex.trim().split(" "); //split the string into however many words. only want 2nd word.
		try{
			if(sexArray[1] == null){
			//unknown sex, probably. print just for testing
			System.out.println("Unknown sex case: sexArray[0] = [" + sexArray[0] + "]" );
			return "Unknown";
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			return "Unknown";
		}
		if(sexArray[1].equals("Female")){
			//animal is a female 
			return "Female";
		}
		else if(sexArray[1].equals("Male")){
			//animal is male 
			return "Male";
		}
		else{
			//unknown probably
			return "Unknown";
		}
	}
	/**
	 * Converts the age string from the data into an int representing the age group.
	 * @param ageStr In the format of "x y", where x is a number and y is week(s)/day(s)/month(s)/year(s), OR x and y blank for unknown
	 * @param animalType Cat or Dog (affects the age groups)
	 * @return baby, adult, senior, unknown 
	 */
	private String getAge(String ageStr, String animalType){
		//Baby = 0, Adult = 1, Senior = 2, Unknown = 3
		//CATS: 0 < x < 1 baby, 1 < x < 8 adult, 8 < x senior. 
		//DOGS: 0 < x < 1 baby, 1 < x < 6 adult, 6 < x senior.
		String ageArray[] = ageStr.trim().split(" "); //split the string into a number string and a string for day/month/year measure
		if (ageArray.length <= 1){
			//unknown age - there weren't 2 values, return 3
			return "Unknown";
		}
		if(ageArray[1].equals("years") || ageArray[1].equals("year")){
			//animal is at least 1 year old. not a baby
			if(animalType.equals("Cat")){ //if animal is a cat
				if(Integer.parseInt(ageArray[0]) < 8){//if the cat is less than 8 years old but at least 1, it's an adult
					return "Adult";
				}
				else{
					return "Senior"; //cat is at least 8 years old. senior
				}
			}
			else{ //if animal is a dog
				if(Integer.parseInt(ageArray[0]) < 6){ //dog is less than 6 but at least 1, adult
					return "Adult"; 
				}
				else{
					return "Senior"; //dog is at least 6 yrs old. senior
				}
			}
		}
		else{
			return "Baby"; //dog or cat is not yet a year old, still a baby (0)
		}
	}
	
	/**
	 * Get the dominant breed from the breed string
	 * @param breed raw string containing breed information from the file
	 * @return string containing the shortened breed
	 */
	private String getBreed(String breed){
		String newBreed = breed;
		if(breed.contains("/")){
			newBreed = breed.split("/")[0]; //if it's multiple breeds, pick the dominant one
		}
		if(newBreed.contains("Mix")){
			newBreed = newBreed.replace("Mix", "");
		}
		newBreed = newBreed.trim(); //trim whitespace on end
		
		return newBreed;
	}
	
	/**
	 * Get the dominant color from the color string
	 * @param color raw string containing color information from the file
	 * @return string containing the shortened color 
	 */
	private String getColor(String color){
		String newColor = color;
		if(color.contains("/")){
			newColor = color.split("/")[0]; //if multi colored pick the dominant one
		}
		newColor = newColor.trim();
		
		return newColor;
	}
	
	/**
	 * Get the age of the animal in number of days
	 * @param ageStr age string as stored in the csv file
	 * @return numeric value (int) signifying how old the animal is in days
	 */
	private int getAgeInDays(String ageStr){
		String ageArray[] = ageStr.trim().split(" "); //split the string into a number string and a string for day/month/year measure
		if (ageArray.length <= 1){
			//unknown age - just return 0
			return 0;
		}
		int age = Integer.parseInt(ageArray[0]); //get the number in front of the string as the base age
		if(ageArray[1].equals("day") || ageArray[1].equals("days")){
			return age; //don't multiply it by anything
		}
		else if(ageArray[1].equals("week") || ageArray[1].equals("weeks")){
			return age*7; //multiply the number by 7 since it is currently "age" weeks old.
		}
		else if(ageArray[1].equals("month") || ageArray[1].equals("months")){
			return age*31; //multiply by avg of 31 days in a month
		}
		else{
			return age*365; //multiply by avg of 365 days a year
		}
		
	}
	
	/**
	 * Get the hour of the day that the animal met its outcome
	 * @param dateTime raw string from the csv file containing the date and time info
	 * @return the hour of the day the animal was adopted/transferred/etc
	 */
	private int getHour(String dateTime){
		String time = dateTime.split(" ")[1]; //only want second half of the string
		String hour = time.split(":")[0]; //only want first number in hr:min:sec
		return Integer.parseInt(hour);
	}
}
