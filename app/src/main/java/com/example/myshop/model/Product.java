package com.example.myshop.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Product {
    @PrimaryKey
    @NonNull
    String id;
    String name;
    String imageUrl;
    double price;
    double rewPrice;
    int quantity;

    public Product(){
        this.id = UUID.randomUUID().toString();
    }


// gharar dadan e getter va setter :
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRewPrice() {
        return rewPrice;
    }

    public void setRewPrice(double rewPrice) {
        this.rewPrice = rewPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
