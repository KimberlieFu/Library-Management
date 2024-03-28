package groupProject2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    //Test that the constructor is able to set the property correctly
    @Test
    void testAdminConstructor() {
        Admin admin = new Admin("admin123", "adminPass", "admin", "admin@example.com", "0412345678", "Admin Fullname", "admin1");

        assertEquals("admin123", admin.getUserID());
        assertEquals("adminPass", admin.getPassword());
        assertEquals("admin", admin.getIdentity());
        assertEquals("admin@example.com", admin.getEmailAddress());
        assertEquals("0412345678", admin.getPhoneNumber());
        assertEquals("Admin Fullname", admin.getFullname());
        assertEquals("admin1", admin.getUsername());
    }

    //Tests that the setter method inherited from the parent class User works properly
    @Test
    void testSetterMethods() {
        Admin admin = new Admin("admin123", "adminPass", "admin", "admin@example.com", "0412345678", "Admin Fullname", "admin1");

        admin.setUserID("newAdmin123");
        admin.setPassword("newAdminPass");
        admin.setIdentity("newAdmin");
        admin.setEmailAddress("newAdmin@example.com");
        admin.setPhoneNumber("0412345678");
        admin.setFullname("New Admin Fullname");
        admin.setUsername("New admin1");
        admin.setUploaderID("uploader123");

        assertEquals("newAdmin123", admin.getUserID());
        assertEquals("newAdminPass", admin.getPassword());
        assertEquals("newAdmin", admin.getIdentity());
        assertEquals("newAdmin@example.com", admin.getEmailAddress());
        assertEquals("0412345678", admin.getPhoneNumber());
        assertEquals("New Admin Fullname", admin.getFullname());
        assertEquals("New admin1", admin.getUsername());
        assertEquals("uploader123", admin.getUploaderID());
    }
}


