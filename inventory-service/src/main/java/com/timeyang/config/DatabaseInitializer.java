package com.timeyang.config;

import com.timeyang.address.Address;
import com.timeyang.address.AddressRepository;
import com.timeyang.catalog.Catalog;
import com.timeyang.catalog.CatalogRepository;
import com.timeyang.inventory.Inventory;
import com.timeyang.inventory.InventoryRepository;
import com.timeyang.inventory.InventoryStatus;
import com.timeyang.product.Product;
import com.timeyang.product.ProductRepository;
import com.timeyang.shipment.Shipment;
import com.timeyang.shipment.ShipmentRepository;
import com.timeyang.shipment.ShipmentStatus;
import com.timeyang.warehouse.Warehouse;
import com.timeyang.warehouse.WarehouseRepository;
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
                new Product("捷克共和国，布拉格", "PD-00005", "<p><em>布拉格是保存最完整的中世纪城市之一。</em>它一定是任何旅游名单上的必去之地。不要错过夜晚的城堡美景。城堡在灯光的映衬之下显得尤为壮观。</p>", 9999.0),
                new Product("维多利亚瀑布", "PD-00006", "维多利亚瀑布，又称莫西奥图尼亚瀑布，位于非洲赞比西河中游，赞比亚与津巴布韦接壤处。宽1,700多米(5,500多英尺)，最高处108米(355英尺)，为世界著名瀑布奇观之一。<br/>维多利亚瀑布由‘魔鬼瀑布’、‘马蹄瀑布’、‘彩虹瀑布’、‘主瀑布’及‘东瀑布’共五道宽达百米的大瀑布组成<p><em>你站在瀑布边缘，看着瀑布一泻而下，发出如雷般的轰鸣，<strong>你无论如何大喊大叫，都听不到自己的声音，你的肾上腺素在体内涌动，你似乎体会到了临近死亡的感觉。</strong></em></p>", 13999.0),
                new Product("泰姬陵", "PD-00007", "泰姬陵（印地语：ताज महल，乌尔都语：تاج محل\u200E），是位于印度北方邦阿格拉的一座用白色大理石建造的陵墓，是印度知名度最高的古迹之一。<p>它是<em>莫卧儿王朝第5代皇帝沙贾汗</em>为了<strong>纪念他的第二任妻子已故皇后姬蔓·芭奴而兴建的陵墓</strong>，竣工于1654年。泰姬陵被广泛认为是“印度穆斯林艺术的珍宝和世界遗产中被广泛赞美的杰作之一”</p>", 13999.0),
                new Product("大堡礁", "PD-00008", "大堡礁（The Great Barrier Reef），是世界最大最长的珊瑚礁群，位于南半球，它纵贯于澳洲的东北沿海，北从托雷斯海峡，南到南回归线以南，绵延伸展共有2011公里，最宽处161公里。有2900个大小珊瑚礁岛，自然景观非常特殊。这里自然条件适宜，无大风大浪，成了多种鱼类的栖息地，而在那里不同的月份还能看到不同的水生珍稀动物，让游客大饱眼福。" +
                        "<p><em>在落潮时，部分的珊瑚礁露出水面形成珊瑚岛。在礁群与海岸之间是一条极方便的交通海路。<strong>风平浪静时，游船在此间通过，船下连绵不断的多彩、多形的珊瑚景色，就成为吸引世界各地游客来猎奇观赏的最佳海底奇观。</strong></em>大堡礁属热带气候，主要受南半球气流控制。</p>", 13999.0)
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

        // 创建一个有着随机库存编号的库存集合。即为每个产品生成一个产品数量为1的库存，有着随机库存编号
        Random random = new Random();
        Set<Inventory> inventories = products.stream()
                .map(product -> new Inventory(IntStream.range(0, 9).mapToObj(i -> Integer.toString(random.nextInt(9))).collect(Collectors.joining("")), product, finalWarehouse, InventoryStatus.IN_STOCK)).collect(Collectors.toSet());
        inventoryRepository.save(inventories);

        // 为每个产品生成10个额外库存。因为一个库存代表一个数量为1的产品，所以每个产品将有10个库存
        for(int i = 0; i < 10; i++) {
            inventoryRepository.save(products.stream().map(product -> new Inventory(IntStream.range(0, 9).mapToObj(x -> Integer.toString(random.nextInt(9))).collect(Collectors.joining("")), product, finalWarehouse, InventoryStatus.IN_STOCK)).collect(Collectors.toSet()));
        }

        Shipment shipment = new Shipment(inventories, shipToAddress, warehouse, ShipmentStatus.SHIPPED);
        shipmentRepository.save(shipment);
    }

}
