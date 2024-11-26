package com.fiap.burguer.driver.dto;

import com.fiap.burguer.core.application.enums.CategoryProduct;

public class ProductCreate {
    private String name;
    private CategoryProduct category;
    private double price;
    private String description;
    private Integer preparationTime;
    private String image;

    public ProductCreate(String name, String image, Integer preparationTime, String description, double price, CategoryProduct category) {
        this.name = name;
        this.image = image;
        this.preparationTime = preparationTime;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryProduct getCategory() {
        return category;
    }

    public void setCategory(CategoryProduct category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
