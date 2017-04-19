package com.timeyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 目录服务
 *
 * @author chaokunyang
 */
@SpringBootApplication
@EnableHystrix
@EnableDiscoveryClient
public class CatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    @Bean

    
    @LoadBalanced
    public RestTemplate loadRestTemplate() {
        return new RestTemplate();
    }

}
