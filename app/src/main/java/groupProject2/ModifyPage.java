package groupProject2;

import groupProject2.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ModifyPage {
    private TextField userIDField;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField nameField;
    private TextField emailField;
    private TextField phoneField;
    private ImageView profilePicture;
    private File updatedPicture;



    // Register Page
    public Scene createModifyScene(Stage primaryStage, User user, User admin) {
        primaryStage.setTitle("Modify Page");
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);

        HBox topSection = new HBox();
        topSection.setAlignment(Pos.CENTER);
        topSection.setPadding(new Insets(20));
        topSection.setSpacing(20);

        // Load the profile picture and create an ImageView


        File prevProfilePic = DataBaseMethod.getBlobAsFile("User", "profilephoto", "userID", user.getUserID());
        Image profileImage = null;


        if (prevProfilePic == null) {
            profileImage = new Image(getClass().getResource("/default.png").toExternalForm());
        } else {
            profileImage = new Image(prevProfilePic.toURI().toString());
        }

        profilePicture = new ImageView(profileImage);
        profilePicture.setFitWidth(100);
        profilePicture.setFitHeight(100);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topSection.getChildren().addAll(spacer, profilePicture);



        ColumnConstraints column1 = new ColumnConstraints(250);
        ColumnConstraints column2 = new ColumnConstraints(250);
        Text titleText = new Text("Edit Profile");
        titleText.setFont(Font.font(24));

        Label userIDLabel = new Label("New UserID:");
        userIDLabel.setFont(Font.font(20));
        userIDField = new TextField();
        grid.add(userIDLabel, 0, 0);
        grid.add(userIDField, 1, 0);
        String prevID = DataBaseMethod.searchSpecificData("User", "userID", "userID", user.getUserID());
        userIDField.setText(prevID);

        Label passwordLabel = new Label("New Password:");
        passwordLabel.setFont(Font.font(20));
        passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        String prevPass = DataBaseMethod.searchSpecificData("User", "password", "userID", user.getUserID());
        passwordField.setText(prevPass);

        Label usernameLabel = new Label("New Username:");
        usernameLabel.setFont(Font.font(20));
        usernameField = new TextField();
        grid.add(usernameLabel, 0, 2);
        grid.add(usernameField, 1, 2);
        String prevName = DataBaseMethod.searchSpecificData("User", "username", "userID", user.getUserID());
        usernameField.setText(prevName);

        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font(20));
        nameField = new TextField();
        grid.add(nameLabel, 0, 3);
        grid.add(nameField, 1, 3);
        String prevFull =  DataBaseMethod.searchSpecificData("User", "fullname", "userID", user.getUserID());
        nameField.setText(prevFull);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font(20));
        emailField = new TextField();
        grid.add(emailLabel, 0, 4);
        grid.add(emailField, 1, 4);
        String prevEmail = DataBaseMethod.searchSpecificData("User", "emailAddress", "userID", user.getUserID());
        emailField.setText(prevEmail);

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font(20));
        phoneField = new TextField();
        grid.add(phoneLabel, 0, 5);
        grid.add(phoneField, 1, 5);
        String prevPhone = DataBaseMethod.searchSpecificData("User", "phoneNumber", "userID", user.getUserID());
        phoneField.setText(prevPhone);

        Button saveButton = new Button("Save Changes");
        Button exitButton = new Button("Return");

        Font buttonFont = Font.font(16);
        int buttonWidth = 150;
        saveButton.setFont(buttonFont);
        saveButton.setPrefWidth(buttonWidth);
        exitButton.setFont(buttonFont);
        exitButton.setPrefWidth(buttonWidth);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(saveButton, exitButton);
        grid.add(buttonBox, 1, 6);

        Label errorMessageLabel = new Label("");
        errorMessageLabel.setFont(Font.font(16));
        errorMessageLabel.setTextFill(Color.RED);
        grid.add(errorMessageLabel, 1, 7);

        Label passMessageLabel = new Label("");
        passMessageLabel.setFont(Font.font(16));
        passMessageLabel.setTextFill(Color.GREEN);
        grid.add(passMessageLabel, 1, 7);



        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String userID = userIDField.getText();
                String userName = usernameField.getText();
                String password = passwordField.getText();
                String fullName = nameField.getText();
                String emailInput = emailField.getText();
                String phoneInput = phoneField.getText();

                String taken = DataBaseMethod.searchSpecificData("User", "userID", "userID", userName);





                if ((taken != null) && !taken.equals(user.getUserID())) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("UserID taken. Choose another one.");
                } else if (!isValidMail(emailInput)) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("Invalid email.");
                } else if (!isValidAustralianPhone(phoneInput)) {
                    passMessageLabel.setText("");
                    errorMessageLabel.setText("Invalid Australian phone number.");
                } else {

                    if (!(password.equals("")) && !(password.equals(prevPass))) {
                        password = PasswordUtils.hashPassword(password);
                        user.setPassword(password);
                    }

                    if (taken == null) {

                        ArrayList<String> uploaderIDList = DataBaseMethod.searchAllData("Scroll", "uploaderID");
                        for (int i = 0; i <  uploaderIDList.size(); i ++) {
                            if (uploaderIDList.get(i).equals(user.getUserID())) {
                                DataBaseMethod.updateData("Scroll", "uploaderID", userID, "uploaderID", user.getUserID());
                            }
                        }
                        user.setUserID(userID);

                    }

                    if (updatedPicture != null) {
                        DataBaseMethod.updateImage("user", updatedPicture, user.getUserID());
                    }



                    user.setUsername(userName);
                    user.setFullname(fullName);
                    user.setEmailAddress(emailInput);
                    user.setPhoneNumber(phoneInput);
                    errorMessageLabel.setText("");
                    passMessageLabel.setText("Update successful!");


                }


            }
        });

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if (admin != null) {

                    BrowseUserPage userPage = new BrowseUserPage();
                    Scene userScene = userPage.createBrowseUserPage(primaryStage, admin);
                    primaryStage.setScene(userScene);

                } else if (user.getIdentity() == null) {

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
            }
        });

        profilePicture.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (selectedFile != null) {
                Image newProfileImage = new Image(selectedFile.toURI().toString());
                profilePicture.setImage(newProfileImage);
                updatedPicture = selectedFile;

            }
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

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(profilePicture);
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

