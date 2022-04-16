package com.example.rockaroundapp.view;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.adapters.ArtistAdapter;
import com.example.rockaroundapp.adapters.VenueAdapter;
import com.example.rockaroundapp.databinding.FragmentDiscoverBinding;
import com.example.rockaroundapp.listeners.ArtistListener;
import com.example.rockaroundapp.listeners.VenueListener;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.viewmodel.DiscoverViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DiscoverFragment extends Fragment implements ArtistListener, VenueListener {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private FragmentDiscoverBinding binding;
    private DiscoverViewModel viewModel;
    private ArtistAdapter artistAdapter;
    private VenueAdapter venueAdapter;
    private RecyclerView recyclerView;
    public AppBarConfiguration configuration;
    private NavController navController;
    private Parcelable state;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private LinearLayoutManager layoutManager;
    private String userType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        userType = getArguments().getString("userType");
        viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navbar);
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        recyclerView = requireActivity().findViewById(R.id.rv_main);
        bottomNavigationView.setVisibility(View.VISIBLE);
        //bottomNavigationView.setSelectedItemId(R.id.discover_btn);
        toolbar.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.discover_toolbar_menu);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover, container, false);
        View view = binding.getRoot();
        recyclerView.setVisibility(View.VISIBLE);
        layoutManager.onRestoreInstanceState(state);
        configuration = new AppBarConfiguration.Builder(R.id.discover, R.id.mapsFragment, R.id.account).build();
        initRecycler(userType);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.discover_toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.filter);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filters, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerListener);
    }

    private void initRecycler(String userType) {
        if (userType.equalsIgnoreCase("solo") || userType.equalsIgnoreCase("group")) {
            toolbar.setTitle("Explore Venues");
            venueAdapter = new VenueAdapter(this);
            venueAdapter.setStateRestorationPolicy(PREVENT_WHEN_EMPTY);
            recyclerView.setAdapter(venueAdapter);
            observeVenues();
        } else {
            toolbar.setTitle("Explore Artists");
            artistAdapter = new ArtistAdapter(this);
            artistAdapter.setStateRestorationPolicy(PREVENT_WHEN_EMPTY);
            recyclerView.setAdapter(artistAdapter);
            observeArtists();
        }
    }

    private void observeArtists() {
        viewModel.getArtistList().observe(getViewLifecycleOwner(), artistList -> artistAdapter.updateArtistList(artistList));
    }

    private void observeVenues() {
        viewModel.getVenueList().observe(getViewLifecycleOwner(), venues -> venueAdapter.updateVenueList(venues));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, configuration);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onArtistClicked(Artist artist) {
        Bundle bundle = new Bundle();
        bundle.putString("id", artist.getId());
        bundle.putString("currentUserType", userType);
        recyclerView.setVisibility(View.INVISIBLE);
        navController.navigate(R.id.action_artistExploreFragment_to_soloProfileFragment, bundle);
    }

    @Override
    public void onVenueClicked(Venue venue) {
        Bundle bundle = new Bundle();
        bundle.putString("id", venue.getId());
        bundle.putString("currentUserType", userType);
        recyclerView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        navController.navigate(R.id.action_discover_to_venueProfileFragment, bundle);
    }

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // position 0 - Descending
            // position 1 - Ascending
            // position 2 - Rating (High - low)
            // position 3 - Rating (Low - high)
            viewModel.sortList(position, userType);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

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
        state = layoutManager.onSaveInstanceState();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        state = layoutManager.onSaveInstanceState();
    }
}