package groupProject2;

import java.sql.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseMethod {

    public static void addData(String tableName, String userID, String password, String identity, String emailAddress, String phoneNumber, String fullname, String username){
        try{
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "INSERT INTO " + tableName + " (userID, password, identity, emailAddress, phoneNumber, fullname, username) VALUES (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement insert = connection.prepareStatement(query);
            insert.setString(1, userID);
            insert.setString(2, password);
            insert.setString(3, identity);
            insert.setString(4, emailAddress);
            insert.setString(5, phoneNumber);
            insert.setString(6, fullname);
            insert.setString(7, username);
            insert.executeUpdate();
            insert.close();
            connection.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void addPng(String tableName, String scrollID, String name, String uploaderID, String uploadDate, File content){
        try {
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "INSERT INTO " + tableName + " (scrollID, name, uploaderID, uploadDate, content) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement insert = connection.prepareStatement(query);

            insert.setString(1, "PNG-" + scrollID);
            insert.setString(2, name);
            insert.setString(3, uploaderID);
            insert.setString(4, uploadDate);

            // Read the file content into a byte array and set it as a BLOB parameter
            FileInputStream inputStream = new FileInputStream(content);
            insert.setBinaryStream(5, inputStream, (int) content.length());

            insert.executeUpdate();
            insert.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void addTxt(String tableName, String scrollID, String name, String uploaderID, String uploadDate, File content) {
        try {
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "INSERT INTO " + tableName + " (scrollID, name, uploaderID, uploadDate, content) VALUES (?, ?, ?, ?, ?);";
            PreparedStatement insert = connection.prepareStatement(query);

            insert.setString(1, "TXT-" + scrollID);
            insert.setString(2, name);
            insert.setString(3, uploaderID);
            insert.setString(4, uploadDate);

            // Read the file content into a byte array and set it as a BLOB parameter
            FileInputStream inputStream = new FileInputStream(content);
            insert.setBinaryStream(5, inputStream, (int) content.length());

            insert.executeUpdate();
            insert.close();
            connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateImage(String tablename, File imageFile, String userid) {
        Connection connection = null;
        FileInputStream fis = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String updateQuery = "UPDATE " + tablename +" SET profilephoto = ? WHERE userID = ?";
            fis = new FileInputStream(imageFile);
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBinaryStream(1, fis, (int) imageFile.length());
            preparedStatement.setString(2, userid);
            preparedStatement.executeUpdate();
            System.out.println("Image updated in the database successfully.");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateBianryFile(File BianryFile, String ScrollID) {
        Connection connection = null;
        FileInputStream fis = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String updateQuery = "UPDATE scroll SET content = ? WHERE ScrollID = ?";
            fis = new FileInputStream(BianryFile);
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setBinaryStream(1, fis, (int) BianryFile.length());
            preparedStatement.setString(2, ScrollID);
            preparedStatement.executeUpdate();
            System.out.println("Scroll updated in the database successfully.");
            updateNumericalData("scroll", "updateTime", 1, "ScrollID", ScrollID);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void uploadBinaryFile(int id, String name, String uploaderId, Date uploadDate, File binaryFile) {
        Connection connection = null;
        FileInputStream fis = null;
        PreparedStatement preparedStatement = null;
        try{
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String insertQuery = "INSERT INTO scroll (id, name, uploaderID, uploadDate, content) VALUES (?, ?, ?, ?, ?)";
            fis = new FileInputStream(binaryFile);
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, uploaderId);
            preparedStatement.setDate(4, uploadDate);
            preparedStatement.setBinaryStream(5, fis, (int) binaryFile.length());
            preparedStatement.executeUpdate();
            System.out.println("File inserted into the database successfully.");
            updateData("scroll", "updateTime", "updateTime + 1", "name", name);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void delData(String searchRange, String tableName, String colName, String keyword) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "DELETE FROM " + tableName + " WHERE " + colName + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, keyword);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Deleted " + rowsAffected + " row(s) successfully.");
            } else {
                System.out.println("No rows were deleted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Integer searchNumOfData(String tableName, String colName, String keyword){
        try{
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query =  "SELECT COUNT(*) FROM " + tableName + " WHERE " + colName + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, keyword);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            preparedStatement.close();
            connection.close();
            return count;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    };

    public static Integer searchNumOfData(String tableName){
        try{
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query =  "SELECT COUNT(*) FROM " + tableName;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);
            preparedStatement.close();
            connection.close();
            return count;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    };

    public static String searchSpecificData(String tableName, String columnName, String conditionColumn, String conditionValue) {
        String cellValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + conditionColumn + " = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, conditionValue);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                cellValue = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return cellValue;
    }

    public static ArrayList<String>  searchSpecificDataByKeyWord(String tableName, String columnName, String conditionColumn, String keywords) {
        String cellValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "SELECT " + columnName + " FROM " + tableName + " WHERE " + conditionColumn + " LIKE ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%"+keywords+"%");
            resultSet = preparedStatement.executeQuery();
            result = new ArrayList<String>();
            while(resultSet.next()){
                cellValue = resultSet.getString(1);
                result.add(cellValue);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static ArrayList<String>  searchAllData(String tableName, String columnName) {
        String cellValue = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> result = new ArrayList<String>();
        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "SELECT " + columnName + " FROM " + tableName;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            result = new ArrayList<String>();
            while(resultSet.next()){
                cellValue = resultSet.getString(1);
                result.add(cellValue);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void updateData(String tableName, String colName, String newValue, String colNameForCondition, String keyword) {
        try {
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "UPDATE " + tableName + " SET " + colName + " = ? WHERE " + colNameForCondition + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, keyword);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void updateNumericalData(String tableName, String colName, int newValue, String colNameForCondition, String keyword) {
        try {
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "UPDATE " + tableName + " SET " + colName + " = " + colName + " + ? WHERE " + colNameForCondition + " = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, newValue);
            preparedStatement.setString(2, keyword);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static String browseUsers(String tablename){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";


        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "SELECT * FROM " + tablename;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String userID = resultSet.getString("userID");
                result += "\nuserID: " + userID;
                String identity = resultSet.getString("identity");
                result += "\nidentity: " + identity;
                String emailAddress = resultSet.getString("emailAddress");
                result += "\nemailAddress: " + emailAddress;
                String phoneNumber = resultSet.getString("phoneNumber");
                result += "\nphoneNumber: " + phoneNumber;
                String fullname = resultSet.getString("fullname");
                result += "\nfullname: " + fullname;
                String uploaderID = resultSet.getString("uploaderID");
                result += "\nuploaderID: " + uploaderID;
                String username = resultSet.getString("username");
                result += "\nusername: " + username + "\n";
            }

            return result;
        } catch (SQLException e1) {
            e1.printStackTrace();
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    public static List<List<String>> browseScrolls(String tablename) {
        List<List<String>> resultList = new ArrayList<>();
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "SELECT * FROM " + tablename;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<String> row = new ArrayList<>();
                row.add(resultSet.getString("scrollID"));
                row.add(resultSet.getString("name"));
                row.add(resultSet.getString("downloadTime"));
                row.add(resultSet.getString("uploadTime"));
                row.add(resultSet.getString("updateTime"));
                resultList.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }


    public static File getBlobAsFile(String tablename, String range, String conditionCol, String condition) {
        File tempFile = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(App.url, App.username, App.password);
            String sql = "SELECT " + range + " FROM " + tablename + " WHERE " + conditionCol + " = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, condition);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Blob blob = resultSet.getBlob(range);
                tempFile = File.createTempFile("blobData", ".tmp");
                try (OutputStream outputStream = new FileOutputStream(tempFile);
                     InputStream inputStream = blob.getBinaryStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }

    public static List<String> getUserScrolls(String tablename,String userID) {
        List<String> userScrolls = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(App.url, App.username, App.password);
            String query = "SELECT  name FROM  " + tablename +" WHERE uploaderID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String scrollName = resultSet.getString("name");
                userScrolls.add(scrollName);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userScrolls;
    }

}