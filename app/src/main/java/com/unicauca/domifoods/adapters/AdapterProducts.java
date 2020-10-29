package com.unicauca.domifoods.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.domain.Product;
import com.unicauca.domifoods.settings.CircleTransform;

import java.util.ArrayList;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.holderProducts> {


    /*Variables that we're gonna use*/
    ArrayList<Product> products;
    Picasso mPicasso;



    public AdapterProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public holderProducts onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        mPicasso = new Picasso.Builder(view.getContext())
                .indicatorsEnabled(false)
                .build();
        return new holderProducts(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holderProducts holder, int position) {
        holder.name_product.setText(products.get(position).getName());
        holder.price.setText("$ "+products.get(position).getPrice().toString());
        mPicasso.load(products.get(position).getImage())
                .placeholder(R.drawable.test)
                .transform(new CircleTransform())
                .into(holder.img_product);
        holder.card_view_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("lino", "Me voy al detalle con la siguiente información :"+ products.get(position).toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class holderProducts extends RecyclerView.ViewHolder {
        ImageView img_product;
        TextView name_product, price;
        CardView card_view_product;
        public holderProducts(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            name_product = itemView.findViewById(R.id.tv_name_product);
            price = itemView.findViewById(R.id.tv_price_product);
            card_view_product = itemView.findViewById(R.id.card_view_product);

        }
    }
}
