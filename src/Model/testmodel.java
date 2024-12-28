package Model;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Attribute;
import weka.core.DenseInstance;

import java.util.Scanner;
import java.util.ArrayList;

public class testmodel {
    public static void main(String[] args) throws Exception {
       
    	J48 model = new J48();
    	try {
    	    model = (J48) SerializationHelper.read("C:\\Users\\ayaes\\eclipse-workspace\\Model\\weka_model1.model");
    	    if (model == null) {
    	        throw new Exception("Model is null.");
    	    }
    	} catch (Exception e) {
    	    System.out.println("Error loading model: " + e.getMessage());
    	    e.printStackTrace();
    	}
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("Sector", (ArrayList<String>) null)); 
        attributes.add(new Attribute("Skill", (ArrayList<String>) null)); 
        
        ArrayList<String> demand = new ArrayList<>();
        demand.add("Highly Demanded");
        demand.add("Normally Demanded");
        demand.add("Not Very Demanded");
        Attribute classAttribute = new Attribute("DemandLabel", demand); 
        
        attributes.add(classAttribute);
        Instances dataset = new Instances("SkillDemandData", attributes, 0);//entire data
        System.out.println(dataset);
        for (int i = 0; i < dataset.numAttributes(); i++) {
            System.out.println("Attribute " + i + ": " + dataset.attribute(i).name());
        }

        dataset.setClassIndex(dataset.numAttributes() - 1); //target

        String input_sector;
        String input_skill;
        Scanner myObj = new Scanner(System.in);
        
        System.out.println("Enter Sector:");
        input_sector = myObj.nextLine();
        
        System.out.println("What skill do you want to evaluate?");
        input_skill = myObj.nextLine();

        // Create a new instance (one row of data, corresponding to sector and skill)
        Instance newInstance = new DenseInstance(3); // 3 attributes in total: sector, skill, demandLabel
        newInstance.setDataset(dataset);
        newInstance.setValue(dataset.attribute("Sector"), input_sector); // Set value for 'Sector'
        newInstance.setValue(dataset.attribute("Skill"), input_skill); // Set value for 'Skill'
        System.out.println(newInstance);
        // Add this new instance to the dataset 
        // its one row in the data
        
        // Predict using the trained model
        try {
            // Associate the new instance with the dataset
            //newInstance.setDataset(dataset); // Ensure the instance is linked to the dataset
            
            // Make prediction
            double classLabel = model.classifyInstance(newInstance); // Predict class for the newInstance

            // Output the predicted class label
            String predictedClass = dataset.classAttribute().value((int) classLabel); // Convert numeric label to string
            System.out.println("Predicted demand for " + input_skill + " in " + input_sector + ": " + predictedClass);
        } catch (Exception e) {
            System.out.println("Error during classification: " + e.getMessage());
            e.printStackTrace();
        }
        myObj.close();
    }
}
