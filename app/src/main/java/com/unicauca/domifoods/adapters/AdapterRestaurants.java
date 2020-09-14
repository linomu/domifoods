package com.unicauca.domifoods.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.domain.Restaurant;

import java.util.ArrayList;

public class AdapterRestaurants extends RecyclerView.Adapter<AdapterRestaurants.holderRestaurants> {

    /*Variables that we're gonna use*/
    ArrayList<Restaurant> restaurants;
    Picasso mPicasso;

    //Constructor
    public AdapterRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public AdapterRestaurants.holderRestaurants onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurants, parent, false);
        mPicasso = new Picasso.Builder(view.getContext())
                .indicatorsEnabled(false)
                .build();
        return new holderRestaurants(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRestaurants.holderRestaurants holder, int position) {

        mPicasso.load(restaurants.get(position).getPhoto())
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class holderRestaurants extends RecyclerView.ViewHolder {
        ImageView imageView;
        public holderRestaurants(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imv_restaurant);
        }
    }
}
