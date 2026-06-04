package com.example.garage;

public class Product {
    private String name;
    private String description;

    // ĐỔI TỪ int imageResId SANG String imageUrl
    private String imageUrl;
    private String searchQuery;

    // Cập nhật constructor
    public Product(String name, String description, String imageUrl, String searchQuery) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl; // URL thay vì Resource ID
        this.searchQuery = searchQuery;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    // Cập nhật getter
    public String getImageUrl() { return imageUrl; }
    public String getSearchQuery() { return searchQuery; }
}