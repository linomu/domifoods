package com.unicauca.domifoods.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.domain.Restaurant;

import java.util.ArrayList;


public class RestaurantFragment extends Fragment {

    /*Variables*/
    ImageView imageView_background;
    Picasso mPicasso;
    RecyclerView recyclerView;
    ArrayList<Restaurant> restaurants;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantFragment newInstance(String param1, String param2) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpTheRecyclerView(view);
        mPicasso = new Picasso.Builder(getContext())
                .indicatorsEnabled(false)
                .build();

        imageView_background = view.findViewById(R.id.iv_background);
        mPicasso.load("https://ak.picdn.net/shutterstock/videos/20526580/thumb/1.jpg")
                .fit()
                .centerCrop()
                .into(imageView_background);
    }

    private void setUpTheRecyclerView(View view) {
        restaurants = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerview_restaurants);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        fillOutTheRestaurants();
        AdapterRestaurants adapterRestaurants= new AdapterRestaurants(restaurants);
        recyclerView.setAdapter(adapterRestaurants);
    }

    public void  fillOutTheRestaurants(){
        restaurants.add(new Restaurant("Pio Pio","https://www.piopio.com.co/img/piopio/logofull.png"));
        restaurants.add(new Restaurant("La cosecha Parrillada","http://www.lacosechaparrillada.com/wp-content/uploads/2018/08/cropped-logo-La-Cosecha-Parrillada-250.png"));
        restaurants.add(new Restaurant("Pio Pio","https://d25dk4h1q4vl9b.cloudfront.net/bundles/front/media/images/header/mcdonalds-logo.png"));
        restaurants.add(new Restaurant("Pio Pio","https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Burger_King_logo.svg/1200px-Burger_King_logo.svg.png"));
        restaurants.add(new Restaurant("Pio Pio","https://img.pystatic.com/restaurants/domi_47712.jpg"));
        restaurants.add(new Restaurant("Pio Pio","https://i.pinimg.com/originals/ec/c3/2b/ecc32bd5a4a7cc2465dace39a54b0561.jpg"));
        restaurants.add(new Restaurant("Pio Pio","https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/1200px-Starbucks_Corporation_Logo_2011.svg.png"));
        restaurants.add(new Restaurant("Pio Pio","https://unicentrodearmenia.com/wp-content/uploads/2018/06/juan-valdez.jpg"));
        restaurants.add(new Restaurant("Pio Pio","https://i.ytimg.com/vi/IG8hHUvYF1w/hqdefault.jpg"));
        restaurants.add(new Restaurant("Pio Pio","https://www.piopio.com.co/img/piopio/logofull.png"));
        restaurants.add(new Restaurant("La cosecha Parrillada","http://www.lacosechaparrillada.com/wp-content/uploads/2018/08/cropped-logo-La-Cosecha-Parrillada-250.png"));
        restaurants.add(new Restaurant("Pio Pio","https://d25dk4h1q4vl9b.cloudfront.net/bundles/front/media/images/header/mcdonalds-logo.png"));
        restaurants.add(new Restaurant("Pio Pio","https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/Burger_King_logo.svg/1200px-Burger_King_logo.svg.png"));
        restaurants.add(new Restaurant("Pio Pio","https://img.pystatic.com/restaurants/domi_47712.jpg"));






    }

}