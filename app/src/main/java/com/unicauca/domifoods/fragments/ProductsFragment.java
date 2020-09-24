package com.unicauca.domifoods.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterCategories;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.settings.CircleTransform;

import java.util.ArrayList;


public class ProductsFragment extends Fragment {


    ImageView img_restaurant_icon, img_restaurant_product_bg;
    Picasso mPicasso;
    RecyclerView recyclerView;
    ArrayList<Category> categories;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*Variable Initialization */
        img_restaurant_icon = view.findViewById(R.id.img_restaurant_icon);
        img_restaurant_product_bg = view.findViewById(R.id.img_restaurant_product_bg);
        /*Picasso Initialization*/
        mPicasso = new Picasso.Builder(getContext()).indicatorsEnabled(false).build();


        /*mPicasso.load("https://ak.picdn.net/shutterstock/videos/20526580/thumb/1.jpg")
                .fit()
                .centerCrop()
                .into(img_restaurant_product_bg);*/
        Picasso.with(getContext()).load("https://cdn.jpegmini.com/user/images/slider_puffin_jpegmini_mobile.jpg").transform(new CircleTransform()).into(img_restaurant_icon);

        setUpTheRecyclerView(view);

    }

    private void setUpTheRecyclerView(View view) {
        categories = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_categories);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fillOutTheCategories();
        AdapterCategories adapterCategories= new AdapterCategories(categories);
        recyclerView.setAdapter(adapterCategories);
    }

    public void  fillOutTheCategories(){
        categories.add(new Category("Almuerzos","https://i.pinimg.com/originals/d1/e9/8c/d1e98c995db0af92a858e7ccf023b37d.jpg"));
        categories.add(new Category("Jugos","https://cdn.kiwilimon.com/recetaimagen/30632/34260.jpg"));
        categories.add(new Category("Postres","https://okdiario.com/img/2018/06/09/tarta-de-limon-655x368.jpg"));
        categories.add(new Category("Café","https://www.juanvaldezcafe.com/sites/default/files/tinto_grande.jpg"));
        categories.add(new Category("Helados","https://imagenes.20minutos.es/files/image_656_370/uploads/imagenes/2020/06/10/helado-de-stracciatella.jpeg"));
    }
}

