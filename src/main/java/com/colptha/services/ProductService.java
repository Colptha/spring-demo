package com.colptha.services;

import com.colptha.dom.command.ProductForm;
import com.colptha.dom.enums.ProductId;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/1/17.
 */
public interface ProductService extends CRUDService<ProductForm, ProductId> {
}
