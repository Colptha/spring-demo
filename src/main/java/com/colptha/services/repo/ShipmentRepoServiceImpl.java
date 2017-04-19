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

import javax.persistence.OptimisticLockException;
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
    public TreeMap<Integer, ProductLot> listProductLotsByShipmentId(ProductId productId) {
        TreeMap<Integer, ProductLot> shipmentFormTreeMap = new TreeMap<>();

        listAll().forEach((shipmentId, shipmentForm) -> shipmentForm.getProductLots().forEach(productLot -> {
            if (productLot.getProductId().equals(productId)) {
                shipmentFormTreeMap.put(shipmentId, productLot);
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
        Integer incomingVersion = currentShipment.getVersion();

        // assign the possibleProductLots to the currentShipment as the set to be inventoried
        Set<ProductLot> currentLots = new HashSet<>();
        shipmentForm.getPossibleProductLots().forEach(currentLots::add);
        currentShipment.setProductLots(currentLots);

        // has the shipment been saved before (ie acquired a shipmentId?)
        Optional<Integer> shipmentId = Optional.ofNullable(currentShipment.getShipmentId());
        Shipment priorShipment = null;

        // if so, get old data from db for comparison and to set db id and created date
        if (shipmentId.isPresent()) {
            priorShipment = shipmentRepository.findByShipmentId(shipmentId.get());
        }

        processShipmentProductLots(shipmentId, currentShipment, priorShipment, currentLots, productService);

        if (shipmentId.isPresent()) {
            if (!shipmentRepository.findByShipmentId(shipmentId.get()).getVersion().equals(incomingVersion)) {
                throw new OptimisticLockException("Incoming shipment version no longer matches database shipment version");
            }
        }
        currentShipment.setVersion(currentShipment.getVersion() + 1);
        return shipmentConverter.convert(shipmentRepository.save(currentShipment));
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentRepository.delete(shipmentId);
    }
}
