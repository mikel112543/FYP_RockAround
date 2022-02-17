package com.example.rockaroundapp.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockaroundapp.R;

import com.example.rockaroundapp.databinding.FragmentSoloSetupBinding;
import com.example.rockaroundapp.viewmodel.LoginViewModel;
import com.example.rockaroundapp.viewmodel.SoloSetupViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.log4j.Logger;
import org.apache.log4j.chainsaw.Main;
import org.apache.log4j.spi.LoggerFactory;

public class SoloSetupFragment extends Fragment {

    private Logger logger = Logger.getLogger(SoloSetupFragment.class);

    private FragmentSoloSetupBinding binding;
    private SoloSetupViewModel soloSetupViewModel;
    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

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
        return view;
    }

    public void observeViewModel(SoloSetupViewModel soloSetupViewModel) {
        soloSetupViewModel.getArtist().observe(getViewLifecycleOwner(), artist -> {
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.addGenreButton.setOnClickListener(this::onAddGenreClicked);
        navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
        super.onViewCreated(view, savedInstanceState);
    }

    private void onAddGenreClicked(View view) {
        GenreDialogFragment dialogFragment = new GenreDialogFragment();
        try {
            dialogFragment.show(getChildFragmentManager(), "dialog");
        }catch (Exception e) {
            logger.error(e.getMessage());
        }
        //navController.navigate(R.id.action_soloSetupFragment_to_genreDialog);
            //TODO finish onClick for adding Genre
            //TODO add onClick for Submission
            //TODO add profile image insertion
            //TODO add multi Image insertion

    }
}