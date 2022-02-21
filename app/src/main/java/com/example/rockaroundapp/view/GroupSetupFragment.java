package com.example.rockaroundapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rockaroundapp.R;
import com.example.rockaroundapp.databinding.FragmentGroupSetupBinding;
import com.example.rockaroundapp.viewmodel.GroupSetupViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class GroupSetupFragment extends Fragment {

    private NavController navController;
    private FragmentGroupSetupBinding binding;
    private GroupSetupViewModel groupSetupViewModel;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_group_setup, container, false);
        View view = binding.getRoot();
        groupSetupViewModel = new ViewModelProvider(this).get(GroupSetupViewModel.class);
        binding.setGroupSetupViewModel(groupSetupViewModel);
        binding.setLifecycleOwner(this);
        toolbar = requireActivity().findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.INVISIBLE);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navbar);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        return view;
    }

    public void observeViewModel() {
        groupSetupViewModel.getGroupArtist().observe(getViewLifecycleOwner(), groupArtist -> {
            if(groupArtist.getStageName().isEmpty()) {
                binding.stageNameTextInputLayout.setError("Please provide a name for your group");
            }else{
                binding.stageNameTextInputLayout.setError(null);
            }
            if(groupArtist.getNoOfMembers() <= 1) {
                binding.noOfMembersText.setError("Please provide members greater than 1");
            }else{
                binding.noOfMembersText.setError(null);
            }
            if(groupArtist.getGenres().isEmpty()) {
                Toast.makeText(getActivity(), "Please choose at least one genre", Toast.LENGTH_SHORT).show();
            }
            if(groupArtist.getPrice().isEmpty()) {
                binding.priceText.setError("Please input your price per session");
            }else{
                binding.priceText.setError(null);
            }
            if(groupArtist.getContactNumber().isEmpty()) {
                binding.contactText.setError("Please provide a contact number");
            }else{
                binding.contactText.setError(null);
            }
            if(!groupArtist.getStageName().isEmpty() && groupArtist.getNoOfMembers() > 1 &&
                    !groupArtist.getGenres().isEmpty() && !groupArtist.getPrice().isEmpty() &&
                        !groupArtist.getContactNumber().isEmpty()) {
                groupSetupViewModel.saveInfo();
            }
        });
        //TODO Store Group Artist in DB
        //TODO Edit Genre Dialog to accept all user types
        //TODO Move on to storing Venue user into DB

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        super.onViewCreated(view, savedInstanceState);
    }
}