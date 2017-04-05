package com.colptha.services.repo;

import com.colptha.services.ShipmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Colptha on 4/5/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("repo")
public class ShipmentServiceRepoImplTest {
    // TODO add tests for shipment repo
    private ShipmentService shipmentService;

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Test
    public void testListAll() throws Exception {
        assert !shipmentService.listAll().isEmpty();
    }
}
