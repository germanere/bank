package models;

import java.text.DecimalFormat;
import java.util.List;

public class DigitalCustomer extends Customer {
    public DigitalCustomer(List<String> values) {
        super(values);
    }

    public void withdraw(String accountNumber, double amount) {
        for (Account account : getAccounts()) {
            if (account.getAccountNumber().equals(accountNumber)) {
                if (account instanceof SavingsAccount) {
                    ((SavingsAccount) account).withdraw(amount);
                } else if (account instanceof LoansAccount) {
                    ((LoansAccount) account).withdraw(amount);
                }
            }
        }
    }
    @Override
    public void disPlayInformation() {
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            System.out.println(getCustomerId() + "  |           " + getName() + " | " + isPremium() + " |   "
                    + formatter.format(geBalance()) + "đ");
            for (int index = 0; index < accounts.size(); index++) {
                System.out.println((index + 1) +  "      " + (accounts.get(index) instanceof LoansAccount ? "LOAN" : "SAVING") + "      "+ "      " + accounts.get(index).toString());
            }
        }
    }


