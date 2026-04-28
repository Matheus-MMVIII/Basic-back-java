package com.basic.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.basic.config.DatabaseConfig;
import com.basic.exception.ConflictException;
import com.basic.exception.NotFoundException;
import com.basic.model.Product;
import com.basic.repository.ProductRepository;
import com.basic.validation.RequestValidator;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> listAll() throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            return productRepository.listAll(connection);
        }
    }

    public Product findById(int id) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            return productRepository.findById(connection, id)
                    .orElseThrow(() -> new NotFoundException("Product not found by ID."));
        }
    }

    public Product create(Map<String, String> payload) throws SQLException {
        String name = RequestValidator.requireText(payload, "name", 2, 100);
        double price = RequestValidator.requireDecimal(payload, "price", 0.0, 1000000.0);
        String category = RequestValidator.requireText(payload, "category", 2, 100);
        int stock = RequestValidator.requireInt(payload, "stock", 0, 1000000);

        try (Connection connection = DatabaseConfig.getConnection()) {
            return productRepository.insert(connection, new Product(0, name, price, category, stock));
        }
    }

    public Product update(int id, Map<String, String> payload) throws SQLException {
        String name = RequestValidator.requireText(payload, "name", 2, 100);
        double price = RequestValidator.requireDecimal(payload, "price", 0.0, 1000000.0);
        String category = RequestValidator.requireText(payload, "category", 2, 100);
        int stock = RequestValidator.requireInt(payload, "stock", 0, 1000000);


        try (Connection connection = DatabaseConfig.getConnection()) {
            if (productRepository.findById(connection, id).isEmpty()) {
                throw new NotFoundException("Product not found by ID to be update. ");
            }
            return productRepository.update(connection, new Product(id, name, price, category, stock));
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnection()) {
            boolean deleted = productRepository.delete(connection, id);
            if (!deleted) {
                throw new NotFoundException("Product not found by ID to be deleted. ");
            }
        }
    }
}