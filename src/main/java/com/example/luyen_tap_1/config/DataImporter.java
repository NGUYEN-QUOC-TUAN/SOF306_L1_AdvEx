package com.example.luyen_tap_1.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataImporter {
    public static void main(String[] args) {
        String jsonFile = "E:\\LAP_TRINH_JAVA_TUAN_PH20022\\LAP_TRINH_JAVA_6\\LUYEN_TAP\\LUYEN_TAP_1\\src\\main\\resources\\static\\people_tong.json";
        String dbUrl = "jdbc:sqlserver://localhost:1433;databaseName=IMPORT_JSON_LV1";
        String dbUser = "sa";
        String dbPassword = "0945525830";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             BufferedReader br = new BufferedReader(new FileReader(jsonFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String id = data[0];
                String first_name = data[1];
                String last_name = data[2];
                String email = data[3];
                String gender = data[4];
                String ip_address = data[5];
                String avatar = data[6];
                String country = data[7];
                String job = data[8];
                String company = data[9];
                String salary = data[10];
                String username = data[11];
                String password = data[12];
                String slogan = data[13];
                // Lấy các giá trị dữ liệu khác từ mảng `data`

                // Tạo câu lệnh SQL INSERT
                String sql = "INSERT INTO people (id, first_name, last_name, email, gender, ip_address, avatar, country, job, company, salary, username, password, slogan) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ? , ? , ? , ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, id);
                statement.setString(2, first_name);
                statement.setString(3, last_name);
                statement.setString(4, email);
                statement.setString(5, gender);
                statement.setString(6, ip_address);
                statement.setString(7, avatar);
                statement.setString(8, country);
                statement.setString(9, job);
                statement.setString(10, company);
                statement.setString(11, salary);
                statement.setString(12, username);
                statement.setString(13, password);
                statement.setString(14, slogan);
                // Đặt các giá trị dữ liệu khác vào câu lệnh INSERT
                // Thực thi câu lệnh INSERT
                statement.executeUpdate();
                statement.close();
            }

            System.out.println("Import thành công!");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
