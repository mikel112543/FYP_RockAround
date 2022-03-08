package com.example.rockaroundapp.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentVenueSetupBinding;
import com.example.rockaroundapp.viewmodel.VenueSetupViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

public class VenueSetupFragment extends Fragment {

    private FragmentVenueSetupBinding binding;
    private VenueSetupViewModel venueSetupViewModel;
    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private ActivityResultLauncher<Intent> getProfiler;
    private TextDrawable orgProfiler;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private String userType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_venue_setup, container, false);
        View view = binding.getRoot();
        venueSetupViewModel = new ViewModelProvider(this).get(VenueSetupViewModel.class);
        binding.setVenueSetupViewModel(venueSetupViewModel);
        binding.setLifecycleOwner(this);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Venue Setup");
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        /*orgProfiler = TextDrawable.builder().buildRect(String.valueOf(getArguments().getString("firstName").charAt(0)), generator.getRandomColor());
        binding.profileImageView.setImageDrawable(orgProfiler);
        assert getArguments() != null;
        userType = getArguments().getString("userType");*/
        observeViewModel();
        return view;
    }

    private void observeViewModel() {
        venueSetupViewModel.getVenueMutableLiveData().observe(getViewLifecycleOwner(), venue -> {
            if (venue.getVenueName().isEmpty()) {
                binding.venueNameText.setError("Please provide a name");
            } else {
                binding.venueNameText.setError(null);
            }
            if (venue.getCapacity() == 0) {
                binding.capacityText.setError("Please provide a rough capacity");
            } else {
                binding.capacityText.setError(null);
            }
            if (venue.getContact().isEmpty()) {
                binding.numberText.setError("Please provide a contact number");
            } else {
                binding.numberText.setError(null);
            }
            if (venue.getAddressLineOne().isEmpty()) {
                binding.addressLineOne.setError("Please provide an address");
            } else {
                binding.addressLineOne.setError(null);
            }
            if (venue.getAddressLineTwo().isEmpty()) {
                binding.addressLineTwo.setError("Please provide an address");
            } else {
                binding.addressLineTwo.setError(null);
            }
            if (venue.getCity().isEmpty()) {
                binding.city.setError("Please provide a city");
            } else {
                binding.city.setError(null);
            }
            if (venue.getCounty().isEmpty()) {
                binding.county.setError("Please provide a county");
            } else {
                binding.county.setError(null);
            }
            if (venue.getCountry().isEmpty()) {
                binding.country.setError("Please provide a country");
            } else {
                binding.country.setError(null);
            }
            if (!binding.venueNameText.isErrorEnabled() && !binding.capacityText.isErrorEnabled()
                    && !binding.addressLineOne.isErrorEnabled() && !binding.addressLineTwo.isErrorEnabled()
                    && !binding.city.isErrorEnabled() && !binding.county.isErrorEnabled()
                    && !binding.country.isErrorEnabled() && !binding.numberText.isErrorEnabled()) {
                venueSetupViewModel.saveInfo();
            }
        });
        venueSetupViewModel.getSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getActivity(), "Profile Setup Successful", Toast.LENGTH_SHORT).show();
                //navController
            }
        });
    }

    private void showGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        //intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        Intent.createChooser(intent, "Select your profile image");
        getProfiler.launch(intent);
    }

    public void setUpProfileResult() {
        getProfiler = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                try {
                    if (result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        Picasso.get().load(selectedImageUri).into(binding.profileImageView);
                        venueSetupViewModel.setProfileImageUri(selectedImageUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.uploadImageButton.setOnClickListener(this::showGallery);
        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        setUpProfileResult();
        super.onViewCreated(view, savedInstanceState);
    }
}