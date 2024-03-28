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

public class AnalyticsPage {

    public Scene createAnalyticsPage(Stage primaryStage, User user) {

        primaryStage.setTitle("Analytics Page");


        List<List<String>> datai = DataBaseMethod.browseScrolls("scroll");
        List<ScrollObject> scrollList = ParseHelper.parseScrollData(datai);


        ObservableList<ScrollObject> scrolls = FXCollections.observableArrayList(scrollList);
        TableView<ScrollObject> tableView = new TableView<>(scrolls);



        // Create columns for User properties
        TableColumn<ScrollObject, String> userIDColumn = new TableColumn<>("Scroll ID");
        userIDColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("scrollID"));

        TableColumn<ScrollObject, String> identityColumn = new TableColumn<>("Scroll Name");
        identityColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("name"));

        TableColumn<ScrollObject, String> emailAddressColumn = new TableColumn<>("Upload Date");
        emailAddressColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("date"));

        TableColumn<ScrollObject, String> phoneNumberColumn = new TableColumn<>("Downloads");
        phoneNumberColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("downloadTime"));

        TableColumn<ScrollObject, String> fullnameColumn = new TableColumn<>("Uploads");
        fullnameColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("uploadTime"));

        TableColumn<ScrollObject, String> uploaderIDColumn = new TableColumn<>("Updates");
        uploaderIDColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty("updateTime"));



        // Add columns to the TableView
        tableView.getColumns().addAll(userIDColumn, identityColumn, emailAddressColumn, phoneNumberColumn, fullnameColumn, uploaderIDColumn);

        Button returnButton = new Button("Return");


        Font buttonFont = Font.font(12);
        int buttonWidth = 125;

        returnButton.setFont(buttonFont);
        returnButton.setPrefWidth(buttonWidth);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);



        // Create an HBox to hold the buttons
        HBox buttonBox = new HBox(10);
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


        returnButton.setOnAction(event -> {
            AdminPage adminPage = new AdminPage();
            Scene adminScene = adminPage.createAdminScene(primaryStage, user);
            primaryStage.setScene(adminScene);
        });




        // Create the scene
        Scene scene = new Scene(grid, 800, 600);
        return scene;

    }

}
