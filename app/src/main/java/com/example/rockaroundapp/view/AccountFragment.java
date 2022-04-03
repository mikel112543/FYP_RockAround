package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private NavController navController;
    private AppBarConfiguration configuration;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUid = auth.getUid();
    private AccountViewModel viewModel;
    private String currentUserType;
    private Drawable starOutline;
    private Drawable starFilled;
    private Drawable halfStar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);
        assert getArguments() != null;
        currentUserType = getArguments().getString("currentUserType");
        starFilled = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_rate_50);
        starOutline = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_outline_24);
        halfStar = ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_half_24);
        if (currentUserType.equalsIgnoreCase("solo")) {
            FragmentArtistProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_profile, container, false);
            binding.setLifecycleOwner(this);
            view = binding.getRoot();
            viewModel.getSoloUser(currentUid).observe(getViewLifecycleOwner(), solo -> {
                binding.setArtistModel(solo);
                mapArtistRatings(solo, binding);
            });
        } else if(currentUserType.equalsIgnoreCase("group")){
            FragmentArtistProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_profile, container, false);
            binding.setLifecycleOwner(this);
            view = binding.getRoot();
            viewModel.getGroupUser(currentUid).observe(getViewLifecycleOwner(), groupArtist -> {
                binding.setArtistModel(groupArtist);
                binding.setGroupModel(groupArtist);
                mapArtistRatings(groupArtist, binding);
            });
        }else{
            FragmentVenueProfileBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venue_profile, container, false);
            binding.setLifecycleOwner(this);
            view = binding.getRoot();
            viewModel.getVenueUser(currentUid).observe(getViewLifecycleOwner(), venue -> {
                binding.setVenueModel(venue);
            });
        }
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        setHasOptionsMenu(true);
        toolbar.inflateMenu(R.menu.account_toolbar_menu);
        configuration = new AppBarConfiguration.Builder(R.id.discover, R.id.map1, R.id.account).build();
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) requireActivity(), navController, configuration);
        super.onViewCreated(view, savedInstanceState);
    }
}