package com.timeyang.product;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Transient;

/**
 * @author chaokunyang
 */
@NodeEntity
public class Product {
    @GraphId
    private Long id;
    private String name, productId, description;
    private Double unitPrice;
    @Transient
    private Boolean inStock;

    public Product() {
    }

    public Product(String name, String productId, String description, Double unitPrice) {
        this.name = name;
        this.productId = productId;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getInStock() {
        return inStock;
    }

    public void setInStock(Boolean inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productId='" + productId + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", inStock=" + inStock +
                '}';
    }
}