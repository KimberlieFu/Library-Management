package groupProject2;

import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User("123", "password123", "User", "user@example.com", "123456789", "John Doe", "johndoe");
    }

    @Test
    public void testGetUserID() {
        assertEquals("123", user.getUserID());
    }

    @Test
    public void testSetUserID() {
        user.setUserID("456");
        assertEquals("456", user.getUserID());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testGetIdentity() {
        assertEquals("User", user.getIdentity());
    }

    @Test
    public void testSetIdentity() {
        user.setIdentity("Admin");
        assertEquals("Admin", user.getIdentity());
    }

    @Test
    public void testGetEmailAddress() {
        assertEquals("user@example.com", user.getEmailAddress());
    }

    @Test
    public void testSetEmailAddress() {
        user.setEmailAddress("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmailAddress());
    }

    @Test
    public void testGetPhoneNumber() {
        assertEquals("123456789", user.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber() {
        user.setPhoneNumber("987654321");
        assertEquals("987654321", user.getPhoneNumber());
    }

    @Test
    public void testGetFullname() {
        assertEquals("John Doe", user.getFullname());
    }

    @Test
    public void testSetFullname() {
        user.setFullname("Jane Smith");
        assertEquals("Jane Smith", user.getFullname());
    }

    @Test
    public void testGetUploaderID() {
        assertNull(user.getUploaderID());
    }

    @Test
    public void testSetUploaderID() {
        user.setUploaderID("789");
        assertEquals("789", user.getUploaderID());
    }

    @Test
    public void testGetUsername() {
        assertEquals("johndoe", user.getUsername());
    }

    @Test
    public void testSetUsername() {
        user.setUsername("janesmith");
        assertEquals("janesmith", user.getUsername());
    }

    @Test
    public void testUploadScroll() {
        int scrollId = 1;
        String scrollName = "Scroll 1";
        java.sql.Date uploadDate = new java.sql.Date(System.currentTimeMillis());
        java.io.File binaryFile = new java.io.File("test-scroll-file.txt");

        user.uploadScroll(scrollId, scrollName, uploadDate, binaryFile);
        assertEquals(user.getUserID(), user.getUploaderID());
    }

    @Test
    public void testGetProperty() {
        StringProperty userIDProperty = user.getProperty("userID");
        assertNotNull(userIDProperty);

        StringProperty identityProperty = user.getProperty("identity");
        assertNotNull(identityProperty);

        StringProperty emailAddressProperty = user.getProperty("emailAddress");
        assertNotNull(emailAddressProperty);

        StringProperty phoneNumberProperty = user.getProperty("phoneNumber");
        assertNotNull(phoneNumberProperty);

        StringProperty fullnameProperty = user.getProperty("fullname");
        assertNotNull(fullnameProperty);

        StringProperty uploaderIDProperty = user.getProperty("uploaderID");
        assertNotNull(uploaderIDProperty);

        StringProperty usernameProperty = user.getProperty("username");
        assertNotNull(usernameProperty);

        StringProperty nonexistentProperty = user.getProperty("nonexistentProperty");
        assertNull(nonexistentProperty);
    }

    
    @Test
    public void testGetPropertyForUsername() {
        assertEquals("johndoe", user.getProperty("username").get());
    }

    @Test
    public void testGetPropertyForNonexistentProperty() {
        assertNull(user.getProperty("nonexistentProperty"));
    }
}


