package com.p.assignmentp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void addProduct(ProductData productData);

    @Query("SELECT * FROM product_data")
    List<ProductData> getProduct();

    @Query("SELECT * FROM product_data WHERE category == :category")
    public List<ProductData> getCategoryWiseProduct(String category);

    @Query("SELECT * FROM product_data WHERE price >= :minRange AND price <= :maxRange")
    public List<ProductData> getRangeProduct(int minRange,int maxRange);

    @Query("SELECT * FROM product_data WHERE price >= :minRange AND price <= :maxRange and category == :category")
    public List<ProductData> getRangeProduct(int minRange,int maxRange,String category);


}
