package extract;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class extractfile {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/scrapping"; // Replace with your database URL
        String username = "root"; // Replace with your MySQL username
        String password = ""; // Replace with your MySQL password
        String tableName = "annonce"; // Replace with the table name
        String outputFile = "output.csv"; // Name of the CSV file to save data

        exportDatabaseToCSV(url, username, password, tableName, outputFile);
        organizeAndLabelData(outputFile, "labeled1_data.csv");
    }

    public static void exportDatabaseToCSV(String url, String username, String password, String tableName, String outputFile) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        FileWriter fileWriter = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database.");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + tableName);
            fileWriter = new FileWriter(outputFile);

            // Write column headers
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                fileWriter.append(metaData.getColumnName(i));
                if (i < columnCount) fileWriter.append(",");
            }
            fileWriter.append("\n");

            // Write data rows
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    fileWriter.append(resultSet.getString(i));
                    if (i < columnCount) fileWriter.append(",");
                }
                fileWriter.append("\n");
            }

            System.out.println("Data exported to " + outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                if (fileWriter != null) fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void organizeAndLabelData(String inputFile, String outputFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             FileWriter writer = new FileWriter(outputFile)) {

            // Read the header
            String header = reader.readLine();
            writer.append("Sector,Skill,DemandLabel\n");

            // Group and count skills by sector
            Map<String, Map<String, Long>> groupedData = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String sector = columns[6]; // Assuming "Secteur" is the 7th column
                String softSkills = columns[20]; // Assuming "SoftSkills" is the 21st column
                String personalityTraits = columns[21]; // Assuming "TraitsPersonnalite" is the 22nd column
                String requiredSkills = columns[22]; // Assuming "CompetencesRequises" is the 23rd column

                // Initialize sector in groupedData if not already present
                groupedData.computeIfAbsent(sector, k -> new HashMap<>());

                // Process each skill category and aggregate
                aggregateSkills(softSkills, groupedData.get(sector), "SoftSkills");
                aggregateSkills(personalityTraits, groupedData.get(sector), "TraitsPersonnalite");
                aggregateSkills(requiredSkills, groupedData.get(sector), "CompetencesRequises");
            }

            // Calculate thresholds for labeling
            List<Long> counts = new ArrayList<>();
            for (var sectorData : groupedData.values()) {
                counts.addAll(sectorData.values());
            }
            Collections.sort(counts);
            long lowThreshold = counts.get(counts.size() / 3);
            long highThreshold = counts.get(counts.size() * 2 / 3);

            // Write each sector's skill, count, and label to the output CSV
            for (var entry : groupedData.entrySet()) {
                String sector = entry.getKey();
                Map<String, Long> skills = entry.getValue();

                for (var skillEntry : skills.entrySet()) {
                    String skillWithType = skillEntry.getKey();
                    long count = skillEntry.getValue();
                    String skillType = skillWithType.split(":")[1];
                    String skill = skillWithType.split(":")[0];

                    String label = labelDemand(count, lowThreshold, highThreshold);
                    writer.append(sector).append(",")
                            .append(skill).append(",")
                            .append(label).append("\n");
                }
            }

            System.out.println("Labeled data saved to " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void aggregateSkills(String skillColumn, Map<String, Long> sectorSkills, String skillType) {
        if (skillColumn != null && !skillColumn.isEmpty()) {
            String[] skillList = skillColumn.split(";");
            for (String skill : skillList) {
                String trimmedSkill = skill.trim();
                // Increment the count of the skill type
                sectorSkills.merge(trimmedSkill + ":" + skillType, 1L, Long::sum);
            }
        }
    }

    public static String labelDemand(long count, long lowThreshold, long highThreshold) {
        if (count < lowThreshold) {
            return "Not Very Demanded";
        } else if (count <= highThreshold) {
            return "Normally Demanded";
        } else {
            return "Highly Demanded";
        }
    }
}
