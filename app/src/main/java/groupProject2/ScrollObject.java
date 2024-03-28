package groupProject2;

import groupProject2.DataBaseMethod;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

public class ScrollObject {
    private String scrollId;
    private String scrollName;
    private String uploaderId;
    private File content;
    private String downloadTime;
    private String uploadTime;
    private String updateTime;
    private String date;


    public ScrollObject(String scrollId, String scrollName, String uploaderId, File content) {
        this.scrollId = scrollId;
        this.scrollName = scrollName;
        this.uploaderId = uploaderId;
        this.content = content;

        getDownloadTime();
        getUpdateTime();
        getUploadTime();
        getDate();
    }

    public String getScrollName() {
        return scrollName;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public File getContent() {
        return content;
    }

    public void setScrollId(String scrollId) {
        this.scrollId = scrollId;
    }

    public void setScrollName(String scrollName) {
        DataBaseMethod.updateData("Scroll", "name", scrollName, "uploaderID", this.uploaderId);
        this.scrollName = scrollName;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public String getDownloadTime() {
        downloadTime = DataBaseMethod.searchSpecificData("Scroll", "downloadTime", "ScrollID", scrollId);
        return downloadTime;
    }

    public String getUploadTime() {
        uploadTime = DataBaseMethod.searchSpecificData("Scroll", "uploadTime", "ScrollID", scrollId);
        return uploadTime;
    }

    public String getUpdateTime() {
        updateTime = DataBaseMethod.searchSpecificData("Scroll", "updateTime", "ScrollID", scrollId);
        return updateTime;
    }

    public String getDate() {
        date = DataBaseMethod.searchSpecificData("Scroll", "uploadDate", "ScrollID", scrollId);
        return date;
    }

    public void setContent(File content) {
        this.content = content;
    }

    public String getScrollId() {
        return scrollId;
    }



    public StringProperty getProperty(String propertyName) {
        final StringProperty scrollIDPlace = new SimpleStringProperty(scrollId);
        final StringProperty namePlace = new SimpleStringProperty(scrollName);
        final StringProperty uploaderIDPlace = new SimpleStringProperty(uploaderId);
        final StringProperty datePlace = new SimpleStringProperty(date);
        final StringProperty downloadTimePlace = new SimpleStringProperty(downloadTime);
        final StringProperty uploadTimePlace = new SimpleStringProperty(uploadTime);
        final StringProperty updateTimePlace = new SimpleStringProperty(updateTime);

        switch (propertyName) {
            case "scrollID":
                return scrollIDPlace;
            case "name":
                return namePlace;
            case "uploaderID":
                return uploaderIDPlace;
            case "date":
                return datePlace;
            case "downloadTime":
                return downloadTimePlace;
            case "uploadTime":
                return uploadTimePlace;
            case "updateTime":
                return updateTimePlace;
            default:
                return null;
        }
    }
}


