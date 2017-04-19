package com.timeyang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 网关服务<br/>
 * OAuth2 Resource Servers, use a Spring Security filter that authenticates requests <strong>via an incoming OAuth2 token.</strong>
 * @author chaokunyang
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableResourceServer
@EnableHystrix
public class EdgeServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdgeServiceApplication.class, args);
    }
}