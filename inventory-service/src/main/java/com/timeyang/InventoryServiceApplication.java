package com.timeyang;

import com.timeyang.catalog.Catalog;
import com.timeyang.config.DatabaseInitializer;
import com.timeyang.product.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 库存服务
 *
 * @author chaokunyang
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableHystrix
public class InventoryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Configuration
    public static class RepositoryMvcConfiguration extends RepositoryRestConfigurerAdapter {
        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Catalog.class, Product.class);
            config.setBasePath("/api");
        }
    }

    @Bean
    @Profile("dev")
    public CommandLineRunner commandLineRunner(DatabaseInitializer databaseInitializer) {
        return args -> databaseInitializer.populate();
    }

}
