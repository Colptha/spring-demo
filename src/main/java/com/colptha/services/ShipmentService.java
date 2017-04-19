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

    default TreeMap<Integer, ProductLot> listProductLotsByProductId(ShipmentForm shipmentForm) {
        TreeMap<Integer, ProductLot> productIdProductLotTreeMap = new TreeMap<>();
        shipmentForm.getProductLots()
                .forEach(productLot -> productIdProductLotTreeMap.put(productLot.getProductId().ordinal(), productLot));
        return productIdProductLotTreeMap;
    }

    default void processShipmentProductLots(Optional<Integer> shipmentId,
                                            Shipment currentShipment,
                                            Shipment priorShipment,
                                            Set<ProductLot> currentLots,
                                            ProductService productService) {

        ShipmentType shipmentType = currentShipment.getShipmentType();

        ShipmentType priorShipmentType = shipmentId.map(
                ifStoredId -> priorShipment.getShipmentType())
                .orElse(null);

        adjustInventoryDirection(shipmentType, priorShipmentType, currentLots);

        if (shipmentId.isPresent()) {
            currentShipment.setCreatedOn(priorShipment.getCreatedOn());
            currentShipment.setDatabaseId(priorShipment.getDatabaseId());

            Set<ProductLot> priorLots = priorShipment.getProductLots();

            // returns list of product id's that are no longer in shipment, hence all lots with these product id's
            // should be 'reverse saved' to the product service to remove their erroneous persistence
            // then they should be removed from priorLots, as they shouldn't be compared to current lots again
            // to detect inventory changes
//            List<ProductId> lotsToRemoveByProductId = determineLotsToRemove(currentLots, priorLots);

            // prior lots should be passed in, any lot with a product id that should be removed should be 'reversed'
            // and then deleted from prior lots
//            versionModifier = removeDeletedLots(lotsToRemoveByProductId, priorLots, productService);

            // finally with only those product lots left in the current and prior lot groups, the inventory difference
            // can be compared and persisted to correct the inventory
            updateProductInventoryOnModifiedLots(currentLots, priorLots, productService);
        } else {
            currentShipment.setShipmentId(getNextId());
            updateProductInventoryOnNewShipment(currentLots, productService);
        }
    }

//    default List<ProductId> determineLotsToRemove(Set<ProductLot> currentLots, Set<ProductLot> priorLots) {
//        List<ProductId> oldProductIdList = new ArrayList<>();
//        priorLots.forEach(productLot -> oldProductIdList.add(productLot.getProductId()));
//
//        List<ProductId> newProductIdList = new ArrayList<>();
//        currentLots.forEach(productLot -> newProductIdList.add(productLot.getProductId()));
//
//        oldProductIdList.removeAll(newProductIdList);
//
//        return oldProductIdList;
//    }

//    default Integer removeDeletedLots(List<ProductId> removeMeList,
//                                        Set<ProductLot> priorLots,
//                                        ProductService productService) {
//        final Integer UNDO = -1;
//        Integer versionModifier = 0;
//
//        if (!removeMeList.isEmpty()) {
//            Iterator<ProductLot> priorLotsIterator = priorLots.iterator();
//
//            if (priorLotsIterator.hasNext()) {
//                ProductLot productLot = priorLotsIterator.next();
//                ProductId productId = productLot.getProductId();
//                if (removeMeList.contains(productId)) {
//                    try {
//                        productService.updateInventory(
//                                productId, UNDO  * productLot.getQuantity());
//                        priorLotsIterator.remove();
//                        versionModifier++;
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//
//        }
//        return versionModifier;
//    }

    default void updateProductInventoryOnModifiedLots(Set<ProductLot> currentLots,
                                                      Set<ProductLot> priorLots,
                                                      ProductService productService) {

        for (ProductLot incomingProductLot : currentLots) {


            ProductId incomingProductId = incomingProductLot.getProductId();

            Optional<ProductLot> priorLot =
                    priorLots.stream().filter(lot -> lot.getProductId() == incomingProductId).findFirst();

            try {
                if (priorLot.isPresent()) {
                    Integer inventoryDiscrepancy = incomingProductLot.getQuantity() - priorLot.get().getQuantity();
                    if (!inventoryDiscrepancy.equals(0)) {
                        productService.updateInventory(incomingProductId, inventoryDiscrepancy);
                    }

                } else {
                    productService.updateInventory(incomingProductId, incomingProductLot.getQuantity());
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
