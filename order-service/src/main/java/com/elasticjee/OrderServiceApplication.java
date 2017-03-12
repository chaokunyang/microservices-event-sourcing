package com.elasticjee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * 订单服务
 * @author chaokunyang
 */
public class OrderServiceApplication {
}

//@SpringBootApplication
//@EnableMongoRepositories
//@EnableMongoAuditing
//@EnableEurekaClient
//@EnableResourceServer
//@EnableOAuth2Client
//@EnableHystrix
//public class OrderApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(OrderApplication.class, args);
//    }
//
//    @LoadBalanced
//    @Bean
//    public OAuth2RestTemplate loadBalancedOauth2RestTemplate(
//            OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
//        return new OAuth2RestTemplate(resource, context);
//    }
//
//    @Bean
//    @Profile("docker")
//    CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
//        return args -> {
//            // Initialize the database for end to end integration testing
//            databaseInitializer.populate();
//        };
//    }
//
//    @Component
//    public static class CustomizedRestMvcConfiguration extends RepositoryRestConfigurerAdapter {
//
//        @Override
//        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//            config.setBasePath("/api");
//        }
//    }
//}
