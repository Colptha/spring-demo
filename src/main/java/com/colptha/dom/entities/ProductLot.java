package com.colptha.dom.entities;

import com.colptha.dom.enums.ProductId;

import javax.persistence.*;

/**
 * Created by Colptha on 4/4/17.
 */
@Embeddable
public class ProductLot {

    private Integer quantity;
    private ProductId productId;


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public void setProductId(ProductId productId) {
        this.productId = productId;
    }

}
