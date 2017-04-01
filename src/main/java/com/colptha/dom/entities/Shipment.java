package com.colptha.dom.entities;

import com.colptha.dom.enums.ShipmentType;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Colptha on 4/1/17.
 */
@Entity
public class Shipment extends AbstractEntityObject {

    private Integer shipmentId;
    private ShipmentType shipmentType;
    private Map<String, Integer> productIdToQuantityMap = new HashMap<>();

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

    public Map<String, Integer> getProductIdToQuantityMap() {
        return productIdToQuantityMap;
    }

    public void setProductIdToQuantityMap(Map<String, Integer> productIdToQuantityMap) {
        this.productIdToQuantityMap = productIdToQuantityMap;
    }
}
