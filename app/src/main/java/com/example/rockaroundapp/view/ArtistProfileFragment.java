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

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentArtistProfileBinding;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.viewmodel.ArtistProfileViewModel;
import com.example.rockaroundapp.viewmodel.ReviewOfArtistViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mysql.cj.util.StringUtils;

public class ArtistProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ArtistProfileViewModel viewModel;
    private FragmentArtistProfileBinding binding;
    private String id;
    private NavController navController;
    private Drawable starOutline;
    private Drawable starFilled;
    private Drawable halfStar;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        starFilled = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24);
        halfStar = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_half_24);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_artist_profile, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(ArtistProfileViewModel.class);
        assert getArguments() != null;
        id = getArguments().getString("id");
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
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
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

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.writeReviewButton.setOnClickListener(this::onWriteReviewClicked);
        binding.moreReviewButton.setOnClickListener();
        super.onViewCreated(view, savedInstanceState);
    }

    private void mapRating(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, double rating) {
        if (rating == 0.0) {
            star1.setBackground(starOutline);
            star2.setBackground(starOutline);
            star3.setBackground(starOutline);
            star4.setBackground(starOutline);
            star5.setBackground(starOutline);
        } else if (rating <= 0.50) {
            star1.setBackground(halfStar);
        } else if (rating <= 1.00) {
            star1.setBackground(starFilled);
        } else if (rating <= 1.50) {
            star1.setBackground(starFilled);
            star2.setBackground(halfStar);
        } else if (rating <= 2.00) {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
        } else if (rating <= 2.50) {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
            star3.setBackground(halfStar);
        } else if (rating <= 3.00) {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
            star3.setBackground(starFilled);
        } else if (rating <= 3.50) {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
            star3.setBackground(starFilled);
            star4.setBackground(halfStar);
        } else if (rating <= 4.00) {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
            star3.setBackground(starFilled);
            star4.setBackground(starFilled);
        } else if (rating <= 4.50) {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
            star3.setBackground(starFilled);
            star4.setBackground(starFilled);
            star5.setBackground(halfStar);
        } else {
            star1.setBackground(starFilled);
            star2.setBackground(starFilled);
            star3.setBackground(starFilled);
            star4.setBackground(starFilled);
            star5.setBackground(starFilled);
        }
    }

    //TODO Implement account page
    //TODO Implement Maps Page
    //TODO Fully implement Reviews
}