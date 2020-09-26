package com.unicauca.domifoods;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unicauca.domifoods.fragments.AlertFragment;
import com.unicauca.domifoods.fragments.DelivermanFragment;
import com.unicauca.domifoods.fragments.OrdersFragment;
import com.unicauca.domifoods.fragments.RestaurantFragment;
import com.unicauca.domifoods.fragments.ShoppingcarFragment;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView menu_options;
    FrameLayout container;

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

    public void onBackPressed() {
        super.onStart();
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==event.KEYCODE_BACK){
          //  AlertDialog.Builder builder = new AlertDialog.Builder(Context:this);
            //builder.setMessage("desea salir de domifoots")
              //      .setPositiveButton(Text:"si")

        }
        return super.onKeyDown(keyCode, event);
    }
}