package com.colptha.services.repo;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.Shipment;
import com.colptha.dom.enums.ProductId;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import com.colptha.services.repo.interfaces.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        shipmentRepository.findAll().forEach(
                shipment -> shipmentFormMap.put(shipment.getShipmentId(), shipmentConverter.convert(shipment)));

        return shipmentFormMap;
    }

    @Override
    public TreeMap<Integer, ProductLot> listByProductId(ProductId productId) {
        TreeMap<Integer, ProductLot> shipmentFormTreeMap = new TreeMap<>();

        listAll().forEach((integer, shipmentForm) -> shipmentForm.getProductLots().forEach(productLot -> {
            if (productLot.getProductId().equals(productId)) {
                shipmentFormTreeMap.put(integer, productLot);
            }
        }));

        return shipmentFormTreeMap;
    }

    @Override
    public ShipmentForm findOne(Integer shipmentId) throws NoSuchElementException {
        return shipmentConverter.convert(shipmentRepository.findByShipmentId(shipmentId));
    }

    @Override
    @Transactional
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) {
        Shipment currentShipment = shipmentConverter.convert(shipmentForm);

        Set<ProductLot> currentLots = currentShipment.getProductLots();

        Optional<Integer> shipmentId = Optional.ofNullable(currentShipment.getShipmentId());
        ShipmentForm priorShipment = null;

        if (shipmentId.isPresent()) {
            priorShipment = findOne(shipmentId.get());
        }

        processShipmentProductLots(shipmentId, currentShipment, priorShipment, currentLots, productService);

        return shipmentConverter.convert(shipmentRepository.save(currentShipment));
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentRepository.delete(shipmentId);
    }
}
