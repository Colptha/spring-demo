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
    public List<ProductLot> listByProductId(ProductId productId) {
        List<ProductLot> productLotList = new ArrayList<>();
        shipmentMap.forEach((integer, shipment) -> {
            shipment.getProductLots().forEach(productLot -> {
                if (productLot.getProductId().equals(productId)) {
                    productLotList.add(productLot);
                }
            });
        });

        return productLotList;
    }

    @Override
    public ShipmentForm findOne(Integer shipmentId) throws NoSuchElementException {
        Optional<Shipment> shipment = Optional.ofNullable(shipmentMap.get(shipmentId));
        return shipment.map(shipment1 -> shipmentConverter.convert(shipment1)).orElse(null);
    }

    @Override
    public ShipmentForm saveOrUpdate(ShipmentForm shipmentForm) {
        Shipment currentShipment = shipmentConverter.convert(shipmentForm);

        /*
        assign incoming product lot information to new product lot objects so they are not
        pointing at the same object in memory if this is an update
        not relevant to database implementations
        */
        Set<ProductLot> currentLots = new HashSet<>(); // start of map object reference fix
        currentShipment.getProductLots().forEach(lot -> {
            ProductLot productLot = new ProductLot();
            productLot.setProductId(lot.getProductId());
            productLot.setQuantity(lot.getQuantity());
            currentLots.add(productLot);
        });
        currentShipment.setProductLots(currentLots); // end of map object reference fix

        Optional<Integer> shipmentId = Optional.ofNullable(currentShipment.getShipmentId());
        ShipmentForm priorShipment = null;

        if (shipmentId.isPresent()) {
            priorShipment = shipmentConverter.convert(shipmentMap.get(shipmentId.get()));
        }

        processShipmentProductLots(shipmentId, currentShipment, priorShipment, currentLots, productService);

        currentShipment.updateTimeStamps(); // map specific
        shipmentMap.put(currentShipment.getShipmentId(), currentShipment); // map specific

        return shipmentConverter.convert(currentShipment);
    }

    @Override
    public void delete(Integer shipmentId) {
        shipmentMap.remove(shipmentId);
    }

}
