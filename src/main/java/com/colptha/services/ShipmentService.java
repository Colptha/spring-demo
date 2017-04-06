package com.colptha.services;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Colptha on 4/4/17.
 */
public interface ShipmentService extends CRUDService<ShipmentForm, Integer> {

    default void updateProductInventoryOnExistingShipment(Set<ProductLot> currentLots,
                                        Set<ProductLot> priorLots,
                                        ProductService productService) {

        List<ProductId> lotsToRemoveByProductId = determineLotsToRemove(currentLots, priorLots);
        removeDeletedLots(lotsToRemoveByProductId, priorLots, productService);
        updateProductInventoryOnModifiedLots(currentLots, priorLots, productService);
    }
    default void removeDeletedLots(List<ProductId> oldProductIdList,
                                   Set<ProductLot> priorLots,
                                   ProductService productService) {
        final Integer UNDO = -1;

        if (!oldProductIdList.isEmpty()) {
            priorLots.forEach(productLot -> {
                ProductId productId = productLot.getProductId();
                if (oldProductIdList.contains(productId)) {
                    try {
                        productService.updateInventory(
                                productId, UNDO  * productLot.getQuantity());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    default List<ProductId> determineLotsToRemove(Set<ProductLot> currentLots, Set<ProductLot> priorLots) {
        List<ProductId> oldProductIdList = new ArrayList<>();
        priorLots.forEach(productLot -> oldProductIdList.add(productLot.getProductId()));

        List<ProductId> newProductIdList = new ArrayList<>();
        currentLots.forEach(productLot -> newProductIdList.add(productLot.getProductId()));

        oldProductIdList.removeAll(newProductIdList);

        return oldProductIdList;
    }

    default void updateProductInventoryOnModifiedLots(Set<ProductLot> currentLots,
                                                      Set<ProductLot> priorLots,
                                                      ProductService productService) {

        currentLots.forEach(incomingProductLot -> {
            ProductId incomingProductId = incomingProductLot.getProductId();

            Optional<ProductLot> priorLot =
                    priorLots.stream().filter(lot -> lot.getProductId() == incomingProductId).findFirst();

            try {
                if (priorLot.isPresent()) {
                    Integer inventoryDiscrepancy = incomingProductLot.getQuantity() - priorLot.get().getQuantity();

                    productService.updateInventory(
                            incomingProductId, inventoryDiscrepancy);

                } else {
                    productService.updateInventory(
                            incomingProductId, incomingProductLot.getQuantity());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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

        //SCENARIOS
        // 1. new inbound           comes in positive goes out positive - DONT ADJUST
        // 2. new outbound          comes in positive goes out negative - ADJUST
        // 3. outbound -> inbound   comes in negative goes out positive - ADJUST but ShipmentType.INBOUND won't change it
        // 4. inbound -> outbound   comes in positive goes out negative - ADJUST
        // 5. outbound -> outbound  comes in negative goes out negative - DONT ADJUST
        // 6. inbound -> inbound    comes in positive goes out positive - DONT ADJUST

        if (ADJUSTMENT_NEEDED) {
            currentLots.forEach(lot -> lot.setQuantity(lot.getQuantity() * shipmentType.changeInventoryDirection()));
        }
    }
}
