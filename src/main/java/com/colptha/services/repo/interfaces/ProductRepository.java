package com.colptha.services.repo.interfaces;

import com.colptha.dom.entities.Product;
import com.colptha.dom.enums.ProductId;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Colptha on 4/4/17.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findByProductId(ProductId productId);
}
