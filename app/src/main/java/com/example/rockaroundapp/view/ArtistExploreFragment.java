package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.adapters.ArtistAdapter;
import com.example.rockaroundapp.databinding.FragmentArtistExploreBinding;
import com.example.rockaroundapp.listeners.ArtistListener;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.viewmodel.ArtistExploreViewModel;
import com.example.rockaroundapp.viewmodel.SoloSetupViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ArtistExploreFragment extends Fragment implements ArtistListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private FragmentArtistExploreBinding binding;
    private ArtistExploreViewModel viewModel;
    private ArtistAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_artist_explore, container, false);
        viewModel = new ViewModelProvider(this).get(ArtistExploreViewModel.class);
        View view = binding.getRoot();
        binding.setArtistExploreViewModel(viewModel);
        binding.setLifecycleOwner(this);
        recyclerView = getActivity().findViewById(R.id.rv_main);
        recyclerView.setHasFixedSize(true);
        bottomNavigationView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        toolbar.setTitle("Explore Artists");
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ArtistAdapter(this);
        recyclerView.setAdapter(adapter);
        observe();
        return view;
    }

    public void observe() {
        viewModel.getArtistList().observe(getViewLifecycleOwner(), artistList -> {
            adapter.updateArtistList(artistList);
        });
    }

    @Override
    public void onArtistClicked(Artist artist) {
        //TODO Create bundle with artistId
        //TODO navigate to user Profile with bundle
    }
}