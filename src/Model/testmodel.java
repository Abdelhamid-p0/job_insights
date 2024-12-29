package Model;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.converters.CSVLoader;
import weka.core.converters.ArffSaver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Scanner;

public class testmodel {
    public static void main(String[] args) throws Exception {

        // Load the trained model
        J48 model = new J48();
        try {
            model = (J48) SerializationHelper.read("weka_model1.model");
            if (model == null) {
                throw new Exception("Model is null.");
            }
        } catch (Exception e) {
            System.out.println("Error loading model: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if the model couldn't be loaded
        }

        // Convert CSV to ARFF if needed
        File csvFile = new File("C:\\Users\\lenovo\\Documents\\S7 dump\\Java\\Ajax Project\\job_insights\\labeled_data.csv");
        File arffFile = new File("C:\\Users\\lenovo\\Documents\\S7 dump\\Java\\Ajax Project\\job_insights\\labeled_data.arff");
        if (!arffFile.exists()) {
            // Convert CSV to ARFF
            convertCSVToARFF(csvFile, arffFile);
        }

        // Load the training dataset structure from the ARFF file
        Instances dataset = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arffFile));
            dataset = new Instances(reader);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading ARFF file: " + e.getMessage());
            e.printStackTrace();
            return; // Exit if the ARFF file couldn't be loaded
        }

        // Set the class index (the last attribute is the class)
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Get user input for prediction
        String input_sector;
        String input_skill;
        Scanner myObj = new Scanner(System.in);

        System.out.println("Enter Sector:");
        input_sector = myObj.nextLine();

        System.out.println("What skill do you want to evaluate?");
        input_skill = myObj.nextLine();

        // Create a new instance (one row of data, corresponding to sector and skill)
        Instance newInstance = new DenseInstance(dataset.numAttributes()); // Match the number of attributes from the dataset
        newInstance.setDataset(dataset);
        newInstance.setValue(dataset.attribute("Sector"), input_sector); // Set value for 'Sector'
        newInstance.setValue(dataset.attribute("Skill"), input_skill); // Set value for 'Skill'

        // Predict using the trained model
        try {
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

    // Method to convert CSV to ARFF using Weka
    private static void convertCSVToARFF(File csvFile, File arffFile) throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(csvFile);
        Instances data = loader.getDataSet();

        // Save as ARFF file
        ArffSaver saver = new ArffSaver();
        saver.setInstances(data);
        saver.setFile(arffFile);
        saver.writeBatch(); // Saves the ARFF file

        System.out.println("CSV file successfully converted to ARFF!");
    }
}