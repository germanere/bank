package dao;

import models.Customer;
import models.Transaction;
import service.BinaryFileService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class TransactionDao implements Serializable {
    private final static String FILE_PATH = "src/store/transactions.dat";

    public static void save(List<Transaction> transactions) throws IOException {
        BinaryFileService.writefile(FILE_PATH,transactions);
    }
    public static List<Transaction> list(){
        return BinaryFileService.readfile(FILE_PATH);
    }
}
