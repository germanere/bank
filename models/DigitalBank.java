package models;

import dao.AccountDao;
import dao.CustomerDao;
import dao.TransactionDao;
import service.TextFileService;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DigitalBank extends Bank implements Serializable {
    private static final long serialVersionUID =-2579281444890490866L;
    private List<Account> accounts;
    private int customerId;
    public Customer getCustomerById(String customerId) {
        Pattern pattern = Pattern.compile("\\d{12}");
        Matcher matcher = pattern.matcher(customerId);
        for (Customer c: customers) {
            if (matcher.find()&& isCustomerExisted(customerId)) {
                return c;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts.stream()
                .filter(account -> account.equals(customerId))
                .collect(Collectors.toList());
    }

    public void showcustomer(){
            List<Customer> customers = CustomerDao.list();
            if (customers.isEmpty()) {
                System.out.println("Chưa có khách hàng nào trong danh sách!");
            } else {
                for (Customer customer : customers) {
                    customer.disPlayInformation();
                }
            }
    }
    public void addCustomers(String fileName) {
        List<Customer> newCustomers = readCustomersFromFile(fileName);
        List<Customer> customerNew = CustomerDao.list();
        boolean validData = false;
        int count=0;
        for (Customer customer : newCustomers) {
            if (!customer.isValidId(customer.getCustomerId())) {
                System.out.println("Không thể thêm khách hàng: " + customer.getCustomerId()
                        + ", số ID không hợp lệ.");
            } else if (findCustomerById(customer.getCustomerId()) != null) {
                System.out.println("Không thể thêm khách hàng: " + customer.getCustomerId()
                        + ", đã tồn tại trong danh sách.");
            } else {
                validData =true;
                customerNew.add(customer);
                count++;
            }
        }
        if (validData) {
            try {
                CustomerDao.save(customerNew);
                System.out.println("Đã thêm " +count + " khách hàng vào danh sách.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addSavingAccount(Scanner scanner, String customerId) throws IOException {
        Customer customer = findCustomerById(customerId);
        List<Account> AccountNews = AccountDao.list();
        Account Accountnew;
        double balance;
        String accountNumber;
        if (customer != null) {
            System.out.println("Tạo tài khoản mới cho khách hàng " + customer.getName() + ".");
            do {
                System.out.print("Nhập ma so tai khoan khach hang : ");
                accountNumber = scanner.nextLine();
                Accountnew = new Account();
                Accountnew.setAccountNumber(accountNumber);
            }while (!checkAccountID(accountNumber) || isAccountExisted(AccountNews, Accountnew));
            do {
                System.out.print("Nhập số dư tai khoan >= 50000đ: ");
                balance = scanner.nextDouble();
            } while (balance <= 50000);
            SavingsAccount account = new SavingsAccount(accountNumber, balance);
            account.setCustomerId(customerId);
            AccountDao.update(account);
            System.out.println("Đã thêm tài khoản mới cho khách hàng " + customer.getName() + ".");
        } else {
            System.out.println("Không tìm thấy khách hàng với số ID là " + customerId + ".");
        }
        scanner.nextLine();
    }
    public void withdraw(Scanner scanner, String customerId) {
        DigitalBank d1 = new DigitalBank();
        Customer customer = findCustomerById(customerId);
        customer.displayAccounts();
        SavingsAccount accountnum ;
        double amount;
        do {
            System.out.println("nhap so tai khoan : ");
            accountnum = (SavingsAccount) customer.getAccountByAccountNumber(scanner.nextLine());
        }while (!checkAccountID(String.valueOf(accountnum))&&d1.isAccountNumberExisted(String.valueOf(accountnum)));
        do {
            System.out.println("nhap so tien rut : ");
            amount =Double.parseDouble(scanner.nextLine());
        }while (amount<=50000);
        customer.withdraw(scanner,accountnum,amount);
    }
    public void transfers(Scanner scanner, String customerId){
        Customer customer = findCustomerById(customerId);
        customer.displayAccounts();
        customer.transfers(scanner);
    }
    public boolean isAccountExisted(List<Account> accountsList, Account newAccount) {
        for (Account account : accountsList) {
            if (account.getAccountNumber().equals(newAccount.getAccountNumber())) {
                return true;
            }
        }
        return false;
    }
    private boolean isCustomerExisted(List<Customer> customers, Customer newCustomer) {
        for (Customer customer : CustomerDao.list()) {
            if (customer.getCustomerId().equals(newCustomer.getCustomerId())) {
                return true;
            }
        }
        return false;
    }
    private Customer getCustomerByID(List<Customer> customerList, String customerId) {
        for (Customer customer : customerList) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    @Override
    public boolean isAccountNumberExisted(String accountNumber) {
        return super.isAccountNumberExisted(accountNumber);
    }
    private List<Customer> readCustomersFromFile(String fileName) {
        List<Customer> customers = new ArrayList<>();
        // dung TextFileService
        ArrayList<String[]> customerValues = TextFileService.readFile(fileName);
        for (String[] customerValue : customerValues) {
            customers.add(new Customer(customerValue[0],customerValue[1]));
        }
        return customers;
    }
    public Customer findCustomerById(String customerId) {
        for (Customer customer : CustomerDao.list()) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    public void displaytran(String cusid){
        Customer customers = findCustomerById(cusid);
        for (Transaction transaction : TransactionDao.list())
            
            transaction.detailtransaction();
    }
    private void displayAccounts(List<Account> accounts) {
        for (Account account : accounts) {
            System.out.println("- Account number: " + account.getAccountNumber() + ", balance: " + account.getBalance());
        }
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
}