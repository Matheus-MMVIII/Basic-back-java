package com.basic.model;

public class Product {
    private final String name;
    private final double price;
    private final Category category;

    public Product(String name, double price, Category category) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Product name cannot be null or empty");
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        if (category == null)
            throw new IllegalArgumentException("Category cannot be null");
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }
}
