package com.example.smarthelmet;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.smarthelmet.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    Context c = this;
    NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPerim();

        manager = getSystemService(NotificationManager.class);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("ch1","Location channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        MyReceiver receiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.setPriority(2147483647);
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(receiver, intentFilter);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    void checkPerim(){

       int loc = ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION);
       int readSms = ActivityCompat.checkSelfPermission(c,Manifest.permission.READ_SMS);
       int smsRec= ActivityCompat.checkSelfPermission(c,Manifest.permission.RECEIVE_SMS);

       if (loc != PackageManager.PERMISSION_GRANTED || readSms != PackageManager.PERMISSION_GRANTED || smsRec != PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(MapsActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},101);
       }

    }
}