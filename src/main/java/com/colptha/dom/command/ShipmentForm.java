package com.colptha.dom.command;

import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Colptha on 4/1/17.
 */
public class ShipmentForm extends AbstractCommandObject {

    private Integer shipmentId;
    private ShipmentType shipmentType;
    private Map<ProductId, Integer> productIdToQuantityMap = new HashMap<>();

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }

    public Map<ProductId, Integer> getProductIdToQuantityMap() {
        return productIdToQuantityMap;
    }

    public void setProductIdToQuantityMap(Map<ProductId, Integer> productIdToQuantityMap) {
        this.productIdToQuantityMap = productIdToQuantityMap;
    }
}
