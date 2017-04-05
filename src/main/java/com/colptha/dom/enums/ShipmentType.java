package com.colptha.dom.enums;

/**
 * Created by Colptha on 4/5/17.
 */
public enum ShipmentType {
    INBOUND (1),
    OUTBOUND (-1);

    private final Integer inventoryDirection;

    ShipmentType(Integer inventoryDirection) {
        this.inventoryDirection = inventoryDirection;
    }

    public Integer getInventoryDirection() {
        return inventoryDirection;
    }
}
