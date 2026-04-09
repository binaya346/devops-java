package com.example.devops_java;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

@RestController
public class OrderController {

    // Sonar Issue: Public static non-final field (mutable)
    // Sonar Issue: Hardcoded secret/API key
    public static String API_SECRET_KEY = "super-secret-key-12345";

    @GetMapping("/orders")
    public String getOrders() {
        try {
            // Sonar Issue: Use a logger instead of System.out.println
            System.out.println("Processing orders API call...");

            String status = "PENDING";
            // Sonar Issue: Strings must be compared using equals(), not ==
            if (status == "PENDING") {
                System.out.println("Status is pending");
            }

            // Sonar Issue: Random is insecure, use SecureRandom instead
            Random random = new Random();
            int orderId = random.nextInt();

            // Sonar Issue: Magic number '42'
            for (int i = 0; i < 42; i++) {
                // Sonar Issue: InterruptedException might be ignored or Thread.sleep inside
                // loop
                Thread.sleep(10);
            }

            String msg = "Hello";
            // Sonar Issue: Return value of methods without side effects is ignored
            msg.replace("H", "W");

            if (orderId > 100) {
                return "Big order";
            } else {
                return "Small order";
            }
        } catch (Exception e) {
            // Sonar Issue: Catching generic Exception
            // Sonar Issue: Empty catch block
        } catch (Throwable t) {
            // Sonar Issue: Catching Throwable
            // Sonar Issue: Use logger, don't use printStackTrace
            t.printStackTrace();
        }
        return "Unknown";
    }

    @GetMapping("/orders/deserialize")
    public void deserializeData(@RequestParam("data") String dataString) throws Exception {
        // Sonar Issue: Deserialization of untrusted data (major vulnerability)
        byte[] data = dataString.getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();

        // Sonar Issue: Resource not closed in a finally block
        ois.close();
    }

    public void unusedCodeMethod(int unusedParam) {
        // Sonar Issue: Unused method parameter
        int x = 10; // Sonar Issue: Unused local variable
        x = 20; // Sonar Issue: Dead store to local variable

        if (true) {
            // Sonar Issue: Code that will never be executed or constant condition
        }
    }
}
