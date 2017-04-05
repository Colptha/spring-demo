package com.colptha.dom.converters;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.Shipment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 4/4/17.
 */
@Component
public class ShipmentConverter implements Converter<Shipment, ShipmentForm> {

    @Override
    public ShipmentForm convert(Shipment shipment) {
        ShipmentForm shipmentForm = new ShipmentForm();
        shipmentForm.setShipmentId(shipment.getShipmentId());
        shipmentForm.setCreatedOn(shipment.getCreatedOn());
        shipmentForm.setUpdatedOn(shipment.getUpdatedOn());
        shipmentForm.setProductLots(shipment.getProductLots());
        shipmentForm.setShipmentType(shipment.getShipmentType());

        return shipmentForm;
    }

    public Shipment convert(ShipmentForm shipmentForm) {
        Shipment shipment = new Shipment();
        shipment.setProductLots(shipmentForm.getProductLots());
        shipment.setShipmentId(shipmentForm.getShipmentId());
        shipment.setShipmentType(shipmentForm.getShipmentType());

        return shipment;
    }
}
