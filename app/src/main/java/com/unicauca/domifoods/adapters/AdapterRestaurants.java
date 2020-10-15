package com.unicauca.domifoods.adapters;

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
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.modelsUser.PostsRestaurants;

import java.util.ArrayList;

public class AdapterRestaurants extends RecyclerView.Adapter<AdapterRestaurants.holderRestaurants> {



    /*Variables that we're gonna use*/
    ArrayList<PostsRestaurants> restaurants;
    Picasso mPicasso;
    RestaurantListener listener;

    //Constructor
    public AdapterRestaurants(ArrayList<PostsRestaurants> restaurants) {
        this.restaurants = restaurants;
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

        holder.tv_nombre_restaurant.setText(restaurants.get(position).getName());
        mPicasso.load(restaurants.get(position).getImage())
                .fit()
                .placeholder(R.drawable.test)
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
       TextView tv_nombre_restaurant;
        CardView cardView_restaurant;

        public holderRestaurants(@NonNull View itemView) {
            super(itemView);
            //textView = itemView.findViewById(R.id.textAddrees);
            imageView = itemView.findViewById(R.id.imv_restaurant);
            cardView_restaurant = itemView.findViewById(R.id.cardview_restaurant);
            tv_nombre_restaurant = itemView.findViewById(R.id.tv_nombre_restaruante);

        }
    }


    public void addRestaurant(ArrayList<PostsRestaurants> listRest){
        restaurants.addAll(listRest);
        notifyDataSetChanged();
    }
}
