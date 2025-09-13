package com.xyzemailer.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.xyzemailer.dto.UserDTO;
import com.xyzemailer.util.JDBCConnection;

public class UserService {
    
    public static int getUserIdByEmail(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String selectQuery = "SELECT user_id FROM users WHERE email = ?";
        
        try {
            connection = JDBCConnection.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);  
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return -1;
    }

    public static String getEmailById(int userId) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String selectQuery = "SELECT email FROM users WHERE user_id = ?";
        
        try {
            connection = JDBCConnection.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setInt(1, userId); 
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return "";
    }
    
    public static boolean registerUser(UserDTO userDTO) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean isRegistered = false;
        
        String insertQuery = "INSERT INTO users(first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
        
        try {
            connection = JDBCConnection.getConnection();
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, userDTO.getFirstName());
            preparedStatement.setString(2, userDTO.getLastName());
            preparedStatement.setString(3, userDTO.getEmail());
            preparedStatement.setString(4, userDTO.getPassword());
            int rowsCounted = preparedStatement.executeUpdate();
            
            if (rowsCounted > 0) {
                isRegistered = true;
            }
        }
        catch (SQLException e) {
            System.err.println("Error: "+ e.getMessage());
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return isRegistered;
    }
    
    public static boolean existingUser(String email) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Boolean isExisting = false;
        
        String selectQuery = "SELECT 1 FROM users WHERE email = ?";
        
        try {
            connection = JDBCConnection.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, email);
            
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                isExisting = true;
            }
        }
        catch (SQLException e) {
            System.err.println("Error: "+ e.getMessage());
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return isExisting;
    }
    
    public static boolean authenticateUser(String email, String password) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Boolean isValid = false;
        
        String selectQuery = "SELECT email, password FROM users WHERE email = ? AND password = ?";
        
        try {
            connection = JDBCConnection.getConnection();
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1,  email);
            preparedStatement.setString(2,  password);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                isValid = true;
            }
        }
        catch (SQLException e) {
            System.err.println("Error: "+ e.getMessage());
        }
        finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return isValid;
    }
}