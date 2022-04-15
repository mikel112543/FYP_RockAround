package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentReviewOfVenueBinding;
import com.example.rockaroundapp.viewmodel.VenueReviewsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ReviewOfVenueFragment extends Fragment {

    private VenueReviewsViewModel mViewModel;
    private FragmentReviewOfVenueBinding binding;
    private Drawable starOutline;
    private Drawable filledStar;
    private final String currentUid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        getArguments().getString("venueId");
        filledStar = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_review_of_venue, container, false);
        mViewModel = new ViewModelProvider(this).get(VenueReviewsViewModel.class);
        return binding.getRoot();
    }

    private void observe() {
        mViewModel.get_venueReview().observe(getViewLifecycleOwner(), venueReview -> {
            if (binding.reviewTitle.getText().toString().isEmpty()) {
                binding.reviewTitle.setError("Please provide a title");
            }
            if (binding.reviewDescription.getText().toString().isEmpty()) {
                binding.reviewDescription.setError("Please write a review");
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        binding.communicationS1.setOnClickListener(this::setRatings);
        binding.communicationS2.setOnClickListener(this::setRatings);
        binding.communicationS3.setOnClickListener(this::setRatings);
        binding.communicationS4.setOnClickListener(this::setRatings);
        binding.communicationS5.setOnClickListener(this::setRatings);

        binding.atmosphereS1.setOnClickListener(this::setRatings);
        binding.atmosphereS2.setOnClickListener(this::setRatings);
        binding.atmosphereS3.setOnClickListener(this::setRatings);
        binding.atmosphereS4.setOnClickListener(this::setRatings);
        binding.atmosphereS5.setOnClickListener(this::setRatings);

        binding.settingS1.setOnClickListener(this::setRatings);
        binding.settingS2.setOnClickListener(this::setRatings);
        binding.settingS3.setOnClickListener(this::setRatings);
        binding.settingS4.setOnClickListener(this::setRatings);
        binding.settingS5.setOnClickListener(this::setRatings);

        binding.reliabilityS1.setOnClickListener(this::setRatings);
        binding.reliabilityS2.setOnClickListener(this::setRatings);
        binding.reliabilityS3.setOnClickListener(this::setRatings);
        binding.reliabilityS4.setOnClickListener(this::setRatings);
        binding.reliabilityS5.setOnClickListener(this::setRatings);

        super.onViewCreated(view, savedInstanceState);
    }

    enum type {
        COMMUNICATION,
        VOCALS,
        ATMOSPHERE,
        RELIABILITY,
        STAGE_PRESENCE,
        SETTING
    }

    private void setRatings(View view) {
        if (view.getId() == R.id.communication_s1) {
            setStar1(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, type.COMMUNICATION);
        } else if (view.getId() == R.id.communication_s2) {
            setStar2(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, type.COMMUNICATION);
        } else if (view.getId() == R.id.communication_s3) {
            setStar3(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, type.COMMUNICATION);
        } else if (view.getId() == R.id.communication_s4) {
            setStar4(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, type.COMMUNICATION);
        } else if (view.getId() == R.id.communication_s5) {
            setStar5(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, type.COMMUNICATION);
        } else if (view.getId() == R.id.reliability_s1) {
            setStar1(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, type.RELIABILITY);
        } else if (view.getId() == R.id.reliability_s2) {
            setStar2(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, type.RELIABILITY);
        } else if (view.getId() == R.id.reliability_s3) {
            setStar3(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, type.RELIABILITY);
        } else if (view.getId() == R.id.reliability_s4) {
            setStar4(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, type.RELIABILITY);
        } else if (view.getId() == R.id.reliability_s5) {
            setStar5(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, type.RELIABILITY);
        } else if (view.getId() == R.id.setting_s1) {
            setStar1(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, type.SETTING);
        } else if (view.getId() == R.id.setting_s2) {
            setStar2(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, type.SETTING);
        } else if (view.getId() == R.id.setting_s3) {
            setStar3(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, type.SETTING);
        } else if (view.getId() == R.id.setting_s4) {
            setStar4(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, type.SETTING);
        } else if (view.getId() == R.id.setting_s5) {
            setStar5(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, type.SETTING);
        } else if (view.getId() == R.id.atmosphere_s1) {
            setStar1(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, type.ATMOSPHERE);
        } else if (view.getId() == R.id.atmosphere_s2) {
            setStar2(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, type.ATMOSPHERE);
        } else if (view.getId() == R.id.atmosphere_s3) {
            setStar3(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, type.ATMOSPHERE);
        } else if (view.getId() == R.id.atmosphere_s4) {
            setStar4(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, type.ATMOSPHERE);
        } else {
            setStar5(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, type.ATMOSPHERE);
        }
    }

    private void setRating(type ratingType, int rating) {
        switch (ratingType) {
            case COMMUNICATION:
                mViewModel.setCommunicationRating(rating);
                break;
            case RELIABILITY:
                mViewModel.setReliabilityRating(rating);
                break;
            case SETTING:
                mViewModel.setSettingRating(rating);
                break;
            case ATMOSPHERE:
                mViewModel.setAtmosphereRating(rating);
                break;
            default:
                break;
        }
    }

    private void setStar1(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, type ratingType) {
        if (star1.getBackground() == filledStar && star2.getBackground() == starOutline) {
            setRating(ratingType, 0);
            star1.setBackground(starOutline);
        } else {
            setRating(ratingType, 1);
            star1.setBackground(filledStar);
            star2.setBackground(starOutline);
            star3.setBackground(starOutline);
            star4.setBackground(starOutline);
            star5.setBackground(starOutline);
        }
    }

    private void setStar2(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, type ratingType) {
        if (star2.getBackground() == filledStar && star3.getBackground() == starOutline) {
            setRating(ratingType, 0);
            star1.setBackground(starOutline);
            star2.setBackground(starOutline);
        } else {
            setRating(ratingType, 2);
            star1.setBackground(filledStar);
            star2.setBackground(filledStar);
            star3.setBackground(starOutline);
            star4.setBackground(starOutline);
            star5.setBackground(starOutline);
        }
    }

    private void setStar3(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, type ratingType) {
        if (star3.getBackground() == filledStar && star4.getBackground() == starOutline) {
            setRating(ratingType, 0);
            star1.setBackground(starOutline);
            star2.setBackground(starOutline);
            star3.setBackground(starOutline);
        } else {
            setRating(ratingType, 3);
            star1.setBackground(filledStar);
            star2.setBackground(filledStar);
            star3.setBackground(filledStar);
            star4.setBackground(starOutline);
            star5.setBackground(starOutline);
        }
    }

    private void setStar4(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, type ratingType) {
        if (star4.getBackground() == filledStar && star5.getBackground() == starOutline) {
            setRating(ratingType, 0);
            star1.setBackground(starOutline);
            star2.setBackground(starOutline);
            star3.setBackground(starOutline);
            star4.setBackground(starOutline);
        } else {
            setRating(ratingType, 4);
            star1.setBackground(filledStar);
            star2.setBackground(filledStar);
            star3.setBackground(filledStar);
            star4.setBackground(filledStar);
            star5.setBackground(starOutline);
        }
    }

    private void setStar5(ImageView star1, ImageView star2, ImageView star3, ImageView star4, ImageView star5, type ratingType) {
        if (star4.getBackground() == filledStar) {
            setRating(ratingType, 0);
            star1.setBackground(starOutline);
            star2.setBackground(starOutline);
            star3.setBackground(starOutline);
            star4.setBackground(starOutline);
            star5.setBackground(starOutline);
        } else {
            setRating(ratingType, 5);
            star1.setBackground(filledStar);
            star2.setBackground(filledStar);
            star3.setBackground(filledStar);
            star4.setBackground(filledStar);
            star5.setBackground(filledStar);
        }
    }

}