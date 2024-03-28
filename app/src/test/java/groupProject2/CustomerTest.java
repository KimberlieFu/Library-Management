package groupProject2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    // Test the getter methods inherited from the User class.
    @Test
    void testCustomerConstructorAndInheritance() {
        Customer customer = new Customer("testID", "testPassword", "customer", "test@example.com", "0411234567", "John Doe", "John");

        assertEquals("testID", customer.getUserID());
        assertEquals("testPassword", customer.getPassword());
        assertEquals("customer", customer.getIdentity());
        assertEquals("test@example.com", customer.getEmailAddress());
        assertEquals("0411234567", customer.getPhoneNumber());
        assertEquals("John Doe", customer.getFullname());
        assertEquals("John", customer.getUsername());
        assertNull(customer.getUploaderID()); // No new fields have been added to the Customer class and the uploaderID should be null.
    }
}

