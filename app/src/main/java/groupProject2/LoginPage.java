package groupProject2;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class LoginPage {
    private TextField userIDField;
    private PasswordField passwordField;


    // Register Page
    public Scene createLoginScene(Stage primaryStage) {
        primaryStage.setTitle("Login Page");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);

        ColumnConstraints column1 = new ColumnConstraints(250);
        ColumnConstraints column2 = new ColumnConstraints(250);
        Text titleText = new Text("Login");
        titleText.setFont(Font.font(24));

        Label userIDLabel = new Label("UserID:");
        userIDLabel.setFont(Font.font(20));
        userIDField = new TextField();
        grid.add(userIDLabel, 0, 0);
        grid.add(userIDField, 1, 0);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font(20));
        passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);


        Button createButton = new Button("Login");
        Button exitButton = new Button("Return");

        Font buttonFont = Font.font(16);
        int buttonWidth = 150;
        createButton.setFont(buttonFont);
        createButton.setPrefWidth(buttonWidth);
        exitButton.setFont(buttonFont);
        exitButton.setPrefWidth(buttonWidth);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createButton, exitButton);
        grid.add(buttonBox, 1, 2);

        Label errorMessageLabel = new Label("");
        errorMessageLabel.setFont(Font.font(16));
        errorMessageLabel.setTextFill(Color.RED);
        grid.add(errorMessageLabel, 1, 3);


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // When the "Return" button is clicked, launch the Main class
                MainPage mainPage = new MainPage();
                Scene mainScene = mainPage.createMainScene(primaryStage);
                primaryStage.setScene(mainScene);
            }
        });




        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String userIDInput = userIDField.getText();
                String passwordInput = passwordField.getText();


                int count = DataBaseMethod.searchNumOfData("user", "userID", userIDInput);
                if (count == 0) {
                    errorMessageLabel.setText("User not found. Please check your username.");
                } else {
                    String passwordInDatabase = DataBaseMethod.searchSpecificData("User", "password", "userID", userIDInput);
                    if (PasswordUtils.verifyPassword(passwordInput, passwordInDatabase)) {

                        if (DataBaseMethod.searchSpecificData("User", "identity", "userID", userIDInput).equals("Admin")){
                            User user = new Admin(userIDInput, passwordInput, DataBaseMethod.searchSpecificData( "User", "identity", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "emailAddress", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "phoneNumber", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "fullname", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "username", "userID", userIDInput));


                            AdminPage adminPage = new AdminPage();
                            Scene adminScene = adminPage.createAdminScene(primaryStage, user);
                            primaryStage.setScene(adminScene);



                        }else if (DataBaseMethod.searchSpecificData("User", "identity", "userID", userIDInput).equals("Customer")){
                            User user = new Customer(userIDInput, passwordInput, DataBaseMethod.searchSpecificData( "User", "identity", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "emailAddress", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "phoneNumber", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "fullname", "userID", userIDInput), DataBaseMethod.searchSpecificData( "User", "username", "userID", userIDInput));


                            CustomerPage customerPage = new CustomerPage();
                            Scene customerScene = customerPage.createCustomerScene(primaryStage, user);
                            primaryStage.setScene(customerScene);



                        } else{
                            errorMessageLabel.setText("Unknown user type.");
                        }
                    } else {
                        errorMessageLabel.setText("Password is incorrect!");
                    }
                }
            }
        });


        // Create a background image
        Image backgroundImage = new Image(getClass().getResource("/login_page.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );

        VBox vBox = new VBox(10);
        vBox.setBackground(new Background(background));
        vBox.getChildren().add(titleText);
        vBox.getChildren().add(grid);
        vBox.setAlignment(Pos.CENTER);
        return new Scene(vBox, 800, 600);
    }

}
