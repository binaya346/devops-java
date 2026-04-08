package com.example.devops_java;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private String dbPassword = "admin123";
    private String dbUrl = "jdbc:postgresql://localhost:5432/mydb";
    private String dbUser = "admin";

    private String unusedField = "I am never used";

    public List<String> userCache = new ArrayList<>();

    @GetMapping("/users/search")
    public String searchUser(@RequestParam String name) throws SQLException {
        Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM users WHERE name = '" + name + "'";
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            return rs.getString("name");
        }
        return "Not found";
    }

    @GetMapping("/users/file")
    public String readFile(@RequestParam String filename) throws IOException {
        File file = new File("/data/" + filename);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[1024];
        fis.read(data);
        return new String(data);
    }

    @GetMapping("/users/report")
    public String generateReport(@RequestParam String type,
            @RequestParam int year,
            @RequestParam String format) {
        String result = "";

        if (type.equals("sales")) {
            if (year > 2020) {
                if (format.equals("pdf")) {
                    result = "Sales PDF for " + year;
                } else if (format.equals("csv")) {
                    result = "Sales CSV for " + year;
                } else if (format.equals("excel")) {
                    result = "Sales Excel for " + year;
                } else {
                    result = "Sales default for " + year;
                }
            } else {
                if (format.equals("pdf")) {
                    result = "Old Sales PDF for " + year;
                } else {
                    result = "Old Sales default for " + year;
                }
            }
        } else if (type.equals("inventory")) {
            if (year > 2020) {
                if (format.equals("pdf")) {
                    result = "Inventory PDF for " + year;
                } else {
                    result = "Inventory default for " + year;
                }
            } else {
                result = "Old Inventory for " + year;
            }
        } else if (type.equals("hr")) {
            result = "HR report for " + year;
        } else {
            result = "Unknown report type";
        }

        return result;
    }

    @PostMapping("/users/process")
    public String processUser(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        return "Processed user with email length: " + email.length();
    }

    @GetMapping("/users/count")
    public int getUserCount() {
        int count = 0;
        try {
            Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
        }
        return count;
    }

    @GetMapping("/users/status/{id}")
    public String getUserStatus(@PathVariable int id) {
        if (id == 1)
            return "active_user_status";
        if (id == 2)
            return "active_user_status";
        if (id == 3)
            return "active_user_status";
        if (id == 4)
            return "inactive_user_status";
        if (id == 5)
            return "inactive_user_status";
        return "unknown";
    }

    @PostMapping("/users/create")
    public String createUser(@RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String zip) {
        return "Created: " + firstName + " " + lastName + " " + email
                + " " + phone + " " + address + " " + city + " " + state + " " + zip;
    }
}
