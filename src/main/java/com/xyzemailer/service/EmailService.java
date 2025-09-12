package com.xyzemailer.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xyzemailer.dto.EmailDTO;
import com.xyzemailer.util.JDBCConnection;

public class EmailService {

	// COMPOSE EMAIL
	public static boolean composeEmail(String senderEmail, String recepientEmail, String subject, String body, String status) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		boolean validToSend = false;
		

		
		int senderId = UserService.getUserIdByEmail(senderEmail);
		int recepientId = UserService.getUserIdByEmail(recepientEmail);
		
		if (senderId == -1 || recepientId == -1) {
			System.err.println("Invalid emails");
			return validToSend;
		}
		
		String insertQuery = "INSERT INTO emails (sender_id, recepient_id, subject, body, status, sent_time) " +
                "VALUES (?, ?, ?, ?, ?, NOW())";

		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setInt(1, senderId);
			preparedStatement.setInt(2, recepientId);
			preparedStatement.setString(3, subject);
			preparedStatement.setString(4, body);
			preparedStatement.setString(5, status);
			
			int rowsInserted = preparedStatement.executeUpdate();
			
			if (rowsInserted > 0) {
				validToSend = true;
				System.out.println("Email saved to "+ status);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	        if (connection != null) {
	            connection.close();
	        }
		}
		return validToSend;
	}	
	
	
	
	
	// GET INBOX
	public static List<EmailDTO> getInboxEmails(int recepientId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<EmailDTO> emails = new ArrayList<>();
		String selectQuery = """
	            SELECT e.email_id,
                e.sender_id,
                e.recepient_id,
                e.subject,
                e.body,
                e.status,
                e.sent_time,
                u.email AS sender_email
         FROM emails e
         JOIN users u ON e.sender_id = u.user_id
         WHERE e.recepient_id = ?
           AND e.status = 'sent'
         ORDER BY e.sent_time DESC
     """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,  recepientId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setStatus(resultSet.getString("status"));
                email.setSentTime(resultSet.getTimestamp("sent_time"));
				emails.add(email);
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
		return emails;
		
	}
	
	
	// GET SENT
	public static List<EmailDTO> getSentEmails(int userId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<EmailDTO> emails = new ArrayList<>();
		String selectQuery = """
			    SELECT e.email_id,
		           e.sender_id,
		           e.recepient_id,
		           e.subject,
		           e.body,
		           e.status,
		           e.sent_time,
		           u.email AS recepient_email
		    FROM emails e
		    JOIN users u ON e.recepient_id = u.user_id
		    WHERE e.sender_id = ?
		      AND e.status = 'sent'
		    ORDER BY e.sent_time DESC
		    """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,  userId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setStatus(resultSet.getString("status"));
                email.setSentTime(resultSet.getTimestamp("sent_time"));
				emails.add(email);
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
		return emails;
		
	}
	
	// GET DRAFT
	public static List<EmailDTO> getDraftEmails(int userId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<EmailDTO> emails = new ArrayList<>();
		String selectQuery = """
			    SELECT e.email_id,
		           e.sender_id,
		           e.recepient_id,
		           e.subject,
		           e.body,
		           e.status,
		           e.sent_time,
		           u.email AS recepient_email
		    FROM emails e
		    JOIN users u ON e.recepient_id = u.user_id
		    WHERE e.sender_id = ?
		      AND e.status = 'draft'
		    ORDER BY e.sent_time DESC
		    """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,  userId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setStatus(resultSet.getString("status"));
                email.setSentTime(resultSet.getTimestamp("sent_time"));
				emails.add(email);
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
		return emails;
		
	}
	
	

}
