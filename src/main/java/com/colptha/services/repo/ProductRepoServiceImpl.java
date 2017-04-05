package com.colptha.services.repo;

import com.colptha.dom.command.ProductForm;
import com.colptha.dom.converters.ProductConverter;
import com.colptha.dom.entities.Product;
import com.colptha.dom.enums.ProductId;
import com.colptha.services.ProductService;
import com.colptha.services.repo.interfaces.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by Colptha on 4/4/17.
 */
@Service
@Profile({"repo", "default"})
public class ProductRepoServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Autowired
    public void setProductConverter(ProductConverter productConverter) {
        this.productConverter = productConverter;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Map<ProductId, ProductForm> listAll() {
        Map<ProductId, ProductForm> productFormMap = new HashMap<>();

        productRepository.findAll().forEach(product -> {
            productFormMap.put(product.getProductId(), productConverter.convert(product));
        });

        return  productFormMap;
    }

    @Override
    public ProductForm findOne(ProductId query) throws NoSuchElementException {
        return productConverter.convert(productRepository.findByProductId(query));
    }

    @Override
    public ProductForm saveOrUpdate(ProductForm productForm) {
        return productConverter.convert(productRepository.save(productConverter.convert(productForm)));
    }

    @Override
    public void updateInventory(ProductId productId, Integer quantity) throws Exception {
        Product product = productRepository.findByProductId(productId);
        product.adjustInventory(quantity);
        saveOrUpdate(productConverter.convert(product));
    }

    @Override
    public void delete(ProductId query) {
        productRepository.delete(productRepository.findByProductId(query));
    }
}
