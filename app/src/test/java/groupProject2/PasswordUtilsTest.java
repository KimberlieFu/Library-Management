package groupProject2;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilsTest {
    @Test
    void testHashPassword() {
        String password = "password123";
        String hashedPassword = PasswordUtils.hashPassword(password);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword);
    }

    @Test
    void testVerifyPassword() {
        String password = "password123";
        String hashedPassword = PasswordUtils.hashPassword(password);

        assertTrue(PasswordUtils.verifyPassword(password, hashedPassword));
    }

    @Test
    void testVerifyPasswordIncorrect() {
        String password = "password123";
        String incorrectPassword = "wrongpassword";
        String hashedPassword = PasswordUtils.hashPassword(password);

        assertFalse(PasswordUtils.verifyPassword(incorrectPassword, hashedPassword));
    }

    @Test
    void testDefaultConstructor() {
        // Assuming there is a default constructor
        PasswordUtils passwordUtils = new PasswordUtils();
        assertNotNull(passwordUtils);
    }

}
