package com.example.myshop.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.myshop.R;
import com.example.myshop.adapter.BasketAdapter;
import com.example.myshop.database.ShopDatabase;
import com.example.myshop.listener.OnAdapterUpdate;
import com.example.myshop.model.Product;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class BasketActivity extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        //........
        ShopDatabase database = ShopDatabase.getInstance(this);
        Executor executor = Executors.newSingleThreadExecutor();
        RecyclerView recyclerViewBasket = findViewById(R.id.recyclerViewBasket);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Product> productList = database.productDao().getAllProduct();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        BasketAdapter basketAdapter = new BasketAdapter(productList, getApplicationContext());
                        basketAdapter.setonAdapterUpdate(new OnAdapterUpdate() {
                            @Override
                            public void onAdapterUpdate() {
                                basketAdapter.notifyDataSetChanged();
                            }
                        });
                        recyclerViewBasket.setAdapter(basketAdapter);
                        recyclerViewBasket.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
            }
        });

    }
}