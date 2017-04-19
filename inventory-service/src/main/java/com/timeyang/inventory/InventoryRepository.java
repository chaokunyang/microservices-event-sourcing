package com.timeyang.inventory;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author chaokunyang
 */

public interface InventoryRepository extends GraphRepository<Inventory> {

    /**
     * Inventory到Product关系为：PRODUCT_TYPE
     * Shipment到Inventory关系为：CONTAINS_PRODUCT
     * @param productId
     * @return
     */
    @Query("MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory) WHERE product.productId = {productId} AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory")
    List<Inventory> getAvailableInventory(@Param("productId") String productId);

    @Query("MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory)-[:STOCKED_IN]->(:Warehouse {name:{warehouseName}}) WHERE product.productId = {productId} AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory")
    List<Inventory> getAvailableInventoryForProductAndWarehouse(@Param("productId") String productId, @Param("warehouseName") String warehouseName);

    @Query("MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory) WHERE product.productId in {productIds} AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory")
    List<Inventory> getAvailableInventoryForProductIds(@Param("productIds")String[] productIds);
}
