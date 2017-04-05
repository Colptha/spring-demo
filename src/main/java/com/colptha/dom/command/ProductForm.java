package com.colptha.dom.command;

import com.colptha.dom.enums.ProductId;

import java.math.BigDecimal;

/**
 * Created by Colptha on 4/1/17.
 */
public class ProductForm extends AbstractCommandObject {

    private ProductId productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer inventory;

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
}
