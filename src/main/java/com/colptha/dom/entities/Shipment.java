package com.colptha.dom.entities;

import com.colptha.dom.enums.ShipmentType;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Colptha on 4/4/17.
 */
@Entity
public class Shipment extends AbstractEntityObject {

    private Integer shipmentId;
    private ShipmentType shipmentType;

    @ElementCollection
    @CollectionTable(name = "PRODUCT_LOT", joinColumns = @JoinColumn(name = "shipment_id"))
    private Set<ProductLot> productLots = new HashSet<>();

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
