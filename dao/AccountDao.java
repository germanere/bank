package dao;

import models.Account;
import service.BinaryFileService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private final static String FILE_PATH ="src/store/accounts.dat";
    public static void save(List<Account> accounts) throws IOException {
        BinaryFileService.writefile(FILE_PATH,accounts);
    }
    public static List<Account> list(){
        return BinaryFileService.readfile(FILE_PATH);
    }
    public static void update(Account EditAccount) throws IOException {
        List<Account> accounts =AccountDao.list();
        boolean hasExist =accounts.stream().anyMatch((account -> account.getAccountNumber().equals(EditAccount.getAccountNumber())));
        if(!hasExist){
            accounts.add(EditAccount);
            save(accounts);
        }
        else{
            List <Account> updateAccounts = new ArrayList<>();
            for(Account account :accounts){
                if(account.getAccountNumber().equals(EditAccount.getAccountNumber())){
                    updateAccounts.add(EditAccount);
                } else {
                    updateAccounts.add(account);
                }
            }
            save(updateAccounts);
        }
    }
}
