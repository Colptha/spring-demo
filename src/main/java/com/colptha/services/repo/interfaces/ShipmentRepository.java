package com.colptha.services.repo.interfaces;

import com.colptha.dom.entities.Shipment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Colptha on 4/4/17.
 */
public interface ShipmentRepository extends CrudRepository<Shipment, Integer> {
    Shipment findByShipmentId(Integer shipmentId);

}
