package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.rockaroundapp.databinding.ReviewOfArtistFragmentBinding;
import com.example.rockaroundapp.viewmodel.ReviewOfArtistViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReviewOfArtistFragment extends Fragment {

    private ReviewOfArtistViewModel mViewModel;
    private ReviewOfArtistFragmentBinding binding;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Drawable filledStar;
    private Drawable starOutline;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        filledStar = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.review_of_artist_fragment, container, false);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(ReviewOfArtistViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        return view;
    }

    private void observe() {
        mViewModel.get_review().observe(getViewLifecycleOwner(), artistReview -> {
            //TODO Finish Review observation
            //TODO Finish add review to repo
            //TODO Complete for Venue review
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.stagePresenceS1.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS2.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS3.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS4.setOnClickListener(this::stagePresenceOnClick);

        binding.vocalsS1.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS2.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS3.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS4.setOnClickListener(this::vocalsRatingOnClick);

        binding.communicationS1.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS2.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS3.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS4.setOnClickListener(this::communicationRatingOnClick);

        binding.reliabilityS1.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS2.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS3.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS4.setOnClickListener(this::reliabilityRatingOnClick);

        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void stagePresenceOnClick(View view) {
        switch (view.getId()) {
            case (R.id.stage_presence_s1):
                mViewModel.setStagePresenceRating(1);
                binding.stagePresenceS1.setBackground(filledStar);
                binding.stagePresenceS2.setBackground(starOutline);
                binding.stagePresenceS3.setBackground(starOutline);
                binding.stagePresenceS4.setBackground(starOutline);
                break;
            case (R.id.stage_presence_s2):
                mViewModel.setStagePresenceRating(2);
                binding.stagePresenceS1.setBackground(filledStar);
                binding.stagePresenceS2.setBackground(filledStar);
                binding.stagePresenceS3.setBackground(starOutline);
                binding.stagePresenceS4.setBackground(starOutline);
                break;
            case (R.id.stage_presence_s3):
                mViewModel.setStagePresenceRating(3);
                binding.stagePresenceS1.setBackground(filledStar);
                binding.stagePresenceS2.setBackground(filledStar);
                binding.stagePresenceS3.setBackground(filledStar);
                binding.stagePresenceS4.setBackground(starOutline);
                break;
            case (R.id.stage_presence_s4):
                mViewModel.setStagePresenceRating(4);
                binding.stagePresenceS1.setBackground(filledStar);
                binding.stagePresenceS2.setBackground(filledStar);
                binding.stagePresenceS3.setBackground(filledStar);
                binding.stagePresenceS4.setBackground(filledStar);
                break;
            default:
                mViewModel.setStagePresenceRating(0);
                binding.stagePresenceS1.setBackground(filledStar);
                binding.stagePresenceS2.setBackground(starOutline);
                binding.stagePresenceS3.setBackground(starOutline);
                binding.stagePresenceS4.setBackground(starOutline);
        }
    }

    private void vocalsRatingOnClick(View view) {
        switch (view.getId()) {
            case (R.id.vocals_s1):
                mViewModel.setVocalsRating(1);
                binding.vocalsS1.setBackground(filledStar);
                binding.vocalsS2.setBackground(starOutline);
                binding.vocalsS3.setBackground(starOutline);
                binding.vocalsS4.setBackground(starOutline);
                break;
            case (R.id.vocals_s2):
                mViewModel.setVocalsRating(2);
                binding.vocalsS1.setBackground(filledStar);
                binding.vocalsS2.setBackground(filledStar);
                binding.vocalsS3.setBackground(starOutline);
                binding.vocalsS4.setBackground(starOutline);
                break;
            case (R.id.vocals_s3):
                mViewModel.setVocalsRating(3);
                binding.vocalsS1.setBackground(filledStar);
                binding.vocalsS2.setBackground(filledStar);
                binding.vocalsS3.setBackground(filledStar);
                binding.vocalsS4.setBackground(starOutline);
                break;
            case (R.id.vocals_s4):
                mViewModel.setVocalsRating(4);
                binding.vocalsS1.setBackground(filledStar);
                binding.vocalsS2.setBackground(filledStar);
                binding.vocalsS3.setBackground(filledStar);
                binding.vocalsS4.setBackground(filledStar);
                break;
            default:
                mViewModel.setVocalsRating(0);
                binding.vocalsS1.setBackground(starOutline);
                binding.vocalsS2.setBackground(starOutline);
                binding.vocalsS3.setBackground(starOutline);
                binding.vocalsS4.setBackground(starOutline);
                break;
        }
    }

    private void communicationRatingOnClick(View view) {
        switch (view.getId()) {
            case (R.id.communication_s1):
                mViewModel.setCommunicationRating(1);
                binding.communicationS1.setBackground(filledStar);
                binding.communicationS2.setBackground(starOutline);
                binding.communicationS3.setBackground(starOutline);
                binding.communicationS4.setBackground(starOutline);
                break;
            case (R.id.communication_s2):
                mViewModel.setCommunicationRating(2);
                binding.communicationS1.setBackground(filledStar);
                binding.communicationS2.setBackground(filledStar);
                binding.communicationS3.setBackground(starOutline);
                binding.communicationS4.setBackground(starOutline);
                break;
            case (R.id.communication_s3):
                mViewModel.setCommunicationRating(3);
                binding.communicationS1.setBackground(filledStar);
                binding.communicationS2.setBackground(filledStar);
                binding.communicationS3.setBackground(filledStar);
                binding.communicationS4.setBackground(starOutline);
                break;
            case (R.id.communication_s4):
                mViewModel.setCommunicationRating(4);
                binding.communicationS1.setBackground(filledStar);
                binding.communicationS2.setBackground(filledStar);
                binding.communicationS3.setBackground(filledStar);
                binding.communicationS4.setBackground(filledStar);
                break;
            default:
                mViewModel.setCommunicationRating(0);
                binding.communicationS1.setBackground(starOutline);
                binding.communicationS2.setBackground(starOutline);
                binding.communicationS3.setBackground(starOutline);
                binding.communicationS4.setBackground(starOutline);
                break;
        }
    }

    private void reliabilityRatingOnClick(View view) {
        switch (view.getId()) {
            case (R.id.reliability_s1):
                mViewModel.setReliabilityRating(1);
                binding.reliabilityS1.setBackground(filledStar);
                binding.reliabilityS2.setBackground(starOutline);
                binding.reliabilityS3.setBackground(starOutline);
                binding.reliabilityS4.setBackground(starOutline);
                break;
            case (R.id.reliability_s2):
                mViewModel.setReliabilityRating(2);
                binding.reliabilityS1.setBackground(filledStar);
                binding.reliabilityS2.setBackground(filledStar);
                binding.reliabilityS3.setBackground(starOutline);
                binding.reliabilityS4.setBackground(starOutline);
                break;
            case (R.id.communication_s3):
                mViewModel.setReliabilityRating(3);
                binding.reliabilityS1.setBackground(filledStar);
                binding.reliabilityS2.setBackground(filledStar);
                binding.reliabilityS3.setBackground(filledStar);
                binding.reliabilityS4.setBackground(starOutline);
                break;
            case (R.id.reliability_s4):
                mViewModel.setReliabilityRating(4);
                binding.reliabilityS1.setBackground(filledStar);
                binding.reliabilityS2.setBackground(filledStar);
                binding.reliabilityS3.setBackground(filledStar);
                binding.reliabilityS4.setBackground(filledStar);
                break;
            default:
                mViewModel.setReliabilityRating(0);
                binding.reliabilityS1.setBackground(starOutline);
                binding.reliabilityS2.setBackground(starOutline);
                binding.reliabilityS3.setBackground(starOutline);
                binding.reliabilityS4.setBackground(starOutline);
                break;
        }
    }
}