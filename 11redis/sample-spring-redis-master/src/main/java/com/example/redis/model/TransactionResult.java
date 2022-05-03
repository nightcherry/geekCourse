package com.example.redis.model;

public class TransactionResult {
    // 1 for OK, 0 for fail
    private int status;
    // error msg
    private String message;
    // current stock
    private long current;

    private TransactionResult(int status, long current){
        this.status = status;
        this.current = current;
    }

    private TransactionResult(int status, String message){
        this.status = status;
        this.message = message;
    }

    public static TransactionResult succ(long current){
        return new TransactionResult(1, current);
    }

    public static TransactionResult fail(String message){
        return new TransactionResult(0, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }
}
