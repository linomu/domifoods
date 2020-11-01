package com.unicauca.domifoods.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unicauca.domifoods.MainActivity;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.adapters.AdapterShoppingListt;
import com.unicauca.domifoods.domain.Product;
import com.unicauca.domifoods.modelsProduct.ProductShoppingCart;

import java.util.ArrayList;


public class ShoppingcarFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView menu_options;
    NavController navController;
    public static ArrayList<ProductShoppingCart> products = new ArrayList<>();
    private AdapterShoppingListt adapterShoppingListt;

    TextView tv_prueba;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShoppingcarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingcarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingcarFragment newInstance(String param1, String param2) {
        ShoppingcarFragment fragment = new ShoppingcarFragment();
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
        return inflater.inflate(R.layout.fragment_shoppingcar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        menu_options = view.findViewById(R.id.menu_options_nav);
        tv_prueba = view.findViewById(R.id.tv_prueba);
        menu_options.setOnNavigationItemSelectedListener(this);
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_menu:
                navController.navigate(R.id.action_shoppingcarFragment_to_restaurantFragment);
                break;
            case R.id.nav_shopping_car:
                Toast.makeText(getContext(), "Here we are :)", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_order:
                navController.navigate(R.id.action_shoppingcarFragment_to_ordersFragment);
                break;
            case R.id.nav_deliveryman:
                navController.navigate(R.id.action_shoppingcarFragment_to_delivermanFragment);
                break;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        Menu menu = menu_options.getMenu();
        MenuItem item = menu.getItem(1);
        item.setChecked(true);
        Log.e("Lino", "OnStart ShoppingFragment");
        this.getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );

        Log.i("lino", "Productos que están en el carrito");
        for(ProductShoppingCart productShoppingCart : products){
            Log.i("lino", productShoppingCart.toString());
            tv_prueba.setText(tv_prueba.getText()+productShoppingCart.toString()+"\n");

        }
    }
}