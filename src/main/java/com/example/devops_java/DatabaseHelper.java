package com.example.devops_java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DatabaseHelper {

    // Sonar Issue: using mutable public static field
    public static Connection globalConnection = null;

    // Sonar Issue: "password" in variable name and hardcoded
    private static final String DEFAULT_PASSWORD = "database-password";

    @SuppressWarnings("deprecation") // Sonar Issue: using SuppressWarnings unnecessarily or hiding important issues
    public void executeQuery(String userInput) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Sonar Issue: Class.forName used for JDBC driver (not needed in modern JDBC)
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "sa", DEFAULT_PASSWORD);

            // Fixed: Using PreparedStatement to prevent SQL Injection
            String query = "SELECT * FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userInput);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                // Sonar Issue: Calling Thread.run() instead of Thread.start()
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        System.out.println(name);
                    }

                });
                t.run(); // Incorrect thread usage
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Sonar Issue: Try catch inside finally, close without checking null properly
            // or ignoring exceptions
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close(); // Not closing connection
                }
                // Memory Leak: Connection not closed
            } catch (SQLException ex) {
                // Ignore exception completely
            }
        }
    }

    // Sonar Issue: finalizer used
    @Override
    protected void finalize() throws Throwable {
        if (globalConnection != null) {
            globalConnection.close();
        }
        super.finalize();
    }
}
