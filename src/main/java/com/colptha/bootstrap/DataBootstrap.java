package com.colptha.bootstrap;

import com.colptha.dom.command.EmployeeForm;
import com.colptha.dom.command.ProductForm;
import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ProductId;
import com.colptha.services.EmployeeService;
import com.colptha.services.ProductService;
import com.colptha.services.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        product1.setProductPrice(new BigDecimal(".89"));
        ProductForm product2 = new ProductForm();
        product2.setProductId(ProductId.A2);
        product2.setProductName("Bananas");
        product2.setProductPrice(new BigDecimal(".69"));
        ProductForm product3 = new ProductForm();
        product3.setProductId(ProductId.A3);
        product3.setProductName("Bell Peppers");
        product3.setProductPrice(new BigDecimal("1.09"));
        ProductForm product4 = new ProductForm();
        product4.setProductId(ProductId.A4);
        product4.setProductName("Broccoli");
        product4.setProductPrice(new BigDecimal(".99"));
        ProductForm product5 = new ProductForm();
        product5.setProductId(ProductId.A5);
        product5.setProductName("Cauliflower");
        product5.setProductPrice(new BigDecimal("1.29"));
        ProductForm product6 = new ProductForm();
        product6.setProductId(ProductId.B1);
        product6.setProductName("Quinoa");
        product6.setProductPrice(new BigDecimal("1.69"));
        ProductForm product7 = new ProductForm();
        product7.setProductId(ProductId.B2);
        product7.setProductName("Wheat");
        product7.setProductPrice(new BigDecimal(".85"));
        ProductForm product8 = new ProductForm();
        product8.setProductId(ProductId.B3);
        product8.setProductName("Oats");
        product8.setProductPrice(new BigDecimal(".79"));
        ProductForm product9 = new ProductForm();
        product9.setProductId(ProductId.B4);
        product9.setProductName("Rice");
        product9.setProductPrice(new BigDecimal(".69"));
        ProductForm product10 = new ProductForm();
        product10.setProductId(ProductId.B5);
        product10.setProductName("Rye");
        product10.setProductPrice(new BigDecimal("1.39"));
        ProductForm product11 = new ProductForm();
        product11.setProductId(ProductId.C1);
        product11.setProductName("Coffee");
        product11.setProductPrice(new BigDecimal("1.09"));
        ProductForm product12 = new ProductForm();
        product12.setProductId(ProductId.C2);
        product12.setProductName("Milk");
        product12.setProductPrice(new BigDecimal("1.69"));
        ProductForm product13 = new ProductForm();
        product13.setProductId(ProductId.C3);
        product13.setProductName("Soy Milk");
        product13.setProductPrice(new BigDecimal("1.99"));
        ProductForm product14 = new ProductForm();
        product14.setProductId(ProductId.C4);
        product14.setProductName("Orange Juice");
        product14.setProductPrice(new BigDecimal(".99"));
        ProductForm product15 = new ProductForm();
        product15.setProductId(ProductId.C5);
        product15.setProductName("Green Smoothie");
        product15.setProductPrice(new BigDecimal("2.29"));
        ProductForm product16 = new ProductForm();
        product16.setProductId(ProductId.D1);
        product16.setProductName("Pork");
        product16.setProductPrice(new BigDecimal("1.89"));
        ProductForm product17 = new ProductForm();
        product17.setProductId(ProductId.D2);
        product17.setProductName("Chicken");
        product17.setProductPrice(new BigDecimal("1.19"));
        ProductForm product18 = new ProductForm();
        product18.setProductId(ProductId.D3);
        product18.setProductName("Beef");
        product18.setProductPrice(new BigDecimal("3.29"));
        ProductForm product19 = new ProductForm();
        product19.setProductId(ProductId.D4);
        product19.setProductName("Veal");
        product19.setProductPrice(new BigDecimal("4.89"));
        ProductForm product20 = new ProductForm();
        product20.setProductId(ProductId.D5);
        product20.setProductName("Venison");
        product20.setProductPrice(new BigDecimal("5.25"));

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
        ProductLot productLot1 = new ProductLot();
        productLot1.setProductId(ProductId.A1);
        productLot1.setQuantity(53);

        ProductLot productLot2 = new ProductLot();
        productLot2.setProductId(ProductId.B3);
        productLot2.setQuantity(25);

        ShipmentForm shipment1 = new ShipmentForm();
        Set<ProductLot> productLotSet = new HashSet<>();
        productLotSet.add(productLot1);
        productLotSet.add(productLot2);

        shipment1.setProductLots(productLotSet);
        shipmentService.saveOrUpdate(shipment1);
        System.out.println("################# load shipments");

        ProductId productId = ProductId.A1;
        Integer amountAdded = 100;

        ProductLot productLot = new ProductLot();
        productLot.setProductId(productId);
        productLot.setQuantity(amountAdded);

        ShipmentForm shipmentForm = new ShipmentForm();
        shipmentForm.getProductLots().add(productLot);
        shipmentService.saveOrUpdate(shipmentForm);
    }
}
