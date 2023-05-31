package service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TextFileService {
    private static final String COMMA_DELIMITER = ",";
    // em doc file bang ham nay de co dc list string[] chua csutomerId va customer name
    public static ArrayList<String[]> readFile(String filePath) {
        ArrayList<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(COMMA_DELIMITER);
                rows.add(data);
            }
        } catch (IOException e) {
            // loi khong doc dc file hay gi xu ly trong day
            e.printStackTrace();
        }
        return rows;
    }

    // Phương thức ghi file
    public static void writeFile(String filePath, ArrayList<String[]> data) {
        File file = new File(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String[] row : data) {
                String line = String.join(COMMA_DELIMITER, row);
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức sửa đổi dữ liệu trong file
    public static void modifyFile(String filePath, String[] newData, int row, int column) {
        ArrayList<String[]> data = readFile(filePath);
        data.get(row)[column] = Arrays.toString(newData);
        writeFile(filePath, data);
    }
}
