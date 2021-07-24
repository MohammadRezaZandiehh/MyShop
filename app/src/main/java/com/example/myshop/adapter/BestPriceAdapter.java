package com.example.myshop.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myshop.R;
import com.example.myshop.model.Product;

import java.util.List;

public class BestPriceAdapter extends RecyclerView.Adapter<BestPriceAdapter.BestPriceViewHolder> {
    List<Product> productList;
    Context context;

    public BestPriceAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public BestPriceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_best_price, parent, false);

        return new BestPriceViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull BestPriceViewHolder holder, int position) {
        Product product = productList.get(position);
        Glide.with(context)
                .load(product.getImageUrl())
                .into(holder.imageViewProduct);
        holder.textViewPrice.setText(product.getPrice() + "");
        holder.textViewRewPrice.setText(product.getRewPrice() + "");
        holder.textViewRewPrice.setPaintFlags(holder.textViewRewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class BestPriceViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewProduct;
        TextView textViewPrice;
        TextView textViewRewPrice;

        public BestPriceViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProduct = itemView.findViewById(R.id.imageViewBestPrice);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewRewPrice = itemView.findViewById(R.id.textViewRewPrice);

        }
    }
}
