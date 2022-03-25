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
import com.example.rockaroundapp.databinding.FragmentArtistProfileBinding;
import com.example.rockaroundapp.viewmodel.ArtistProfileViewModel;
import com.example.rockaroundapp.viewmodel.ReviewOfArtistViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mysql.cj.util.StringUtils;

public class ArtistProfileFragment extends Fragment {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ArtistProfileViewModel viewModel;
    private ReviewOfArtistViewModel reviewViewModel;
    private FragmentArtistProfileBinding binding;
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
                inflater, R.layout.fragment_artist_profile, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(ArtistProfileViewModel.class);
        //reviewViewModel = new ViewModelProvider(this).get(ReviewOfArtistViewModel.class);
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
            if (StringUtils.isEmptyOrWhitespaceOnly(artist.getProfileImg())) {
                binding.profileImage.setImageDrawable(artist.getDefaultProfiler());
            }
        });
    }

    private void checkIsReviewed() {
        viewModel.alreadyReviewed(id).observe(getViewLifecycleOwner(), alreadyReviewed -> {
            if (Boolean.TRUE.equals(alreadyReviewed)) {
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void observeGroup() {
        viewModel.getGroupArtist(id).observe(getViewLifecycleOwner(), groupArtist -> {
            binding.artistMembers.setVisibility(View.VISIBLE);
            binding.setArtistModel(groupArtist);
            binding.setGroupModel(groupArtist);
            if (StringUtils.isEmptyOrWhitespaceOnly(groupArtist.getProfileImg())) {
                binding.profileImage.setImageDrawable(groupArtist.getDefaultProfiler());
            }
        });
    }

    private void onWriteReviewClicked(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("artistId", id);
        navController.navigate(R.id.action_artistProfile_to_reviewOfArtistFragment, bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        checkIsReviewed();
        binding.writeReviewButton.setOnClickListener(this::onWriteReviewClicked);
        super.onViewCreated(view, savedInstanceState);
    }

    //TODO Implement account page
    //TODO Implement Maps Page
    //TODO Fully implement Reviews
}