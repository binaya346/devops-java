package com.example.devops_java;

import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class PaymentService {

    // Sonar Issue: Hardcoded DB credentials or sensitive information
    private final String PAYMENT_GATEWAY_PASSWORD = "super-secret-password";

    // Sonar Issue: Avoid variables with one character name, avoid public non-static
    // variables
    public int x = 0;

    public void processPayment(String creditCard, double amount) {
        // Sonar Issue: Cyclomatic complexity might not be huge but nesting is bad
        if (creditCard != null) {
            if (!creditCard.isEmpty()) {
                if (creditCard.length() == 16) {
                    if (amount > 0) {
                        if (amount < 10000) {
                            System.out.println("Processing valid payment"); // Sonar Issue: System.out
                        } else {
                            System.out.println("Amount too large");
                        }
                    } else {
                        System.out.println("Invalid amount");
                    }
                }
            }
        }
    }

    public String generateHash(String data) {
        try {
            // Sonar Issue: Weak cryptographic hash (MD5 is vulnerable)
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(data.getBytes());

            // Converting to hex string (inefficiently)
            String hexString = "";
            for (byte b : hashBytes) {
                // Sonar Issue: Using string concatenation in loop instead of StringBuilder
                hexString += String.format("%02x", b);
            }
            return hexString;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(); // Sonar Issue: printStackTrace
            return null; // Sonar Issue: returning null instead of throwing exception/optional
        }
    }

    // Sonar Issue: Boolean method naming and returning boolean literals
    public boolean checkPaymentStatus() {
        boolean status = isGatewayUp();
        if (status == true) { // Sonar Issue: comparing boolean with true
            return true;
        } else {
            return false;
        }
    }

    private boolean isGatewayUp() {
        return true;
    }
}
