package com.ron.test.threadlocal.goodsample;

public class Context {

    private String transactionId = null;
    
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}