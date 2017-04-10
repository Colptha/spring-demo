package com.colptha.bootstrap;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.command.ProductForm;
import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;
import com.colptha.services.EmployeeService;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Colptha on 3/31/17.
 */
@Component
public class DataBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private EmployeeService employeeService;
    private ProductService productService;
    private ShipmentService shipmentService;

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadEmployees();
        loadProducts();
        loadShipments();
    }

    private void loadEmployees() {
        EmployeeForm employee1 = new EmployeeForm();
        EmployeeForm employee2 = new EmployeeForm();
        EmployeeForm employee3 = new EmployeeForm();
        EmployeeForm employee4 = new EmployeeForm();
        EmployeeForm employee5 = new EmployeeForm();
        employee1.setEmployeeId("aa1");
        employee1.setFirstName("John");
        employee1.setLastName("Gill");
        employee2.setEmployeeId("aa2");
        employee2.setFirstName("Sandy");
        employee2.setLastName("Wilson");
        employee3.setEmployeeId("aa3");
        employee3.setFirstName("Martin");
        employee3.setLastName("Bucer");
        employee4.setEmployeeId("aa4");
        employee4.setFirstName("Bill");
        employee4.setLastName("Thompson");
        employee5.setEmployeeId("aa5");
        employee5.setFirstName("Jason");
        employee5.setLastName("Ruby");

        employeeService.saveOrUpdate(employee1);
        employeeService.saveOrUpdate(employee2);
        employeeService.saveOrUpdate(employee3);
        employeeService.saveOrUpdate(employee4);
        employeeService.saveOrUpdate(employee5);
        System.out.println("################# load employees");
    }

    private void loadProducts() {
        ProductForm product1 = new ProductForm();
        product1.setProductId(ProductId.A1);
        product1.setProductName("Apples");
        ProductForm product2 = new ProductForm();
        product2.setProductId(ProductId.A2);
        product2.setProductName("Bananas");
        ProductForm product3 = new ProductForm();
        product3.setProductId(ProductId.A3);
        product3.setProductName("Bell Peppers");
        ProductForm product4 = new ProductForm();
        product4.setProductId(ProductId.A4);
        product4.setProductName("Broccoli");
        ProductForm product5 = new ProductForm();
        product5.setProductId(ProductId.A5);
        product5.setProductName("Cauliflower");
        ProductForm product6 = new ProductForm();
        product6.setProductId(ProductId.B1);
        product6.setProductName("Quinoa");
        ProductForm product7 = new ProductForm();
        product7.setProductId(ProductId.B2);
        product7.setProductName("Wheat");
        ProductForm product8 = new ProductForm();
        product8.setProductId(ProductId.B3);
        product8.setProductName("Oats");
        ProductForm product9 = new ProductForm();
        product9.setProductId(ProductId.B4);
        product9.setProductName("Rice");
        ProductForm product10 = new ProductForm();
        product10.setProductId(ProductId.B5);
        product10.setProductName("Rye");
        ProductForm product11 = new ProductForm();
        product11.setProductId(ProductId.C1);
        product11.setProductName("Coffee");
        ProductForm product12 = new ProductForm();
        product12.setProductId(ProductId.C2);
        product12.setProductName("Milk");
        ProductForm product13 = new ProductForm();
        product13.setProductId(ProductId.C3);
        product13.setProductName("Soy Milk");
        ProductForm product14 = new ProductForm();
        product14.setProductId(ProductId.C4);
        product14.setProductName("Orange Juice");
        ProductForm product15 = new ProductForm();
        product15.setProductId(ProductId.C5);
        product15.setProductName("Green Smoothie");
        ProductForm product16 = new ProductForm();
        product16.setProductId(ProductId.D1);
        product16.setProductName("Pork");
        ProductForm product17 = new ProductForm();
        product17.setProductId(ProductId.D2);
        product17.setProductName("Chicken");
        ProductForm product18 = new ProductForm();
        product18.setProductId(ProductId.D3);
        product18.setProductName("Beef");
        ProductForm product19 = new ProductForm();
        product19.setProductId(ProductId.D4);
        product19.setProductName("Veal");
        ProductForm product20 = new ProductForm();
        product20.setProductId(ProductId.D5);
        product20.setProductName("Venison");

        productService.saveOrUpdate(product1);
        productService.saveOrUpdate(product2);
        productService.saveOrUpdate(product3);
        productService.saveOrUpdate(product4);
        productService.saveOrUpdate(product5);
        productService.saveOrUpdate(product6);
        productService.saveOrUpdate(product7);
        productService.saveOrUpdate(product8);
        productService.saveOrUpdate(product9);
        productService.saveOrUpdate(product10);
        productService.saveOrUpdate(product11);
        productService.saveOrUpdate(product12);
        productService.saveOrUpdate(product13);
        productService.saveOrUpdate(product14);
        productService.saveOrUpdate(product15);
        productService.saveOrUpdate(product16);
        productService.saveOrUpdate(product17);
        productService.saveOrUpdate(product18);
        productService.saveOrUpdate(product19);
        productService.saveOrUpdate(product20);
        System.out.println("################# load products");
    }

    private void loadShipments() {
        int quantity100 = 100;
        int quantity75 = 75;
        int quantity50 = 50;

        ProductLot productLot1 = new ProductLot();
        productLot1.setProductId(ProductId.A1);
        productLot1.setQuantity(quantity100);

        ProductLot productLot2 = new ProductLot();
        productLot2.setProductId(ProductId.A2);
        productLot2.setQuantity(quantity100);

        ProductLot productLot3 = new ProductLot();
        productLot3.setProductId(ProductId.A3);
        productLot3.setQuantity(quantity100);

        ProductLot productLot4 = new ProductLot();
        productLot4.setProductId(ProductId.A4);
        productLot4.setQuantity(quantity100);

        ProductLot productLot5 = new ProductLot();
        productLot5.setProductId(ProductId.A5);
        productLot5.setQuantity(quantity100);

        ProductLot productLot6 = new ProductLot();
        productLot6.setProductId(ProductId.B1);
        productLot6.setQuantity(quantity100);

        ProductLot productLot7 = new ProductLot();
        productLot7.setProductId(ProductId.B2);
        productLot7.setQuantity(quantity100);

        ProductLot productLot8 = new ProductLot();
        productLot8.setProductId(ProductId.B3);
        productLot8.setQuantity(quantity100);

        ProductLot productLot9 = new ProductLot();
        productLot9.setProductId(ProductId.B4);
        productLot9.setQuantity(quantity100);

        ProductLot productLot10 = new ProductLot();
        productLot10.setProductId(ProductId.B5);
        productLot10.setQuantity(quantity100);

        ProductLot productLot11 = new ProductLot();
        productLot11.setProductId(ProductId.C1);
        productLot11.setQuantity(quantity100);

        ProductLot productLot12 = new ProductLot();
        productLot12.setProductId(ProductId.C2);
        productLot12.setQuantity(quantity100);

        ProductLot productLot13 = new ProductLot();
        productLot13.setProductId(ProductId.C3);
        productLot13.setQuantity(quantity100);

        ProductLot productLot14 = new ProductLot();
        productLot14.setProductId(ProductId.C4);
        productLot14.setQuantity(quantity100);

        ProductLot productLot15 = new ProductLot();
        productLot15.setProductId(ProductId.C5);
        productLot15.setQuantity(quantity100);

        ProductLot productLot16 = new ProductLot();
        productLot16.setProductId(ProductId.D1);
        productLot16.setQuantity(quantity100);

        ProductLot productLot17 = new ProductLot();
        productLot17.setProductId(ProductId.D2);
        productLot17.setQuantity(quantity100);

        ProductLot productLot18 = new ProductLot();
        productLot18.setProductId(ProductId.D3);
        productLot18.setQuantity(quantity100);

        ProductLot productLot19 = new ProductLot();
        productLot19.setProductId(ProductId.D4);
        productLot19.setQuantity(quantity100);

        ProductLot productLot20 = new ProductLot();
        productLot20.setProductId(ProductId.D5);
        productLot20.setQuantity(quantity100);

        ProductLot productLot21 = new ProductLot();
        productLot21.setProductId(ProductId.A1);
        productLot21.setQuantity(quantity75);

        ProductLot productLot22 = new ProductLot();
        productLot22.setProductId(ProductId.A2);
        productLot22.setQuantity(quantity75);

        ProductLot productLot23 = new ProductLot();
        productLot23.setProductId(ProductId.A3);
        productLot23.setQuantity(quantity50);

        ProductLot productLot24 = new ProductLot();
        productLot24.setProductId(ProductId.A3);
        productLot24.setQuantity(quantity75);

        ProductLot productLot25 = new ProductLot();
        productLot25.setProductId(ProductId.A4);
        productLot25.setQuantity(quantity75);

        ProductLot productLot26 = new ProductLot();
        productLot26.setProductId(ProductId.B2);
        productLot26.setQuantity(quantity50);

        ProductLot productLot27 = new ProductLot();
        productLot27.setProductId(ProductId.B4);
        productLot27.setQuantity(quantity50);

        ProductLot productLot28 = new ProductLot();
        productLot28.setProductId(ProductId.C5);
        productLot28.setQuantity(quantity50);

        ProductLot productLot29 = new ProductLot();
        productLot29.setProductId(ProductId.D3);
        productLot29.setQuantity(quantity50);

        ProductLot productLot30 = new ProductLot();
        productLot30.setProductId(ProductId.D2);
        productLot30.setQuantity(quantity50);

        ProductLot productLot31 = new ProductLot();
        productLot31.setProductId(ProductId.D1);
        productLot31.setQuantity(quantity75);

        ProductLot productLot32 = new ProductLot();
        productLot32.setProductId(ProductId.A1);
        productLot32.setQuantity(quantity75);

        ProductLot productLot33 = new ProductLot();
        productLot33.setProductId(ProductId.A2);
        productLot33.setQuantity(quantity100);

        Set<ProductLot> productLotSet = new HashSet<>();
        productLotSet.add(productLot1);
        productLotSet.add(productLot2);
        productLotSet.add(productLot3);
        productLotSet.add(productLot4);
        productLotSet.add(productLot5);
        productLotSet.add(productLot6);
        productLotSet.add(productLot7);
        productLotSet.add(productLot8);
        productLotSet.add(productLot9);
        productLotSet.add(productLot10);
        productLotSet.add(productLot11);
        productLotSet.add(productLot12);
        productLotSet.add(productLot13);
        productLotSet.add(productLot14);
        productLotSet.add(productLot15);
        productLotSet.add(productLot16);
        productLotSet.add(productLot17);
        productLotSet.add(productLot18);
        productLotSet.add(productLot19);
        productLotSet.add(productLot20);

        ShipmentForm shipment1 = new ShipmentForm();
        shipment1.setShipmentType(ShipmentType.INBOUND);
        shipment1.setProductLots(productLotSet);

        ShipmentForm shipment2 = new ShipmentForm();
        shipment2.setShipmentType(ShipmentType.INBOUND);
        shipment2.getProductLots().add(productLot21);
        shipment2.getProductLots().add(productLot22);
        shipment2.getProductLots().add(productLot23);

        ShipmentForm shipment3 = new ShipmentForm();
        shipment3.getProductLots().add(productLot24);
        shipment3.getProductLots().add(productLot25);
        shipment3.getProductLots().add(productLot26);
        shipment3.getProductLots().add(productLot27);
        shipment3.getProductLots().add(productLot28);
        shipment3.getProductLots().add(productLot29);
        shipment3.getProductLots().add(productLot30);
        shipment3.setShipmentType(ShipmentType.INBOUND);

        ShipmentForm shipment4 = new ShipmentForm();
        shipment4.getProductLots().add(productLot31);
        shipment4.setShipmentType(ShipmentType.OUTBOUND);

        ShipmentForm shipment5 = new ShipmentForm();
        shipment5.getProductLots().add(productLot32);
        shipment5.setShipmentType(ShipmentType.OUTBOUND);

        ShipmentForm shipment6 = new ShipmentForm();
        shipment6.getProductLots().add(productLot33);
        shipment6.setShipmentType(ShipmentType.OUTBOUND);

        shipmentService.saveOrUpdate(shipment1);
        shipmentService.saveOrUpdate(shipment2);
        shipmentService.saveOrUpdate(shipment3);
        shipmentService.saveOrUpdate(shipment4);
        shipmentService.saveOrUpdate(shipment5);
        shipmentService.saveOrUpdate(shipment6);

        System.out.println("################# load shipments");
    }
}
