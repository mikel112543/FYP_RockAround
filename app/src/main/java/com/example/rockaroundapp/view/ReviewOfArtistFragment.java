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

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.review_of_artist_fragment, container, false);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(ReviewOfArtistViewModel.class);
        binding.setReviewOfArtistViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        toolbar = getActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.VISIBLE);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }


}