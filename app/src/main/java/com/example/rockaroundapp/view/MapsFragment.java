package com.example.rockaroundapp.view;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment {

    private final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private double deviceLat;
    private double deviceLong;
    private FragmentMapsBinding binding;
    private Marker locationMarker;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            if (checkPermissions()) {
                //TODO Get all artists/venues and place markers for the location
                //TODO Place markers for each location
                LatLng currentLocation = new LatLng(deviceLat, deviceLong);
                locationMarker = googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
            } else {
                LatLng ireland = new LatLng(53.14, 7.69);
                locationMarker = googleMap.addMarker(new MarkerOptions().position(ireland).title("Marker to Ireland"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(ireland));
            }

        }
    };

    private final ActivityResultLauncher<String[]> locationPermissionResults = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            results -> {
                if (results.containsValue(Boolean.FALSE)) {
                    Log.e(TAG, "AT LEAST ONE PERMISSION NOT GRANTED");
                } else {
                    Log.e(TAG, "PERMISSION GRANTED");
                }
            });

    private boolean checkPermissions() {
        locationPermissionResults.launch(permissions);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e(TAG, "AT LEAST ONE PERMISSION NOT GRANTED");
        } else {
            LocationManager lm = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            deviceLat = location.getLatitude();
            deviceLong = location.getLongitude();
            return true;
        }
        return false;
    }

    private void onLocationClick(View view) {
        locationMarker.remove();
        if (checkPermissions()) {
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            if (mapFragment != null) {
                mapFragment.getMapAsync(callback);
            }
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.locationButton.setOnClickListener(this::onLocationClick);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }
}