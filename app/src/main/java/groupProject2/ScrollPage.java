package groupProject2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
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


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


public class ScrollPage {
    private File uploadedFile;
    private File currFile;
    public Scene createScrollScene(Stage primaryStage, User user, ScrollObject scroll, boolean myScroll) {
        primaryStage.setTitle("Scroll Page - " + scroll.getScrollName()); // Set the title including scroll name

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));


        Text titleText = new Text(scroll.getScrollName());
        titleText.setFont(Font.font(24));
        vBox.getChildren().addAll(titleText);

        BorderPane borderPane = new BorderPane();

        if (scroll.getScrollId().contains("TXT")) {

            String fileContent = "";
            currFile = DataBaseMethod.getBlobAsFile("Scroll", "content", "name", scroll.getScrollName());

            try {
                fileContent = new String(Files.readAllBytes(currFile.toPath()));
            } catch (IOException e) {
                e.printStackTrace();

            }

            Label textPreviewLabel = new Label();
            textPreviewLabel.setStyle("-fx-background-color: white; -fx-border-color: black;");
            textPreviewLabel.setWrapText(true);
            textPreviewLabel.setPrefSize(600, 400);


            VBox textPreviewBox = new VBox(textPreviewLabel);
            textPreviewBox.setAlignment(Pos.TOP_CENTER);
            textPreviewBox.setSpacing(10);
            textPreviewBox.setPadding(new Insets(10));
            textPreviewBox.setSpacing(10);
            textPreviewBox.setPadding(new Insets(10));
            textPreviewLabel.setText(fileContent);
            borderPane.setCenter(textPreviewBox);
            vBox.getChildren().addAll(textPreviewBox);

        }

        if (scroll.getScrollId().contains("PNG")) {

            currFile = DataBaseMethod.getBlobAsFile("Scroll", "content", "name", scroll.getScrollName());

            Image image = new Image(currFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            VBox imageBox = new VBox(imageView);
            imageBox.setAlignment(Pos.CENTER);
            imageView.setFitWidth(400);
            imageView.setFitHeight(400);
            imageBox.setSpacing(20);
            imageBox.setPadding(new Insets(20, 0, 20, 0));
            borderPane.setCenter(imageBox);
            vBox.getChildren().addAll(imageBox);

        }

        Button uploadButton = new Button("Edit");
        Button downloadButton = new Button("Download");
        Button returnButton = new Button("Return");

        Font buttonFont = Font.font(12);
        int buttonWidth = 125;
        uploadButton.setFont(buttonFont);
        uploadButton.setPrefWidth(buttonWidth);
        downloadButton.setFont(buttonFont);
        downloadButton.setPrefWidth(buttonWidth);
        returnButton.setFont(buttonFont);
        returnButton.setPrefWidth(buttonWidth);


        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        // Set button actions
        uploadButton.setOnAction(event -> {
            ScrollPage scrollPage = new ScrollPage();
            Scene scrollScene = scrollPage.createModifyScrollScene(primaryStage, user, scroll, myScroll);
            primaryStage.setScene(scrollScene);

        });


        downloadButton.setOnAction(event -> {
            if (currFile != null) {
                try {
                    FileChooser fileChooser = new FileChooser();
                    if (scroll.getScrollId().contains("TXT")) {
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                    }

                    if (scroll.getScrollId().contains("PNG")) {
                        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

                    }

                    File selectedFile = fileChooser.showSaveDialog(primaryStage);

                    if (selectedFile != null) {
                        Files.copy(currFile.toPath(), selectedFile.toPath());
                        DataBaseMethod.updateNumericalData("scroll", "downloadTime", 1, "Name", scroll.getScrollName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        returnButton.setOnAction(event -> {

            LibraryPage libraryPage = new LibraryPage();
            Scene libraryScene;

            if (myScroll) {
                libraryScene = libraryPage.createMyScrollScene(primaryStage, user, true);
            } else {

                libraryScene = libraryPage.createLibraryScene(primaryStage, user);

            }
            primaryStage.setScene(libraryScene);


        });


        if (user.getUserID() != null) {

            if (user.getUserID().equals(scroll.getUploaderId())) {
                buttonBox.getChildren().addAll(uploadButton, downloadButton, leftSpacer, rightSpacer, returnButton);
            } else {
                buttonBox.getChildren().addAll(downloadButton, leftSpacer, rightSpacer, returnButton);
            }
        } else {
            buttonBox.getChildren().addAll(leftSpacer, rightSpacer, returnButton);
        }



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
        vBox.getChildren().addAll(buttonBox);


        return new Scene(vBox, 800, 600);
    }

    public Scene createAddScrollScene(Stage primaryStage, User user, boolean myScroll) {
        TextField scrollNameField;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);

        primaryStage.setTitle("Register Scroll Page");

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));



        Text titleText = new Text("Register Scroll Page");
        titleText.setFont(Font.font(24));




        Label scrollNameLabel = new Label("Scroll Name:");
        scrollNameLabel.setFont(Font.font(20));
        scrollNameField = new TextField();
        scrollNameField.setPrefWidth(300);
        grid.setAlignment(Pos.CENTER);
        grid.add(scrollNameLabel, 0, 0);
        grid.add(scrollNameField, 1, 0);



        Label typeLabel = new Label("Scroll Type:");
        typeLabel.setFont(Font.font(20));
        ObservableList<String> options = FXCollections.observableArrayList(
                "Text file (.txt)",
                "PNG file (.png)"
        );
        ComboBox<String> comboBox = new ComboBox<>(options);

        comboBox.setPromptText("Type of file");
        grid.add(typeLabel, 0, 1);
        grid.add(comboBox, 1, 1);
        comboBox.setMinWidth(300);



        Button uploadButton = new Button("Upload");
        Button returnButton = new Button("Return");
        Button saveButton = new Button("Save");

        Font buttonFont = Font.font(12);
        int buttonWidth = 125;
        uploadButton.setFont(buttonFont);
        uploadButton.setPrefWidth(buttonWidth);
        returnButton.setFont(buttonFont);
        returnButton.setPrefWidth(buttonWidth);
        saveButton.setFont(buttonFont);
        saveButton.setPrefWidth(buttonWidth);

        Label errorMessageLabel = new Label("");
        errorMessageLabel.setFont(Font.font(16));
        errorMessageLabel.setTextFill(Color.RED);
        grid.add(errorMessageLabel, 1, 3);

        Label passMessageLabel = new Label("");
        passMessageLabel.setFont(Font.font(16));
        passMessageLabel.setTextFill(Color.GREEN);
        grid.add(passMessageLabel, 1, 3);

        grid.add(saveButton, 1, 2);



        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);


        GridPane.setValignment(scrollNameLabel, VPos.CENTER);
        GridPane.setValignment(scrollNameField, VPos.CENTER);
        GridPane.setValignment(typeLabel, VPos.CENTER);
        GridPane.setValignment(comboBox, VPos.CENTER);



        // Add buttons to the buttonBox
        if (user.getUserID() != null) {
            buttonBox.getChildren().addAll(uploadButton, leftSpacer, rightSpacer, returnButton);
        } else {
            buttonBox.getChildren().addAll(leftSpacer, rightSpacer, returnButton);
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vBox);
        borderPane.setBottom(buttonBox);
        borderPane.setAlignment(grid, Pos.CENTER);






        Image backgroundImage = new Image(getClass().getResource("/main_page.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        borderPane.setBackground(new Background(background));
        vBox.getChildren().addAll(grid);





        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                LibraryPage libraryPage = new LibraryPage();
                Scene libraryScene;
                if (myScroll) {
                    libraryScene = libraryPage.createMyScrollScene(primaryStage, user, true);

                } else {
                    libraryScene = libraryPage.createLibraryScene(primaryStage, user);

                }
                primaryStage.setScene(libraryScene);


            }
        });


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String scrollName = scrollNameField.getText();
                String currDate = getCurrentSQLDate();
                String selectedFileType = comboBox.getValue();

                int taken = DataBaseMethod.searchNumOfData("Scroll", "Name", scrollName);

                if (uploadedFile == null) {
                    errorMessageLabel.setText("No file selected.");
                    passMessageLabel.setText("");
                }

                else if (taken == 1) {
                    errorMessageLabel.setText("Scroll name taken.");
                    passMessageLabel.setText("");

                } else if (!(scrollName.isEmpty())) {
                    int count = DataBaseMethod.searchNumOfData("Scroll") + 1;
                    String scrollID = String.valueOf(count);


                    if (selectedFileType.equals("PNG file (.png)")) {
                        DataBaseMethod.addPng("Scroll", scrollID, scrollName, user.getUserID(), currDate, uploadedFile);
                    }

                    else if (selectedFileType.equals("Text file (.txt)")) {
                        DataBaseMethod.addTxt("Scroll", scrollID, scrollName, user.getUserID(), currDate, uploadedFile);
                    }

                    DataBaseMethod.updateNumericalData("scroll", "uploadTime", 1, "Name", scrollName);



                    passMessageLabel.setText("Scroll successfully saved.");
                    errorMessageLabel.setText("");

                } else {
                    errorMessageLabel.setText("Scroll name empty.");
                    passMessageLabel.setText("");
                }

            }
        });



        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedFileType = comboBox.getValue();

                if (selectedFileType != null) {
                    FileChooser fileChooser = new FileChooser();

                    // Set the extension filter based on the selected file type
                    FileChooser.ExtensionFilter extensionFilter = null;
                    if (selectedFileType.equals("Text file (.txt)")) {
                        extensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");

                    } else if (selectedFileType.equals("PNG file (.png)")) {
                        extensionFilter = new FileChooser.ExtensionFilter("PNG Files", "*.png");
                    }

                    if (extensionFilter != null) {
                        fileChooser.getExtensionFilters().add(extensionFilter);
                        File selectedFile = fileChooser.showOpenDialog(primaryStage);


                        if (selectedFile != null) {
                            uploadedFile = selectedFile;
                            if (selectedFileType.equals("Text file (.txt)")) {
                                String fileName = selectedFile.getName();
                                String fileContent = "";


                                try {
                                    fileContent = new String(Files.readAllBytes(selectedFile.toPath()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    errorMessageLabel.setText("Error reading file content.");
                                    passMessageLabel.setText("");
                                    return;
                                }

                                Label textPreviewLabel = new Label();
                                textPreviewLabel.setStyle("-fx-background-color: white; -fx-border-color: black;");
                                textPreviewLabel.setWrapText(true);
                                textPreviewLabel.setPrefSize(600, 400);


                                VBox textPreviewBox = new VBox(textPreviewLabel);
                                textPreviewBox.setAlignment(Pos.TOP_CENTER);
                                textPreviewBox.setSpacing(10);
                                textPreviewBox.setPadding(new Insets(10));
                                textPreviewBox.setSpacing(10);
                                textPreviewBox.setPadding(new Insets(10));
                                textPreviewLabel.setText(fileContent);
                                borderPane.setCenter(textPreviewBox);
                                passMessageLabel.setText("File uploaded: " + fileName);
                                errorMessageLabel.setText("");



                            } else if (selectedFileType.equals("PNG file (.png)")) {


                                Image image = new Image(selectedFile.toURI().toString());
                                ImageView imageView = new ImageView(image);
                                VBox imageBox = new VBox(imageView);
                                imageBox.setAlignment(Pos.CENTER);
                                imageView.setFitWidth(250);
                                imageView.setFitHeight(250);
                                imageBox.setSpacing(20);
                                imageBox.setPadding(new Insets(20, 0, 20, 0));
                                borderPane.setCenter(imageBox);



                                passMessageLabel.setText("File uploaded: " + selectedFile.getName());
                                errorMessageLabel.setText("");

                            }
                        } else {
                            // The user canceled the file selection
                            errorMessageLabel.setText("File selection canceled.");
                            passMessageLabel.setText("");
                        }
                    } else {
                        errorMessageLabel.setText("Unsupported file type.");
                        passMessageLabel.setText("");
                    }
                } else {
                    errorMessageLabel.setText("Please select a file type.");
                    passMessageLabel.setText("");
                }
            }
        });


        return new Scene(borderPane, 800, 600);
    }

    public Scene createModifyScrollScene(Stage primaryStage, User user, ScrollObject scroll, boolean myScroll) {
        TextField scrollNameField;

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setAlignment(Pos.CENTER);

        primaryStage.setTitle("Modify Scroll Page");

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10));



        Text titleText = new Text("Modify Scroll Page");
        titleText.setFont(Font.font(24));




        Label scrollNameLabel = new Label("Scroll Name:");
        scrollNameLabel.setFont(Font.font(20));
        scrollNameField = new TextField();
        scrollNameField.setPrefWidth(300);
        grid.setAlignment(Pos.CENTER);
        grid.add(scrollNameLabel, 0, 0);
        grid.add(scrollNameField, 1, 0);
        String prevName = DataBaseMethod.searchSpecificData("Scroll", "name", "ScrollID", scroll.getScrollId());
        scrollNameField.setText(prevName);



        Label typeLabel = new Label("Scroll Type:");
        typeLabel.setFont(Font.font(20));
        ObservableList<String> options = FXCollections.observableArrayList(
                "Text file (.txt)",
                "PNG file (.png)"
        );
        ComboBox<String> comboBox = new ComboBox<>(options);

        comboBox.setPromptText("Type of file");
        grid.add(typeLabel, 0, 1);
        grid.add(comboBox, 1, 1);
        comboBox.setMinWidth(300);
        if (scroll.getScrollId().contains("TXT")) {
            comboBox.setValue("Text file (.txt)");
        }

        if (scroll.getScrollId().contains("PNG")) {
            comboBox.setValue("PNG file (.png)");
        }



        Button uploadButton = new Button("Upload");
        Button deleteButton = new Button("Delete");
        Button returnButton = new Button("Return");
        Button saveButton = new Button("Save");

        Font buttonFont = Font.font(12);
        int buttonWidth = 125;
        uploadButton.setFont(buttonFont);
        uploadButton.setPrefWidth(buttonWidth);
        returnButton.setFont(buttonFont);
        returnButton.setPrefWidth(buttonWidth);
        saveButton.setFont(buttonFont);
        saveButton.setPrefWidth(buttonWidth);
        deleteButton.setFont(buttonFont);
        deleteButton.setPrefWidth(buttonWidth);


        Label errorMessageLabel = new Label("");
        errorMessageLabel.setFont(Font.font(16));
        errorMessageLabel.setTextFill(Color.RED);
        grid.add(errorMessageLabel, 1, 3);

        Label passMessageLabel = new Label("");
        passMessageLabel.setFont(Font.font(16));
        passMessageLabel.setTextFill(Color.GREEN);
        grid.add(passMessageLabel, 1, 3);

        grid.add(saveButton, 1, 2);



        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();

        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);


        GridPane.setValignment(scrollNameLabel, VPos.CENTER);
        GridPane.setValignment(scrollNameField, VPos.CENTER);
        GridPane.setValignment(typeLabel, VPos.CENTER);
        GridPane.setValignment(comboBox, VPos.CENTER);



        // Add buttons to the buttonBox

        buttonBox.getChildren().addAll(uploadButton, deleteButton, leftSpacer, rightSpacer, returnButton);


        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vBox);
        borderPane.setBottom(buttonBox);
        borderPane.setAlignment(grid, Pos.CENTER);






        Image backgroundImage = new Image(getClass().getResource("/main_page.png").toExternalForm());

        // Create a BackgroundImage
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        borderPane.setBackground(new Background(background));
        vBox.getChildren().addAll(grid);





        returnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                LibraryPage libraryPage = new LibraryPage();
                Scene libraryScene;
                if (myScroll) {
                    libraryScene = libraryPage.createMyScrollScene(primaryStage, user, true);

                } else {
                    libraryScene = libraryPage.createLibraryScene(primaryStage, user);

                }
                primaryStage.setScene(libraryScene);


            }
        });

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Scroll");
                alert.setContentText("Are you sure you want to delete this scroll?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Remove the selected scroll from the list
                    DataBaseMethod.delData(null, "Scroll", "ScrollID", scroll.getScrollId());
                    // Refresh the TableView

                }

                LibraryPage libraryPage = new LibraryPage();
                Scene libraryScene;
                if (myScroll) {
                    libraryScene = libraryPage.createMyScrollScene(primaryStage, user, true);

                } else {
                    libraryScene = libraryPage.createLibraryScene(primaryStage, user);

                }
                primaryStage.setScene(libraryScene);

            }
        });


        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String scrollName = scrollNameField.getText();
                String selectedFileType = comboBox.getValue();

                int taken = DataBaseMethod.searchNumOfData("Scroll", "Name", scrollName);
                String prevScrollID = DataBaseMethod.searchSpecificData("Scroll", "ScrollID", "ScrollID", scroll.getScrollId());
                char count = prevScrollID.charAt(prevScrollID.length() - 1);



                if (!(scrollName.isEmpty()) && (uploadedFile != null)) {

                    if ((selectedFileType.equals("PNG file (.png)")) && (scroll.getScrollId().contains("PNG"))) {
                        DataBaseMethod.updateBianryFile(uploadedFile, scroll.getScrollId());

                    }

                    else if (selectedFileType.equals("Text file (.txt)") && (scroll.getScrollId().contains("TXT"))) {
                        DataBaseMethod.updateBianryFile(uploadedFile, scroll.getScrollId());

                    }

                    else if (selectedFileType.equals("Text file (.txt)")) {

                        String scrollID = "TXT-" + count;
                        DataBaseMethod.updateData("Scroll", "ScrollID", scrollID, "Name", scroll.getScrollName());
                        DataBaseMethod.updateBianryFile(uploadedFile, scrollID);
                        DataBaseMethod.updateNumericalData("scroll", "updateTime", 1, "ScrollID", scrollID);
                        scroll.setScrollId(scrollID);


                    }

                    else if (selectedFileType.equals("PNG file (.png)")) {

                        String scrollID = "PNG-" + count;
                        DataBaseMethod.updateData("Scroll", "ScrollID", scrollID, "Name", scroll.getScrollName());
                        DataBaseMethod.updateBianryFile(uploadedFile, scrollID);
                        scroll.setScrollId(scrollID);

                    }

                    passMessageLabel.setText("Scroll successfully saved.");
                    errorMessageLabel.setText("");

                }
                else if (uploadedFile == null) {
                    passMessageLabel.setText("Scroll successfully saved.");
                    errorMessageLabel.setText("");
                }

                else {
                    errorMessageLabel.setText("Scroll name empty.");
                    passMessageLabel.setText("");
                }




                if (taken == 1 && !(scrollName.equals(scroll.getScrollName()))) {
                    errorMessageLabel.setText("Scroll name taken.");
                    passMessageLabel.setText("");

                } else {
                    DataBaseMethod.updateData("Scroll", "Name", scrollName, "ScrollID", scroll.getScrollId());
                }




            }
        });



        uploadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedFileType = comboBox.getValue();

                if (selectedFileType != null) {
                    FileChooser fileChooser = new FileChooser();

                    // Set the extension filter based on the selected file type
                    FileChooser.ExtensionFilter extensionFilter = null;
                    if (selectedFileType.equals("Text file (.txt)")) {
                        extensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");

                    } else if (selectedFileType.equals("PNG file (.png)")) {
                        extensionFilter = new FileChooser.ExtensionFilter("PNG Files", "*.png");
                    }

                    if (extensionFilter != null) {
                        fileChooser.getExtensionFilters().add(extensionFilter);
                        File selectedFile = fileChooser.showOpenDialog(primaryStage);


                        if (selectedFile != null) {
                            uploadedFile = selectedFile;
                            if (selectedFileType.equals("Text file (.txt)")) {
                                String fileName = selectedFile.getName();
                                String fileContent = "";


                                try {
                                    fileContent = new String(Files.readAllBytes(selectedFile.toPath()));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    errorMessageLabel.setText("Error reading file content.");
                                    passMessageLabel.setText("");
                                    return;
                                }

                                Label textPreviewLabel = new Label();
                                textPreviewLabel.setStyle("-fx-background-color: white; -fx-border-color: black;");
                                textPreviewLabel.setWrapText(true);
                                textPreviewLabel.setPrefSize(600, 400);


                                VBox textPreviewBox = new VBox(textPreviewLabel);
                                textPreviewBox.setAlignment(Pos.TOP_CENTER);
                                textPreviewBox.setSpacing(10);
                                textPreviewBox.setPadding(new Insets(10));
                                textPreviewBox.setSpacing(10);
                                textPreviewBox.setPadding(new Insets(10));
                                textPreviewLabel.setText(fileContent);
                                borderPane.setCenter(textPreviewBox);
                                passMessageLabel.setText("File uploaded: " + fileName);
                                errorMessageLabel.setText("");



                            } else if (selectedFileType.equals("PNG file (.png)")) {


                                Image image = new Image(selectedFile.toURI().toString());
                                ImageView imageView = new ImageView(image);
                                VBox imageBox = new VBox(imageView);
                                imageBox.setAlignment(Pos.CENTER);
                                imageView.setFitWidth(250);
                                imageView.setFitHeight(250);
                                imageBox.setSpacing(20);
                                imageBox.setPadding(new Insets(20, 0, 20, 0));
                                borderPane.setCenter(imageBox);



                                passMessageLabel.setText("File uploaded: " + selectedFile.getName());
                                errorMessageLabel.setText("");

                            }
                        } else {
                            // The user canceled the file selection
                            errorMessageLabel.setText("File selection canceled.");
                            passMessageLabel.setText("");
                        }
                    } else {
                        errorMessageLabel.setText("Unsupported file type.");
                        passMessageLabel.setText("");
                    }
                } else {
                    errorMessageLabel.setText("Please select a file type.");
                    passMessageLabel.setText("");
                }
            }
        });


        return new Scene(borderPane, 800, 600);

    }

    public Scene createMyScrollsScene(Stage primaryStage, User user) {
        primaryStage.setTitle("My Scrolls");

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(20));

        Text titleText = new Text("My Scrolls");
        titleText.setFont(Font.font(24));

        ListView<String> scrollListView = new ListView<>();
        List<String> userScrolls = DataBaseMethod.getUserScrolls("scroll",user.getUserID());
        scrollListView.getItems().addAll(userScrolls);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        TextArea scrollTextArea = new TextArea();
        scrollTextArea.setWrapText(true);
        scrollTextArea.setEditable(false);



        scrollListView.setOnMouseClicked(event -> {
            String selectedScrollName = scrollListView.getSelectionModel().getSelectedItem();
            if (selectedScrollName != null) {
                byte[] scrollContentBytes = getScrollContent(selectedScrollName);

                if (scrollContentBytes != null) {
                    String scrollContent = new String(scrollContentBytes, StandardCharsets.UTF_8);
                    scrollTextArea.setText(scrollContent);

                    Image image = new Image(new ByteArrayInputStream(scrollContentBytes));
                    imageView.setImage(image);

                    showPopupWindow(selectedScrollName, imageView, scrollTextArea);
                }
            }
        });

        Button returnButton = new Button("Return");

        returnButton.setOnAction(event -> {
            CustomerPage customerPage = new CustomerPage();
            Scene customerScene = customerPage.createCustomerScene(primaryStage, user);
            primaryStage.setScene(customerScene);
        });

        HBox contentBox = new HBox(20);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(10, 0, 10, 0));
        contentBox.getChildren().addAll(returnButton);

        vBox.getChildren().addAll(titleText, scrollListView, contentBox);



        BackgroundImage background = new BackgroundImage(
                new Image(getClass().getResource("/main_page.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true)
        );
        vBox.setBackground(new Background(background));

        return new Scene(vBox, 800, 600);
    }


    private void showPopupWindow(String scrollName, ImageView imageView, TextArea scrollTextArea) {
        Stage popupStage = new Stage();
        popupStage.setTitle(scrollName + " Content");

        VBox popupLayout = new VBox(60);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.getChildren().addAll(imageView, scrollTextArea);

        Scene popupScene = new Scene(popupLayout, 600, 400);
        popupStage.setScene(popupScene);

        popupStage.show();

    }

    private byte[] getScrollContent(String scrollName) {
        byte[] scrollContent = null;

        try (Connection connection = DriverManager.getConnection(App.url, App.username, App.password)) {
            String query = "SELECT content FROM scroll WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, scrollName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        Blob blob = resultSet.getBlob("content");
                        scrollContent = blob.getBytes(1, (int) blob.length());
                        blob.free();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scrollContent;
    }




    public static String getCurrentSQLDate() {
        // Get the current date
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

        // Format the date to SQL format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(currentDate);
    }
}
