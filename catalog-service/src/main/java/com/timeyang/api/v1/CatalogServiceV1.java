package com.timeyang.api.v1;

import com.timeyang.catalog.Catalog;
import com.timeyang.catalog.CatalogInfo;
import com.timeyang.catalog.CatalogInfoRepository;
import com.timeyang.product.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

/**
 * @author chaokunyang
 */
@Service
public class CatalogServiceV1 {
    @Autowired
    private CatalogInfoRepository catalogInfoRepository;
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand
    public Catalog getCatalog() {
        CatalogInfo activeCatalogInfo = catalogInfoRepository.findCatalogInfoByActive(true);

        Catalog catalog = restTemplate.getForObject(String.format("http://inventory-service/api/catalogs/search/findCatalogByCatalogNumber?catalogNumber=%s", activeCatalogInfo.getCatalogId()), Catalog.class);
        ProductResource products = restTemplate.getForObject(String.format("http://inventory-service/api/catalogs/%s/products", catalog.getId()), ProductResource.class);

        catalog.setProducts(products.getContent().stream().collect(Collectors.toSet()));
        return catalog;
    }

    @HystrixCommand
    public Product getProduct(String productId) {
        return restTemplate.getForObject(String.format("http://inventory-service/v1/products/%s", productId), Product.class);
    }
}
