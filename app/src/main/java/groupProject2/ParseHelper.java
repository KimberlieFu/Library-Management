package groupProject2;

import java.util.ArrayList;
import java.util.List;

public class ParseHelper {
    public static List<User> parseUserData(String input) {
        List<User> users = new ArrayList<>();
        String[] userStrings = input.split("\n\n"); // Split by double line breaks

        for (String userString : userStrings) {
            User user = parseUser(userString);
            if (user != null) {
                users.add(user);
            }
        }

        return users;
    }

    public static List<ScrollObject> parseScrollData(List<List<String>> input) {
        List<ScrollObject> scrolls = new ArrayList<>();

        for (List scrollInfo : input) {
            ScrollObject scroll = new ScrollObject((String) scrollInfo.get(0), (String) scrollInfo.get(1), null, null);
            scrolls.add(scroll);
        }

        return scrolls;
    }



    private static User parseUser(String userString) {
        String[] lines = userString.split("\n");

        String userID = null;
        String username = null;
        String identity = null;
        String emailAddress = null;
        String phoneNumber = null;
        String fullname = null;
        String uploaderID = null;

        for (String line : lines) {
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                String key = parts[0];
                String value = parts[1];

                switch (key) {
                    case "userID":
                        userID = value;
                        break;
                    case "identity":
                        identity = value;
                        break;
                    case "emailAddress":
                        emailAddress = value;
                        break;
                    case "phoneNumber":
                        phoneNumber = value;
                        break;
                    case "fullname":
                        fullname = value;
                        break;
                    case "uploaderID":
                        uploaderID = value;
                        break;
                    case "username":
                        username = value;
                    default:
                        // Handle unknown properties or ignore them
                }
            }
        }

        if (userID != null && identity != null && emailAddress != null && phoneNumber != null && fullname != null && username != null) {
            User user = new User(userID, "", identity, emailAddress, phoneNumber, fullname, username);
            user.setUploaderID(uploaderID);
            return user;
        }

        return null;
    }
}
