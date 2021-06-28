package com.example.finalassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    SupportMapFragment mapFragment;
    String add,tmp;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        add = getIntent().getStringExtra("Address");
        List<Address> addressList = null;

        mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.google_map);

        if(add != null || !add.equals(""))
        {
            Geocoder geocoder = new Geocoder(MapActivity.this);
            try {
                addressList = geocoder.getFromLocationName(add,1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = addressList.get(0);

            latLng = new LatLng(address.getLatitude(),address.getLongitude());
            tmp = latLng.toString();

        }
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(new MarkerOptions().position(latLng).title(add));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}