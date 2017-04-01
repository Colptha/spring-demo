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
    private Integer quantityInStock;

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

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
