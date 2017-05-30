package com.colptha.services.repo;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.Shipment;
import com.colptha.dom.entities.exceptions.NegativeInventoryException;
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
        Shipment shipment = shipmentRepository.findByShipmentId(shipmentId);
        if (shipment == null) throw new NoSuchElementException("shipmentId " + shipmentId + " not found");
        return shipmentConverter.convert(shipment);
    }

    @Transactional
    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) throws NegativeInventoryException {

        boolean isNewShipment = shipmentForm.getIsNewShipment();

        Shipment currentShipment = shipmentConverter.convert(shipmentForm);
        Integer incomingVersion = currentShipment.getVersion();

        Set<ProductLot> currentLots = new HashSet<>();
        shipmentForm.getPossibleProductLots().addAll(currentLots);
        currentShipment.setProductLots(currentLots);

        Shipment priorShipment = null;
        Integer shipmentId = null;

        if (!shipmentForm.getIsNewShipment()) {
            shipmentId = shipmentForm.getShipmentId();
            priorShipment = shipmentRepository.findByShipmentId(shipmentId);
            currentShipment.setDatabaseId(priorShipment.getDatabaseId());
        }

        processShipmentProductLots(isNewShipment, currentShipment, priorShipment, currentLots, productService);

        if (!isNewShipment) {
            if (!shipmentRepository.findByShipmentId(shipmentId).getVersion().equals(incomingVersion)) {
                throw new OptimisticLockException("Incoming shipment version no longer matches database shipment version");
            }
        }

        currentShipment.setVersion(currentShipment.getVersion() + 1);
        return shipmentConverter.convert(shipmentRepository.save(currentShipment));
    }

    @Override
    public void delete(Integer shipmentId) {
        int databaseId = shipmentRepository.findByShipmentId(shipmentId).getDatabaseId();
        shipmentRepository.delete(databaseId);
    }

    @Override
    public void clearInventoryByShipmentId(Integer shipmentId) throws NegativeInventoryException {
        Shipment shipment = shipmentRepository.findByShipmentId(shipmentId);
        ShipmentForm shipmentForm = shipmentConverter.convert(shipment);

        saveOrUpdate(shipmentForm);
    }
}
