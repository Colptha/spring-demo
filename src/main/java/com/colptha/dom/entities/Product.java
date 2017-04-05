package com.colptha.dom.entities;

import com.colptha.dom.enums.ProductId;

import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * Created by Colptha on 4/1/17.
 */
@Entity
public class Product extends AbstractEntityObject {

    private ProductId productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer inventory = 0;

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

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public void adjustInventory(Integer quantity) throws Exception {
        if (this.getInventory() + quantity < 0) {
            throw new Exception("Inventory cannot be negative");
        }
        this.inventory += quantity;
    }
}
