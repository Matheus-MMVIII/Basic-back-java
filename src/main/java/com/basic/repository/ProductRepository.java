package com.basic.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.basic.model.Product;

public class ProductRepository {
    public Product insert(Connection connection, Product product) throws SQLException {
        String sql = "INSERT INTO products (name, price, category, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getCategory());
            statement.setInt(4, product.getStock());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return new Product(
                            generatedKeys.getInt(1),
                            product.getName(),
                            product.getPrice(),
                            product.getCategory(),
                            product.getStock());
                }
            }
        }
        throw new SQLException("Failure to generate product identifier. ");
    }

    public Optional<Product> findById(Connection connection, int productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    public Optional<Product> findByIdForUpdate(Connection connection, int productId) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ? FOR UPDATE";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapRow(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    public Product update(Connection connection, Product product) throws SQLException {
        String sql = "UPDATE product SET name = ?, price = ?, stock = ?, size = ?, color = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setString(3, product.getCategory());
            statement.setInt(4, product.getStock());
            statement.executeUpdate();
        }
        return product;
    }

    public boolean delete(Connection connection, int productId) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, productId);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Product> listAll(Connection connection) throws SQLException {
        String sql = "SELECT * FROM product ORDER BY id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<Product> productItems = new ArrayList<>();
                while (resultSet.next()) {
                    productItems.add(mapRow(resultSet));
                }
                return productItems;
            }
        }
    }

    private Product mapRow(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price"),
                resultSet.getString("Category"),
                resultSet.getInt("stock"));
    }
}