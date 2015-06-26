package org.springframework.gnomes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.java.ServiceScan;
import org.springframework.cloud.netflix.servo.ServoMetricsAutoConfiguration;

/**
 * @author Vinicius Carvalho
 */
@SpringBootApplication(exclude = {ServoMetricsAutoConfiguration.class})
@ServiceScan
@EnableDiscoveryClient
@EnableCircuitBreaker
public class AggregationApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AggregationApplication.class);
        application.run(args);
    }

}
