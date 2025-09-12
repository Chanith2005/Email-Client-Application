package com.xyzemailer.dto;

import java.sql.Timestamp;

public class EmailDTO {
	
	private int senderId;
	private int recepientId;
	private String subject;
	private String body;
	private String status;
	private Timestamp sentTime;
	
	public EmailDTO() {}
	
	public EmailDTO(int senderId, int recepientId, String subject, String body, String status, Timestamp sentTime) {
	
		this.senderId = senderId;
		this.recepientId = recepientId;
		this.subject = subject;
		this.body = body;
		this.status = status;
		this.sentTime = sentTime;
	}

	public int getSenderId() {
		return senderId;
	}

	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}

	public int getRecepientId() {
		return recepientId;
	}

	public void setRecepientId(int recepientId) {
		this.recepientId = recepientId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getSentTime() {
		return sentTime;
	}

	public void setSentTime(Timestamp timestamp) {
		this.sentTime = timestamp;
	}

}
