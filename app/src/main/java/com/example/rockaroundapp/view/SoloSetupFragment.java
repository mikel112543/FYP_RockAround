package com.example.rockaroundapp.view;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.rockaroundapp.R;

import com.example.rockaroundapp.databinding.FragmentSoloSetupBinding;
import com.example.rockaroundapp.viewmodel.SoloSetupViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import org.apache.log4j.Logger;

import java.util.Objects;

public class SoloSetupFragment extends Fragment {

    private Logger logger = Logger.getLogger(SoloSetupFragment.class);

    private FragmentSoloSetupBinding binding;
    private SoloSetupViewModel soloSetupViewModel;
    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private ActivityResultLauncher<Intent> getProfiler;
    private TextDrawable orgProfiler;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    //TODO Properly insert image location into Profile Image of Artist

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,R.layout.fragment_solo_setup, container, false);
        View view = binding.getRoot();
        soloSetupViewModel = new ViewModelProvider(this).get(SoloSetupViewModel.class);
        binding.setSoloSetupViewModel(soloSetupViewModel);
        binding.setLifecycleOwner(this);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        assert getArguments() != null;
        orgProfiler = TextDrawable.builder().buildRect(String.valueOf(getArguments().getString("firstName").charAt(0)), generator.getRandomColor());
        binding.profileImageView.setImageDrawable(orgProfiler);
        observeViewModel(soloSetupViewModel);

        return view;
    }

    public void observeViewModel(SoloSetupViewModel soloSetupViewModel) {
        soloSetupViewModel.getArtist().observe(getViewLifecycleOwner(), artist -> {
            if(artist.getGenres().isEmpty()) {
                binding.genresTextInputLayout.setError("You must provide a genre");
            }else {
                binding.genresTextInputLayout.setError(null);
            }
            if(artist.getContactNumber().isEmpty()) {
                binding.contactText.setError("Please provide a contact number");
            }else{
                binding.contactText.setError(null);
            }
            if(!artist.getGenres().isEmpty() && !artist.getContactNumber().isEmpty()) {
                soloSetupViewModel.saveInfo();
            }
        });
        soloSetupViewModel.getGenresStringMutable().observe(getViewLifecycleOwner(), string -> {
            if(!string.isEmpty()) {
                binding.genresTextInputLayout.setText(string);
            }else{
                binding.genresTextInputLayout.setText("");
            }
        });
        soloSetupViewModel.getSetUpSuccess().observe(getViewLifecycleOwner(), aBoolean -> {
            if(Boolean.TRUE.equals(aBoolean)) {
                Toast.makeText(getActivity(), "Setup Successful", Toast.LENGTH_SHORT).show();
                navController.navigate(R.id.action_soloSetupFragment_to_exploreFragment);
            }
        });

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.addGenreButton.setOnClickListener(this::onAddGenreClicked);
        binding.uploadImageButton.setOnClickListener(this::showGallery);
        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        setUpProfileResult();
        super.onViewCreated(view, savedInstanceState);
    }

    private void onAddGenreClicked(View view) {
        GenreDialogFragment dialogFragment = new GenreDialogFragment();
        dialogFragment.show(getChildFragmentManager(), "dialog");

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
            if(result.getResultCode() == Activity.RESULT_OK){
                try{
                    if(result.getData() != null) {
                        Uri selectedImageUri = result.getData().getData();
                        Picasso.get().load(selectedImageUri).into(binding.profileImageView);
                        soloSetupViewModel.setProfileImageUri(selectedImageUri);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}