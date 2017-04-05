package com.colptha.dom.command;

import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ShipmentType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Colptha on 4/4/17.
 */
public class ShipmentForm extends AbstractCommandObject{
    private Integer shipmentId;
    private Set<ProductLot> productLots = new HashSet<>();
    private ShipmentType shipmentType;

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

    public Set<ProductLot> getProductLots() {
        return productLots;
    }

    public void setProductLots(Set<ProductLot> productLots) {
        this.productLots = productLots;
    }

    public ShipmentType getShipmentType() {
        return shipmentType;
    }

    public void setShipmentType(ShipmentType shipmentType) {
        this.shipmentType = shipmentType;
    }
}
