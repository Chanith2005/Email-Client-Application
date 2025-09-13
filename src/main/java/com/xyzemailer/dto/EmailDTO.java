package com.xyzemailer.dto;

import java.sql.Timestamp;

public class EmailDTO {
    private int emailId;
    private int senderId;
    private int recepientId;
    private String subject;
    private String body;
    private String senderStatus;
    private String recipientStatus;
    private Timestamp sentTime;

    public EmailDTO() {}

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
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

    public String getSenderStatus() {
        return senderStatus;
    }

    public void setSenderStatus(String senderStatus) {
        this.senderStatus = senderStatus;
    }

    public String getRecipientStatus() {
        return recipientStatus;
    }

    public void setRecipientStatus(String recipientStatus) {
        this.recipientStatus = recipientStatus;
    }

    public Timestamp getSentTime() {
        return sentTime;
    }

    public void setSentTime(Timestamp sentTime) {
        this.sentTime = sentTime;
    }
}