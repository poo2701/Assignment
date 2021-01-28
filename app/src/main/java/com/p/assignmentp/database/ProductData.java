package com.p.assignmentp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_data")
public class ProductData {

    @PrimaryKey(autoGenerate = true)
    public long productId;

    String name;
    Double price;
    String productImage;
    String category;

    public ProductData( String name, Double price, String productImage, String category) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.productImage = productImage;
        this.category = category;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}

