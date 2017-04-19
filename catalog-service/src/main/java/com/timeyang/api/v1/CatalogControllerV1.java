package com.timeyang.api.v1;

import com.timeyang.catalog.Catalog;
import com.timeyang.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author chaokunyang
 */
@RestController
@RequestMapping("/v1")
public class CatalogControllerV1 {
    @Autowired
    private CatalogServiceV1 catalogService;

    @RequestMapping(value = "/catalog", method = RequestMethod.GET, name = "getCatalog")
    public ResponseEntity<Catalog> getCatalog() {
        return Optional.ofNullable(catalogService.getCatalog())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/products/{productId}", method = RequestMethod.GET, name = "getProduct")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId) {
        return Optional.ofNullable(catalogService.getProduct(productId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
