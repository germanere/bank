package models;

import Utils.Util;
import dao.CustomerDao;
import dao.TransactionDao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Account implements Serializable {
    private String accountNumber;
    public double balance;
    public String customerId;
    private final List<Transaction> transactions = new ArrayList<>();
    public Account() {
        super();
    }
    public Account(String accountNumber, double balance) {
        super();
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        for(Customer customer : CustomerDao.list()) {
            if(customer.getCustomerId().equals(this.customerId)) {
                return customer;
            }
        }
        return null;
    }

    public String getCustomerId() {
        return String.valueOf(customerId);
    }

    public double getBalance() {
        return balance;
    }
    public double setBalance(double balance) {
        this.balance = balance;
        return balance;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public boolean isPremium() {
        return this.balance >= 10000000;
    }
    public List<Transaction> getTransactions() {
        List<Transaction> accountTransactions = TransactionDao.list();
        for (Transaction transaction : TransactionDao.list()) {
            if (transaction.getAccountNumber() == accountNumber) {
                accountTransactions.add(transaction);
            }
        }
        return accountTransactions;
    }

    public void createTransaction(double amount, String time, boolean status, TransactionType type) {
        Transaction transaction = new Transaction(amount,time,status,accountNumber,type);
        transactions.add(transaction);
        if (type == TransactionType.DEPOSIT) {
            balance += amount;
        } else if (type == TransactionType.WITHDRAW) {
            balance -= amount;
        }
    }

    public void displayTransactionsList() {
        List<Transaction> accountTransactions = TransactionDao.list();
        System.out.println("[GD] " + accountNumber + " | " );
            System.out.println(" | " +accountTransactions +" | "+ Util.getDatetime());
    }
    public void input(Scanner scanner) {
        System.out.print("Nhap so tai khoan: ");
        accountNumber = scanner.nextLine();
        System.out.print("Nhap so tien ban dau: ");
        double amount = scanner.nextDouble();
        if (amount <= 0 || amount % 10 != 0) {
            System.out.println("Giao dich ban dau khong hop le!");
            return;
        }
        scanner.nextLine();
        customerId = scanner.nextLine();
        createTransaction(amount, "First transaction", true, TransactionType.DEPOSIT);
    }
    public String toString() {
        return this.accountNumber  +" | " + "\t\t\t\t\tSố dư: " + this.balance+"đ";
    }

    public void transfer(Account toAccount, double amount) {
        if (this.balance < amount) {
            System.out.println("Insufficient funds!");
            return;
        }
        this.balance -= amount;
        toAccount.balance += amount;
        deposit(amount);
    }

    public void deposit(double amount) {
        balance += amount; // adding the deposited amount to the balance field
    }
}
