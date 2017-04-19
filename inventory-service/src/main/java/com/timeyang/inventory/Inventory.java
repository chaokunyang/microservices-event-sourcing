package com.timeyang.inventory;

import com.timeyang.product.Product;
import com.timeyang.warehouse.Warehouse;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * 库存<br/>
 * 每个库存代表的产品数量为1。有多少个库存状态为IN_STOCK的库存，就有多少个产品有库存
 * @author chaokunyang
 */
@NodeEntity
public class Inventory {
    @GraphId
    private Long id;

    private String inventoryNumber;

    @Relationship(type = "PRODUCT_TYPE")
    private Product product;

    @Relationship(type = "STOCKED_IN")
    private Warehouse warehouse;

    private InventoryStatus status;

    public Inventory() {
    }

    public Inventory(String inventoryNumber, Product product, Warehouse warehouse, InventoryStatus status) {
        this.inventoryNumber = inventoryNumber;
        this.product = product;
        this.warehouse = warehouse;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInventoryNumber() {
        return inventoryNumber;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Warehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", inventoryNumber='" + inventoryNumber + '\'' +
                ", product=" + product +
                ", warehouse=" + warehouse +
                ", status=" + status +
                '}';
    }
}
