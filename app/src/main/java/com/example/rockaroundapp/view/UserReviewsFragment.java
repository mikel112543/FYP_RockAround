package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.adapters.ReviewsAdapter;
import com.example.rockaroundapp.databinding.FragmentUserReviewsBinding;
import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.viewmodel.ArtistReviewsViewModel;
import com.example.rockaroundapp.viewmodel.VenueReviewsViewModel;

import java.util.List;

public class UserReviewsFragment extends Fragment {

    private String currentUserType;
    private ArtistReviewsViewModel artistReviewsViewModel;
    private VenueReviewsViewModel venueReviewsViewModel;
    private LinearLayoutManager layoutManager;
    private NavController navController;
    private RecyclerView recyclerView;
    private String artistId;
    private Toolbar toolbar;
    private ReviewsAdapter adapter;
    private List<ArtistReview> artistReviews;
    private FragmentUserReviewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        setHasOptionsMenu(true);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_reviews, container, false);
        currentUserType = getArguments().getString("currentUserType");
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        toolbar.inflateMenu(R.menu.discover_toolbar_menu);
        // Inflate the layout for this fragment
        initRecycler();
        return binding.getRoot();
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

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // position 0 - Descending
            // position 1 - Ascending
            // position 2 - Rating (High - low)
            // position 3 - Rating (Low - high)
            if(currentUserType.equalsIgnoreCase("venue")) {
                artistReviewsViewModel.sortReviews(position);
            }else{
                venueReviewsViewModel.sortReviews(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void initRecycler() {
        adapter = new ReviewsAdapter(currentUserType);
        recyclerView = requireActivity().findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        assert getArguments() != null;
        if(currentUserType.equalsIgnoreCase("venue")) {
            artistId = getArguments().getString("artistId");
            artistReviewsViewModel = new ViewModelProvider(this).get(ArtistReviewsViewModel.class);
            artistReviewsViewModel.getAllReviews(artistId).observe(getViewLifecycleOwner(), reviews -> {
                if(!reviews.isEmpty()) {
                    binding.noReviews.setVisibility(View.INVISIBLE);
                }
                adapter.updateArtistReviewList(reviews);
            });
        }else{
            String venueId = getArguments().getString("venueId");
            venueReviewsViewModel = new ViewModelProvider(this).get(VenueReviewsViewModel.class);
            venueReviewsViewModel.getAllReviews(venueId).observe(getViewLifecycleOwner(), reviews -> {
                if(!reviews.isEmpty()) {
                    binding.noReviews.setVisibility(View.INVISIBLE);
                }
                adapter.updateVenueReviews(reviews);
            });
        }
    }
}