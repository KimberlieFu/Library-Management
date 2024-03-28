package groupProject2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

public class AdminPage {
    private ImageView profilePicture;
    public Scene createAdminScene(Stage primaryStage, User user) {
        primaryStage.setTitle("Admin Page");

        HBox topSection = new HBox();
        topSection.setAlignment(Pos.CENTER);
        topSection.setPadding(new Insets(20));
        topSection.setSpacing(20);

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));



        // Load the profile picture and create an ImageView
        File prevProfilePic = DataBaseMethod.getBlobAsFile("User", "profilephoto", "userID", user.getUserID());
        Image profileImage = new Image(prevProfilePic.toURI().toString());


        profilePicture = new ImageView(profileImage);
        profilePicture.setFitWidth(100);
        profilePicture.setFitHeight(100);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topSection.getChildren().addAll(spacer, profilePicture);
        vBox.getChildren().addAll(profilePicture);


        Text welcomeText = new Text(user.getIdentity() + " " + user.getUsername());
        welcomeText.setFont(Font.font(24));




        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);



        Button browseUserButton = new Button("Browse Users");
        Button myScrollsButton = new Button("My Scrolls");
        Button browseScrollsButton = new Button("Browse Scrolls");
        Button analyticsButton = new Button("Analytics");
        Button editButton = new Button("Modify Profile");
        Button logoutButton = new Button("Logout");


        // Button settings
        Font buttonFont = Font.font(16);
        int buttonWidth = 150;

        browseUserButton.setFont(buttonFont);
        browseUserButton.setPrefWidth(buttonWidth);

        myScrollsButton.setFont(buttonFont);
        myScrollsButton.setPrefWidth(buttonWidth);

        browseScrollsButton.setFont(buttonFont);
        browseScrollsButton.setPrefWidth(buttonWidth);

        analyticsButton.setFont(buttonFont);
        analyticsButton.setPrefWidth(buttonWidth);

        editButton.setFont(buttonFont);
        editButton.setPrefWidth(buttonWidth);

        logoutButton.setFont(buttonFont);
        logoutButton.setPrefWidth(buttonWidth);


        buttonContainer.getChildren().addAll(browseUserButton, myScrollsButton, browseScrollsButton, analyticsButton, editButton, logoutButton);
        vBox.getChildren().addAll(welcomeText, buttonContainer, topSection);



        // Event handling for the buttons can be added here
        logoutButton.setOnAction(event -> {

            MainPage mainPage = new MainPage();
            Scene mainScene = mainPage.createMainScene(primaryStage);
            primaryStage.setScene(mainScene);
        });

        browseScrollsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LibraryPage libraryPage = new LibraryPage();
                Scene registerScene = libraryPage.createLibraryScene(primaryStage, user);
                primaryStage.setScene(registerScene);
            }
        });

        editButton.setOnAction(event -> {

            ModifyPage modifyPage = new ModifyPage();
            Scene modifyScene = modifyPage.createModifyScene(primaryStage, user, null);
            primaryStage.setScene(modifyScene);
        });


        browseUserButton.setOnAction(event -> {

            BrowseUserPage userPage = new BrowseUserPage();
            Scene userScene = userPage.createBrowseUserPage(primaryStage, user);
            primaryStage.setScene(userScene);
        });


        analyticsButton.setOnAction(event -> {

            AnalyticsPage analyticsPage = new AnalyticsPage();
            Scene analyticsScene = analyticsPage.createAnalyticsPage(primaryStage, user);
            primaryStage.setScene(analyticsScene);
        });


        myScrollsButton.setOnAction(event -> {
            LibraryPage libraryPage = new LibraryPage();
            Scene myScene = libraryPage.createMyScrollScene(primaryStage, user, true);
            primaryStage.setScene(myScene);

        });






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
        vBox.setBackground(new Background(background));




        return new Scene(vBox, 800, 600);
    }
}
