package com.mva.inmobiliariaalaniz.ui.inicio;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class LeerMapa implements OnMapReadyCallback {
    private LatLng INMOBILIARIA;
    private Context context;
    private GoogleMap map;

    public LeerMapa(Context context) {
        this.INMOBILIARIA= new LatLng(-33.30312, -66.34572);
        this.context= context;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        map.addMarker(new MarkerOptions()
                        .position(INMOBILIARIA))
                .setTitle("INMOBILIARIA ALANIZ");

        obtenerUltimaUbicacion();
    }
    private void obtenerUltimaUbicacion() {
        FusedLocationProviderClient fl= LocationServices.getFusedLocationProviderClient(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fl.getLastLocation().addOnSuccessListener(context.getMainExecutor(), new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng ua= new LatLng(location.getLatitude(),location.getLongitude());
                    map.addMarker(new MarkerOptions().position(ua))

                            .setTitle("Mi Ubicación");
                    CameraPosition camUlp= new CameraPosition.Builder()
                            .target(ua)
                            .zoom(15)
                            .bearing(45)
                            .tilt(70)
                            .build();
                    CameraUpdate caULP= CameraUpdateFactory.newCameraPosition(camUlp);
                    map.animateCamera(caULP);
                    map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(@NonNull LatLng latLng) {
                            Projection proj = map.getProjection();
                            android.graphics.Point coor= proj.toScreenLocation(latLng);
                            map.addMarker(new MarkerOptions().position(latLng))

                                    .setTitle("Nuevo");
                            Log.d("salida",coor.x +"   "+coor.y);

                        }
                    });
                }
            }
        });
    }

}


