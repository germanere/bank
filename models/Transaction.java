package models;

import Utils.Util;

import java.io.Serializable;
import java.util.UUID;

enum TransactionType {
    DEPOSIT, WITHDRAW, TRANSFER;
}

public class Transaction implements Serializable {
    private static final long serialVersionUID =4521914338856050008L;
    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;
    private TransactionType type;

    public Transaction(double amount, String time, boolean status, String accountNumber, TransactionType type) {
        this.id =String.valueOf(UUID.randomUUID());
        this.amount=amount;
        this.accountNumber=accountNumber;
        this.status=status;
        this.time=time;
        this.type=type;
    }
    public String getTime() {
        return time;
    }
    public String getAccountNumber() {
        return accountNumber;
    }

    public void detailtransaction(){
        System.out.printf("[GD] %7s | %-8S | %,-13.0fđ | %11s %n",accountNumber,type,amount,Util.getDatetime());
    }

}


