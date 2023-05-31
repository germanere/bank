package models;

public interface IReport {
    void log(double amount, TransactionType type, String receiveAccount);
}
