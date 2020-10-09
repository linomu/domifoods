package com.unicauca.domifoods.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.domain.Restaurant;

import java.util.ArrayList;

public class AdapterRestaurants extends RecyclerView.Adapter<AdapterRestaurants.holderRestaurants> {



    /*Variables that we're gonna use*/
    ArrayList<Restaurant> restaurants;
    Picasso mPicasso;
    RestaurantListener listener;

    //Constructor
    public AdapterRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public AdapterRestaurants() {

    }

    //Interface -- Declare
    public interface RestaurantListener{
        void restaurantSelected(int id);
    }


    //Bind
    public void setListener(RestaurantListener listener) {
        this.listener = listener;
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
    public void onBindViewHolder(@NonNull AdapterRestaurants.holderRestaurants holder, final int position) {

        mPicasso.load(restaurants.get(position).getImage())
                .fit()
                .into(holder.imageView);
        //Trigger
        holder.cardView_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.restaurantSelected(restaurants.get(position).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class holderRestaurants extends RecyclerView.ViewHolder {
        ImageView imageView;
        CardView cardView_restaurant;
        public holderRestaurants(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imv_restaurant);
            cardView_restaurant = itemView.findViewById(R.id.cardview_restaurant);
        }
    }

    public void addRestaurant(ArrayList<Restaurant> listRest){
        //dataset.addAll(listRest);
        notifyDataSetChanged();
    }
}
