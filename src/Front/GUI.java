package Front;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class GUI extends Application {

    private boolean isGraphVisible = false; // To manage the visibility of statistics
    private messenger server; // Reference to the server

    @Override
    public void start(Stage primaryStage) {
        try {
            // Connect to the server
            server = (messenger) Naming.lookup("//localhost:1110/Test");
        } catch (Exception e) {
            showError("Error connecting to the server: " + e.getMessage());
            return;
        }

        // Text area for displaying chat messages
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(300);

        // User input field
        TextField userInput = new TextField();
        userInput.setPromptText("Posez votre question ici...");

        // Send button for the chatbot
        Button sendButton = new Button("Envoyer");

        // Graphs (line and pie chart)
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineGraph = new LineChart<>(xAxis, yAxis);
        lineGraph.setTitle("Statistiques Linéaires");
        lineGraph.setPrefHeight(300);
        lineGraph.setVisible(false); // Hidden by default

        PieChart pieChart = new PieChart();
        pieChart.setTitle("Répartition des Statistiques");
        pieChart.setVisible(false); // Hidden by default

        // Button to toggle graph visibility
        Button toggleGraphButton = new Button("▶");
        toggleGraphButton.setStyle("-fx-background-color: #6200ee; -fx-text-fill: white;");
        toggleGraphButton.setOnAction(event -> {
            isGraphVisible = !isGraphVisible;
            animateGraphVisibility(lineGraph, isGraphVisible);
            animateGraphVisibility(pieChart, isGraphVisible);
            toggleGraphButton.setText(isGraphVisible ? "▼" : "▶");
        });

        // Button to access the announcements page
        Button annoncesButton = new Button("Voir les annonces");
        annoncesButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        annoncesButton.setOnAction(event -> showAnnoncesPage());

        // Layout for user inputs
        HBox inputLayout = new HBox(10, userInput, sendButton);
        inputLayout.setPadding(new Insets(10));
        inputLayout.setHgrow(userInput, Priority.ALWAYS);

        // Layout for buttons
        HBox buttonLayout = new HBox(10, toggleGraphButton, annoncesButton);
        buttonLayout.setPadding(new Insets(10));

        // Main layout
        VBox root = new VBox(10, chatArea, inputLayout, buttonLayout, lineGraph, pieChart);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: #f4f4f4;");

        // Action for the send button
        sendButton.setOnAction(event -> {
            String userMessage = userInput.getText();
            if (!userMessage.isEmpty()) {
                chatArea.appendText("Utilisateur: " + userMessage + "\n");
                try {
                    String response = server.sendInput(userMessage);
                    chatArea.appendText("Chatbot: " + response + "\n");
                } catch (RemoteException e) {
                    chatArea.appendText("Chatbot: Error communicating with the server.\n");
                }
                userInput.clear();
            }
        });

        // Scene configuration
        Scene scene = new Scene(root, 700, 600);
        primaryStage.setTitle("Calculatrice Chatbot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to show the announcements page
    private void showAnnoncesPage() {
        Stage annoncesStage = new Stage();
        annoncesStage.setTitle("Annonces");

        // Filters (Ville and Fonction)
        ComboBox<String> villeFilter = new ComboBox<>();
        villeFilter.getItems().addAll("Casablanca", "Rabat", "Fès", "Marrakech","Agadir","Tanger");
        villeFilter.setPromptText("Ville");

        ComboBox<String> fonctionFilter = new ComboBox<>();
        fonctionFilter.getItems().addAll("Test logiciel", "Développement logiciel", "Architecture logicielle", "Gestion de projet","Analyse fonctionnelle");
        fonctionFilter.setPromptText("Fonction");

        Button filterButton = new Button("Filtrer");
        filterButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

        // ListView for displaying announcements
        ListView<String> annoncesList = new ListView<>();

        filterButton.setOnAction(event -> {
            String selectedVille = villeFilter.getValue();
            String selectedFonction = fonctionFilter.getValue();

            try {
                ArrayList<Annonce> annonces = server.filter(selectedFonction, selectedVille);
                annoncesList.getItems().clear();
                for (Annonce annonce : annonces) {
                    annoncesList.getItems().add(annonce.toString1());
                }
            } catch (RemoteException e) {
                showError("Error filtering announcements: " + e.getMessage());
            }
        });

        HBox filterLayout = new HBox(10, villeFilter, fonctionFilter, filterButton);
        filterLayout.setPadding(new Insets(10));

        VBox annoncesLayout = new VBox(10, filterLayout, annoncesList);
        annoncesLayout.setPadding(new Insets(15));

        // Scene configuration
        Scene annoncesScene = new Scene(annoncesLayout, 600, 400);
        annoncesStage.setScene(annoncesScene);
        annoncesStage.show();
    }

    // Error dialog
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method for graph animation
    private void animateGraphVisibility(Region graph, boolean isVisible) {
        FadeTransition fade = new FadeTransition(Duration.millis(500), graph);
        fade.setFromValue(isVisible ? 0.0 : 1.0);
        fade.setToValue(isVisible ? 1.0 : 0.0);
        fade.setOnFinished(event -> graph.setVisible(isVisible));
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
