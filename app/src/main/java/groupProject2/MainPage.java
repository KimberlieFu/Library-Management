package groupProject2;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage {
    public Scene createMainScene(Stage primaryStage) {
        primaryStage.setTitle("Main Page");
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        Text welcomeText = new Text("Welcome to the Library of Agility");
        welcomeText.setFont(Font.font(24));


        VBox buttonContainer = new VBox(10);
        buttonContainer.setAlignment(Pos.CENTER);



        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        Button guestButton = new Button("Guest");
        Button exitButton = new Button("Exit");


        // Button settings
        Font buttonFont = Font.font(16);
        int buttonWidth = 150;

        loginButton.setFont(buttonFont);
        loginButton.setPrefWidth(buttonWidth);

        registerButton.setFont(buttonFont);
        registerButton.setPrefWidth(buttonWidth);

        guestButton.setFont(buttonFont);
        guestButton.setPrefWidth(buttonWidth);

        exitButton.setFont(buttonFont);
        exitButton.setPrefWidth(buttonWidth);


        buttonContainer.getChildren().addAll(loginButton, registerButton, guestButton, exitButton);
        vBox.getChildren().addAll(welcomeText, buttonContainer);



        // Event handling for the buttons can be added here
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // When the "Login" button is clicked, launch the Login class
                LoginPage loginPage = new LoginPage();
                Scene loginScene = loginPage.createLoginScene(primaryStage);
                primaryStage.setScene(loginScene);
            }
        });


        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                RegisterPage registerPage = new RegisterPage();
                Scene registerScene = registerPage.createRegisterScene(primaryStage);
                primaryStage.setScene(registerScene);
            }
        });

        guestButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LibraryPage libraryPage = new LibraryPage();
                User user = new User(null, null, null,null,null,null, null);
                Scene registerScene = libraryPage.createLibraryScene(primaryStage, user);
                primaryStage.setScene(registerScene);
            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });







        // Create a background image
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
