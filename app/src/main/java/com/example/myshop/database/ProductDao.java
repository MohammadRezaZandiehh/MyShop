package com.example.myshop.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myshop.model.Product;

import java.util.List;

@Dao
public interface ProductDao {


    @Query("select * from product")
    List<Product> getAllProduct();

    @Insert(onConflict = OnConflictStrategy.REPLACE)   // "jaygozin kardne" yek mahsool ba yek ID e yeksan b ajye "azafe karde an"
    void addProduct(Product product);

    @Delete
    void deleteProduct (Product product);

    @Update
    void updateProdoct (Product product);

    @Query("select sum(quantity) from product")
    int getSumQuantity();


}
