package com.preplane.dev.models;

public class VoteThread {
    private int threadId;
    private int userId;
    private int vote;

    public VoteThread(){}

    public VoteThread(int threadId, int userId, int vote){
        this.threadId = threadId;
        this.userId = userId;
        this.vote = vote;
    }

    public int getThreadId(){
        return threadId;
    }

    public void setThreadId(int threadId){
        this.threadId = threadId;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getVote(){
        return vote;
    }

    public void setVote(int vote){
        this.vote = vote;
    }
}