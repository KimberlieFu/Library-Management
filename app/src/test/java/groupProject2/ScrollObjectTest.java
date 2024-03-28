package groupProject2;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;


public class ScrollObjectTest {
    private ScrollObject scrollObject;

    @BeforeEach
    public void setUp() {
        scrollObject = new ScrollObject("1", "Scroll 1", "user123", new File("scroll1.txt"));
    }
    @Test
    void testGetters() {
        ScrollObject scrollObject = new ScrollObject("1", "Scroll1", "user1", new File("scroll1.txt"));

        assertEquals("Scroll1", scrollObject.getScrollName());
        assertEquals("user1", scrollObject.getUploaderId());
        assertEquals(new File("scroll1.txt"), scrollObject.getContent());
        assertEquals("1", scrollObject.getScrollId());
    }

    @Test
    void testSetters() {
        ScrollObject scrollObject = new ScrollObject("1", "Scroll1", "user1", new File("scroll1.txt"));

        scrollObject.setScrollName("UpdatedScroll");
        scrollObject.setUploaderId("updatedUser");
        scrollObject.setContent(new File("updatedScroll.txt"));

        assertEquals("UpdatedScroll", scrollObject.getScrollName());
        assertEquals("updatedUser", scrollObject.getUploaderId());
        assertEquals(new File("updatedScroll.txt"), scrollObject.getContent());
    }

    @Test
    void testSetScrollId() {
        ScrollObject scrollObject = new ScrollObject("1", "Scroll1", "user1", new File("scroll1.txt"));

        scrollObject.setScrollId("2");

        assertEquals("2", scrollObject.getScrollId());
    }


    @Test
    public void testGetProperty() {
        assertNotNull(scrollObject.getProperty("scrollID"));
        assertNotNull(scrollObject.getProperty("name"));
        assertNotNull(scrollObject.getProperty("uploaderID"));
        assertNotNull(scrollObject.getProperty("date"));
        assertNotNull(scrollObject.getProperty("downloadTime"));
        assertNotNull(scrollObject.getProperty("uploadTime"));
        assertNotNull(scrollObject.getProperty("updateTime"));
        assertNull(scrollObject.getProperty("nonexistentProperty"));
    }

}
