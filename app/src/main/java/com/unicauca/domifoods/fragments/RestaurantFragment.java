package com.unicauca.domifoods.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.apiUser.ApiUser;
import com.unicauca.domifoods.apiUser.RetrofitClient;
import com.unicauca.domifoods.dialogs.SimpleDialogOptions;
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.modelsUser.GetRestaurant;
import com.unicauca.domifoods.modelsUser.Login_response;
import com.unicauca.domifoods.modelsUser.PostsRestaurants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestaurantFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    /*Variables*/
    //David
    //private TextView mJsonTxtView;
    //private ImageView mJsonImgView;
    //private RecyclerView.Adapter adapter;
    //TextView tv_post;
    private Retrofit retrofit;
    private static  final String TAG = "DOMIFOOD";
    private AdapterRestaurants listRestAdapter;
    //fin David
    ImageView imageView_background;
    Picasso mPicasso;
    RecyclerView recyclerView;
    ArrayList<PostsRestaurants> restaurants;
    private ProgressDialog progDailog;

    NavController navController;
    BottomNavigationView menu_options;
    ImageView img_salir;

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



        img_salir = view.findViewById(R.id.img_salir);
        img_salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDialogOptions simpleDialog = new SimpleDialogOptions();
                //Por medio de este set, le estoy pasando informacion al Dialog
                FragmentManager fm = getActivity().getSupportFragmentManager();
                simpleDialog.show(fm, "LoginDialog");
            }
        });
        navController = Navigation.findNavController(view);
        menu_options = view.findViewById(R.id.menu_options_nav);
        menu_options.setOnNavigationItemSelectedListener(this);
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

    private void setUpTheRecyclerView(View view) {
        restaurants = new ArrayList<>();

        //David
        startProgressDialog();
        //tv_post = view.findViewById(R.id.tv_restaurant);
        recyclerView = view.findViewById(R.id.recyclerview_restaurants);
        Call<List<PostsRestaurants>> restaurant = RetrofitClient.getInstance().getApi().getPosts();
        restaurant.enqueue(new Callback<List<PostsRestaurants>>() {
            @Override
            public void onResponse(Call<List<PostsRestaurants>> call, Response<List<PostsRestaurants>> response) {
                Log.i("david", "ingresar al metodo response"+ response.code());
                List<PostsRestaurants> ListRest = response.body();
                for(PostsRestaurants rest: ListRest){
                    //Log.i("david", "Name"+ rest.getName());
                    restaurants.add(rest);

                }

                listRestAdapter = new AdapterRestaurants(restaurants);
                recyclerView.setAdapter(listRestAdapter);
                recyclerView.setHasFixedSize(true);
                GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
                recyclerView.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<List<PostsRestaurants>> call, Throwable t) {
                Log.i("david", "ingresar al metodo Failer"+t.getMessage());
                Log.i("david", "ingresar al metodo Failer"+t.getCause());
            }
        });

        //Fin David

        //recyclerView = view.findViewById(R.id.recyclerview_restaurants);
        //LinearLayoutManager layoutManager  = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        //recyclerView.setLayoutManager(layoutManager);

        //fillOutTheRestaurants();
        AdapterRestaurants adapterRestaurants = new AdapterRestaurants(restaurants);
        adapterRestaurants.setListener(new AdapterRestaurants.RestaurantListener() {
            @Override
            public void restaurantSelected(int id) {

                //ese id es el codigo del restaurante
                Fragment selectedFragment = ProductsFragment.newInstance(String.valueOf(id));
                // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
                //como pasar datos de un destiono a otro.. usando el navcontroler
                navController.navigate(R.id.action_restaurantFragment_to_productsFragment);
            }
        });
        recyclerView.setAdapter(adapterRestaurants);

        //Listener

    }

    @Override
    public void onStart() {
        super.onStart();
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        Log.e("Lino", "OnStart RestaurantFragment");
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_menu:
                Toast.makeText(getContext(), "Here we are :)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_shopping_car:
                navController.navigate(R.id.action_restaurantFragment_to_shoppingcarFragment);
                break;
            case R.id.nav_order:
                navController.navigate(R.id.action_restaurantFragment_to_ordersFragment);
                break;
            case R.id.nav_deliveryman:
                navController.navigate(R.id.action_restaurantFragment_to_delivermanFragment);
                break;
        }
        return true;
    }


}
/*
progDailog = new ProgressDialog(view.getContext());
        progDailog.setMessage("Cargando...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(false);
        progDailog.show();
        progDailog.dismiss();


*/
