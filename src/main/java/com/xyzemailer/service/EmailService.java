// Updated EmailService.java - Now properly sets sender and recipient status
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
		
		String insertQuery = "INSERT INTO emails (sender_id, recepient_id, subject, body, sender_status, recipient_status, sent_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, NOW())";

		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(insertQuery);
			
			preparedStatement.setInt(1, senderId);
			preparedStatement.setInt(2, recepientId);
			preparedStatement.setString(3, subject);
			preparedStatement.setString(4, body);
			
		
			if ("draft".equals(status)) {
				preparedStatement.setString(5, "draft"); 
				preparedStatement.setString(6, null);     
			} else {
				preparedStatement.setString(5, "sent");   
				preparedStatement.setString(6, "inbox");  
			}
			
			int rowsInserted = preparedStatement.executeUpdate();
			
			if (rowsInserted > 0) {
				validToSend = true;
				System.out.println("Email saved as " + status);
			} else {
				System.out.println("No rows were inserted");
			}
			
		}
		catch (SQLException e) {
			System.err.println("SQL Exception in composeEmail: " + e.getMessage());
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
	
	
	// GET EMAIL BY ID
	public static EmailDTO getEmailById(int emailId, int userId) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    String selectQuery = """
	        SELECT e.email_id, e.sender_id, e.recepient_id, e.subject, e.body, 
	               e.sender_status, e.recipient_status, e.sent_time
	        FROM emails e
	        WHERE e.email_id = ? 
	          AND (e.sender_id = ? OR e.recepient_id = ?)
	          AND ((e.sender_id = ? AND e.sender_status != 'deleted') 
	               OR (e.recepient_id = ? AND e.recipient_status != 'deleted'))
	        """;
	    
	    try {
	        connection = JDBCConnection.getConnection();
	        preparedStatement = connection.prepareStatement(selectQuery);
	        preparedStatement.setInt(1, emailId);
	        preparedStatement.setInt(2, userId);
	        preparedStatement.setInt(3, userId);
	        preparedStatement.setInt(4, userId);
	        preparedStatement.setInt(5, userId);
	        resultSet = preparedStatement.executeQuery();
	        
	        if (resultSet.next()) {
	            EmailDTO email = new EmailDTO();
	            email.setEmailId(resultSet.getInt("email_id"));
	            email.setSenderId(resultSet.getInt("sender_id"));
	            email.setRecepientId(resultSet.getInt("recepient_id"));
	            email.setSubject(resultSet.getString("subject"));
	            email.setBody(resultSet.getString("body"));
	            email.setSenderStatus(resultSet.getString("sender_status"));
	            email.setRecipientStatus(resultSet.getString("recipient_status"));
	            email.setSentTime(resultSet.getTimestamp("sent_time"));
	            return email;
	        }
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally {
	        if (resultSet != null) resultSet.close();
	        if (preparedStatement != null) preparedStatement.close();
	        if (connection != null) connection.close();
	    }
	    return null;
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
                e.sender_status,
                e.recipient_status,
                e.sent_time,
                u.email AS sender_email
         FROM emails e
         JOIN users u ON e.sender_id = u.user_id
         WHERE e.recepient_id = ?
           AND e.recipient_status = 'inbox'
         ORDER BY e.sent_time DESC
     """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,  recepientId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setEmailId(resultSet.getInt("email_id"));
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setSenderStatus(resultSet.getString("sender_status"));
                email.setRecipientStatus(resultSet.getString("recipient_status"));
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
		           e.sender_status,
		           e.recipient_status,
		           e.sent_time,
		           u.email AS recepient_email
		    FROM emails e
		    JOIN users u ON e.recepient_id = u.user_id
		    WHERE e.sender_id = ?
		      AND e.sender_status = 'sent'
		    ORDER BY e.sent_time DESC
		    """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,  userId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setEmailId(resultSet.getInt("email_id"));
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setSenderStatus(resultSet.getString("sender_status"));
                email.setRecipientStatus(resultSet.getString("recipient_status"));
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
		           e.sender_status,
		           e.recipient_status,
		           e.sent_time,
		           u.email AS recepient_email
		    FROM emails e
		    JOIN users u ON e.recepient_id = u.user_id
		    WHERE e.sender_id = ?
		      AND e.sender_status = 'draft'
		    ORDER BY e.sent_time DESC
		    """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1,  userId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setEmailId(resultSet.getInt("email_id"));
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setSenderStatus(resultSet.getString("sender_status"));
                email.setRecipientStatus(resultSet.getString("recipient_status"));
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
	
	// GET JUNK
	public static List<EmailDTO> getJunkEmails(int userId) throws SQLException {
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
		           e.sender_status,
		           e.recipient_status,
		           e.sent_time,
		           u.email AS other_email
		    FROM emails e
		    JOIN users u ON (CASE WHEN e.sender_id = ? THEN e.recepient_id ELSE e.sender_id END) = u.user_id
		    WHERE ((e.sender_id = ? AND e.sender_status = 'junk') 
		       OR (e.recepient_id = ? AND e.recipient_status = 'junk'))
		    ORDER BY e.sent_time DESC
		    """;
		
		try {
			connection = JDBCConnection.getConnection();
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, userId);
			preparedStatement.setInt(2, userId);
			preparedStatement.setInt(3, userId);
			resultSet = preparedStatement.executeQuery();
			
			while (resultSet.next()) {
				EmailDTO email = new EmailDTO();
				email.setEmailId(resultSet.getInt("email_id"));
				email.setSenderId(resultSet.getInt("sender_id"));
				email.setRecepientId(resultSet.getInt("recepient_id"));
				email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setSenderStatus(resultSet.getString("sender_status"));
                email.setRecipientStatus(resultSet.getString("recipient_status"));
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
	
	// MOVE TO JUNK
	public static boolean moveToJunk(int emailId, int userId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean removed = false;
		
		String selectQuery = "SELECT sender_id, recepient_id FROM emails WHERE email_id = ?";
		
		try {
			connection = JDBCConnection.getConnection();
			

			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, emailId);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int senderId = resultSet.getInt("sender_id");
				int recipientId = resultSet.getInt("recepient_id");
				String updateQuery = "";
				
				if (senderId == userId) {
					updateQuery = "UPDATE emails SET sender_status = 'junk' WHERE email_id = ?";
				} else if (recipientId == userId) {
					updateQuery = "UPDATE emails SET recipient_status = 'junk' WHERE email_id = ?";
				}
				
			
				if (!updateQuery.isEmpty()) {
					preparedStatement = connection.prepareStatement(updateQuery);
					preparedStatement.setInt(1, emailId);
					int rowsUpdated = preparedStatement.executeUpdate();
					
					if (rowsUpdated > 0) {
						System.out.println("Email moved to junk for user: " + userId);
						removed = true;
					}
				}
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
		return removed;
	}
	
	// DELETE FROM JUNK 
	public static boolean deleteJunkEmails(int emailId, int userId) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean deleted = false;
		
	
		String selectQuery = "SELECT sender_id, recepient_id FROM emails WHERE email_id = ?";
		
		try {
			connection = JDBCConnection.getConnection();
			
			preparedStatement = connection.prepareStatement(selectQuery);
			preparedStatement.setInt(1, emailId);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				int senderId = resultSet.getInt("sender_id");
				int recipientId = resultSet.getInt("recepient_id");
				String updateQuery = "";
				
				if (senderId == userId) {
					updateQuery = "UPDATE emails SET sender_status = 'deleted' WHERE email_id = ?";
				} else if (recipientId == userId) {
					updateQuery = "UPDATE emails SET recipient_status = 'deleted' WHERE email_id = ?";
				}
				
				if (!updateQuery.isEmpty()) {
					preparedStatement = connection.prepareStatement(updateQuery);
					preparedStatement.setInt(1, emailId);
					int rowsUpdated = preparedStatement.executeUpdate();
					
					if (rowsUpdated > 0) {
						System.out.println("Email deleted for user: " + userId);
						deleted = true;
					}
				}
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
		return deleted;
	}
}