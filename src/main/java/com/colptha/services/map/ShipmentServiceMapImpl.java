package com.colptha.services.map;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.Shipment;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Colptha on 4/4/17.
 */
@Service
@Profile("map")
public class ShipmentServiceMapImpl implements ShipmentService {

    private static final Integer UNDO = -1;
    private Map<Integer, Shipment> shipmentMap = new HashMap<>();
    private ShipmentConverter shipmentConverter;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setShipmentConverter(ShipmentConverter shipmentConverter) {
        this.shipmentConverter = shipmentConverter;
    }

    @Override
    public Map<Integer, ShipmentForm> listAll() {
        Map<Integer, ShipmentForm> shipmentFormMap = new HashMap<>();

        shipmentMap.forEach((integer, shipment) -> shipmentFormMap.put(integer, shipmentConverter.convert(shipment)));

        return shipmentFormMap;
    }

    @Override
    public ShipmentForm findOne(Integer shipmentId) throws NoSuchElementException {
        Optional<Shipment> shipment = Optional.ofNullable(shipmentMap.get(shipmentId));
        return shipment.map(shipment1 -> shipmentConverter.convert(shipment1)).orElse(null);
    }

    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) {
        Shipment shipment = shipmentConverter.convert(shipmentForm);
        ShipmentType shipmentType = shipment.getShipmentType();
        Set<ProductLot> currentLots = shipment.getProductLots();

        Optional<Integer> shipmentId = Optional.ofNullable(shipment.getShipmentId());

        if (shipmentId.isPresent()) {
            Shipment priorShipment = shipmentMap.get(shipmentId.get());
            shipment.setCreatedOn(priorShipment.getCreatedOn());
            Set<ProductLot> priorLots = priorShipment.getProductLots();

            List<ProductId> lotsToRemoveByProductId = determineLotsToRemove(currentLots, priorLots);
            removeDeletedLots(lotsToRemoveByProductId, priorLots, shipmentType);
            updateProductInventoryOnModifiedLots(currentLots, priorLots, shipmentType);

        } else {
            shipment.setShipmentId(getNextId());
            updateProductInventoryOnNewShipment(currentLots, shipmentType);
        }

        shipment.updateTimeStamps();
        shipmentMap.put(shipment.getShipmentId(), shipment);
        return shipmentConverter.convert(shipment);
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentMap.remove(shipmentId);
    }

    private Integer getNextId() {

        if (shipmentMap.isEmpty()) {
            return 1;
        }

        Integer largest = Collections.max(shipmentMap.keySet());
        return largest + 1;
    }

    private void removeDeletedLots(List<ProductId> oldProductIdList, Set<ProductLot> priorLots,
                                   ShipmentType shipmentType) {

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

    private List<ProductId> determineLotsToRemove(Set<ProductLot> currentLots, Set<ProductLot> priorLots) {
        List<ProductId> oldProductIdList = new ArrayList<>();
        priorLots.forEach(productLot -> oldProductIdList.add(productLot.getProductId()));
        List<ProductId> newProductIdList = new ArrayList<>();
        currentLots.forEach(productLot -> newProductIdList.add(productLot.getProductId()));
        oldProductIdList.removeAll(newProductIdList);
        return oldProductIdList;
    }

    private void updateProductInventoryOnModifiedLots(Set<ProductLot> currentLots, Set<ProductLot> priorLots,
                                                      ShipmentType shipmentType) {

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

    private void updateProductInventoryOnNewShipment(Set<ProductLot> currentLots, ShipmentType shipmentType) {
        currentLots.forEach(lot -> {
            try {
                productService.updateInventory(lot.getProductId(), shipmentType.getInventoryDirection() * lot.getQuantity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
