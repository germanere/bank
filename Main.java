import Utils.Util;
import dao.CustomerDao;
import models.Customer;
import models.DigitalBank;
import models.Transaction;

import java.io.*;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static final DigitalBank activeBank = new DigitalBank();
    private static void initMenu() {
        boolean runAgain = false;
        do {
            try {
                System.out.println("+----------+--------------------+----------+");
                System.out.println("| NGAN HANG SO | fx21320@v4.0.0            |");
                System.out.println("+----------+--------------------+----------+");
                System.out.println(" 1. Thong tin khach hang                    ");
                System.out.println(" 2. Nhap danh sach khach hang               ");
                System.out.println(" 3. Them tai khoan ATM                      ");
                System.out.println(" 4. Chuyen tien                             ");
                System.out.println(" 5. Rut tien                                ");
                System.out.println(" 6. Lich su giao dich                       ");
                System.out.println(" 0. Thoat                                   ");
                System.out.println("+----------+--------------------+----------+");
                System.out.print("Chuc nang: ");
                int choose = Integer.parseInt(scanner.nextLine());

                switch (choose) {
                    case 1:
                        showCustomer();
                        break;
                    case 2:
                        inputCustomer();
                        break;
                    case 3:
                        addaccountATM();
                        break;
                    case 4:
                        transfer();
                        break;
                    case 5:
                        withdraw();
                        break;
                    case 6:
                        displaytrans();
                        break;
                    case 0:
                        System.out.println("ban da thoat khoi chuong trinh");
                        System.exit(choose);
                        break;
                    default:
                        System.out.println("Chuc nang khong ton tai! Hay chon lai ma chuc nang");
                        runAgain = true;
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Chuc nang khong ton tai! Hay chon lai ma chuc nang");
                runAgain = true;
            }
        } while (runAgain);
    }
    //chuc nang 1
    public static void showCustomer(){
        activeBank.showcustomer();
        initMenu();
    }
    //chuc nang 2
    public static void inputCustomer() throws IOException {
        // Duong dan den file customers.txt
        activeBank.addCustomers("src/store/customers.txt");
        initMenu();
    }
    //chuc nang 3
    public static void addaccountATM() throws IOException {
        String cusid;
        do {
            System.out.println("nhap ma so khach hang:  ");
            cusid =scanner.nextLine();
        }while (!Util.isValidCCCD(cusid)&&!activeBank.isAccountNumberExisted(cusid));
        activeBank.addSavingAccount(scanner,cusid);
        initMenu();
    }
    //chuc nang 4
    public static void transfer(){
        String cusid;
        do {
            System.out.println("nhap ma so khach hang : ");
            cusid =scanner.nextLine();
        }while (!Util.isValidCCCD(cusid)&&!activeBank.isAccountNumberExisted(cusid));
        activeBank.transfers(scanner,cusid);
        initMenu();
    }
    //chuc nang 5
    public static void withdraw(){
        String cusid;
        do {
            System.out.println("nhap ma so khach hang : ");
            cusid =scanner.nextLine();
        }while (!Util.isValidCCCD(cusid)&&!activeBank.isAccountNumberExisted(cusid));
        activeBank.withdraw(scanner,cusid);
        initMenu();
    }// sua ham displaytransaction la xong

    public static void displaytrans(){
    Transaction tram;
        String cusid;
        do {
            System.out.println("nhap ma so khach hang : ");
            cusid =scanner.nextLine();
        }while (!Util.isValidCCCD(cusid)&&!activeBank.isAccountNumberExisted(cusid));
        activeBank.displaytran(cusid);
        initMenu();
    }

    public static void main(String[] args) {
            initMenu();
    }

}
// duong dan C:\Users\Admin\IdeaProjects\Asm04\src\Store\customers.txt