package com.unicauca.domifoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.unicauca.domifoods.adapters.AdapterRestaurants;
import com.unicauca.domifoods.domain.Restaurant;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_background;
    Picasso mPicasso;
    RecyclerView recyclerView;
    ArrayList<Restaurant> restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpTheRecyclerView();
        mPicasso = new Picasso.Builder(getApplicationContext())
                .indicatorsEnabled(false)
                .build();

        imageView_background = findViewById(R.id.iv_background);
        mPicasso.load("https://ak.picdn.net/shutterstock/videos/20526580/thumb/1.jpg")
                .fit()
                .centerCrop()
                .into(imageView_background);
    }

    private void setUpTheRecyclerView() {
        restaurants = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_restaurants);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        fillOutTheRestaurants();
        AdapterRestaurants adapterRestaurants= new AdapterRestaurants(restaurants);
        recyclerView.setAdapter(adapterRestaurants);
    }

    public void  fillOutTheRestaurants(){
        restaurants.add(new Restaurant("Pio Pio","https://www.piopio.com.co/img/piopio/logofull.png"));
        restaurants.add(new Restaurant("La cosecha Parrillada","http://www.lacosechaparrillada.com/wp-content/uploads/2018/08/cropped-logo-La-Cosecha-Parrillada-250.png"));
        restaurants.add(new Restaurant("Pio Pio","https://www.unicauca.edu.co/sistemas/sites/default/files/fotografia/perfiles/fotomia_0.jpg"));
        restaurants.add(new Restaurant("Pio Pio","https://www.unicauca.edu.co/sistemas/sites/default/files/fotografia/perfiles/fjpino.png"));
        restaurants.add(new Restaurant("Pio Pio","https://0.academia-photos.com/42065034/11710805/13055952/s200_erwin_meza.vega.png"));
        restaurants.add(new Restaurant("Pio Pio","https://www.unicauca.edu.co/sistemas/sites/default/files/fotografia/perfiles/Foto.jpg"));
        restaurants.add(new Restaurant("Pio Pio","https://www.fietnew.site/wp-content/uploads/2018/12/CarlosArdila.jpg"));
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
}