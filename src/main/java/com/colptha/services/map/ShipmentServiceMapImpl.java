package com.colptha.services.map;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.converters.ShipmentConverter;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.entities.Shipment;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Colptha on 4/4/17.
 */
@Service
@Profile("map")
public class ShipmentServiceMapImpl implements ShipmentService {

    private Map<Integer, Shipment> shipmentMap = new HashMap<>();
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

    @Override
    public Map<Integer, ShipmentForm> listAll() {
        Map<Integer, ShipmentForm> shipmentFormMap = new HashMap<>();

        shipmentMap.forEach((integer, shipment) -> shipmentFormMap.put(integer, shipmentConverter.convert(shipment)));

        return shipmentFormMap;
    }

    @Override
    public ShipmentForm findOne(Integer shipmentId) throws NoSuchElementException {
        Optional<Shipment> shipment = Optional.ofNullable(shipmentMap.get(shipmentId));
        return shipment.map(shipment1 -> shipmentConverter.convert(shipment1)).orElse(null);
    }

    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) {
        Shipment shipment = shipmentConverter.convert(shipmentForm);
        ShipmentType shipmentType = shipment.getShipmentType();

        // assign incoming product lot information to new product lots so they are not
        // pointing at the same object in memory if this is an update
        Set<ProductLot> currentLots = new HashSet<>();
        shipment.getProductLots().forEach(lot -> {
            ProductLot productLot = new ProductLot();
            productLot.setProductId(lot.getProductId());
            productLot.setQuantity(lot.getQuantity());
            currentLots.add(productLot);
        });
        shipment.setProductLots(currentLots);

        Optional<Integer> shipmentId = Optional.ofNullable(shipment.getShipmentId());
        Shipment priorShipment = null;
        ShipmentType priorShipmentType = null;
        if (shipmentId.isPresent()) {
            priorShipment = shipmentMap.get(shipmentId.get());
            priorShipmentType = priorShipment.getShipmentType();
        }

        final boolean ADJUSTMENT_NEEDED =
                (priorShipmentType == null && shipmentType == ShipmentType.OUTBOUND) ||
                (priorShipmentType == ShipmentType.OUTBOUND && shipmentType == ShipmentType.INBOUND) ||
                (priorShipmentType == ShipmentType.INBOUND && shipmentType == ShipmentType.OUTBOUND);

        //SCENARIOS
        // 1. new inbound           comes in positive goes out positive - DONT ADJUST
        // 2. new outbound          comes in positive goes out negative - ADJUST
        // 3. outbound -> inbound   comes in negative goes out positive - ADJUST but ShipmentType.INBOUND won't change it
        // 4. inbound -> outbound   comes in positive goes out negative - ADJUST
        // 5. outbound -> outbound  comes in negative goes out negative - DONT ADJUST
        // 6. inbound -> inbound    comes in positive goes out positive - DONT ADJUST

        if (ADJUSTMENT_NEEDED) {
            currentLots.forEach(lot -> lot.setQuantity(lot.getQuantity() * shipmentType.changeInventoryDirection()));
        }

        if (shipmentId.isPresent()) {
            shipment.setCreatedOn(priorShipment.getCreatedOn());
            Set<ProductLot> priorLots = priorShipment.getProductLots();

            List<ProductId> lotsToRemoveByProductId = determineLotsToRemove(currentLots, priorLots);
            removeDeletedLots(lotsToRemoveByProductId, priorLots, productService);
            updateProductInventoryOnModifiedLots(currentLots, priorLots, productService);
        } else {
            shipment.setShipmentId(getNextId());
            updateProductInventoryOnNewShipment(currentLots, productService);
        }

        shipment.updateTimeStamps();
        shipmentMap.put(shipment.getShipmentId(), shipment);
        return shipmentConverter.convert(shipment);
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentMap.remove(shipmentId);
    }

    private Integer getNextId() {

        if (shipmentMap.isEmpty()) {
            return 1;
        }

        Integer largest = Collections.max(shipmentMap.keySet());
        return largest + 1;
    }


}
