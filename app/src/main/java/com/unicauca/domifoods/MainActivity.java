package com.unicauca.domifoods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.domain.Restaurant;
import com.unicauca.domifoods.fragments.DelivermanFragment;
import com.unicauca.domifoods.fragments.OrdersFragment;
import com.unicauca.domifoods.fragments.ProductsFragment;
import com.unicauca.domifoods.fragments.RestaurantFragment;
import com.unicauca.domifoods.fragments.ShoppingcarFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView menu_options;
    FrameLayout container;
    static public String whereAmI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*The first fragmente*/
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, restaurantFragment).commit();
        /*Variables*/
        menu_options = findViewById(R.id.menu_options_nav);
        menu_options.setOnNavigationItemSelectedListener(this);
        container = findViewById(R.id.frame_container);
    }

    /*
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id_restaurant_selected",id_restaurant_selected);
        Log.i("Lino","OnSaveInstanceStated saves "+id_restaurant_selected);
    }
    */
    @Override
    protected void onStart() {
        super.onStart();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()){
            case R.id.nav_menu:
                selectedFragment = new RestaurantFragment();

                break;
            case R.id.nav_shopping_car:
                //selectedFragment = new ShoppingcarFragment();
                selectedFragment = new ShoppingcarFragment();
                Toast.makeText(this, "No implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_order:
                selectedFragment = new OrdersFragment();
                Toast.makeText(this, "No implemented yet", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_deliveryman:
                selectedFragment = new DelivermanFragment();
                Toast.makeText(this, "It will be implemented soon", Toast.LENGTH_SHORT).show();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(whereAmI.equals("Products")){
            Log.i("Lino","BackPress and I'm in products");
            /*Fragment selectedFragment = new RestaurantFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, selectedFragment).commit();
            */


        }else{
            Log.i("Lino","BackPress but  I'm not in products");

        }

    }
}