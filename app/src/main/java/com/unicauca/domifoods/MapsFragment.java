package com.unicauca.domifoods;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    public static final String LATITUD = "myLatitud";
    public static final String LONGITUD = "myLongitud";
    public static final String TITULO = "myTitulo";
    public static final String SNIPPET= "mySnippet";
    private double myLatitud;
    private double myLongitud;
    private String myTitulo, mySnippet;

    public String getMyTitulo() {
        return myTitulo;
    }

    public void setMyTitulo(String myTitulo) {
        this.myTitulo = myTitulo;
    }

    public String getMySnippet() {
        return mySnippet;
    }

    public void setMySnippet(String mySnippet) {
        this.mySnippet = mySnippet;
    }

    public double getMyLatitud() {
        return myLatitud;
    }

    public void setMyLatitud(double myLatitud) {
        this.myLatitud = myLatitud;
    }

    public double getMyLongitud() {
        return myLongitud;
    }

    public void setMyLongitud(double myLongitud) {
        this.myLongitud = myLongitud;
    }
    private NotificarCoordenadas listener;
    public interface NotificarCoordenadas{
        void enviarCoordenadas(LatLng latLng);
    }

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            int zoom = 18;
            LatLng casa = new LatLng(getMyLatitud(), getMyLongitud());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(casa, zoom));
            googleMap.addMarker(new MarkerOptions()
                    .position(casa)
                    .title(getMyTitulo())
                    .snippet(getMySnippet())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(casa));

            //Obtener las coordenadas
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    Log.i("map_lino", "Estamos haciendo click en : "+latLng);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    //markerOptions.title(latLng.latitude +" "+ latLng.longitude);
                    markerOptions.title("¿Quieres recibir aquí tu pedido? 😁");
                    googleMap.clear();
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.addMarker(markerOptions);
                    try {
                        listener.enviarCoordenadas(latLng);
                    }catch (Exception e){
                        Toast.makeText(getContext(), "😰 ¿Dónde quieres recibir tu pedido?", Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        myLatitud = args.getDouble(LATITUD);
        myLongitud = args.getDouble(LONGITUD);
        myTitulo = args.getString(TITULO);
        mySnippet = args.getString(SNIPPET);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.listener = (NotificarCoordenadas) context;
    }
}