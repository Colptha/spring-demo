package com.colptha.services;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.Shipment;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;

import java.util.*;

/**
 * Created by Colptha on 4/4/17.
 */
public interface ShipmentService extends CRUDService<ShipmentForm, Integer> {

    TreeMap<Integer, ProductLot> listProductLotsByShipmentId(ProductId productId);

    void clearInventoryByShipmentId(Integer shipmentId) throws Exception;

    default TreeMap<Integer, ProductLot> listProductLotsByProductId(ShipmentForm shipmentForm) {
        TreeMap<Integer, ProductLot> productIdProductLotTreeMap = new TreeMap<>();
        shipmentForm.getProductLots()
                .forEach(productLot -> productIdProductLotTreeMap.put(productLot.getProductId().ordinal(), productLot));
        return productIdProductLotTreeMap;
    }

    default void processShipmentProductLots(boolean isNewShipment, Shipment currentShipment,
                                            Shipment priorShipment, Set<ProductLot> currentLots,
                                            ProductService productService) throws Exception {

        ShipmentType shipmentType = currentShipment.getShipmentType();

        ShipmentType priorShipmentType = null;
        Set<ProductLot> priorLots = null;

        if (!isNewShipment) {
            priorShipmentType = priorShipment.getShipmentType();
            priorLots = priorShipment.getProductLots();
        }

        adjustInventoryDirection(shipmentType, priorShipmentType, currentLots);

        checkForInventoryErrors(currentLots, priorLots, productService);

        if (!isNewShipment) {
            currentShipment.setCreatedOn(priorShipment.getCreatedOn());
            currentShipment.setDatabaseId(priorShipment.getDatabaseId());

            updateProductInventoryOnModifiedLots(currentLots, priorLots, productService);
        } else {
            currentShipment.setShipmentId(getNextId());

            updateProductInventoryOnNewShipment(currentLots, productService);
        }
    }

    default void checkForInventoryErrors(Set<ProductLot> currentLots, Set<ProductLot> priorLots, ProductService productService) throws Exception {

        for (ProductLot incomingProductLot : currentLots) {
            ProductId incomingProductId = incomingProductLot.getProductId();

            ProductLot priorLot;

            Integer inventoryChange;
            if (priorLots == null) {
                inventoryChange = incomingProductLot.getQuantity();
            } else {
                priorLot = priorLots.stream()
                        .filter(productLot -> productLot.getProductId() == incomingProductId).findFirst().get();

                inventoryChange = incomingProductLot.getQuantity() - priorLot.getQuantity();
            }

            Integer inventoryInWarehouse = productService.findOne(incomingProductId).getProductInventory();
            if (inventoryInWarehouse + inventoryChange < 0) {
                throw new Exception("Product inventory cannot be negative: Product ID: " + incomingProductId);
            }

        }
    }

    default void updateProductInventoryOnModifiedLots(Set<ProductLot> currentLots,
                                                      Set<ProductLot> priorLots,
                                                      ProductService productService) {

        for (ProductLot incomingProductLot : currentLots) {

            ProductId incomingProductId = incomingProductLot.getProductId();

            ProductLot priorLot = priorLots.stream()
                    .filter(lot -> lot.getProductId() == incomingProductId).findFirst().get();

            try {
                Integer inventoryDiscrepancy = incomingProductLot.getQuantity() - priorLot.getQuantity();
                if (!inventoryDiscrepancy.equals(0)) {
                    productService.updateInventory(incomingProductId, inventoryDiscrepancy);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    default void updateProductInventoryOnNewShipment(Set<ProductLot> currentLots, ProductService productService) {
        currentLots.forEach(lot -> {
            try {
                productService.updateInventory(lot.getProductId(), lot.getQuantity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    default void adjustInventoryDirection(ShipmentType shipmentType,
                                          ShipmentType priorShipmentType,
                                          Set<ProductLot> currentLots) {

        final boolean ADJUSTMENT_NEEDED =
                (priorShipmentType == null && shipmentType == ShipmentType.OUTBOUND) ||
                        (priorShipmentType == ShipmentType.OUTBOUND && shipmentType == ShipmentType.INBOUND) ||
                        (priorShipmentType == ShipmentType.INBOUND && shipmentType == ShipmentType.OUTBOUND);

       /*
         SCENARIOS
         1. new inbound           comes in positive goes out positive - DO NOT ADJUST
         2. new outbound          comes in positive goes out negative - ADJUST
         3. outbound -> inbound   comes in negative goes out positive - ADJUST
         4. inbound -> outbound   comes in positive goes out negative - ADJUST
         5. outbound -> outbound  comes in negative goes out negative - DO NOT ADJUST
         6. inbound -> inbound    comes in positive goes out positive - DO NOT ADJUST
       */

        if (ADJUSTMENT_NEEDED) {
            currentLots.forEach(lot -> lot.setQuantity(lot.getQuantity() * shipmentType.changeInventoryDirection()));
        }
    }

    default Integer getNextId() {
        Map<Integer, ShipmentForm> formMap = listAll();

        if (formMap.isEmpty()) {
            return 1;
        }

        return Collections.max(formMap.keySet()) + 1;
    }
}
