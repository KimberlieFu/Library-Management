package groupProject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;


public class BrowseUserPage {


    public Scene createBrowseUserPage(Stage primaryStage, User user) {
        primaryStage.setTitle("Browse User Page");


        String data = DataBaseMethod.browseUsers("user");

        // Parse the data into a list of users
        List<User> userList = ParseHelper.parseUserData(data);



        // Create an ObservableList of users
        ObservableList<User> users = FXCollections.observableArrayList(userList);
        TableView<User> tableView = new TableView<>(users);



        // Create columns for User properties
        TableColumn<User, String> userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("userID"));

        TableColumn<User, String> identityColumn = new TableColumn<>("Identity");
        identityColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("identity"));

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("username"));

        TableColumn<User, String> emailAddressColumn = new TableColumn<>("Email Address");
        emailAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("emailAddress"));

        TableColumn<User, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("phoneNumber"));

        TableColumn<User, String> fullnameColumn = new TableColumn<>("Full Name");
        fullnameColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("fullname"));



        // Add columns to the TableView
        tableView.getColumns().addAll(userIDColumn, identityColumn, usernameColumn, emailAddressColumn, phoneNumberColumn, fullnameColumn);

        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button returnButton = new Button("Return");


        Font buttonFont = Font.font(12);
        int buttonWidth = 125;

        updateButton.setFont(buttonFont);
        updateButton.setPrefWidth(buttonWidth);

        deleteButton.setFont(buttonFont);
        deleteButton.setPrefWidth(buttonWidth);

        returnButton.setFont(buttonFont);
        returnButton.setPrefWidth(buttonWidth);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);



        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(updateButton, deleteButton);
        buttonBox.getChildren().addAll(spacer, returnButton);


        // Create a background image
        Image backgroundImage = new Image(getClass().getResource("/admin_page.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );





        // Create a VBox to hold the TableView
        VBox vbox = new VBox(tableView);

        // Create a ScrollPane to make the table scrollable
        ScrollPane scrollPane = new ScrollPane(vbox);
        tableView.setPrefHeight(500);
        tableView.setPrefWidth(775);

        // Set padding around the ScrollPane using a GridPane
        GridPane grid = new GridPane();
        grid.setBackground(new Background(background));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.add(scrollPane, 0, 0);
        grid.add(buttonBox, 0, 1);


        Label errorMessageLabel = new Label("");
        errorMessageLabel.setFont(Font.font(16));
        errorMessageLabel.setTextFill(Color.RED);
        grid.add(errorMessageLabel, 0, 3);


        updateButton.setOnAction(event -> {
            errorMessageLabel.setText("");
            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {

                ModifyPage modifyPage = new ModifyPage();
                Scene modifyScene = modifyPage.createModifyScene(primaryStage, selectedUser, user);
                primaryStage.setScene(modifyScene);


            } else {
                errorMessageLabel.setText("No selected user.");
            }
        });

        returnButton.setOnAction(event -> {
            AdminPage adminPage = new AdminPage();
            Scene adminScene = adminPage.createAdminScene(primaryStage, user);
            primaryStage.setScene(adminScene);
        });


        deleteButton.setOnAction(event -> {
            errorMessageLabel.setText("");
            User selectedUser = tableView.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {

                if (selectedUser.getIdentity().equals("Admin")) {
                    errorMessageLabel.setText("Can not remove admin.");

                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Delete User");
                    alert.setContentText("Are you sure you want to delete this user?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Remove the selected user from the list
                        users.remove(selectedUser);
                        DataBaseMethod.delData(null, "User", "userID", selectedUser.getUserID());
                        // Refresh the TableView
                        tableView.refresh();
                    }

                }


            } else {
                errorMessageLabel.setText("No selected user.");
            }
        });





        // Create the scene
        Scene scene = new Scene(grid, 800, 600);
        return scene;
    }




}