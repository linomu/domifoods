package com.unicauca.domifoods.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.MainActivity;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterCategories;
import com.unicauca.domifoods.adapters.AdapterProducts;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.domain.Product;
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.settings.CircleTransform;

import java.util.ArrayList;
import java.util.Map;


public class ProductsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener{


    ImageView img_restaurant_icon, img_restaurant_product_bg;
    Picasso mPicasso;
    RecyclerView recyclerView, recyclerView_products;
    ArrayList<Category> categories;
    ArrayList<Product> products;
    NavController navController;
    BottomNavigationView menu_options;

    private static final String ID_RESTAURANT_PRODUCTS = "id_restaurant_products";


    private String  id_restaurant;


    public ProductsFragment() {
        // Required empty public constructor
    }


    public static ProductsFragment newInstance(String id_restaurant) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ID_RESTAURANT_PRODUCTS, id_restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id_restaurant = getArguments().getString(ID_RESTAURANT_PRODUCTS);
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



        navController = Navigation.findNavController(view);
        menu_options = view.findViewById(R.id.menu_options_nav);
        menu_options.setOnNavigationItemSelectedListener(this);


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

        Toast.makeText(getContext(), "Aqui tengo su id" + id_restaurant, Toast.LENGTH_SHORT).show();

        setUpTheRecyclerView(view);
        setUpTheRecyclerViewProducts(view);

    }


/*
    @Override
    public void onPause() {
        super.onPause();
        MainActivity.id_restaurant_selected = id_restaurant;
        Log.i("Lino","OnPause method executed itself and MainActivity has "+ MainActivity.id_restaurant_selected);
    }
*/
    private void setUpTheRecyclerView(View view) {
        categories = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_categories);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        fillOutTheCategories();
        AdapterCategories adapterCategories= new AdapterCategories(categories);
        recyclerView.setAdapter(adapterCategories);
    }


    private void setUpTheRecyclerViewProducts(View view) {
        products = new ArrayList<>();
        recyclerView_products = view.findViewById(R.id.recycler_products);
        recyclerView_products.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fillOutTheProducts();
        AdapterProducts adapterProducts = new AdapterProducts(products);
        recyclerView_products.setAdapter(adapterProducts);
    }

    public void  fillOutTheCategories(){
        //Servicio web
        categories.add(new Category("Almuerzos","https://i.pinimg.com/originals/d1/e9/8c/d1e98c995db0af92a858e7ccf023b37d.jpg"));
        categories.add(new Category("Jugos","https://cdn.kiwilimon.com/recetaimagen/30632/34260.jpg"));
        categories.add(new Category("Postres","https://okdiario.com/img/2018/06/09/tarta-de-limon-655x368.jpg"));
        categories.add(new Category("Café","https://www.juanvaldezcafe.com/sites/default/files/tinto_grande.jpg"));
        categories.add(new Category("Helados","https://imagenes.20minutos.es/files/image_656_370/uploads/imagenes/2020/06/10/helado-de-stracciatella.jpeg"));
    }
    public void  fillOutTheProducts(){

        products.add(new Product("Arroz con Pollo","https://s1.eestatic.com/2020/01/28/cocinillas/recetas/pasta-y-arroz/Arroz-Pollo-Pasta_y_arroz_463216136_143694761_1024x576.jpg", (float) 15000));
        products.add(new Product("Caldo de Res","https://www.deliciosi.com/images/100/150/caldo-de-res.jpg", (float)12000));
        products.add(new Product("Sancocho","https://t1.rg.ltmcdn.com/es/images/1/8/1/img_sancocho_de_gallina_o_pollo_12181_orig.jpg", (float)10000));
        products.add(new Product("Ajiaco","https://images-gmi-pmc.edge-generalmills.com/4a994b44-4d9c-4552-82e4-6e9964322a78.jpg",(float)8500));
        products.add(new Product("Frijoles","https://t2.uc.ltmcdn.com/images/8/1/4/img_como_hacer_frijoles_colombianos_31418_orig.jpg", (float)7500));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()){
            case R.id.nav_menu:
                navController.navigate(R.id.action_productsFragment_to_restaurantFragment);
                break;
            case R.id.nav_shopping_car:
                navController.navigate(R.id.action_productsFragment_to_shoppingcarFragment);
                break;
            case R.id.nav_order:
                navController.navigate(R.id.action_productsFragment_to_ordersFragment);
                break;
            case R.id.nav_deliveryman:
                navController.navigate(R.id.action_productsFragment_to_delivermanFragment);
                break;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        Log.e("Lino", "OnStart ProdutcsFragment");
    }

}

