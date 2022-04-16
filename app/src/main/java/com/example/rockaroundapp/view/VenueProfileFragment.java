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
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentVenueProfileBinding;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.viewmodel.VenueProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.mysql.cj.util.StringUtils;

import java.util.Objects;

public class VenueProfileFragment extends Fragment {

    private FragmentVenueProfileBinding binding;
    private VenueProfileViewModel viewModel;
    private Toolbar toolbar;
    private final String currentUid = FirebaseAuth.getInstance().getUid();
    private String id;
    private NavController navController;
    private Drawable starFilled;
    private Drawable starOutline;
    private Drawable halfStar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venue_profile, container, false);
        View view = binding.getRoot();
        starFilled = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24);
        halfStar = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_half_24);
        viewModel = new ViewModelProvider(this).get(VenueProfileViewModel.class);
        assert getArguments() != null;
        id = getArguments().getString("id");
        binding.setLifecycleOwner(this);
        checkIsReviewed();
        observeVenue();
        return view;
    }

    private void checkIsReviewed() {
        viewModel.getAlreadyReviewed(id).observe(getViewLifecycleOwner(), alreadyReviewed -> {
            if (Boolean.TRUE.equals(alreadyReviewed)) {
                binding.writeReviewButton.setEnabled(false);
                binding.writeReviewButton.setText(getString(R.string.venue_already_reviewed));
            }
        });
    }

    private void observeVenue() {
        viewModel.getVenue(id).observe(getViewLifecycleOwner(), venue -> {
            mapAllStars(venue);
            binding.setVenueModel(venue);
            if (StringUtils.isEmptyOrWhitespaceOnly(venue.getProfileImg())) {
                Drawable orgProfile = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_account_circle_24, null);
                binding.profileImage.setImageDrawable(orgProfile);
            }
            if (Objects.equals(currentUid, venue.getId())) {
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
            }
        });
        viewModel.getReviews(id).observe(getViewLifecycleOwner(), reviews -> {
            if (reviews.isEmpty()) {
                binding.divider5.setVisibility(View.INVISIBLE);
                binding.noReviews.setVisibility(View.VISIBLE);
            } else if (reviews.size() == 1) {
                binding.setReviewOne(reviews.get(0));
                binding.divider5.setVisibility(View.VISIBLE);
                binding.noReviews.setVisibility(View.INVISIBLE);
            } else {
                binding.setReviewOne(reviews.get(0));
                binding.setReviewTwo(reviews.get(1));
                binding.divider5.setVisibility(View.VISIBLE);
                binding.noReviews.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void onWriteReviewClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("venueId", id);
        navController.navigate(R.id.action_venueProfileFragment_to_reviewOfVenueFragment, bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.writeReviewButton.setOnClickListener(this::onWriteReviewClicked);
        super.onViewCreated(view, savedInstanceState);
    }

    private void mapAllStars(Venue venue) {
        mapRating(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, venue.getAvgAtmosphereRating());
        mapRating(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, venue.getAvgCommunicationRating());
        mapRating(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, venue.getAvgReliabilityRating());
        mapRating(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, venue.getAvgSettingRating());
        mapRating(binding.overallRatingS1, binding.overallRatingS2, binding.overallRatingS3, binding.overallRatingS4, binding.overallRatingS5, venue.getAvgOverallRating());
    }

    private void mapRating(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, double rating) {
        if (rating == 0.0) {
            star1.setBackground(starOutline);
            star2.setBackground(starOutline);
            star3.setBackground(starOutline);
            star4.setBackground(starOutline);
            star5.setBackground(starOutline);
        } else {
            if (rating <= 0.50) {
                star1.setBackground(halfStar);
            }
            if (rating > 0.50) {
                star1.setBackground(starFilled);
            }
            if (rating > 1.00 && rating <= 1.50) {
                star2.setBackground(halfStar);
            }
            if (rating > 1.50) {
                star2.setBackground(starFilled);
            }
            if (rating > 2.00 && rating <= 2.50) {
                star3.setBackground(halfStar);
            }
            if (rating > 2.50) {
                star3.setBackground(starFilled);
            }
            if (rating > 3.00 && rating <= 3.50) {
                star4.setBackground(halfStar);
            }
            if (rating > 3.50) {
                star4.setBackground(starFilled);
            }
            if (rating > 4.00 && rating <= 4.50) {
                star5.setImageDrawable(halfStar);
                //star5.setBackground(halfStar);
            }
            if (rating > 4.50) {
                star5.setBackground(starFilled);
            }
        }
    }
}