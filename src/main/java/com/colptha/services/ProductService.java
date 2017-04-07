package com.colptha.services;

import com.colptha.dom.command.ProductForm;
import com.colptha.dom.entities.Product;
import com.colptha.dom.enums.ProductId;


/**
 * Created by Colptha on 4/1/17.
 */
public interface ProductService extends CRUDService<ProductForm, ProductId> {
    void updateInventory(ProductId productId, Integer quantity) throws Exception;
    ProductForm saveOrUpdate(Product product);
}
