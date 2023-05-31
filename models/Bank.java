package models;

import dao.CustomerDao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Bank {
    private final String id;
    public final List<Customer> customers;
    private String bankname;

    public Bank(){
        this.customers = new ArrayList<>();
        this.id = String.valueOf(UUID.randomUUID());
        this.bankname=bankname;
    }
    public  String getId(){
        return id;
    }
    //ham them khach hang
    public List<Customer> getCustomers() {
        return customers;
    }
    public void addCustomer(Customer newCustomer) {
        if (!isCustomerExisted(newCustomer.getCustomerId())) {
            customers.add(newCustomer);
        }
    }
    public void addAccount(String customerId, Account account) {
        for (Customer customer : customers) {
            if (isCustomerExisted(customerId) && customer.getCustomerId().equals(customerId)) {
                customer.addAccount(account);
            }
        }
    }
    //ham kiem tra customer ton tai
    public boolean isCustomerExisted(String customerId) {
        for (Customer customer : CustomerDao.list()) {
            if (customer.getCustomerId().equals(customerId))
                return true;
        }
        return false;
    }
    public boolean isAccountNumberExisted(String accountNumber) {
        for (Customer cus : CustomerDao.list()) {
            List<Account> accounts = cus.getAccounts();
            for (Account acc : accounts) {
                if (acc.getAccountNumber().equals(accountNumber)) {
                    return true;
                }
            }
        }
        return false;
    }
}

