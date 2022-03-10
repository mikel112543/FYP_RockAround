package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentSoloProfileBinding;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.viewmodel.SoloProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SoloProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private SoloProfileViewModel viewModel;
    private FragmentSoloProfileBinding binding;
    private Artist artist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_solo_profile, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(SoloProfileViewModel.class);
        assert getArguments() != null;
        String id = getArguments().getString("id");
        binding.setLifecycleOwner(this);
        artist = viewModel.getArtist(id);
        binding.setArtistModel(artist);
        //TODO Bind to artist properly
        //TODO Add review onClicks
        return view;
    }
}