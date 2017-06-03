package com.colptha.bootstrap;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.command.ProductForm;
import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.Address;
import com.colptha.dom.entities.exceptions.NegativeInventoryException;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.enums.ShipmentType;
import com.colptha.services.EmployeeService;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

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
        Address address1 = new Address();
        address1.setLine1("100 Main Street");
        address1.setLine2("Apt C");
        address1.setCity("St. Louis");
        address1.setState("MO");
        address1.setZipcode("63101");

        Address address2 = new Address();
        address2.setLine1("32 Kantian Road");
        address2.setCity("St. Louis");
        address2.setState("MO");
        address2.setZipcode("63102");

        Address address3 = new Address();
        address3.setLine1("7892 Apriori Lane");
        address3.setLine2("Apt 12");
        address3.setCity("Collinsville");
        address3.setState("IL");
        address3.setZipcode("62234");

        Address address4 = new Address();
        address4.setLine1("125 Beltline Road");
        address4.setCity("Belleville");
        address4.setState("IL");
        address4.setZipcode("62221");

        Address address5 = new Address();
        address5.setLine1("3410 Descartes Street");
        address5.setLine2("Apt 7");
        address5.setCity("St. Louis");
        address5.setState("MO");
        address5.setZipcode("63101");

        EmployeeForm employee1 = new EmployeeForm();
        EmployeeForm employee2 = new EmployeeForm();
        EmployeeForm employee3 = new EmployeeForm();
        EmployeeForm employee4 = new EmployeeForm();
        EmployeeForm employee5 = new EmployeeForm();

        employee1.setEmployeeId("a001");
        employee1.setFirstName("John");
        employee1.setLastName("Gill");
        employee1.setAddress(address1);
        employee1.setPassword("password");
        employee1.setRole("ADMIN");

        employee2.setEmployeeId("a002");
        employee2.setFirstName("Sandy");
        employee2.setLastName("Wilson");
        employee2.setAddress(address2);
        employee2.setPassword("password");
        employee2.setRole("MANAGER");

        employee3.setEmployeeId("a003");
        employee3.setFirstName("Martin");
        employee3.setLastName("Bucer");
        employee3.setAddress(address3);
        employee3.setPassword("password");
        employee3.setRole("USER");

        employee4.setEmployeeId("b001");
        employee4.setFirstName("Bill");
        employee4.setLastName("Thompson");
        employee4.setAddress(address4);
        employee4.setPassword("password");
        employee4.setRole("USER");

        employee5.setEmployeeId("b002");
        employee5.setFirstName("Jason");
        employee5.setLastName("Ruby");
        employee5.setAddress(address5);
        employee5.setPassword("password");
        employee5.setRole("USER");


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
        int quantity1000 = 1000;
        int quantity750 = 750;
        int quantity500 = 500;
        int quantity375 = 375;


        ShipmentForm shipment1 = new ShipmentForm();
        shipment1.setShipmentType(ShipmentType.INBOUND);
        shipment1.getPossibleProductLots().forEach(productLot -> productLot.setQuantity(quantity1000));


        ShipmentForm shipment2 = new ShipmentForm();
        shipment2.setShipmentType(ShipmentType.INBOUND);
        shipment2.getPossibleProductLots().forEach(productLot -> productLot.setQuantity(quantity750));


        ShipmentForm shipment3 = new ShipmentForm();
        shipment3.setShipmentType(ShipmentType.INBOUND);
        shipment3.getPossibleProductLots().forEach(productLot -> productLot.setQuantity(quantity500));

        ShipmentForm shipment4 = new ShipmentForm();
        shipment4.setShipmentType(ShipmentType.OUTBOUND);
        shipment4.getPossibleProductLots().forEach(productLot -> productLot.setQuantity(quantity375));

        ShipmentForm shipment5 = new ShipmentForm();
        shipment5.setShipmentType(ShipmentType.OUTBOUND);
        shipment5.getPossibleProductLots().forEach(productLot -> productLot.setQuantity(quantity375));

        ShipmentForm shipment6 = new ShipmentForm();
        shipment6.setShipmentType(ShipmentType.OUTBOUND);
        shipment6.getPossibleProductLots().get(10).setQuantity(quantity750);

        try {
            shipmentService.saveOrUpdate(shipment1);
            shipmentService.saveOrUpdate(shipment2);
            shipmentService.saveOrUpdate(shipment3);
            shipmentService.saveOrUpdate(shipment4);
            shipmentService.saveOrUpdate(shipment5);
            shipmentService.saveOrUpdate(shipment6);
        } catch (NegativeInventoryException e) {
            e.printStackTrace();
        }


        System.out.println("################# load shipments");
    }
}
