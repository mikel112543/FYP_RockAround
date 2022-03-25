package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import com.google.firebase.auth.FirebaseAuth;

public class ReviewOfArtistFragment extends Fragment {

    private ReviewOfArtistViewModel mViewModel;
    private ReviewOfArtistFragmentBinding binding;
    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private Drawable filledStar;
    private Drawable starOutline;
    private String artistId;
    private String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

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
        assert getArguments() != null;
        artistId = getArguments().getString("artistId");
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        bottomNavigationView = requireActivity().findViewById(R.id.bottom_navbar);
        observe();
        return view;
    }

    private void observe() {
        mViewModel.get_review().observe(getViewLifecycleOwner(), artistReview -> {
            if(binding.reviewTitle.getText().toString().isEmpty()) {
                binding.reviewTitle.setError("Please provide a title");
            }
            if(binding.reviewDescription.getText().toString().isEmpty()) {
                binding.reviewDescription.setError("Please write a review");
            }
            if(!(binding.reviewTitle.getText().toString().isEmpty() && binding.reviewDescription.getText().toString().isEmpty())) {
                artistReview.setReviewerId(currentUid);
                artistReview.setReviewedId(artistId);
                mViewModel.submitReview(artistReview);
            }
        });
        mViewModel.getSuccess().observe(getViewLifecycleOwner(), success -> {
            if(Boolean.TRUE.equals(success)) {
                Toast.makeText(getActivity(), "Review successful", Toast.LENGTH_SHORT).show();
                navController.navigateUp();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.stagePresenceS1.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS2.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS3.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS4.setOnClickListener(this::stagePresenceOnClick);
        binding.stagePresenceS5.setOnClickListener(this::stagePresenceOnClick);

        binding.vocalsS1.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS2.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS3.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS4.setOnClickListener(this::vocalsRatingOnClick);
        binding.vocalsS5.setOnClickListener(this::vocalsRatingOnClick);

        binding.communicationS1.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS2.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS3.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS4.setOnClickListener(this::communicationRatingOnClick);
        binding.communicationS5.setOnClickListener(this::communicationRatingOnClick);

        binding.reliabilityS1.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS2.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS3.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS4.setOnClickListener(this::reliabilityRatingOnClick);
        binding.reliabilityS5.setOnClickListener(this::reliabilityRatingOnClick);

        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }

    private void stagePresenceOnClick(View view) {
        switch (view.getId()) {
            case (R.id.stage_presence_s1):
                if (binding.stagePresenceS1.getBackground() == filledStar && binding.stagePresenceS2.getBackground() == starOutline) {
                    mViewModel.setStagePresenceRating(0);
                    binding.stagePresenceS1.setBackground(starOutline);
                } else {
                    mViewModel.setStagePresenceRating(1);
                    binding.stagePresenceS1.setBackground(filledStar);
                    binding.stagePresenceS2.setBackground(starOutline);
                    binding.stagePresenceS3.setBackground(starOutline);
                    binding.stagePresenceS4.setBackground(starOutline);
                    binding.stagePresenceS5.setBackground(starOutline);
                }
                break;
            case (R.id.stage_presence_s2):
                if (binding.stagePresenceS2.getBackground() == filledStar && binding.stagePresenceS3.getBackground() == starOutline) {
                    mViewModel.setStagePresenceRating(0);
                    binding.stagePresenceS1.setBackground(starOutline);
                    binding.stagePresenceS2.setBackground(starOutline);
                } else {
                    mViewModel.setStagePresenceRating(2);
                    binding.stagePresenceS1.setBackground(filledStar);
                    binding.stagePresenceS2.setBackground(filledStar);
                    binding.stagePresenceS3.setBackground(starOutline);
                    binding.stagePresenceS4.setBackground(starOutline);
                    binding.stagePresenceS5.setBackground(starOutline);
                }
                break;
            case (R.id.stage_presence_s3):
                if (binding.stagePresenceS3.getBackground() == filledStar && binding.stagePresenceS4.getBackground() == starOutline) {
                    mViewModel.setStagePresenceRating(0);
                    binding.stagePresenceS1.setBackground(starOutline);
                    binding.stagePresenceS2.setBackground(starOutline);
                    binding.stagePresenceS3.setBackground(starOutline);
                } else {
                    mViewModel.setStagePresenceRating(3);
                    binding.stagePresenceS1.setBackground(filledStar);
                    binding.stagePresenceS2.setBackground(filledStar);
                    binding.stagePresenceS3.setBackground(filledStar);
                    binding.stagePresenceS4.setBackground(starOutline);
                    binding.stagePresenceS5.setBackground(starOutline);
                }
                break;
            case (R.id.stage_presence_s4):
                if (binding.stagePresenceS4.getBackground() == filledStar && binding.stagePresenceS5.getBackground() == starOutline) {
                    mViewModel.setStagePresenceRating(4);
                    binding.stagePresenceS1.setBackground(starOutline);
                    binding.stagePresenceS2.setBackground(starOutline);
                    binding.stagePresenceS3.setBackground(starOutline);
                    binding.stagePresenceS4.setBackground(starOutline);
                } else {
                    mViewModel.setStagePresenceRating(4);
                    binding.stagePresenceS1.setBackground(filledStar);
                    binding.stagePresenceS2.setBackground(filledStar);
                    binding.stagePresenceS3.setBackground(filledStar);
                    binding.stagePresenceS4.setBackground(filledStar);
                    binding.stagePresenceS5.setBackground(starOutline);
                }
                break;
            case (R.id.stage_presence_s5):
                if (binding.stagePresenceS5.getBackground() == filledStar) {
                    mViewModel.setStagePresenceRating(0);
                    binding.stagePresenceS1.setBackground(starOutline);
                    binding.stagePresenceS2.setBackground(starOutline);
                    binding.stagePresenceS3.setBackground(starOutline);
                    binding.stagePresenceS4.setBackground(starOutline);
                    binding.stagePresenceS5.setBackground(starOutline);
                } else {
                    mViewModel.setStagePresenceRating(5);
                    binding.stagePresenceS1.setBackground(filledStar);
                    binding.stagePresenceS2.setBackground(filledStar);
                    binding.stagePresenceS3.setBackground(filledStar);
                    binding.stagePresenceS4.setBackground(filledStar);
                    binding.stagePresenceS5.setBackground(filledStar);
                }
                break;
            default:
                mViewModel.setStagePresenceRating(0);
                binding.stagePresenceS1.setBackground(starOutline);
                binding.stagePresenceS2.setBackground(starOutline);
                binding.stagePresenceS3.setBackground(starOutline);
                binding.stagePresenceS4.setBackground(starOutline);
                binding.stagePresenceS5.setBackground(starOutline);
        }
    }

    private void vocalsRatingOnClick(View view) {
        switch (view.getId()) {
            case (R.id.vocals_s1):
                if (binding.vocalsS1.getBackground() == filledStar && binding.vocalsS2.getBackground() == starOutline) {
                    mViewModel.setVocalsRating(0);
                    binding.vocalsS1.setBackground(starOutline);
                } else {
                    mViewModel.setVocalsRating(1);
                    binding.vocalsS1.setBackground(filledStar);
                    binding.vocalsS2.setBackground(starOutline);
                    binding.vocalsS3.setBackground(starOutline);
                    binding.vocalsS4.setBackground(starOutline);
                }
                break;
            case (R.id.vocals_s2):
                if (binding.vocalsS2.getBackground() == filledStar && binding.vocalsS3.getBackground() == starOutline) {
                    mViewModel.setVocalsRating(0);
                    binding.vocalsS1.setBackground(starOutline);
                    binding.vocalsS2.setBackground(starOutline);
                } else {
                    mViewModel.setVocalsRating(2);
                    binding.vocalsS1.setBackground(filledStar);
                    binding.vocalsS2.setBackground(filledStar);
                    binding.vocalsS3.setBackground(starOutline);
                    binding.vocalsS4.setBackground(starOutline);
                    binding.vocalsS5.setBackground(starOutline);
                }
                break;
            case (R.id.vocals_s3):
                if (binding.vocalsS3.getBackground() == filledStar && binding.vocalsS4.getBackground() == starOutline) {
                    mViewModel.setVocalsRating(0);
                    binding.vocalsS1.setBackground(starOutline);
                    binding.vocalsS2.setBackground(starOutline);
                    binding.vocalsS3.setBackground(starOutline);
                } else {
                    mViewModel.setVocalsRating(3);
                    binding.vocalsS1.setBackground(filledStar);
                    binding.vocalsS2.setBackground(filledStar);
                    binding.vocalsS3.setBackground(filledStar);
                    binding.vocalsS4.setBackground(starOutline);
                    binding.vocalsS5.setBackground(starOutline);
                }
                break;
            case (R.id.vocals_s4):
                if (binding.vocalsS4.getBackground() == filledStar && binding.vocalsS5.getBackground() == starOutline) {
                    mViewModel.setVocalsRating(0);
                    binding.vocalsS1.setBackground(starOutline);
                    binding.vocalsS2.setBackground(starOutline);
                    binding.vocalsS3.setBackground(starOutline);
                    binding.vocalsS4.setBackground(starOutline);
                } else {
                    mViewModel.setVocalsRating(4);
                    binding.vocalsS1.setBackground(filledStar);
                    binding.vocalsS2.setBackground(filledStar);
                    binding.vocalsS3.setBackground(filledStar);
                    binding.vocalsS4.setBackground(filledStar);
                    binding.vocalsS5.setBackground(starOutline);
                }
                break;
            case (R.id.vocals_s5):
                if (binding.vocalsS4.getBackground() == filledStar) {
                    mViewModel.setVocalsRating(0);
                    binding.vocalsS1.setBackground(starOutline);
                    binding.vocalsS2.setBackground(starOutline);
                    binding.vocalsS3.setBackground(starOutline);
                    binding.vocalsS4.setBackground(starOutline);
                    binding.vocalsS5.setBackground(starOutline);
                } else {
                    mViewModel.setVocalsRating(5);
                    binding.vocalsS1.setBackground(filledStar);
                    binding.vocalsS2.setBackground(filledStar);
                    binding.vocalsS3.setBackground(filledStar);
                    binding.vocalsS4.setBackground(filledStar);
                    binding.vocalsS5.setBackground(filledStar);
                }
                break;
            default:
                mViewModel.setVocalsRating(0);
                binding.vocalsS1.setBackground(starOutline);
                binding.vocalsS2.setBackground(starOutline);
                binding.vocalsS3.setBackground(starOutline);
                binding.vocalsS4.setBackground(starOutline);
                binding.vocalsS5.setBackground(starOutline);
                break;
        }
    }

    private void communicationRatingOnClick(View view) {
        switch (view.getId()) {
            case (R.id.communication_s1):
                if (binding.communicationS1.getBackground() == filledStar && binding.communicationS2.getBackground() == starOutline) {
                    mViewModel.setCommunicationRating(0);
                    binding.communicationS1.setBackground(starOutline);
                } else {
                    mViewModel.setCommunicationRating(1);
                    binding.communicationS1.setBackground(filledStar);
                    binding.communicationS2.setBackground(starOutline);
                    binding.communicationS3.setBackground(starOutline);
                    binding.communicationS4.setBackground(starOutline);
                    binding.communicationS5.setBackground(starOutline);
                }
                break;
            case (R.id.communication_s2):
                if (binding.communicationS2.getBackground() == filledStar && binding.communicationS3.getBackground() == starOutline) {
                    mViewModel.setCommunicationRating(0);
                    binding.communicationS1.setBackground(starOutline);
                    binding.communicationS2.setBackground(starOutline);
                } else {
                    mViewModel.setCommunicationRating(2);
                    binding.communicationS1.setBackground(filledStar);
                    binding.communicationS2.setBackground(filledStar);
                    binding.communicationS3.setBackground(starOutline);
                    binding.communicationS4.setBackground(starOutline);
                    binding.communicationS5.setBackground(starOutline);
                }
                break;
            case (R.id.communication_s3):
                if (binding.communicationS3.getBackground() == filledStar && binding.communicationS4.getBackground() == starOutline) {
                    mViewModel.setCommunicationRating(0);
                    binding.communicationS1.setBackground(starOutline);
                    binding.communicationS2.setBackground(starOutline);
                    binding.communicationS3.setBackground(starOutline);
                }else{
                    mViewModel.setCommunicationRating(3);
                    binding.communicationS1.setBackground(filledStar);
                    binding.communicationS2.setBackground(filledStar);
                    binding.communicationS3.setBackground(filledStar);
                    binding.communicationS4.setBackground(starOutline);
                    binding.communicationS5.setBackground(starOutline);
                }
                break;
            case (R.id.communication_s4):
                if (binding.communicationS4.getBackground() == filledStar && binding.communicationS5.getBackground() == starOutline) {
                    mViewModel.setCommunicationRating(0);
                    binding.communicationS1.setBackground(starOutline);
                    binding.communicationS2.setBackground(starOutline);
                    binding.communicationS3.setBackground(starOutline);
                    binding.communicationS4.setBackground(starOutline);
                }else {
                    mViewModel.setCommunicationRating(4);
                    binding.communicationS1.setBackground(filledStar);
                    binding.communicationS2.setBackground(filledStar);
                    binding.communicationS3.setBackground(filledStar);
                    binding.communicationS4.setBackground(filledStar);
                    binding.communicationS5.setBackground(starOutline);
                }
                break;
            case (R.id.communication_s5):
                if (binding.communicationS5.getBackground() == filledStar) {
                    mViewModel.setCommunicationRating(0);
                    binding.communicationS1.setBackground(starOutline);
                    binding.communicationS2.setBackground(starOutline);
                    binding.communicationS3.setBackground(starOutline);
                    binding.communicationS4.setBackground(starOutline);
                    binding.communicationS5.setBackground(starOutline);
                } else {
                    mViewModel.setCommunicationRating(5);
                    binding.communicationS1.setBackground(filledStar);
                    binding.communicationS2.setBackground(filledStar);
                    binding.communicationS3.setBackground(filledStar);
                    binding.communicationS4.setBackground(filledStar);
                    binding.communicationS5.setBackground(filledStar);
                }
                break;
            default:
                mViewModel.setCommunicationRating(0);
                binding.communicationS1.setBackground(starOutline);
                binding.communicationS2.setBackground(starOutline);
                binding.communicationS3.setBackground(starOutline);
                binding.communicationS4.setBackground(starOutline);
                binding.communicationS5.setBackground(starOutline);
                break;
        }
    }

    private void reliabilityRatingOnClick(View view) {
        switch (view.getId()) {
            case (R.id.reliability_s1):
                if (binding.reliabilityS1.getBackground() == filledStar && binding.reliabilityS2.getBackground() == starOutline) {
                    mViewModel.setReliabilityRating(0);
                    binding.reliabilityS1.setBackground(starOutline);
                    binding.reliabilityS2.setBackground(starOutline);
                } else {
                    mViewModel.setReliabilityRating(1);
                    binding.reliabilityS1.setBackground(filledStar);
                    binding.reliabilityS2.setBackground(starOutline);
                    binding.reliabilityS3.setBackground(starOutline);
                    binding.reliabilityS4.setBackground(starOutline);
                    binding.reliabilityS5.setBackground(starOutline);
                }
                break;
            case (R.id.reliability_s2):
                if (binding.reliabilityS2.getBackground() == filledStar && binding.reliabilityS3.getBackground() == starOutline) {
                    mViewModel.setReliabilityRating(0);
                    binding.reliabilityS1.setBackground(starOutline);
                    binding.reliabilityS2.setBackground(starOutline);
                    binding.reliabilityS3.setBackground(starOutline);
                } else {
                    mViewModel.setReliabilityRating(2);
                    binding.reliabilityS1.setBackground(filledStar);
                    binding.reliabilityS2.setBackground(filledStar);
                    binding.reliabilityS3.setBackground(starOutline);
                    binding.reliabilityS4.setBackground(starOutline);
                    binding.reliabilityS5.setBackground(starOutline);
                }
                break;
            case (R.id.reliability_s3):
                if (binding.reliabilityS3.getBackground() == filledStar && binding.reliabilityS4.getBackground() == starOutline) {
                    mViewModel.setReliabilityRating(0);
                    binding.reliabilityS1.setBackground(starOutline);
                    binding.reliabilityS2.setBackground(starOutline);
                    binding.reliabilityS3.setBackground(starOutline);
                } else {
                    mViewModel.setReliabilityRating(3);
                    binding.reliabilityS1.setBackground(filledStar);
                    binding.reliabilityS2.setBackground(filledStar);
                    binding.reliabilityS3.setBackground(filledStar);
                    binding.reliabilityS4.setBackground(starOutline);
                    binding.reliabilityS5.setBackground(starOutline);
                }
                break;
            case (R.id.reliability_s4):
                if (binding.reliabilityS4.getBackground() == filledStar && binding.reliabilityS5.getBackground() == starOutline) {
                    mViewModel.setReliabilityRating(0);
                    binding.reliabilityS1.setBackground(starOutline);
                    binding.reliabilityS2.setBackground(starOutline);
                    binding.reliabilityS3.setBackground(starOutline);
                    binding.reliabilityS4.setBackground(starOutline);
                }else{
                    mViewModel.setReliabilityRating(4);
                    binding.reliabilityS1.setBackground(filledStar);
                    binding.reliabilityS2.setBackground(filledStar);
                    binding.reliabilityS3.setBackground(filledStar);
                    binding.reliabilityS4.setBackground(filledStar);
                    binding.reliabilityS5.setBackground(starOutline);
                }
                break;
            case (R.id.reliability_s5):
                if (binding.reliabilityS5.getBackground() == filledStar) {
                    mViewModel.setReliabilityRating(0);
                    binding.reliabilityS1.setBackground(starOutline);
                    binding.reliabilityS2.setBackground(starOutline);
                    binding.reliabilityS3.setBackground(starOutline);
                    binding.reliabilityS4.setBackground(starOutline);
                    binding.reliabilityS5.setBackground(starOutline);
                }else{
                    mViewModel.setReliabilityRating(5);
                    binding.reliabilityS1.setBackground(filledStar);
                    binding.reliabilityS2.setBackground(filledStar);
                    binding.reliabilityS3.setBackground(filledStar);
                    binding.reliabilityS4.setBackground(filledStar);
                    binding.reliabilityS5.setBackground(filledStar);
                }
                break;
            default:
                mViewModel.setReliabilityRating(0);
                binding.reliabilityS1.setBackground(starOutline);
                binding.reliabilityS2.setBackground(starOutline);
                binding.reliabilityS3.setBackground(starOutline);
                binding.reliabilityS4.setBackground(starOutline);
                binding.reliabilityS5.setBackground(starOutline);
                break;
        }
    }
}