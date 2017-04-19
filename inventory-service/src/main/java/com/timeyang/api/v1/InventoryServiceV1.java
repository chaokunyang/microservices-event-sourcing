package com.timeyang.api.v1;

import com.timeyang.inventory.Inventory;
import com.timeyang.inventory.InventoryRepository;
import com.timeyang.product.Product;
import com.timeyang.product.ProductRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chaokunyang
 */
@Service
public class InventoryServiceV1 {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    Session neo4jTemplate;

    @HystrixCommand(fallbackMethod = "getProductFallback")
    public Product getProduct(String productId) {
        Product product = productRepository.getProductByProductId(productId);
        if(product != null) {
            Stream<Inventory> availableInventory = inventoryRepository.getAvailableInventory(productId).stream();
            product.setInStock(availableInventory.findAny().isPresent());
        }
        return product;
    }

    private Product getProductFallback(String productId) {
        return null;
    }

    public List<Inventory> getAvailableInventoryForProductIds(String productIds) {
        List<Inventory> inventories = inventoryRepository.getAvailableInventoryForProductIds(productIds.split(","));
        return neo4jTemplate.loadAll(inventories, 1)
                .stream().collect(Collectors.toList());
    }

}