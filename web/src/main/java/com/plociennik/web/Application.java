package com.plociennik.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;

import com.plociennik.web.config.CredentialsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "com.plociennik.common",
                "com.plociennik.model",
                "com.plociennik.service",
                "com.plociennik.web",
        }
)
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        CredentialsConfig.load();
        SpringApplication.run(Application.class, args);
    }

    private void logConnection() {
        try {
            // Load credentials
            CredentialsConfig.load();

            // Get database URL from your credentials (adjust as needed)
            String dbUrl = System.getenv("CUSTOM_DATABASE_URL"); // or however you store this
            System.out.println("dbUrl: " + dbUrl);

            // Log the database connection attempt (without sensitive info)
            if (dbUrl != null) {
                URI uri = new URI(dbUrl.replaceAll("^jdbc:", ""));
                String host = uri.getHost();
                int port = uri.getPort() > 0 ? uri.getPort() : 5432; // Default postgres port

                logger.info("Attempting to connect to database at host: {} port: {}", host, port);

                // Test basic connectivity
                try {
                    // Test if host is reachable
                    boolean reachable = InetAddress.getByName(host).isReachable(5000);
                    logger.info("Host {} is reachable: {}", host, reachable);

                    // Try opening a socket
                    try (Socket socket = new Socket(host, port)) {
                        logger.info("Successfully opened socket to {}:{}", host, port);
                    } catch (Exception e) {
                        logger.error("Failed to open socket to {}:{} - {}", host, port, e.getMessage());
                    }
                } catch (Exception e) {
                    logger.error("Network connectivity test failed: {}", e.getMessage());
                }
            } else {
                logger.warn("DATABASE_URL not found in environment variables");
            }
        } catch (Exception e) {
            logger.error("Error during startup: {}", e.getMessage(), e);
        }
    }

}