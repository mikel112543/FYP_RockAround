package com.example.rockaroundapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentArtistProfileBinding;
import com.example.rockaroundapp.databinding.FragmentVenueProfileBinding;
import com.example.rockaroundapp.model.Artist;
import com.example.rockaroundapp.model.Venue;
import com.example.rockaroundapp.viewmodel.AccountViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private NavController navController;
    private AppBarConfiguration configuration;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final String currentUid = auth.getUid();
    private String currentUserType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        AccountViewModel viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        assert getArguments() != null;
        currentUserType = getArguments().getString("currentUserType");
        viewModel.getUserType().observe(getViewLifecycleOwner(), userType -> currentUserType = userType);
        if (currentUserType.equalsIgnoreCase("solo") || currentUserType.equalsIgnoreCase("group")) {
            FragmentArtistProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_profile, container, false);
            binding.setLifecycleOwner(this);
            view = binding.getRoot();
            if (currentUserType.equalsIgnoreCase("solo")) {
                viewModel.getSoloUser(currentUid).observe(getViewLifecycleOwner(), solo -> {
                    binding.setArtistModel(solo);
                    mapArtistRatings(solo, binding);
                });
            } else {
                viewModel.getGroupUser(currentUid).observe(getViewLifecycleOwner(), groupArtist -> {
                    binding.setArtistModel(groupArtist);
                    binding.setGroupModel(groupArtist);
                    mapArtistRatings(groupArtist, binding);
                });
            }
            binding.writeReviewButton.setVisibility(View.INVISIBLE);
            viewModel.getArtistReviews().observe(getViewLifecycleOwner(), venueReviews -> {
                if (!venueReviews.isEmpty()) {
                    binding.setArtistReview1(venueReviews.get(0));
                } else {
                    binding.noReviews.setVisibility(View.VISIBLE);
                    binding.divider5.setVisibility(View.INVISIBLE);
                }
                if (venueReviews.size() >= 2) {
                    binding.setArtistReview2(venueReviews.get(1));
                }
            });
        } else {
            FragmentVenueProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venue_profile, container, false);
            binding.setLifecycleOwner(this);
            view = binding.getRoot();
            viewModel.getVenueUser(currentUid).observe(getViewLifecycleOwner(), venue -> {
                binding.setVenueModel(venue);
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
                mapVenueRatings(venue, binding);
            });
            viewModel.getVenueReviews().observe(getViewLifecycleOwner(), venueReviews -> {
                if (!venueReviews.isEmpty()) {
                    binding.setReviewOne(venueReviews.get(0));
                } else {
                    binding.noReviews.setVisibility(View.VISIBLE);
                    binding.divider5.setVisibility(View.INVISIBLE);
                }
                if (venueReviews.size() >= 2) {
                    binding.setReviewTwo(venueReviews.get(1));
                }
            });
        }
        Toolbar toolbar = requireActivity().findViewById(R.id.main_toolbar);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.account_toolbar_menu);
        configuration = new AppBarConfiguration.Builder(R.id.discover, R.id.mapsFragment, R.id.account).build();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.account_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            auth.signOut();
            navController.navigate(R.id.action_account_to_loginFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    private void mapArtistRatings(Artist artist, FragmentArtistProfileBinding binding) {
        mapRating(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, artist.getAvgReliabilityRating());
        mapRating(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, artist.getAvgCommunicationRating());
        mapRating(binding.vocalsS1, binding.vocalsS2, binding.vocalsS3, binding.vocalsS4, binding.vocalsS5, artist.getAvgVocalsRating());
        mapRating(binding.stagePresenceS1, binding.stagePresenceS2, binding.stagePresenceS3, binding.stagePresenceS4, binding.stagePresenceS5, artist.getAvgStagePresenceRating());
        mapRating(binding.overallRatingS1, binding.overallRatingS2, binding.overallRatingS3, binding.overallRatingS4, binding.overallRatingS5, artist.getAvgOverallRating());
    }

    private void mapVenueRatings(Venue venue, FragmentVenueProfileBinding binding) {
        mapRating(binding.reliabilityS1, binding.reliabilityS2, binding.reliabilityS3, binding.reliabilityS4, binding.reliabilityS5, venue.getAvgReliabilityRating());
        mapRating(binding.communicationS1, binding.communicationS2, binding.communicationS3, binding.communicationS4, binding.communicationS5, venue.getAvgCommunicationRating());
        mapRating(binding.settingS1, binding.settingS2, binding.settingS3, binding.settingS4, binding.settingS5, venue.getAvgSettingRating());
        mapRating(binding.atmosphereS1, binding.atmosphereS2, binding.atmosphereS3, binding.atmosphereS4, binding.atmosphereS5, venue.getAvgAtmosphereRating());
        mapRating(binding.overallRatingS1, binding.overallRatingS2, binding.overallRatingS3, binding.overallRatingS4, binding.overallRatingS5, venue.getAvgOverallRating());
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
        } else {
            if (rating <= 0.50) {
                star1.setImageResource(halfStar);
            }
            if (rating > 0.50) {
                star1.setImageResource(starFilled);
            }
            if (rating > 1.00 && rating <= 1.50) {
                star2.setImageResource(halfStar);
            }
            if (rating > 1.50) {
                star2.setImageResource(starFilled);
            }
            if (rating > 2.00 && rating <= 2.50) {
                star3.setImageResource(halfStar);
            }
            if (rating > 2.50) {
                star3.setImageResource(starFilled);
            }
            if (rating > 3.00 && rating <= 3.50) {
                star4.setImageResource(halfStar);
            }
            if (rating > 3.50) {
                star4.setImageResource(starFilled);
            }
            if (rating > 4.00 && rating <= 4.50) {
                star5.setImageResource(halfStar);
            }
            if (rating > 4.50) {
                star5.setImageResource(starFilled);
            }
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, configuration);
        super.onViewCreated(view, savedInstanceState);
    }
}