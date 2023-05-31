package Utils;
import models.Customer;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Util {
    public static String getDatetime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
    public static String formatBalance(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        return  decimalFormat.format(amount)+"đ" ;
    }
    public static boolean isValidCCCD(String cccd) {
        return cccd.length() == 12 && cccd.matches("[0-9]+");
    }
}
