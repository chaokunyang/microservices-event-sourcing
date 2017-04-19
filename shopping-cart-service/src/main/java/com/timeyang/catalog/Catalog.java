package com.timeyang.catalog;


import com.timeyang.product.Product;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 目录
 * @author yangck
 */
public class Catalog implements Serializable {
    private Long id;

    private String name;

    private Set<Product> products = new HashSet<>();

    @Override
    public String toString() {
        return "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                '}';
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
