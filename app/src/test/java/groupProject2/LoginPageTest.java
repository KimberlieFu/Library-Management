package groupProject2;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoginPageTest extends ApplicationTest {
    private LoginPage loginPage;
    private Stage primaryStage;


    @Override
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        loginPage = new LoginPage();
        primaryStage = stage;
        Scene scene = loginPage.createLoginScene(primaryStage);
        stage.setScene(scene);
        stage.show();
    }

}
