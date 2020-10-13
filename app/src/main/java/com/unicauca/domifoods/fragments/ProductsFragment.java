package com.unicauca.domifoods.fragments;

import android.app.ProgressDialog;
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
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.domain.Category;
import com.unicauca.domifoods.domain.Product;
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.modelsCategory.CategoriesResponse;
import com.unicauca.domifoods.modelsProduct.ProductResponse;
import com.unicauca.domifoods.settings.CircleTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductsFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {


    ImageView img_restaurant_icon, img_restaurant_product_bg;
    Picasso mPicasso;
    RecyclerView recyclerView, recyclerView_products;
    ArrayList<Category> categories;
    ArrayList<Product> products;
    NavController navController;
    BottomNavigationView menu_options;
    private ProgressDialog progDailog;

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

        id_restaurant= "1";

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

    public void  fillOutTheCategories() {
        //Servicio web

        Call<List<CategoriesResponse>> call = RetrofitClient.getInstance().getApi().getCategoriesByRestaurant(1);
        call.enqueue(new Callback<List<CategoriesResponse>>() {
            @Override
            public void onResponse(Call<List<CategoriesResponse>> call, Response<List<CategoriesResponse>> response) {
                Log.i("Lino", "I'm inside OnResponse");
                if(response.isSuccessful()){
                    Log.i("Lino", "The response was successful. Code: "+response.code());
                    List<CategoriesResponse> categoriesByResponse = response.body();
                    for(CategoriesResponse category : categoriesByResponse){
                        Log.i("Lino", category.toString());
                        categories.add(new Category(category.getName(),category.getImage()));

                    }
                }else{
                    Log.i("Lino", "The response wasn't successful. Code: "+response.code());

                }

            }

            @Override
            public void onFailure(Call<List<CategoriesResponse>> call, Throwable t) {
                Log.i("Lino", "OnFailure! ");
                //stopProgressDialog();
            }
        });


    }
    public void  fillOutTheProducts(){


        int idRestaurant = 2;
        int idCategory = 3;
        Call<List<ProductResponse>> call = RetrofitClient.getInstance().getApi().getProductsByCategoryAndRestaurant(idRestaurant, idCategory);
        call.enqueue(new Callback<List<ProductResponse>>() {
            @Override
            public void onResponse(Call<List<ProductResponse>> call, Response<List<ProductResponse>> response) {
                Log.i("Lino", "I'm inside OnResponse FillOutTheProduct");
                if(response.isSuccessful()){
                    Log.i("Lino", "The response was successful. Code: "+response.code());
                    List<ProductResponse> productsByCategoryResponse = response.body();
                    for(ProductResponse product : productsByCategoryResponse){
                        Log.i("Lino", product.toString());
                        products.add(new Product(product.getName(),product.getImage(), (float) product.getPrice()));
                    }
                }else{
                    Log.i("Lino", "The response wasn't successful. Code: "+response.code());
                }

            }

            @Override
            public void onFailure(Call<List<ProductResponse>> call, Throwable t) {
                Log.i("Lino", "The response wasn't successful. Problem: "+t.getMessage());
                //stopProgressDialog();

            }
        });


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

    private void startProgressDialog() {
        progDailog = new ProgressDialog(getContext());
        progDailog.setMessage(getResources().getString(R.string.loading));
        //progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //progDailog.setCancelable(true);
        progDailog.show();
    }
    private void stopProgressDialog(){
        progDailog.dismiss();
    }

}

