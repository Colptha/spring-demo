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
                                        ShipmentType shipmentType,
                                        ProductService productService) {

        List<ProductId> lotsToRemoveByProductId = determineLotsToRemove(currentLots, priorLots);
        removeDeletedLots(lotsToRemoveByProductId, priorLots, shipmentType, productService);
        updateProductInventoryOnModifiedLots(currentLots, priorLots, shipmentType, productService);
    }
    default void removeDeletedLots(List<ProductId> oldProductIdList,
                                   Set<ProductLot> priorLots,
                                   ShipmentType shipmentType,
                                   ProductService productService) {
        final Integer UNDO = -1;

        if (!oldProductIdList.isEmpty()) {
            priorLots.forEach(productLot -> {
                ProductId productId = productLot.getProductId();
                if (oldProductIdList.contains(productId)) {
                    try {
                        productService.updateInventory(
                                productId, UNDO * shipmentType.getInventoryDirection() * productLot.getQuantity());
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
                                                      ShipmentType shipmentType,
                                                      ProductService productService) {

        currentLots.forEach(incomingProductLot -> {
            ProductId incomingProductId = incomingProductLot.getProductId();

            Optional<ProductLot> priorLot =
                    priorLots.stream().filter(lot -> lot.getProductId() == incomingProductId).findFirst();

            try {
                if (priorLot.isPresent()) {
                    Integer inventoryDiscrepancy = incomingProductLot.getQuantity() - priorLot.get().getQuantity();

                    productService.updateInventory(
                            incomingProductId, shipmentType.getInventoryDirection() * inventoryDiscrepancy);

                } else {
                    productService.updateInventory(
                            incomingProductId, shipmentType.getInventoryDirection() * incomingProductLot.getQuantity());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    default void updateProductInventoryOnNewShipment(Set<ProductLot> currentLots,
                                                     ShipmentType shipmentType,
                                                     ProductService productService) {
        currentLots.forEach(lot -> {
            try {
                productService.updateInventory(lot.getProductId(), shipmentType.getInventoryDirection() * lot.getQuantity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
