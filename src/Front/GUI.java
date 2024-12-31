package Front;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.tuple.Pair;
import rmi_api.Annonce;
import rmi_api.ResponseRMI;
import  rmi_api.ServicesAPI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI extends Application {

    private boolean isGraphVisible = false; // To manage the visibility of statistics
    private List<Annonce> listeAnnoces; // Reference to the server
    ServicesAPI service;
    ResponseRMI response;
    PieChart pieChart = new PieChart();
    Text description = new Text("Ce graphique montre la répartition des statistiques.") ;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Connexion au registre RMI
            Registry registry = LocateRegistry.getRegistry("localhost", 5002);
            System.out.println("Client Connected with back en 5002");

            // Recherche du service distant
            service = (ServicesAPI) registry.lookup("ServiceAPI");

            System.out.println("Request to getAllAnnonces");

            // Appel du service distant
            ResponseRMI response = service.getAllAnnonces();

            System.out.println("Response to getAllAnnonces: " + response);


            if (response != null && response.body != null && response.body.annonceList != null) {
                // Traitement de la réponse
                listeAnnoces = response.body.annonceList;
                System.out.println("Response status code: " + response.statuscode);
                System.out.println("Total annonces: " + listeAnnoces.size());  // Vérifier la taille des annonces
            } else {
                System.err.println("La réponse est vide ou incorrecte.");
            }
        } catch (RemoteException e) {
            showError("Remote exception: " + e.getMessage());
            e.printStackTrace();
        } catch (NotBoundException e) {
            showError("Service not found in the registry: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showError("General error connecting to the server: " + e.getMessage());
            e.printStackTrace();
        }


        // Text area for displaying chat messages
        VBox chatArea = new VBox();
        chatArea.setSpacing(10);
        chatArea.setPadding(new Insets(10));

        // User input field
        TextField userInput = new TextField();
        userInput.setPromptText("Posez votre question ici...");

        // Send button for the chatbot
        Button sendButton = new Button("Envoyer");

        sendButton.setOnAction(event -> {
            // Get user input
            String userMessage = userInput.getText();

            System.out.println("User: " + userMessage);

            // If user input is not empty
            if (!userMessage.trim().isEmpty()) {
                // Display user message in the chat area
                HBox userBox = new HBox();
                userBox.setSpacing(10);
                userBox.setStyle("-fx-alignment: center-right;");
                Label userLabel = new Label(userMessage);
                userLabel.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10;");
                userBox.getChildren().add(userLabel);
                chatArea.getChildren().add(userBox);

                // Call chatbotQuery() to get a response
                ResponseRMI botResponse = null;
                try {
                    botResponse = chatbootQuerry(userMessage);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Create container for bot response and rating system
                VBox responseContainer = new VBox(5);
                responseContainer.setStyle("-fx-alignment: center-left;");

                // Display bot response in the chat area
                HBox botBox = new HBox();
                botBox.setSpacing(10);
                botBox.setStyle("-fx-alignment: center-left;");

                // Create the label for the response and allow text wrapping
                Label botLabel = new Label(botResponse.body.chatbootResp);
                botLabel.setStyle("-fx-background-color: #6200ee; -fx-text-fill: white; -fx-padding: 10; -fx-background-radius: 10;");
                botLabel.setWrapText(true);
                botLabel.setMaxWidth(400);

                botBox.getChildren().add(botLabel);

                // Create rating system
                HBox ratingBox = new HBox(5);
                ratingBox.setStyle("-fx-padding: 5 0 0 10;");

                ToggleButton[] stars = new ToggleButton[5];
                int[] currentRating = {0};

                // Create star buttons
                for (int i = 0; i < 5; i++) {
                    final int starValue = i + 1;
                    ToggleButton star = new ToggleButton("☆");
                    star.setStyle("""
                -fx-background-color: transparent; 
                -fx-text-fill: #FFD700; 
                -fx-font-size: 16px;
                -fx-min-width: 30px;
                -fx-min-height: 30px;
                -fx-padding: 0;
                -fx-cursor: hand;
            """);

                    star.setOnAction(e -> {
                        currentRating[0] = starValue;
                        // Update stars appearance
                        for (int j = 0; j < 5; j++) {
                            stars[j].setText(j < starValue ? "★" : "☆");
                        }
                    });

                    // Hover effect
                    star.setOnMouseEntered(e -> {
                        if (!star.isDisabled()) {
                            for (int j = 0; j < starValue; j++) {
                                stars[j].setText("★");
                            }
                        }
                    });

                    star.setOnMouseExited(e -> {
                        if (!star.isDisabled()) {
                            for (int j = 0; j < 5; j++) {
                                stars[j].setText(j < currentRating[0] ? "★" : "☆");
                            }
                        }
                    });

                    stars[i] = star;
                    ratingBox.getChildren().add(star);
                }

                // Create submit button
                Button submitRating = new Button("Envoyer");
                submitRating.setStyle("""
            -fx-background-color: #4CAF50; 
            -fx-text-fill: white;
            -fx-padding: 5 10;
            -fx-cursor: hand;
            -fx-background-radius: 5;
        """);

                submitRating.setOnAction(e -> {
                    try {
                        // Send rating to server (you'll need to implement this)
                        // Envoyer la note au serveur
                        System.out.println(currentRating[0]);
                        try {
                            System.out.println("Accé au rmi");
                            service.submitRating(currentRating[0]);

                        } catch (RemoteException ex) {
                            throw new RuntimeException(ex);
                        }

                        // Disable rating system after submission
                        for (ToggleButton star : stars) {
                            star.setDisable(true);
                        }
                        submitRating.setDisable(true);
                        submitRating.setText("Merci ✓");
                        submitRating.setStyle(submitRating.getStyle() + "-fx-background-color: #45a049;");

                    } catch (Exception ex) {
                        showError("Erreur lors de l'envoi de la note: " + ex.getMessage());
                    }
                });

                ratingBox.getChildren().add(submitRating);

                // Add response and rating system to container
                responseContainer.getChildren().addAll(botBox, ratingBox);

                // Add container to chat area
                chatArea.getChildren().add(responseContainer);

                // Clear the user input field
                userInput.clear();
            }
        });
        // Layout for user inputs
        HBox inputLayout = new HBox(10, userInput, sendButton);
        inputLayout.setPadding(new Insets(10));
        inputLayout.setHgrow(userInput, Priority.ALWAYS);

        // Button to toggle graph visibility
        Button toggleGraphButton = new Button("▶");
        toggleGraphButton.setStyle("-fx-background-color: #6200ee; -fx-text-fill: white;");
        toggleGraphButton.setOnAction(event -> {
            isGraphVisible = !isGraphVisible;
            animateGraphVisibility(pieChart, isGraphVisible);
            toggleGraphButton.setText(isGraphVisible ? "▼" : "▶");
        });

        // Button to access the announcements page
        Button annoncesButton = new Button("Voir les annonces");
        annoncesButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white;");
        annoncesButton.setOnAction(event -> showAnnoncesPage());

        // Layout for buttons
        HBox buttonLayout = new HBox(10, toggleGraphButton, annoncesButton);
        buttonLayout.setPadding(new Insets(10));

        // Main layout
        VBox mainContent = new VBox(10, chatArea, inputLayout, buttonLayout, pieChart);
        mainContent.setPadding(new Insets(15));
        mainContent.setStyle("-fx-background-color: #f4f4f4;");

        // Wrapping main layout in a ScrollPane for scrollability
        ScrollPane scrollPane = new ScrollPane(mainContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Scene configuration
        Scene scene = new Scene(scrollPane, 800, 600);
        primaryStage.setTitle("Job_insights Chatboot");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private ResponseRMI chatbootQuerry(String input) throws RemoteException, SQLException {

        // Log the request
        System.out.println("Request to processUserQuery");

        // Process the query and get the response
        response = service.processUserQuery(input);
        System.out.println("Response to processUserQuery: " + response);

        // Get the annonce list (if needed)
        listeAnnoces = response.body.annonceList;

        description = new Text(response.body.chatbootResp);

        // Clear the existing data from the PieChart before adding new data
        pieChart.getData().clear();  // Clears the old data
        // Add new data to the PieChart
        pieChart.getData().addAll(convertToPieChartData(response.body.Statistics));

        return response;
    }

    private ArrayList<PieChart.Data> convertToPieChartData(ArrayList<Pair<String, Integer>> statisticsData) {
        ArrayList<PieChart.Data> pieChartData = new ArrayList<>();
        for (Pair<String, Integer> entry : statisticsData) {
            // Create PieChart.Data for each entry in the statistics list
            PieChart.Data slice = new PieChart.Data(entry.getKey(), entry.getValue());
            pieChartData.add(slice);
        }
        return pieChartData;
    }


            public static String extractPercentage (String inputText){
                // Expression régulière mise à jour pour capturer le nom de la ville ou secteur et le pourcentage
                String regex = ".*?((?:\\D+?\\s?)?\\w+?)\\s*est\\s*:\\s*(\\d+).*"; // Capture ville/secteur et pourcentage
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(inputText);

                // Vérifier si l'expression régulière correspond à quelque chose
                if (matcher.find()) {
                    // Extraire la ville/secteur et le pourcentage
                    String cityOrSector = matcher.group(1).trim();  // Ville/Secteur (ex: Rabat, Informatique)
                    String percentage = matcher.group(2); // Pourcentage (ex: 13)

                    // Retourner le résultat formaté
                    return "Le pourcentage des offres dans ce " + cityOrSector + " est : " + percentage + " %";
                } else {
                    // Si aucun match, retourner une chaîne indiquant l'absence de données
                    return "Ce graphique montre la répartition des statistiques.";
                }
            }


            // Method to show the announcements page
            private void showAnnoncesPage () {
                Stage annoncesStage = new Stage();
                annoncesStage.setTitle("Annonces");

                // Filters (Ville and Fonction)
                ComboBox<String> villeFilter = new ComboBox<>();
                villeFilter.getItems().addAll("Casablanca", "Rabat", "Fès", "Marrakech", "Agadir", "Tanger");
                villeFilter.setPromptText("Ville");

                ComboBox<String> fonctionFilter = new ComboBox<>();
                fonctionFilter.getItems().addAll("Test logiciel", "Développement logiciel", "Architecture logicielle", "Gestion de projet", "Analyse fonctionnelle");
                fonctionFilter.setPromptText("Fonction");

                Button filterButton = new Button("Filtrer");
                filterButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");

                // ListView for displaying announcements
                ListView<Annonce> annoncesList = new ListView<>();
                annoncesList.getItems().addAll(listeAnnoces);

                // Custom cell factory for concise display and clickable link
                annoncesList.setCellFactory(listView -> new ListCell<>() {
                    @Override
                    protected void updateItem(Annonce annonce, boolean empty) {
                        super.updateItem(annonce, empty);
                        if (empty || annonce == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            // Create concise display text
                            String displayText = String.format("Titre: %s\nVille: %s\nSecteur: %s\nFonction: %s",
                                    annonce.getTitle(), annonce.getCity(), annonce.getSecteur(), annonce.getFonction());

                            // Create a hyperlink for the URL
                            // Create a hyperlink for the URL
                            Hyperlink urlLink = new Hyperlink(annonce.getUrl());
                            urlLink.setOnAction(e -> getHostServices().showDocument(annonce.getUrl()));

                            // Combine display text and hyperlink in a VBox
                            VBox content = new VBox(new Label(displayText), urlLink);
                            content.setSpacing(5);
                            setGraphic(content);
                        }
                    }
                });

                // Layout for filters and list
                HBox filtersLayout = new HBox(10, villeFilter, fonctionFilter, filterButton);
                filtersLayout.setPadding(new Insets(10));

                VBox annoncesLayout = new VBox(10, filtersLayout, annoncesList);
                annoncesLayout.setPadding(new Insets(10));

                // Scene configuration
                Scene annoncesScene = new Scene(annoncesLayout, 600, 400);
                annoncesStage.setScene(annoncesScene);
                annoncesStage.show();
            }

            private void animateGraphVisibility (Region graph,boolean isVisible){
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), graph);
                fadeTransition.setFromValue(isVisible ? 0 : 1);
                fadeTransition.setToValue(isVisible ? 1 : 0);
                fadeTransition.setOnFinished(e -> graph.setVisible(isVisible));
                fadeTransition.play();
            }

            private void showError (String errorMessage){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Une erreur s'est produite");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            }

    // Dans la classe GUI, ajoutez cette méthode pour créer le système d'étoiles
    private HBox createRatingSystem() {
        HBox ratingBox = new HBox(5);
        ratingBox.setStyle("-fx-padding: 5 0 0 10;"); // Ajoute un peu d'espace au-dessus des étoiles

        ToggleButton[] stars = new ToggleButton[5];
        int[] currentRating = {0}; // Pour stocker la note actuelle

        for (int i = 0; i < 5; i++) {
            final int starValue = i + 1;
            ToggleButton star = new ToggleButton("☆");
            star.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFD700; -fx-font-size: 16px;");

            star.setOnAction(e -> {
                currentRating[0] = starValue;
                // Met à jour l'apparence des étoiles
                for (int j = 0; j < 5; j++) {
                    stars[j].setText(j < starValue ? "★" : "☆");
                }
            });

            stars[i] = star;
            ratingBox.getChildren().add(star);
        }

        // Ajouter le bouton Envoyer
        Button submitButton = new Button("Envoyer");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        submitButton.setOnAction(e -> {
            // Envoyer la note au serveur
            System.out.println(currentRating[0]);
            try {
                System.out.println("Accé au rmi");
                service.submitRating(currentRating[0]);

            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }

            // Désactiver le système de notation après l'envoi
            for (ToggleButton star : stars) {
                star.setDisable(true);
            }
            submitButton.setDisable(true);

            // Feedback visuel optionnel
            submitButton.setText("Envoyé ✓");
        });

        ratingBox.getChildren().add(submitButton);
        return ratingBox;
    }

    public static void main (String[]args){
                launch(args);
            }
        }



