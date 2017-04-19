package com.timeyang;


import com.timeyang.address.AddressRepository;
import com.timeyang.catalog.CatalogRepository;
import com.timeyang.inventory.InventoryRepository;
import com.timeyang.product.ProductRepository;
import com.timeyang.shipment.ShipmentRepository;
import com.timeyang.warehouse.WarehouseRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * neo4j配置
 * @author chaokunyang
 */
@Configuration
public class GraphConfiguration extends Neo4jConfiguration {

    @Autowired
    private Neo4jProperties properties;

    @Bean
    public Neo4jServer neo4jServer() {
        String uri = properties.getUri();
        String username = properties.getUsername();
        String password= properties.getPassword();
        if(StringUtils.hasText(username) && StringUtils.hasText(username))
            return new RemoteServer(uri, username, password);
        return new RemoteServer(uri);
    }

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

    @Bean
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
