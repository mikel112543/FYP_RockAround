package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentArtistProfileBinding;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.viewmodel.ArtistProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mysql.cj.util.StringUtils;

public class ArtistProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ArtistProfileViewModel viewModel;
    private FragmentArtistProfileBinding binding;
    private String id;
    private String currentUserType;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        setHasOptionsMenu(false);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_artist_profile, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(ArtistProfileViewModel.class);
        assert getArguments() != null;
        id = getArguments().getString("id");
        currentUserType = getArguments().getString("currentUserType");
        binding.setLifecycleOwner(this);
        observeSolo();
        observeGroup();
        checkIsReviewed();
        return view;
    }

    private void observeSolo() {
        viewModel.getSoloArtist(id).observe(getViewLifecycleOwner(), artist -> {
            binding.setArtistModel(artist);
            mapAllStars(artist);
            if (StringUtils.isEmptyOrWhitespaceOnly(artist.getProfileImg())) {
                binding.profileImage.setImageDrawable(artist.getDefaultProfiler());
            }
        });
        viewModel.getReviews(id).observe(getViewLifecycleOwner(), reviews -> {
            if (!reviews.isEmpty()) {
                binding.setArtistReview1(reviews.get(0));
                binding.noReviews.setVisibility(View.INVISIBLE);
            } else {
                binding.divider5.setVisibility(View.INVISIBLE);
                binding.noReviews.setVisibility(View.VISIBLE);
            }
            if (reviews.size() >= 2) {
                binding.setArtistReview2(reviews.get(1));
            }
        });
    }

    private void observeGroup() {
        viewModel.getGroupArtist(id).observe(getViewLifecycleOwner(), groupArtist -> {
            binding.artistMembers.setVisibility(View.VISIBLE);
            mapAllStars(groupArtist);
            binding.setArtistModel(groupArtist);
            binding.setGroupModel(groupArtist);
            if (StringUtils.isEmptyOrWhitespaceOnly(groupArtist.getProfileImg())) {
                binding.profileImage.setImageDrawable(groupArtist.getDefaultProfiler());
            }
        });
        viewModel.getReviews(id).observe(getViewLifecycleOwner(), reviews -> {
            if (!reviews.isEmpty()) {
                binding.setArtistReview1(reviews.get(0));
                binding.noReviews.setVisibility(View.INVISIBLE);
            } else {
                binding.divider5.setVisibility(View.INVISIBLE);
                binding.noReviews.setVisibility(View.VISIBLE);
            }
            if (reviews.size() >= 2) {
                binding.setArtistReview2(reviews.get(1));
            }
        });
    }

    private void mapAllStars(Artist artist) {
        mapRating(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, artist.getAvgReliabilityRating());
        mapRating(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, artist.getAvgCommunicationRating());
        mapRating(binding.vocalsS1, binding.vocalsS2, binding.vocalsS3, binding.vocalsS4, binding.vocalsS5, artist.getAvgVocalsRating());
        mapRating(binding.stagePresenceS1, binding.stagePresenceS2, binding.stagePresenceS3, binding.stagePresenceS4, binding.stagePresenceS5, artist.getAvgStagePresenceRating());
        mapRating(binding.overallRatingS1, binding.overallRatingS2, binding.overallRatingS3, binding.overallRatingS4, binding.overallRatingS5, artist.getAvgOverallRating());

    }

    private void checkIsReviewed() {
        viewModel.alreadyReviewed(id).observe(getViewLifecycleOwner(), alreadyReviewed -> {
            if (Boolean.TRUE.equals(alreadyReviewed)) {
                binding.writeReviewButton.setEnabled(false);
                binding.writeReviewButton.setText(getString(R.string.artist_already_reviewed));
            }
        });
    }

    private void onWriteReviewClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("artistId", id);
        navController.navigate(R.id.action_artistProfile_to_reviewOfArtistFragment, bundle);
    }

    private void onMoreReviewsClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("artistId", id);
        bundle.putString("currentUserType", currentUserType);
        navController.navigate(R.id.action_artistProfile_to_userReviewsFragment, bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.writeReviewButton.setOnClickListener(this::onWriteReviewClicked);
        binding.moreReviewButton.setOnClickListener(this::onMoreReviewsClicked);
        super.onViewCreated(view, savedInstanceState);
    }

    private void mapRating(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, double rating) {
        int starFilled = R.drawable.ic_baseline_star_rate_50;
        int halfStar = R.drawable.ic_baseline_star_half_24;
        int starOutline = R.drawable.ic_baseline_star_outline_24;
        if (rating == 0.0) {
            star1.setImageResource(starOutline);
            star2.setImageResource(starOutline);
            star3.setImageResource(starOutline);
            star4.setImageResource(starOutline);
            star5.setImageResource(starOutline);
        } else if (rating <= 0.50) {
            star1.setImageResource(halfStar);
        } else if (rating <= 1.00) {
            star1.setImageResource(starFilled);
        } else if (rating <= 1.50) {
            star1.setImageResource(starFilled);
            star2.setImageResource(halfStar);
        } else if (rating <= 2.00) {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
        } else if (rating <= 2.50) {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
            star3.setImageResource(halfStar);
        } else if (rating <= 3.00) {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
            star3.setImageResource(starFilled);
        } else if (rating <= 3.50) {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
            star3.setImageResource(starFilled);
            star4.setImageResource(halfStar);
        } else if (rating <= 4.00) {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
            star3.setImageResource(starFilled);
            star4.setImageResource(starFilled);
        } else if (rating <= 4.50) {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
            star3.setImageResource(starFilled);
            star4.setImageResource(starFilled);
            star5.setImageResource(halfStar);
        } else {
            star1.setImageResource(starFilled);
            star2.setImageResource(starFilled);
            star3.setImageResource(starFilled);
            star4.setImageResource(starFilled);
            star5.setImageResource(starFilled);
        }
    }
}