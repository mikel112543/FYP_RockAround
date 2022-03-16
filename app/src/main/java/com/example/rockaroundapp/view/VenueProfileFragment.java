package com.example.rockaroundapp.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentVenueProfileBinding;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_venue_profile, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(VenueProfileViewModel.class);
        assert getArguments() != null;
        id = getArguments().getString("id");
        binding.setLifecycleOwner(this);
        observeVenue();
        return view;
    }

    private void observeVenue() {
        viewModel.getVenue(id).observe(getViewLifecycleOwner(), venue -> {
            binding.setVenueModel(venue);
            if (StringUtils.isEmptyOrWhitespaceOnly(venue.getProfileImg())) {
                Drawable orgProfile = getResources().getDrawable(R.drawable.ic_baseline_account_circle_24);
                binding.profileImage.setImageDrawable(orgProfile);
            }
            if (Objects.equals(currentUid, venue.getId())) {
                binding.writeReviewButton.setVisibility(View.INVISIBLE);
            }
        });
    }
}