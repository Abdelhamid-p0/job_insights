package Model;

import java.io.File;
import java.util.Random;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.SerializationHelper;

public class trainedmodel {
    public static void main(String args[]) {
        try { 
            // Load CSV data
            CSVLoader loader = new CSVLoader();
            loader.setSource(new File("C:\\Users\\lenovo\\Documents\\S7 dump\\Java\\Ajax Project\\job_insights\\labeled_data.csv"));
            Instances data = loader.getDataSet();

            // Debug: Print attribute information to verify the data
            System.out.println("Attributes in dataset:");
            for (int i = 0; i < data.numAttributes(); i++) {
                System.out.println("Attribute " + i + ": " + data.attribute(i).name() + " (" + data.attribute(i).type() + ")");
            }

            // Ensure the class attribute (DemandLabel) is set correctly
            data.setClassIndex(data.numAttributes() - 1);  // Assuming 'DemandLabel' is the last column
            System.out.println("Class attribute: " + data.classAttribute().name());

            // Initialize J48 classifier
            J48 j48classifier = new J48();

            // Build the classifier with training data
            j48classifier.buildClassifier(data);

            // Evaluate the model using 10-fold cross-validation
            Evaluation eval = new Evaluation(data);
            eval.crossValidateModel(j48classifier, data, 10, new Random(1));
            System.out.println(eval.toSummaryString("Results:\n", false));

            // Serialize the model to a file
            SerializationHelper.write("C:\\Users\\lenovo\\Documents\\S7 dump\\Java\\Ajax Project\\job_insights\\src\\Model\\weka_model1.model", j48classifier);
            
        } catch (Exception e) {
            System.out.println("Error occurred! " + e.getMessage());
            e.printStackTrace();
        }
    }
}
