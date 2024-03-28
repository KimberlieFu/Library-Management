package groupProject2;


import groupProject2.DataBaseMethod;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;
import java.sql.Date;



public class User {
    private String userID;
    private String password;
    private String identity;
    private String emailAddress;
    private String phoneNumber;
    private String fullname;
    private String uploaderID;
    private String username;


    public User(String userID, String password, String identity, String emailAddress, String phoneNumber, String fullname, String username) {
        this.userID = userID;
        this.password = password;
        this.identity = identity;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.fullname = fullname;
        this.uploaderID = null;
        this.username = username;
    }


    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public String getIdentity() {
        return identity;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUploaderID() {
        return uploaderID;
    }

    public String getUsername() {
        return username;
    }

    public void setUserID(String s) {
        DataBaseMethod.updateData("user", "userID", s, "emailAddress", this.emailAddress);
        this.userID = s;
    }

    public void setPassword(String s) {
        
        DataBaseMethod.updateData("user", "password", s, "userID", this.userID);
        this.password = s;
    }

    public void setIdentity(String s) {
        DataBaseMethod.updateData("user", "identity", s, "userID", this.userID);
        this.identity = s;
    }

    public void setEmailAddress(String s) {
        DataBaseMethod.updateData("user", "emailAddress", s, "userID", this.userID);
        this.emailAddress = s;
    }

    public void setPhoneNumber(String s) {
        DataBaseMethod.updateData("user", "phoneNumber", s, "userID", this.userID);
        this.phoneNumber = s;
    }

    public void setFullname(String s) {
        DataBaseMethod.updateData("user", "fullname", s, "userID", this.userID);
        this.fullname = s;
    }

    public void setUploaderID(String s) {
        DataBaseMethod.updateData("user", "uploaderID", s, "userID", this.userID);
        this.uploaderID = s;
    }

    public void setUsername(String s) {
        DataBaseMethod.updateData("user", "username", s, "userID", this.userID);
        this.username = s;
    }

    public void uploadScroll(int id, String name, Date uploadDate, File binaryFile){
        setUploaderID(this.userID);
        DataBaseMethod.uploadBinaryFile(id, name, this.uploaderID, uploadDate, binaryFile);

    }


    public StringProperty getProperty(String propertyName) {
        final StringProperty userIDPlace = new SimpleStringProperty(userID);
        final StringProperty usernamePlace = new SimpleStringProperty(username);
        final StringProperty identityPlace = new SimpleStringProperty(identity);
        final StringProperty emailAddressPlace = new SimpleStringProperty(emailAddress);
        final StringProperty phoneNumberPlace = new SimpleStringProperty(phoneNumber);
        final StringProperty fullnamePlace = new SimpleStringProperty(fullname);
        final StringProperty uploaderIDPlace = new SimpleStringProperty(uploaderID);



        switch (propertyName) {
            case "userID":
                return userIDPlace;
            case "identity":
                return identityPlace;
            case "emailAddress":
                return emailAddressPlace;
            case "phoneNumber":
                return phoneNumberPlace;
            case "fullname":
                return fullnamePlace;
            case "uploaderID":
                return uploaderIDPlace;
            case "username":
                return usernamePlace;
            default:
                return null;

        }
    }
}
