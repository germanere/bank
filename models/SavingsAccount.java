package models;

import Utils.Util;
import dao.AccountDao;
import dao.TransactionDao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class SavingsAccount extends Account implements Withdraw,ReportService ,ITransfer {
    private static final long serialVersionUID =-1587674692852394718L;
    private static final double SAVINGS_ACCOUNT_MAX_WITHDRAW = 5000000;
    private static final double SAVINGS_ACCOUNT_MIN_WITHDRAW = 50000;
    List <Transaction> transactionsAll = TransactionDao.list();
    public SavingsAccount (String accountNumber,double balance){
        super(accountNumber,balance);
    }
    public String getDateTime() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sD = new SimpleDateFormat("dd/MM/yyyy hh🇲🇲ss");
        String stringOfDate = sD.format(date);
        return stringOfDate;
    }
    public void createTransaction(double amount, boolean status, TransactionType transactionType) throws IOException {
        transactionsAll = TransactionDao.list();
        double accountNumber = 0;
        Transaction transaction = new Transaction(amount,
                getDateTime(),
                status,
                getAccountNumber()
                , transactionType);
        transactionsAll.add(transaction);
        TransactionDao.save(transactionsAll);
    }

    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            System.out.println("Rut tien thanh cong.");
            this.setBalance(this.getBalance()-amount);
            log(amount);
            try {
                createTransaction(-amount,true, TransactionType.WITHDRAW);
                AccountDao.update(this);
                TransactionDao.save(this.getTransactions());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isAccepted(double amount) {
        if (amount >= SAVINGS_ACCOUNT_MIN_WITHDRAW && amount % 10000 == 0
                && (getBalance() - amount) >= SAVINGS_ACCOUNT_MIN_WITHDRAW) {
            if (!isPremium()) {
                return amount <= SAVINGS_ACCOUNT_MAX_WITHDRAW;
            } else {
                return true;
            }
        }
        System.out.println("So tien khong du de thuc hien giao dich");
        return false;
    }
    public void transfers(Account receiveAccount, double amount) {
        List<Transaction> transactions = TransactionDao.list();
        if (amount > 0 && amount <= balance && amount % 50 == 0) { // check for valid transfer condition
            Transaction trandebit = new Transaction(-amount,Util.getDatetime(),true,getAccountNumber(),TransactionType.TRANSFER); // create new transaction
            Transaction tranCredit = new Transaction(amount, Util.getDatetime(), true, receiveAccount.getAccountNumber(),TransactionType.TRANSFER);
            balance -= amount; //subtract amount from sender account balance
            receiveAccount.balance += amount; //add amount to receiver account balance
            try {
                AccountDao.update(this);
                AccountDao.update(receiveAccount);
                transactions.add(trandebit);
                transactions.add(tranCredit);
                TransactionDao.save(transactions);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Chuyen tien that bai");
        }
    }
    @Override
    public void log(double amount) {
        System.out.println("+----------+--------------------+-----------+");
        System.out.println("        BIEN LAI GIAO DICH SAVINGS"       );
        System.out.printf("NGAY G/D:                   %s%n", Util.getDatetime());
        System.out.printf("ATM ID:                     %s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK:                      %s%n", getAccountNumber());
        System.out.printf("SO TIEN:                    %s%n", Util.formatBalance(setBalance(getBalance())));
        System.out.printf("PHI + VAT:                  %s%n", "0đ");
        System.out.println("+----------+--------------------+-----------+");
    }
    @Override
    public boolean transfer(String receiveAccount, double amount) {
        return false;
    }
}
