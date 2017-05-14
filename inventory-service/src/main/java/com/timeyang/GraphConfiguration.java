package com.timeyang;


import com.timeyang.address.AddressRepository;
import com.timeyang.catalog.CatalogRepository;
import com.timeyang.inventory.InventoryRepository;
import com.timeyang.product.ProductRepository;
import com.timeyang.shipment.ShipmentRepository;
import com.timeyang.warehouse.WarehouseRepository;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * neo4j配置
 * @author chaokunyang
 */
@Component
@EnableNeo4jRepositories
@EnableTransactionManagement
public class GraphConfiguration extends Neo4jConfiguration {

    @Bean
    public SessionFactory getSessionFactory() {
        // 指定Neo4j应该扫描哪些包，使用每个包里面的类来指定包扫描路径，以避免脆弱性，类型安全，易于重构
        Class<?>[] packageClasses = {
                AddressRepository.class,
                CatalogRepository.class,
                InventoryRepository.class,
                ProductRepository.class,
                ShipmentRepository.class,
                WarehouseRepository.class
        };
        String[] packageNames =
                Arrays.asList(packageClasses)
                        .stream()
                        .map( c -> getClass().getPackage().getName())
                        .collect(Collectors.toList())
                        .toArray(new String[packageClasses.length]);
        return new SessionFactory(packageNames);
    }

    // needed for session in view in web-applications
    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
