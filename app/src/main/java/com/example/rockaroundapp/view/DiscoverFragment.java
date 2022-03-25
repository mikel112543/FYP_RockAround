package com.example.rockaroundapp.view;

import static androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY;

import android.graphics.drawable.Drawable;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import com.example.rockaroundapp.databinding.FragmentArtistExploreBinding;
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
    private FragmentArtistExploreBinding binding;
    private DiscoverViewModel viewModel;
    private ArtistAdapter artistAdapter;
    private VenueAdapter venueAdapter;
    private RecyclerView recyclerView;
    public AppBarConfiguration configuration;
    private NavController navController;
    private Parcelable state;
    private Drawable starOutline;
    private Drawable starFilled;
    private Drawable halfStar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private LinearLayoutManager layoutManager;
    private String userType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        userType = getArguments().getString("userType");
        viewModel = new ViewModelProvider(this).get(DiscoverViewModel.class);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navbar);
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        recyclerView = requireActivity().findViewById(R.id.rv_main);
        bottomNavigationView.setVisibility(View.VISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.toolbar_menu);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_explore, container, false);
        View view = binding.getRoot();
        initRecycler(userType);
        recyclerView.setVisibility(View.VISIBLE);
        layoutManager.onRestoreInstanceState(state);
        configuration = new AppBarConfiguration.Builder(R.id.discover, R.id.account).build();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.filter);
        Spinner spinner = (Spinner) item.getActionView();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.filters, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerListener);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            auth.signOut();
            navController.navigate(R.id.loginFragment);
            recyclerView.setVisibility(View.INVISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initRecycler(String userType) {
        if (userType == "SOLO" || userType == "GROUP") {
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
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController, configuration);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onArtistClicked(Artist artist) {
        Bundle id = new Bundle();
        id.putString("id", artist.getId());
        Toast.makeText(getActivity(), "ID of artist" + artist.getId(), Toast.LENGTH_SHORT).show();
        recyclerView.setVisibility(View.INVISIBLE);
        navController.navigate(R.id.action_artistExploreFragment_to_soloProfileFragment, id);
    }

    @Override
    public void onVenueClicked(Venue venue) {
        Bundle id = new Bundle();
        id.putString("id", venue.getId());
        Toast.makeText(getActivity(), "ID of venue" + venue.getId(), Toast.LENGTH_SHORT).show();
        recyclerView.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        navController.navigate(R.id.action_discover_to_venueProfileFragment, id);
        //TODO Venue Profile
        //TODO Venue & Artist Reviews
        //TODO Account Page
        // Maps Page
    }

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            String selected = parent.getItemAtPosition(position).toString();
            Toast.makeText(getActivity(), selected + " selected", Toast.LENGTH_SHORT).show();

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