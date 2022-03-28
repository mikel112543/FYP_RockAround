package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.adapters.ReviewsAdapter;
import com.example.rockaroundapp.databinding.FragmentUserReviewsBinding;
import com.example.rockaroundapp.model.ArtistReview;
import com.example.rockaroundapp.viewmodel.ReviewOfArtistViewModel;
import com.example.rockaroundapp.viewmodel.VenueReviewsViewModel;

import java.util.List;

public class UserReviewsFragment extends Fragment {

    private String currentUserType;
    private ReviewOfArtistViewModel reviewOfArtistViewModel;
    private VenueReviewsViewModel venueReviewsViewModel;
    private LinearLayoutManager layoutManager;
    private NavController navController;
    private RecyclerView recyclerView;
    private String artistId;
    private String venueId;
    private List<ArtistReview> artistReviews;
    private FragmentUserReviewsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        currentUserType = getArguments().getString("currentUserType");
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_reviews, container, false);
        initRecycler();
        return binding.getRoot();
        //TODO hook up reviews with adapter and recyclerview
    }

    private void initRecycler() {
        ReviewsAdapter adapter = new ReviewsAdapter(currentUserType);
        recyclerView = requireActivity().findViewById(R.id.rv_main);
        //recyclerView.setVisibility(View.VISIBLE);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        if(currentUserType.equalsIgnoreCase("venue")) {
            assert getArguments() != null;
            artistId = getArguments().getString("artistId");
            reviewOfArtistViewModel = new ViewModelProvider(this).get(ReviewOfArtistViewModel.class);
            reviewOfArtistViewModel.getAllReviews(artistId).observe(getViewLifecycleOwner(), reviews -> {
                artistReviews = reviews;
                reviewOfArtistViewModel.get_artistReviewers(artistReviews).observe(getViewLifecycleOwner(), reviewers -> {
                    adapter.updateArtistReviewList(artistReviews, reviewers);
                });
                binding.noReviews.setVisibility(View.INVISIBLE);
                if(artistReviews.isEmpty()) {
                    binding.noReviews.setVisibility(View.VISIBLE);
                }
            });
            //adapter.updateArtistReviewList(artistReviews);
            //1. Get List of Reviews
            //2. Traverse List of Reviews and find documents of reviewers from list of reviews
            //3. Create list of reviewers and pass into adapter
            //check if viewing their own profile (pass currentUid in bundle and compare)
            //observeArtistReviews(filter)
        }else{
            venueReviewsViewModel = new ViewModelProvider(this).get(VenueReviewsViewModel.class);
            //observeVenueReviews(filter)
        }
    }
}