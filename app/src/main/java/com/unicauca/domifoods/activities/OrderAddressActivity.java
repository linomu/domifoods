package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicauca.domifoods.MapsFragment;
import com.unicauca.domifoods.R;
import com.unicauca.domifoods.fragments.OrderAddressFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAddressActivity extends AppCompatActivity implements MapsFragment.NotificarCoordenadas{

    //Location
    private LocationManager locationManager;
    private Location location;
    private LocationListener locationListener;
    private double longitude;
    private double latitude;
    final int[] onLocationChanged = {0};
    FloatingActionButton fab_position;
    FrameLayout conatiner_act;


    EditText et_direccion_envio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address);
        et_direccion_envio = findViewById(R.id.et_direccion_envio);
        conatiner_act = findViewById(R.id.conatiner_act);
        fab_position = findViewById(R.id.fab_position);


        conatiner_act.setVisibility(View.INVISIBLE);
        fab_position.setVisibility(View.INVISIBLE);


        fab_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsFragment fr = new MapsFragment();
                Bundle bn = new Bundle();
                //tv_coordenadas.setText(location.getLatitude()+" , "+location.getLongitude());
                //obtenerDireccion(location.getLatitude(), location.getLongitude());
                bn.putDouble(MapsFragment.LATITUD, latitude);
                bn.putDouble(MapsFragment.LONGITUD, longitude);
                bn.putString(MapsFragment.TITULO, "🏠");
                bn.putString(MapsFragment.SNIPPET, "Aquí te encuentras!");

                fr.setArguments(bn);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.conatiner_act, fr)
                        .commit();
                et_direccion_envio.setText("");
                et_direccion_envio.setText(obtenerDireccion(latitude, longitude));
            }
        });
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                conatiner_act.setVisibility(View.VISIBLE);
                fab_position.setVisibility(View.VISIBLE);
                if (onLocationChanged[0] == 0) {
                    MapsFragment fr = new MapsFragment();
                    Bundle bn = new Bundle();
                    //tv_coordenadas.setText(location.getLatitude()+" , "+location.getLongitude());
                    //obtenerDireccion(location.getLatitude(), location.getLongitude());
                    bn.putDouble(MapsFragment.LATITUD, latitude);
                    bn.putDouble(MapsFragment.LONGITUD, longitude);
                    bn.putString(MapsFragment.TITULO, "🏠");
                    bn.putString(MapsFragment.SNIPPET, "Aquí te encuentras!");
                    fr.setArguments(bn);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.conatiner_act, fr)
                            .commit();
                }
                onLocationChanged[0]++;


                Log.i("linomapa", "Longitude " + longitude + " Latitude " + latitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), "What that hell is going on?", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.e("linomapa", "VOY A " +
                        " QUE ENCIENDAN EL GPS");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            Log.e("linomapa", "Pedir permisos");
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);

            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //askLocation();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

    }

    @Override
    public void enviarCoordenadas(LatLng latLng) {
        Toast.makeText(this, "Coordenadas: "+latLng, Toast.LENGTH_SHORT).show();
        et_direccion_envio.setText("");
        et_direccion_envio.setText(obtenerDireccion(latLng.latitude, latLng.longitude));

    }

    public String obtenerDireccion(double latitud, double longitud){
        String direccion="";
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            Log.i("linomapa", address + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName);
            direccion = address;
            Log.i("linomapa", address);
                    //tv_address.setText(address+" "+city+" "+state+" "+country+" "+postalCode+" "+ knownName);
        } catch (IOException e) {
            Log.i("linomapa", e.getMessage());
            //tv_address.setText("No se pudo obtener la dirección");
            e.printStackTrace();

        }
        return  direccion;


    }
}