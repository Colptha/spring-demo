package com.colptha.dom.command;

import com.colptha.dom.enums.ProductId;

/**
 * Created by Colptha on 4/1/17.
 */
public class ProductForm extends AbstractCommandObject {

    private ProductId productId;
    private String productName;
    private Integer productInventory;

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
}
