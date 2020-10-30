package com.unicauca.domifoods.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.unicauca.domifoods.MapsFragment;
import com.unicauca.domifoods.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAddressActivity extends AppCompatActivity implements MapsFragment.NotificarCoordenadas, View.OnClickListener {


    //Location
    private LocationManager locationManager;
    private Location location;
    private LocationListener locationListener;
    private double longitude=0;
    private double latitude=0;
    private double final_longitude=0;
    private double final_latitude=0;

    int onLocationChanged=0;

    private FloatingActionButton fab_position;
    private FrameLayout conatiner_act;
    private Button btn_continuar_direccion;
    private EditText et_direccion_envio,et_observaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_address);
        InicializarVariables();
        locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                conatiner_act.setVisibility(View.VISIBLE);
                fab_position.setVisibility(View.VISIBLE);
                if (onLocationChanged == 0) {
                    final_latitude = latitude;
                    final_longitude = longitude;
                    et_direccion_envio.setText("");
                    et_direccion_envio.setText(obtenerDireccion(latitude, longitude));
                    try {
                        MapsFragment fr = new MapsFragment();
                        Bundle bn = new Bundle();
                        bn.putDouble(MapsFragment.LATITUD, latitude);
                        bn.putDouble(MapsFragment.LONGITUD, longitude);
                        bn.putString(MapsFragment.TITULO, "🏠");
                        bn.putString(MapsFragment.SNIPPET, "Aquí te encuentras!");
                        fr.setArguments(bn);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.conatiner_act, fr)
                                .commit();
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Error> "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                onLocationChanged++;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(OrderAddressActivity.this, "El GPS está activo 😁", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(OrderAddressActivity.this, "Tienes el GPS descativado 😥", Toast.LENGTH_LONG).show();
                conatiner_act.setVisibility(View.INVISIBLE);
                fab_position.setVisibility(View.INVISIBLE);
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

    private void InicializarVariables() {
        et_direccion_envio = findViewById(R.id.et_direccion_envio);
        conatiner_act = findViewById(R.id.conatiner_act);
        fab_position = findViewById(R.id.fab_position);
        et_observaciones = findViewById(R.id.et_observaciones);
        btn_continuar_direccion = findViewById(R.id.btn_continuar_direccion);
        conatiner_act.setVisibility(View.INVISIBLE);
        fab_position.setVisibility(View.INVISIBLE);
        btn_continuar_direccion.setOnClickListener(this);
        fab_position.setOnClickListener(this);
    }

    @Override
    public void enviarCoordenadas(LatLng latLng) {
        final_latitude = latLng.latitude;
        final_longitude = latLng.longitude;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_continuar_direccion:
                String direccionSeleccionada = et_direccion_envio.getText().toString();
                if(direccionSeleccionada.equals("")){
                    et_direccion_envio.setError(getResources().getString(R.string.msg_required_failed));
                }else{
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    if(final_latitude!=0 && final_longitude!=0){
                        intent.putExtra(PaymentActivity.LATITUD,final_latitude);
                        intent.putExtra(PaymentActivity.LONGITUD,final_longitude);
                    }
                    intent.putExtra(PaymentActivity.OBSERVACIONES,et_observaciones.getText().toString());
                    intent.putExtra(PaymentActivity.DIRECCION,et_direccion_envio.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.fab_position:
                final_latitude = latitude;
                final_longitude = longitude;
                MapsFragment fr = new MapsFragment();
                Bundle bn = new Bundle();
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
                break;

        }
    }
}