package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.ReviewOfArtistFragmentBinding;
import com.example.rockaroundapp.viewmodel.ArtistReviewsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ReviewOfArtistFragment extends Fragment {

    private ArtistReviewsViewModel mViewModel;
    private ReviewOfArtistFragmentBinding binding;
    private NavController navController;
    private Drawable filledStar;
    private Drawable starOutline;
    private String artistId;
    private final String currentUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        filledStar = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.review_of_artist_fragment, container, false);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(ArtistReviewsViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        assert getArguments() != null;
        artistId = getArguments().getString("artistId");
        observe();
        return view;
    }

    private void observe() {
        mViewModel.get_review().observe(getViewLifecycleOwner(), artistReview -> {
            artistReview.setReliabilityRating(binding.reliabilityRatingStars.getRating());
            artistReview.setCommunicationRating(binding.communicationRatingStars.getRating());
            artistReview.setVocalsRating(binding.vocalsReviewStars.getRating());
            artistReview.setStagePresenceRating(binding.presenceReviewStars.getRating());

            if (binding.reviewTitle.getText().toString().isEmpty()) {
                binding.reviewTitle.setError("Please provide a title");
            }
            if (binding.reviewDescription.getText().toString().isEmpty()) {
                binding.reviewDescription.setError("Please write a review");
            }
            if (!(binding.reviewTitle.getText().toString().isEmpty() && binding.reviewDescription.getText().toString().isEmpty())) {
                artistReview.setReviewerId(currentUid);
                artistReview.setReviewedId(artistId);
                mViewModel.submitReview(artistReview);
            }
        });
        mViewModel.getSuccess().observe(getViewLifecycleOwner(), success -> {
            if (Boolean.TRUE.equals(success)) {
                Toast.makeText(getActivity(), "Review successful", Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }
}