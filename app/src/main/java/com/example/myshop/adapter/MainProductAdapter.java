package com.example.myshop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop.R;
import com.example.myshop.database.ShopDatabase;
import com.example.myshop.listener.OnAddProduct;
import com.example.myshop.model.Product;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainProductAdapter extends RecyclerView.Adapter<MainProductAdapter.MainProductViewHolder> {
    static List<Product> productList;
    static Context context;
    static OnAddProduct onAddProduct;

    public MainProductAdapter(List<Product> productList, Context context, OnAddProduct onAddProduct) {
        this.productList = productList;
        this.context = context;
        this.onAddProduct = onAddProduct;
    }


    @NonNull
    @Override
    public MainProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_home, parent, false);

        return new MainProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainProductViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.imageViewProduct);

        holder.textViewProductPrice.setText(product.getName());
        holder.textViewProductName.setText(product.getPrice() + "");
        holder.textViewProductRawPrice.setText(product.getRewPrice() + "");
        // chon 2 kHATE BALA AZ jense double hastand +"" kardim ta b String tabdil shavand .
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    static class MainProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewProduct;
        TextView textViewProductName;
        TextView textViewProductPrice;
        TextView textViewProductRawPrice;
        Button buttonAddToBasket;

        public MainProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
            textViewProductName = itemView.findViewById(R.id.textViewProductName);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            textViewProductRawPrice = itemView.findViewById(R.id.textViewProductRawPrice);
            buttonAddToBasket = itemView.findViewById(R.id.buttonAddToBasket);

            buttonAddToBasket.setOnClickListener(this);
            // no e dgiam mishod onClickListener ro ezafe krd .....

//            buttonAddToBasket.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }

        @Override
        public void onClick(View v) {
            Product product = productList.get(getAdapterPosition());
            // tozhi e khafani dar bare khate balaei dad k manish vhi mishe .
            product.setQuantity(product.getQuantity() + 1);
            ShopDatabase shopDatabase = ShopDatabase.getInstance(context);
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    shopDatabase.productDao().addProduct(product);
                }
            });
            onAddProduct.onPrductAdded();
        }
    }

}