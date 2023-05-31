package models;
import Utils.Util;
public class LoansAccount extends  Account implements Withdraw,ReportService {
    static final double LOAN_ACCOUNT_WITHDRAW_FEE = 0.05;
    static final double LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE = 0.01;
    static final double LOAN_ACCOUNT_MAX_BALANCE = 100000000;

    public LoansAccount(String accountNumber,double balance){
        super(accountNumber,balance);
    }
    @Override
    public boolean withdraw(double amount) {
        if (isAccepted(amount)) {
            System.out.println("Rut tien thanh cong.");
            setBalance(getBalance() - amount * ((isPremium()) ? LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE : LOAN_ACCOUNT_WITHDRAW_FEE));
            log(amount);
            return  true;
        }
        return false;
    }

        @Override
        public boolean isAccepted(double amount) {
            if (amount >= 50000.0 && amount <= 100000000.0)
                return true;
            return false;
        }
    @Override
    public void log(double amount) {
        System.out.println("+----------+-----------------+----------+");
        System.out.println("       BIEN LAI GIAO DICH LOANS        ");
        System.out.printf("NGAY G/D:                   %s%n", Util.getDatetime());
        System.out.printf("ATM ID:                     %s%n", "DIGITAL-BANK-ATM 2023");
        System.out.printf("SO TK:                      %s%n", getAccountNumber());
        System.out.printf("SO TIEN:                    %s%n", Util.formatBalance(amount));
        System.out.printf("SO DU:                      %s%n", Util.formatBalance(getBalance()-amount));
        System.out.printf("PHI + VAT:                  %s%n", Util.formatBalance(amount*LOAN_ACCOUNT_WITHDRAW_PREMIUM_FEE));
        System.out.println("+----------+--------------------+-----------+");
    }

}