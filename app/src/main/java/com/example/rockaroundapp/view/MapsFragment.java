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
import androidx.lifecycle.ViewModelProvider;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentMapsBinding;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.viewmodel.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MapsFragment extends Fragment {

    private String currentUid = FirebaseAuth.getInstance().getUid();

    private final String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    private MapViewModel viewModel;
    private double deviceLat;
    private double deviceLong;
    private FragmentMapsBinding binding;
    private Marker locationMarker;
    private boolean mapReady;
    private List<Artist> artistList;
    private List<Venue> venueList;
    private String userType;
    private GoogleMap gMap;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            gMap = googleMap;
            mapReady = true;
            if (checkPermissions()) {
                LatLng currentLocation = new LatLng(deviceLat, deviceLong);
                locationMarker = googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Current Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
                if(userType != null) {
                    viewModel.saveCoordinates(deviceLat, deviceLong, userType);
                }
            } else {
                LatLng ireland = new LatLng(53.14, 7.69);
                locationMarker = googleMap.addMarker(new MarkerOptions().position(ireland).title("Marker to Ireland"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(ireland));
            }
            updateMap();
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

    public MapsFragment() {
    }

    private boolean checkPermissions() {
        locationPermissionResults.launch(permissions);
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager lm = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            deviceLat = location.getLatitude();
            deviceLong = location.getLongitude();
            return true;
        } else {
            Log.e(TAG, "AT LEAST ONE PERMISSION NOT GRANTED");
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
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        assert getArguments() != null;
        userType = getArguments().getString("currentUserType");
        getLists();
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

    private void getUserType() {
        viewModel.getUserType(currentUid).observe(getViewLifecycleOwner(), userType -> {
            this.userType = userType;
            if(checkPermissions()) {
                viewModel.saveCoordinates(deviceLat, deviceLong, userType);
            }
            getLists();
        });
    }

    private void getLists() {
        if (userType != null) {
            if (userType.equalsIgnoreCase("solo") || userType.equalsIgnoreCase("group")) {
                viewModel.getVenues().observe(getViewLifecycleOwner(), venueList -> {
                    this.venueList = venueList;
                    updateMap();
                });
            } else {
                viewModel.getArtists().observe(getViewLifecycleOwner(), artistList -> {
                    this.artistList = artistList;
                    updateMap();
                });
            }
        }
    }

    private void updateMap() {
        if (mapReady && userType != null && (artistList != null || venueList != null)) {
            if (userType.equalsIgnoreCase("venue")) {
                assert artistList != null;
                for (Artist artist : artistList) {
                    if (artist.getLatitude() != 0 && artist.getLongitude() != 0) {
                        LatLng location = new LatLng(artist.getLatitude(), artist.getLongitude());
                        locationMarker = gMap.addMarker(new MarkerOptions().position(location).title(artist.getStageName()));
                    }
                }
            }
            if (userType.equalsIgnoreCase("group") || userType.equalsIgnoreCase("solo")) {
                assert venueList != null;
                for (Venue venue : venueList) {
                    if (venue.getLatitude() != 0 && venue.getLongitude() != 0) {
                        LatLng location = new LatLng(venue.getLatitude(), venue.getLongitude());
                        locationMarker = gMap.addMarker(new MarkerOptions().position(location).title(venue.getVenueName()));
                    }
                }
            }
        }
    }
}