package com.example.rockaroundapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentSoloProfileBinding;
import com.example.rockaroundapp.viewmodel.ArtistProfileViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SoloProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ArtistProfileViewModel viewModel;
    private FragmentSoloProfileBinding binding;
    private TextDrawable orgProfiler;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private String id;
    private NavController navController;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private String currentUid = auth.getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        toolbar.setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_solo_profile, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(ArtistProfileViewModel.class);
        assert getArguments() != null;
        id = getArguments().getString("id");
        binding.setLifecycleOwner(this);
        observeSolo();
        observeGroup();
        return view;
    }

    private void observeSolo() {
        viewModel.getSoloArtist(id).observe(getViewLifecycleOwner(), artist -> {
            binding.setArtistModel(artist);
            if (artist.getProfileImg().isEmpty()) {
                orgProfiler = TextDrawable.builder().buildRect(String.valueOf(artist.getStageName().charAt(0)), generator.getRandomColor());
                binding.profileImage.setImageDrawable(orgProfiler);
            }
            if (Objects.equals(artist.getId(), currentUid)) {
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void observeGroup() {
        viewModel.getGroupArtist(id).observe(getViewLifecycleOwner(), groupArtist -> {
            binding.artistMembers.setVisibility(View.VISIBLE);
            binding.setGroupModel(groupArtist);
            binding.setArtistModel(groupArtist);
            if (groupArtist.getProfileImg().isEmpty()) {
                orgProfiler = TextDrawable.builder().buildRect(String.valueOf(groupArtist.getStageName().charAt(0)), generator.getRandomColor());
                binding.profileImage.setImageDrawable(orgProfiler);
            }
            if (Objects.equals(groupArtist.getId(), currentUid)) {
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    //TODO Add review onClicks
    private void onWriteReviewClicked(View view) {
        navController.navigate(R.id.action_soloProfileFragment_to_reviewOfArtistFragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.writeReviewButton.setOnClickListener(this::onWriteReviewClicked);
        super.onViewCreated(view, savedInstanceState);
    }

    //TODO link up group to explore
    //TODO Do venue explore
    //TODO Implement account page
    //TODO Implement Maps Page
    //TODO Fully implement Reviews
}