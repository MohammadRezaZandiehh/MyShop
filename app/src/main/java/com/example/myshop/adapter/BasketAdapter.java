package com.example.myshop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myshop.R;
import com.example.myshop.database.ShopDatabase;
import com.example.myshop.listener.OnAdapterUpdate;
import com.example.myshop.model.Product;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder> {
    List<Product> productList;
    Context context;
    ShopDatabase database;
    Executor executor = Executors.newSingleThreadExecutor();
    OnAdapterUpdate onAdapterUpdate;

    public BasketAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.database = ShopDatabase.getInstance(context);
    }

    public void setonAdapterUpdate(OnAdapterUpdate onadapterUpdate) {
        this.onAdapterUpdate = onadapterUpdate;
    }

    @NonNull
    @Override
    public BasketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_basket, parent, false);

        return new BasketViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BasketViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.imageViewProduct);

        holder.textViewProductName.setText(product.getName() + "");
        holder.textViewProductPrice.setText(product.getPrice() + "");
        holder.textViewProductRawPrice.setText(product.getRewPrice() + "");
        holder.textViewProductQuantity.setText(product.getQuantity() + "");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class BasketViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;
        ImageView buttonIncQuantoty;
        ImageView buttonDecQuantoty;
        ImageView imageViewDelete;

        TextView textViewProductPrice;
        TextView textViewProductRawPrice;
        TextView textViewProductName;
        TextView textViewProductQuantity;

        public BasketViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductRawPrice = itemView.findViewById(R.id.textViewProductRawPrice);
            textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);

            buttonIncQuantoty = itemView.findViewById(R.id.imageViewAdd);
            buttonDecQuantoty = itemView.findViewById(R.id.imageViewMines);

            // ListenerS :

            buttonIncQuantoty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    incToProductQuantity(productList.get(getAdapterPosition()));
                }
            });


            buttonDecQuantoty.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dicToProductQuantity(productList.get(getAdapterPosition()));
                }
            });

            imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteProduct(productList.get(getAdapterPosition()));
                }
            });
        }
    }


    // methods :
    public void incToProductQuantity(Product product) {
        product.setQuantity(product.getQuantity() + 1);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.productDao().addProduct(product);
            }
        });
        onAdapterUpdate.onAdapterUpdate();
    }

    //...................

    public void dicToProductQuantity(Product product) {
        if (product.getQuantity() > 1) {
            product.setQuantity(product.getQuantity() - 1);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    database.productDao().addProduct(product);
                }
            });
            onAdapterUpdate.onAdapterUpdate();
        }
    }

    //................

    void deleteProduct(Product product) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    database.productDao().deleteProduct(product);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        productList.remove(product);
        onAdapterUpdate.onAdapterUpdate();
    }
}
