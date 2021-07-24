package com.example.myshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myshop.adapter.BestPriceAdapter;
import com.example.myshop.adapter.GlideImageLoadingService;
import com.example.myshop.adapter.MainProductAdapter;
import com.example.myshop.adapter.MainSlideAdapter;
import com.example.myshop.database.ShopDatabase;
import com.example.myshop.listener.OnAddProduct;
import com.example.myshop.model.Product;
import com.example.myshop.view.BasketActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class MainActivity extends AppCompatActivity {
    ShopDatabase database;
    Executor executor;
    CountAnimationTextView textViewCounter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////////////////////////////////////////// refrence ha va sdakhtane object ha :

        Slider.init(new GlideImageLoadingService(this));
        Slider slider = findViewById(R.id.banner_slider);
        slider.setAdapter(new MainSlideAdapter());

        executor = Executors.newSingleThreadExecutor();
        database = ShopDatabase.getInstance(this);
        RecyclerView recyclerViewMain = findViewById(R.id.recycelerViewMain);
        RecyclerView recycelerViewBestPrice = findViewById(R.id.recycelerViewBestPrice);
        textViewCounter = findViewById(R.id.count_animation_textView);

        //------------------

        updateCounter();

        OnAddProduct onAddProduct = new OnAddProduct() {
            @Override
            public void onPrductAdded() {
                updateCounter();
            }
        };
        recyclerViewMain.setAdapter(new MainProductAdapter(getDammyData(), this, onAddProduct));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        // Grid b ma komak mikone ta object hamon ro dar sotoon namayesh bedim .
        // spanCount b ma tedad e sotoon ro mige .
        recyclerViewMain.setLayoutManager(gridLayoutManager);

        MaterialToolbar materialToolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(materialToolbar);
//        database.productDao().addProduct();

        ImageView imageViewBasket = findViewById(R.id.imageViewBasket);
        imageViewBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BasketActivity.class);
                startActivity(intent);
            }
        });

        recycelerViewBestPrice.setAdapter(new BestPriceAdapter(getDammyDataBestPrice(), MainActivity.this));
        recycelerViewBestPrice.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

    }


    public List<Product> getDammyData() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();

        product1.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/2855984.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600/quality,q_80");
        product2.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/119562528.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600/quality,q_80");
        product3.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/1287370.jpg?x-oss-process=image/resize,m_lfit,h_600,w_600/quality,q_80");
        product4.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/140228.jpg?x-oss-process=image/resize,h_1600/quality,q_80");
        product5.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/1467042.jpg?x-oss-process=image/resize,h_1600/quality,q_80");

        product1.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product2.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product3.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product4.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product5.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");

        product1.setRewPrice(1230000);
        product2.setRewPrice(1230000);
        product3.setRewPrice(1230000);
        product4.setRewPrice(1230000);
        product5.setRewPrice(1230000);

        product1.setPrice(1230000);
        product2.setPrice(1230000);
        product3.setPrice(1230000);
        product4.setPrice(1230000);
        product5.setPrice(1230000);

        // ezafe krdne har yek az product haye sakhte shode dar khat haye bala b array ei k az jense 'product' b name "productList" sakhtim .
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);

        return productList;
    }

    public void updateCounter() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                int sum = database.productDao().getSumQuantity();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewCounter.setText(sum + "");
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCounter();
    }


    public List<Product> getDammyDataBestPrice() {
        List<Product> productList = new ArrayList<>();

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();
        Product product5 = new Product();

        product1.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/121485581.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60");
        product2.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/c056f666e23214b59b404f3cf938e303c1500758_1607497926.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60");
        product3.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/c8fea49327a60ea8ce0cadc63a2c830ece90e95f_1601117240.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60");
        product4.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/c064146971724e1f25f37a2dc00a42c4d3d36ccc_1608845687.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60");
        product5.setImageUrl("https://dkstatics-public.digikala.com/digikala-products/e19b78fcf2b83c3091a8e65b7cb042e7ede3e77c_1608879874.jpg?x-oss-process=image/resize,m_lfit,h_350,w_350/quality,q_60");

        product1.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product2.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product3.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product4.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");
        product5.setName("ساعت مچی مردانه کاسیو ادیفایس EF-550D-1AVDF");

        product1.setPrice(999000);
        product2.setPrice(100000);
        product3.setPrice(99000);
        product4.setPrice(3000000);
        product5.setPrice(12000);

        product1.setRewPrice(2300000);
        product2.setRewPrice(800000);
        product3.setRewPrice(4000000);
        product4.setRewPrice(60000000);
        product5.setRewPrice(2300000);


        // ezafe krdne har yek az product haye sakhte shode dar khat haye bala b array ei k az jense 'product' b name "productList" sakhtim .
        productList.add(product1);
        productList.add(product2);
        productList.add(product3);
        productList.add(product4);
        productList.add(product5);

        return productList;
    }
}