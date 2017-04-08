package com.colptha.services.repo;

import com.colptha.dom.command.ProductForm;
import com.colptha.dom.enums.ProductId;
import com.colptha.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by Colptha on 4/7/17.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("repo")
public class ProductServiceRepoImplTest {
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Test
    public void testProductService() throws Exception {
        assert productService != null;

    }

    @Test
    public void testListAll() throws Exception {

        assert !productService.listAll().isEmpty();
    }

    @Test
    public void testFindOne() throws Exception {
        ProductForm productForm = productService.findOne(ProductId.A1);
        assert productForm != null;
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        ProductForm productForm = productService.findOne(ProductId.A1);
        assert productForm != null;
        System.out.println("Version: " + productForm.getVersion());

        String newName = "New Name";
        Date createdOn = productForm.getCreatedOn();
        Date updatedOn = productForm.getUpdatedOn();
        productForm.setProductName(newName);

        Thread.sleep(500);

        ProductForm savedProductForm = productService.saveOrUpdate(productForm);
        System.out.println("Version: " + savedProductForm.getVersion());
        assert savedProductForm.getCreatedOn().equals(createdOn);
        assert !savedProductForm.getUpdatedOn().equals(updatedOn);
        assert savedProductForm.getProductName().equals(newName);

        assert savedProductForm.getVersion() == productForm.getVersion() + 1;
    }

    @Test
    public void testUpdateInventory() throws Exception {
        ProductId productId = ProductId.A1;
        ProductForm productForm = productService.findOne(productId);
        Integer inventory = productForm.getProductInventory();
        Integer addedInventory = 50;

        productService.updateInventory(productForm.getProductId(), addedInventory);

        ProductForm adjustedProductForm = productService.findOne(productId);

        assert adjustedProductForm.getProductInventory() == inventory + addedInventory;

    }

    @Test
    public void testDelete() throws Exception {

        Integer numberOfProducts = productService.listAll().size();

        productService.delete(ProductId.B1);

        assert productService.listAll().size() == numberOfProducts - 1;

    }
}
