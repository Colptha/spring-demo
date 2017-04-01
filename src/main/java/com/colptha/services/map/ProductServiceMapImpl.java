package com.colptha.services.map;

import com.colptha.dom.command.ProductForm;
import com.colptha.dom.converters.ProductConverter;
import com.colptha.dom.entities.Product;
import com.colptha.dom.enums.ProductId;
import com.colptha.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/1/17.
 */
@Service
@Profile("map")
public class ProductServiceMapImpl implements ProductService {

    private Map<ProductId, Product> products = new HashMap<>();
    private ProductConverter productConverter;

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Override
    public Map<ProductId, ProductForm> listAll() {
        Map<ProductId, ProductForm> productFormMap = new HashMap<>();
        products.forEach((productId, product) -> productFormMap.put(productId, productConverter.convert(product)));
        return productFormMap;
    }

    @Override
    public ProductForm findOne(ProductId query) throws NoSuchElementException {
        return productConverter.convert(products.get(query));
    }

    @Override
    public ProductForm saveOrUpdate(ProductForm productForm) {
        // dates will be trashed during conversion (for consistency of created on dates)
        Product product = productConverter.convert(productForm);
        ProductId productId = product.getProductId();

        // check for and add creation date
        if (products.containsKey(productId)) {
            product.setCreatedOn(products.get(productId).getCreatedOn());
        }

        // normally Hibernate would update time stamps
        product.updateTimeStamps();
        products.put(productId, product);

        // send back with valid timestamps
        return productConverter.convert(product);
    }

    @Override
    public ProductForm delete(ProductId query) {
        return productConverter.convert(products.remove(query));
    }
}
