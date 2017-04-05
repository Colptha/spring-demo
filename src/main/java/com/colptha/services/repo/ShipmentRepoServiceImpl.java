package com.colptha.services.repo;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.Shipment;
import com.colptha.dom.enums.ShipmentType;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import com.colptha.services.repo.interfaces.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Colptha on 4/5/17.
 */
@Service
@Profile({"repo", "default"})
public class ShipmentRepoServiceImpl implements ShipmentService {
    private ShipmentRepository shipmentRepository;
    private ShipmentConverter shipmentConverter;
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

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
        Shipment shipment = shipmentConverter.convert(shipmentForm);
        ShipmentType shipmentType = shipment.getShipmentType();
        Set<ProductLot> currentLots = shipment.getProductLots();

        Optional<Integer> shipmentId = Optional.ofNullable(shipment.getShipmentId());

        if (shipmentId.isPresent()) {
            Shipment priorShipment = shipmentRepository.findByShipmentId(shipmentId.get());
            shipment.setCreatedOn(priorShipment.getCreatedOn());
            shipment.setId(priorShipment.getId());
            shipment.setVersion(priorShipment.getVersion());

            Set<ProductLot> priorLots = priorShipment.getProductLots();

            updateProductInventoryOnExistingShipment(currentLots, priorLots, shipmentType, productService);

        } else {
            updateProductInventoryOnNewShipment(currentLots, shipmentType, productService);
        }

        shipment.updateTimeStamps();

        return shipmentConverter.convert(shipmentRepository.save(shipment));
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentRepository.delete(shipmentId);
    }
}
