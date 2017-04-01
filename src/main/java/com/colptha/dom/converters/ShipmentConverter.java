package com.colptha.dom.converters;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.Shipment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 4/1/17.
 */
@Component
public class ShipmentConverter implements Converter<Shipment, ShipmentForm> {

    @Override
    public ShipmentForm convert(Shipment shipment) {
        ShipmentForm shipmentForm = new ShipmentForm();
        shipmentForm.setUpdatedOn(shipment.getUpdatedOn());
        shipmentForm.setCreatedOn(shipment.getCreatedOn());
        shipmentForm.setProductIdToQuantityMap(shipment.getProductIdToQuantityMap());
        shipmentForm.setShipmentId(shipment.getShipmentId());
        shipmentForm.setShipmentType(shipment.getShipmentType());

        return shipmentForm;
    }

    public Shipment convert(ShipmentForm shipmentForm) {
        Shipment shipment = new Shipment();
        // dates will be set on the way into the database
        shipment.setProductIdToQuantityMap(shipmentForm.getProductIdToQuantityMap());
        shipment.setShipmentId(shipmentForm.getShipmentId());
        shipment.setShipmentType(shipmentForm.getShipmentType());

        return shipment;
    }
}
