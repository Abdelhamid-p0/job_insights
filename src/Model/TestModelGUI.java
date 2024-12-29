package Model;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.DenseInstance;
import weka.core.converters.CSVLoader;
import weka.core.converters.ArffSaver;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestModelGUI {
    private static J48 model;
    private static Instances dataset;

    public static void main(String[] args) throws Exception {
        // Load the trained model
        try {
            model = (J48) SerializationHelper.read("weka_model1.model");
            if (model == null) {
                throw new Exception("Model is null.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading model: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Convert CSV to ARFF if needed
        File csvFile = new File("C:\\Users\\lenovo\\Documents\\S7 dump\\Java\\Ajax Project\\job_insights\\labeled_data.csv");
        File arffFile = new File("C:\\Users\\lenovo\\Documents\\S7 dump\\Java\\Ajax Project\\job_insights\\labeled_data.arff");
        if (!arffFile.exists()) {
            convertCSVToARFF(csvFile, arffFile);
        }

        // Load the training dataset structure from the ARFF file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arffFile));
            dataset = new Instances(reader);
            reader.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading ARFF file: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        // Set the class index (the last attribute is the class)
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Create the GUI
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Job Demand Predictor");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new GridLayout(4, 2, 10, 10));

            JLabel sectorLabel = new JLabel("Sector:");
            sectorLabel.setBorder(new EmptyBorder(5, 60, 0, 0));

            JLabel skillLabel = new JLabel("Skill:");
            skillLabel.setBorder(new EmptyBorder(5, 60, 0, 0));

            JTextField sectorField = new JTextField();
            sectorField.setBorder(createRoundedBorder());

            JTextField skillField = new JTextField();
            skillField.setBorder(createRoundedBorder());


            JButton predictButton = new JButton("Predict Demand");
            JLabel resultLabel = new JLabel("Result: ");
            resultLabel.setBorder(new EmptyBorder(0, 10, 0, 0)); // Optional margin for the result label

            frame.add(sectorLabel);
            frame.add(sectorField);
            frame.add(skillLabel);
            frame.add(skillField);
            frame.add(new JLabel()); // Empty space
            frame.add(predictButton);
            frame.add(new JLabel()); // Empty space
            frame.add(resultLabel);

            frame.setVisible(true);

            predictButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String inputSector = sectorField.getText().trim();
                    String inputSkill = skillField.getText().trim();

                    if (inputSector.isEmpty() || inputSkill.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Please fill in both Sector and Skill fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    try {
                        // Create a new instance for prediction
                        Instance newInstance = new DenseInstance(dataset.numAttributes());
                        newInstance.setDataset(dataset);
                        newInstance.setValue(dataset.attribute("Sector"), inputSector);
                        newInstance.setValue(dataset.attribute("Skill"), inputSkill);

                        // Make the prediction
                        double classLabel = model.classifyInstance(newInstance);
                        String predictedClass = dataset.classAttribute().value((int) classLabel);


                        // Display the prediction result
                        // Display the prediction result with "Result" in black and the predicted class in red
                        String resultText = "<html>Result: <span style='color:red;'>" + predictedClass + "</span></html>";
                        resultLabel.setText(resultText); // Set HTML formatted text
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Error during classification: " + ex.getMessage(), "Prediction Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            });

            frame.setVisible(true);
        });
    }

    // Method to create a rounded border for JTextField
    private static Border createRoundedBorder() {
        // Create a rounded border with a 2px line and color (you can change the color)
        return BorderFactory.createLineBorder(Color.GRAY, 2, true);
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
        saver.writeBatch();

        System.out.println("CSV file successfully converted to ARFF!");
    }
}
