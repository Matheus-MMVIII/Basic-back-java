package com.basic.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.basic.exception.NotFoundException;
import com.basic.http.util.JsonUtil;
import com.basic.model.Product;
import com.basic.repository.ProductRepository;
import com.basic.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

class ProductServiceTests {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
    }

    @Test
    void productsTests() throws SQLException {
        String id = mustCreateProduct();

        mustUpdateProduct(mustFindedById(id));
        mustDeleteProduct(id);

    }

    @Test
    void mustSpecificListProducts() throws SQLException {

        Random rand = new Random();

        int limit = rand.nextInt(1, 10);

        String[] ids = new String[limit];

        for (int i = 0; i < limit; i++) {
            ids[i] = mustCreateProduct();
        }

        assertNotNull(productService.listPage("aaa", limit));

        for (int i = 0; i < limit; i++) {
            mustDeleteProduct(ids[i]);
        }

    }

    String mustCreateProduct() throws SQLException {
        Map<String, String> payload = JsonUtil.parseFlatObject("{\"name\":\"TESTE\",\"price\":0.0,\"category\":\"Food\",\"stock\":0}");

        Product product = productService.create(payload);

        assertNotNull(product);

        assertEquals("TESTE", product.getName());
        assertEquals(0.0d, product.getPrice());
        assertEquals("Food", product.getCategory());
        assertEquals(0, product.getStock());

        return product.getId();
    }

    Product mustFindedById(String id) throws SQLException {
        Product product = productService.findById(id);

        assertNotNull(product);

        assertEquals(id, product.getId());

        return product;
    }

    void mustUpdateProduct(Product oldProduct) throws SQLException {
        Map<String, String> payload = JsonUtil.parseFlatObject("{\"name\":\"TESTANDO\",\"price\":10.0,\"category\":\"Food\",\"stock\":10}");

        assertEquals("TESTE", oldProduct.getName());
        assertEquals(0.0d, oldProduct.getPrice());
        assertEquals("Food", oldProduct.getCategory());
        assertEquals(0, oldProduct.getStock());

        Product product = productService.update(oldProduct.getId(), payload);

        assertNotNull(product);

        assertEquals(oldProduct.getId(), product.getId());
        assertEquals("TESTANDO", product.getName());
        assertEquals(10.0d, product.getPrice());
        assertEquals("Food", product.getCategory());
        assertEquals(10, product.getStock());
    }

    void mustDeleteProduct(String id) throws SQLException {
        productService.delete(id);
        assertThrows(NotFoundException.class, () -> { productService.findById(id); });
    }

}