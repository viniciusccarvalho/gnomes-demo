package org.springframework.gnomes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.java.ServiceScan;

/**
 * @author Vinicius Carvalho
 */
@SpringBootApplication
@EnableDiscoveryClient
@ServiceScan
public class DataApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DataApplication.class);
        app.run(args);
    }


}
