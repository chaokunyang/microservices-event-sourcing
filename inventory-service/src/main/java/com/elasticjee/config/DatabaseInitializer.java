package com.elasticjee.config;

import com.elasticjee.address.Address;
import com.elasticjee.address.AddressRepository;
import com.elasticjee.catalog.Catalog;
import com.elasticjee.catalog.CatalogRepository;
import com.elasticjee.inventory.Inventory;
import com.elasticjee.inventory.InventoryRepository;
import com.elasticjee.inventory.InventoryStatus;
import com.elasticjee.product.Product;
import com.elasticjee.product.ProductRepository;
import com.elasticjee.shipment.Shipment;
import com.elasticjee.shipment.ShipmentRepository;
import com.elasticjee.shipment.ShipmentStatus;
import com.elasticjee.warehouse.Warehouse;
import com.elasticjee.warehouse.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 初始化数据库
 *
 * @author yangck
 */
@Service
@Profile("dev")
public class DatabaseInitializer {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private Neo4jConfiguration neo4jConfiguration;

    public void populate() throws Exception {

        // 删除所有边和节点
        neo4jConfiguration.getSession().query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r;", new HashMap<>()).queryResults();

        List<Product> products = Arrays.asList(
                new Product("巴黎", "PD-00001", "<p>法国是欧洲浪漫的中心，它悠久历史、具有丰富文化内涵的名胜古迹及乡野风光吸引着世界各地的旅游者。风情万种的花都巴黎，美丽迷人的蓝色海岸，阿尔卑斯山的滑雪场等都是令人神往的旅游胜地 </p>", 9999.0),
                new Product("马尔代夫", "PD-00002", "<p>马尔代夫位于斯里兰卡南方的海域里，被称为印度洋上人间最后的乐园。<strong>马尔代夫由露出水面及部分露出水面的大大小小千余个珊瑚岛组成。</strong><br>" +
                        "<em>马尔代夫 蜜月与爱人情陷天堂岛</em></p>", 29999.0),
                new Product("印度尼西亚，巴厘岛", "PD-00003", "<p>　　它是南太平洋最美丽的景点之一。居住在这里的热情人们让巴厘岛是如此的特别。从肉饼饭到火山，再到古朴的巴厘岛村落，巴厘岛一定是你终身难忘的旅程。</p>", 29999.0),
                new Product("法国，普罗旺斯", "PD-00004", "<p>它已不再是一个单纯的地域名称，更代表了一种简单无忧、轻松慵懒的生活方式，一种宠辱不惊，看庭前花开花落；去留无意，望天上云卷云舒的闲适意境。<strong>如果旅行是为了摆脱生活的桎梏，普罗旺斯会让你忘掉一切。</strong></p>", 9999.0),
                new Product("捷克共和国，布拉格", "PD-00005", "<p><em>布拉格是保存最完整的中世纪城市之一。</em>它一定是任何旅游名单上的必去之地。不要错过夜晚的城堡美景。城堡在灯光的映衬之下显得尤为壮观。</p>", 9999.0)

        );
        productRepository.save(products);

        Catalog catalog = new Catalog(0L, "测试目录1");
        catalog.getProducts().addAll(products);
        catalogRepository.save(catalog);

        Address warehouseAddress = new Address("中国", "云南", "丽江", "古城区", "street1", "street2", 000000);
        Address shipToAddress = new Address("中国", "海南省", "三亚", "天涯区", "street1", "street2", 000001);
        addressRepository.save(Arrays.asList(warehouseAddress, shipToAddress));

        Warehouse warehouse = new Warehouse("测试仓库1");
        warehouse.setAddress(warehouseAddress);
        warehouse = warehouseRepository.save(warehouse);
        Warehouse finalWarehouse = warehouse;

        // 创建一个有着随机库存的库存集合
        Random random = new Random();
        Set<Inventory> inventories = products.stream()
                .map(product -> new Inventory(IntStream.range(0, 9).mapToObj(i -> Integer.toString(random.nextInt(9))).collect(Collectors.joining("")), product, finalWarehouse, InventoryStatus.IN_STOCK)).collect(Collectors.toSet());
        inventoryRepository.save(inventories);

        // 为每个产品生成10个额外库存
        for(int i = 0; i < 10; i++) {
            inventoryRepository.save(products.stream().map(product -> new Inventory(IntStream.range(0, 9).mapToObj(x -> Integer.toString(random.nextInt(9))).collect(Collectors.joining("")), product, finalWarehouse, InventoryStatus.IN_STOCK)).collect(Collectors.toSet()));
        }

        Shipment shipment = new Shipment(inventories, shipToAddress, warehouse, ShipmentStatus.SHIPPED);
        shipmentRepository.save(shipment);
    }

}
