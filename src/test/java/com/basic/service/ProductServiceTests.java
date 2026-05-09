package com.basic.service;

import com.basic.dto.PageResult;
import com.basic.http.util.JsonUtil;
import com.basic.model.Product;
import com.basic.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTests {

    ProductRepository productRepository;
    ProductService productService;

    @BeforeEach
    void setup() {
        productRepository = new ProductRepository();
        productService = new ProductService(productRepository);
    }

    @Test
    void mustListProducts() throws SQLException {

        List<Product> list = productService.listAll();

        assertNotNull(list);
    }

    @Test
    void mustSpecificListProducts() throws SQLException {

        Random rand = new Random();

        Long cursor = rand.nextLong(1,  10);

        int limit = (int)(cursor+10);

        PageResult<Product> listPage = productService.listPage(cursor, limit);

        assertNotNull(listPage);

        assertTrue(listPage.getData().size() >= 0);

        assertTrue(listPage.getData().size() <= (int)(limit-cursor));
    }

    @Test
    void mustCreateProduct() throws SQLException {
        Map<String, String> payload = JsonUtil.parseFlatObject("{\"name\":\"TESTE\",\"price\":0.0,\"category\":\"Food\",\"stock\":0}");

        Product product = productService.create(payload);

        assertNotNull(product);

        assertEquals("TESTE", product.getName());
        assertEquals(0.0d, product.getPrice());
        assertEquals("Food", product.getCategory());
        assertEquals(0, product.getStock());
    }

    @Test
    void mustFindById() throws SQLException {
        Product product = productService.findById(1);

        assertNotNull(product);
    }

    @Test
    void mustUpdateProduct() throws SQLException {
        Map<String, String> payload = JsonUtil.parseFlatObject("{\"name\":\"TESTE\",\"price\":0.0,\"category\":\"Food\",\"stock\":0}");

        Product product = productService.update(1, payload);

        assertNotNull(product);

        assertEquals("TESTE", product.getName());
        assertEquals(0.0d, product.getPrice());
        assertEquals("Food", product.getCategory());
        assertEquals(0, product.getStock());
    }

    @Test
    void mustDeleteProduct() throws SQLException {
        productService.delete(1);
    }

}