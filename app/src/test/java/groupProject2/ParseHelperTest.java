package groupProject2;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParseHelperTest {
    @Test
    public void testParseUserData() {
        String input = "userID: 1\n" +
                "username: user1\n" +
                "identity: User\n" +
                "emailAddress: user1@example.com\n" +
                "phoneNumber: 123456789\n" +
                "fullname: John Doe\n" +
                "\n" +
                "userID: 2\n" +
                "username: user2\n" +
                "identity: Admin\n" +
                "emailAddress: user2@example.com\n" +
                "phoneNumber: 987654321\n" +
                "fullname: Jane Smith\n" +
                "\n" +
                "userID: 3\n" +
                "username: user3\n" +
                "identity: User\n" +
                "emailAddress: user3@example.com\n" +
                "phoneNumber: 555555555\n" +
                "fullname: Alice Johnson\n";

        List<User> users = ParseHelper.parseUserData(input);
        assertNotNull(users);
        assertEquals(3, users.size());

        User user1 = users.get(0);
        assertEquals("1", user1.getUserID());
        assertEquals("user1", user1.getUsername());
        assertEquals("User", user1.getIdentity());
        assertEquals("user1@example.com", user1.getEmailAddress());
        assertEquals("123456789", user1.getPhoneNumber());
        assertEquals("John Doe", user1.getFullname());
        assertNull(user1.getUploaderID());

        User user2 = users.get(1);
        assertEquals("2", user2.getUserID());
        assertEquals("user2", user2.getUsername());
        assertEquals("Admin", user2.getIdentity());
        assertEquals("user2@example.com", user2.getEmailAddress());
        assertEquals("987654321", user2.getPhoneNumber());
        assertEquals("Jane Smith", user2.getFullname());
        assertNull(user2.getUploaderID());

        User user3 = users.get(2);
        assertEquals("3", user3.getUserID());
        assertEquals("user3", user3.getUsername());
        assertEquals("User", user3.getIdentity());
        assertEquals("user3@example.com", user3.getEmailAddress());
        assertEquals("555555555", user3.getPhoneNumber());
        assertEquals("Alice Johnson", user3.getFullname());
        assertNull(user3.getUploaderID());
    }

    @Test
    public void testParseScrollData() {
        List<List<String>> input = List.of(
                List.of("1", "Scroll 1"),
                List.of("2", "Scroll 2"),
                List.of("3", "Scroll 3")
        );

        List<ScrollObject> scrolls = ParseHelper.parseScrollData(input);
        assertNotNull(scrolls);
        assertEquals(3, scrolls.size());

        ScrollObject scroll1 = scrolls.get(0);
        assertEquals("1", scroll1.getScrollId());
        assertEquals("Scroll 1", scroll1.getScrollName());
        assertNull(scroll1.getUploaderId());
        assertNull(scroll1.getContent());

        ScrollObject scroll2 = scrolls.get(1);
        assertEquals("2", scroll2.getScrollId());
        assertEquals("Scroll 2", scroll2.getScrollName());
        assertNull(scroll2.getUploaderId());
        assertNull(scroll2.getContent());

        ScrollObject scroll3 = scrolls.get(2);
        assertEquals("3", scroll3.getScrollId());
        assertEquals("Scroll 3", scroll3.getScrollName());
        assertNull(scroll3.getUploaderId());
        assertNull(scroll3.getContent());
    }


    @Test
    public void testParseUserDataWithUploaderID() {
        String input = "userID: 1\n" +
                "username: user1\n" +
                "identity: User\n" +
                "emailAddress: user1@example.com\n" +
                "phoneNumber: 123456789\n" +
                "fullname: John Doe\n" +
                "uploaderID: 10\n" +
                "\n" +
                "userID: 2\n" +
                "username: user2\n" +
                "identity: Admin\n" +
                "emailAddress: user2@example.com\n" +
                "phoneNumber: 987654321\n" +
                "fullname: Jane Smith\n" +
                "\n" +
                "userID: 3\n" +
                "username: user3\n" +
                "identity: User\n" +
                "emailAddress: user3@example.com\n" +
                "phoneNumber: 555555555\n" +
                "fullname: Alice Johnson\n";

        List<User> users = ParseHelper.parseUserData(input);
        assertNotNull(users);
        assertEquals(3, users.size());

        User user1 = users.get(0);
        assertEquals("1", user1.getUserID());
        assertEquals("user1", user1.getUsername());
        assertEquals("User", user1.getIdentity());
        assertEquals("user1@example.com", user1.getEmailAddress());
        assertEquals("123456789", user1.getPhoneNumber());
        assertEquals("John Doe", user1.getFullname());
        assertEquals("10", user1.getUploaderID());
    }



}


