package com.basic.model;

public class Product {
    private final int id;
    private final String name;
    private final double price;
    private final Category category;
    private final int stock;

    public Product(int id, String name, double price, String category, int stock) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be null or empty");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");
        if (stock < 0)
            throw new IllegalArgumentException("Stock cannot be negative");
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = Category.valueOf(category);
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category.name();
    }

    public int getStock() {
        return stock;
    }
}
