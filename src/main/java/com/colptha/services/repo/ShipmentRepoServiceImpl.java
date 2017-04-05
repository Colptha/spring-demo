package com.colptha.services.repo;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.services.ShipmentService;
import com.colptha.services.repo.interfaces.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/5/17.
 */
@Service
@Profile({"repo", "default"})
public class ShipmentRepoServiceImpl implements ShipmentService {
    private ShipmentRepository shipmentRepository;
    private ShipmentConverter shipmentConverter;

    @Autowired
    public void setShipmentConverter(ShipmentConverter shipmentConverter) {
        this.shipmentConverter = shipmentConverter;
    }

    @Autowired
    public void setShipmentRepository(ShipmentRepository shipmentRepository) {
        this.shipmentRepository = shipmentRepository;
    }

    @Override
    public Map<Integer, ShipmentForm> listAll() {
        Map<Integer, ShipmentForm> shipmentFormMap = new HashMap<>();
        shipmentRepository.findAll().forEach(shipment -> {
            shipmentFormMap.put(shipment.getShipmentId(), shipmentConverter.convert(shipment));
        });
        return shipmentFormMap;
    }

    @Override
    public ShipmentForm findOne(Integer shipmentId) throws NoSuchElementException {
        return shipmentConverter.convert(shipmentRepository.findByShipmentId(shipmentId));
    }

    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) {
        return shipmentConverter.convert(shipmentRepository.save(shipmentConverter.convert(shipmentForm)));
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentRepository.delete(shipmentId);
    }
}
