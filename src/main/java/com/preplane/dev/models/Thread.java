package com.preplane.dev.models;

import java.util.Date;

public class Thread {
    private int threadId;
    private String title;
    private String content;
    private Date createdAt;
    private int userCreated; // Foreign Key from Users
    private User creator;
    private int upvotes;
    private int downvotes;

    public Thread() {
    }

    public Thread(String title, String content, int userCreated) {
        this.title = title;
        this.content = content;
        this.userCreated = userCreated;
    }

    public User getCreator() {
        return this.creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(int userCreated) {
        this.userCreated = userCreated;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }

    @Override
    public String toString() {
        return "Thread {\n" +
                "  threadId = " + this.threadId + ",\n" +
                "  title = '" + this.title + "',\n" +
                "  content = '" + this.content + "',\n" +
                "  createdAt = " + this.createdAt + ",\n" +
                "  userCreated = " + this.userCreated + "\n" +
                "  upvotes = " + this.upvotes + "\n" +
                "  downvotes = " + this.downvotes + "\n" +
                "}";
    }
}
