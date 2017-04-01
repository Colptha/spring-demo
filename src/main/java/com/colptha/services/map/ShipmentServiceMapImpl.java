package com.colptha.services.map;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.dom.entities.Shipment;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/1/17.
 */
@Service
@Profile("map")
public class ShipmentServiceMapImpl implements ShipmentService {

    private Map<Integer, Shipment> shipments = new HashMap<>();
    private ShipmentConverter shipmentConverter;

    @Autowired
    public void setShipmentConverter(ShipmentConverter shipmentConverter) {
        this.shipmentConverter = shipmentConverter;
    }

    @Override
    public Map<Integer, ShipmentForm> listAll() {
        Map<Integer, ShipmentForm> shipmentFormMap = new HashMap<>();
        shipments.forEach((integer, shipment) -> shipmentFormMap.put(integer, shipmentConverter.convert(shipment)));
        return  shipmentFormMap;
    }

    @Override
    public ShipmentForm findOne(Integer query) throws NoSuchElementException {
        return shipmentConverter.convert(shipments.get(query));
    }

    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) {
        // dates will be trashed during conversion (for consistency of created on dates)
        Shipment shipment = shipmentConverter.convert(shipmentForm);
        Integer shipmentId = shipment.getShipmentId();

        // check for and add creation date
        if (shipments.containsKey(shipmentId)) {
            shipment.setCreatedOn(shipments.get(shipmentId).getCreatedOn());
        }

        // normally Hibernate would update time stamps
        shipment.updateTimeStamps();
        shipments.put(shipmentId, shipment);

        // send back with valid timestamps
        return shipmentConverter.convert(shipment);
    }

    @Override
    public ShipmentForm delete(Integer query) {
        return shipmentConverter.convert(shipments.remove(query));
    }
}
