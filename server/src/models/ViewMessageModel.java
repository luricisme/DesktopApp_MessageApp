package models;

import java.sql.Timestamp;

public class ViewMessageModel {

    private long messageId;
    private int fromUserId;
    private int toUserId;
    private int messageType;
    private String content;
    private Integer fileId;
    private Timestamp createdAt;
    private boolean isRead;
    
    public ViewMessageModel(){
        
    }

    public ViewMessageModel(long messageId, int fromUserId, int toUserId, int messageType, String content,
            Integer fileId, Timestamp createdAt, boolean isRead) {
        this.messageId = messageId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.messageType = messageType;
        this.content = content;
        this.fileId = fileId;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
