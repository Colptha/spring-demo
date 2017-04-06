package com.colptha.dom.enums;

/**
 * Created by Colptha on 4/5/17.
 */
public enum ShipmentType {
    INBOUND,
    OUTBOUND;

    private final Integer changeInventoryDirection = -1;

    ShipmentType() {}

    public Integer changeInventoryDirection() {
        return changeInventoryDirection;
    }
}
