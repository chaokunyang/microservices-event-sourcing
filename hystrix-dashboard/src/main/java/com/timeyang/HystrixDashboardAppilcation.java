package com.timeyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author chaokunyang
 */
@SpringBootApplication
@EnableHystrixDashboard
public class HystrixDashboardAppilcation {
    public static void main(String[] args) {
        SpringApplication.run(HystrixDashboardAppilcation.class, args);
    }
}
