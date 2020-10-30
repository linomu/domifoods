package com.unicauca.domifoods.fragments;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.unicauca.domifoods.MapsFragment;
import com.unicauca.domifoods.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderAddressFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderAddressFragment extends Fragment implements MapsFragment.NotificarCoordenadas {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private double longitude;
    private double latitude;

    private EditText et_direccion_envio;


    public static double otherLongitude;
    public static double otherlatitude;
    //Location
    private LocationManager locationManager;
    private Location location;
    private LocationListener locationListener;

    final int[] onLocationChanged = {0};

    public OrderAddressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderAddressFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderAddressFragment newInstance(String param1, String param2) {
        OrderAddressFragment fragment = new OrderAddressFragment();
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

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();

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

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, fr)
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
                Toast.makeText(getContext(), "What that hell is going on?", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.e("linomapa", "VOY A " +
                        " QUE ENCIENDAN EL GPS");
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getContext(),
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_direccion_envio = view.findViewById(R.id.et_direccion_envio);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    public void configurar(){
        et_direccion_envio.setHint("lino");
    }
    public static void obtenerCalle(double latitud, double longitud, Context context){
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(context, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            Log.i("linomapa", address + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName);

            //tv_address.setText(address+" "+city+" "+state+" "+country+" "+postalCode+" "+ knownName);
        } catch (IOException e) {
            Log.i("linomapa", e.getMessage());
            //tv_address.setText("No se pudo obtener la dirección");
            e.printStackTrace();

        }


    }

    public void obtenerDireccion(double latitud, double longitud) {
        Geocoder geocoder;
        List<Address> addresses = new ArrayList<>();
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitud, longitud, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            Log.i("linomapa", address + " " + city + " " + state + " " + country + " " + postalCode + " " + knownName);

            //tv_address.setText(address+" "+city+" "+state+" "+country+" "+postalCode+" "+ knownName);
        } catch (IOException e) {
            Log.i("linomapa", e.getMessage());
            //tv_address.setText("No se pudo obtener la dirección");
            e.printStackTrace();

        }

        Location loc1 = new Location("");
        loc1.setLatitude(latitud);
        loc1.setLongitude(longitud);

        Location loc2 = new Location("");
        loc2.setLatitude(location.getLatitude());
        loc2.setLongitude(location.getLongitude());

        float distance = loc1.distanceTo(loc2);

        int speed = 30;
        float time = distance / speed;
        //tv_address.setText(tv_address.getText()+"\n\nTiempo: "+String.valueOf(time));


        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(latitud - location.getLatitude());
        double lngDiff = Math.toRadians(longitud - location.getLongitude());
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2) +
                Math.cos(Math.toRadians(location.getLatitude())) * Math.cos(Math.toRadians(latitud)) *
                        Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distancia2 = earthRadius * c;

        int meterConversion = 1609;

        float distancia = (float) (distancia2 * meterConversion);
        //tv_address.setText(tv_address.getText()+"\n\nDistancia: "+String.valueOf(distancia));

        float[] results = new float[10];
        Location.distanceBetween(location.getLatitude(), location.getLongitude(), latitud, longitud, results);

        float km = results[0] / 1000;
        float t = km / 15;
        float min = t * 60;
        //tv_address.setText(tv_address.getText()+"\n\nDistancia 2: "+String.valueOf(results[0])+" Min: "+min);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 10 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        }
    }

    @Override
    public void enviarCoordenadas(LatLng latLng) {
        Toast.makeText(getContext(), "Coordenadas: " + latLng, Toast.LENGTH_SHORT).show();
    }
}