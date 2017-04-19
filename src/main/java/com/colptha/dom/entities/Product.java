package com.colptha.dom.entities;

import com.colptha.dom.enums.ProductId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Version;

/**
 * Created by Colptha on 4/1/17.
 */
@Entity
public class Product extends AbstractEntityObject {

    @Enumerated(EnumType.STRING)
    private ProductId productId;
    private String productName;
    private Integer productInventory = 0;

    @Version
    private Integer version;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public void adjustInventory(Integer quantity) throws Exception {
        if (this.getProductInventory() + quantity < 0) {
            throw new Exception("Inventory cannot be negative");
        }
        this.productInventory += quantity;
    }
}
