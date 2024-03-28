package groupProject2;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;


public class LibraryPage {
    private ListView<String> scrollListView;
    private TextField searchField;

    public Scene createLibraryScene(Stage primaryStage, User user) {
        primaryStage.setTitle("Library Page");
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        Text titleText = new Text("Library Scrolls");
        titleText.setFont(Font.font(24));

        searchField = new TextField();
        searchField.setPromptText("Search scrolls");

        scrollListView = new ListView<>();
        for(String s : DataBaseMethod.searchAllData("scroll", "name")){
            scrollListView.getItems().addAll(s);
        }

        scrollListView.setPrefSize(740, 5000);

        ScrollPane scrollPane = new ScrollPane(scrollListView);
        scrollPane.setPrefSize(600, 450);

        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Search Scrolls");
        Button searchButton = new Button("Search");
        searchBox.getChildren().addAll(searchField, searchButton);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        Button browseButton = new Button("Browse");
        Button addScrollButton = new Button("Add Scroll");
        Button backButton = new Button("Return");

        Font buttonFont = Font.font(12);
        int buttonWidth = 125;
        browseButton.setFont(buttonFont);
        browseButton.setPrefWidth(buttonWidth);
        backButton.setFont(buttonFont);
        backButton.setPrefWidth(buttonWidth);
        addScrollButton.setFont(buttonFont);
        addScrollButton.setPrefWidth(buttonWidth);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        vBox.getChildren().addAll(titleText, searchBox, scrollPane, buttonBox);


        if (user.getUserID() != null) {
            buttonBox.getChildren().addAll(browseButton, addScrollButton, leftSpacer, rightSpacer, backButton);
        } else {
            buttonBox.getChildren().addAll(browseButton, leftSpacer, rightSpacer, backButton);
        }



        // Event handling for the buttons
        browseButton.setOnAction(event -> {
            String selectedScroll = scrollListView.getSelectionModel().getSelectedItem();
            if (selectedScroll != null) {
                String scrollID = DataBaseMethod.searchSpecificData( "Scroll", "ScrollID", "Name", selectedScroll);
                String uploaderID = DataBaseMethod.searchSpecificData( "Scroll", "uploaderID", "Name", selectedScroll);
                File content = DataBaseMethod.getBlobAsFile("Scroll", "content", "uploaderID", user.getUserID());
                ScrollObject scroll = new ScrollObject(scrollID, selectedScroll, uploaderID, content);

                ScrollPage scrollPage = new ScrollPage();
                Scene scrollScene = scrollPage.createScrollScene(primaryStage, user, scroll, false);
                primaryStage.setScene(scrollScene);

            } else {
                // Handle no scroll selected
            }
        });


        backButton.setOnAction(event -> {
            if (user.getIdentity() == null) {

                MainPage mainPage = new MainPage();
                Scene mainScene = mainPage.createMainScene(primaryStage);
                primaryStage.setScene(mainScene);

            } else if (user.getIdentity().equals("Admin")) {

                AdminPage adminPage = new AdminPage();
                Scene adminScene = adminPage.createAdminScene(primaryStage, user);
                primaryStage.setScene(adminScene);

            }

            else {

                CustomerPage customerPage = new CustomerPage();
                Scene customerScene = customerPage.createCustomerScene(primaryStage, user);
                primaryStage.setScene(customerScene);

            }


        });


        searchButton.setOnAction(event -> {
            scrollListView.getItems().clear();
            if (searchField.getText().isEmpty()) {
                for(String s : DataBaseMethod.searchAllData("scroll", "name")){
                    scrollListView.getItems().addAll(s);
                }

            } else {


                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "name", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "name", searchField.getText())) {
                        scrollListView.getItems().addAll(s);
                    }
                }
                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploaderID", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploaderID", searchField.getText())) {
                        scrollListView.getItems().addAll(s);
                    }

                }
                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "scrollID", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "scrollID", searchField.getText())) {
                        scrollListView.getItems().addAll(s);
                    }

                }
                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploadDate", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploadDate", searchField.getText())) {
                        scrollListView.getItems().addAll(s);
                    }


                }


                if (scrollListView.getItems().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Result");
                    alert.setHeaderText(null);
                    alert.setContentText("No results found for your search.");

                    alert.showAndWait();

                    for (String s : DataBaseMethod.searchAllData("scroll", "name")) {
                        scrollListView.getItems().addAll(s);
                    }
                }

            }

            HashSet<String> hashset = new HashSet<>(scrollListView.getItems());
            List<String> newlist = new ArrayList<>(hashset);
            scrollListView.getItems().clear();
            for(String s : newlist){
                scrollListView.getItems().addAll(s);
            }
        });


        addScrollButton.setOnAction(event -> {

            ScrollPage scrollPage = new ScrollPage();
            Scene scrollScene = scrollPage.createAddScrollScene(primaryStage, user, false);
            primaryStage.setScene(scrollScene);

        });

        Image backgroundImage = new Image(getClass().getResource("/main_page.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        vBox.setBackground(new Background(background));

        return new Scene(vBox, 800, 600);
    }


    public Scene createMyScrollScene(Stage primaryStage, User user, boolean myScroll) {
        primaryStage.setTitle("My Scrolls");
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        Text titleText = new Text("My Scrolls");
        titleText.setFont(Font.font(24));

        searchField = new TextField();
        searchField.setPromptText("Search scrolls");

        scrollListView = new ListView<>();
        for(String s : DataBaseMethod.searchAllData("scroll", "name")){
            String userID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "Name", s);
            if (userID.equals(user.getUserID())) {
                scrollListView.getItems().addAll(s);
            }
        }

        scrollListView.setPrefSize(740, 5000);

        ScrollPane scrollPane = new ScrollPane(scrollListView);
        scrollPane.setPrefSize(600, 450);

        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("Search Scrolls");
        Button searchButton = new Button("Search");
        searchBox.getChildren().addAll(searchField, searchButton);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        Button browseButton = new Button("Browse");
        Button addScrollButton = new Button("Add Scroll");
        Button backButton = new Button("Return");

        Font buttonFont = Font.font(12);
        int buttonWidth = 125;
        browseButton.setFont(buttonFont);
        browseButton.setPrefWidth(buttonWidth);
        backButton.setFont(buttonFont);
        backButton.setPrefWidth(buttonWidth);
        addScrollButton.setFont(buttonFont);
        addScrollButton.setPrefWidth(buttonWidth);

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        vBox.getChildren().addAll(titleText, searchBox, scrollPane, buttonBox);

        buttonBox.getChildren().addAll(browseButton, addScrollButton, leftSpacer, rightSpacer, backButton);

        browseButton.setOnAction(event -> {
            String selectedScroll = scrollListView.getSelectionModel().getSelectedItem();
            if (selectedScroll != null) {
                String scrollID = DataBaseMethod.searchSpecificData( "Scroll", "ScrollID", "Name", selectedScroll);
                String uploaderID = DataBaseMethod.searchSpecificData( "Scroll", "uploaderID", "Name", selectedScroll);
                File content = DataBaseMethod.getBlobAsFile("Scroll", "content", "uploaderID", user.getUserID());
                ScrollObject scroll = new ScrollObject(scrollID, selectedScroll, uploaderID, content);

                ScrollPage scrollPage = new ScrollPage();
                Scene scrollScene = scrollPage.createScrollScene(primaryStage, user, scroll, true);
                primaryStage.setScene(scrollScene);

            } else {
                // Handle no scroll selected
            }
        });


        backButton.setOnAction(event -> {
            if (user.getIdentity() == null) {

                MainPage mainPage = new MainPage();
                Scene mainScene = mainPage.createMainScene(primaryStage);
                primaryStage.setScene(mainScene);

            } else if (user.getIdentity().equals("Admin")) {

                AdminPage adminPage = new AdminPage();
                Scene adminScene = adminPage.createAdminScene(primaryStage, user);
                primaryStage.setScene(adminScene);

            }

            else {

                CustomerPage customerPage = new CustomerPage();
                Scene customerScene = customerPage.createCustomerScene(primaryStage, user);
                primaryStage.setScene(customerScene);

            }


        });


        searchButton.setOnAction(event -> {
            scrollListView.getItems().clear();
            if (searchField.getText().isEmpty()) {
                for(String s : DataBaseMethod.searchAllData("scroll", "name")){
                    String uploaderID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "name", s);
                    if (uploaderID.equals(user.getUserID())) {
                        scrollListView.getItems().addAll(s);
                    }
                }

            } else {


                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "name", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "name", searchField.getText())) {

                        String uploaderID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "name", s);
                        if (uploaderID.equals(user.getUserID())) {
                            scrollListView.getItems().addAll(s);
                        }
                    }
                }
                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploaderID", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploaderID", searchField.getText())) {

                        String uploaderID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "name", s);
                        if (uploaderID.equals(user.getUserID())) {
                            scrollListView.getItems().addAll(s);
                        }
                    }

                }
                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "scrollID", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "scrollID", searchField.getText())) {

                        String uploaderID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "name", s);
                        if (uploaderID.equals(user.getUserID())) {
                            scrollListView.getItems().addAll(s);
                        }
                    }

                }
                if (DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploadDate", searchField.getText()) != null) {
                    for (String s : DataBaseMethod.searchSpecificDataByKeyWord("scroll", "name", "uploadDate", searchField.getText())) {

                        String uploaderID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "name", s);
                        if (uploaderID.equals(user.getUserID())) {
                            scrollListView.getItems().addAll(s);
                        }
                    }


                }


                if (scrollListView.getItems().isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Search Result");
                    alert.setHeaderText(null);
                    alert.setContentText("No results found for your search.");

                    alert.showAndWait();

                    for (String s : DataBaseMethod.searchAllData("scroll", "name")) {

                        String uploaderID = DataBaseMethod.searchSpecificData("Scroll", "uploaderID", "name", s);
                        if (uploaderID.equals(user.getUserID())) {
                            scrollListView.getItems().addAll(s);
                        }
                    }
                }

            }

            HashSet<String> hashset = new HashSet<>(scrollListView.getItems());
            List<String> newlist = new ArrayList<>(hashset);
            scrollListView.getItems().clear();
            for(String s : newlist){
                scrollListView.getItems().addAll(s);
            }
        });


        addScrollButton.setOnAction(event -> {

            ScrollPage scrollPage = new ScrollPage();
            Scene scrollScene = scrollPage.createAddScrollScene(primaryStage, user, true);
            primaryStage.setScene(scrollScene);

        });





        Image backgroundImage = new Image(getClass().getResource("/main_page.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        vBox.setBackground(new Background(background));

        return new Scene(vBox, 800, 600);


    }
}
