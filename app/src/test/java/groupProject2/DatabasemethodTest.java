package groupProject2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.sql.*;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DatabasemethodTest {
    static DataBaseMethod dbm = new DataBaseMethod();

    @Test
    public void testAddSearchDeleteAndUpdateData(){
        String password = PasswordUtils.hashPassword("2");
        dbm.addData("testuser", "2", password , "admin", "1.@a.com", "0401888542", "2", "2");
        int numOfDataByUsername = dbm.searchNumOfData("testuser", "username", "2");
        String hashedPassword = dbm.searchSpecificData("testuser", "password", "userID", "2");
        int numOfAllData = dbm.searchNumOfData("testuser");
        assertEquals(1, numOfDataByUsername);
        assertEquals(1, numOfAllData);
        assertEquals(PasswordUtils.hashPassword("2"), hashedPassword);
        dbm.updateData("testuser", "fullname", "A", "userID", "2");
        String s = dbm.searchSpecificData("testuser", "fullname", "userID", "2");
        assertEquals("A", s);
        int numOfAllData2 = dbm.searchNumOfData("testuser","fullname","A");
        assertEquals(1, numOfAllData2);
        dbm.delData("", "testuser", "userID", "2");
        int numOfDataAfterDelete = dbm.searchNumOfData("testuser");
        assertEquals(0, numOfDataAfterDelete);
    }


    @Test
    public void testAddAndUpdatePng(){
        String tableName = "testscroll";
        String scrollID = "1";
        String name = "test.png";
        String uploaderID = "user123";
        String uploadDate = "2023-10-24";
        File content = new File(getClass().getResource("/test.txt").getFile());
        dbm.addPng(tableName, scrollID, name, uploaderID, uploadDate, content);
        int numOfDataAfteradding = dbm.searchNumOfData("testscroll");
        assertEquals(1, numOfDataAfteradding);
        File content2 = new File(getClass().getResource("/test2.txt").getFile());
        dbm.updateBianryFile(content2,"1");
        File f = dbm.getBlobAsFile("testscroll", "content","name", "test.png");
        assertNotNull(f);
        dbm.delData("", "testscroll", "name", "test.png");
        int numOfDataAfterDelete = dbm.searchNumOfData("testscroll");
        assertEquals(0, numOfDataAfterDelete);
    }

    @Test
    public void testAddAndUpdateTxt() {
        String tableName = "testscroll";
        String scrollID = "2";
        String name = "test.txt";
        String uploaderID = "user123";
        String uploadDate = "2023-10-24";


        File content = new File(getClass().getResource("/test.txt").getFile());

        dbm.addTxt(tableName, scrollID, name, uploaderID, uploadDate, content);
        int numOfDataAfteradding = dbm.searchNumOfData("testscroll");
        assertEquals(1, numOfDataAfteradding);
        File content2 = new File(getClass().getResource("/test2.txt").getFile());

        dbm.updateBianryFile(content2, "1");
        File f = dbm.getBlobAsFile("testscroll", "content", "name", "test.txt");
        assertNotNull(f);
        dbm.delData("", "testscroll", "name", "test.txt");
        int numOfDataAfterDelete = dbm.searchNumOfData("testscroll");
        assertEquals(0, numOfDataAfterDelete);
    }


//    @Test
//    public void updateImageTest(){
//        File content = new File("C:\\Users\\Mamba4live\\Desktop\\Lab05-Vinit-Group1-A2\\app\\src\\test\\resources\\test2.png");
//        dbm.addData("testuser", "2", "2", "admin", "1.@a.com", "0401888542", "2", "2");
//        dbm.updateImage("testuser", content, "2");
//        File f = dbm.getBlobAsFile("testuser", "profilephoto","userID", "2");
//        assertNotNull(f);
//        dbm.delData("", "testuser", "userID", "2");
//    }



    @Test
    public void testBrowseusers(){
        String password = PasswordUtils.hashPassword("2");
        dbm.addData("testuser", "2", password , "admin", "1.@a.com", "0401888542", "2", "2");
        String s = dbm.browseUsers("testuser");
        String result = "\nuserID: 2\nidentity: admin\nemailAddress: 1.@a.com\nphoneNumber: 0401888542\nfullname: 2\nuploaderID: null\nusername: 2\n";
        assertEquals(s, result);
        dbm.delData("", "testuser", "userID", "2");
    }

    @Test
    public void testBrowseScrolls() {
        String tableName = "testscroll";
        String scrollID = "1";
        String name = "test.png";
        String uploaderID = "user123";
        String uploadDate = "2023-10-24";

        // Use ClassLoader to load the resource
        ClassLoader classLoader = getClass().getClassLoader();
        File content = new File(classLoader.getResource("test.png").getFile());

        dbm.addPng(tableName, scrollID, name, uploaderID, uploadDate, content);
        List<List<String>> resultList = dbm.browseScrolls("testScroll");
        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        List<String> row = resultList.get(0);
        assertEquals("PNG-" + scrollID, row.get(0));
        assertEquals(name, row.get(1));
        dbm.delData("", "testscroll", "name", "test.png");
    }



    @Test
    public void testGetUserScrolls(){
        String password = PasswordUtils.hashPassword("2");
        dbm.addData("testuser", "2", password , "admin", "1.@a.com", "0401888542", "2", "2");
        List<String> userScrolls = dbm.getUserScrolls("testscroll", "2");
        assertEquals(Collections.emptyList(),userScrolls);
        dbm.delData("", "testuser", "userID", "2");
    }



    @Test
    public void testSearchAllData(){
        List<String> result = new ArrayList<>(2);
        result.add("2");
        String password = PasswordUtils.hashPassword("2");
        dbm.addData("testuser", "2", password , "admin", "1.@a.com", "0401888542", "2", "2");
        List<String> data = dbm.searchAllData("testuser", "fullname");
        assertEquals(result,data);
        dbm.delData("", "testuser", "userID", "2");
    }


    @Test
    public void testSearchSpecificDataByKeyWord(){
        List<String> result = new ArrayList<>(2);
        result.add("2");
        String password = PasswordUtils.hashPassword("2");
        dbm.addData("testuser", "2", password , "admin", "1.@a.com", "0401888542", "2", "2");
        List<String> data = dbm.searchSpecificDataByKeyWord("testuser", "fullname","emailAddress","a");
        assertEquals(result,data);
        dbm.delData("", "testuser", "userID", "2");
    }
}
