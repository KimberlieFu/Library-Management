package groupProject2;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainPageTest extends ApplicationTest {
    private MainPage mainPage;
    private Stage primaryStage;


    @Override
    public void start(Stage stage) {
        mainPage = new MainPage();
        primaryStage = stage;
        Scene scene = mainPage.createMainScene(stage);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testLoginButton() {
        clickOn("Login");
    }


}
