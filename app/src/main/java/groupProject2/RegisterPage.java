package groupProject2;

import groupProject2.MainPage;
import groupProject2.PasswordUtils;
import groupProject2.DataBaseMethod;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterPage {
    private TextField userIDField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private TextField codeField;
    private String adminCode = "123";



    // Register Page
    public Scene createRegisterScene(Stage primaryStage) {
        primaryStage.setTitle("Register Page");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);


        Text titleText = new Text("Register");
        titleText.setFont(Font.font(24));

        Label userIDLabel = new Label("New UserID:");
        userIDLabel.setFont(Font.font(20));
        userIDField = new TextField();
        grid.add(userIDLabel, 0, 0);
        grid.add(userIDField, 1, 0);

        Label passwordLabel = new Label("New Password:");
        passwordLabel.setFont(Font.font(20));
        passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        Label userLabel = new Label("New Username:");
        userLabel.setFont(Font.font(20));
        usernameField = new TextField();
        grid.add(userLabel, 0, 2);
        grid.add(usernameField, 1, 2);

        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font(20));
        nameField = new TextField();
        grid.add(nameLabel, 0, 3);
        grid.add(nameField, 1, 3);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font(20));
        emailField = new TextField();
        grid.add(emailLabel, 0, 4);
        grid.add(emailField, 1, 4);

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font(20));
        phoneField = new TextField();
        grid.add(phoneLabel, 0, 5);
        grid.add(phoneField, 1, 5);

        CheckBox adminCheckBox = new CheckBox("I am an admin");
        grid.add(adminCheckBox, 1, 6);

        Label codeLabel = new Label("Admin Code:");
        codeLabel.setFont(Font.font(20));
        codeField = new TextField();
        grid.add(codeLabel, 0, 7);
        grid.add(codeField, 1, 7);
        codeLabel.setVisible(false);
        codeField.setVisible(false);




        Button createButton = new Button("Create Account");
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
        grid.add(buttonBox, 1, 8);

        Label errorMessageLabel = new Label("");
        errorMessageLabel.setFont(Font.font(16));
        errorMessageLabel.setTextFill(Color.RED);
        grid.add(errorMessageLabel, 1, 9);

        Label passMessageLabel = new Label("");
        passMessageLabel.setFont(Font.font(16));
        passMessageLabel.setTextFill(Color.GREEN);
        grid.add(passMessageLabel, 1, 9);



        adminCheckBox.setOnAction(event -> {
            boolean adminChecked = adminCheckBox.isSelected();
            codeLabel.setVisible(adminChecked);
            codeField.setVisible(adminChecked);
        });


        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String userID = userIDField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String fullName = nameField.getText();
                String emailInput = emailField.getText();
                String phoneInput = phoneField.getText();

                int count = DataBaseMethod.searchNumOfData("user", "userID", userID);


                if (username.isEmpty() || userID.isEmpty() || password.isEmpty() || fullName.isEmpty() || emailInput.isEmpty() || phoneInput.isEmpty()) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("Please fill in all fields.");
                } else if (!isValidMail(emailInput)) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("Invalid email.");
                } else if (!isValidAustralianPhone(phoneInput)) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("Invalid Australian phone number.");
                } else if (count == 1) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("User ID taken.");
                } else {
                    password = PasswordUtils.hashPassword(password);


                    if (codeField.getText().equals(adminCode)) {
                        DataBaseMethod.addData("User", userID, password, "Admin", emailInput, phoneInput, fullName, username);

                    } else {
                        DataBaseMethod.addData("User", userID, password, "Customer", emailInput, phoneInput, fullName, username);

                    }
                    File defaultProfile = new File("src/main/resources/default.png");
                    DataBaseMethod.updateImage("user",defaultProfile, userID);

                    errorMessageLabel.setText("");
                    passMessageLabel.setText("Register successful!");
                }
            }
        });


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainPage mainPage = new MainPage();
                Scene mainScene = mainPage.createMainScene(primaryStage);
                primaryStage.setScene(mainScene);
            }
        });

        // Create a background image
        Image backgroundImage = new Image(getClass().getResource("/register_page.png").toExternalForm());

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



    public static boolean isValidMail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@\\w+\\.com$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean isValidAustralianPhone(String phone) {
        String phoneRegex = "^(04|05|\\+614|\\(04\\))[ ]?\\d{2}[ ]?\\d{3}[ ]?\\d{3}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }


}

