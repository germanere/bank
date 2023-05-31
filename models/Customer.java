package models;

import Utils.Util;
import dao.AccountDao;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 4303930202272309371L;
    public List<Account> accounts = new ArrayList<>();
    DigitalBank duc = new DigitalBank();
    public Customer(List<String> values){
        this(values.get(0),values.get(1));
    }
    public Customer(String Cusid,String name) {
        this.name =name;
        this.customerId=Cusid;
    }
    public List<Account> getAccounts() {
        return accounts.stream()
                .filter(account -> account.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    public Account getAccountByAccountNumber(String accountNumber) {
        List<Account> accounts = AccountDao.list();
        Account ac =accounts.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst().orElse(null);
        if(ac==null) {
            System.out.println("Tai khoan khong ton tai, yeu cau nhap lai :");
            return null;
        }
        return ac;
    }
    public String isPremium() {
        final String[] premium = {"Normal"};
        AccountDao.list().stream().filter(new Predicate<Account>() {
            @Override
            public boolean test(Account account) {
                return account.customerId.equals(customerId);
            }
        }).forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                if (account.isPremium()){
                    premium[0] = "Premium";
                }
            }
        });
        return premium[0];
    }

    public void addAccount(Account newAccount) {
        if (accounts.isEmpty()) {
            accounts.add(newAccount);
        } else {
            List<String> accountNumbers = new ArrayList<>();
            for (Account account : accounts) {
                accountNumbers.add(account.getAccountNumber());
            }
            if (!accountNumbers.contains(newAccount.getAccountNumber())) {
                accounts.add(newAccount);
                System.out.println("Them tai khoan thanh cong.");
            } else {
                System.out.println("Tai khoan da ton tai");
            }
        }
    }

    //ham get balance
    public double geBalance() {
        final double[] sum = {0};
        List<Account> accountList = AccountDao.list();
        accountList.stream().filter(new Predicate<Account>() {
            @Override
            public boolean test(Account account) {
                return account.customerId.equals(customerId);
            }
        }).forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                sum[0] += account.getBalance();
            }
        });
        return sum[0];
    }
    public void disPlayInformation() {
        final int[] index = {1};
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        System.out.println(getCustomerId() + "  |           " + getName() + " | " + isPremium() + " |   "
                + formatter.format(geBalance()) + "đ");
        List<Account> accountList = AccountDao.list();
        accountList.stream().filter(new Predicate<Account>() {
            @Override
            public boolean test(Account account) {
                return account.customerId.equals(customerId);
            }
        }).forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                System.out.println((index[0]) + "     " + account.getAccountNumber()
                        +"  |   \t\t"
                        + (account instanceof LoansAccount ? "LOAN" : "SAVING") + "| "
                        + "\t\t\t"
                        + Util.formatBalance(account.getBalance()));
                index[0]++;
            }
        });
    }
    public void displayTransactionInformation() {
        disPlayInformation();
        System.out.println("\nTransactions:");
        for (Account account : getAccounts()) {
            System.out.println("\nAccount Number: " + account.getAccountNumber());
//            System.out.println("Account Type: " + account.);
            System.out.println("Balance: " + account.getBalance());
            System.out.println("Transactions:");
            for (Transaction transaction : account.getTransactions()) {
                System.out.println(transaction.toString());
            }
        }
    }
    public void withdraw(Scanner scanner,Account account,double amount){
        if(account instanceof SavingsAccount){
            ((SavingsAccount)account).withdraw(amount);
        }
        else {
            System.out.println("Khach hang nay khong co tai khoan nao ");
        }
    }
    public void transfers(Scanner scanner) {
        SavingsAccount fromAccountNumber;
        SavingsAccount ToAccountNumber;
        List<Account> AccountNews = AccountDao.list();
        do {
            System.out.print("Nhập số tài khoản : ");
            fromAccountNumber = (SavingsAccount) getAccountByAccountNumber(scanner.nextLine());
        } while (fromAccountNumber == null || !checkAccountID(fromAccountNumber.getAccountNumber()) || !duc.isAccountExisted(AccountNews, fromAccountNumber));
        do {
            System.out.println("Nhập số tài khoản người nhận (exit để thoát):");
            ToAccountNumber = (SavingsAccount) getAccountByAccountNumber(scanner.nextLine());
        }while (ToAccountNumber.equals(null) || !checkAccountID(ToAccountNumber.getAccountNumber()) && !duc.isAccountExisted(AccountNews, ToAccountNumber));
        System.out.println("Gửi tiền đến tài khoản : " +ToAccountNumber + " | " +ToAccountNumber.getAccountNumber());
        System.out.println("Nhap so tien can chuyen : ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.println("Bạn xác nhận chuyển tiền " + amount + " từ tài khoản "
                + fromAccountNumber + " đến tài khoản " + ToAccountNumber + "? (Y/N)");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            fromAccountNumber.transfers(ToAccountNumber,amount);
        }
    }
    public boolean isValidId(String id) throws NumberFormatException {
        boolean result = false;
        // Kiểm tra xem chuỗi đầu vào có độ dài là 12 ký tự
        if (id.length() == 12) {
            // Kiểm tra xem chuỗi đầu vào toàn chữ số
            try{
                Long.parseLong(id);
                result = true;
            }catch (Exception e){}
        }
        return result;
    }
    public static boolean checkAccountID(String customerID) {
        if (customerID == null || customerID.length() != 6) {
            return false;
        }
        for (char c : customerID.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
    public void displayAccounts() {
        final int[] index = {1};
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        System.out.println(getCustomerId() + "  |           " + getName() + " | " + isPremium() + " |   "
                + formatter.format(geBalance()) + "đ");
        List<Account> accountList = AccountDao.list();
        accountList.stream().filter(new Predicate<Account>() {
            @Override
            public boolean test(Account account) {
                return account.customerId.equals(customerId);
            }
        }).forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                System.out.println((index[0]) + "     " + account.getAccountNumber()
                        +"  |   \t\t"
                        + (account instanceof LoansAccount ? "LOAN" : "SAVING") + "| "
                        + "\t\t\t"
                        + Util.formatBalance(account.getBalance()));
                index[0]++;
            }
        });
    }
}
class CustomerIdNotValidException extends Exception {
    public CustomerIdNotValidException(String message) {
        super(message);
    }
}




