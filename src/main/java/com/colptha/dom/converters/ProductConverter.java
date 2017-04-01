package com.colptha.dom.converters;

import com.colptha.dom.command.ProductForm;
import com.colptha.dom.entities.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by Colptha on 4/1/17.
 */
@Component
public class ProductConverter implements Converter<Product, ProductForm> {
    @Override
    public ProductForm convert(Product product) {
        ProductForm productForm = new ProductForm();
        productForm.setUpdatedOn(product.getUpdatedOn());
        productForm.setCreatedOn(product.getCreatedOn());
        productForm.setProductId(product.getProductId());
        productForm.setProductName(product.getProductName());
        productForm.setProductPrice(product.getProductPrice());
        productForm.setQuantityInStock(product.getQuantityInStock());

        return productForm;
    }

    public Product convert(ProductForm productForm) {
        Product product = new Product();
        // dates will be set on the way into the database
        product.setProductId(productForm.getProductId());
        product.setProductName(productForm.getProductName());
        product.setProductPrice(productForm.getProductPrice());
        product.setQuantityInStock(productForm.getQuantityInStock());

        return product;
    }
}
