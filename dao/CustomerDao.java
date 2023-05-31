package dao;

import models.Customer;
import service.BinaryFileService;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class CustomerDao implements Serializable {
    private final static String FILE_PATH = "src/store/customers.dat";

    public static void save(List<Customer> customers) throws IOException {
        BinaryFileService.writefile(FILE_PATH, customers);
    }

    public static List<Customer> list() {
        return BinaryFileService.readfile(FILE_PATH);
    }
}
