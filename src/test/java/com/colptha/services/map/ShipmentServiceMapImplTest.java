package com.colptha.services.map;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Colptha on 4/5/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("map")
public class ShipmentServiceMapImplTest {
    private ShipmentService shipmentService;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Test
    public void testListAll() throws Exception {
        assert !shipmentService.listAll().isEmpty();
    }

    @Test
    public void testFindOne() throws Exception {
        Integer key = 1;
        Optional<ShipmentForm> shipmentForm = Optional.ofNullable(shipmentService.findOne(key));
        assert shipmentForm.isPresent();
    }

    @Test
    public void testSave() throws Exception {
        ProductId productId = ProductId.A1;
        Integer amountAdded = 100;

        ProductLot productLot = new ProductLot();
        productLot.setProductId(productId);
        productLot.setQuantity(amountAdded);

        Integer productQuantity = productService.findOne(productId).getInventory();

        ShipmentForm shipmentForm = new ShipmentForm();
        shipmentForm.setShipmentType(ShipmentType.INBOUND);
        shipmentForm.getProductLots().add(productLot);

        ShipmentForm savedShipmentForm = shipmentService.saveOrUpdate(shipmentForm);

        assert savedShipmentForm.getProductLots().equals(shipmentForm.getProductLots());
        assert savedShipmentForm.getShipmentId() != null;
        assert savedShipmentForm.getCreatedOn() != null;
        assert savedShipmentForm.getUpdatedOn() != null;
        assert productService.findOne(productId).getInventory() == productQuantity + amountAdded;
    }

    @Test
    public void testUpdate() throws Exception {
        Integer shipmentId = 1;
        ShipmentForm shipmentForm = shipmentService.findOne(shipmentId);
        assert shipmentForm != null;
        Set<ProductLot> productLotSet = shipmentForm.getProductLots();
        assert productLotSet != null;

        ProductId productId = ((ProductLot) productLotSet.toArray()[0]).getProductId();
        Integer productInventory = productService.findOne(productId).getInventory();
        Integer oldProductLotInventory = ((ProductLot) productLotSet.toArray()[0]).getQuantity();
        assert oldProductLotInventory != 200;
        Integer newProductLotInventory = 200;
        Integer discrepancy = newProductLotInventory - oldProductLotInventory;

        ProductLot newProductLot = new ProductLot();
        newProductLot.setProductId(productId);
        newProductLot.setQuantity(newProductLotInventory);
        Set<ProductLot> newProductLotSet = new HashSet<>();
        newProductLotSet.add(newProductLot);

        shipmentForm.setProductLots(newProductLotSet);
        assert shipmentForm.getShipmentId().equals(shipmentId);
        shipmentForm.setShipmentType(ShipmentType.OUTBOUND);

        ShipmentForm updatedShipmentForm = shipmentService.saveOrUpdate(shipmentForm);

        System.out.println("productInventory before update: " + productInventory);
        System.out.println("discrepancy: " + discrepancy);
        System.out.println("actual data after update: " + productService.findOne(productId).getInventory());
        assert productService.findOne(productId).getInventory() == productInventory - discrepancy;
        assert updatedShipmentForm.getProductLots() != null;
        assert updatedShipmentForm.getShipmentId().equals(shipmentId);
        assert updatedShipmentForm.getUpdatedOn() != shipmentForm.getUpdatedOn();
        assert updatedShipmentForm.getUpdatedOn() != null;
        assert updatedShipmentForm.getCreatedOn() != shipmentForm.getUpdatedOn();

    }

    @Test
    public void testDelete() throws Exception {
        assert shipmentService.findOne(1) != null;
        shipmentService.delete(2);
        Optional<ShipmentForm> shipmentForm = Optional.ofNullable(shipmentService.findOne(2));
        assert !shipmentForm.isPresent();
    }
}
