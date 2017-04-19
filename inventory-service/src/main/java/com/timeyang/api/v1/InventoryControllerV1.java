package com.timeyang.api.v1;

import com.timeyang.inventory.Inventory;
import com.timeyang.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author chaokunyang
 */
@RestController
@RequestMapping("/v1")
public class InventoryControllerV1 {
    @Autowired
    private InventoryServiceV1 inventoryService;

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET, name = "getProduct")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        return Optional.ofNullable((inventoryService.getProduct(productId)))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/inventory", method = RequestMethod.GET, name = "getAvailableInventoryForProductIds")
    public ResponseEntity<List<Inventory>> getAvailableInventoryForProductIds(@RequestParam("productIds") String productIds) {
        return Optional.ofNullable(inventoryService.getAvailableInventoryForProductIds(productIds))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}