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
    public void testSaveAndUpdate() throws Exception {
        ProductId firstProductId = ProductId.A4;
        ProductId secondProductId = ProductId.A5;
        ProductId thirdProductId = ProductId.C2;
        ProductId fourthProductId = ProductId.C4;
        Integer firstQuantity = 125;
        Integer secondQuantity = 375;
        Integer thirdQuantity = 50;
        Integer changedQuantity = 25;

        ShipmentForm shipmentForm = new ShipmentForm();

        ProductLot productLot1 = new ProductLot();
        productLot1.setProductId(firstProductId);
        productLot1.setQuantity(firstQuantity);

        ProductLot productLot2 = new ProductLot();
        productLot2.setProductId(secondProductId);
        productLot2.setQuantity(secondQuantity);

        ProductLot productLot3 = new ProductLot();
        productLot3.setProductId(thirdProductId);
        productLot3.setQuantity(thirdQuantity);

        Set<ProductLot> productLotSet = new HashSet<>();
        productLotSet.add(productLot1);
        productLotSet.add(productLot2);
        productLotSet.add(productLot3);

        shipmentForm.setShipmentType(ShipmentType.INBOUND);
        shipmentForm.setProductLots(productLotSet);

        Integer preSaveQuantityOnFirstProductId = productService.findOne(firstProductId).getInventory();
        Integer preSaveQuantityOnSecondProductId = productService.findOne(secondProductId).getInventory();
        Integer preSaveQuantityOnThirdProductId = productService.findOne(thirdProductId).getInventory();
        Integer preSaveQuantityOnFourthProductId = productService.findOne(fourthProductId).getInventory();
        System.out.println("#####################");
        System.out.println("Presave first product: " + preSaveQuantityOnFirstProductId);
        System.out.println("Adding... " + firstQuantity);
        System.out.println("Presave second product: " + preSaveQuantityOnSecondProductId);
        System.out.println("Adding... " + secondQuantity);
        System.out.println("Presave third product: " + preSaveQuantityOnThirdProductId);
        System.out.println("Adding... " + thirdQuantity);
        System.out.println("Presave fourth product: " + preSaveQuantityOnFourthProductId);
        System.out.println("No changes made.");
        System.out.println("#####################");

        ShipmentForm savedShipmentForm = shipmentService.saveOrUpdate(shipmentForm);

        Integer postSaveQuantityOnFirstProductId = productService.findOne(firstProductId).getInventory();
        Integer postSaveQuantityOnSecondProductId = productService.findOne(secondProductId).getInventory();
        Integer postSaveQuantityOnThirdProductId = productService.findOne(thirdProductId).getInventory();
        Integer postSaveQuantityOnFourthProductId = productService.findOne(fourthProductId).getInventory();

        System.out.println("#####################");
        System.out.println("Postsave first product: " + postSaveQuantityOnFirstProductId);
        System.out.println("Should be: " + (firstQuantity + preSaveQuantityOnFirstProductId));
        System.out.println("Postsave second product: " + postSaveQuantityOnSecondProductId);
        System.out.println("Should be: " + (secondQuantity + preSaveQuantityOnSecondProductId));
        System.out.println("Postsave third product: " + postSaveQuantityOnThirdProductId);
        System.out.println("Should be: " + (thirdQuantity + preSaveQuantityOnThirdProductId));
        System.out.println("Postsave fourth product: " + postSaveQuantityOnFourthProductId);
        System.out.println("Should be: " + preSaveQuantityOnFourthProductId);
        System.out.println("#####################");

        assert savedShipmentForm.getShipmentId() != null;
        assert postSaveQuantityOnFirstProductId
                .equals(preSaveQuantityOnFirstProductId + firstQuantity);
        assert postSaveQuantityOnSecondProductId
                .equals(preSaveQuantityOnSecondProductId + secondQuantity);

        // same productId but changing quantity
        ProductLot productLot4 = new ProductLot();
        productLot4.setProductId(firstProductId);
        productLot4.setQuantity(changedQuantity);

        // same quantity but changing productId
        ProductLot productLot5 = new ProductLot();
        productLot5.setProductId(fourthProductId);
        productLot5.setQuantity(secondQuantity);
        // the 'missing lot' should be removed

        Set<ProductLot> newProductLotSet = new HashSet<>();
        newProductLotSet.add(productLot4);
        newProductLotSet.add(productLot5);
        savedShipmentForm.setProductLots(newProductLotSet);

        shipmentService.saveOrUpdate(savedShipmentForm);

        Integer postUpdateSaveQuantityOnFirstProductId = productService.findOne(firstProductId).getInventory();
        Integer postUpdateSaveQuantityOnSecondProductId = productService.findOne(secondProductId).getInventory();
        Integer postUpdateSaveQuantityOnThirdProductId = productService.findOne(thirdProductId).getInventory();
        Integer postUpdateSaveQuantityOnFourthProductId = productService.findOne(fourthProductId).getInventory();

        System.out.println("#####################");
        System.out.println("PostUpdate first product: " + postUpdateSaveQuantityOnFirstProductId);
        System.out.println("Should be: " + (preSaveQuantityOnFirstProductId + changedQuantity));
        System.out.println("PostUpdate second product: " + postUpdateSaveQuantityOnSecondProductId);
        System.out.println("Should be: " + preSaveQuantityOnSecondProductId);
        System.out.println("PostUpdate third product: " + postUpdateSaveQuantityOnThirdProductId);
        System.out.println("Should be: " + preSaveQuantityOnThirdProductId);
        System.out.println("PostUpdate fourth product: " + postUpdateSaveQuantityOnFourthProductId);
        System.out.println("Should be: " + (secondQuantity + preSaveQuantityOnFourthProductId));
        System.out.println("#####################");

        assert postUpdateSaveQuantityOnFirstProductId
                .equals(preSaveQuantityOnFirstProductId + changedQuantity);
        assert postUpdateSaveQuantityOnSecondProductId
                .equals(preSaveQuantityOnSecondProductId);
        assert postUpdateSaveQuantityOnThirdProductId
                .equals(preSaveQuantityOnThirdProductId);
        assert postUpdateSaveQuantityOnFourthProductId
                .equals(secondQuantity + preSaveQuantityOnFourthProductId);
        // test outbound to inbound

        ProductLot outboundProductLot1 = new ProductLot();
        outboundProductLot1.setProductId(firstProductId);
        outboundProductLot1.setQuantity(changedQuantity);

        ProductLot outboundProductLot2 = new ProductLot();
        outboundProductLot2.setProductId(fourthProductId);
        outboundProductLot2.setQuantity(secondQuantity);

        Set<ProductLot> outboundProductLotSet = new HashSet<>();
        outboundProductLotSet.add(outboundProductLot1);
        outboundProductLotSet.add(outboundProductLot2);

        ShipmentForm outboundShipmentForm = new ShipmentForm();
        outboundShipmentForm.setShipmentType(ShipmentType.OUTBOUND);
        outboundShipmentForm.setProductLots(outboundProductLotSet);

        ShipmentForm savedOutboundShipmentForm = shipmentService.saveOrUpdate(outboundShipmentForm);

        Integer postOutboundSaveQuantityOnFirstProductId = productService.findOne(firstProductId).getInventory();
        Integer postOutboundSaveQuantityOnSecondProductId = productService.findOne(secondProductId).getInventory();
        Integer postOutboundSaveQuantityOnThirdProductId = productService.findOne(thirdProductId).getInventory();
        Integer postOutboundSaveQuantityOnFourthProductId = productService.findOne(fourthProductId).getInventory();

        System.out.println("#####################");
        System.out.println("PostOutbound first product: " + postOutboundSaveQuantityOnFirstProductId);
        System.out.println("Should be: " + preSaveQuantityOnFirstProductId);
        System.out.println("PostOutbound second product: " + postOutboundSaveQuantityOnSecondProductId);
        System.out.println("Should be: " + preSaveQuantityOnSecondProductId);
        System.out.println("PostOutbound third product: " + postOutboundSaveQuantityOnThirdProductId);
        System.out.println("Should be: " + preSaveQuantityOnThirdProductId);
        System.out.println("PostOutbound fourth product: " + postOutboundSaveQuantityOnFourthProductId);
        System.out.println("Should be: " + preSaveQuantityOnFourthProductId);
        System.out.println("#####################");

        assert postOutboundSaveQuantityOnFirstProductId
                .equals(preSaveQuantityOnFirstProductId);
        assert postOutboundSaveQuantityOnFourthProductId
                .equals(preSaveQuantityOnFourthProductId);

        savedOutboundShipmentForm.setShipmentType(ShipmentType.INBOUND);

        shipmentService.saveOrUpdate(savedOutboundShipmentForm);

        Integer outboundToInboundSaveQuantityOnFirstProductId = productService.findOne(firstProductId).getInventory();
        Integer outboundToInboundSaveQuantityOnSecondProductId = productService.findOne(secondProductId).getInventory();
        Integer outboundToInboundSaveQuantityOnThirdProductId = productService.findOne(thirdProductId).getInventory();
        Integer outboundToInboundSaveQuantityOnFourthProductId = productService.findOne(fourthProductId).getInventory();

        System.out.println("#####################");
        System.out.println("PostOutbound first product: " + outboundToInboundSaveQuantityOnFirstProductId);
        System.out.println("Should be: " + (preSaveQuantityOnFirstProductId + (changedQuantity * 2)));
        System.out.println("PostOutbound second product: " + outboundToInboundSaveQuantityOnSecondProductId);
        System.out.println("Should be: " + preSaveQuantityOnSecondProductId);
        System.out.println("PostOutbound third product: " + outboundToInboundSaveQuantityOnThirdProductId);
        System.out.println("Should be: " + preSaveQuantityOnThirdProductId);
        System.out.println("PostOutbound fourth product: " + outboundToInboundSaveQuantityOnFourthProductId);
        System.out.println("Should be: " + (preSaveQuantityOnFourthProductId + (secondQuantity * 2)));
        System.out.println("#####################");

        assert outboundToInboundSaveQuantityOnFirstProductId
                .equals(preSaveQuantityOnFirstProductId + (changedQuantity * 2));
        assert outboundToInboundSaveQuantityOnFourthProductId
                .equals(preSaveQuantityOnFourthProductId + (secondQuantity * 2));
        // need to do an inbound to an outbound
    }

    @Test
    public void testDelete() throws Exception {
        assert shipmentService.findOne(2) != null;
        shipmentService.delete(2);
        Optional<ShipmentForm> shipmentForm = Optional.ofNullable(shipmentService.findOne(2));
        assert !shipmentForm.isPresent();
    }
}
