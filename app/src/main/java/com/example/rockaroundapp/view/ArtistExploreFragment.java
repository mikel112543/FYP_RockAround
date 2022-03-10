package com.example.rockaroundapp.view;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.ALLOW;
import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT;
import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.adapters.ArtistAdapter;
import com.example.rockaroundapp.databinding.FragmentArtistExploreBinding;
import com.example.rockaroundapp.listeners.ArtistListener;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.viewmodel.ArtistExploreViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ArtistExploreFragment extends Fragment implements ArtistListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private FragmentArtistExploreBinding binding;
    private ArtistExploreViewModel viewModel;
    private ArtistAdapter adapter;
    private RecyclerView recyclerView;
    private NavController navController;

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
        adapter.setStateRestorationPolicy(PREVENT_WHEN_EMPTY);
        recyclerView.setVisibility(View.VISIBLE);
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onArtistClicked(Artist artist) {
        Bundle id = new Bundle();
        id.putString("id", artist.getId());
        Toast.makeText(getActivity(), "ID of artist" + artist.getId(), Toast.LENGTH_SHORT).show();
        if (artist.getUserType() == "SOLO") {
            recyclerView.setVisibility(View.INVISIBLE);
            navController.navigate(R.id.action_artistExploreFragment_to_soloProfileFragment, id);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}